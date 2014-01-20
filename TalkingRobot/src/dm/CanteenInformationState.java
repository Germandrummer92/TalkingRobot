package dm;


/**
 * This Class represents a state of an Canteen Information Dialog.
 * @author Daniel Draper
 * @version 1.0
 *
 */
public class CanteenInformationState extends DialogState {

    private CanteenInfo currentState;

    /**
     * Creates a new CanteenInformationState in the ENTRY state.
     */
    public CanteenInformationState() {
    	super();
    	currentState = CanteenInfo.ENTRY;
    }
    
    /**
     * Creates a new CanteenInformationState in the state specified
     * @param currentState the state of the new object
     */
    public CanteenInformationState(CanteenInfo currentState) {
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
public CanteenInfo getCurrentState() {
	return currentState;
}

/**
 * @param currentState the currentState to set
 */
public void setCurrentState(CanteenInfo currentState) {
	this.currentState = currentState;
}

}