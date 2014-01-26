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
		String output =  "<" + ((Ingredient)((KitchenAssistanceDialog)currentDialog).getRequestedObject()).getIngredientData().getIngredientName() + ">";
		output = output + " {" + ((Ingredient)((KitchenAssistanceDialog)currentDialog).getRequestedObject()).getIngredientData().getIngredientLocation() + "}";
		return output;
	case KA_TELL_INGREDIENT_NOT_FOUND:
		setQuestion(false);
		return "<" + ((Ingredient)((KitchenAssistanceDialog)currentDialog).getRequestedObject()).getIngredientData().getIngredientName() + ">";
	case KA_TELL_TOOL_FOUND:
		setQuestion(false);
		String output2 =  "<" + ((Tool)((KitchenAssistanceDialog)currentDialog).getRequestedObject()).getToolData().getToolName() + ">";
		output = output2 + " {" + ((Tool)((KitchenAssistanceDialog)currentDialog).getRequestedObject()).getToolData().getLocation() + "}";
		return output2;
	case KA_TELL_TOOL_NOT_FOUND:
		setQuestion(false);
		return "<" + ((Tool)((KitchenAssistanceDialog)currentDialog).getRequestedObject()).getToolData().getToolName() + ">";
	default:
		return null;
	  	
	  }
  }


}