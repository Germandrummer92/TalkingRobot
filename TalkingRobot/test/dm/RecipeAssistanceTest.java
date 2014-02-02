/**
 * 
 */
package dm;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import data.DataDeserializer;
import data.EnumDeserializer;
import data.KeywordData;

/**
 * @author Daniel Draper
 * @version 1.0
 * This class tests the RecipeAssistanceTest and makes sure all different keywords are resolved the right way.
 */
public class RecipeAssistanceTest {

	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DialogTest.currentDialog = new RecipeAssistanceDialog(new Session(new User(), Robot.loadRobots().get(0)), new RecipeAssistanceState());
	}

	/**
	 * Tests the RecipeAssistanceDialog to see, if passed a recipe keyword, it switches to the correct state.
	 */
	@Test
	public void recipeTest() {
		ArrayList<Keyword> recipe = new ArrayList<Keyword>();
		recipe.add(loadKeyword(168)); //Hamburger recipe
		try {
			DialogTest.currentDialog.updateState(recipe, null, null);
		}
		catch (WrongStateClassException e) {
			assertTrue(DialogTest.currentDialog.getCurrentDialogState().getCurrentState().equals(RecipeAssistance.RA_RECIPE_FOUND));
		}
		assertTrue(DialogTest.currentDialog.getCurrentDialogState().getCurrentState().equals(RecipeAssistance.RA_RECIPE_FOUND));
	}
	
	/**
	 * Tests the RecipeAssistanceDialog to see, if passed a recipe name not know, if it switches to the correct state.
	 */
	@Test
	public void recipeNotFoundTest() {
		ArrayList<String> recipe = new ArrayList<String>();
		recipe.add("Spaetzle");
		try {
			DialogTest.currentDialog.updateState(null, recipe, null);
		}
		catch (WrongStateClassException e) {
			assertTrue(DialogTest.currentDialog.getCurrentDialogState().getCurrentState().equals(RecipeAssistance.RA_RECIPE_NOT_FOUND));
		}
		assertTrue(DialogTest.currentDialog.getCurrentDialogState().getCurrentState().equals(RecipeAssistance.RA_RECIPE_NOT_FOUND));
	}
	
	/**
	 * Tests the RecipeAssistanceDialog to see, if passed a recipe name and "Ingredient" if it switches to the correct state.
	 */
	@Test
	public void recipeIngredientsTest() {
		ArrayList<Keyword> keywords = new ArrayList<Keyword>();
		keywords.add(loadKeyword(73)); //ingredient
		keywords.add(loadKeyword(168)); //hamburger
		try {
			DialogTest.currentDialog.updateState(keywords, null, null);
		}
		catch (WrongStateClassException e) {
			assertTrue(DialogTest.currentDialog.getCurrentDialogState().getCurrentState().equals(RecipeAssistance.RA_TELL_INGREDIENTS));
		}
		assertTrue(DialogTest.currentDialog.getCurrentDialogState().getCurrentState().equals(RecipeAssistance.RA_TELL_INGREDIENTS));
	}
	
	/**
	 * Tests the RecipeAssistanceDialog to see, if passed a ingredient, to see if it switches to the correct state.
	 */
	@Test
	public void ingredientsTest() {
		ArrayList<Keyword> keywords = new ArrayList<Keyword>();
		keywords.add(loadKeyword(174)); //tomato
		try {
			DialogTest.currentDialog.updateState(keywords, null, null);
		}
		catch (WrongStateClassException e) {
			assertTrue(DialogTest.currentDialog.getCurrentDialogState().getCurrentState().equals(RecipeAssistance.RA_TELL_INGREDIENT_FOUND));
		}
		assertTrue(DialogTest.currentDialog.getCurrentDialogState().getCurrentState().equals(RecipeAssistance.RA_TELL_INGREDIENT_FOUND));
	}
	
	/**
	 * Loads the keyword for which the id has been passed
	 * @param keywordID the ID of the keyword to be loaded
	 * @return the loaded keyword
	 */
	private Keyword loadKeyword(int keywordID) {
		File load = new File("resources/files/KeywordData/" + keywordID + ".json");
		//Needed for Generic Enum Deserialization
		EnumDeserializer des = new EnumDeserializer();
		DataDeserializer d = new DataDeserializer();
		Gson loader = new GsonBuilder().registerTypeAdapter(java.lang.Enum.class, des).registerTypeAdapter(data.Data.class, d).create();
		/*BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(load));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}*/
	  	KeywordData read = null;
	  		try {
				read = loader.fromJson(new FileReader(load), KeywordData.class);
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	  /*	try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	  	return new Keyword(read);	
	  	
	}
	
	/**
	 * Makes sure the Dialog is in the start state when invoking the test
	 */
	@After
	public void tearDown() {
		DialogTest.currentDialog = new RecipeAssistanceDialog(new Session(new User(), Robot.loadRobots().get(0)), new RecipeAssistanceState());
	}

}
