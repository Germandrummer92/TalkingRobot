package nlu;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Bettina Weller
 * @version 1.0
 * This class tests the class PhoenixAdapter.
 *
 */
public class PhoenixAdapterTest {
	private PhoenixAdapter phoenix;
	
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
		this.phoenix = new PhoenixAdapter();
	}
	
	/**
	 * checks the result of "For sandwiches you need toast and salad" for term.gra
	 */
	@Test
	public void term1() { 
		setInput("For sandwiches you need toast and salad");
		LinkedList <String> result = phoenix.operatePhoenix("run_parse_term", 0, 
				new File("resources/nlu/Phoenix/TalkingRobot/Term/"));
		LinkedList <String> compare = new LinkedList<String>();
		compare.add("for you need");
		compare.add("and");
		assertEquals(compare, result);
	}
	
	/**
	 * checks the result of "My very pretty name is Nicole" for term.gra
	 */
	@Test
	public void term2() { 
		setInput("My very pretty name is Nicole");
		LinkedList <String> result = phoenix.operatePhoenix("run_parse_term", 0, 
				new File("resources/nlu/Phoenix/TalkingRobot/Term/"));
		LinkedList <String> compare = new LinkedList<String>();
		compare.add("my name is");
		assertEquals(compare, result);
	}
	
	/**
	 * checks the result of "I want to go to the cantean" for possibleKw.gra
	 */
	@Test
	public void possibleKw1() { 
		setInput("I want to go to the cantean");
		LinkedList <String> result = phoenix.operatePhoenix("run_parse_possiblekw", 0,
				new File("resources/nlu/Phoenix/TalkingRobot/PossibleKw/"));
		LinkedList <String> compare = new LinkedList<String>();
		compare.add("i");
		compare.add("want");
		compare.add("to");
		compare.add("go");
		compare.add("the");
		assertEquals(compare, result);
	}
	
	/**
	 * checks the result of "that is al" for possibleKw.gra
	 */
	@Test
	public void possibleKw2() { 
		setInput("that is al");
		LinkedList <String> result = phoenix.operatePhoenix("run_parse_possiblekw", 0,
				new File("resources/nlu/Phoenix/TalkingRobot/PossibleKw/"));
		LinkedList <String> compare = new LinkedList<String>();
		compare.add("that");
		compare.add("is");
		assertEquals(compare, result);
	}
	
	/**
	 * checks the result of "Yes that is right" for approval.gra
	 */
	@Test
	public void approval1() { 
		setInput("Yes that is right");
		LinkedList <String> result = phoenix.operatePhoenix("run_parse_approval", 1,
				new File("resources/nlu/Phoenix/TalkingRobot/Approval/"));
		LinkedList <String> compare = new LinkedList<String>();
		compare.add("yes");
		assertEquals(compare, result);
	}
	
	/**
	 * checks the result of "It was the first option" for approval.gra
	 */
	@Test
	public void approval2() { 
		setInput("It was the first option");
		LinkedList <String> result = phoenix.operatePhoenix("run_parse_approval", 1,
				new File("resources/nlu/Phoenix/TalkingRobot/Approval/"));
		LinkedList <String> compare = new LinkedList<String>();
		compare.add("first");
		assertEquals(compare, result);
	}
	
	/**
	 * checks the result of "Could you recommend me something to eat at the canteen" for keyword.gra
	 */
	@Test
	public void keyword1() { 
		setInput("Could you recommend me something to eat at the canteen");
		LinkedList <String> result = phoenix.operatePhoenix("run_parse_keyword", 1,
				new File("resources/nlu/Phoenix/TalkingRobot/Keyword/"));
		LinkedList <String> compare = new LinkedList<String>();
		compare.add("recommendation");
		compare.add("canteen");
		assertEquals(compare, result);
	}
	
	/**
	 * checks the result of "Could you get me the knife from the cupboard" for keyword.gra
	 */
	@Test
	public void keyword2() { 
		setInput("Could you get me the knife from the cupboard");
		LinkedList <String> result = phoenix.operatePhoenix("run_parse_keyword", 1,
				new File("resources/nlu/Phoenix/TalkingRobot/Keyword/"));
		LinkedList <String> compare = new LinkedList<String>();
		compare.add("bring");
		compare.add("knife");
		compare.add("cupboard");
		assertEquals(compare, result);
	}
	
	/**
	 * checks the result of "Where is the canteen" for keyword.gra
	 */
	@Test
	public void keyword3() { 
		setInput("Where is the canteen");
		LinkedList <String> result = phoenix.operatePhoenix("run_parse_keyword", 1,
				new File("resources/nlu/Phoenix/TalkingRobot/Keyword/"));
		LinkedList <String> compare = new LinkedList<String>();
		compare.add("where is");
		compare.add("canteen");
		assertEquals(compare, result);
	}
	
	@After
	public void tearDown() {
		this.phoenix = null;
	}
	

}
