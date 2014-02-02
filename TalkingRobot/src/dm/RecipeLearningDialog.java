package dm;
import java.util.ArrayList;
import java.util.List;

import data.Data;
import data.IngredientData;
import data.KeywordData;
import data.KeywordType;
import data.RecipeData;
import data.RecipeStepData;
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
  
  private DialogModus dialogModus;

  private void createRecipe() {
  }

  /**
 	 * @param session
 	 * @param dialogState
 	 */
 	public RecipeLearningDialog(Session session, DialogState dialogState) {
 		super(session, dialogState);
 		this.dialogModus = DialogModus.RECIPE_LEARNING;
 		numOfSteps = 0;
 		// TODO Auto-generated constructor stub
 	}
 	
@Override
public void updateState(List<Keyword> keywords, List<String> terms,
		List<String> approval) throws WrongStateClassException {
	RecipeLearningState currState;
	if (getCurrentDialogState().getClass() != RecipeLearningState.class) {
		throw new WrongStateClassException(getCurrentDialogState().getCurrentState().getClass().getName());
	}
	else {
		currState = ((RecipeLearningState)getCurrentDialogState());
	}
	if (!updateStateKeywordJump(keywords)) { 
	
	switch ((RecipeLearning)getCurrentDialogState().getCurrentState()) {
	case RL_ENTRY:
		updateStateEntry(keywords, terms); //done
	case RL_ASK_RECIPE_NAME:
		updateStateRecipeName(keywords, terms); //partially done, questions open
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
		updateStateAskOrigin(keywords, terms); //done
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
		updateStateStep(keywords, terms); //done
		break;
	case RL_ASK_NEXT_STEP:
		updateStateStep(keywords, terms); //done
		break;
	case RL_ASK_LAST_STEP:
		updateStateLastStep(keywords, terms); //done
		break;
	case RL_ASK_STEP_RIGHT:
		updateStateStepRight(keywords, terms); //done
		break;
	case RL_EXIT:
		updateStateExit(keywords, terms); //done
		break;
	default:	
	}
	}
	
}

/**
 * Updates the State according to the keywords passed. Jumps to Reference with highest Priority.
 * @param keywords
 * @return if the jump was completed
 */
private boolean updateStateKeywordJump(List<Keyword> keywords) {
	if (keywords.isEmpty()) {
		return false;
	}
	//Check if all keywords pointing to same state
	else {
		boolean sameRef = true;
		Enum<?> ref = keywords.get(0).getReference().get(0).getCurrentState();
		for (Keyword kw : keywords) {
			for (DialogState d : kw.getReference()) {
			if (!ref.equals(d)) {
				sameRef = false;
			}
			}
		}
		if (sameRef == true) {
			getCurrentDialogState().setCurrentState(ref);
			return true;
		}
		//If not go to keyword with highest priority
		else {
			int priorityMax = keywords.get(0).getKeywordData().getPriority();
			Keyword curKW = keywords.get(0);
			DialogState curRef = keywords.get(0).getReference().get(0);
			for (Keyword kw : keywords) {
				for (DialogState d : kw.getReference()) {
				if (kw.getKeywordData().getPriority() > priorityMax) {
					curKW = kw;
					priorityMax = curKW.getKeywordData().getPriority();
					curRef = d;
				}
				if (d.getCurrentState().getClass().getName().equals("dm.RecipeLearning")) {
					if (kw.getKeywordData().getPriority() + 3 > priorityMax) {
						curKW = kw;
						priorityMax = curKW.getKeywordData().getPriority();
						curRef = d;
					}
					}
				}
				}
			getCurrentDialogState().setCurrentState(curRef.getCurrentState());
			return true;
			}
		}
}

private void updateStateExit(List<Keyword> keywords, List<String> terms) {
	
	//not reachable due to keyword jump jump
}


private void updateStateStepRight(List<Keyword> keywords, List<String> terms) {
	//not needed for now
	
}

