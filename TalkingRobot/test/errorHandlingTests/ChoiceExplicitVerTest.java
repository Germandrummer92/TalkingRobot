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
public class ChoiceExplicitVerTest extends BasicTest{
	
	/**
	 * Testing explicit verification + choice strategy in main dialog
	 */
	@Test
	public void cevTest1() {
		userInput.add("hi");
		userInput.add("my name is meng meng");
		userInput.add("cavideria");
		userInput.add("cavideria");
		userInput.add("cavideria");
		userInput.add("cavideria");
		userInput.add("yes");
		
		this.runMainActivityWithTestInput(userInput);
		
		assertTrue((nlgResults.get(2).contains("cavideria") || nlgResults.get(2).contains("cafeteria"))
				&& (nlgResults.get(4).contains("cavideria") || nlgResults.get(4).contains("cafeteria"))
				&& nlgResults.get(5).contains("cafeteria")
				&& (nlgResults.get(6).contains("cafeteria") || nlgResults.get(6).contains("there")));
	}
	
	/**
	 * Testing explicit verification + choice strategy in main dialog
	 */
	@Test
	public void cevTest2() {
		userInput.add("hi");
		userInput.add("my name is meng meng");
		userInput.add("cavideria");
		userInput.add("cavideria");
		userInput.add("cavideria");
		userInput.add("cavideria");
		userInput.add("no");
		
		this.runMainActivityWithTestInput(userInput);
		
		assertTrue((nlgResults.get(2).contains("cavideria") || nlgResults.get(2).contains("cafeteria"))
				&& (nlgResults.get(4).contains("cavideria") || nlgResults.get(4).contains("cafeteria"))
				&& nlgResults.get(5).contains("cafeteria")
				&& (nlgResults.get(6).contains("again") || nlgResults.get(6).contains("repeat")));
	}
	
	/**
	 * Testing choice strategy in main dialog
	 */
	@Test
	public void cTest2() {
		userInput.add("hi");
		userInput.add("my name is meng meng");
		userInput.add("cavideria");
		userInput.add("i meant the first");
		
		this.runMainActivityWithTestInput(userInput);
		
		assertTrue((nlgResults.get(2).contains("cavideria") || nlgResults.get(2).contains("cafeteria"))
				&& (nlgResults.get(3).contains("again") || nlgResults.get(3).contains("repeat")));
	}
	
	/**
	 * Testing choice strategy in main dialog
	 */
	@Test
	public void cTest3() {
		userInput.add("hi");
		userInput.add("my name is meng meng");
		userInput.add("cavideria");
		userInput.add("the second");
		
		this.runMainActivityWithTestInput(userInput);
		
		assertTrue((nlgResults.get(2).contains("cavideria") || nlgResults.get(2).contains("cafeteria"))
				&& (nlgResults.get(3).contains("cafeteria") || nlgResults.get(3).contains("there")));
	}

}
