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
 * This class tests different combinations of keywords for the KitchenAssistance Dialog and the results of the updateState method.
 */
public class KitchenAssistanceTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DialogTest.currentDialog = new KitchenAssistanceDialog(new Session(new User(), Robot.loadRobots().get(0)), new KitchenAssistanceState());
	}

	/**
	 * Tests if the system finds a certain Tool and if it finds the right toolname from the keyword.
	 */
	@Test
	public void ToolTest() {
		ArrayList<Keyword> tool = new ArrayList<Keyword>();
		tool.add(loadKeyword(171)); //oven
		//tool.add(loadKeyword(172)); //knife
		//tool.add(loadKeyword(173)); //spoon
		try {
			DialogTest.currentDialog.updateState(tool, null, null);
		} catch (WrongStateClassException e) {
			//never Reached
		}
		assertTrue(DialogTest.currentDialog.getCurrentDialogState().getCurrentState().equals(KitchenAssistance.KA_TELL_TOOL_FOUND) 
				&& ((KitchenAssistanceDialog)DialogTest.currentDialog).getRequestedObjectName().equals("oven")
				&& ((Tool)((KitchenAssistanceDialog)DialogTest.currentDialog).getRequestedObject()).getToolData().getToolName().equals("oven"));
	}
	
	/**
	 * Tests if the system finds a certain Ingredient and if it finds the right ingredient name from the keyword.
	 */
	@Test
	public void IngredientTest() {
		ArrayList<Keyword> ingredient = new ArrayList<Keyword>();
		ingredient.add(loadKeyword(174)); //tomato
		try {
			DialogTest.currentDialog.updateState(ingredient, null, null);
		} catch (WrongStateClassException e) {
			//never Reached
		}
		assertTrue(DialogTest.currentDialog.getCurrentDialogState().getCurrentState().equals(KitchenAssistance.KA_TELL_INGREDIENT_FOUND) 
				&& ((KitchenAssistanceDialog)DialogTest.currentDialog).getRequestedObjectName().equals("Tomato")
				&& ((Ingredient)((KitchenAssistanceDialog)DialogTest.currentDialog).getRequestedObject()).getIngredientData().getIngredientName().equals("Tomato"));
	}
	
	/**
	 * Tests if the system goes to the right state, when passed a string not equal to an already learned tool or ingredient.
	 */
	@Test
	public void NotFoundTest() {
		ArrayList<String> ingredient = new ArrayList<String>();
		ArrayList<String> tool = new ArrayList<String>();
		tool.add("fork");
		ingredient.add("sugar"); //tomato
		try {
			DialogTest.currentDialog.updateState(null, ingredient, null);
		} catch (WrongStateClassException e) {
			//never Reached
		}
		assertTrue(DialogTest.currentDialog.getCurrentDialogState().getCurrentState().equals(KitchenAssistance.KA_TELL_TOOL_NOT_FOUND) 
				&& ((KitchenAssistanceDialog)DialogTest.currentDialog).getRequestedObjectName().equals("sugar"));
		try {
			DialogTest.currentDialog.updateState(null, tool, null);
		} catch (WrongStateClassException e) {
			//never Reached
		}
		assertTrue(DialogTest.currentDialog.getCurrentDialogState().getCurrentState().equals(KitchenAssistance.KA_TELL_TOOL_NOT_FOUND) 
				&& ((KitchenAssistanceDialog)DialogTest.currentDialog).getRequestedObjectName().equals("fork"));
	}
	/**
	 * Loads and returns the keyword specified by the ID passed as a parameter
	 * @param KeywordID
	 * @return
	 */
	private Keyword loadKeyword(int keywordID) {
		File load = new File("resources/files/KeywordData/" + keywordID + ".json");
		//Needed for Generic Enum Deserialization
		EnumDeserializer des = new EnumDeserializer();
		DataDeserializer d = new DataDeserializer();
		Gson loader = new GsonBuilder().registerTypeAdapter(java.lang.Enum.class, des).registerTypeAdapter(data.Data.class, d).create();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(load));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	  	KeywordData read = null;
	  		try {
				read = loader.fromJson(br.readLine(), KeywordData.class);
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	  	try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	  	return new Keyword(read);	
	  	
	}
	
	@After
	public void tearDown() {
		DialogTest.currentDialog = new KitchenAssistanceDialog(new Session(new User(), Robot.loadRobots().get(0)), new KitchenAssistanceState());
	}
}
