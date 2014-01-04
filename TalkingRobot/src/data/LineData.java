package data;

import java.util.ArrayList;

import org.json.JSONObject;

public class LineData implements Data {

  private Integer lineID;

  private String lineName;

  private ArrayList<MealData> todayMeals;

  private JSONObject lineJSON;

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