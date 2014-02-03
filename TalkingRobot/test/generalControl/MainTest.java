package generalControl;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Bettina Weller
 * @version 1.0
 * This class tests the class Main.
 *
 */
public class MainTest {
	private Main main;
	
	@Before
	public void setUp() {
		this.main = null;
	}
	
	/**
	 * test the singleton nature of main by creating the first instance
	 */
	@Test
	public void test() {
		main = Main.giveMain();
		assertTrue(main != null);
	}
	
	/**
	 * test the singleton nature of main by trying to create a second instance
	 */
	@Test
	public void test2() {
		main = Main.giveMain();
		Main main2 = Main.giveMain();
		assertTrue(main.equals(main2));
	}
	
	@After
	public void tearDown() {
		this.main = null;
	}
	

}
