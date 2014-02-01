package dm;

import java.util.ArrayList;

import data.UserData;

/**
 * 
 * @author Daniel Draper
 * @version 1.1
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
   * Creates a new User from already saved UserData. Only accessible, by loadUsers().
   * @param d the UserData of the new User
   */
  protected User(UserData d) {
	  userData = d;
  }
  
  /**
 * Creates a new User without any saved Data. Needed to create new empty session.
 */
public User() {
	
}

/**
   * 
   * @return the user's data
   */
  public UserData getUserData() {
  		return userData;
  }
  
  /**
   * Loads the List of current Users known to the system from files. 
   * @return the List of current Users known to the system.
   */
  public static ArrayList<User> loadUsers() {
	  ArrayList<User> res = new ArrayList<User>();
	  for (UserData d : UserData.loadData()) {
		  res.add(new User(d));
	  }
	  return res;
  }

/* (non-Javadoc)
 * @see java.lang.Object#hashCode()
 */
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((userData == null) ? 0 : userData.hashCode());
	return result;
}

/* (non-Javadoc)
 * @see java.lang.Object#equals(java.lang.Object)
 */
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	User other = (User) obj;
	if (userData == null) {
		if (other.userData != null)
			return false;
	} else if (!userData.equals(other.userData))
		return false;
	return true;
}

  
}