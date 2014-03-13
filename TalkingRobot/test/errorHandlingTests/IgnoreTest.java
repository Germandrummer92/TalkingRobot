package errorHandlingTests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import basicTestCases.BasicTest;

/**
 * 
 * @author Meng Meng Yan
 * @version 1.0
 *
 */
public class IgnoreTest extends BasicTest{
	
	/**
	 * Testing ignore strategy in main dialog
	 */
	@Test
	public void ignTest1() {
		userInput.add("hi");
		userInput.add("my name is meng meng");
		userInput.add("cafteria");
		
		this.runMainActivityWithTestInput(userInput);
		
		assertTrue(nlgResults.get(2).contains("cafeteria")
				|| nlgResults.get(2).contains("there"));
	}
}
