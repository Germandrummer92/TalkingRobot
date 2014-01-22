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
<<<<<<< HEAD
public void updateState(List<Keyword> keywords, List<String> terms) {
=======
public void updateState(List<String> keywords, List<String> terms,
		List<String> approval) throws WrongStateClassException {
	// TODO Auto-generated method stub
	RecipeAssistanceState currState;
	if (getCurrentDialogState().getClass() != RecipeAssistanceState.class) {
		throw new WrongStateClassException();
	}
	else {
		currState = ((RecipeAssistanceState)getCurrentDialogState());
	}
	switch (currState.getCurrentState()) {
	//case ENTRY,
	case RECIPE_NOT_FOUND:
		updateStateRNF(keywords, terms);
		break;
	case TELL_INGREDIENTS:
		updateStateTellIngredients(keywords, terms);
		break;
	case TELL_TOOLS:
		updateStateTellTools(keywords, terms);
		break;
	case TELL_STEPS:
		updateStateTellSteps(keywords, terms);
		break;
	case TELL_COUNTRY_OF_ORIGIN:
		updateStateTellOrigin(keywords, terms);
		break;
	case TELL_CREATOR:
		updateStateTellCreator(keywords, terms);
		break;
	case TELL_NUM_OF_STEPS:
		updateStateTellNumOfSteps(keywords, terms);
		break;
	case TELL_INGREDIENT_NOT_FOUND:
		updateStateTellIngredientNotFound(keywords, terms);
		break;
	case TELL_TOOL_FOUND:
		updateStateTellToolFound(keywords, terms);
		break;
	case TELL_TOOL_NOT_FOUND:
		updateStateTellToolNotFound(keywords, terms);
		break;
	case TELL_WHOLE_RECIPE:
		updateStateTellWholeRecipe(keywords, terms);
		break;
	case EXIT:
		updateStateExit(keywords, terms);
		break;
	default:	
	}
	
}

private void updateStateExit(List<String> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateTellWholeRecipe(List<String> keywords,
		List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateTellToolNotFound(List<String> keywords,
		List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateTellToolFound(List<String> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateTellIngredientNotFound(List<String> keywords,
		List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateTellNumOfSteps(List<String> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateTellCreator(List<String> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateTellOrigin(List<String> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateTellSteps(List<String> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateTellTools(List<String> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateTellIngredients(List<String> keywords,
		List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateRNF(List<String> keywords, List<String> terms) {
>>>>>>> 72949fa36c389ac1d4c9ebf712cdfc771014857a
	// TODO Auto-generated method stub
	
}

}