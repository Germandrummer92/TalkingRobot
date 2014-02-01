package dm;

import java.util.ArrayList;

import data.IngredientData;
import data.RecipeData;
import data.RecipeStepData;
import data.ToolData;

/**
 * This class represents the different States a Recipe Assistance Dialog can have.
 * @author Aleksandar Andonov
 * @author Daniel Draper
 * @version 1.2
 *
 */
public class RecipeAssistanceState extends DialogState {

	
  /**
   * Creates a new Recipe Assistance State in the ENTRY state.
   */
  public RecipeAssistanceState() {
	  super();
	 setCurrentState(RecipeAssistance.RA_ENTRY);
  }
  
  /**
   * Creates a new Recipe Assistance State in the state specified.
   * @param currentState
   */
  public RecipeAssistanceState(RecipeAssistance currentState) {
	  super();
	  setCurrentState(currentState);
  }
	
  /**
   * @see DialogState#getOutputKeyword()
   */
  public String getOutputKeyword() {
		  RecipeAssistanceDialog dialog = (RecipeAssistanceDialog) DialogManager.giveDialogManager().getCurrentDialog();
		  RecipeData recipe = null;
		  String output = "";
		  String recipeName = dialog.getRecipeName();
		  String ingreds = null;
		  String steps = null;
		  String tools = null;
		  if (dialog.getCurrRecipe() != null && dialog.getCurrRecipe().getRecipeData() != null) {
			  	recipe = dialog.getCurrRecipe().getRecipeData();
			  	recipeName = recipe.getRecipeName();
		 		ingreds = getIngredients(recipe);
		 		steps = getSteps(recipe);
		 		tools = getTools(recipe);
		  }
	 switch ((RecipeAssistance)getCurrentState()) {
		case RA_ENTRY:
			setQuestion(false);
			break;
		case RA_EXIT:
			setQuestion(false);
			break;
		case RA_RECIPE_NOT_FOUND:
			setQuestion(true);
			return "<" + recipeName + ">";
		case RA_TELL_COUNTRY_OF_ORIGIN:
			setQuestion(false);
			return "<" + recipe.getOriginalCountry() + "> {" + recipeName + "}";
		case RA_TELL_CREATOR:
			setQuestion(false);
			return "<" + recipe.getCreator().getUserName() + "> {" + recipeName + "}";
		case RA_TELL_INGREDIENTS:
			setQuestion(false);
			return "<" + ingreds + ">";
		case RA_TELL_INGREDIENT_FOUND:
			setQuestion(false);
			return "<" + recipeName + ">";
		case RA_TELL_INGREDIENT_NOT_FOUND:
			setQuestion(false);
			return "<" + recipeName + ">";
		case RA_TELL_NUM_OF_STEPS:
			setQuestion(false);
			return "<" + Integer.toString(recipe.getNumOfSteps()) + ">";
		case RA_TELL_STEPS:
			setQuestion(false);
			return "<" + steps + "> {" + recipeName + "}";
		case RA_TELL_TOOLS:
			setQuestion(false);
			return "<" + tools + "> {" + recipeName + "}";
		case RA_TELL_TOOL_FOUND:
			setQuestion(false);
			return "<" +recipeName + ">";
		case RA_TELL_TOOL_NOT_FOUND:
			setQuestion(false);
			return "<" +recipeName + ">";
		case RA_TELL_WHOLE_RECIPE:
			setQuestion(false);
			return "<" + ingreds + ", " + tools +"> {" + steps + "}";
		case RA_WAITING_FOR_RECIPE_NAME:
			setQuestion(true);
			break;
		case RA_RECIPE_FOUND:
			setQuestion(true);
			return "<" + recipeName + ">";
		default:
			break;
		  }
		  
		return output;  
  }

  /**
   * Returns the tools of a recipe as a string with commas
   * @param recipe the recipe from which the tool should be taken
   * @return the tools separated by commas
   */
private String getTools(RecipeData recipe) {
	String res = "";
	ArrayList<ToolData> tools = recipe.getTools();
	if (tools.size() == 0) {
		return res;
	}
	
	ToolData last = tools.remove(recipe.getTools().size() - 1);
	for (ToolData tool : tools) {
		res += tool.getToolName() + " ,";
	}
	res += last.getToolName();
	return res;
}

/**
 * Returns the steps of a recipe as a single String.
 * @param recipe the recipe to which the steps belong
 * @return all steps as a string
 */
private String getSteps(RecipeData recipe) {
	String res = "";
	ArrayList<RecipeStepData> steps = recipe.getSteps();
	if (steps.size() == 0) {
		return res;
	}
	
	for (RecipeStepData step : steps) {
		res += step.getDescription() + " ";
	}
	return res;
}

/**
 * All ingredients of a recipe as a string, separated by commas
 * @param recipe the recipe with which the ingredients are associated
 * @return the ingredients of the recipe as a string.
 */
private String getIngredients(RecipeData recipe) {
	String res = "";
	ArrayList<IngredientData> ingreds = recipe.getIngredients();
	if (ingreds.size() == 0) {
		return res;
	}
	
	IngredientData last = ingreds.remove(recipe.getIngredients().size() - 1);
	for (IngredientData ing : ingreds) {
		res += ing.getIngredientName() + " ,";
	}
	res += last.getIngredientName();
	return res;
}



}