package data;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

/**
 * @author Xizhe Lian
 * @version 1.0
 * This class represents the data for an ingredient 
 */
public class IngredientData implements Data {

  private Integer ingredientID;

  private String ingredientName;

  private JSONObject ingredientJSON;

/**
 * constructs an ingredient  
 * @param id holds the ID of ingredient
 * @param name holds the name of ingredient
 */
public IngredientData(int id, String name) {
	this.ingredientID = id;
	this.ingredientName = name;
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
    IngredientData newData = creator.fromJson(jsonString, this.getClass());
    this.ingredientID = newData.getIngredientID();
    this.ingredientName = newData.getIngredientName();
	
}

@Override
/**
 * @see Data#writeFile()
 */
public void writeFile() {
	String pathname = "resources/files/IngredientData/" + ingredientID + ".json";
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
 * to get ID of the ingredient
 * @return ingredient's ID
 */
public Integer getIngredientID() {
	return ingredientID;
}

/**
 * to get ingredient name
 * @return ingredient's name
 */
public String getIngredientName() {
	return ingredientName;
}

/**
 * to get JSON data of the ingredient
 * @return JSON data of the ingredient
 */
public JSONObject getIngredientJSON() {
	return ingredientJSON;
}


}