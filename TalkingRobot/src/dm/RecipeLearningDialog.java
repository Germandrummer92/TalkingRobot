package dm;
import java.util.ArrayList;
import java.util.List;

import data.IngredientData;
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
		updateStateIngred(keywords, terms);
		break;
	case RL_ASK_NEXT_INGREDIENT:
		updateStateIngred(keywords, terms);
		break;
	case RL_ASK_INGREDIENT_RIGHT:
		updateStateIngredRight(keywords, terms, approval);
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

private void updateStateIngredRight(List<Keyword> keywords, List<String> terms,
		List<String> approval) {
	if (approval.isEmpty()) {
		//TODO
	}
	else if (approval.get(0).equals("yes")) {
		DialogState nextState = new DialogState();
		nextState.setCurrentState(RecipeLearning.RL_ASK_NEXT_INGREDIENT);
		setCurrentDialogState(nextState);
	}
	else if (approval.get(0).equals("no")) {
		ingredientsList.remove(ingredientsList.size() - 1);
		//TODO if terms == 1, if keyword has ingreds ...
		DialogState nextState = new DialogState();
		nextState.setCurrentState(RecipeLearning.RL_ASK_NEXT_INGREDIENT);
		setCurrentDialogState(nextState);
	}
	
}

//private void updateStateNextIngred(List<Keyword> keywords, List<String> terms) {
	
	
//}

private void updateStateIngred(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	//red tomatos  where terms where keywords???
	DialogState nextState;
	List<Keyword> ingredients = keywordsFromType(IngredientData.class, keywords);
	if (ingredients.isEmpty()) {
		if (terms.isEmpty()) {
			//error TODO
		}
		else if (terms.size() == 1) {
			IngredientData newIngred = new IngredientData(ingredients.get(0).toString(), "");
			ingredientsList.add(new Ingredient(newIngred));
			nextState = new DialogState();
			nextState.setCurrentState(RecipeLearning.RL_ASK_INGREDIENT_RIGHT);
			setCurrentDialogState(nextState);
		}
		else {
			// no ingredient found, evt more states? or error handling
		}
	}
	else if (ingredients.size() == 1) {
		IngredientData newIngred = new IngredientData(ingredients.get(0).toString(), "");
		ingredientsList.add(new Ingredient(newIngred));
		nextState = new DialogState();
		nextState.setCurrentState(RecipeLearning.RL_ASK_NEXT_INGREDIENT);
		setCurrentDialogState(nextState);
	}
	else if (ingredients.size() == 2) {
		//choice stratregy ???
	}
	else {
		//or save all and ask or error handling
		IngredientData newIngred = new IngredientData(ingredients.get(0).toString(), "");
		ingredientsList.add(new Ingredient(newIngred));
		nextState = new DialogState();
		nextState.setCurrentState(RecipeLearning.RL_ASK_INGREDIENT_RIGHT);
		setCurrentDialogState(nextState);
	}
}

private void updateStateRecipeName(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateEntry(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private <T> List<Keyword> keywordsFromType(Class<T> type, List<Keyword> keywords) {
	// TODO Auto-generated method stub
	List<Keyword> res = new ArrayList<Keyword>();
	if (keywords == null) {
		return res;
	}
	for (Keyword keyword : keywords) {
		if (keyword.getKeywordData().getDataReference().getClass() == type) {
			res.add(keyword);
		}
	}
	return res;
}

}