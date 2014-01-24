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
  	private CanteenNames canteenName;
	private String address;
  	private ArrayList<LineData> lines;
  	boolean isOpen; //mensa is open when one of it lines is open
  
  
	/**
	* Creates a new CanteenData object.
	* @param canteenID ID of the new Canteen
	* @param canteenName Name of the new Canteen
	* @param address Adress of the new Canteen
	* @param lines Lines of the new Canteen
	*/
	public CanteenData(CanteenNames canteenName, String address, ArrayList<LineData> lines) {
		this.canteenID = nextID();
		this.canteenName = canteenName;
		this.address = address;
		this.lines = lines;
	}
	
	//not sure about the seven
	/**
	 * 
	 * @param canteenName
	 * @param timeOffset The time offset from the current time in days. For example
	 * 1 for tommorow, -1 for yesterday and so on. Must be an integer between -3 and
	 * 6 
	 */
	public CanteenData(CanteenNames canteenName, int timeOffset) {
		String dirPath = "resources/files/CanteenMenu/";
		String jsonString = getJsonString(dirPath + canteenName.toString() + ".json");
		System.out.println(jsonString); //TEST
		switch(canteenName) {
			case ADENAUERRING:
				setAdenauerringLines(jsonString, timeOffset);
				this.canteenName = canteenName;
				address = "Adenauerring 7";
				break;
			case MOLTKE:
				setMoltkeLines(jsonString, timeOffset);
				this.canteenName = canteenName;
				address = ""; //unknown
				break;
		}
		
		
		
		
		
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
         * @return the canteenID
         */
        public Integer getCanteenID() {
                return canteenID;
        }
        
        /**
         * @return the canteenName
         */
        public CanteenNames getCanteenName() {
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
	public void setCanteenName (CanteenNames canteenName) {
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
	
	private void setAdenauerringLines(String jsonString, int timeOffset) {
		try {
			lines = new ArrayList<LineData>();
			JSONObject obj = new JSONObject(jsonString);
			JSONObject canteen = obj.getJSONObject("adenauerring");
			String unixTime = manageTime(canteen, timeOffset);
			if (unixTime == null) {
				return;
			}
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
			
			e.printStackTrace();
		}
	}

	private void setMoltkeLines(String jsonString, int timeOffset) {
		// TODO Auto-generated method stub
		try {
			lines = new ArrayList<LineData>();
			JSONObject obj = new JSONObject(jsonString);
			JSONObject canteen = obj.getJSONObject("moltke");
			String unixTime = manageTime(canteen, timeOffset);
			if (unixTime == null) {
				return; //canteen is closed, now + offset = Saturday or Sunday
			}
			JSONObject day = canteen.getJSONObject(unixTime);
			//Wahlessen 1
			JSONArray mealArray = day.getJSONArray("wahl1");
			lines.add(new LineData("line one", getMeals(mealArray)));//choice one?
			//Wahlessen 2
			mealArray = day.getJSONArray("wahl2");
			lines.add(new LineData("line two", getMeals(mealArray)));
			//Aktionstheke
			mealArray = day.getJSONArray("aktion");
			lines.add(new LineData("aktion", getMeals(mealArray)));
			//Gut und günstig
			mealArray = day.getJSONArray("gut");
			lines.add(new LineData("Gut und günstig", getMeals(mealArray)));
			//Buffet
			mealArray = day.getJSONArray("buffet");
			lines.add(new LineData("buffet", getMeals(mealArray)));
			//Schnitzelbar
			mealArray = day.getJSONArray("schnitzelbar");
			lines.add(new LineData("schnitzelbar", getMeals(mealArray)));
			
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
	}

	/**
	 * Get the unixTime stamp from the JSONObject Canteen with the given offset, if mensa is closed on that day (now+offset) return null
	 * @param canteen
	 * @param timeOffset
	 * @return
	 */
	private String manageTime(JSONObject canteen, int timeOffset) {
		// TODO Auto-generated method stub
		Iterator iterator = canteen.keys();
		DateTime now = new DateTime(new Date());
		if (timeOffset > 0 && timeOffset < 7) {
			now = now.plusDays(timeOffset);
		}
		else if (timeOffset < 0 && timeOffset > -3) {
			now = now.minusDays(timeOffset);
		}
		else {
			//will not work correctly -> Exception??? when time offset is unacceptable
		}
		
		String currWeekDay = now.dayOfWeek().getAsText(); //1 for Monday
		int currHour = now.hourOfDay().get();// from 0 to 23
		if (currWeekDay.equals("Saturday") || currWeekDay.equals("Sunday")) {
			isOpen = false;
			return null;
		}
		//else if (currHour < 11 || currHour >= 14) {
		//	
		//} it's complicated, when are differentLines open ... for lines separate method -> is open in lineData, file with opening times...
		
		Date time = null;
		String unixTime;
		do {
			unixTime = (String) iterator.next();
			long timeStamp = Long.parseLong(unixTime);
			time = new java.util.Date((long)timeStamp*1000);
			
		}
		while(Days.daysBetween(now, new DateTime(time)).getDays() != 0 && iterator.hasNext());
		return unixTime;
	}
	
	/**
	 * Get the jsons String from file with path specified by parameter
	 * @param path The path of the file
	 * @return the read string
	 * @throws FileNotFoundException 
	 */
	private String getJsonString(String path) {
		// TODO Auto-generated method stub
		File currFile = new File(path);
		String jsonString = ""; //the json String representation of the File
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(currFile));
			
			String lastReadLine = reader.readLine();
			while (lastReadLine != null) {
				jsonString += lastReadLine;
				lastReadLine = reader.readLine();
			} //Get the json String representation of the File
			reader.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return jsonString;
	}

	/**
	 * Extracts the list of meals from an JSONArray containing meals descriptions
	 * @param mealsArray
	 * @return
	 * @throws JSONException when array is not formated accordingly to mensa style
	 */
	private ArrayList<MealData> getMeals(JSONArray mealsArray) throws JSONException {
		if (mealsArray.length() <= 1) {
			return new ArrayList<MealData>(); //noData:true
		}
		ArrayList<MealData> meals = new ArrayList<MealData>();
		JSONObject meal;
		for(int i = 0; i < mealsArray.length(); i++) {
			JSONObject meal1 = mealsArray.getJSONObject(i);
			MealData meal1Data = getMeal(meal1);
			meals.add(meal1Data);
		}
		return meals;
	}

	/**
	 * Extract the MealData from a meal mensa JSON object
	 * @param meal
	 * @return
	 */
	private MealData getMeal(JSONObject meal) {
		MealData result = null;
		try {
			float price = (float) meal.getDouble("price_1");//for students
			String name = meal.getString("meal"); //dish???
			result = new MealData(name, null, price);
			//->category classes!!!
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return result;
	}
	
	/**
     * @return the next unique ID
     */
    private int nextID() {
            File f = new File("resources/files/CanteenData/");
            return f.listFiles().length;
    }
	
	//TEST
	public static void main(String[] args) {
		CanteenData aden = new CanteenData(CanteenNames.ADENAUERRING, 1);
		System.out.println(aden.generateJSON());
		CanteenData moltke = new CanteenData(CanteenNames.MOLTKE, 0);
		System.out.println(moltke.generateJSON());
	}
	
}

