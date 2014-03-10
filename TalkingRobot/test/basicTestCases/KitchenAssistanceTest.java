package basicTestCases;


import static org.junit.Assert.assertTrue;
import generalControl.Main;

import java.io.File;

import org.junit.Test;

/**
 * 
 * @author Meng Meng Yan
 * @version
 * 
 * Tests for the kitchen assistance (in func. spec. cooking assistance; name changed to
 * kitchen assistance because it covers up more than just help for cooking)
 */
public class KitchenAssistanceTest extends BasicTest{
	
	/**
	 * tests the kitchen assistance for help with a tool.
	 */
	@Test
	public void toolAssistanceTest() {
		
		userInput.add("hello");
		userInput.add("my name is meng meng");	
		userInput.add("can you get me a knife");
		
		this.runMainActivityWithTestInput(userInput);
		
		assertTrue(this.nlgResults.get(nlgResults.size() - 1).contains("silverware drawer"));
	}
	
	/**
	 * tests the kitchen assistance for help with a tool which the system does not know.
	 */
	@Test
	public void toolAssistanceTest2() {
		
		userInput.add("hello");
		userInput.add("my name is meng meng");	
		userInput.add("can you bring me a scissor");
		
		this.runMainActivityWithTestInput(userInput);
		
		assertTrue(this.nlgResults.get(nlgResults.size() - 1).contains("don't know"));
	}
	
	/**
	 * tests the kitchen assistance about teaching a location.
	 */
	@Test
	public void toolAssistanceTest3() {
		
		userInput.add("hello");
		userInput.add("my name is meng meng");	
		userInput.add("can you bring me a scissor");
		userInput.add("yes sure");
		userInput.add("it is a tool");
		userInput.add("it is on the desk");
		userInput.add("can you bring me a scissor");
		
		this.runMainActivityWithTestInput(userInput);
		
		assertTrue(this.nlgResults.get(nlgResults.size() - 1).contains("desk"));
	}

}
