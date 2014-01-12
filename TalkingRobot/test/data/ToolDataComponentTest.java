package data;

import java.io.File;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public class ToolDataComponentTest extends DataComponentTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testData = new ToolData("knife", "table", new ArrayList<RecipeData>());
		testDataPath = "resources/files/ToolData/";
		testDataClone = new ToolData(testData.generateJSON());
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		ToolData concreteData = (ToolData) testData;
		File testDataFile = new File(testDataPath + concreteData.getToolID() + ".json");
		testDataFile.delete(); //clean up the created file
	}

}
