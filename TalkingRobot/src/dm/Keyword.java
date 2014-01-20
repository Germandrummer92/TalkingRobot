package dm;


import data.Data;
import data.KeywordData;
import data.UserData;

/**
 * A keyword known to the robot.
 * @author Daniel Draper
 * @version 1.0
 *
 */
public class Keyword {

  private KeywordData keywordData;

  /**
   * Creates a new Keyword and saves its data from the parameters specified.
   * @param keyword the actual keyword
   * @param state the state it points to
   * @param priority the priority it has in determining the next state
   * @param reference if it has a reference to a different data object
   */
  public Keyword(String keyword, DialogState state, int priority, Data reference) {
	  keywordData = new KeywordData(keyword, state, priority, reference);
	  keywordData.writeFile();
  }
  
  /**
   * Creates a new Keyword from the data parameter specified.
   * @param d the data to be set
   */
  public Keyword(KeywordData d) {
	  keywordData = d;
  }

  /**
   * 
   * @return the Dialog state this keyword points to.
   */
public DialogState getReference() {
  		return keywordData.getDialogState();
  }

/**
 * @return the keyword as a String.
 */
  public String getWord() {
  		return keywordData.getWord();
  }

/**
 * @return
 */
public KeywordData getKeywordData() {
	return keywordData;
}
  
}