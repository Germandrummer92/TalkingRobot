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
public class ErrorHandlingTest extends BasicTest{
	
	/**
	 * Testing restart, rephrase and restart strategy in main dialog
	 */
	@Test
	public void reTest1() {
		userInput.add("hi");
		userInput.add("my name is meng meng");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("errorhandling");
		userInput.add("handle the error");
		userInput.add("handle the error somehow");
		userInput.add("handle this error");
		
		this.runMainActivityWithTestInput(userInput);
		
		assertTrue((nlgResults.get(2).contains("again") || nlgResults.get(2).contains("repeat"))
				&& (nlgResults.get(4).contains("again") || nlgResults.get(4).contains("repeat"))
				&& nlgResults.get(5).contains("rephrase")
				&& nlgResults.get(7).contains("rephrase")
				&& (nlgResults.get(8).contains("help") || nlgResults.get(8).contains("can I do")));
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
		userInput.add("yes");
		
		this.runMainActivityWithTestInput(userInput);
		
		assertTrue((nlgResults.get(6).contains("again") || nlgResults.get(6).contains("repeat"))
				&& (nlgResults.get(8).contains("again") || nlgResults.get(8).contains("repeat"))
				&& nlgResults.get(9).contains("rephrase")
				&& nlgResults.get(11).contains("rephrase")
				&& (nlgResults.get(12).contains("new recipe") || nlgResults.get(12).contains("teach me")) 
				&& (nlgResults.get(13).contains("name") || nlgResults.get(13).contains("recipe")));
	}

}
