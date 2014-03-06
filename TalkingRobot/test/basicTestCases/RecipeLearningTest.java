package basicTestCases;


import static org.junit.Assert.assertTrue;
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
		this.runMainActivityWithTestInput(userInput);
		
//		assertTrue(Main.giveMain().getNlgResult().contains("know you") 
//				|| Main.giveMain().getNlgResult().contains("profile has been saved"));
	}

}
