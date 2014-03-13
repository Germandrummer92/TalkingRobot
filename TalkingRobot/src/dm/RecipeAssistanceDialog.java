package dm;

import java.util.List;

import data.IngredientData;
import data.KeywordType;
import data.RecipeData;
import data.ToolData;

/**
 * This class represents a new recipe assistance dialog.
 * @author Aleksandar Andonov Daniel Draper
 * @version 2.3
 */
public class RecipeAssistanceDialog extends KitchenDialog {

	private Recipe currRecipe;
	private String recipeName;
	private int curNumIngredient = 0;
	private IngredientData currIngredient;
	private boolean teaching = false;

	
	/**
	 * @param session
	 * @param dialogState
	 */
	public RecipeAssistanceDialog(Session session, DialogState dialogState) {
		super(session, dialogState);
		this.dialogModus = DialogModus.RECIPE_ASSISTANCE;
	}

public Recipe getCurrRecipe() {
  return currRecipe;
  }

/**
 * @return the recipeName
 */
public String getRecipeName() {
	return recipeName;
}

/**
 * @param recipeName the recipeName to set
 */
public void setRecipeName(String recipeName) {
	this.recipeName = recipeName;
}

@Override

public void updateState(List<Keyword> keywords, List<String> terms,
		List<String> approval) throws WrongStateClassException {
	//RecipeAssistanceState currState;
	updateStateKeywordJump(keywords);
	if (getCurrentDialogState().getClass() != RecipeAssistanceState.class || getCurrentDialogState().getCurrentState().getClass() != RecipeAssistance.class) {
		throw new WrongStateClassException(getCurrentDialogState().getCurrentState().getClass().getName());
	}
	
	switch ((RecipeAssistance)getCurrentDialogState().getCurrentState()) {
	case RA_ENTRY: 
		updateStateEntry(keywords, terms);
		break;
	case RA_RECIPE_NOT_FOUND:
		updateStateRNF(keywords, approval, terms);
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
	case RA_TELL_COUNTRY_FOUND:
		updateStateTellCountryFound(keywords, terms);
		break;
	case RA_TELL_COUNTRY_NOT_FOUND:
		updateStateTellCountryNotFound(keywords, terms);
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
	case RA_TELL_INGREDIENT_FOUND:
		updateStateTellIngredientFound(keywords, terms);
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
	case RA_WAITING_FOR_RECIPE_NAME:
		updateStateWaitingName(keywords, terms);
		break;
	case RA_RECIPE_FOUND:
		updateStateFound(keywords, terms);
		break;
	case RA_DELETE_RECIPE:
		updateStateDelete(keywords, terms);
		break;
	case RA_EXIT:
		updateStateExit(keywords, terms);
		break;
	case RA_TELL_ONE_INGREDIENT:
		updateStateOneIngredient(keywords, terms);
		break;
	case RA_TELL_INGREDIENTS_DONE:
		updateStateDone(keywords, terms);
		break;
	default:	
	}
	if (getCurrentDialogState().getClass() != RecipeAssistanceState.class || getCurrentDialogState().getCurrentState().getClass() != RecipeAssistance.class) {
		throw new WrongStateClassException(getCurrentDialogState().getCurrentState().getClass().getName());
	}
	
}




/**
 * Updates the RA_TELL_INGREDIENTS_DONE state
 */
private void updateStateDone(List<Keyword> keywords, List<String> terms) {
	currIngredient = null;
	curNumIngredient = 0;
	
}

/**
 * Updates the RA_TELL_ONE_INGREDIENT state
 * @param keywords
 * @param terms
 */
private void updateStateOneIngredient(List<Keyword> keywords, List<String> terms) {
	if (keywords != null && !keywords.isEmpty()) {
		for (Keyword kw : keywords) {
			for (DialogState ref : kw.getReference()) {
				if (ref.getCurrentState().equals(RecipeAssistance.RA_TELL_ONE_INGREDIENT)) {
					if (currRecipe != null && currRecipe.getRecipeData() != null) {
						if (curNumIngredient < currRecipe.getRecipeData().getIngredients().size()) {
							 currIngredient = currRecipe.getRecipeData().getIngredients().get(curNumIngredient);
							 curNumIngredient++;
						}
						else {
							getCurrentDialogState().setCurrentState(RecipeAssistance.RA_TELL_INGREDIENTS_DONE);
						}
					}
					else {
						getCurrentDialogState().setCurrentState(RecipeAssistance.RA_RECIPE_NOT_FOUND);
					}
					
			}
		}
	}
	}
	else {
		DialogManager.giveDialogManager().setInErrorState(true);
	}
}

/**
* Updates the State according to the keywords passed. Jumps to Reference with highest Priority.
* @param keywords
* @return if the jump was completed
*/
private boolean updateStateKeywordJump(List<Keyword> keywords) {
	if (keywords == null || keywords.isEmpty()) {
		return false;
	}
	//Check if all keywords pointing to same state, in one of their references (takes first state, if more than one state)
	else {
	/*	boolean sameRef = false;
		Enum<?> refMax = null;
		for (DialogState d : keywords.get(0).getReference()) {
			int count = 0;
			for (Keyword kw : keywords) {
				if (kw.getReference().contains(d)) {
					count++;
				}
				if (count == keywords.size()) {
					sameRef = true;
					refMax = d.getCurrentState();
				}
			}

		}
		if (sameRef) {
			getCurrentDialogState().setCurrentState(refMax);
			return true;
		}*/
		//If not go to keyword with highest priority
		//else {
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
				//If the Keyword has a pointer to RA_* we should rate that higher since we're IN RA_
				if (d.getCurrentState().getClass().getName().equals("dm.RecipeAssistance")) {
					if (kw.getKeywordData().getPriority() + 3 > priorityMax) {
						curKW = kw;
						priorityMax = curKW.getKeywordData().getPriority() + 3;
						curRef = d;
					}
				}
				}
				}
			getCurrentDialogState().setCurrentState(curRef.getCurrentState());
			return true;
			}
		//}
}

