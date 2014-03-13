package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * @author Xizhe Lian
 * @version 1.1
 * This class represents the data for an ingredient 
 */
public class IngredientData implements Data {

  private Integer ingredientID;

  private String ingredientName;
  
  private String ingredientLocation;

/**
 * constructs an ingredient  
 * @param name holds the name of ingredient
 */
public IngredientData(String name, String location) {
	this.ingredientID = nextID();
	this.ingredientName = name;
	this.setIngredientLocation(location);
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
    this.ingredientLocation = newData.getIngredientLocation();
	
}

/**
 * @return the Ingredient's ID
 */
public Integer getIngredientID() {
	return ingredientID;
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
 * to get ingredient name
 * @return ingredient's name
 */
public String getIngredientName() {
	return ingredientName;
}


/**
 * 
 */
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




/**
 * This static method returns a List of all existing IngredientData files.
 * @return a list of all existing IngredientData files
 */
public static ArrayList <IngredientData> loadData() {
	File load = new File("resources/files/IngredientData/");
	Gson loader = new Gson();
	ArrayList <IngredientData> res = new ArrayList <IngredientData>();
	for (File f : load.listFiles()) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	  	IngredientData read = null;
	  	try {
			read = loader.fromJson(br.readLine(), IngredientData.class);
			res.add(read);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	  	finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	return res;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	IngredientData other = (IngredientData) obj;
	if (ingredientLocation == null) {
		if (other.ingredientLocation != null)
			return false;
	} else if (!ingredientLocation.equals(other.ingredientLocation))
		return false;
	if (ingredientName == null) {
		if (other.ingredientName != null)
			return false;
	} else if (!ingredientName.equals(other.ingredientName))
		return false;
	return true;
}

/**
 * A help method to get unique ID of the ingredient
 * @return the unique ID of the ingredient
 */
private int nextID() {
	File f = new File("resources/files/IngredientData/");
	return f.listFiles().length;
}

/**
 * @return the ingredientLocation
 */
public String getIngredientLocation() {
	return ingredientLocation;
}

/**
 * @param ingredientLocation the ingredientLocation to set
 */
public void setIngredientLocation(String ingredientLocation) {
	this.ingredientLocation = ingredientLocation;
}

/*public static void main(String[] args) {
	new IngredientData("Tomato", "Top Cabinet").writeFile();
}*/
}