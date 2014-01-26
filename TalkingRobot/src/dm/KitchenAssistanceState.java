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
	  setCurrentState(KitchenAssistance.KA_ENTRY);
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
	  switch((KitchenAssistance)getCurrentState()) {
	case KA_ENTRY:
		break;
	case KA_EXIT:
		break;
	case KA_TELL_INGREDIENT_FOUND:
		break;
	case KA_TELL_INGREDIENT_NOT_FOUND:
		break;
	case KA_TELL_TOOL_FOUND:
		break;
	case KA_TELL_TOOL_NOT_FOUND:
		break;
	default:
		break;
	  	
	  }
  }


}