package data;

import static org.junit.Assert.*;

import org.junit.Test;

public abstract class DataComponentTest {

	static Data testData;
	static String testDataPath;

	/**
	 * Write the data in a file, create a new data object from this file and
	 * test if the two objects are identical.
	 */
	@Test
	public void testRobotData() {
		testData.writeFile();
		RobotData testDataClone = new RobotData(testData.generateJSON()); //a clone of the object, test indirectly createFromJson
		assertTrue(testData.equals(testDataClone));
	}

}
