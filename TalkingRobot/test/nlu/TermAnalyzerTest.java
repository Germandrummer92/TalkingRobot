package nlu;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Bettina Weller
 * @version 1.0
 * This class tests TermAnalyzer for different inputs
 *
 */
public class TermAnalyzerTest {
	private TermAnalyzer term = new TermAnalyzer();
	
	private void setInput(String input) {
  		File file = new File("resources/nlu/Phoenix/TalkingRobot/input");
  		PrintWriter writer;
  		try {
  			writer = new PrintWriter(file, "UTF-8");
  			writer.println(input);
  			writer.close();
  		} catch (FileNotFoundException | UnsupportedEncodingException e) {
  			e.printStackTrace();
  		}
	}
	
	@Before
	public void setUp() {
		this.term = new TermAnalyzer();
	}

	/**
	 * checks the result for "for sandwiches you need toast and salad"
	 */
	@Test
	public void and() { 
		setInput("for sandwiches you need toast and salad");
		List <String> result = term.analyze("for sandwiches you need toast and salad");
		List <String> compare = new LinkedList<String>();
		compare.add("toast");
		compare.add("salad");
		compare.add("for sandwiches you need toast and salad");
		assertEquals(compare, result);
	}
	
	/**
	 * checks the result for "you need toast and salad for sandwiches"
	 */
	@Test
	public void and2() { 
		setInput("you need toast and salad for sandwiches");
		List <String> result = term.analyze("you need toast and salad for sandwiches");
		List <String> compare = new LinkedList<String>();
		compare.add("toast");
		compare.add("salad");
		compare.add("you need toast and salad for sandwiches");
		assertEquals(compare, result);
	}
	
	
	/**
	 * checks the result for "salad is needed for sandwiches"
	 */
	@Test
	public void test2() { 
		setInput("salad is needed for sandwiches");
		List <String> result = term.analyze("salad is needed for sandwiches");
		List <String> compare = new LinkedList<String>();
		compare.add("salad");
		compare.add("salad is needed for sandwiches");
		assertEquals(compare, result);
	}
	
	/**
	 * checks the result for "salad"
	 */
	@Test
	public void simpleInput() { 
		setInput("salad");
		List <String> result = term.analyze("salad");
		List <String> compare = new LinkedList<String>();
		compare.add("salad");
		compare.add("salad");
		assertEquals(compare, result);
	}
	
	/**
	 * checks the result for "for spaghetti bolognese tomatoes are needed"
	 */
	@Test
	public void test() { 
		setInput("for spaghetti bolognese tomatoes are needed");
		List <String> result = term.analyze("for spaghetti bolognese tomatoes are needed");
		List <String> compare = new LinkedList<String>();
		compare.add("tomatoes");
		compare.add("for spaghetti bolognese tomatoes are needed");
		assertEquals(compare, result);
	}
	
	
	/**
	 * checks the result for "my very pretty name is nicole"
	 */
	@Test
	public void name() { 
		setInput("my very pretty name is nicole");
		List <String> result = term.analyze("my very pretty name is nicole");
		List <String> compare = new LinkedList<String>();
		compare.add("nicole");
		compare.add("my very pretty name is nicole");
		assertEquals(compare, result);
	}
	
	/**
	 * checks the result for "the recipes name is pasta"
	 */
	@Test
	public void name2() { 
		setInput("the recipe name is pasta");
		List <String> result = term.analyze("the recipe name is pasta");
		List <String> compare = new LinkedList<String>();
		compare.add("pasta");
		compare.add("the recipe name is pasta");
		assertEquals(compare, result);
	}
	
	/**
	 * checks the result for "the recipe is called pasta"
	 */
	@Test
	public void name3() { 
		setInput("the recipe is called pasta");
		List <String> result = term.analyze("the recipe is called pasta");
		List <String> compare = new LinkedList<String>();
		compare.add("pasta");
		compare.add("the recipe is called pasta");
		assertEquals(compare, result);
	}
	
	/**
	 * checks the result for "the next tool is knife"
	 */
	@Test
	public void testNext() { 
		setInput("the next tool is knife");
		List <String> result = term.analyze("the next tool is knife");
		List <String> compare = new LinkedList<String>();
		compare.add("knife");
		compare.add("the next tool is knife");
		assertEquals(compare, result);
	}
	
	/**
	 * checks the result for "can you bring me a knife"
	 */
	@Test
	public void testBring() { 
		setInput("can you bring me a knife");
		List <String> result = term.analyze("can you bring me a knife");
		List <String> compare = new LinkedList<String>();
		compare.add("knife");
		compare.add("can you bring me a knife");
		assertEquals(compare, result);
	}
	
	/**
	 * checks the result for "we need tomatos"
	 */
	@Test
	public void testNeed() { 
		setInput("we need tomatos");
		List <String> result = term.analyze("we need tomatos");
		List <String> compare = new LinkedList<String>();
		compare.add("tomatos");
		compare.add("we need tomatos");
		assertEquals(compare, result);
	}
	
	@After
	public void tearDown() {
		this.term = null;
	}
}
