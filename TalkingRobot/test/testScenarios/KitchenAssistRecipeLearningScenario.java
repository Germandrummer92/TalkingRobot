package testScenarios;

import static org.junit.Assert.*;

import generalControl.Main;

import java.io.File;

import org.junit.Test;

/**
 * 
 * @author Luiz Henrique S. Silva
 *
 *	Testing Scenario present in the Functional Requirements 9.1.a) for Recipe Learning
 */

public class KitchenAssistRecipeLearningScenario  extends basicTestCases.BasicTest {
	@Test
	public void RLTest() {
		
		userInput.add("hi");
		userInput.add("Mike");
		userInput.add("yes"); //confirm
		userInput.add("student");
		//System does not  support deletion, so it will not be made. 
		userInput.add("how can i cook ravioli-bolognese");
		userInput.add("ravioli-bolognese");
		userInput.add("yes");
		userInput.add("italy");
		userInput.add("we need ravioli");
		userInput.add("yes"); //confirm
		userInput.add("minced meat");
		userInput.add("yes"); //confirm
		userInput.add("onions");
		userInput.add("yes");//confirm
		userInput.add("we also need garlic");
		userInput.add("yes");
		userInput.add("carrot");
		userInput.add("yes");
		userInput.add("parmesan cheese"); //last on
		userInput.add("yes");
		userInput.add("oregano");
		userInput.add("yes");
		userInput.add("that is all");
		userInput.add("we need a pot");
		userInput.add("yes");
		userInput.add("we also need a spoon");
		userInput.add("last one is a bowl");
		userInput.add("first you need to prepare the sauce.");
		userInput.add("then you have to cook the ravioli");
		userInput.add("then put everything together and enjoy");
		userInput.add("that is all");
		userInput.add("how can i cook ravioli-bolognese");
		runMainActivityWithTestInput(userInput);
		
		//To prepare this recipe you will need the following ingredients and tools spaghetti minced meat onions garlic carrot parmesan cheese oregano pot pot spoon.  When you have them follow the following steps first you need to prepare the sauce then you have to cook the spaghetti at last you have to put everything together and enjoy
		File newRecipe = new File("resources/files/RecipeData/" + recipeDataCount + ".json");
		assertTrue(newRecipe.exists() 
				&& Main.giveMain().getNlgResult().contains("To prepare this recipe you will need the following ingredients" +
		" and tools ravioli minced meat onions garlic carrot parmesan cheese oregano pot spoon.  When you have them follow" + 
		" the following steps first you need to prepare the sauce. then you have to cook the ravioli then put everything " + 
		"together and enjoy"));
		
		removableFiles.add("ravioli-bolognese");
		removableFiles.add("ravioli");
		removableFiles.add("minced meat");
		removableFiles.add("onions");
		removableFiles.add("garlic");
		removableFiles.add("carrot");
		removableFiles.add("parmesan cheese");
		removableFiles.add("oregano");
		removableFiles.add("pot");
	}
}