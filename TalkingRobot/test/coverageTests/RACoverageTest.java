package coverageTests;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * This class tests more lines of the RecipeAssistanceDialog to increase its coverage
 * @author Daniel Draper
 * @version 1.0
 *
 */
public class RACoverageTest extends basicTestCases.BasicTest {

	/**
	 * Test to see if the origin of a recipe is correctly returned
	 */
	@Test
	public void originTest1() {
		userInput.add("hello");
		userInput.add("daniel");
		userInput.add("can you help me with cooking");
		userInput.add("what origin does hamburger have");
		runMainActivityWithTestInput(userInput);
		assertTrue(nlgResults.get(2).contains("what") || nlgResults.get(2).contains("What")
				&& nlgResults.get(3).contains("USA"));
		
	}
	
	/**
	 * Test to see if the origin of a recipe is correctly returned
	 */
	@Test
	public void originTest2() {
		userInput.add("hello");
		userInput.add("daniel");
		userInput.add("can you help me with cooking");
		userInput.add("what is the origin");
		userInput.add("hamburger");
		userInput.add("origin");
		runMainActivityWithTestInput(userInput);
		assertTrue(nlgResults.get(2).contains("what") || nlgResults.get(2).contains("What")
				&& nlgResults.get(3).contains("what") || nlgResults.get(3).contains("What") || nlgResults.get(3).contains("name")
				&& nlgResults.get(4).contains("hamburger")
				&& nlgResults.get(5).contains("USA"));
		
	}
	/**
	 * Tests if the steps are passed correctly
	 */
	@Test
	public void stepsTest() {
		userInput.add("hello");
		userInput.add("daniel");
		userInput.add("can you help me with cooking");
		userInput.add("tell me the steps of hamburger");
		runMainActivityWithTestInput(userInput);
		assertTrue(nlgResults.get(2).contains("what") || nlgResults.get(2).contains("What")
				&& nlgResults.get(3).contains("Form the ground Beef into hamburgers"));
		
	}
	
	/**
	 * Tests if the steps are passed correctly
	 */
	@Test
	public void stepsTest2() {
		userInput.add("hello");
		userInput.add("daniel");
		userInput.add("can you help me with cooking");
		userInput.add("tell me the steps");
		userInput.add("hamburger");
		userInput.add("steps");
		runMainActivityWithTestInput(userInput);
		assertTrue(nlgResults.get(2).contains("what") || nlgResults.get(2).contains("What")
				&& nlgResults.get(3).contains("what") || nlgResults.get(3).contains("What") || nlgResults.get(3).contains("name")
				&& nlgResults.get(4).contains("hamburger")
				&& nlgResults.get(5).contains("Form the ground Beef into hamburgers"));
	}
	
	/**
	 * Tests if the steps are passed correctly
	 */
	@Test
	public void toolsTest() {
		userInput.add("hello");
		userInput.add("daniel");
		userInput.add("can you help me with cooking");
		userInput.add("tell me the tools of hamburger");
		runMainActivityWithTestInput(userInput);
		assertTrue(nlgResults.get(2).contains("what") || nlgResults.get(2).contains("What")
				&& nlgResults.get(3).contains("knife"));
		
	}
	
	/**
	 * Tests if the tools are passed correctly
	 */
	@Test
	public void toolsTest2() {
		userInput.add("hello");
		userInput.add("daniel");
		userInput.add("can you help me with cooking");
		userInput.add("tell me the tools");
		userInput.add("hamburger");
		userInput.add("tools");
		runMainActivityWithTestInput(userInput);
		assertTrue(nlgResults.get(2).contains("what") || nlgResults.get(2).contains("What")
				&& nlgResults.get(3).contains("what") || nlgResults.get(3).contains("What") || nlgResults.get(3).contains("name")
				&& nlgResults.get(4).contains("hamburger")
				&& nlgResults.get(5).contains("knife"));
	}
	
	@Test
	public void ingstest2() {
		userInput.add("hello");
		userInput.add("daniel");
		userInput.add("can you help me with cooking");
		userInput.add("tell me the ingredients");
		userInput.add("hamburger");
		userInput.add("ingredients");
		runMainActivityWithTestInput(userInput);
		assertTrue(nlgResults.get(2).contains("what") || nlgResults.get(2).contains("What")
				&& nlgResults.get(3).contains("what") || nlgResults.get(3).contains("What") || nlgResults.get(3).contains("name")
				&& nlgResults.get(4).contains("hamburger")
				&& nlgResults.get(5).contains("lettuce"));
	}
	
