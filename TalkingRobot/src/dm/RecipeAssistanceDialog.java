package dm;

import java.util.List;

import data.IngredientData;
import data.KeywordType;
import data.RecipeData;

/**
 * This class represents a new recipe assistance dialog.
 * @author Aleksandar Andonov
 * @version 1.0
 */
public class RecipeAssistanceDialog extends KitchenDialog {

	private Recipe currRecipe;
	private String recipeName;

	
	/**
	 * @param session
	 * @param dialogState
	 */
	public RecipeAssistanceDialog(Session session, DialogState dialogState) {
		super(session, dialogState);
		this.dialogModus = DialogModus.RECIPE_ASSISTANCE;
		// TODO Auto-generated constructor stub
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
	case RA_EXIT:
		updateStateExit(keywords, terms);
		break;
	default:	
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
		boolean sameRef = false;
		Enum<?> refMax = null;
		for (DialogState d : keywords.get(0).getReference()) {
			
			Enum<?> ref = d.getCurrentState();
			for (Keyword kw : keywords) {
				int count = 0;
				for (DialogState d1 : kw.getReference()) {
					if (d1.getCurrentState().equals(ref)) {
						count++;
					}
					if (count == 1) {
							sameRef = true;
					}
				}
				
			}
			if (sameRef && refMax == null) {
				refMax = ref;
			}
		}
		if (sameRef) {
			getCurrentDialogState().setCurrentState(refMax);
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
				//If the Keyword has a pointer to RA_* we should rate that higher since we're IN RA_
				if (d.getCurrentState().getClass().getName().equals("dm.RecipeAssistance")) {
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

/**
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
			}
		}
	}
	
}

/**
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
		}
	}
	//If there was a keyword something's wrong, and if there's no terms somethings wrong as well.
		DialogManager.giveDialogManager().setInErrorState(true);

	
}
/**
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
		//if no terms have been passed something went wrong
		else {
			DialogManager.giveDialogManager().setInErrorState(true);
		}
	}
	
}

private void updateStateExit(List<Keyword> keywords, List<String> terms) {
	//Should be jumping due to keywords
	DialogManager.giveDialogManager().setInErrorState(true);
	
}

private void updateStateTellWholeRecipe(List<Keyword> keywords,
		List<String> terms) {
	//If we came here due to a keyword, there should be a recipe keyword, or the recipe has been set
	if (keywords != null && !keywords.isEmpty()) {
		for (Keyword kw : keywords) {
			if (kw.getKeywordData().getType().equals((KeywordType.RECIPE))) {
				currRecipe = new Recipe((RecipeData)kw.getKeywordData().getDataReference().get(0));
			}
		}
	}
	//If we don't know the recipe, we need to ask for the name
	if (currRecipe == null || currRecipe.getRecipeData() == null) {
		getCurrentDialogState().setCurrentState(RecipeAssistance.RA_WAITING_FOR_RECIPE_NAME);
	}
}

private void updateStateTellToolNotFound(List<Keyword> keywords,
		List<String> terms) {
	//State is not used
	
}

private void updateStateTellToolFound(List<Keyword> keywords, List<String> terms) {
	//State is not used
	
}

/**
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
					if (r.getRecipeData().getIngredients().contains((IngredientData)kw.getKeywordData().getDataReference().get(0)));
						currRecipe = r;
					}
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

private void updateStateTellIngredientNotFound(List<Keyword> keywords,
		List<String> terms) {
	//If we came here, keyword for ingredients and unknown ingredient must have been passed
	if (terms == null || terms.isEmpty()) {
		DialogManager.giveDialogManager().setInErrorState(true);
	}
	
}

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
	}
}

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
	}
	
}

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
	}
	
}

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
	}
	
}

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
	//If not, the recipe should've already been set, if not, we need a recipe Name.
	if (currRecipe == null || currRecipe.getRecipeData() == null) {
		getCurrentDialogState().setCurrentState(RecipeAssistance.RA_WAITING_FOR_RECIPE_NAME);
	}
	
}

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
	}
	
}

private void updateStateRNF(List<Keyword> keywords, List<String> terms) {
	//Should jump due to keywords, if not something's wrong
	DialogManager.giveDialogManager().setInErrorState(true);
	
}

}