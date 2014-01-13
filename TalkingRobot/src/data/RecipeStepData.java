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
* This class represents a data of a step in a recipe.
* @author Xizhe Lian
* @version 1.0
*/
public class RecipeStepData implements Data {

  private Integer stepID;

  private String description;

  private ArrayList<IngredientData> ingredients;

/**
 * The constructor constructs the class with one parameter
 * @param descri description of a recipe step
 */
public RecipeStepData(String descri) {
	this.stepID = nextID();
	this.description = descri;
	this.ingredients = new ArrayList<IngredientData>();
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
 * @see Data#createFromJSONText(String jsonString)
 */
public void createFromJSONText(String jsonString) {
	Gson creator; 
	creator = new Gson();
	RecipeStepData newData = creator.fromJson(jsonString, this.getClass());
	this.stepID = newData.getStepID();
	this.description = newData.getDescription();
	this.ingredients = newData.getIngredients();
	
}

@Override
/**
 * @see Data#writeFile()
 */
public void writeFile() {
	String pathname = "resources/files/RecipeStepData/" + stepID + ".json";
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
 * @see Data#getJson()
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

/**
 * A help method to get the unique ID of a recipe step Data
 * @return the last ID in the list of recipe step Data
 */
private int nextID() {
	File f = new File("resources/files/RecipeStepData/");
	return f.listFiles().length;
}

/**
 * @return step ID
 */
public Integer getStepID() {
	return stepID;
}

/**
 * @param stepID the ID of this step
 */
public void setStepID(Integer stepID) {
	this.stepID = stepID;
}

/**
 * @return description of this step
 */
public String getDescription() {
	return description;
}

/**
 * @param description the description of this step
 */
public void setDescription(String description) {
	this.description = description;
}

/**
 * @return a list of ingredients needed in this step
 */
public ArrayList<IngredientData> getIngredients() {
	return ingredients;
}

/**
 * @param ingredients a list of ingredients needed in this step
 */
public void setIngredients(ArrayList<IngredientData> ingredients) {
	this.ingredients = ingredients;
}

}