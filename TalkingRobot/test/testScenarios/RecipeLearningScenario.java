package testScenarios;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Test;

import data.IngredientData;
import data.MealCategoryData;
import data.RecipeData;
import data.RecipeStepData;
import data.ToolData;
import data.UserData;

/**
 * This class test the recipe learning and assistance dialogs. It creates a new
 * recipe by using the system-user dialog and then checks weather that corresponds to the 
 * recipe which the user wanted to create.
 * The second part then test some features of the recipe assistance dialog (recommendation based
 * on country of origin and question about country of origin).
 * @author Aleksandar Andonov
 * @version 1.0
 */
public class RecipeLearningScenario extends basicTestCases.BasicTest  {

	@Test
	public void RLTest() {
		//first part of the dialog: RL
		userInput.add("hi");
		userInput.add("alex");
		userInput.add("i would like to teach you a recipe");
		userInput.add("it is called marinated greek chicken skewers");
		userInput.add("greecetest");
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
		
		//load wished recipe
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
		rec.setOriginalCountry("greecetest");
		//set number of steps
		rec.setNumOfSteps(2);
		//set recipe name
		rec.setRecipeName("marinated greek chicken skewers");
		//set meal category
		MealCategoryData mealCategory = new MealCategoryData("default");
		rec.setMealCategory(mealCategory);
		
		//load recipe from dialog
		ArrayList<RecipeData> recipes = new ArrayList<RecipeData>();
		recipes = RecipeData.loadData();
		RecipeData recFromDialog = null;
		for (int i = 0; i < recipes.size(); i++) {
			RecipeData rd = recipes.get(i);
			if (rd.getRecipeName().equals("marinated greek chicken skewers")) {
				recFromDialog = rd;
			}
				
		}
		
		//2nd part of test at RA
		nlgResults = new ArrayList<String>();
		userInput = new LinkedList<String>();
		userInput.add("what is the country of origin of marinated greek chicken skewers");
		userInput.add("i need a recipe from greecetest");
		runMainActivityWithTestInput(userInput);
		
		
		//remove unneeded files
		removableFiles.add("marinated greek chicken skewers");
		removableFiles.add("greecetest");
		removableFiles.add("lemon juice");
		removableFiles.add("red onion");
		removableFiles.add("pepper");
		removableFiles.add("glass bowl");

		assertTrue(recFromDialog.equals(rec));
		assertTrue(nlgResults.get(0).contains("greecetest") && nlgResults.get(1).contains("marinated greek chicken skewers"));
	}

}
