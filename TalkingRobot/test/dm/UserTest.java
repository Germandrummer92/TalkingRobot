package dm;

import static org.junit.Assert.assertEquals;

import java.io.File;
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
	 * checks if the number of loaded users is the number of existing users
	 */
	@Test
	public void load() {
		ArrayList<User> load = new ArrayList<User>();
		load = User.loadUsers();
		File directory = new File("resources/files/UserData/");
		assertEquals(directory.list().length, load.size());
	}
	
	@After
	public void tearDown() {
		this.user = null;
	}

}
