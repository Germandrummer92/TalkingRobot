package data;

import org.json.JSONObject;

public class MealData implements Data {

  private Integer mealID;

  private String mealName;

  private MealCategoryData mealCategory;

  private Float price;

  private JSONObject mealJSON;

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