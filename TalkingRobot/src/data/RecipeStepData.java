package data;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

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

/**
 * The constructor constructs the class with one parameter
 * @param descri description of a recipe step
 */
public RecipeStepData(String descri) {
	this.description = descri;
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
	this.description = newData.getDescription();
	
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

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	RecipeStepData other = (RecipeStepData) obj;
	if (description == null) {
		if (other.description != null)
			return false;
	} else if (!description.equals(other.description))
		return false;
	return true;
}



}