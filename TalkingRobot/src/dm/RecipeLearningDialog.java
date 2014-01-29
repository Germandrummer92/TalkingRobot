package dm;
import java.util.ArrayList;
import java.util.List;

import data.Data;
import data.IngredientData;
import data.RecipeData;
import data.ToolData;
import data.UserData;

public class RecipeLearningDialog extends KitchenDialog {

private UserData creator;

  private String countryOfOrigin;

  private List<Ingredient> ingredientsList;

  private List<Tool> toolsList;

  private String recipeName;

  private RecipeStep[] recipeSteps;

  private Integer numOfSteps;
  
  private Recipe recipe;

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
		updateStateIngred(keywords, terms); //done
		break;
	case RL_ASK_NEXT_INGREDIENT:
		updateStateIngred(keywords, terms); //done
		break;
	case RL_ASK_INGREDIENT_RIGHT:
		updateStateIngredRight(keywords, terms, approval); //done
		break;
	case RL_ASK_COUNTRY_OF_ORIGIN:
		updateStateAskOrigin(keywords, terms);
		break;
	case RL_ASK_FIRST_TOOL:
		updateStateTool(keywords, terms); //done
		break;
	case RL_ASK_NEXT_TOOL:
		updateStateTool(keywords, terms); //done
		break;
	case RL_ASK_TOOL_RIGHT:
		updateStateToolRight(keywords, terms, approval); //done
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

private void updateStateToolRight(List<Keyword> keywords, List<String> terms, List<String> approval) {
	
	if (approval.isEmpty()) {
		//TODO no answer at approval question
		DialogManager.giveDialogManager().setInErrorState(true);
	}
	else if (approval.get(0).equals("yes")) {
		DialogState nextState = new DialogState();
		nextState.setCurrentState(RecipeLearning.RL_ASK_NEXT_TOOL);
		setCurrentDialogState(nextState);
		ToolData newTool = toolsList.get(toolsList.size() - 1).getToolData();
		addWord(newTool.getToolName(), 0, new RecipeAssistanceState(RecipeAssistance.RA_TELL_TOOL_FOUND), newTool); //0 because it can show to recipe assistance or kitchen assistance
	}
	else if (approval.get(0).equals("no")) {
		ingredientsList.remove(ingredientsList.size() - 1);
		//TODO if terms == 1, if keyword has ingreds ...
		DialogState nextState = new DialogState();
		nextState.setCurrentState(RecipeLearning.RL_ASK_NEXT_TOOL);
		setCurrentDialogState(nextState);
	}
	
}

private void updateStateTool(List<Keyword> keywords, List<String> terms) {
	DialogState nextState;
	List<Keyword> tools = keywordsFromType(ToolData.class, keywords);
	if (tools.isEmpty()) {
		if (terms.isEmpty()) {
			DialogManager.giveDialogManager().setInErrorState(true); //no keys or terms
		}
		else if (terms.size() == 1) {
			ArrayList<RecipeData> associatedRecipes = new ArrayList<RecipeData>();
			associatedRecipes.add(recipe.getRecipeData());
			ToolData newTool = new ToolData(terms.get(0).toString(), "", associatedRecipes); //location???
			toolsList.add(new Tool(newTool));
			nextState = new DialogState();
			nextState.setCurrentState(RecipeLearning.RL_ASK_TOOL_RIGHT);
			setCurrentDialogState(nextState);
		}
		else {
			DialogManager.giveDialogManager().setInErrorState(true);
			// too many terms
		}
	}
	else if (tools.size() == 1) {
		ArrayList<RecipeData> associatedRecipes = new ArrayList<RecipeData>();
		associatedRecipes.add(recipe.getRecipeData());
		ToolData newTool = new ToolData(terms.get(0).toString(), "", associatedRecipes); //location???
		toolsList.add(new Tool(newTool));
		nextState = new DialogState();
		nextState.setCurrentState(RecipeLearning.RL_ASK_NEXT_TOOL);
		setCurrentDialogState(nextState);
		addWord(newTool.getToolName(), 0, new RecipeAssistanceState(RecipeAssistance.RA_TELL_TOOL_FOUND), newTool); //0 because it can show to recipe assistance or kitchen assistance
	}
	else if (tools.size() == 2) {
		//choice stratregy ???
	}
	else {
		//or save all and ask or error handling
		ArrayList<RecipeData> associatedRecipes = new ArrayList<RecipeData>();
		associatedRecipes.add(recipe.getRecipeData());
		ToolData newTool = new ToolData(terms.get(0).toString(), "", associatedRecipes); //location???
		toolsList.add(new Tool(newTool));
		nextState = new DialogState();
		nextState.setCurrentState(RecipeLearning.RL_ASK_TOOL_RIGHT);
		setCurrentDialogState(nextState);
	}
	
}

//private void updateStateFirstTool(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
//}

private void updateStateAskOrigin(List<Keyword> keywords, List<String> terms) {
	
	
}
/**
 * Decide weather the ingredient chosen by the state before is correct. If yes save
 * it as a keyword in the future and add it to the database.
 * @param keywords 
 * @param terms
 * @param approval
 */
private void updateStateIngredRight(List<Keyword> keywords, List<String> terms,
		List<String> approval) {
	if (approval.isEmpty()) {
		//TODO no answer at approval question
		DialogManager.giveDialogManager().setInErrorState(true);
	}
	else if (approval.get(0).equals("yes")) {
		DialogState nextState = new DialogState();
		nextState.setCurrentState(RecipeLearning.RL_ASK_NEXT_INGREDIENT);
		setCurrentDialogState(nextState);
		IngredientData ingred = ingredientsList.get(ingredientsList.size() - 1).getIngredientData();
		addWord(ingred.getIngredientName(), 5, new RecipeAssistanceState(RecipeAssistance.RA_TELL_INGREDIENT_FOUND), ingred);
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

/**
 * Read out the ingredient given by the user for this recipe. If the ingredient
 * can't be read out go to error handling. If system is not sure weather an ingredient
 * is understood correctly, ask the user to approve the data (e.g. new ingredient,
 * many ingredients said).
 * @param keywords
 * @param terms
 */
private void updateStateIngred(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	//red tomatos  where terms where keywords???
	DialogState nextState;
	List<Keyword> ingredients = keywordsFromType(IngredientData.class, keywords);
	if (ingredients.isEmpty()) {
		if (terms.isEmpty()) {
			DialogManager.giveDialogManager().setInErrorState(true); //no keys or terms
		}
		else if (terms.size() == 1) {
			IngredientData newIngred = new IngredientData(ingredients.get(0).toString(), "");
			ingredientsList.add(new Ingredient(newIngred));
			nextState = new DialogState();
			nextState.setCurrentState(RecipeLearning.RL_ASK_INGREDIENT_RIGHT);
			setCurrentDialogState(nextState);
		}
		else {
			DialogManager.giveDialogManager().setInErrorState(true);
			// too many terms
		}
	}
	else if (ingredients.size() == 1) {
		IngredientData newIngred = new IngredientData(ingredients.get(0).toString(), "");
		ingredientsList.add(new Ingredient(newIngred));
		nextState = new DialogState();
		nextState.setCurrentState(RecipeLearning.RL_ASK_NEXT_INGREDIENT);
		setCurrentDialogState(nextState);
		addWord(newIngred.getIngredientName(), 5, new RecipeAssistanceState(RecipeAssistance.RA_TELL_INGREDIENT_FOUND), newIngred);
		
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

private void addWord(String name, int priority,
		DialogState state, Data dataRef) {
	dataRef.writeFile(); //save ingredient/keyword
	DialogManager.giveDialogManager().getDictionary().addKeyword(name, priority, state, dataRef);
	
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