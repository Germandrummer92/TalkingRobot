package dm;
import java.util.List;

import data.UserData;

public class RecipeLearningDialog extends KitchenDialog {

private UserData creator;

  private String countryOfOrigin;

  private List<Ingredient> ingredientsList;

  private List<Tool> toolsList;

  private String recipeName;

  private RecipeStep[] recipeSteps;

  private Integer numOfSteps;

  private void createRecipe() {
  }

  /**
 	 * @param session
 	 * @param dialogState
 	 */
 	public RecipeLearningDialog(Session session, DialogState dialogState) {
 		super(session, dialogState);
 		// TODO Auto-generated constructor stub
 	}
 	
@Override
<<<<<<< HEAD
public void updateState(List<Keyword> keywords, List<String> terms) {
=======
public void updateState(List<String> keywords, List<String> terms,
		List<String> approval) throws WrongStateClassException {
	RecipeLearningState currState;
	if (getCurrentDialogState().getClass() != RecipeLearningState.class) {
		throw new WrongStateClassException();
	}
	else {
		currState = ((RecipeLearningState)getCurrentDialogState());
	}
	switch (currState.getCurrentState()) {
	case ENTRY:
		updateStateEntry(keywords, terms);
	case ASK_RECIPE_NAME:
		updateStateRecipeName(keywords, terms);
		break;
	case ASK_FIRST_INGREDIENT:
		updateStateFirstIngred(keywords, terms);
		break;
	case ASK_NEXT_INGREDIENT:
		updateStateNextIngred(keywords, terms);
		break;
	case ASK_INGREDIENT_RIGHT:
		updateStateIngredRight(keywords, terms);
		break;
	case ASK_COUNTRY_OF_ORIGIN:
		updateStateAskOrigin(keywords, terms);
		break;
	case ASK_FIRST_TOOL:
		updateStateFirstTool(keywords, terms);
		break;
	case ASK_NEXT_TOOL:
		updateStateNextTool(keywords, terms);
		break;
	case ASK_TOOL_RIGHT:
		updateStateToolRight(keywords, terms);
		break;
	case ASK_FIRST_STEP:
		updateStateFirstStep(keywords, terms);
		break;
	case ASK_NEXT_STEP:
		updateStateNextStep(keywords, terms);
		break;
	case ASK_LAST_STEP:
		updateStateLastStep(keywords, terms);
		break;
	case ASK_STEP_RIGHT:
		updateStateStepRight(keywords, terms);
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

private void updateStateStepRight(List<String> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateLastStep(List<String> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateNextStep(List<String> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateFirstStep(List<String> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateToolRight(List<String> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateNextTool(List<String> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateFirstTool(List<String> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateAskOrigin(List<String> keywords, List<String> terms) {
	
	
}

private void updateStateIngredRight(List<String> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateNextIngred(List<String> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateFirstIngred(List<String> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateRecipeName(List<String> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateEntry(List<String> keywords, List<String> terms) {
>>>>>>> 72949fa36c389ac1d4c9ebf712cdfc771014857a
	// TODO Auto-generated method stub
	
}

}