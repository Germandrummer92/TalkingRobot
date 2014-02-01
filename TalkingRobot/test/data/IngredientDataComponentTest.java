package data;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
/**
 * Tests the IngredientDataClass.
 * @author Daniel Draper
 * @version 1.0
 *
 */
public class IngredientDataComponentTest extends DataComponentTest{
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testData = new IngredientData("salt", "Cabinet 1");
		testDataPath = "resources/files/IngredientData/";
		testDataClone = new IngredientData(testData.generateJSON(), 2);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		IngredientData concreteData = (IngredientData) testData;
		File testDataFile = new File(testDataPath + concreteData.getIngredientID() + ".json");
		testDataFile.delete(); //clean up the created file
	}
	
}
