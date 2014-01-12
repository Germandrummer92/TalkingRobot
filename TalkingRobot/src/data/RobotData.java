package data;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.*;

/**
 * This class represents the data which should be stored for the robot.
 * 	@author Aleksandar Andonov
 *	@version 1.0
 * 
 */
public class RobotData implements Data {
	private int robotID;
	private String robotName;
	private boolean isInKitchen; 
	
	/**
	 * Creates a new RobotData object with the given name and information
	 * if the robot is in the kitchen.
	 * @param robotName the name of the robot
	 * @param isInKitchen true if the robot is in the kitchen
	 */
	public RobotData(String robotName, boolean isInKitchen) {
		this.isInKitchen = isInKitchen;
		this.robotName = robotName;
		robotID = nextID();
	}
	
	 /**
	   * Create the object directly from a jsonString
	   * @param jsonString
	   */
	public RobotData(String jsonString) {
		this.createFromJSONText(jsonString);
	 }
	
	@Override
	 /**
     * @see Data#generateJSON()
     */
	public String generateJSON() {
		Gson creator; 
		creator = new Gson();
		return creator.toJson(this);
	}
	
	@Override
	/**
	 * @see Data#createFromJSONText(String jsonString)
	 */
	public void createFromJSONText(String jsonString) {
		Gson creator; 
		creator = new Gson();
		RobotData newData = creator.fromJson(jsonString, this.getClass());
		robotID = newData.getRobotID();
		isInKitchen = newData.isInKitchen();
		robotName = newData.getRobotName();
	}
	
	@Override
	/**
	 * @see Data#writeFile()
	 */
	public void writeFile() {
		String pathname = "resources/files/RobotData/" + robotID + ".json";
		PrintWriter writer;
		try {
			writer = new PrintWriter(pathname, "UTF-8");
			writer.println(this.generateJSON());
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	/**
	 * @see Data#getJson()
	 */
	public JSONObject getJson() {
		JSONObject object = null;
		try {
			object = new JSONObject(this.generateJSON());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return object;
	}

	public int getRobotID() {
		return robotID;
	}

	public String getRobotName() {
		return robotName;
	}

	public boolean isInKitchen() {
		return isInKitchen;
	}

	public void setRobotID(int robotID) {
		this.robotID = robotID;
	}

	public void setRobotName(String robotName) {
		this.robotName = robotName;
	}

	public void setInKitchen(boolean isInKitchen) {
		this.isInKitchen = isInKitchen;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isInKitchen ? 1231 : 1237);
		result = prime * result + robotID;
		result = prime * result
				+ ((robotName == null) ? 0 : robotName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RobotData other = (RobotData) obj;
		if (isInKitchen != other.isInKitchen)
			return false;
		if (robotID != other.robotID)
			return false;
		if (robotName == null) {
			if (other.robotName != null)
				return false;
		} else if (!robotName.equals(other.robotName))
			return false;
		return true;
	}

	/**
	 * @return the next unique ID
	 */
	private int nextID() {
		File f = new File("resources/files/RobotData/");
		return f.listFiles().length;
	}
}