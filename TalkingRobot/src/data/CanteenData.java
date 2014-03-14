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

import org.joda.time.DateTime;
import org.json.*;

import com.google.gson.Gson;

/**
* This Class represents a canteen. It offers means to automatically load data for the canteens from
* there json representation formatted as in the KIT canteen files.
* @author Aleksandar Andonov, Meng Meng Yan
* @version 3.5
* @see Data
*/
public class CanteenData implements Data{

  	private Integer canteenID;
  	private CanteenNames canteenName;
	private String address;
  	private ArrayList<LineData> lines;
  	private ArrayList<MealCategoryData> categories;
  	boolean isOpen; //flag signals weather the mensa is open. Is false only on weekends.
  	
  
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
		categories = MealCategoryData.loadData();
	}
	
	/**
	 * Constructs the canteen object from the canteen file for the day today+offset. The lines are named then as follows:
	 * Adenauerring: line one, line two, line three, line four, schnitzelbar, theke (this is for cafeteria), nmtisch (cafeteria after 14:30),
	 * Curry Queen, line six
	 * Moltke: line one (stands for Wahlessen 1), line two (Wahlessen 2), aktion, gut und gunstig (stands for gut und günstig), buffet, schnitzelbar
	 * Erzberger: line one(stands for Wahlessen 1), line two (Wahlessen 2)
	 * Gottesaue: line one(stands for Wahlessen 1), line two (Wahlessen 2)
	 * Tiefenbronner: line one (stands for Wahl 1), line two (Wahl 2), gut und gunstig (gut und günstig), buffet
	 * Holzgarten: gut und gunstig (stands for gut und günstig), gut und gunstig 2 (gut und günstig 2)  
	 * @param canteenName The name of the canteen wanted
	 * @param timeOffset The time offset from the current time in days. For example
	 * 1 for tommorow, -1 for yesterday and so on. Must be an integer between -3 and
	 * 13.
	 */
	public CanteenData(CanteenNames canteenName, int timeOffset) {
		this.canteenName = canteenName;
		isOpen = true; //default is true, is managed by the manageTime method
		categories = MealCategoryData.loadData();
		String dirPath = "resources/files/CanteenMenu/www.studentenwerk-karlsruhe.de/json_external" +
				"/kit_edu/canteen/";
		File canteenFile = new File(dirPath + canteenName.toString().toLowerCase());
		if (!canteenFile.exists()) {
			updateMenu();
		}
		boolean linesSet = setLines(dirPath, timeOffset);
		//if lines were not set, try updating and then try to set them again
		if (!linesSet && isOpen) {
			updateMenu();
			setLines(dirPath, timeOffset);
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
	
	
	public ArrayList<MealCategoryData> getCategories() {
		return categories;
	}

	public boolean isOpen() {
		return isOpen;
	}

	/**
	 * Set the lines of this canteen using the file located in the folder with path dirPath for the day
	 * specified by today+offset
	 * @param dirPath The path to the directory holding the canteen file
	 * @param timeOffset The offset from the current file
	 * @return true if the lines were set successfully
	 */
	private boolean setLines(String dirPath, int timeOffset) {
		String jsonString = getJsonString(dirPath + canteenName.toString().toLowerCase());
		boolean linesSet = false;
		switch(canteenName) {
			case ADENAUERRING:
				linesSet = setAdenauerringLines(jsonString, timeOffset);
				address = "Adenauerring 7";
				break;
			case MOLTKE:
				linesSet = setMoltkeLines(jsonString, timeOffset);
				address = ""; //unknown
				break;
			case  ERZBERGER:
				linesSet = setErzbergerLines(jsonString, timeOffset);
				address = "";//unknown
				break;
			case GOTTESAUE:
				linesSet = setGottesaueLines(jsonString, timeOffset);
				address = "";//unknown
				break;
			case TIEFENBRONNER:
				linesSet = setTiefenbronnerLines(jsonString, timeOffset);
				address = "";//unknown
				break;
			case HOLZGARTEN:
				linesSet = setHolzgartenLines(jsonString, timeOffset);
				address ="";//unknown
				break;
		}	
		return linesSet;
	}
	
	/**
	 * Updates the menu of this canteen.
	 */
	private void updateMenu() {
		String mensaName = canteenName.toString().toLowerCase();
		try {
			ProcessBuilder pb = new ProcessBuilder("./mensaUpdate.sh", mensaName);
			String scriptPath = System.getProperty("user.dir");
			scriptPath += "/resources/files/CanteenMenu/";
			pb.directory(new File(scriptPath));
			Process process = pb.start();
			process.waitFor();
		} catch (IOException e) {
				//do nothing
			//e.printStackTrace();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
	}
	
	/**
	 * Sets the lines of an Adenauerring type mensa (where the lines are named the same way as in the Adenauerring canteen).
	 * @param jsonString The json String containing the description of the Adenauerring canteen.
	 * @param timeOffset The time offset from the current time.
	 * @return true if the lines were set successfully
	 */
	private boolean setAdenauerringLines(String jsonString, int timeOffset) {
		try {
			lines = new ArrayList<LineData>();
			JSONObject obj = new JSONObject(jsonString);
			JSONObject canteen = obj.getJSONObject("adenauerring");
			String unixTime = manageTime(canteen, timeOffset);
			if (unixTime == null) {
				return false;
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
			lines.add(new LineData("schnitzelbar", getMeals(mealArray)));
			//abend
			mealArray = day.getJSONArray("abend");
			lines.add(new LineData("dinner", getMeals(mealArray)));
			//heisstheke
			mealArray = day.getJSONArray("heisstheke");
			lines.add(new LineData("theke", getMeals(mealArray)));
			//nmtisch???
			mealArray = day.getJSONArray("nmtisch");
			lines.add(new LineData("nmtisch", getMeals(mealArray)));
			//update = linie 6
			mealArray = day.getJSONArray("update");
			lines.add(new LineData("line six", getMeals(mealArray)));
			//aktion = curry queen
			mealArray = day.getJSONArray("aktion");
			lines.add(new LineData("Curry Queen", getMeals(mealArray)));
			return true;
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		return false;
	}

	private boolean setMoltkeLines(String jsonString, int timeOffset) {
		try {
			lines = new ArrayList<LineData>();
			JSONObject obj = new JSONObject(jsonString);
			JSONObject canteen = obj.getJSONObject("moltke");
			String unixTime = manageTime(canteen, timeOffset);
			if (unixTime == null) {
				return false; //canteen is closed, now + offset = Saturday or Sunday
			}
			JSONObject day = canteen.getJSONObject(unixTime);
			//Wahlessen 1
			JSONArray mealArray = day.getJSONArray("wahl1");
			lines.add(new LineData("line one", getMeals(mealArray)));
			//Wahlessen 2
			mealArray = day.getJSONArray("wahl2");
			lines.add(new LineData("line two", getMeals(mealArray)));
			//Aktionstheke
			mealArray = day.getJSONArray("aktion");
			lines.add(new LineData("aktion", getMeals(mealArray)));
			//Gut und gunstig
			mealArray = day.getJSONArray("gut");
			lines.add(new LineData("gut und gunstig", getMeals(mealArray)));
			//Buffet
			mealArray = day.getJSONArray("buffet");
			lines.add(new LineData("buffet", getMeals(mealArray)));
			//Schnitzelbar
			mealArray = day.getJSONArray("schnitzelbar");
			lines.add(new LineData("schnitzelbar", getMeals(mealArray)));
			return true;
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean setErzbergerLines(String jsonString, int timeOffset) {
		try {
			lines = new ArrayList<LineData>();
			JSONObject obj = new JSONObject(jsonString);
			JSONObject canteen = obj.getJSONObject("erzberger");
			String unixTime = manageTime(canteen, timeOffset);
			if (unixTime == null) {
				return false; //canteen is closed, now + offset = Saturday or Sunday or offset is wrong/ menu us old
			}
			JSONObject day = canteen.getJSONObject(unixTime);
			//Wahlessen 1
			JSONArray mealArray = day.getJSONArray("wahl1");
			lines.add(new LineData("line one", getMeals(mealArray)));
			//Wahlessen 2
			mealArray = day.getJSONArray("wahl2");
			lines.add(new LineData("line two", getMeals(mealArray)));
			return true;
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean setGottesaueLines(String jsonString, int timeOffset) {
		try {
			lines = new ArrayList<LineData>();
			JSONObject obj = new JSONObject(jsonString);
			JSONObject canteen = obj.getJSONObject("gottesaue");
			String unixTime = manageTime(canteen, timeOffset);
			if (unixTime == null) {
				return false; //canteen is closed, now + offset = Saturday or Sunday
			}
			JSONObject day = canteen.getJSONObject(unixTime);
			//Wahlessen 1
			JSONArray mealArray = day.getJSONArray("wahl1");
			lines.add(new LineData("line one", getMeals(mealArray)));
			//Wahlessen 2
			mealArray = day.getJSONArray("wahl2");
			lines.add(new LineData("line two", getMeals(mealArray)));
			return true;
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean setTiefenbronnerLines(String jsonString, int timeOffset) {
		try {
			lines = new ArrayList<LineData>();
			JSONObject obj = new JSONObject(jsonString);
			JSONObject canteen = obj.getJSONObject("tiefenbronner");
			String unixTime = manageTime(canteen, timeOffset);
			if (unixTime == null) {
				return false; //canteen is closed, now + offset = Saturday or Sunday
			}
			JSONObject day = canteen.getJSONObject(unixTime);
			//Wahlessen 1
			JSONArray mealArray = day.getJSONArray("wahl1");
			lines.add(new LineData("line one", getMeals(mealArray)));
			//Wahlessen 2
			mealArray = day.getJSONArray("wahl2");
			lines.add(new LineData("line two", getMeals(mealArray)));
			//Gut und gunstig
			mealArray = day.getJSONArray("gut");
			lines.add(new LineData("gut und gunstig", getMeals(mealArray)));
			//Buffet
			mealArray = day.getJSONArray("buffet");
			lines.add(new LineData("buffet", getMeals(mealArray)));
			return true;
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		return false;
	}

	private boolean setHolzgartenLines(String jsonString, int timeOffset) {
		try {
			lines = new ArrayList<LineData>();
			JSONObject obj = new JSONObject(jsonString);
			JSONObject canteen = obj.getJSONObject("holzgarten");
			String unixTime = manageTime(canteen, timeOffset);
			if (unixTime == null) {
				return false; //canteen is closed, now + offset = Saturday or Sunday
			}
			JSONObject day = canteen.getJSONObject(unixTime);
			
			//Gut und gunstig
			JSONArray mealArray = day.getJSONArray("gut");
			lines.add(new LineData("gut und gunstig", getMeals(mealArray)));
			//Gut und Gunstig 2
			mealArray = day.getJSONArray("gut2");
			lines.add(new LineData("gut und gunstig two", getMeals(mealArray)));
			return true;
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Extract the unixTime stamp from the JSONObject canteen. The time stamp must be for the same day as today + offset(in days).
	 * If canteen is closed on that day (now+offset) then the isOpen flag is set to false and null is returned.
	 * If a stamp matching now + offset wasn#t found in the JSON object canteen (formatted like the canteen file of the KIT), then return null
	 * @param canteen The JSON object representing a canteen formatted as in the canteen file of the KIT
	 * @param timeOffset An integer between -3 and 13
	 * @return The unix time stamp in the canteen JSONObject for today+offset or null if the day is saturday or sunday or if the today+offset
	 * is out of the bounds of the canteen file
	 */
	private String manageTime(JSONObject canteen, int timeOffset) {
	
		String[] names = JSONObject.getNames(canteen);
		
		DateTime now = new DateTime(new Date());		
		if (timeOffset > 0 && timeOffset <= 13) {
			now = now.plusDays(timeOffset);
		}
		else if (timeOffset < 0 && timeOffset > -3) {
			now = now.minusDays(timeOffset);
		}
		else {
			return null;
		}
		
		String currWeekDay = now.dayOfWeek().getAsText(); //1 for Monday
		//int currHour = now.hourOfDay().get();// from 0 to 23
		if (currWeekDay.equals("Saturday") || currWeekDay.equals("Sunday")) {
			isOpen = false;
			return null;
		}
		//else if (currHour < 11 || currHour >= 14) {
		//	
		//} it's complicated, when are differentLines open ... for lines separate method -> is open in lineData, file with opening times...
		
		Date time = null;
		String unixTime = "";
		for (int i = 0; i < names.length; i++) {
			unixTime = names[i];
			long timeStamp = Long.parseLong(unixTime);
			time = new java.util.Date((long)timeStamp*1000L);
			DateTime dt = new DateTime(time);	
			if (now.getDayOfYear() == dt.getDayOfYear()) {
				return unixTime;
			}
		}
		return null;
	}
	
	/**
	 * Extract the json String from the file with the path specified.
	 * @param path The path to the file
	 * @return The String which was read from the file
	 * @throws FileNotFoundException When the file specified by path wasn't found
	 */
	private String getJsonString(String path) {
		File currFile = new File(path);
		String jsonString = ""; //the json String representation of the File
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(currFile));
			//Get the json String representation of the File
			String lastReadLine = reader.readLine();
			while (lastReadLine != null) {
				jsonString += lastReadLine;
				lastReadLine = reader.readLine();
			} 
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		finally {
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return jsonString;
	}

	/**
	 * Extracts the list of meals from a JSONArray containing objects which represent meals in the canteen file.
	 * @param mealsArray the array containing object which represent meals as in the canteen file
	 * @return A list of MealData object representing the meals in this system.
	 * @throws JSONException when the array is not formated accordingly to the canteen file
	 */
	private ArrayList<MealData> getMeals(JSONArray mealsArray) throws JSONException {
		if (mealsArray.length() <= 1) {
			return new ArrayList<MealData>(); //noData:true
		}
		ArrayList<MealData> meals = new ArrayList<MealData>();
		for(int i = 0; i < mealsArray.length(); i++) {
			JSONObject meal1 = mealsArray.getJSONObject(i);
			MealData meal1Data = getMeal(meal1);
			meals.add(meal1Data);
		}
		return meals;
	}

	/**
	 * Extract the MealData from a JSON object (which represents meal in the canteen file)
	 * @param meal the JSON object representing a valid meal from the canteen file
	 * @return the MealData object constructed from the JSON object
	 */
	private MealData getMeal(JSONObject meal) {
		MealData result = null;
		try {
			float s_price = (float) meal.getDouble("price_1");//for students
			float e_price = (float) meal.getDouble("price_2");//for employees
			String name = meal.getString("meal");
			String dish = meal.getString("dish");
			name = name + " " + dish;
			result = new MealData(name, getCategories(meal), s_price, e_price);
		} catch (JSONException e) {
			e.printStackTrace();
		} 
		return result;
	}
	
	/**
	 * Returns a list with the categories to which the meal described by
	 * the JSONObject meal is part of.
	 * @param meal The json object meal
	 * @return A list of the meal categories to which the meal is part of
	 */
	private ArrayList<MealCategoryData> getCategories(JSONObject meal) {
		ArrayList<MealCategoryData> mealCat = new ArrayList<MealCategoryData>();
		try {
			boolean bio = meal.getBoolean("bio");
			boolean fish = meal.getBoolean("fish");
			boolean pork = meal.getBoolean("pork");
			boolean cow = meal.getBoolean("cow");
			boolean vegan = meal.getBoolean("vegan");
			boolean veg = meal.getBoolean("veg");
			if(bio) {
				addCategory(mealCat, "bio");
			}
			else if(fish) {
				addCategory(mealCat, "fish");
			}
			else if(pork) {
				addCategory(mealCat, "pork");
			}
			else if(cow) {
				addCategory(mealCat, "cow");
			}
			else if (vegan) {
				addCategory(mealCat, "vegan");
			}
			else if (veg) {
				addCategory(mealCat, "veg");
			}
		}
		catch(JSONException e) {
			e.printStackTrace();
		}
		return mealCat;
	}

	/**
	 * Adds a category with the given name to the given list. If category already
	 * exists in the system, it is added. If this is not the case a new category
	 * with the given name is created and written in the database. 
	 * @param mealCat the list of categories
	 * @param mealCatName the category name
	 */
	private void addCategory(ArrayList<MealCategoryData> mealCat, String mealCatName) {
		if (!categoryExists(mealCatName, mealCat)) {
			MealCategoryData cat = new MealCategoryData(mealCatName);
			cat.writeFile();
			mealCat.add(cat); //add to the local list (the one of the current meal)
			categories.add(cat); //add to the global list(the one of the canteen)
		}
	}

	private boolean categoryExists(String categoryName, ArrayList<MealCategoryData> mealCat) {
		for (MealCategoryData category : categories) {
			if (category.getMealCategoryName().equals(categoryName)) {
				mealCat.add(category);
				return true;
			}
		}
		return false;
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
	//	CanteenData aden = new CanteenData(CanteenNames.ADENAUERRING, 0);
	//	System.out.println(aden.generateJSON());
	//	CanteenData moltke = new CanteenData(CanteenNames.MOLTKE, 0);
	//	System.out.println(moltke.generateJSON());
	//	CanteenData aden = new CanteenData(CanteenNames.ADENAUERRING, 0);

		CanteenData cd = new CanteenData(CanteenNames.ADENAUERRING, 2);
		System.out.println(cd.generateJSON());
		System.out.println("Done");
	}
	
}

