package nlu;

import java.io.File;
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
	}
	  
	/**
	 * @see InputAdapter#analyze()
	 */
	public List<String> analyze(String input) {
		 LinkedList<String> result = phoenix.operatePhoenix(input, this.runParse, this.extractFlag, this.compile);
		  
		 LinkedList<String> possibleKeywords = this.cleanInput(input, result);
		 LinkedList<String> possibleKeywords2 = this.checkForKeywordsConsistingOfSeveralWords(possibleKeywords, result);
		 possibleKeywords = checkForKeywordsConsistingOfOneWord(possibleKeywords); 
		  
		 return cleanOfNotPossibleKeywords(possibleKeywords);
		 
	 }


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

	private LinkedList<String> checkForKeywordsConsistingOfSeveralWords(
			LinkedList<String> possibleKeywords, LinkedList<String> result) {
		// TODO Auto-generated method stub
		return null;
	}

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
		  LinkedList<Keyword> keywordList = (LinkedList<Keyword>) dictionary.getKeywordList();
		  
		  String mostProbableKeyword = "";
		  int shortestDistance = 100;
		  
		  for(int i = 0; i < keywordList.size(); i++) {
			  
			  Levenshtein levenDistance = new Levenshtein(possibleKw, keywordList.get(i).toString());
			  int distance = levenDistance.getDistance();
			  
			  if(distance < shortestDistance) {
				  mostProbableKeyword = keywordList.get(i).toString();
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
	  
	public static void main(String[] args) {
		PossibleKeywordAnalyzer pwa = new PossibleKeywordAnalyzer();

		String help = "hi i am a looser";
		String[] test = help.split("idiot");
		for(int i = 0; i < test.length; i++) {
			System.out.println(test[i]);
		}
	}
}
