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
	
	ErrorHandling errorHandling;
	MealData randomMeal;
	RecipeData randomRecipe;

	/**
	 * @see ErrorStrategy#handleError(List)
	 */
	@Override
	public ErrorHandlingState handleError(List<String> errorWords) {
		this.riseCounter();
		Dialog currentDialog = DialogManager.giveDialogManager().getCurrentDialog();
		ErrorHandlingState errorState = decideDialogBasedErrorHandling(currentDialog);
		return null;
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
					errorState = new ErrorHandlingState(true, ErrorHandling.RESTART_START, result);
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
		return "how can i help you";
	}
	
	private String handleCanteen(Dialog currentDialog) {
		return "how can i help you";
	}
	
	private String handleCanteenInformation(Dialog currentDialog) {
		CanteenInformationDialog dialog = (CanteenInformationDialog) currentDialog;
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
		String output = "<" + meal.getMealName() + ">";
		output = output + "<" + line.getLineName() + ">";
		output = output + "<" + canteenData.getCanteenName().toString() + ">";
		return output;
	}

//	private String handleCanteenRecommendation(Dialog currentDialog) {
//		//  Auto-generated method stub
//		return null;
//	}
	
	private String handleKitchen(Dialog currentDialog) {
		return "how can i help you";
	}

	private String handleKitchenAssistance(Dialog currentDialog) {
		ArrayList<String> kitchenAssist = new ArrayList<String>();
		kitchenAssist.add("bring you ingredients");
		kitchenAssist.add("bring you tools");
		kitchenAssist.add("set the table");
		kitchenAssist.add("use the dishwasher");
		String output = "i can ";
		ArrayList<Integer> usedNumbers = new ArrayList<Integer>();
		
		while(usedNumbers.size() != 2) {
			int random = this.getRandomNum(4);
			if(!usedNumbers.contains(random)) {
				usedNumbers.add(random);
				output = output + kitchenAssist.get(random) + " ";
			}
		}
		
		output = output + "and ";
		while(usedNumbers.size() != 3) {
			int random = this.getRandomNum(4);
			if(!usedNumbers.contains(random)) {
				usedNumbers.add(random);
				output = output + kitchenAssist.get(random) + " ";
			}
		}
		
		output = output + "so how can i help you";
		
		return output;
	}

	private String handleRecipeAssistance(Dialog currentDialog) {
		RecipeAssistanceDialog dialog = (RecipeAssistanceDialog) currentDialog;
		
		if(dialog.getCurrRecipe() != null) {
			return "what do you want to know about " + dialog.getCurrRecipe().getRecipeData().getRecipeName();
		} else {
			Dictionary dictionary = new Dictionary();
			ArrayList<RecipeData> recipes = RecipeData.loadData();
			RecipeData recipe = recipes.get(this.getRandomNum(recipes.size()));
			this.randomRecipe = recipe;
			return "would you like to eat " + recipe.getRecipeName();
		}
		
	}

	private String handleRecipeLearning(Dialog currentDialog) {
		return "do you still want to teach me a recipe";
	}
	
	private int getRandomNum(int limit) {
		return (int) (Math.random() * (limit));
	}

}
