package data;

import org.json.JSONObject;

/*
 * @author Xizhe Lian
 * @version 1.0
 * This class represents the data for an ingredient 
 */
public class IngredientData implements Data {

  private Integer ingredientID;

  private String ingredientName;

  private JSONObject ingredientJSON;

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