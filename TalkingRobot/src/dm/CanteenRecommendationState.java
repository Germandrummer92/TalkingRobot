package dm;


/**
 * This class represents the state a Canteen Recommendation Dialog can have.
 * @author Daniel Draper
 * @version 1.0
 *
 */
public class CanteenRecommendationState extends DialogState {

    private CanteenRecom currentState;
    
    /**
     * Creates a new CanteenRecommendationState in the ENTRY state.
     */
    public CanteenRecommendationState() {
    	super();
    	currentState = CanteenRecom.ENTRY;
    }
    
    /**
     * Creates a new CanteenRecommendationState in the specified state.
     * @param currentState the new state.
     */
    public CanteenRecommendationState(CanteenRecom currentState) {
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
public CanteenRecom getCurrentState() {
	return currentState;
}

/**
 * @param currentState the currentState to set
 */
public void setCurrentState(CanteenRecom currentState) {
	this.currentState = currentState;
}


}