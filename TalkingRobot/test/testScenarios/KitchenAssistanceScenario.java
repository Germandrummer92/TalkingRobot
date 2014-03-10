package testScenarios;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 *
 * @author Daniel Draper
 * Testscenario 2
 */
public class KitchenAssistanceScenario extends basicTestCases.BasicTest {

	@Test
	public void KATest() {
		userInput.add("hi");
		userInput.add("daniel");
		userInput.add("can you help me in the kitchen");
		userInput.add("bring me something");
		userInput.add("bring me sausage");
		userInput.add("yes");
		userInput.add("ingredient");
		userInput.add("supermarket");
		userInput.add("bring me sausage");
		removableFiles.add("sausage");
		runMainActivityWithTestInput(userInput);
		assertTrue(nlgResults.get(2).contains("What can") &&
				   nlgResults.get(3).contains("What can") &&
				   nlgResults.get(4).contains("don't know") &&
				   nlgResults.get(5).contains("Ingredient") &&
				   nlgResults.get(6).contains("sausage") &&
				   nlgResults.get(7).contains("What can") &&
				   nlgResults.get(8).contains("Can't") || nlgResults.get(8).contains("can't"));
	}

}
