package data;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

public class ToolData implements Data {

  private int toolID;

  private String toolName;

  private String location;

  private ArrayList <RecipeData> recipes;
  
  public ToolData(String toolName, String location, ArrayList<RecipeData> recipes) {
	  this.toolName = toolName;
	  this.location = location;
	  this.recipes = recipes;
  }

	@Override
	public String generateJSON() {
		Gson creator; 
		creator = new Gson();
		return creator.toJson(this);
	}
	
	@Override
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




}