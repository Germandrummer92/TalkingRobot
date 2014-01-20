package dm;

/**
 * This class represents the different states a KitchenAssistanceDialog can have.
 * @author Daniel Draper
 * @version 1.0
 *
 */
public class KitchenAssistanceState extends DialogState {

	private KitchenAssistance currentState;

	/**
	 * Creates a new KitchenAssistanceState in the ENTRY state.
	 */
  public KitchenAssistanceState() {
	  super();
	  currentState = KitchenAssistance.ENTRY;
  }
  /**
   * Creates a new KitchenAssistanceState in the state specified.
   * @param currentState the state in which KitchenAssistance will be
   */
  public KitchenAssistanceState(KitchenAssistance currentState) {
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
public KitchenAssistance getCurrentState() {
	return currentState;
}
/**
 * @param currentState the currentState to set
 */
public void setCurrentState(KitchenAssistance currentState) {
	this.currentState = currentState;
}

}