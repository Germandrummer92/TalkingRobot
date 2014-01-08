package data;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.json.JSONObject;
import org.json.JSONException;
import com.google.gson.*;


/**
 *
 * @author Bettina Weller
 * @version 1.0
 * This class represents what type or category of a meal it is.
 * @see Data
 */
public class MealCategoryData implements Data {

  private Integer mealCategoryID;

  private String mealCategoryName;

  private JSONObject mealCategoryJSON;

/**
 * creates a new MealCategoryData object.
 * @param mealCategoryID the ID of the relevant mealCategory
 * @param mealCategoryName the name of a meal's category
 */
public MealCategoryData(Integer mealCategoryID, String mealCategoryName) {
	this.mealCategoryID = mealCategoryID;
	this.mealCategoryName = mealCategoryName;
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

}
