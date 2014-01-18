package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import data.RecipeData;

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
   * @param arrayList the recipes where this tool is used
   */
  public ToolData(String toolName, String location, ArrayList<RecipeData> arrayList) {
	  this.toolName = toolName;
	  this.location = location;
	  this.recipes = arrayList;
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
		String pathname = "resources/files/ToolData/" + toolID + ".json";
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



@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((location == null) ? 0 : location.hashCode());
	result = prime * result + ((recipes == null) ? 0 : recipes.hashCode());
	result = prime * result + toolID;
	result = prime * result + ((toolName == null) ? 0 : toolName.hashCode());
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
	ToolData other = (ToolData) obj;
	if (location == null) {
		if (other.location != null)
			return false;
	} else if (!location.equals(other.location))
		return false;
	if (recipes == null) {
		if (other.recipes != null)
			return false;
	} else if (!recipes.equals(other.recipes))
		return false;
	if (toolID != other.toolID)
		return false;
	if (toolName == null) {
		if (other.toolName != null)
			return false;
	} else if (!toolName.equals(other.toolName))
		return false;
	return true;
}

/**
 * This static method returns a List of all existing ToolData files.
 * @return a list of all existing ToolData files
 */
public static ArrayList <ToolData> loadData() {
	File load = new File("resources/files/ToolData/");
	Gson loader = new Gson();
	ArrayList <ToolData> res = new ArrayList <ToolData>();
	for (File f : load.listFiles()) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	  	ToolData read = null;
	  		try {
				read = loader.fromJson(br.readLine(), ToolData.class);
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	  	res.add(read);
	}
	return res;
}

/**
 * @return the next unique ID
 */
private int nextID() {
	File f = new File("resources/files/ToolData/");
	return f.listFiles().length;
}
}