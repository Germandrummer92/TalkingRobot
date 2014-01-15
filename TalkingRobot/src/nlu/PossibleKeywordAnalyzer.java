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

	  protected File grammarFile = new File("resources/files/NLUGrammar/possibleKwGrammar.gra/");//possibleGrammar.gra
	
	  private Levenshtein levenshtein = new Levenshtein();
	
	  private Dictionary dictionary;
	  
	/**
	 * @see InputAdapter#analyze()
	 */
	public List<String> analyze(String input) {
		  LinkedList<String> result = phoenix.operatePhoenix(grammarFile);
		  
		  LinkedList<String> notPossibleKeywords = new LinkedList<String>();
		  
		  for(int i = 0; i < result.size(); i++) {
			  String actualKeyword = compareToAll(result.get(i));
			  
			  if(actualKeyword == null) {
				  notPossibleKeywords.add(result.get(i));
			  } else {
				  
				  Levenshtein levenDistance = levenshtein.Levenshtein(result.get(i), actualKeyword);
				  int distance = levenDistance.getDistance();
				  
				  result.set(i, concatenateWords(result.get(i), actualKeyword, distance));
			  }
			  
		  }
		  
		  if(notPossibleKeywords.isEmpty()) {
			  return result;
		  } else {
			  return cleanOfNotPossibleKeywords(result, notPossibleKeywords);
		  }
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
		  LinkedList<Keyword> keywordList = null; //TODO benötigt Liste aller Keywords
		  
		  String mostProbableKeyword = "";
		  int shortestDistance = 100;
		  
		  for(int i = 0; i < keywordList.size(); i++) {
			  
			  Levenshtein levenDistance = levenshtein.Levenshtein(possibleKw, keywordList.get(i).toString());
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
	  private List<String> cleanOfNotPossibleKeywords(List<String> possibleKeywords,
			  List<String> notPossibleKeywords) {
		  
		  int j = notPossibleKeywords.size() - 1;
		  while (j >= 0) {
			  for(int i = (possibleKeywords.size() - 1); i >= 0; i--) {
				  if(possibleKeywords.get(i).equals(notPossibleKeywords.get(j))) {
					  possibleKeywords.remove(i);
					  j--;
				  }
			  }
		  }
		  return possibleKeywords;
	  }
}