/**
 * Updates the Recipe Found State according to parsed keywords/terms
 * @param keywords
 * @param terms
 */
private void updateStateFound(List<Keyword> keywords, List<String> terms) {
	//Came here due to keyword jump, so a keyword should be the recipe name
	if (keywords != null && !keywords.isEmpty()) {
		for (Keyword kw : keywords) {
			if (kw.getKeywordData().getType().equals(KeywordType.RECIPE)) {
				//First Reference is the Recipe for recipe keywords
				currRecipe = new Recipe((RecipeData)kw.getKeywordData().getDataReference().get(0));
				recipeName = currRecipe.getRecipeData().getRecipeName();
				return;
			}
		}
	}
	if ((keywords == null || keywords.isEmpty()) && (terms == null || terms.isEmpty()) && currRecipe != null && currRecipe.getRecipeData() != null) {
		getCurrentDialogState().setCurrentState(RecipeAssistance.RA_TELL_INGREDIENTS);
		return;
	}
	DialogManager.giveDialogManager().setInErrorState(true);
	
}

/**
* Updates the Waiting for Recipe name according to parsed keywords/terms
* @param keywords
* @param terms
*/
private void updateStateWaitingName(List<Keyword> keywords, List<String> terms) {
	//We came here since we didn't get a jump, if we got a jump we're in RECIPE_FOUND
	if (keywords == null || keywords.isEmpty()) {
		if (terms != null && !terms.isEmpty()) {
			//Will save first term no matter what user said.
			recipeName = terms.get(0);
			getCurrentDialogState().setCurrentState(RecipeAssistance.RA_RECIPE_NOT_FOUND);
			return;
		}
	}
	//If there was a keyword something's wrong, and if there's no terms somethings wrong as well, unless a keyword to this state was passed
	else {
		if (keywords != null && !keywords.isEmpty()) {
			for (Keyword kw : keywords) {
				for (DialogState d : kw.getReference()) {
					if (d.getCurrentState().equals(RecipeAssistance.RA_WAITING_FOR_RECIPE_NAME)) {
						getCurrentDialogState().setCurrentState(RecipeAssistance.RA_WAITING_FOR_RECIPE_NAME);
						return;
					}
				}
			}
		}
		DialogManager.giveDialogManager().setInErrorState(true);
	}
		

	
}

