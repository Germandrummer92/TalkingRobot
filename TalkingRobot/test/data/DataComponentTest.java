package data;

import static org.junit.Assert.*;

import org.junit.Test;

public abstract class DataComponentTest {

	static Data testData;
	static String testDataPath;
	static Data testDataClone;
	
	/**
	 * Write the data in a file, create a new data object from this file and
	 * test if the two objects are identical.
	 */
	@Test
	public void testRobotData() {
		testData.writeFile();
		assertTrue(testData.equals(testDataClone));
	}

}
