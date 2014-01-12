package data;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.*;

/**
 * 
 * @author Bettina Weller
 * @version 1.0
 * This class represents the data about a line at a canteen.
 * @see Data
 */

public class LineData implements Data {

  private Integer lineID;

  private String lineName;

  private ArrayList<MealData> todayMeals;

  private JSONObject lineJSON;


  /**
   * Creates a new LineData object.
   * @param lineID the ID of the relevant line. 
   * @param lineName the name of the line.
   * @param todayMeals the meals offered at that day at the relevant line.
   */
  public LineData(Integer lineID, String lineName, ArrayList<MealData> todayMeals) {
  	this.lineID = lineID;
  	this.lineName = lineName;
  	this.todayMeals = todayMeals;
  }
  
  /**
   * @return lineID
   */
  public Integer getLineID() {
  	return lineID;
  }
  
  /**
   * @return lineName
   */
  public String getLineName() {
  	return lineName;
  }
  
  /**
   * @return todayMeals
   */
  public ArrayList<MealData> getTodayMeals() {
  	return todayMeals;
  }
  
  /**
   * @param lineID the lineID to set
   */
  public void setLineID(Integer lineID) {
  	this.lineID = lineID;
  }
  
  /**
   * @param lineName the lineName to set
   */
  public void setLineName(String lineName) {
  	this.lineName = lineName;
  }
  
  /**
   * @param todayMeals the todayMeals to set
   */
  public void setTodayMeals(ArrayList<MealData> todayMeals) {
  	this.todayMeals = todayMeals;
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
	LineData newData = creator.fromJson(jsonString, this.getClass());
	lineID = newData.getLineID();
	lineName = newData.getLineName();
	todayMeals = newData.getTodayMeals();
}

@Override
/**
 * @see Data#writeFile()
 */
public void writeFile() {
	String pathname = "resources/files/LineData/" + lineID + ".json";
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

/**
         * @return the next unique ID
         */
        private int nextID() {
                File f = new File("resources/files/LineData/");
                return f.listFiles().length;
        }

}
