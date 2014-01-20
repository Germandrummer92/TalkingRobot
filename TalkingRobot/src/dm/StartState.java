package dm;

/**
 * This class represents the different states a StartDialog can have.
 * @author Daniel Draper
 * @version 1.0
 *
 */
public class StartState extends DialogState {

	private Start currentState;
	
	/**
	 * Creates a new StartState in the ENTRY state.
	 */
  public StartState() {
	  super();
	  currentState = Start.ENTRY;
  }
  /**
   * Creates a new StartState in the state specified.
   * @param the state of Start
   */
  public StartState(Start currentState) {
	  super();
	  this.currentState = currentState;
  }
  /**
   * @see DialogState#getOutputKeyword()
   */
  public String getOutputKeyword() {
  return null;
  }
  
  
/**
 * @return the currentState
 */
public Start getCurrentState() {
	return currentState;
}
/**
 * @param currentState the currentState to set
 */
public void setCurrentState(Start currentState) {
	this.currentState = currentState;
}


}