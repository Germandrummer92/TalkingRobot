package dm;

import data.UserData;

/**
 * 
 * @author Daniel Draper
 * @version 1.0
 * This class represents a user of the system.
 */
public class User {

  private UserData userData;
  
  /**
   * Creates a new User and saves it in a file.
   * @param userName the user Name of the new User
   * @param isStudent if the new User is a student
   */
  public User(String userName, Boolean isStudent) {
	  userData = new UserData(userName, isStudent);
	  userData.writeFile();
  }

  /**
   * 
   * @return the user's data
   */
  public UserData getUserData() {
  		return userData;
  }

}