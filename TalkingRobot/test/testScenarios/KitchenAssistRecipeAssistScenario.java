package testScenarios;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * 
 * @author Bettina Weller
 * @version 1.0
 * test scenario 9.1.b from the functional spec
 */
public class KitchenAssistRecipeAssistScenario extends basicTestCases.BasicTest{

	@Test
	public void KitchenRecipeAssist() {
		userInput.add("hi");
		userInput.add("bettina");
		userInput.add("could you help me cook");
		userInput.add("a recipe from the usa");
		userInput.add("tell me the ingredients");
		userInput.add("what kitchen tools are needed");
		userInput.add("can you help me in the kitchen now");
		userInput.add("where can i find a knife");
		
		runMainActivityWithTestInput(userInput);
		
		assertTrue(nlgResults.get(3).contains("hamburger"));
		assertTrue(nlgResults.get(4).contains("Ground beef") && nlgResults.get(4).contains("Lettuce") 
				&& nlgResults.get(4).contains("Buns")
				&& nlgResults.get(5).contains("knife")
				&& nlgResults.get(6).contains("can I"));
		assertTrue(nlgResults.get(7).contains("silverware drawer"));
		//TODO does not change from RA to KA
//		assertTrue(nlgResults.get(6).contains("silverware drawer"));
	}
}
