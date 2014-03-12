package basicTestCases;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * 
 * @author Bettina Weller
 * @version 1.0
 * A test class for canteen information.
 */
public class CanteenInformationTest extends BasicTest{
	
	/**
	 * tests for meals at line one in canteen adenauerring.
	 * everything in one sentence mentioned.
	 */
	@Test
	public void lineOneTodayAdenExpl() {
		userInput.add("hello");
		userInput.add("i am bettina");
		userInput.add("what can i eat at line one in canteen adenauerring");
//		userInput.add("how much does it cost");
		
		this.runMainActivityWithTestInput(userInput);
		
		assertTrue(nlgResults.get(2).contains("at line one"));
	}
	
	/**
	 * tests for meals at line one in canteen moltke.
	 * everything in one sentence mentioned.
	 */
	@Test
	public void lineOneTodayMoltkeExpl() {
		userInput.add("hello");
		userInput.add("i am bettina");
		userInput.add("what can i eat at line one in canteen Moltke");
//		userInput.add("how much does it cost");
		
		this.runMainActivityWithTestInput(userInput);
		
		assertTrue(nlgResults.get(2).contains("at choice one") || nlgResults.get(2).contains("at line one"));
	}
	
	/**
	 * tests for meals at line one in canteen adenauerring next monday.
	 * everything in one sentence mentioned.
	 */
	@Test
	public void lineOneNextMondayAdenExpl() {
		userInput.add("hello");
		userInput.add("i am bettina");
		userInput.add("what can i eat at line one in canteen adenauerring next monday");
//		userInput.add("how much does it cost");
		
		this.runMainActivityWithTestInput(userInput);
		
		assertTrue(nlgResults.get(2).contains("at line one"));
	}
	
	/**
	 * tests for meals at line one in canteen moltke next monday.
	 * everything in one sentence mentioned.
	 */
	@Test
	public void lineOneNextMondayMoltkeExpl() {
		userInput.add("hello");
		userInput.add("i am bettina");
		userInput.add("what can i eat at line one in canteen Moltke next monday");
//		userInput.add("how much does it cost");
		
		this.runMainActivityWithTestInput(userInput);
		
		assertTrue(nlgResults.get(2).contains("at choice one") || nlgResults.get(2).contains("at line one"));
	}
	
	/**
	 * ask for the location of line one in canteen adenauerring.
	 */
	@Test
	public void whereLineOneAdenExpl() {
		userInput.add("hello");
		userInput.add("i am bettina");
		userInput.add("where is line one in canteen adenauerring");
		
		this.runMainActivityWithTestInput(userInput);
		
		assertTrue(nlgResults.get(2).contains("line one"));
	}

}
