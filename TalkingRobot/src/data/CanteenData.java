package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.json.*;

import com.google.gson.Gson;

/**
*
* @author Aleksandar Andonov, Meng Meng Yan
* @version 2.0
* This Class represents the data for a canteen.
* @see Data
*/
public class CanteenData implements Data{

  	private Integer canteenID;
  	private String canteenName;
	private String address;
  	private ArrayList<LineData> lines;
  
  
	/**
	* Creates a new CanteenData object.
	* @param canteenID ID of the new Canteen
	* @param canteenName Name of the new Canteen
	* @param address Adress of the new Canteen
	* @param lines Lines of the new Canteen
	*/
	public CanteenData(String canteenName, String address, ArrayList<LineData> lines) {
		this.canteenID = nextID();
		this.canteenName = canteenName;
		this.address = address;
		this.lines = lines;
	}
	
	public CanteenData(String jsonString) {
		//this.createFromJSONText(jsonString);
		try {
			//delete time stamp with shift
			lines = new ArrayList<LineData>();
			JSONObject obj = new JSONObject(jsonString);
			JSONObject canteen = obj.getJSONObject("adenauerring");
			
			Iterator iterator = canteen.keys();
			Date now = new Date();
			Date time = null;
			String unixTime;
			do {
				unixTime = (String) iterator.next();
				long timeStamp = Long.parseLong(unixTime);
				time = new java.util.Date((long)timeStamp*1000);
				
			}
			while(Days.daysBetween(new DateTime(now), new DateTime(time)).getDays() != 0);
			
			JSONObject day = canteen.getJSONObject(unixTime);
			//Line1
			JSONArray mealArray = day.getJSONArray("l1");
			lines.add(new LineData("line one", getMeals(mealArray)));
			//Line 2
			mealArray = day.getJSONArray("l2");
			lines.add(new LineData("line two", getMeals(mealArray)));
			//Line 3
			mealArray = day.getJSONArray("l3");
			lines.add(new LineData("line three", getMeals(mealArray)));
			//Line 4
			mealArray = day.getJSONArray("l45");
			lines.add(new LineData("line four", getMeals(mealArray)));
			//SchnitzelBar
			mealArray = day.getJSONArray("schnitzelbar");
			lines.add(new LineData("schnitzel bar", getMeals(mealArray)));
			//
			mealArray = day.getJSONArray("abend");
			lines.add(new LineData("dinner", getMeals(mealArray)));
			//heisstheke
			mealArray = day.getJSONArray("heisstheke");
			lines.add(new LineData("theke", getMeals(mealArray)));
			//nmtisch???
			mealArray = day.getJSONArray("nmtisch");
			lines.add(new LineData("nmtisch", getMeals(mealArray)));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private ArrayList<MealData> getMeals(JSONArray mealsArray) throws JSONException {
		ArrayList<MealData> meals = new ArrayList<MealData>();
		JSONObject meal;
		for(int i = 0; i < mealsArray.length(); i++) {
			JSONObject meal1 = mealsArray.getJSONObject(i);
			MealData meal1Data = getMeal(meal1);
			meals.add(meal1Data);
		}
		return meals;
	}

	private MealData getMeal(JSONObject mealArray) {
		MealData result = null;
		try {
			float price = (float) mealArray.getDouble("price_1");//for students
			String name = mealArray.getString("meal"); //dish???
			result = new MealData(name, null, price);
			//category classes
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return result;
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
	        CanteenData newData = creator.fromJson(jsonString, this.getClass());
	        canteenID = newData.getCanteenID();
	        canteenName = newData.getCanteenName();
	        address = newData.getAddress();
	        lines = newData.getLines();
	}

	@Override
	/**
         * @see Data#writeFile()
         */
	public void writeFile() {
		String pathname = "resources/files/CanteenData/" + canteenID + ".json";
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
                File f = new File("resources/files/CanteenData/");
                return f.listFiles().length;
        }

	/**
         * @return the canteenID
         */
        public Integer getCanteenID() {
                return canteenID;
        }
        
        /**
         * @return the canteenName
         */
        public String getCanteenName() {
                return canteenName;
        }
        
        /**
         * @return the adress
         */
        public String getAddress() {
        	return address;
        }
  
  	/**
         * @return the lines
         */
	public ArrayList<LineData> getLines() {
		return lines;
	}
	
	/**
         * @param canteenID the canteenID to set
         */
	public void setCanteenID (Integer canteenID) {
		this.canteenID = canteenID;
	}
	
	/**
         * @param canteenName the canteenName to set
         */
	public void setCanteenName (String canteenName) {
		this.canteenName = canteenName;
	}
	
	/**
         * @param adress the adress to set
         */
	public void setAddress (String address) {
		this.address = address;
	}
	
	/**
         * @param lines the lines to set
         */
	public void setLines (ArrayList<LineData> lines) {
		this.lines = lines;
	}
	
	//TEST
	public static void main(String[] args) {
		String dirPath = "resources/files/CanteenData/adenauer.json";
		File currFile = new File(dirPath);
		if (!currFile.exists()) {
			return; //there is nothing to delete
		}
		
		String jsonString = ""; //the json String representation of the last File
		CanteenData Cantee;
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(currFile));
			
			String lastReadLine = reader.readLine();
			while (lastReadLine != null) {
				jsonString += lastReadLine;
				lastReadLine = reader.readLine();
			} //Get the json String representation of the last File
			Cantee = new CanteenData(jsonString); //load the last recipe
			reader.close();
			System.out.println(Cantee.generateJSON());
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
}

