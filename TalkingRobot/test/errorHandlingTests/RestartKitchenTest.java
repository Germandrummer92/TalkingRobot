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
public class RestartKitchenTest extends BasicTest{
	
	/**
	 * Testing restart, rephrase and restart strategy in RA
	 */
	@Test
	public void reTest1() {
		userInput.add("hi");
		userInput.add("my name is meng meng");
		userInput.add("can you tell me about hamburger");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("handle the error");
		userInput.add("handle the error somehow");
		userInput.add("handle this error");
		userInput.add("tool");
		
		this.runMainActivityWithTestInput(userInput);
		
		assertTrue((nlgResults.get(3).contains("again") || nlgResults.get(3).contains("repeat"))
				&& (nlgResults.get(5).contains("again") || nlgResults.get(5).contains("repeat"))
				&& nlgResults.get(6).contains("rephrase")
				&& nlgResults.get(8).contains("rephrase")
				&& nlgResults.get(9).contains("hamburger")
				&& nlgResults.get(10).contains("knife"));
	}
	
	/**
	 * Testing restart, rephrase and restart strategy in RA
	 */
	@Test
	public void reTest2() {
		userInput.add("hi");
		userInput.add("my name is meng meng");
		userInput.add("can you tell me about hamburger");
		userInput.add("ingredients");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("handle the error");
		userInput.add("handle the error somehow");
		userInput.add("handle this error");
		userInput.add("no");
		
		this.runMainActivityWithTestInput(userInput);
		
		assertTrue((nlgResults.get(4).contains("again") || nlgResults.get(4).contains("repeat"))
				&& (nlgResults.get(6).contains("again") || nlgResults.get(6).contains("repeat"))
				&& nlgResults.get(7).contains("rephrase")
				&& nlgResults.get(9).contains("rephrase")
				&& nlgResults.get(10).contains("hamburger")
				&& (nlgResults.get(11).contains("again") || nlgResults.get(11).contains("repeat")));
	}
	
	/**
	 * Testing restart, rephrase and restart strategy in RA
	 * TODO repetition of output
	 */
	@Test
	public void reTest3() {
		userInput.add("hi");
		userInput.add("my name is meng meng");
		userInput.add("can you tell me about hamburger");
		userInput.add("ingredients");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("handle the error");
		userInput.add("handle the error somehow");
		userInput.add("handle this error");
		userInput.add("yes");
		
		this.runMainActivityWithTestInput(userInput);
		
		assertTrue((nlgResults.get(4).contains("again") || nlgResults.get(4).contains("repeat"))
				&& (nlgResults.get(6).contains("again") || nlgResults.get(6).contains("repeat"))
				&& nlgResults.get(7).contains("rephrase")
				&& nlgResults.get(9).contains("rephrase")
				&& nlgResults.get(10).contains("hamburger")
				&& (nlgResults.get(11).contains("What") || nlgResults.get(11).contains("what")));
	}
	
	/**
	 * Testing restart, rephrase and restart strategy in RL
	 */
	@Test
	public void reTest4() {
		userInput.add("hi");
		userInput.add("my name is meng meng");
		userInput.add("can i teach you a recipe");
		userInput.add("french fries");
//		removableFiles.add("french fries");
		userInput.add("france");
		removableFiles.add("france");
		userInput.add("you need potatoes");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("handle the error");
		userInput.add("handle the error somehow");
		userInput.add("handle this error");
		userInput.add("no");
		
		this.runMainActivityWithTestInput(userInput);
		
		assertTrue((nlgResults.get(6).contains("again") || nlgResults.get(6).contains("repeat"))
				&& (nlgResults.get(8).contains("again") || nlgResults.get(8).contains("repeat"))
				&& nlgResults.get(9).contains("rephrase")
				&& nlgResults.get(11).contains("rephrase")
				&& (nlgResults.get(12).contains("teach") || nlgResults.get(12).contains("tell"))
				&& (nlgResults.get(13).contains("again") || nlgResults.get(13).contains("repeat")));
	}
	
	/**
	 * Testing restart, rephrase and restart strategy in RL
	 */
	@Test
	public void reTest5() {
		userInput.add("hi");
		userInput.add("my name is meng meng");
		userInput.add("can i teach you a recipe");
		userInput.add("french fries");
//		removableFiles.add("french fries");
		userInput.add("france");
		removableFiles.add("france");
		userInput.add("you need potatoes");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("handle the error");
		userInput.add("handle the error somehow");
		userInput.add("handle this error");
		userInput.add("yes");
		
		this.runMainActivityWithTestInput(userInput);
		
		assertTrue((nlgResults.get(6).contains("again") || nlgResults.get(6).contains("repeat"))
				&& (nlgResults.get(8).contains("again") || nlgResults.get(8).contains("repeat"))
				&& nlgResults.get(9).contains("rephrase")
				&& nlgResults.get(11).contains("rephrase")
				&& (nlgResults.get(12).contains("new recipe") || nlgResults.get(12).contains("teach me")) 
				&& (nlgResults.get(13).contains("name") || nlgResults.get(13).contains("recipe")));
	}
	
	/**
	 * Testing restart, rephrase and restart strategy in KA
	 */
	@Test
	public void reTest6() {
		userInput.add("hi");
		userInput.add("my name is meng meng");
		userInput.add("can you bring me something");
		userInput.add("knife");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("handle the error");
		userInput.add("handle the error somehow");
		userInput.add("handle this error");
		userInput.add("no");
		
		this.runMainActivityWithTestInput(userInput);
		
//		assertTrue((nlgResults.get(6).contains("again") || nlgResults.get(6).contains("repeat"))
//				&& (nlgResults.get(8).contains("again") || nlgResults.get(8).contains("repeat"))
//				&& nlgResults.get(9).contains("rephrase")
//				&& nlgResults.get(11).contains("rephrase")
//				&& (nlgResults.get(12).contains("teach") || nlgResults.get(12).contains("tell"))
//				&& (nlgResults.get(13).contains("again") || nlgResults.get(13).contains("repeat")));
	}

}
