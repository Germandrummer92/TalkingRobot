/**
 * 
 */
package dm;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Daniel Draper
 * @version 1.0
 *
 */
public class StartDialogTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DialogTest.currentDialog = new StartDialog(new Session(new User(), new Robot("Superman", true)));
	}



}