/**
* Updates the Entry state according to parsed keywords/terms
* @param keywords
* @param terms
*/
private void updateStateEntry(List<Keyword> keywords, List<String> terms) {
	//Either jumped due to RecipeName passed, or no Recipe could be found
	if (keywords == null || keywords.isEmpty()) {
		if (terms != null && !terms.isEmpty()) {
			recipeName = terms.get(0);
			getCurrentDialogState().setCurrentState(RecipeAssistance.RA_RECIPE_NOT_FOUND);
			return;
		}
		//if no terms have been passed something went wrong or just recipe/other weird keyword passed
		else {
			if (keywords != null && !keywords.isEmpty()) {
				for (Keyword kw : keywords) {
					for (DialogState d : kw.getReference()) {
						if (d.getCurrentState().equals(RecipeAssistance.RA_ENTRY)) {
							getCurrentDialogState().setCurrentState(RecipeAssistance.RA_WAITING_FOR_RECIPE_NAME);
							return;
						}
					}
				}
			}
			DialogManager.giveDialogManager().setInErrorState(true);
		}
	}
	
}

/**
* Updates the Exit state according to parsed keywords/terms
* @param keywords
* @param terms
*/
private void updateStateExit(List<Keyword> keywords, List<String> terms) {
	//Should be jumping due to keywords
	DialogManager.giveDialogManager().setInErrorState(true);
	
}

/**
* Updates the Tell whole Recipe state according to parsed keywords/terms
* @param keywords
* @param terms
*/
private void updateStateTellWholeRecipe(List<Keyword> keywords,
		List<String> terms) {
	//If we came here due to a keyword, there should be a recipe keyword, or the recipe has been set
	if (keywords != null && !keywords.isEmpty()) {
		for (Keyword kw : keywords) {
			if (kw.getKeywordData().getType().equals((KeywordType.RECIPE))) {
				currRecipe = new Recipe((RecipeData)kw.getKeywordData().getDataReference().get(0));
				return;
			}
		}
	}
	//If we don't know the recipe, we need to ask for the name
	if (currRecipe == null || currRecipe.getRecipeData() == null) {
		getCurrentDialogState().setCurrentState(RecipeAssistance.RA_WAITING_FOR_RECIPE_NAME);
		return;
	}
	else {
		DialogManager.giveDialogManager().setInErrorState(true);
	}
}

/**
* Updates the Tool not found state according to parsed keywords/terms
* @param keywords
* @param terms
*/
private void updateStateTellToolNotFound(List<Keyword> keywords,
		List<String> terms) {
	//If we came here, keyword for ingredients and unknown ingredient must have been passed
	if (terms == null || terms.isEmpty()) {
		DialogManager.giveDialogManager().setInErrorState(true);
	}	
}

/**
* Updates the country found state according to parsed keywords/terms
* @param keywords
* @param terms
*/
private void updateStateTellCountryFound(List<Keyword> keywords, List<String> terms) {
	//We came here due to a jump, so the Ingredient should have been passed, if not there's an error
		if (keywords != null && !keywords.isEmpty()) {
			for (Keyword kw : keywords) {
				if (kw.getKeywordData().getType().equals(KeywordType.COUNTRY)) {
					for (Recipe r : getRecipeDatabase()) {
						if (r.getRecipeData().getOriginalCountry() != null) {
							if (r.getRecipeData().getOriginalCountry().equalsIgnoreCase(kw.getWord())) {
								currRecipe = r;
								return;
							}
					}
					}
					getCurrentDialogState().setCurrentState(RecipeAssistance.RA_TELL_TOOL_NOT_FOUND);
					return;
					}
				}
			if (currRecipe == null || currRecipe.getRecipeData() == null) {
				DialogManager.giveDialogManager().setInErrorState(true);
			}
			}
		else {
			DialogManager.giveDialogManager().setInErrorState(true);
		}
	
}

