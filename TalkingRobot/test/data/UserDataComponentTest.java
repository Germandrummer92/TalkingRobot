package data;


import java.io.File;
import org.junit.After;
import org.junit.Before;

/**
 * @author Daniel Draper
 * @version 1.0
 * Tests the UserData class.
 */
public class UserDataComponentTest extends DataComponentTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUpBeforeClass() throws Exception {
		testData = new UserData("Daniel", true);
		testDataPath = "resources/files/UserData/";
		testDataClone = new UserData(testData.generateJSON());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDownAfterClass() throws Exception {
		UserData concreteData = (UserData) testData;
		File testDataFile = new File(testDataPath + concreteData.getUserID() + ".json");
		testDataFile.delete(); //clean up the created file
	}



}
