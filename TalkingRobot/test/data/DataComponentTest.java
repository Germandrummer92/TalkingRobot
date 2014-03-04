package data;

import static org.junit.Assert.*;

import org.junit.Test;
/**
 * An abstract class describing test cases which can be applied to a number of data classes. 
 * @author Aleksandar Andonov
 * @version 1.0
 */
public abstract class DataComponentTest {

	static Data testData;
	static String testDataPath;
	static Data testDataClone;
	
	/**
	 * Write the data in a file, create a new data object from this file and
	 * test if the two objects are identical (the one created from the data file and the
	 * one from which the data file was created).
	 */
	@Test
	public void testData() {
		testData.writeFile();
		assertTrue(testData.equals(testDataClone));
	}

}
