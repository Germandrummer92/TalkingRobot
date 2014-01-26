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
public void updateState(List<Keyword> keywords, List<String> terms,
		List<String> approval) throws WrongStateClassException {
	RecipeLearningState currState;
	if (getCurrentDialogState().getClass() != RecipeLearningState.class) {
		throw new WrongStateClassException(getCurrentDialogState().getClass().getName());
	}
	else {
		currState = ((RecipeLearningState)getCurrentDialogState());
	}
	switch ((RecipeLearning)getCurrentDialogState().getCurrentState()) {
	case RL_ENTRY:
		updateStateEntry(keywords, terms);
	case RL_ASK_RECIPE_NAME:
		updateStateRecipeName(keywords, terms);
		break;
	case RL_ASK_FIRST_INGREDIENT:
		updateStateFirstIngred(keywords, terms);
		break;
	case RL_ASK_NEXT_INGREDIENT:
		updateStateNextIngred(keywords, terms);
		break;
	case RL_ASK_INGREDIENT_RIGHT:
		updateStateIngredRight(keywords, terms);
		break;
	case RL_ASK_COUNTRY_OF_ORIGIN:
		updateStateAskOrigin(keywords, terms);
		break;
	case RL_ASK_FIRST_TOOL:
		updateStateFirstTool(keywords, terms);
		break;
	case RL_ASK_NEXT_TOOL:
		updateStateNextTool(keywords, terms);
		break;
	case RL_ASK_TOOL_RIGHT:
		updateStateToolRight(keywords, terms);
		break;
	case RL_ASK_FIRST_STEP:
		updateStateFirstStep(keywords, terms);
		break;
	case RL_ASK_NEXT_STEP:
		updateStateNextStep(keywords, terms);
		break;
	case RL_ASK_LAST_STEP:
		updateStateLastStep(keywords, terms);
		break;
	case RL_ASK_STEP_RIGHT:
		updateStateStepRight(keywords, terms);
		break;
	case RL_EXIT:
		updateStateExit(keywords, terms);
		break;
	default:	
	}
	
}

private void updateStateExit(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateStepRight(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateLastStep(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateNextStep(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateFirstStep(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateToolRight(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateNextTool(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateFirstTool(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateAskOrigin(List<Keyword> keywords, List<String> terms) {
	
	
}

private void updateStateIngredRight(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateNextIngred(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateFirstIngred(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateRecipeName(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateEntry(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

}