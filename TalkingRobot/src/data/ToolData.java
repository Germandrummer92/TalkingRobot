package data;

import java.util.ArrayList;

import org.json.JSONObject;

public class ToolData implements Data {

  private Integer toolID;

  private String toolName;

  private String location;

  private ArrayList <RecipeData> recipes;

  private JSONObject toolJSON;

@Override
public String generateJSON() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void createFromJSONText(String jsonString) {
	// TODO Auto-generated method stub
	
}

@Override
public void writeFile() {
	// TODO Auto-generated method stub
	
}

@Override
public JSONObject getJson() {
	// TODO Auto-generated method stub
	return null;
}

}