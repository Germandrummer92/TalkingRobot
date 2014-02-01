/**
 * 
 */
package dm;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Daniel Draper
 * @author Bettina Weller
 * @version 1.0
 * This class tests the Dictionary class.
 */
public class DictionaryTest {
	private Dictionary dictionary;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	
	@Before
	public void setUp() {
		this.dictionary = new Dictionary();
	}
	
	/**
	 * searches for the keywords "hi", "name", which should exist
	 */
	@Test
	public void search1() {
		ArrayList<String> input = new ArrayList<String>();
		input.add("hi");
		input.add("name");
		ArrayList<Keyword> res = (ArrayList<Keyword>) dictionary.findKeywords(input);
		ArrayList<String> comp = new ArrayList<String>();
		for(int i = 0; i < res.size(); i++) {
			comp.add(res.get(i).getWord());
		}
		assertEquals(input, comp);
	}
	
	/**
	 * searches for the keyword "monkey", which should not exist
	 */
	@Test
	public void search2() {
		ArrayList<String> input = new ArrayList<String>();
		input.add("monkey");
		ArrayList<Keyword> res = (ArrayList<Keyword>) dictionary.findKeywords(input);
		assertEquals(0, res.size());
	}

	
	@After
	public void tearDown() {
		this.dictionary = null;
	}

}
