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
 * @author Xizhe Lian
 * @version 1.2
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

		System.out.println(errorHandleState.getCurrentState());
		System.out.println(errorHandleState.getOutputKeyword());
		System.out.println(comparison.getOutputKeyword());
		System.out.println(errorHandleState.isQuestion());
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
