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

public class KeywordAnalyzerTest {
	private KeywordAnalyzer keyword;
	
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
		this.keyword = new KeywordAnalyzer();
	}
	
	/**
	 * checks the result for "Could you recommend me something to eat at the canteen"
	 */
	@Test
	public void canteenRecommendation() { 
		setInput("Could you recommend me something to eat at the canteen");
		LinkedList <String> result = (LinkedList<String>) keyword.analyze("Could you recommend me something to eat at the canteen");
		LinkedList <String> compare = new LinkedList<String>();
		compare.add("recommendation");
		compare.add("canteen");
		assertEquals(compare, result);
	}
	
	/**
	 * checks the result for "Could you get me the knife from the cupboard"
	 */
	@Test
	public void keyword2() { 
		setInput("Could you get me the knife from the cupboard");
		LinkedList <String> result = (LinkedList<String>) keyword.analyze("Could you get me the knife from the cupboard");
		LinkedList <String> compare = new LinkedList<String>();
		compare.add("bring");
		compare.add("knife");
		compare.add("cupboard");
		assertEquals(compare, result);
	}

	@After
	public void tearDown() {
		this.keyword = null;
	}
}
