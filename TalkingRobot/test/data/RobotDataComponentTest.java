package data;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class RobotDataComponentTest extends DataComponentTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testData = new RobotData("harry potter", true);
		testDataPath = "resources/files/RobotData/";
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		RobotData concreteData = (RobotData) testData;
		File testDataFile = new File(testDataPath + concreteData.getRobotID() + ".json");
		testDataFile.delete(); //clean up the created file
	}

}