private void updateStateLastStep(List<Keyword> keywords, List<String> terms) {
	
	DialogState nextState;
	//ganzes Step in terms, was wenn ein keyword in step gefunden wurde -> zerlegt
	if (terms.isEmpty()) {
		DialogManager.giveDialogManager().setInErrorState(true); //no recipe step found
	}
	else if (terms.size() == 1) {
		recipeSteps[numOfSteps] = new RecipeStep(terms.get(0));
		numOfSteps++;
		nextState = new DialogState();
		nextState.setCurrentState(RecipeLearning.RL_EXIT);
		setCurrentDialogState(nextState);
		createRecipe();
	}
	else {
		//TODO maybe take the string with biggeest length and ask user to confirm?
		DialogManager.giveDialogManager().setInErrorState(true);
	}
	
	
}

private void updateStateStep(List<Keyword> keywords, List<String> terms) {
	
	
	DialogState nextState;
	//ganzes Step in terms, was wenn ein keyword in step gefunden wurde -> zerlegt
	
	if (userSaidEnd(keywords)) {
		nextState = new RecipeLearningState(RecipeLearning.RL_EXIT);
		setCurrentDialogState(nextState);
		createRecipe();
	}
	
	if (terms.isEmpty()) {
		DialogManager.giveDialogManager().setInErrorState(true); //no recipe step found
	}
	else if (terms.size() == 1) {
		recipeSteps[numOfSteps] = new RecipeStep(terms.get(0));
		numOfSteps++;
		nextState = new DialogState();
		if (numOfSteps == 19) {
			nextState.setCurrentState(RecipeLearning.RL_ASK_LAST_STEP);
			setCurrentDialogState(nextState);
		}
		else {
			nextState.setCurrentState(RecipeLearning.RL_ASK_NEXT_STEP);
			setCurrentDialogState(nextState);
		}
		
	}
	else {
		//TODO maybe take the string with biggeest length and ask user to confirm?
		DialogManager.giveDialogManager().setInErrorState(true);
	}
	
}

private void updateStateToolRight(List<Keyword> keywords, List<String> terms, List<String> approval) {
	
	if (approval.isEmpty()) {
		DialogManager.giveDialogManager().setInErrorState(true);
	}
	else if (approval.get(0).equals("yes")) {
		DialogState nextState = new DialogState();
		nextState.setCurrentState(RecipeLearning.RL_ASK_NEXT_TOOL);
		setCurrentDialogState(nextState);
		ToolData newTool = toolsList.get(toolsList.size() - 1).getToolData();
		addWord(newTool.getToolName(), 0, new RecipeAssistanceState(RecipeAssistance.RA_TELL_TOOL_FOUND), newTool, KeywordType.TOOL); //0 because it can show to recipe assistance or kitchen assistance
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
	List<Keyword> tools = keywordsFromType(KeywordType.TOOL, keywords);
	
	if (userSaidEnd(keywords)) {
		nextState = new RecipeLearningState(RecipeLearning.RL_ASK_FIRST_STEP);
		setCurrentDialogState(nextState);
	}
	
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
		saveTool(tools);
		nextState = new DialogState();
		nextState.setCurrentState(RecipeLearning.RL_ASK_NEXT_TOOL);
		setCurrentDialogState(nextState);
		
	}
	else if (tools.size() == 2) {
		//choice stratregy ???
	}
	else {
		//or save all and ask or error handling
		saveTool(tools);
		nextState = new DialogState();
		nextState.setCurrentState(RecipeLearning.RL_ASK_TOOL_RIGHT);
		setCurrentDialogState(nextState);
	}
	
}

