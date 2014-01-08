package data;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

/**
* @author Meng Meng Yan
* @version 1.0
* This Class represents the data for a meal.
* @see Data
*/
public class MealData implements Data {

	private Integer mealID;
	private String mealName;
	private MealCategoryData mealCategory;
	private Float price;
	private JSONObject mealJSON;
	
	/**
        * Creates a new MealData object.
        * @param mealID ID of the new Meal
        * @param mealName Name of the new Meal
        * @param mealCategory mealCategory of the new Meal
        * @param price Price of the new Meal
        */
	public MealData(int mealID, String mealName, MealCategoryData mealCategory, float price) {
		this.mealID = mealID;
		this.mealName = mealName;
		this.mealCategory = mealCategory;
		this.price = price;
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
	         MealData newData = creator.fromJson(jsonString, this.getClass());
	         mealID = newData.getMealID();
	         mealName = newData.getMealName();
	         mealCategory = newData.getMealCategory();
	         price = newData.getPrice();
	}

	@Override
	/**
	* @see Data#writeFile()
	*/
        public void writeFile() {
                 String pathname = "resources/files/MealData/" + mealID + ".json";
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
	* @return the mealID
	*/
        public Integer getMealID() {
                return mealID;
        }
        
        /**
	* @return the mealName
	*/
        public String getMealName() {
                return mealName;
        }
        
        /**
	* @return the mealCategory
	*/
        public MealCategoryData getMealCategory() {
                return mealCategory;
        }
  
	/**
	* @return the price
	*/
        public Float getPrice() {
                return price;
        }
        
        /**
	* @param mealID the mealID to set
	*/
        public void setMealID (Integer mealID) {
                this.mealID = mealID;
        }
        
        /**
	* @param mealName the mealName to set
	*/
        public void setMealName (String mealName) {
                this.mealName = mealName;
        }
        
        /**
	* @param mealCategory the mealCategory to set
	*/
        public void setMealCategory (MealCategoryData mealCategory) {
                this.mealCategory = mealCategory;
        }
        
        /**
	* @param price the price to set
	*/
        public void setPrice (float price) {
                this.price = price;
        }
        
        /**
	* @param mealJSON the mealJSON to set
	*/
        public void setJSON (JSONObject mealJSON) {
                this.mealJSON = mealJSON;
        }

}
