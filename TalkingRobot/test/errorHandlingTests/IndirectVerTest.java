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
public class IndirectVerTest extends BasicTest{
	
	/**
	 * Testing indirect verification strategy in main dialog
	 */
	@Test
	public void ivTest1() {
		userInput.add("hi");
		userInput.add("my name is meng meng");
		userInput.add("cafideria");
		userInput.add("yes");
		
		this.runMainActivityWithTestInput(userInput);
		
		assertTrue(nlgResults.get(3).contains("cafeteria")
				|| nlgResults.get(3).contains("there"));
	}
	
	/**
	 * Testing indirect verification strategy in main dialog
	 */
	@Test
	public void ivTest2() {
		userInput.add("hi");
		userInput.add("my name is meng meng");
		userInput.add("cafideria");
		userInput.add("no");
		
		this.runMainActivityWithTestInput(userInput);
		
		assertTrue(nlgResults.get(3).contains("again")
				|| nlgResults.get(3).contains("repeat"));
	}

}
