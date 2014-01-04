package data;

import java.util.ArrayList;

import org.json.JSONObject;

public class RecipeData implements Data {

  private Integer recipeID;

  private String recipeName;

  private Integer numOfSteps;

  private ArrayList<IngredientData> ingredients;

  private ArrayList<RecipeStepData> steps;

  private ArrayList<ToolData> tools;

  private UserData creator;

  private String originalCountry;

  private JSONObject recipeJSON;

  private MealCategoryData mealCategory;

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