package dm;


/**
 * This class represents the state a Canteen Recommendation Dialog can have.
 * @author Daniel Draper
 * @version 1.0
 *
 */
public class CanteenRecommendationState extends DialogState {


    
    /**
     * Creates a new CanteenRecommendationState in the ENTRY state.
     */
    public CanteenRecommendationState() {
    	super();
    	setCurrentState(CanteenRecom.ENTRY);
    }
    
    /**
     * Creates a new CanteenRecommendationState in the specified state.
     * @param currentState the new state.
     */
    public CanteenRecommendationState(CanteenRecom currentState) {
    	super();
    	setCurrentState(currentState);
    }
   /**
    * @see DialogState#getOutputKeyword()
    */
  public String getOutputKeyword() {
	  return null;
  }



}