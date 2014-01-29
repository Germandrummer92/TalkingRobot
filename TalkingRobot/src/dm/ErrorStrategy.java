package dm;

import java.util.List;

/**
 * 
 * @author Meng Meng Yan
 * @version 1.0
 * template for the error strategy classes.
 */
public abstract class ErrorStrategy {

	 /**
	  * indicates the number of times the certain strategy has been used in one 
	  * errorhandling round.
	  */
	  protected Integer counter = 0;
	
	  /**
	   * method to handle an error with the given list of words which might be keywords.
	   * @param errorWords words which might be keywords
	   * @return a special DialogState which contains the information 
	   * needed by the NLG to create the next output
	   */
	  public abstract ErrorHandlingState handleError(List<String> errorWords);
	
	  /**
	   * resets the counter to zero.
	   */
	  public void clearCounter() {
		  counter = 0;
	  }
	
	  /**
	   * getter-method of counter.
	   * @return the value of counter
	   */
	  public Integer getCounter() {
		  return counter;
	  }
	
	  /**
	   * rises value of counter by one.
	   */
	  protected void riseCounter() {
		  counter++;
	  }
}
