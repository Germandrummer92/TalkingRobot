package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

/**
 * This class represents the data which the system should save about every tool.
 * @author Aleksandar Andonov
 * @version 1.0
 */
public class ToolData implements Data {

  private int toolID;

  private String toolName;

  private String location;

  private ArrayList <RecipeData> recipes; //the recipes where this tool is used
  
  /**
   * Creates a new ToolData object with the given name abd location.
   * @param toolName the name of the tool
   * @param location the locaton of the tool
   * @param recipes the recipes where this tool is used
   */
  public ToolData(String toolName, String location, ArrayList<RecipeData> recipes) {
	  this.toolName = toolName;
	  this.location = location;
	  this.recipes = recipes;
	  toolID = nextID();

  }
  
  /**
   * Create the object directly from a jsonString
   * @param jsonString
   */
  public ToolData(String jsonString) {
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
     * @see Data#createFromJSONText()
     */
	public void createFromJSONText(String jsonString) {
		Gson creator; 
		creator = new Gson();
		ToolData newData = creator.fromJson(jsonString, this.getClass());
		toolID = newData.getToolID();
		toolName = newData.getToolName();
		location = newData.getLocation();
		recipes = newData.getRecipes();
	}
	
	@Override
	/**
     * @see Data#writeFile()
     */
	public void writeFile() {
		String pathname = "resources/files/RobotData/" + toolID + ".json";
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
     * @see Data#getJSON()
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

public Integer getToolID() {
	return toolID;
}

public void setToolID(Integer toolID) {
	this.toolID = toolID;
}

public String getToolName() {
	return toolName;
}

public void setToolName(String toolName) {
	this.toolName = toolName;
}

public String getLocation() {
	return location;
}

public void setLocation(String location) {
	this.location = location;
}

public ArrayList<RecipeData> getRecipes() {
	return recipes;
}

public void setRecipes(ArrayList<RecipeData> recipes) {
	this.recipes = recipes;
}

/**
 * @return the next unique ID
 */
private int nextID() {
	File f = new File("resources/files/UserData/");
	return f.listFiles().length;
}
}