/**
* Updates the Tool found state according to parsed keywords/terms
* @param keywords
* @param terms
*/
private void updateStateTellToolFound(List<Keyword> keywords, List<String> terms) {
	//We came here due to a jump, so the Ingredient should have been passed, if not there's an error
		if (keywords != null && !keywords.isEmpty()) {
			for (Keyword kw : keywords) {
				if (kw.getKeywordData().getType().equals(KeywordType.TOOL)) {
					for (Recipe r : getRecipeDatabase()) {
						if (r.getRecipeData().getTools() != null) {
							for (ToolData t : r.getRecipeData().getTools()) {
								if (t.getToolName().equals(kw.getWord())) {
									currRecipe = r;
									return;
								}
							}
					}
					}
					getCurrentDialogState().setCurrentState(RecipeAssistance.RA_TELL_TOOL_NOT_FOUND);
					return;
					}
				}
			if (currRecipe == null || currRecipe.getRecipeData() == null) {
				DialogManager.giveDialogManager().setInErrorState(true);
			}
			}
		else {
			DialogManager.giveDialogManager().setInErrorState(true);
		}
	
}

/**
* Updates the Ingredient found state according to parsed keywords/terms
* @param keywords
* @param terms
*/
private void updateStateTellIngredientFound(List<Keyword> keywords,
		List<String> terms) {
	//We came here due to a jump, so the Ingredient should have been passed, if not there's an error
	if (keywords != null && !keywords.isEmpty()) {
		for (Keyword kw : keywords) {
			if (kw.getKeywordData().getType().equals(KeywordType.INGREDIENT)) {
				for (Recipe r : getRecipeDatabase()) {
					if (r.getRecipeData().getIngredients() != null) {
						for (IngredientData i : r.getRecipeData().getIngredients()) {
							if (i.getIngredientName().equals(kw.getWord())) {
								currRecipe = r;
								return;
							}
						}
				}
				}
				getCurrentDialogState().setCurrentState(RecipeAssistance.RA_TELL_INGREDIENT_NOT_FOUND);
				}
			}
		if (currRecipe == null || currRecipe.getRecipeData() == null) {
			DialogManager.giveDialogManager().setInErrorState(true);
		}
	}
	else {
		DialogManager.giveDialogManager().setInErrorState(true);
	}
	
}

/**
* Updates the country not found state according to parsed keywords/terms
* @param keywords
* @param terms
*/
private void updateStateTellCountryNotFound(List<Keyword> keywords,
		List<String> terms) {
	//If we came here, keyword for ingredients and unknown ingredient must have been passed
	if (terms == null || terms.isEmpty()) {
		DialogManager.giveDialogManager().setInErrorState(true);
	}
	
}

/**
* Updates the Ingredient not found state according to parsed keywords/terms
* @param keywords
* @param terms
*/
private void updateStateTellIngredientNotFound(List<Keyword> keywords,
		List<String> terms) {
	//If we came here, keyword for ingredients and unknown ingredient must have been passed
	if (terms == null || terms.isEmpty()) {
		DialogManager.giveDialogManager().setInErrorState(true);
	}
	
}

/**
* Updates the tell number of steps according to parsed keywords/terms
* @param keywords
* @param terms
*/
private void updateStateTellNumOfSteps(List<Keyword> keywords, List<String> terms) {
	//We should have come here by a keyword jump and the recipe shouldve been passed or set
	if (keywords != null && !keywords.isEmpty()) {
		for (Keyword kw : keywords) {
			if (kw.getKeywordData().getType() == KeywordType.RECIPE) {
				currRecipe = new Recipe((RecipeData)kw.getKeywordData().getDataReference().get(0));
				return;
			}
		}
	}
	//If not, the recipe should've already been set, if not, we need a recipe Name.
	if (currRecipe == null || currRecipe.getRecipeData() == null) {
		getCurrentDialogState().setCurrentState(RecipeAssistance.RA_WAITING_FOR_RECIPE_NAME);
		return;
	}
	if (keywords == null || keywords.isEmpty() && currRecipe != null) {
		DialogManager.giveDialogManager().setInErrorState(true);
	}
}

