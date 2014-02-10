package nlu;

import static org.junit.Assert.assertTrue;

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
 * This class tests the class PossibleKeywordAnalyzer.
 *
 */
public class PossibleKeywordAnalyzerTest {
	private PossibleKeywordAnalyzer possibleKw;
	
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
		this.possibleKw = new PossibleKeywordAnalyzer();
	}
	
	/**
	 * checks the result for "I want to go to the cantean"
	 */
	@Test
	public void canteen() { 
		setInput("I want to go to the cantean");
		List<String> result = possibleKw.analyze("i want to go to the cantean");	
		LinkedList <String> compare = new LinkedList<String>();
		compare.add("cantean;canteen;1");
		assertTrue(result.containsAll(compare));
	}
	
	/**
	 * checks the result for "that is al"
	 */
	@Test
	public void finish() { 
		setInput("that is al");
		List<String> result = possibleKw.analyze("that is al");
		
		List <String> compare = new LinkedList<String>();
		compare.add("that is al;that is all;1");
		assertTrue(result.containsAll(compare));
	}
	
	@After
	public void tearDown() {
		this.possibleKw = null;
	}

}
