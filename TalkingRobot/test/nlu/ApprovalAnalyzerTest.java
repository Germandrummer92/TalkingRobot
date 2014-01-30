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

public class ApprovalAnalyzerTest {
	private ApprovalAnalyzer approval;

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
		this.approval = new ApprovalAnalyzer();
	}
	
	/**
	 * checks the result for "Yes that is right"
	 */
	@Test
	public void yes() {
		setInput("Yes that is right");
		LinkedList<String> result = (LinkedList<String>) approval.analyze("Yes that is right");
		LinkedList<String> compare = new LinkedList<String>();
		compare.add("yes");
		assertEquals(compare, result);
	}
	
	/**
	 * checks the result for "It was the first option"
	 */
	@Test
	public void approval2() { 
		setInput("It was the first option");
		LinkedList <String> result = (LinkedList<String>) approval.analyze("Yes that is right");
		LinkedList <String> compare = new LinkedList<String>();
		compare.add("first");
		assertEquals(compare, result);
	}
	
	@After
	public void tearDown() {
		this.approval = null;
	}
}
