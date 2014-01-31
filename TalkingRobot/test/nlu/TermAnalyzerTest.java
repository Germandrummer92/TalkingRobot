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
	 * checks the result for "For sandwiches you need toast and salad"
	 */
	@Test
	public void and() { 
		setInput("For sandwiches you need toast and salad");
		List <String> result = term.analyze("For sandwiches you need toast and salad");
		List <String> compare = new LinkedList<String>();
		compare.add("toast");
		compare.add("salad");
		assertEquals(compare, result);
	}
	
	/**
	 * checks the result for "My very pretty name is Nicole"
	 */
	@Test
	public void name() { 
		setInput("My very pretty name is Nicole");
		List <String> result = term.analyze("My very pretty name is Nicole");
		List <String> compare = new LinkedList<String>();
		compare.add("nicole");
		assertEquals(compare, result);
	}
	
	@After
	public void tearDown() {
		this.term = null;
	}
}
