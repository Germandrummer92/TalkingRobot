package dm;

import java.util.List;

/**
 * This class represents a new recipe assistance dialog.
 * @author Aleksandar Andonov
 * @version 1.0
 */
public class RecipeAssistanceDialog extends KitchenDialog {

	private Recipe currRecipe;

	
	/**
	 * @param session
	 * @param dialogState
	 */
	public RecipeAssistanceDialog(Session session, DialogState dialogState) {
		super(session, dialogState);
		// TODO Auto-generated constructor stub
	}

  public Recipe getCurrRecipe() {
  return currRecipe;
  }

@Override

public void updateState(List<Keyword> keywords, List<String> terms,
		List<String> approval) throws WrongStateClassException {
	// TODO Auto-generated method stub
	RecipeAssistanceState currState;
	if (getCurrentDialogState().getClass() != RecipeAssistanceState.class) {
		throw new WrongStateClassException();
	}
	
	switch ((RecipeAssistance)getCurrentDialogState().getCurrentState()) {
	//case ENTRY,
	case RA_RECIPE_NOT_FOUND:
		updateStateRNF(keywords, terms);
		break;
	case RA_TELL_INGREDIENTS:
		updateStateTellIngredients(keywords, terms);
		break;
	case RA_TELL_TOOLS:
		updateStateTellTools(keywords, terms);
		break;
	case RA_TELL_STEPS:
		updateStateTellSteps(keywords, terms);
		break;
	case RA_TELL_COUNTRY_OF_ORIGIN:
		updateStateTellOrigin(keywords, terms);
		break;
	case RA_TELL_CREATOR:
		updateStateTellCreator(keywords, terms);
		break;
	case RA_TELL_NUM_OF_STEPS:
		updateStateTellNumOfSteps(keywords, terms);
		break;
	case RA_TELL_INGREDIENT_NOT_FOUND:
		updateStateTellIngredientNotFound(keywords, terms);
		break;
	case RA_TELL_TOOL_FOUND:
		updateStateTellToolFound(keywords, terms);
		break;
	case RA_TELL_TOOL_NOT_FOUND:
		updateStateTellToolNotFound(keywords, terms);
		break;
	case RA_TELL_WHOLE_RECIPE:
		updateStateTellWholeRecipe(keywords, terms);
		break;
	case RA_EXIT:
		updateStateExit(keywords, terms);
		break;
	default:	
	}
	
}

private void updateStateExit(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateTellWholeRecipe(List<Keyword> keywords,
		List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateTellToolNotFound(List<Keyword> keywords,
		List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateTellToolFound(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateTellIngredientNotFound(List<Keyword> keywords,
		List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateTellNumOfSteps(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateTellCreator(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateTellOrigin(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateTellSteps(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateTellTools(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateTellIngredients(List<Keyword> keywords,
		List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateRNF(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

}