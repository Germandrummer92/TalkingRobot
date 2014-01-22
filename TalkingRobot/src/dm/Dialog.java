package dm;
import java.util.List;


/**
 * Abstract class describing one dialog in the system.
 * @author Daniel Draper
 * @version 1.0
 *
 */
public abstract class Dialog {

  private Session currentSession;

  private DialogState currentState;
  
  /**
   * Creates a new dialog without a specified state in the first possible DialogState.
   * @param currentSession the currentSession starting this Dialog
   */
  public Dialog(Session currentSession) {
	  this.currentSession = currentSession;
	  currentState = new StartState();
	  currentState.setCurrentState(Start.ENTRY);
  }
  
  /**
   * Creates a new Dialog in a certain DialogState.
   * @param currentSession the current Session
   * @param currentState the current DialogState
   */
   public Dialog(Session currentSession, DialogState currentState) {
	   this.currentSession = currentSession;
	   this.currentState = currentState;
   }

  /**
   * 
   * @return the current Session
   */
  public Session getCurrentSession() {
  		return currentSession;
  }

  /**
   * Updates the State of the current Dialog according to the User's input.
   * @param keywords the keywords input by the user
   * @param terms the terms input by the user
   * @param approval the answer of an approval question
 * @throws WrongStateClassException if the DialogState isn't compatible with the current Dialog
   */

  public abstract void updateState(List<Keyword> keywords, List<String> terms, List<String> approval)
		  throws WrongStateClassException;


  /**
   * 
   * @return the current State the Dialog is in
   */
  public DialogState getCurrentDialogState() {
  		return currentState;
  }


/**
 * @param currentSession the currentSession to set
 */
public void setCurrentSession(Session currentSession) {
	this.currentSession = currentSession;
}

/**
 * @param currentState the currentState to set
 */
public void setCurrentDialogState(DialogState currentState) {
	this.currentState = currentState;
}

}