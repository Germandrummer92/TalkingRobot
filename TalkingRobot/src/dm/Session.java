package dm;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Represents a started Session of a User with the robot.
 * @author Daniel Draper
 * @version 1.0
 *
 */
public class Session {

  private User currentUser;

  private Robot currentRobot;

  private Date date;

  private SimpleDateFormat dateFormat;

  private String currentLocation;
  
  /**
   * Creates a new Session, with the specified parameters.
   * @param currentUser
   * @param currentRobot
   * @param currentLocation
   */
  public Session(User currentUser, Robot currentRobot) {
	  this.currentUser = currentUser;
	  this.currentRobot = currentRobot;
	  if (currentRobot.getRobotData().isInKitchen()) {
		  currentLocation = "Kitchen";
	  }
	  else {
		  currentLocation = "Canteen";
	  }
	  date = new Date();
	  dateFormat = new SimpleDateFormat();
  }

  /**
   * 
   * @return the current User
   */
  public User getCurrentUser() {
	  return currentUser;
  }

  /**
   * 
   * @return the current Robot
   */
  public Robot getCurrentRobot() {
  	return currentRobot;
  }

  /**
   * 
   * @return the current Date formatted
   */
  public String getCurrentDateFormatted() {
  		return dateFormat.format(date);
  }

  /**
   * 
   * @return the current Location
   */
  public String getCurrentLocation() {
	  return currentLocation;
  }

  /**
   * Creates a new User and saves it.
   * @param userName the User's name
   * @param employeeStatus if the User is a student
   * @return the User created
   */
  private User createNewUser(String userName, Boolean employeeStatus) {
  		return new User(userName, employeeStatus);
  }

}