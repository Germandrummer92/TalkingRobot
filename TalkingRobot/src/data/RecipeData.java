package data;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

/**
 * 
 * @author Luiz Henrique. Soares Silva
 * @version 1.0
 * This class describes and implements a Recipe Data.
 *
 */
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
		Gson creator = new Gson();
		return creator.toJson(this);
	}

	@Override
	public void createFromJSONText(String jsonString) {
		Gson creator;
		creator =  new Gson();
		RecipeData newData = creator.fromJson(jsonString, this.getClass());
		this.recipeID = newData.getRecipeID();
		this.recipeName = newData.getRecipeName();
		this.numOfSteps = newData.getNumOfSteps();
		this.ingredients = newData.getIngredients();
		this.steps = newData.getSteps();
		this.tools = newData.getTools();
		this.creator = newData.getCreator();
		this.originalCountry = newData.getOriginalCountry();
		this.mealCategory = newData.getMealCategory();
	}
	
	@Override
	public void writeFile() {
		String pathname = "resources/files/KeywordData/" + recipeID + ".json";
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
	
	public Integer getRecipeID() {
		return recipeID;
	}
	
	public void setRecipeID(Integer recipeID) {
		this.recipeID = recipeID;
	}
	
	public String getRecipeName() {
		return recipeName;
	}
	
	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}
	
	public Integer getNumOfSteps() {
		return numOfSteps;
	}
	
	public void setNumOfSteps(Integer numOfSteps) {
		this.numOfSteps = numOfSteps;
	}
	
	public ArrayList<IngredientData> getIngredients() {
		return ingredients;
	}
	
	public void setIngredients(ArrayList<IngredientData> ingredients) {
		this.ingredients = ingredients;
	}
	
	public ArrayList<RecipeStepData> getSteps() {
		return steps;
	}
	
	public void setSteps(ArrayList<RecipeStepData> steps) {
		this.steps = steps;
	}
	
	public ArrayList<ToolData> getTools() {
		return tools;
	}
	
	public void setTools(ArrayList<ToolData> tools) {
		this.tools = tools;
	}
	
	public UserData getCreator() {
		return creator;
	}
	
	public void setCreator(UserData creator) {
		this.creator = creator;
	}
	
	public String getOriginalCountry() {
		return originalCountry;
	}
	
	public void setOriginalCountry(String originalCountry) {
		this.originalCountry = originalCountry;
	}
	
	public JSONObject getRecipeJSON() {
		return recipeJSON;
	}
	
	public void setRecipeJSON(JSONObject recipeJSON) {
		this.recipeJSON = recipeJSON;
	}
	
	public MealCategoryData getMealCategory() {
		return mealCategory;
	}
	
	public void setMealCategory(MealCategoryData mealCategory) {
		this.mealCategory = mealCategory;
	}

}