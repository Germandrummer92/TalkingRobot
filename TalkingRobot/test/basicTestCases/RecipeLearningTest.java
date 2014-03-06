package basicTestCases;


import static org.junit.Assert.assertTrue;

import java.io.File;

import generalControl.Main;

import org.junit.Test;

public class RecipeLearningTest extends BasicTest {
	
	@Test
	public void newRecipeTest() {
		
		userInput.add("hello");
		userInput.add("my name is meng meng");	
		userInput.add("i want to teach you a recipe");
		userInput.add("it is called lol");
		removableFiles.add("lol");
		
		userInput.add("it is from the moon");
		removableFiles.add("moon");
		
		userInput.add("the first ingredient is haha");
		removableFiles.add("haha");
		
		userInput.add("yes");
		userInput.add("tomato");
		userInput.add("that is all");
		
		userInput.add("we need a knife");
		userInput.add("that was the last");
		
		userInput.add("you first need to laugh");
		userInput.add("then you also need to smile");
		userInput.add("the last step is to grin");
		
		userInput.add("tell me about lol");
		
		this.runMainActivityWithTestInput(userInput);
		
		File newRecipe = new File("resources/files/RecipeData/" + recipeDataCount + ".json");
		assertTrue(newRecipe.exists() 
				&& Main.giveMain().getNlgResult().contains("to prepare this recipe") );
	}

}
