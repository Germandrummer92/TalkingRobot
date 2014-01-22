package dm;
import java.util.ArrayList;

import data.RobotData;


/**
 * Represents the robot the system is currently running on.
 * @author Daniel Draper
 * @version 1.0
 *
 */
public class Robot {

  public RobotData robotData;

  /**
   * Creates a new Robot and saves its data.
   * @param robotName
   * @param isInKitchen if the Robot is currently situated in the Kitchen
   */
  public Robot(String robotName, Boolean isInKitchen) {
	  robotData = new RobotData(robotName, isInKitchen);
	  robotData.writeFile();
  }
  
  /**
   * Creates a new Robot from an already saved robotdata file.
   * @param robotData
   */
  private Robot(RobotData robotData) {
	  this.robotData = robotData;
  }
  /**
   * 
   * @return the robot's data
   */
  public RobotData getRobotData() {
	  return robotData;
  }
  
  /**
   * Loads all the robot data files saved on the harddrive, and creates a list of robot object with those datas.
   * @return the List of Robots known to the system.
   */
  public static ArrayList<Robot> loadRobots() {
	  ArrayList<Robot> res = new ArrayList<Robot>();
	  for (RobotData d : RobotData.loadData()) {
		  res.add(new Robot(d));
	  }
	  return res;
  }

}