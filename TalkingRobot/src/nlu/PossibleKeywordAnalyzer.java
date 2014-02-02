package nlu;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import dm.Dictionary;
import dm.Keyword;

/**
 * 
 * @author Meng Meng Yan
 * @version 1.0
 * The class PossibleKeywordAnalyzer analyzes the input String regarding words which might be
 * keywords which indicate the next possible dialogstate. It will compare possible keywords to actual
 * keywords.
 */
public class PossibleKeywordAnalyzer extends InputAnalyzer {

	
	
	private Dictionary dictionary;
	  
	/**
	 * Creates a new PossibleKeywordAnalyzer object.
	 */
	public PossibleKeywordAnalyzer() {
		this.runParse = "run_parse_possiblekw";
		this.extractFlag = 0;
		this.compile = new File ("resources/nlu/Phoenix/TalkingRobot/PossibleKw/");
		this.dictionary = new Dictionary();
	}
	  
	/**
	 * @see InputAdapter#analyze()
	 */
	public List<String> analyze(String input) {
		 LinkedList<String> result = phoenix.operatePhoenix(this.runParse, this.extractFlag, this.compile);
		  
		 LinkedList<String> possibleKeywords = this.cleanInput(input, result);
		 LinkedList<String> possibleKeywords2 = this.checkForKeywordsConsistingOfSeveralWords(possibleKeywords, result);
		 possibleKeywords = checkForKeywordsConsistingOfOneWord(possibleKeywords); 
		 
		 possibleKeywords.addAll(possibleKeywords2);
		  
		 return cleanOfNotPossibleKeywords(possibleKeywords);
		 
	 }

	/**
	 * searches for keywords which can be satisfied with only one word from the user input.
	 * @param possibleKeywords words from the input which might be keywords
	 * @return list of words from the input
	 */
	private LinkedList<String> checkForKeywordsConsistingOfOneWord(
			LinkedList<String> possibleKeywords) {
		for(int i = 0; i < possibleKeywords.size(); i++) {
			 String actualKeyword = compareToAll(possibleKeywords.get(i));
			  
			 if(actualKeyword != null) {
				 Levenshtein levenDistance = new Levenshtein(possibleKeywords.get(i), actualKeyword);
				 int distance = levenDistance.getDistance();
				 possibleKeywords.set(i, concatenateWords(possibleKeywords.get(i), actualKeyword, distance));
			 }
		 }
		return possibleKeywords;
	}
	
	/**
	 * searches for keywords which can only be satisfied with several words from the user input.
	 * @param possibleKeywords words from the input which might be keywords
	 * @param result result from Phoenix; words which were part of the input
	 * @return list of words from the input
	 */
	private LinkedList<String> checkForKeywordsConsistingOfSeveralWords(
			LinkedList<String> possibleKeywords, LinkedList<String> result) {
		
		LinkedList<String> output = new LinkedList<String>();
		ArrayList<Keyword> keywordList = (ArrayList<Keyword>) dictionary.getKeywordList();
		
		for(int i = 0; i < keywordList.size(); i++) {
			if(keywordList.get(i).getWord().matches(".+ .+")) {
				String[] keywordWords = keywordList.get(i).getWord().split(" ");
				String possibleOutput = keywordList.get(i).getWord() + ";";
				Integer distance = 0;
				for(int j = 0; j < keywordWords.length; j++) {
					String possibleMatch = this.seekMatchingWords(keywordWords[j], result, possibleKeywords);
					if(possibleMatch.equals("")) {distance = 11;}
					else if(!possibleMatch.endsWith(keywordWords[j])) {
						Levenshtein levenshtein = new Levenshtein(possibleMatch, keywordWords[j]);
						distance = distance + levenshtein.getDistance();
						possibleOutput = possibleOutput + possibleMatch + " ";
					} else { possibleOutput = possibleOutput + possibleMatch + " "; }
				}
				if(distance <= 10) { 
					possibleOutput = possibleOutput.trim() + ";" + distance.toString();
					output.add(possibleOutput);
				}
			}
		}
		
		return output;
	}
	