private void saveTool(List<Keyword> tools) {
	// TODO Auto-generated method stub
	KeywordData foundToolKey = tools.get(0).getKeywordData();
	ToolData foundTool;
	if (foundToolKey.getDataReference().size() >= 1) {
		foundTool = (ToolData) foundToolKey.getDataReference().get(0);
		foundTool.getRecipes().add(recipe.getRecipeData());
		foundTool.writeFile(); //overwrite old file
		ArrayList<Data> dataRefs = new ArrayList<Data>();
		dataRefs.add(foundTool);
		foundToolKey.setDataReference(dataRefs);
		foundToolKey.writeFile(); //overwrite old file
	}
	else {
		ArrayList<RecipeData> associatedRecipes = new ArrayList<RecipeData>();
		associatedRecipes.add(recipe.getRecipeData());
		foundTool = new ToolData(foundToolKey.getWord(), "", associatedRecipes);
		addWord(foundTool.getToolName(), 0, new RecipeAssistanceState(RecipeAssistance.RA_TELL_TOOL_FOUND), foundTool, KeywordType.TOOL);
	}
	toolsList.add(new Tool(foundTool));
	
	
}

private void updateStateAskOrigin(List<Keyword> keywords, List<String> terms) {
	DialogState nextState;
	List<Keyword> countries = keywordsFromType(KeywordType.COUNTRY, keywords);
	if (countries.size() == 0) {
		if (terms.size() ==  1) {
			ArrayList<DialogState> states = new ArrayList<DialogState>();
			states.add(new RecipeAssistanceState(RecipeAssistance.RA_TELL_COUNTRY_OF_ORIGIN));
			ArrayList<Data> refs = new ArrayList<Data>();
			refs.add(recipe.getRecipeData());
			KeywordData newCountry = new KeywordData(terms.get(0), states, 5, refs, KeywordType.COUNTRY);
			newCountry.writeFile();
			DialogManager.giveDialogManager().getDictionary().addKeyword(terms.get(0), 5, states, refs, KeywordType.COUNTRY);
			countryOfOrigin = terms.get(0);
			keywords.add(new Keyword(newCountry));
		}
		else {
			DialogManager.giveDialogManager().setInErrorState(true);
		}
	}
	else if (countries.size() == 1) {
		countryOfOrigin = countries.get(0).getWord();
		countries.get(0).getKeywordData().getDataReference().add(recipe.getRecipeData());
		countries.get(0).getKeywordData().writeFile();
	}
	else {
		countryOfOrigin = countries.get(0).getWord();
		countries.get(0).getKeywordData().getDataReference().add(recipe.getRecipeData());
		countries.get(0).getKeywordData().writeFile();
	}
	nextState = new RecipeLearningState(RecipeLearning.RL_ASK_FIRST_INGREDIENT);
	setCurrentDialogState(nextState);
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
		addWord(ingred.getIngredientName(), 5, new RecipeAssistanceState(RecipeAssistance.RA_TELL_INGREDIENT_FOUND), ingred, KeywordType.INGREDIENT);
	}
	else if (approval.get(0).equals("no")) {
		ingredientsList.remove(ingredientsList.size() - 1);
		//TODO if terms == 1, if keyword has ingreds ...
		DialogState nextState = new DialogState();
		nextState.setCurrentState(RecipeLearning.RL_ASK_NEXT_INGREDIENT);
		setCurrentDialogState(nextState);
	}
	
}

/**
 * Read out the ingredient given by the user for this recipe. If the ingredient
 * can't be read out go to error handling. If system is not sure weather an ingredient
 * is understood correctly, ask the user to approve the data (e.g. new ingredient,
 * many ingredients said).
 * @param keywords
 * @param terms
 */
private void updateStateIngred(List<Keyword> keywords, List<String> terms) {

	DialogState nextState;
	List<Keyword> ingredients = keywordsFromType(KeywordType.INGREDIENT, keywords);
	
	if (userSaidEnd(keywords)) {
		nextState = new RecipeLearningState(RecipeLearning.RL_ASK_FIRST_TOOL);
		setCurrentDialogState(nextState);
	}
	
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
		saveIngred(ingredients);
		nextState = new DialogState();
		nextState.setCurrentState(RecipeLearning.RL_ASK_NEXT_INGREDIENT);
		setCurrentDialogState(nextState);
		
	}
	else if (ingredients.size() == 2) {
		//choice stratregy ???
	}
	else {
		//or save all and ask or error handling
		saveIngred(ingredients);
		nextState = new DialogState();
		nextState.setCurrentState(RecipeLearning.RL_ASK_INGREDIENT_RIGHT);
		setCurrentDialogState(nextState);
	}
}

