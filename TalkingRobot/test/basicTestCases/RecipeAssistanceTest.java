package basicTestCases;


import static org.junit.Assert.assertTrue;
import generalControl.Main;

import org.junit.Test;

public class RecipeAssistanceTest extends BasicTest{
	
	/**
	 * tests recipe learning dialog.
	 * uses recipe assistance afterwards to check if the recipe was created.
	 */
	@Test
	public void askForExistingRecipeTest() {
		
		userInput.add("hello");
		userInput.add("my name is meng meng");	
		userInput.add("can you tell me something about hamburger");
		userInput.add("which country is it from");
		userInput.add("which ingredients do i need");
		userInput.add("what is the first ingredient");
		userInput.add("what tools are needed for hamburgers");
		userInput.add("what is the first step for hamburger");
		userInput.add("what is the next one");
		
		
		this.runMainActivityWithTestInput(userInput);

		assertTrue(nlgResults.get(3).contains("USA")
				&& nlgResults.get(4).contains("Ground beef")
				&& nlgResults.get(4).contains("Lettuce")
				&& nlgResults.get(4).contains("Buns")
				&& nlgResults.get(6).contains("knife"));
	}
	
//	/**
//	 * tests recipe learning dialog.
//	 * uses recipe assistance afterwards to check if the recipe was created.
//	 */
//	@Test
//	public void askForNonExistingRecipeTest() {
//		
//		userInput.add("hello");
//		userInput.add("my name is meng meng");	
//		userInput.add("can you tell me something about cheeseburger");
//		userInput.add("which country is it from");
//		userInput.add("which ingredients do i need");
////		userInput.add("what tools are needed for hamburgers");
//		
//		this.runMainActivityWithTestInput(userInput);
//
//		assertTrue(true);
//	}
}
