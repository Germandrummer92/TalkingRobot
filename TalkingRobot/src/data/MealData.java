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
* @author Meng Meng Yan
* @version 1.5
* This Class represents the data for a meal.
* @see Data
*/
public class MealData implements Data {

	private Integer mealID;
	private String mealName;
	private ArrayList<MealCategoryData> mealCategory;
	private Float s_price;
	private Float e_price;
	
	/**
        * Creates a new MealData object.
        * @param mealID ID of the new Meal
        * @param mealName Name of the new Meal
        * @param mealCategory mealCategory of the new Meal
        * @param price Price of the new Meal
        */
	public MealData(String mealName, ArrayList<MealCategoryData> mealCategory, float s_price, float e_price) {
		this.mealID = nextID();
		this.mealName = mealName;
		this.mealCategory = mealCategory;
		this.s_price = s_price;
		this.e_price = e_price;
	}

	 /**
		 *  Create the object directly from a jsonString
		 * @param jsonString the json String
		 */
		public MealData(String jsonString) {
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
	         MealData newData = creator.fromJson(jsonString, this.getClass());
	         mealID = newData.getMealID();
	         mealName = newData.getMealName();
	         mealCategory = newData.getMealCategory();
	         s_price = newData.getS_price();
	         e_price = newData.getE_price();
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
         * @return the next unique ID
         */
        private int nextID() {
                File f = new File("resources/files/MealData/");
                return f.listFiles().length;
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
        public ArrayList<MealCategoryData> getMealCategory() {
                return mealCategory;
        }
  
	/**
	* @return the price
	*/
        /*
        public Float getPrice() {
                return price;
        }*/
        
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
        public void setMealCategory (ArrayList<MealCategoryData> mealCategory) {
                this.mealCategory = mealCategory;
        }
        
        /**
	* @param price the price to set
	*/ /*
        public void setPrice (float price) {
                this.price = price;
        }*/

		public Float getS_price() {
			return s_price;
		}

		public void setS_price(Float s_price) {
			this.s_price = s_price;
		}

		public Float getE_price() {
			return e_price;
		}

		public void setE_price(Float e_price) {
			this.e_price = e_price;
		}

}
