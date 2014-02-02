package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.json.JSONObject;
import org.json.JSONException;
import com.google.gson.*;


/**
 *
 * @author Bettina Weller
 * @version 1.1
 * This class represents what type or category of a meal it is.
 * @see Data
 */
public class MealCategoryData implements Data {

  private Integer mealCategoryID;

  private String mealCategoryName;

/**
 * creates a new MealCategoryData object.
 * @param mealCategoryID the ID of the relevant mealCategory
 * @param mealCategoryName the name of a meal's category
 */
public MealCategoryData(String mealCategoryName) {
	this.mealCategoryID = nextID();
	this.mealCategoryName = mealCategoryName;
}

/**
 *  Create the object directly from a jsonString
 * @param jsonString the json String
 * @param constNum to differ from constructor one, type any int
 */
public MealCategoryData(String jsonString, int constNum) {
	this.createFromJSONText(jsonString);
}

/**
 * @return mealCategoryID
 */
public Integer getMealCategoryID() {
	return mealCategoryID;
}

/**
 * @return mealCategoryName
 */
public String getMealCategoryName() {
	return mealCategoryName;
}

/**
 * @param mealCategoryID the mealCategoryID to set
 */
public void setMealCategoryID (Integer mealCategoryID){
	this.mealCategoryID = mealCategoryID;
}

/**
 * @param mealCategoryName the mealCategoryName to set
 */
public void setMealCategoryName (String mealCategoryName) {
	this.mealCategoryName = mealCategoryName;
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
        MealCategoryData newData = creator.fromJson(jsonString, this.getClass());
        mealCategoryID = newData.getMealCategoryID();
        mealCategoryName = newData.getMealCategoryName();
}

@Override
/**
* @see Data#writeFile()
*/
public void writeFile() {
	String pathname = "resources/files/MealCategoryData/" + mealCategoryID + ".json";
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

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	MealCategoryData other = (MealCategoryData) obj;
	if (mealCategoryName == null) {
		if (other.mealCategoryName != null)
			return false;
	} else if (!mealCategoryName.equals(other.mealCategoryName))
		return false;
	return true;
}

/**
 * This static method returns a List of all existing MealCategoryData files.
 * @return a list of all existing MealCategoryData files
 */
public static ArrayList <MealCategoryData> loadData() {
	File load = new File("resources/files/MealCategoryData/");
	Gson loader = new Gson();
	ArrayList <MealCategoryData> res = new ArrayList <MealCategoryData>();
	for (File f : load.listFiles()) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	  	MealCategoryData read = null;
	  		try {
				read = loader.fromJson(br.readLine(), MealCategoryData.class);
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	  	res.add(read);
	}
	return res;
}

/**
         * @return the next unique ID
         */
        private int nextID() {
                File f = new File("resources/files/MealCategoryData/");
                return f.listFiles().length;
        }

}
