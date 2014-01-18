package dm;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import data.KeywordData;


/**
 * The Dictionary of all the current keywords we have.
 * @author Daniel Draper
 * @version 1.0
 *
 */
public class Dictionary {

  private List<Keyword> keywordList;

  /**
   * Creates a new Dictionary and loads all the available keywords.
   */
  public Dictionary() {
	  loadKeywords();
  }
  /**
   * Loads the List of the keywords.
   */
  private void loadKeywords() {
	  keywordList = new ArrayList<Keyword>();
	  for (KeywordData d : KeywordData.loadData()) {
		  keywordList.add(new Keyword(d));
	  }
  }

  /**
   * Find possible Keywords matching the UserInput
   * @param kwInput
   * @return the List of keywords found in the Input.
   */
  public List<Keyword> findKeywords(List<String> kwInput) {
	  ArrayList<Keyword> res = new ArrayList<Keyword>();
	  for (String s: kwInput) {
		  for (Keyword kw: keywordList) {
			  if (kw.toString().equals(s)) {
				  res.add(kw);
			  }
		  }
	  }
	  return res;
  }

}