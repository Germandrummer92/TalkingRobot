package errorHandlingTests;



import static org.junit.Assert.assertTrue;

import org.junit.Test;

import basicTestCases.BasicTest;

public class RestartCanteenTest extends BasicTest {

	/**
	 * Testing restart, rephrase and restart strategy in CI
	 */
	@Test
	public void reTest1() {
		userInput.add("hi");
		userInput.add("my name is meng meng");
		userInput.add("i want to go to the canteen");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("handle the error");
		userInput.add("handle the error somehow");
		userInput.add("handle this error");
		userInput.add("yes");
		
		this.runMainActivityWithTestInput(userInput);
		
		assertTrue(true);
	}
	
	/**
	 * Testing restart, rephrase and restart strategy in CI
	 */
	@Test
	public void reTest2() {
		userInput.add("hi");
		userInput.add("my name is meng meng");
		userInput.add("i want to go to the canteen");
		userInput.add("can i eat at line one");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("handle the error");
		userInput.add("handle the error somehow");
		userInput.add("handle this error");
		userInput.add("yes");
		
		this.runMainActivityWithTestInput(userInput);
		
		assertTrue(true);
	}
	
	/**
	 * Testing restart, rephrase and restart strategy in CR
	 */
	@Test
	public void reTest3() {
		userInput.add("hi");
		userInput.add("my name is meng meng");
		userInput.add("i want a recommendation");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("handle the error");
		userInput.add("handle the error somehow");
		userInput.add("handle this error");
		userInput.add("pork");
		userInput.add("yes");
		
		this.runMainActivityWithTestInput(userInput);
		
		assertTrue((nlgResults.get(3).contains("again") || nlgResults.get(3).contains("repeat"))
				&& (nlgResults.get(5).contains("again") || nlgResults.get(5).contains("repeat"))
				&& nlgResults.get(6).contains("rephrase")
				&& nlgResults.get(8).contains("rephrase")
				&& (nlgResults.get(9).contains("would you like") || nlgResults.get(9).contains("prefer")));
	}
	
	/**
	 * Testing restart, rephrase and restart strategy in CR
	 */
	@Test
	public void reTest4() {
		userInput.add("hi");
		userInput.add("my name is meng meng");
		userInput.add("i want a recommendation");
		userInput.add("pork");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("handle the error");
		userInput.add("handle the error somehow");
		userInput.add("handle this error");
		userInput.add("handle this error please");
		userInput.add("yes");
		
		this.runMainActivityWithTestInput(userInput);
		
//		assertTrue((nlgResults.get(3).contains("again") || nlgResults.get(3).contains("repeat"))
//				&& (nlgResults.get(5).contains("again") || nlgResults.get(5).contains("repeat"))
//				&& nlgResults.get(6).contains("rephrase")
//				&& nlgResults.get(8).contains("rephrase")
//				&& (nlgResults.get(9).contains("would you like") || nlgResults.get(9).contains("prefer")));
	}
	
	/**
	 * Testing restart, rephrase and restart strategy in CR
	 */
	@Test
	public void reTest5() {
		userInput.add("hi");
		userInput.add("my name is meng meng");
		userInput.add("i want a recommendation");
		userInput.add("pork");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("handle the error");
		userInput.add("handle the error somehow");
		userInput.add("handle this error");
		userInput.add("handle this error please");
		userInput.add("no");
		userInput.add("i want a recommendation");
		
		this.runMainActivityWithTestInput(userInput);
		
		assertTrue((nlgResults.get(5).contains("again") || nlgResults.get(5).contains("repeat"))
				&& (nlgResults.get(7).contains("again") || nlgResults.get(7).contains("repeat"))
				&& nlgResults.get(8).contains("rephrase")
				&& nlgResults.get(10).contains("rephrase")
				&& (nlgResults.get(11).contains("you mean") || nlgResults.get(11).contains("you like"))
				&& (nlgResults.get(12).contains("again") || nlgResults.get(12).contains("repeat"))
				&& (nlgResults.get(13).contains("would you like") || nlgResults.get(13).contains("prefer")));
	}
	
}