/**
* Updates the tell creator state according to parsed keywords/terms
* @param keywords
* @param terms
*/
private void updateStateTellCreator(List<Keyword> keywords, List<String> terms) {
	//We should have come here by a keyword jump and the recipe shouldve been passed or set
	if (keywords != null && !keywords.isEmpty()) {
		for (Keyword kw : keywords) {
			if (kw.getKeywordData().getType() == KeywordType.RECIPE) {
				currRecipe = new Recipe((RecipeData)kw.getKeywordData().getDataReference().get(0));
				return;
			}
		}
	}
	//If not, the recipe should've already been set, if not, we need a recipe Name.
	if (currRecipe == null || currRecipe.getRecipeData() == null) {
		getCurrentDialogState().setCurrentState(RecipeAssistance.RA_WAITING_FOR_RECIPE_NAME);
		return;
	}
	if (keywords == null || keywords.isEmpty() && currRecipe != null) {
		DialogManager.giveDialogManager().setInErrorState(true);
	}
}

/**
* Updates the tell origin state according to parsed keywords/terms
* @param keywords
* @param terms
*/
private void updateStateTellOrigin(List<Keyword> keywords, List<String> terms) {
	//Check if new Recipe was also passed as a keyword
	if (keywords != null && !keywords.isEmpty()) {
		for (Keyword kw : keywords) {
			if (kw.getKeywordData().getType() == KeywordType.RECIPE) {
				currRecipe = new Recipe((RecipeData)kw.getKeywordData().getDataReference().get(0));
				return;
			}
		}
	}
	//If not, the recipe should've already been set, if not, we need a recipe Name.
	if (currRecipe == null || currRecipe.getRecipeData() == null) {
		getCurrentDialogState().setCurrentState(RecipeAssistance.RA_WAITING_FOR_RECIPE_NAME);
		return;
	}
	if (keywords == null || keywords.isEmpty() && currRecipe != null) {
		DialogManager.giveDialogManager().setInErrorState(true);
	}
}

/**
* Updates the tell steps state according to parsed keywords/terms
* @param keywords
* @param terms
*/
private void updateStateTellSteps(List<Keyword> keywords, List<String> terms) {
	//We should have come here by a keyword jump and the recipe shouldve been passed or set
	if (keywords != null && !keywords.isEmpty()) {
		for (Keyword kw : keywords) {
			if (kw.getKeywordData().getType() == KeywordType.RECIPE) {
				currRecipe = new Recipe((RecipeData)kw.getKeywordData().getDataReference().get(0));
				
				return;
			}
		}
	}
	//If not, the recipe should've already been set, if not, we need a recipe Name.
	if (currRecipe == null || currRecipe.getRecipeData() == null) {
		getCurrentDialogState().setCurrentState(RecipeAssistance.RA_WAITING_FOR_RECIPE_NAME);
		return;
	}
	if (keywords == null || keywords.isEmpty() && currRecipe != null) {
		DialogManager.giveDialogManager().setInErrorState(true);
	}
	
}

