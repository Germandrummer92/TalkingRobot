package basicTestCases;



import static org.junit.Assert.assertTrue;
import generalControl.Main;

import org.junit.Test;

public class RecipeAssistanceTest extends BasicTest{
	
	/**
	 * tests recipe assistance dialog about existing recipe.
	 */
	@Test
	public void askForExistingRecipeTest() {
		
		userInput.add("hello");
		userInput.add("my name is meng meng");	
		userInput.add("can you help me with hamburger");
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
	
	/**
	 * tests recipe assistance dialog about a non existing recipe.
	 */
	@Test
	public void askForNonExistingRecipeTest() {
		
		userInput.add("hello");
		userInput.add("my name is meng meng");	
		userInput.add("can you help me with cheeseburger");
		
		this.runMainActivityWithTestInput(userInput);

		assertTrue(nlgResults.get(2).contains("again")
				|| nlgResults.get(2).contains("repeat"));
	}
	
	/**
	 * tests recipe assistance dialog about a non existing recipe.
	 */
	@Test
	public void askForRecipeHelpTest() {
		
		userInput.add("hello");
		userInput.add("my name is meng meng");	
		userInput.add("can you help me with cooking");
		userInput.add("what recipe uses lettuce");
		
		this.runMainActivityWithTestInput(userInput);

		assertTrue(nlgResults.get(2).contains("What")
				&& nlgResults.get(3).contains("hamburger"));
	}
}
