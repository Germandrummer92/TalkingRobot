package testScenarios;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import data.IngredientData;
import data.MealCategoryData;
import data.RecipeData;
import data.RecipeStepData;
import data.ToolData;
import data.UserData;

public class RecipeLearningScenario extends basicTestCases.BasicTest  {

	@Test
	public void RLTest() {
		userInput.add("hi");
		userInput.add("alex");
		userInput.add("i would like to teach you a recipe");
		userInput.add("it is called marinated greek chicken skewers");
		userInput.add("Greece");
		userInput.add("we need red onion");
		userInput.add("yes"); //confirm
		userInput.add("lemon juice");
		userInput.add("yes"); //confirm
		userInput.add("pepper");
		userInput.add("yes");//confirm
		userInput.add("that was the last one");
		userInput.add("glass bowl");
		userInput.add("yes");
		userInput.add("the next tool is a knife");
		userInput.add("that was the last one"); //last on
		userInput.add("First marinate the chicken");
		userInput.add("Then call the catering service and eat something yummy");
		userInput.add("that was the last one");
		runMainActivityWithTestInput(userInput);
		//compare recipe from dialog with the one wished
		//set ingredients
		RecipeData rec = new RecipeData(null, null, null, null, null, null, null);
		IngredientData lj = new IngredientData("lemon juice", "");
		IngredientData redOnion = new IngredientData("red onion", "");
		IngredientData pep = new IngredientData("pepper", "");
		ArrayList<IngredientData> ingreds = new ArrayList<IngredientData>();
		ingreds.add(redOnion);
		ingreds.add(lj);
		ingreds.add(pep);
		rec.setIngredients(ingreds);
		//set tools
		ArrayList<ToolData> tools = new ArrayList<ToolData>();
		ArrayList<RecipeData> toolRec = new ArrayList<RecipeData>();
		toolRec.add(rec);
		ArrayList<ToolData> toolDatabase = ToolData.loadData();
		ToolData knife = null;
		ToolData bowl = null;
		for (int i = 0; i < toolDatabase.size(); i++) {
			if (toolDatabase.get(i).getToolName().equals("knife")) {
				knife = toolDatabase.get(i);
			}
			if (toolDatabase.get(i).getToolName().equals("glass bowl")) {
				bowl = toolDatabase.get(i);
			}
		}
		tools.add(bowl);
		tools.add(knife);
		rec.setTools(tools);
		//set steps
		ArrayList<RecipeStepData> steps = new ArrayList<RecipeStepData>();
		RecipeStepData step1 = new RecipeStepData("First marinate the chicken");
		RecipeStepData step2 = new RecipeStepData("Then call the catering service and eat something yummy");
		steps.add(step1);
		steps.add(step2);
		rec.setSteps(steps);
		//set user
		UserData alex = null;
		ArrayList<UserData> users = UserData.loadData();
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUserName().equals("Alex"))
				alex = users.get(i);
		}
		rec.setCreator(alex);
		//set origin country
		rec.setOriginalCountry("Greece");
		//set number of steps
		rec.setNumOfSteps(2);
		//set recipe name
		rec.setRecipeName("marinated greek chicken skewers");
		//set meal category
		MealCategoryData mealCategory = new MealCategoryData("default");
		rec.setMealCategory(mealCategory);
		
		//compare the two recipes
		ArrayList<RecipeData> recipes = new ArrayList<RecipeData>();
		recipes = RecipeData.loadData();
		RecipeData recFromDialog = null;
		for (int i = 0; i < recipes.size(); i++) {
			RecipeData rd = recipes.get(i);
			if (rd.getRecipeName().equals("marinated greek chicken skewers")) {
//				System.out.println(rd.getRecipeName()); TODO debug
				recFromDialog = rd;
			}
				
		}
//		System.out.println(recFromDialog.getRecipeID()); TODO debug
//		System.out.println(recFromDialog.getRecipeName()); TODO debug
		//remove unneeded files
		removableFiles.add("marinated greek chicken skewers");
		removableFiles.add("Greece");
		removableFiles.add("lemon juice");
		removableFiles.add("red onion");
		removableFiles.add("pepper");
		removableFiles.add("glass bowl");
//		rec.writeFile(); //TODO
		assertTrue(recFromDialog.equals(rec));
	}
		//TODO
//		assertTrue(nlgResults.get(1).contains("What can") &&
//				nlgResults.get(2).contains("this recipe") &&
//				nlgResults.get(3).contains("where") &&
//				nlgResults.get(4).contains("first") && //first ingredient
//				(nlgResults.get(5).contains("really") || nlgResults.get(5).contains("sure")) &&
//				(nlgResults.get(6).contains("next") || nlgResults.get(6).contains("also")) &&
//				nlgResults.get(11).contains("first tool") &&
//				nlgResults.get(12).contains("really") &&
//				nlgResults.get(13).contains("first tool") &&
//				nlgResults.get(11).contains("first tool") &&
//				);prefix
//				}
//	}

}