/**
* Updates the tell tools state according to parsed keywords/terms
* @param keywords
* @param terms
*/
private void updateStateTellTools(List<Keyword> keywords, List<String> terms) {
	//We should have come here by a keyword jump and the recipe shouldve been passed or set
	if (keywords != null && !keywords.isEmpty()) {
		for (Keyword kw : keywords) {
			if (kw.getKeywordData().getType() == KeywordType.RECIPE) {
				currRecipe = new Recipe((RecipeData)kw.getKeywordData().getDataReference().get(0));
				return;
			}
		}
	}

			//System.out.println(currRecipe.getRecipeData().getTools().size());

	//If not, the recipe should've already been set, if not, we need a recipe Name.
	if (currRecipe == null || currRecipe.getRecipeData() == null) {
		getCurrentDialogState().setCurrentState(RecipeAssistance.RA_WAITING_FOR_RECIPE_NAME);
		return;
	}
	if (keywords == null || keywords.isEmpty() && currRecipe != null) {
		DialogManager.giveDialogManager().setInErrorState(true);
	}
}

/**
* Updates the tell ingredients state according to parsed keywords/terms
* @param keywords
* @param terms
*/
private void updateStateTellIngredients(List<Keyword> keywords,
		List<String> terms) {
	//We should have come here by a keyword jump and the recipe shouldve been passed or set
	if (keywords != null && !keywords.isEmpty()) {
		for (Keyword kw : keywords) {
			if (kw.getKeywordData().getType() == KeywordType.RECIPE) {
				currRecipe = new Recipe((RecipeData)kw.getKeywordData().getDataReference().get(0));
				return;
			}
		}
	}
	//If not, the recipe should've already been set, if not, we need a recipe Name.
	if (currRecipe == null || currRecipe.getRecipeData() == null) {
		getCurrentDialogState().setCurrentState(RecipeAssistance.RA_WAITING_FOR_RECIPE_NAME);
		return;
	}
	if (keywords == null || keywords.isEmpty() && currRecipe != null) {
		DialogManager.giveDialogManager().setInErrorState(true);
	}
	
}

private void updateStateDelete(List<Keyword> keywords, List<String> terms) {
	//We should have come here by a keyword jump and the recipe shouldve been passed or set
		if (keywords != null && !keywords.isEmpty()) {
			for (Keyword kw : keywords) {
				if (kw.getKeywordData().getType() == KeywordType.RECIPE) {
					currRecipe = new Recipe((RecipeData)kw.getKeywordData().getDataReference().get(0));
					recipeName = currRecipe.getRecipeData().getRecipeName();
					return;
				}
			}
		}
		//If not, the recipe should've already been set, if not, we need a recipe Name.
		if (currRecipe == null || currRecipe.getRecipeData() == null) {
			getCurrentDialogState().setCurrentState(RecipeAssistance.RA_WAITING_FOR_RECIPE_NAME);
		}
		//If we have a currRecipe we can delete it then
		else {
			//Remove keyword:
			DialogManager.giveDialogManager().getDictionary().removeKeyword(currRecipe.getRecipeData().getRecipeName());
			getRecipeDatabase().remove(currRecipe);
			currRecipe.getRecipeData().deleteFile();
			
		}
}

/**
* Updates the recipe not found state according to parsed keywords/terms
* @param keywords
* @param terms
*/
private void updateStateRNF(List<Keyword> keywords, List<String> approval, List<String> terms) {
	
	if (approval != null && !approval.isEmpty()) {
		if (approval.get(0).equals("yes")) {
			getCurrentDialogState().setCurrentState(RecipeLearning.RL_ENTRY);
			teaching = true;
			return;
		}
		else {
			getCurrentDialogState().setCurrentState(RecipeAssistance.RA_ENTRY);
			return;
		}
	}
	//Should jump due to keywords, if not something's wrong
	DialogManager.giveDialogManager().setInErrorState(true);
	
}

/**
 * 
 * @return the currIngredient
 */
public IngredientData getCurrIngredient() {
	return currIngredient;
}

/**
 * 
 * @param currIngredient the IngredientData to set
 */
public void setCurrIngredient(IngredientData currIngredient) {
	this.currIngredient = currIngredient;
}

/**
 * @return the teaching
 */
public boolean isTeaching() {
	return teaching;
}

/**
 * @param teaching the teaching to set
 */
public void setTeaching(boolean teaching) {
	this.teaching = teaching;
}

}