package dm;


/**
 * This Class represents a state of an Canteen Information Dialog.
 * @author Daniel Draper
 * @version 1.0
 *
 */
public class CanteenInformationState extends DialogState {

 

    /**
     * Creates a new CanteenInformationState in the ENTRY state.
     */
    public CanteenInformationState() {
    	super();
    	setCurrentState(CanteenInfo.CI_ENTRY);
    }
    
    /**
     * Creates a new CanteenInformationState in the state specified
     * @param currentState the state of the new object
     */
    public CanteenInformationState(CanteenInfo currentState) {
    	super();
    	setCurrentState(currentState);
    }
  /**
   * @see DialogState#getOutputKeyword()
   */
  public String getOutputKeyword() {
	  String keywords = null;
	  
  return keywords;
  }

}