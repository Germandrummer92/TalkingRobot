package data;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
/**
 * This class tests the recipeData class and applies the abstract tests defined in DataComponentTest.
 * @author Aleksandar Andonov
 * @version 1.0
 */
public class RecipeDataComponentTest extends DataComponentTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ArrayList<IngredientData> ingreds = new ArrayList<IngredientData>();
		ArrayList<ToolData> tools = new ArrayList<ToolData>();
		ArrayList<RecipeStepData> rs = new ArrayList<RecipeStepData>();
		ingreds.add(new IngredientData("tomatos", "moon"));
		tools.add(new ToolData("knife", "mars", null));
		rs.add(new RecipeStepData("cut the spaghetti with mars knife"));
		rs.add(new RecipeStepData("let them boil in helium"));
		testData = new RecipeData("spaghetti a la USSR", ingreds, rs, tools, null, "USSR", null);
		testDataPath = "resources/files/RecipeData/";
		testDataClone = new RecipeData(testData.generateJSON());
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		RecipeData concreteData = (RecipeData) testData;
		File testDataFile = new File(testDataPath + concreteData.getRecipeID() + ".json");
		testDataFile.delete(); //clean up the created file
	}

}
