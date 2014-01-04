package data;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.*;


public class RobotData implements Data {
	private int RobotID;
	private String robotName;
	private boolean isInKitchen; 
	
	public RobotData(int robotID, String robotName, boolean isInKitchen) {
		this.RobotID = robotID;
		this.isInKitchen = isInKitchen;
		this.robotName = robotName;
	}
	
	public RobotData() {
		
	}
	
	@Override
	public String generateJSON() {
		Gson creator; 
		creator = new Gson();
		return creator.toJson(this);
	}
	
	@Override
	public void createFromJSONText(String text) {
		Gson creator; 
		creator = new Gson();
		RobotData newData = creator.fromJson(text, this.getClass());
		RobotID = newData.getRobotID();
		isInKitchen = newData.isInKitchen();
		robotName = newData.getRobotName();
	}
	
	@Override
	public void writeFile() {
		String pathname = "JsonResources/RobotData/" + RobotID + ".json";
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
		return RobotID;
	}

	public String getRobotName() {
		return robotName;
	}

	public boolean isInKitchen() {
		return isInKitchen;
	}

	public void setRobotID(int robotID) {
		RobotID = robotID;
	}

	public void setRobotName(String robotName) {
		this.robotName = robotName;
	}

	public void setInKitchen(boolean isInKitchen) {
		this.isInKitchen = isInKitchen;
	}
	
	
}