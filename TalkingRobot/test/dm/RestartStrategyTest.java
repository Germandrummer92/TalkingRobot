package dm;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.CanteenData;
import data.CanteenNames;
import data.LineData;
import data.MealData;
import data.RecipeData;

/**
 * 
 * @author Meng Meng Yan
 * @version 1.0
 */
public class RestartStrategyTest {
	
	RestartStrategy restart;
	DialogManager dm;
	Dialog dialog;
	
	@Before
	public void setUp() {
		restart = new RestartStrategy();
		dm = DialogManager.giveDialogManager();
	}
	
	@Test
	public void testRestartStartDialog() {
		dialog = new StartDialog(null);
		dm.setCurrentDialog(dialog);
		
		ErrorHandlingState errorHandleState = restart.handleError(null);
		ErrorHandlingState comparison = new ErrorHandlingState(
				true, ErrorHandling.RESTART_START, null);

		assertEquals(comparison.getCurrentState(), errorHandleState.getCurrentState());
		assertEquals(comparison.getOutputKeyword(), errorHandleState.getOutputKeyword());
		assertEquals(comparison.isQuestion(), errorHandleState.isQuestion());
	}
	
	@Test
	public void testRestartCIDialog() {
		ArrayList<MealData> todayMeal1 = new ArrayList<MealData>();
		todayMeal1.add(new MealData("chicken drum sticks", null, (float) 0.1));
		todayMeal1.add(new MealData("pizza", null, (float) 0.1));
		
		ArrayList<MealData> todayMeal2 = new ArrayList<MealData>();
		todayMeal1.add(new MealData("borek", null, (float) 0.1));
		todayMeal1.add(new MealData("spaghetti napoli", null, (float) 0.1));
		todayMeal1.add(new MealData("pancakes", null, (float) 0.1));
		
		ArrayList<LineData> lines = new ArrayList<LineData>();
		lines.add(new LineData("line 1", todayMeal1));
		lines.add(new LineData("line 2", todayMeal2));
		
		CanteenData canteenData = new CanteenData(CanteenNames.MOLTKE, "moltkstrasse", lines);
		dialog = new CanteenInformationDialog(null, null, new Canteen(canteenData));
		dm.setCurrentDialog(dialog);
		
		ErrorHandlingState errorHandleState = restart.handleError(null);
		
		ArrayList<String> meals = new ArrayList<String>();
		meals.add("chicken drum sticks");
		meals.add("pizza");
		meals.add("borek");
		meals.add("spaghetti napoli");
		meals.add("pancakes");
		
		ArrayList<String> line = new ArrayList<String>();
		line.add("line 1");
		line.add("line 2");
		
		String outputKeyword = errorHandleState.getOutputKeyword().replace("<", "");
		outputKeyword = outputKeyword.replace(">", "");
		String[] output = outputKeyword.split(",");

		assertTrue(meals.contains(output[0])
				&& line.contains(output[1])
				&& output[2].equals("moltke")
				&& (errorHandleState.getCurrentState() == ErrorHandling.RESTART_CI)
				&& (errorHandleState.isQuestion()));
	}
	
	@Test
	public void testRestartKADialog() {
		dialog = new KitchenAssistanceDialog(null, null);

		dm.setCurrentDialog(dialog);
		
		ErrorHandlingState errorHandleState = restart.handleError(null);
		ErrorHandlingState comparison = new ErrorHandlingState(
				true, ErrorHandling.RESTART_KA, null);

		assertEquals(comparison.getCurrentState(), errorHandleState.getCurrentState());
		assertEquals(comparison.getOutputKeyword(), errorHandleState.getOutputKeyword());
		assertEquals(comparison.isQuestion(), errorHandleState.isQuestion());
	}
	
	@Test
	public void testRestartRADialog() {
		dialog = new RecipeAssistanceDialog(null, null);
		dm.setCurrentDialog(dialog);
		
		ErrorHandlingState errorHandleState = restart.handleError(null);
		ErrorHandlingState comparison = new ErrorHandlingState(
				true, ErrorHandling.RESTART_RA, null);
		
		String output = errorHandleState.getOutputKeyword().replace("<", "");
		output = output.replace(">", "");
		
		ArrayList<RecipeData> recipes = RecipeData.loadData();
		ArrayList<String> recipeNames = new ArrayList<String>();
		for(int i = 0; i < recipes.size(); i++) {
			String recipeName = recipes.get(i).getRecipeName().replace("<", "");
			recipeNames.add(recipeName.replace(">", ""));
		}
		assertEquals(comparison.getCurrentState(), errorHandleState.getCurrentState());
		assertTrue(recipeNames.contains(output));
		assertEquals(comparison.isQuestion(), errorHandleState.isQuestion());
	}
	
	@Test
	public void testRestartRLDialog() {
		Robot rob = new Robot("Max", true);
		
		dialog = new RecipeLearningDialog(new Session(new User(), rob), null);

		dm.setCurrentDialog(dialog);
		
		ErrorHandlingState errorHandleState = restart.handleError(null);
		ErrorHandlingState comparison = new ErrorHandlingState(
				true, ErrorHandling.RESTART_RL, null);


		assertEquals(comparison.getCurrentState(), errorHandleState.getCurrentState());
		assertEquals(comparison.getOutputKeyword(), errorHandleState.getOutputKeyword());
		assertEquals(comparison.isQuestion(), errorHandleState.isQuestion());
	}
	
	
	@After
	public void tearDown() {
		restart = null;
		dm = null;
		dialog = null;
	}

}