private void saveIngred(List<Keyword> ingredients) {
	KeywordData foundIngredKey = ingredients.get(0).getKeywordData();
	IngredientData foundIngred;
	if (foundIngredKey.getDataReference().size() >= 1) {
		foundIngred = (IngredientData) foundIngredKey.getDataReference().get(0);
		ArrayList<Data> dataRefs = new ArrayList<Data>();
		dataRefs.add(foundIngred);
		foundIngredKey.setDataReference(dataRefs);
		foundIngredKey.writeFile(); //overwrite old file
	}
	else {
		foundIngred = new IngredientData(foundIngredKey.getWord(), "");
	}
	ingredientsList.add(new Ingredient(foundIngred));
	
}

private void addWord(String name, int priority,
		DialogState state, Data dataRef, KeywordType type) {
	dataRef.writeFile(); //save ingredient/keyword
	ArrayList<Data> dataRefs = new ArrayList<Data>();
	dataRefs.add(dataRef);
	ArrayList<DialogState> states = new ArrayList<DialogState>();
	states.add(state);
	DialogManager.giveDialogManager().getDictionary().addKeyword(name, priority, states, dataRefs, type);
	
}

private void updateStateRecipeName(List<Keyword> keywords, List<String> terms) {
	DialogState nextState;
	//can a recipe name be in keywords ???
	if (terms.isEmpty()) {
		DialogManager.giveDialogManager().setInErrorState(true); //no recipe name found
	}
	else if (terms.size() == 1) {
		recipeName = terms.get(0);
		nextState = new DialogState();
		nextState.setCurrentState(RecipeLearning.RL_ASK_FIRST_INGREDIENT);
		setCurrentDialogState(nextState);
	}
	else {
		//TODO how to extract it from more than one word, keywords vergleich?
		DialogManager.giveDialogManager().setInErrorState(true);
	}
}

private void updateStateEntry(List<Keyword> keywords, List<String> terms) {
	// don't analyze anything, just say hello and ask about the recipe name
	
}

private boolean userSaidEnd(List<Keyword> keywords) {
	for (Keyword keyword : keywords) {
		for (DialogState state : keyword.getReference()) {
			Enum<?> innerState = state.getCurrentState();
			Enum<?> cs = getCurrentDialogState().getCurrentState(); //current state
			boolean iState = cs.equals(RecipeLearning.RL_ASK_NEXT_INGREDIENT) || cs.equals(RecipeLearning.RL_ASK_FIRST_INGREDIENT);
			boolean tState = cs.equals(RecipeLearning.RL_ASK_FIRST_TOOL) || cs.equals(RecipeLearning.RL_ASK_NEXT_TOOL);
			boolean sState = cs.equals(RecipeLearning.RL_ASK_FIRST_STEP) || cs.equals(RecipeLearning.RL_ASK_NEXT_STEP) || cs.equals(RecipeLearning.RL_ASK_LAST_STEP);
			if (innerState.equals(RecipeLearning.RL_ASK_FIRST_TOOL) && iState) {
				return true;
			}
			else if (innerState.equals(RecipeLearning.RL_ASK_FIRST_STEP) && tState) {
				return true;
			}
			else if (innerState.equals(RecipeLearning.RL_EXIT) && sState) {
				return true;
			}
		}
	}
	return false;
}

private List<Keyword> keywordsFromType(KeywordType type, List<Keyword> keywords) {
	List<Keyword> res = new ArrayList<Keyword>();
	if (keywords == null) {
		return res;
	}
	for (Keyword keyword : keywords) {
		if (keyword.getKeywordData().getType().equals(type)) {
			res.add(keyword);
		}
	}
	return res;
}

//Just to see all keyword which we have
public static void main(String[] args) {
	List<KeywordData> keywords = KeywordData.loadData();
	for (KeywordData key : keywords) {
		System.out.println(key.generateJSON());
	}
}
}