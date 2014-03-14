package coverageTests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 *	Covers more situations possible in Canteen Recommendation
 * 
 * @author Luiz Henrique S. Silva
 *
 */
public class CRCoverageTest extends basicTestCases.BasicTest {

	@Test
	public void recomTest1() {
		userInput.add("hello");
		userInput.add("hi");
		userInput.add("luiz");
		userInput.add("what can i eat with pork today");
		//Covering approval
		userInput.add("yes");
		userInput.add("what can i eat with fish tomorrow");
		//Covering different week days
		userInput.add("beef monday");
		userInput.add("cow tuesday");
		userInput.add("vegan wednesday");
		//Covering approval
		userInput.add("no");
		userInput.add("vegetarian thursday");
		//Covering error handling
		userInput.add("blablabla");
		userInput.add("pork sunday");
		userInput.add("pork saturday");
		runMainActivityWithTestInput(userInput);
		
		assertTrue(nlgResults.get(4).contains("else") && nlgResults.get(9).contains("else")
				&& nlgResults.get(12).contains("closed") && nlgResults.get(13).contains("closed"));
		
	}
	
	@Test
	public void recomTest2() {
		userInput.add("hello");
		userInput.add("hi");
		userInput.add("luiz");
		userInput.add("i need a suggestion");
		userInput.add("blablabla");
		userInput.add("i need a suggestion");
		userInput.add("vegan wednesday");
		runMainActivityWithTestInput(userInput);

		assertTrue(nlgResults.get(5).contains("to have")
				&& nlgResults.get(6).contains("at"));
		
	}
	
}