	/**
	 * seeks for a matching word.
	 * @param keywordWord a word which is part of a keyword; e.g. "how" in "how much costs"
	 * @param word a list of words which were part of the input and were found by phoenix
	 * @param possibleWord words which might be keywords
	 * @return a matching word; if no matching word than an empty string;
	 */
	private String seekMatchingWords(String keywordWord,
			LinkedList<String> word, LinkedList<String> possibleWord) {
		for(int j = 0; j < word.size(); j++) {
			if(keywordWord.equals(word.get(j))) { return word.get(j); }
		}
		
		int shortestDistance = 3;
		String wordWithShortestDistance = "";
		for(int j = 0; j < possibleWord.size(); j++) {
			Levenshtein levenshtein = new Levenshtein(keywordWord, possibleWord.get(j));
			if(levenshtein.getDistance() < shortestDistance) { wordWithShortestDistance = possibleWord.get(j); }
		}
		return wordWithShortestDistance;
	}

	/**
	 * removes words which were found by phoenix from the input.
	 * @param input the user input
	 * @param result the result of phoenix
	 * @return a list of words which were not found by phoenix in the input
	 */
	private LinkedList<String> cleanInput(String input,
			LinkedList<String> result) {
		  
		LinkedList<String> output = new LinkedList<String>();
		output.add(input);
		
		for(int i = 0; i < result.size(); i++) {
			LinkedList<String> nextRound = new LinkedList<String>();
			for(int j = 0; j < output.size(); j++) {
			
				String[] help = output.get(j).split(result.get(i));
					
				for(int k = 0; k < help.length; k++) {
					if(help[k] != "") {
						nextRound.add(help[k].trim());
					}
				}
				
			}
			output = nextRound;
		}
		
		LinkedList<String> singleWords = new LinkedList<String>();
		for(int i= 0; i < output.size(); i++) {
			String[] help = output.get(i).split(" ");
			for(int j = 0; j < help.length; j++) {
				if(help[j] != "") {
					singleWords.add(help[j].trim());
				}
			}
		}
		return singleWords;
	}

	/**
	   * concatenates the three arguments to one String.
	   * 
	   * @param s1 first String; a possible keyword
	   * @param s2 second String; an actual keyword
	   * @param distance the Levenshtein distance between these two distances
	   * @return the concatenation of the 3 arguments
	   */
	  private String concatenateWords(String s1, String s2, Integer distance) {
		  return s1 + ";" + s2 + ";" + distance.toString();
	  }
	  
	  /**
	   * compares one possible keyword to all actual keywords and finds the most probable
	   * keyword.
	   * @param possibleKw the possible keyword
	   * @return the most probable keyword if the Levenshtein distance is smaller than 10
	   */
	  private String compareToAll(String possibleKw) {
		  ArrayList<Keyword> keywordList = (ArrayList<Keyword>) dictionary.getKeywordList();
		  
		  String mostProbableKeyword = "";
		  int shortestDistance = 100;
		  
		  for(int i = 0; i < keywordList.size(); i++) {
			  
			  Levenshtein levenDistance = new Levenshtein(possibleKw, keywordList.get(i).getWord());
			  int distance = levenDistance.getDistance();
			  
			  if(distance < shortestDistance) {
				  mostProbableKeyword = keywordList.get(i).getWord();
				  shortestDistance = distance;
			  }
		  }
		  
		  if(shortestDistance <= 10) {
			  return mostProbableKeyword;
		  } else {
			  return null;
		  }
	  }
	  
	  /**
	   * cleans the list of possible keywords from the improbable keywords.
	   * 
	   * @param possibleKeywords list of possible keywords
	   * @param notPossibleKeywords improbable keywords
	   * @return list which only contains probable keywords
	   */
	  private List<String> cleanOfNotPossibleKeywords(List<String> possibleKeywords) {
		  
		  LinkedList<String> result = new LinkedList<String>();
		  
		  for(int i = 0; i < possibleKeywords.size(); i++) {
			  if(possibleKeywords.get(i).matches(".*;.*;[0-9]+")) { result.add(possibleKeywords.get(i)); }
		  }
		  return result;
	  }
	
}
