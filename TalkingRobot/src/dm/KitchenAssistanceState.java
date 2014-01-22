package dm;

/**
 * This class represents the different states a KitchenAssistanceDialog can have.
 * @author Daniel Draper
 * @version 1.0
 *
 */
public class KitchenAssistanceState extends DialogState {


	/**
	 * Creates a new KitchenAssistanceState in the ENTRY state.
	 */
  public KitchenAssistanceState() {
	  super();
	  setCurrentState(KitchenAssistance.ENTRY);
  }
  /**
   * Creates a new KitchenAssistanceState in the state specified.
   * @param currentState the state in which KitchenAssistance will be
   */
  public KitchenAssistanceState(KitchenAssistance currentState) {
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