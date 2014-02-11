package dm;
import java.util.ArrayList;
import java.util.List;

import data.Data;
import data.IngredientData;
import data.KeywordData;
import data.KeywordType;
import data.MealCategoryData;
import data.RecipeData;
import data.RecipeStepData;
import data.ToolData;
import data.UserData;

public class RecipeLearningDialog extends KitchenDialog {

private User creator;

  private String countryOfOrigin;

  private ArrayList<Ingredient> ingredientsList;

  private ArrayList<Tool> toolsList;

  private String recipeName;

  private RecipeStep[] recipeSteps;

  private Integer numOfSteps;
  
  private Recipe recipe;

  /**
 	 * @param session
 	 * @param dialogState
 	 */
 	public RecipeLearningDialog(Session session, DialogState dialogState) {
 		super(session, dialogState);
 		this.dialogModus = DialogModus.RECIPE_LEARNING;
 		numOfSteps = 0;
 		creator = this.getCurrentSession().getCurrentUser();
 		ingredientsList = new ArrayList<Ingredient>();
 		toolsList = new ArrayList<Tool>();
 		recipeSteps = new RecipeStep[20];
 		RecipeData rd = new RecipeData("", null, null, null, null, "", null);
 		recipe = new Recipe(rd);
 		// TODO Auto-generated constructor stub
 	}
 	
@Override
public void updateState(List<Keyword> keywords, List<String> terms,
		List<String> approval) throws WrongStateClassException {
	RecipeLearningState currState;
	if (getCurrentDialogState().getClass() != RecipeLearningState.class || getCurrentDialogState().getCurrentState().getClass() != RecipeLearning.class) {
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
 * @throws WrongStateClassException 
 */
private boolean updateStateKeywordJump(List<Keyword> keywords) throws WrongStateClassException {
	DialogState currState = this.getCurrentDialogState();
	if (!currState.getCurrentState().getClass().equals(RecipeLearning.class)) {
		throw new WrongStateClassException(getCurrentDialogState().getCurrentState().getClass().getName());
	}
	RecipeLearning innerState = (RecipeLearning) currState.getCurrentState(); 
	boolean iState = innerState.equals(RecipeLearning.RL_ASK_NEXT_INGREDIENT) || innerState.equals(RecipeLearning.RL_ASK_FIRST_INGREDIENT);
	boolean tState = innerState.equals(RecipeLearning.RL_ASK_FIRST_TOOL) || innerState.equals(RecipeLearning.RL_ASK_NEXT_TOOL);
	boolean sState = innerState.equals(RecipeLearning.RL_ASK_FIRST_STEP) || innerState.equals(RecipeLearning.RL_ASK_NEXT_STEP) || innerState.equals(RecipeLearning.RL_ASK_LAST_STEP);
	//if exit state is reached stay here so that exit routine is executed
	if (innerState.equals(RecipeLearning.RL_EXIT)) {
		return false;
	}
	
	if (keywords.isEmpty()) {
		return false;
	}
	
	if(innerState.equals(RecipeLearning.RL_ASK_RECIPE_NAME)) {
		for (Keyword keyword: keywords) {
			if (!keyword.getKeywordData().getType().equals(KeywordType.DEFAULT)) {
				return false;
			}
		}
	}
	//if in country and country type then don't jump (ask ingredient)
	if (innerState.equals(RecipeLearning.RL_ASK_COUNTRY_OF_ORIGIN)) {
		for (Keyword keyword: keywords) {
			if (keyword.getKeywordData().getType().equals(KeywordType.COUNTRY)) {
				return false;
			}
		}
	}
	//if in ingred and ingred type then don't jump (ask tool)
	if (iState) {
		for (Keyword keyword: keywords) {
			if (keyword.getKeywordData().getType().equals(KeywordType.INGREDIENT)) {
				return false;
			}
		}
	}
	//if in tool and tool type then don't jump
	if (tState) {
		for (Keyword keyword: keywords) {
			if (keyword.getKeywordData().getType().equals(KeywordType.TOOL)) {
				return false;
			}
		}
	}
	//if in step, don't change
	if (sState) {
		return false;
	}
	//Check if all keywords pointing to same state
	
	//if there is one reference to this state in any keyword stay here
	boolean allHaveRef = true;
	boolean hasOneRef = false;
	for (Keyword kw : keywords) {
		for (DialogState ds : kw.getKeywordData().getDialogState()) {
			if (ds.getCurrentState().equals(innerState)) {
				hasOneRef = true;
			}
		}
		allHaveRef = allHaveRef && hasOneRef;
	}
	if (allHaveRef) {
		return true;
	}
	//go to the keyword with highest priority
	else {
		int maxPrio = -1;
		ArrayList<DialogState> maxPrioState = null;
		for (Keyword kw : keywords) {
			if (kw.getKeywordData().getPriority() > maxPrio && !kw.getKeywordData().getDialogState().isEmpty()) {
				maxPrio = kw.getKeywordData().getPriority();
				maxPrioState = kw.getKeywordData().getDialogState();
			}
		}
		if (maxPrioState != null) {
			//if it points to this mode stay here
			for (DialogState ds : maxPrioState) {
				if (ds.getCurrentState().getClass().getName().equals("dm.RecipeLearning")) { 
					//setCurrentDialogState(ds);
					return false;
				}
			}
			//take by default the first one, problem -> json can't read out the class because attribut is of type dialogState ...
			setDialogStateFromKeywordState(maxPrioState.get(0));
		//	setCurrentDialogState(maxPrioState.get(0)); 
		}
		throw new WrongStateClassException(getCurrentDialogState().getCurrentState().getClass().getName()); //go to another state
		}
}

private void updateStateExit(List<Keyword> keywords, List<String> terms) throws WrongStateClassException {
	StartState nextState;
	nextState = new StartState();
	nextState.setCurrentState(Start.S_USER_FOUND);
	setCurrentDialogState(nextState);
	for (Recipe recipe : this.getRecipeDatabase()) {
		if (recipe.getRecipeData().getRecipeName().equals(recipeName)) {
			throw new WrongStateClassException(nextState.getCurrentState().getClass().getName()); //go back to start dialog
		}
	}
	createRecipe();
	throw new WrongStateClassException(nextState.getCurrentState().getClass().getName()); //go back to start dialog
}


private void updateStateStepRight(List<Keyword> keywords, List<String> terms) {
	//not needed for now
	
}

private void updateStateLastStep(List<Keyword> keywords, List<String> terms) {
	
	RecipeLearningState nextState;
	if (terms.size() > 0) {
		recipeSteps[numOfSteps] = new RecipeStep(terms.get(terms.size() - 1));
		numOfSteps++;
		nextState = new RecipeLearningState();
		nextState.setCurrentState(RecipeLearning.RL_EXIT);
		setCurrentDialogState(nextState);
		createRecipe();
	}
	//theoretically never happens
	else {
		DialogManager.giveDialogManager().setInErrorState(true);
	}
	
}

private void updateStateStep(List<Keyword> keywords, List<String> terms) {
	RecipeLearningState nextState;
	if (userSaidEnd(keywords)) {
		nextState = new RecipeLearningState(RecipeLearning.RL_EXIT);
		setCurrentDialogState(nextState);
		createRecipe();
		return;
	}
	
	if (terms.size() > 0) {
		recipeSteps[numOfSteps] = new RecipeStep(terms.get(terms.size() - 1));
		numOfSteps++;
		nextState = new RecipeLearningState();
		if (numOfSteps == 19) {
			nextState.setCurrentState(RecipeLearning.RL_ASK_LAST_STEP);
			setCurrentDialogState(nextState);
		}
		else {
			nextState.setCurrentState(RecipeLearning.RL_ASK_NEXT_STEP);
			setCurrentDialogState(nextState);
		}
		
	}
	//theoretically never happens
	else {
		//TODO maybe take the string with biggeest length and ask user to confirm?
		DialogManager.giveDialogManager().setInErrorState(true);
	}
	
}

private void updateStateToolRight(List<Keyword> keywords, List<String> terms, List<String> approval) {
	RecipeLearningState nextState;
	if (approval.isEmpty()) {
		DialogManager.giveDialogManager().setInErrorState(true);
	}
	else if (approval.get(0).equals("yes")) {
		nextState = new RecipeLearningState();
		nextState.setCurrentState(RecipeLearning.RL_ASK_NEXT_TOOL);
		setCurrentDialogState(nextState);
		ToolData newTool = toolsList.get(toolsList.size() - 1).getToolData();
		addWord(newTool.getToolName(), 0, new RecipeAssistanceState(RecipeAssistance.RA_TELL_TOOL_FOUND), newTool, KeywordType.TOOL); //0 because it can show to recipe assistance or kitchen assistance
	}
	else if (approval.get(0).equals("no")) {
		ingredientsList.remove(ingredientsList.size() - 1);
		//TODO if terms == 1, if keyword has ingreds ...
		nextState = new RecipeLearningState();
		nextState.setCurrentState(RecipeLearning.RL_ASK_NEXT_TOOL);
		setCurrentDialogState(nextState);
	}
	
}

private void updateStateTool(List<Keyword> keywords, List<String> terms) {
	// TODO mehr als ein tool durch terms
	RecipeLearningState nextState;
	List<Keyword> tools = keywordsFromType(KeywordType.TOOL, keywords);
	
	if (userSaidEnd(keywords)) {
		nextState = new RecipeLearningState(RecipeLearning.RL_ASK_FIRST_STEP);
		setCurrentDialogState(nextState);
		return;
	}
	
	if (tools.isEmpty()) {
		if (terms.size() > 0) {
			ArrayList<RecipeData> associatedRecipes = new ArrayList<RecipeData>();
			associatedRecipes.add(recipe.getRecipeData());
			ToolData newTool = new ToolData(terms.get(0).toString(), "", associatedRecipes); //location???
			toolsList.add(new Tool(newTool));
			nextState = new RecipeLearningState();
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
		nextState = new RecipeLearningState();
		nextState.setCurrentState(RecipeLearning.RL_ASK_NEXT_TOOL);
		setCurrentDialogState(nextState);
		
	}
	else {
		//or save all and ask or error handling
		saveTool(tools);
		nextState = new RecipeLearningState();
		nextState.setCurrentState(RecipeLearning.RL_ASK_NEXT_TOOL);
		setCurrentDialogState(nextState);
	}
	
}

//only used if tools found as keywords
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
	RecipeLearningState nextState;
	List<Keyword> countries = keywordsFromType(KeywordType.COUNTRY, keywords);
	if (countries.size() == 0) {
		if (terms.size() >  0) {
			ArrayList<DialogState> states = new ArrayList<DialogState>();
			states.add(new RecipeAssistanceState(RecipeAssistance.RA_TELL_COUNTRY_OF_ORIGIN));
			states.add(new RecipeLearningState(RecipeLearning.RL_ASK_FIRST_INGREDIENT));
			ArrayList<Data> refs = new ArrayList<Data>();
			refs.add(recipe.getRecipeData());
			KeywordData newCountry = new KeywordData(terms.get(terms.size() - 1), states, 5, refs, KeywordType.COUNTRY);
			newCountry.writeFile();
			DialogManager.giveDialogManager().getDictionary().addKeyword(terms.get(terms.size() - 1), 5, states, refs, KeywordType.COUNTRY);
			countryOfOrigin = terms.get(terms.size() - 1);
			keywords.add(new Keyword(newCountry));
		}
		//theoretically never happens
		else {
			DialogManager.giveDialogManager().setInErrorState(true);
		}
	}
	else if(countries.size() == 1) {
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
 * Decide whether the ingredient chosen by the state before is correct. If yes save
 * it as a keyword in the future and add it to the database.
 * @param keywords 
 * @param terms
 * @param approval
 */
private void updateStateIngredRight(List<Keyword> keywords, List<String> terms,
		List<String> approval) {
	RecipeLearningState nextState;
	if (approval.isEmpty()) {
		DialogManager.giveDialogManager().setInErrorState(true);
	}
	else if (approval.get(0).equals("yes")) {
		nextState = new RecipeLearningState();
		nextState.setCurrentState(RecipeLearning.RL_ASK_NEXT_INGREDIENT);
		setCurrentDialogState(nextState);
		IngredientData ingred = ingredientsList.get(ingredientsList.size() - 1).getIngredientData();
		addWord(ingred.getIngredientName(), 5, new RecipeAssistanceState(RecipeAssistance.RA_TELL_INGREDIENT_FOUND), ingred, KeywordType.INGREDIENT);
	}
	else if (approval.get(0).equals("no")) {
		ingredientsList.remove(ingredientsList.size() - 1);
		//TODO if terms == 1, if keyword has ingreds ...
		nextState = new RecipeLearningState();
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

	RecipeLearningState nextState;
	List<Keyword> ingredients = keywordsFromType(KeywordType.INGREDIENT, keywords);
	
	if (userSaidEnd(keywords)) {
		nextState = new RecipeLearningState(RecipeLearning.RL_ASK_FIRST_TOOL);
		setCurrentDialogState(nextState);
		return;
	}
	
	if (ingredients.isEmpty()) {
		if (terms.size() > 0) {
			IngredientData newIngred = new IngredientData(terms.get(0).toString(), "");
			ingredientsList.add(new Ingredient(newIngred));
			nextState = new RecipeLearningState();
				nextState.setCurrentState(RecipeLearning.RL_ASK_INGREDIENT_RIGHT);
				setCurrentDialogState(nextState);
		}
		//theretically never happens
		else {
			DialogManager.giveDialogManager().setInErrorState(true);
		}	
	}
	else if (ingredients.size() == 1) {
		saveIngred(ingredients);
		nextState = new RecipeLearningState();
		nextState.setCurrentState(RecipeLearning.RL_ASK_NEXT_INGREDIENT);
		setCurrentDialogState(nextState);
	}
	else {
		//or save all and ask or error handling
		saveIngred(ingredients);
		nextState = new RecipeLearningState();
		nextState.setCurrentState(RecipeLearning.RL_ASK_NEXT_INGREDIENT);
		setCurrentDialogState(nextState);
	}
}

/**
 * Takes the first ingredient from the given list of keywords
 * @param ingredients
 */
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
	RecipeLearningState nextState;
	//TODO recipe already exists
	if (terms.size() > 0) {
		recipeName = terms.get(0);
		nextState = new RecipeLearningState();
		nextState.setCurrentState(RecipeLearning.RL_ASK_COUNTRY_OF_ORIGIN);
		setCurrentDialogState(nextState);
	}
	//theoretically never happens
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

private void createRecipe() {
  	MealCategoryData mealCategory = new MealCategoryData("default"); //not in obligatory criteria
  	ArrayList<RecipeStep> rs = new ArrayList<RecipeStep>();
  	for (int i = 0; i < recipeSteps.length; i++) {
  		if (recipeSteps[i] != null) {
  			rs.add(recipeSteps[i]);
  		}
  	}
  	Recipe recipe = new Recipe(recipeName, ingredientsList, rs, toolsList,
  			creator ,countryOfOrigin, mealCategory);
  	recipe.getRecipeData().writeFile();
  	getRecipeDatabase().add(recipe);
  	
	addWord(recipe.getRecipeData().getRecipeName(), 3, new RecipeAssistanceState(RecipeAssistance.RA_TELL_WHOLE_RECIPE), recipe.getRecipeData(), KeywordType.RECIPE);
}

/**
 * sets the dialog state with the given dialog state. So the state has the
 * appropriate class, because when loading from json the class is just
 * DialogState and every comparison with getClass doesn't work anymore
 * as the concrete class is not known. 
 * @param the dialog state
 */
private void setDialogStateFromKeywordState(DialogState ds) {
	switch(ds.getCurrentState().getClass().getName()) {
		case "dm.RecipeLearning":
			RecipeLearningState rls = new RecipeLearningState();
			rls.setCurrentState(ds.getCurrentState());
			setCurrentDialogState(rls);
			break;
		case "dm.RecipeAssistance":
			RecipeAssistanceState ras = new RecipeAssistanceState();
			ras.setCurrentState(ds.getCurrentState());
			setCurrentDialogState(ras);
			break;
		case "dm.KitchenAssistance":
			KitchenAssistanceState kas = new KitchenAssistanceState();
			kas.setCurrentState(ds.getCurrentState());
			setCurrentDialogState(kas);
			break;
		case "CanteenRecom":
			CanteenRecommendationState crs = new CanteenRecommendationState();
			crs.setCurrentState(ds.getCurrentState());
			setCurrentDialogState(crs);
			break;
		case "CanteenInfo":
			CanteenInformationState cis = new CanteenInformationState();
			cis.setCurrentState(ds.getCurrentState());
			setCurrentDialogState(cis);
			break;
	}
	
}

//Just to see all keyword which we have
public static void main(String[] args) {
	List<KeywordData> keywords = KeywordData.loadData();
	for (KeywordData key : keywords) {
		System.out.println(key.getWordID()+ "      " + key.generateJSON());
	}
}
}