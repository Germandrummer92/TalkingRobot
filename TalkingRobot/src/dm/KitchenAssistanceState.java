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
	  Dialog currentDialog = DialogManager.giveDialogManager().getCurrentDialog();
	  switch((KitchenAssistance)getCurrentState()) {
	case KA_ENTRY:
		setQuestion(true);
		return "<" + currentDialog.getCurrentSession().getCurrentUser().getUserData().getUserName() + ">";
	case KA_EXIT:
		setQuestion(false);
		return null;
	case KA_TELL_INGREDIENT_FOUND:
		setQuestion(false);
		String output =  "<" + ((KitchenAssistanceDialog)currentDialog).getRequestedObjectName() + ">";
		output = output + ";{" + ((Ingredient)((KitchenAssistanceDialog)currentDialog).getRequestedObject()).getIngredientData().getIngredientLocation() + "}";
		return output;
	case KA_TELL_INGREDIENT_NOT_FOUND:
		setQuestion(false);
		return "<" + ((KitchenAssistanceDialog)currentDialog).getRequestedObjectName() + ">";
	case KA_TELL_TOOL_FOUND:
		setQuestion(false);
		String output2 =  "<" + ((KitchenAssistanceDialog)currentDialog).getRequestedObjectName() + ">";
		output2 = output2 + ";{" + ((Tool)((KitchenAssistanceDialog)currentDialog).getRequestedObject()).getToolData().getLocation() + "}";
		return output2;
	case KA_TELL_TOOL_NOT_FOUND:
		setQuestion(false);
		return "<" + ((KitchenAssistanceDialog)currentDialog).getRequestedObjectName() + ">";
	default:
		return null;
	  	
	  }
  }


}