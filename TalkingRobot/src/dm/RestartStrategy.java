package dm;



import java.util.ArrayList;
import java.util.List;

import data.CanteenData;
import data.LineData;
import data.MealData;
import data.RecipeData;

/**
 * 
 * @author Meng Meng Yan
 * @version 1.0
 * the restart strategy : looks for the last Dialog modus and tries to restart the dialog from that point.
 */
public class RestartStrategy extends ErrorStrategy {
	
	private ErrorHandling errorHandling;
	private MealData randomMeal;
	private RecipeData randomRecipe;

	/**
	 * @see ErrorStrategy#handleError(List)
	 */
	@Override
	public ErrorHandlingState handleError(List<String> errorWords) {
		this.riseCounter();
		ErrorHandlingState errorState = null;
		if(errorWords != null) {
			Dialog currentDialog = DialogManager.giveDialogManager().getCurrentDialog();
			errorState = decideDialogBasedErrorHandling(currentDialog);
		} else {
			errorState = new ErrorHandlingState(true, ErrorHandling.RESTART_START, null);
		}
		return errorState;
	}

	private ErrorHandlingState decideDialogBasedErrorHandling(Dialog currentDialog) {
		DialogModus dialogModus = currentDialog.getDialogModus();

		String result;
		ErrorHandlingState errorState = null;
		switch(dialogModus) {
		case START:
					result = handleStart(currentDialog);
					this.errorHandling = ErrorHandling.RESTART_START;
	
					errorState = new ErrorHandlingState(true, ErrorHandling.RESTART_START, result);
					break;
		case CANTEEN:
					result = handleCanteen(currentDialog);
					errorState = new ErrorHandlingState(true, ErrorHandling.RESTART_START, result);
					break;
		case CANTEEN_INFORMATION:
					result = handleCanteenInformation(currentDialog);
					this.errorHandling = ErrorHandling.RESTART_CI;
					errorState = new ErrorHandlingState(true, ErrorHandling.RESTART_CI, result);
					break;
		case CANTEEN_RECOMMENDATION:
					result = handleCanteenInformation(currentDialog);
					this.errorHandling = ErrorHandling.RESTART_CR;
					errorState = new ErrorHandlingState(true, ErrorHandling.RESTART_CR, result);
					break;
		case KITCHEN:
					result = handleKitchen(currentDialog);
					errorState = new ErrorHandlingState(true, ErrorHandling.RESTART_START, result);
					break;
		case KITCHEN_ASSISTANCE:
					result = handleKitchenAssistance(currentDialog);
					this.errorHandling = ErrorHandling.RESTART_KA;
					errorState = new ErrorHandlingState(true, ErrorHandling.RESTART_KA, result);
					break;
		case RECIPE_ASSISTANCE:
					result = handleRecipeAssistance(currentDialog);
					this.errorHandling = ErrorHandling.RESTART_RA;
					errorState = new ErrorHandlingState(true, ErrorHandling.RESTART_RA, result);
					break;
		case RECIPE_LEARNING:

					result = handleRecipeLearning(currentDialog);
					this.errorHandling = ErrorHandling.RESTART_RL;
					errorState = new ErrorHandlingState(true, ErrorHandling.RESTART_RL, result);
					break;
		default:
					break;
		}

		return errorState;
	}
	

	private String handleStart(Dialog currentDialog) {
		return null;
	}
	
	private String handleCanteen(Dialog currentDialog) {
		return null;
	}
	
	private String handleCanteenInformation(Dialog currentDialog) {
		CanteenDialog dialog = (CanteenDialog) currentDialog;
		CanteenData canteenData = dialog.getCurrentCanteen().getCanteenData();
		ArrayList<MealData> todayMeals = new ArrayList<MealData>();
		for( LineData line : canteenData.getLines()) {
			todayMeals.addAll(line.getTodayMeals());
		}

		MealData meal = todayMeals.get(this.getRandomNum(todayMeals.size()));
		LineData line = null;
		for( LineData lineHelp : canteenData.getLines()) {
			for ( MealData mealHelp : lineHelp.getTodayMeals()) {
				if(mealHelp.equals(meal)) {
					line = lineHelp;
				}
			}
		}
		
		randomMeal = meal;
		String output = "<" + meal.getMealName() + ">,";
		output = output + "<" + line.getLineName() + ">,";
		output = output + "<" + canteenData.getCanteenName().toString().toLowerCase() + ">";
		return output;
	}

//	private String handleCanteenRecommendation(Dialog currentDialog) {
//		//  Auto-generated method stub
//		return null;
//	}
	
	private String handleKitchen(Dialog currentDialog) {
		return null;
	}

	private String handleKitchenAssistance(Dialog currentDialog) {
		return null;
	}

	private String handleRecipeAssistance(Dialog currentDialog) {
		RecipeAssistanceDialog dialog = (RecipeAssistanceDialog) currentDialog;
		
		if(dialog.getCurrRecipe() != null) {
			this.randomRecipe = dialog.getCurrRecipe().getRecipeData();
			return "<" + dialog.getCurrRecipe().getRecipeData().getRecipeName() + ">";
		} else {
			Dictionary dictionary = new Dictionary();
			ArrayList<RecipeData> recipes = RecipeData.loadData();
			RecipeData recipe = recipes.get(this.getRandomNum(recipes.size()));
			this.randomRecipe = recipe;
			return "<" + recipe.getRecipeName() + ">";
		}
		
	}

	private String handleRecipeLearning(Dialog currentDialog) {
		return null;
	}
	
	private int getRandomNum(int limit) {
		return (int) (Math.random() * (limit));
	}
	
	public ErrorHandling getErrorHandling() {
		return this.errorHandling;
	}
	
	public MealData getMeal() {
		return this.randomMeal;
	}
	
	public RecipeData getRecipe() {
		return this.randomRecipe;
	}

}
