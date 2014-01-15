package data;

import java.io.File;
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

/**
 * constructs an ingredient  
 * @param name holds the name of ingredient
 */
public IngredientData(String name) {
	this.ingredientID = nextID();
	this.ingredientName = name;
}

/**
 *  Create the object directly from a jsonString
 * @param jsonString the json String
 * @param constNum to differ from constructor one, type any int
 */
public IngredientData(String jsonString, int constNum) {
	this.createFromJSONText(jsonString);
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
 * A help method to get unique ID of the ingredient
 * @return the unique ID of the ingredient
 */
private int nextID() {
	File f = new File("resources/files/IngredientData/");
	return f.listFiles().length;
}

public void setIngredientID(Integer ingredientID) {
	this.ingredientID = ingredientID;
}

public void setIngredientName(String ingredientName) {
	this.ingredientName = ingredientName;
}
/*
@Override
public boolean equals(Object obj) {
	if (obj == null) {
		return false;
	}
	if (this == obj) {
		return true;
	}
	 
	if (obj.getClass() != this.getClass()) {
		return false;
	}
	
	IngredientData other = (IngredientData) obj;
	
	if ((this.ingredientID == other.getIngredientID())
			&& (this.ingredientName == other.getIngredientName())) {
		return true;
	} else return false;
}
*/

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	IngredientData other = (IngredientData) obj;
	if (ingredientID == null) {
		if (other.ingredientID != null)
			return false;
	} else if (!ingredientID.equals(other.ingredientID))
		return false;
	if (ingredientName == null) {
		if (other.ingredientName != null)
			return false;
	} else if (!ingredientName.equals(other.ingredientName))
		return false;
	return true;
}




}