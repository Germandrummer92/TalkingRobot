package dm;

/**
 * This class represents the state a Recipe Learning Dialog is in.
 * @author Aleksandar Andonov
 * @author Daniel Draper
 * @version 2.0
 *
 */
public class RecipeLearningState extends DialogState {


	/**
	 * Creates a new RecipeLearningState in the ENTRY state.
	 */
	public RecipeLearningState() {
		super();
		setCurrentState(RecipeLearning.RL_ENTRY);
	}
	
	/**
	 * Creates a new RecipeLearningState in the State specified
	 * @param currentState
	 */
	public RecipeLearningState(RecipeLearning currentState) {
		super();
		setCurrentState(currentState);
	}
	
  /**
   * @see DialogState#getOutputKeyword()
   */
	@Override
  public String getOutputKeyword() {
		setQuestion(false);
		try {
		RecipeLearningDialog cd = (RecipeLearningDialog) DialogManager.giveDialogManager().getCurrentDialog();
		String recipeName = cd.getRecipeName();
		if (getCurrentState().getClass().equals(RecipeLearning.class)) {
			switch((RecipeLearning)getCurrentState()) {
			case RL_ASK_COUNTRY_OF_ORIGIN:
				break;
			case RL_ASK_FIRST_INGREDIENT:
				return "<"+ recipeName + ">";
			case RL_ASK_FIRST_STEP:
				return "<"+ recipeName + ">";
			case RL_ASK_FIRST_TOOL:
				return "<"+ recipeName + ">";
			case RL_ASK_INGREDIENT_RIGHT:
				int indexIngred = cd.getIngredientsList().size() - 1;
				String lastIngred = cd.getIngredientsList().get(indexIngred).getIngredientData().getIngredientName();
				return "<" + lastIngred + ">,{" + recipeName + "}";
			case RL_ASK_LAST_STEP:
				return "<"+ recipeName + ">";
			case RL_ASK_NEXT_INGREDIENT:
				break;
			case RL_ASK_NEXT_STEP:
				break;
			case RL_ASK_NEXT_TOOL:
				break;
			case RL_ASK_RECIPE_NAME:
				break;
			case RL_ASK_STEP_RIGHT:
				break;
			case RL_ASK_TOOL_RIGHT:
				int indexTool = cd.getToolsList().size() - 1;
				String lastTool = cd.getToolsList().get(indexTool).getToolData().getToolName();
				return "<"+ lastTool + ">";
			case RL_ENTRY:
				break;
			case RL_EXIT:
				break;
			default:
				break;
			
			}
		}
		}
		catch (ClassCastException e) {
			
		}
		return "";
  }


}