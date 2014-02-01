package dm;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Bettina Weller
 * @version1.0
 * This class tests the class User
 */
public class UserTest {
	private User user;
	
	@Before
	public void setUp() {
		this.user = new User();
	}
	
	/**
	 * currently 6 users exists. checks if 6 users were loaded
	 */
	@Test
	public void load() {
		ArrayList<User> load = new ArrayList<User>();
		load = user.loadUsers();
		assertEquals(6, load.size());
	}
	
	@After
	public void tearDown() {
		this.user = null;
	}

}
