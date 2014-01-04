package data;

import java.util.ArrayList;

import org.json.JSONObject;

public class RecipeStepData implements Data {

  private Integer stepID;

  private String description;

  private ArrayList<IngredientData> ingredients;

  private JSONObject stepJSON;

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