package dm;


/**
 * This class represents a DialogState taken by one of the Dialogs in the system.
 * @author Daniel Draper
 * @version 1.0
 *
 */
public class DialogState {


  private Boolean question;

  /**
   * Creates and returns the keyword responsible for the NLG creation of an output.
   * @return the keyword
   */
  public String getOutputKeyword() {
	  return null;
  }

  /**
   * Creates a new DialogState
   */
  public DialogState() {
	  
  }
 

  /**
   * Returns if the output is a question or an answer to a question.
   * @return
   */
  public Boolean isQuestion() {
	  return question;
  }

/**
 * @param question the question to set
 */
public void setQuestion(Boolean question) {
	this.question = question;
}

  
}