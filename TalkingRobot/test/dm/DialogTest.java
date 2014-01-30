/**
 * 
 */
package dm;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Daniel Draper
 * @version 1.0
 * Tests the different DialogClasses
 */
public class DialogTest {

	static Dialog currentDialog;
	static Dictionary dictionary;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		dictionary = new Dictionary();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void jumpTest() {
		for (Keyword kw : dictionary.getKeywordList()) {
			ArrayList<Keyword> kwList = new ArrayList<Keyword>();
			kwList.add(kw);
			try {
				currentDialog.updateState(kwList, new ArrayList<String>(), new ArrayList<String>());
			} catch (WrongStateClassException e) {
				assertTrue(currentDialog.getCurrentDialogState().getCurrentState().equals(kw.getReference().getCurrentState()));
			}
			assertTrue(currentDialog.getCurrentDialogState().getCurrentState().equals(kw.getReference().getCurrentState()));
		}
	}

}