	/**
	 * Tests if the creator is passed correctly
	 */
	@Test
	public void creatorTest() {
		userInput.add("hello");
		userInput.add("daniel");
		userInput.add("can you help me with cooking");
		userInput.add("tell me the creator of hamburger");
		runMainActivityWithTestInput(userInput);
		assertTrue(nlgResults.get(2).contains("what") || nlgResults.get(2).contains("What")
				&& nlgResults.get(3).contains("Daniel"));
		
	}
	
	@Test
	public void creatorTest2() {
		userInput.add("hello");
		userInput.add("daniel");
		userInput.add("can you help me with cooking");
		userInput.add("tell me the creator");
		userInput.add("hamburger");
		userInput.add("creator");
		runMainActivityWithTestInput(userInput);
		assertTrue(nlgResults.get(2).contains("what") || nlgResults.get(2).contains("What")
				&& nlgResults.get(3).contains("what") || nlgResults.get(3).contains("What") || nlgResults.get(3).contains("name")
				&& nlgResults.get(4).contains("hamburger")
				&& nlgResults.get(5).contains("Daniel"));
	}
	
	@Test
	public void rnfTest() {
		userInput.add("hello");
		userInput.add("daniel");
		userInput.add("can you help me with cooking");
		userInput.add("apple");
		userInput.add("yes");
		runMainActivityWithTestInput(userInput);
		assertTrue(nlgResults.get(2).contains("what") || nlgResults.get(2).contains("What")
				&& nlgResults.get(3).contains("apple")
				&& nlgResults.get(4).contains("recipe"));
	}
	
	@Test
	public void oneIngredientTest() {
		userInput.add("hello");
		userInput.add("daniel");
		userInput.add("can you help me with cooking");
		userInput.add("tell me the first ingredient of hamburger");
		userInput.add("tell me the next ingredient");
		runMainActivityWithTestInput(userInput);
		assertTrue(nlgResults.get(2).contains("what") || nlgResults.get(2).contains("What")
				&& nlgResults.get(3).contains("Ground beef")
				&& nlgResults.get(4).contains("Lettuce"));
	}
	
	@Test
	public void oneIngredientTest2() {
		userInput.add("hello");
		userInput.add("daniel");
		userInput.add("can you help me with cooking");
		userInput.add("tell me the first ingredient");
		userInput.add("hamburger");
		userInput.add("tell me the first ingredient");
		userInput.add("tell me the next ingredient");
		runMainActivityWithTestInput(userInput);
		assertTrue(nlgResults.get(2).contains("what") || nlgResults.get(2).contains("What")
				&& nlgResults.get(4).contains("hamburger")
				&& nlgResults.get(5).contains("Ground beef")
				&& nlgResults.get(6).contains("Lettuce"));

	}
	
	@Test
	public void countryTest() {
		userInput.add("hello");
		userInput.add("daniel");
		userInput.add("can you help me with cooking");
		userInput.add("tell me a recipe from the USA");
		runMainActivityWithTestInput(userInput);
		assertTrue(nlgResults.get(2).contains("what") || nlgResults.get(2).contains("What")
				&& nlgResults.get(3).contains("hamburger"));

	}
	
	@Test
	public void countryTest2() {
		userInput.add("hello");
		userInput.add("daniel");
		userInput.add("can you help me with cooking");
		userInput.add("tell me a recipe from britain");
		runMainActivityWithTestInput(userInput);
		assertTrue(nlgResults.get(2).contains("what") || nlgResults.get(2).contains("What")
				&& nlgResults.get(3).contains("no"));

	}
	
	@Test
	public void toolTest() {
		userInput.add("hello");
		userInput.add("daniel");
		userInput.add("can you help me with cooking");
		userInput.add("tell me a recipe with knife");
		runMainActivityWithTestInput(userInput);
		assertTrue(nlgResults.get(2).contains("what") || nlgResults.get(2).contains("What")
				&& nlgResults.get(3).contains("hamburger"));

	}
	
	@Test
	public void toolTest2() {
		userInput.add("hello");
		userInput.add("daniel");
		userInput.add("can you help me with cooking");
		userInput.add("tell me a recipe with oven");
		runMainActivityWithTestInput(userInput);
		assertTrue(nlgResults.get(2).contains("what") || nlgResults.get(2).contains("What")
				&& nlgResults.get(3).contains("no"));

	}

}
