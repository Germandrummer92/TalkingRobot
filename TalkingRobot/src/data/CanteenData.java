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
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.json.*;

import com.google.gson.Gson;

/**
*
* @author Aleksandar Andonov, Meng Meng Yan
* @version 3.0
* This Class represents the data of a canteen of a canteen and offers means to automatically load data for the canteens from
* there json representation.
* @see Data
*/
public class CanteenData implements Data{

  	private Integer canteenID;
  	private CanteenNames canteenName;
	private String address;
  	private ArrayList<LineData> lines;
  	private ArrayList<MealCategoryData> categories;
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
		categories = MealCategoryData.loadData();
	}
	
	/**
	 * Constructs
	 * @param canteenName
	 * @param timeOffset The time offset from the current time in days. For example
	 * 1 for tommorow, -1 for yesterday and so on. Must be an integer between -3 and
	 * 7
	 */
	public CanteenData(CanteenNames canteenName, int timeOffset) {
		categories = MealCategoryData.loadData();
		String dirPath = "resources/files/CanteenMenu/www.studentenwerk-karlsruhe.de/json_external" +
				"/kit_edu/canteen/";
		updateMenu(canteenName.toString().toLowerCase());
		String jsonString = getJsonString(dirPath + canteenName.toString().toLowerCase());
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
			case  ERZBERGER:
				setErzbergerLines(jsonString, timeOffset);
				this.canteenName = canteenName;
				address = "";//unknown
				break;
			case GOTTESAUE:
				setGottesaueLines(jsonString, timeOffset);
				this.canteenName = canteenName;
				address = "";//unknown
				break;
			case TIEFENBRONNER:
				setTiefenbronnerLines(jsonString, timeOffset);
				this.canteenName = canteenName;
				address = "";//unknown
				break;
			case HOLZGARTEN:
				setHolzgartenLines(jsonString, timeOffset);
				this.canteenName = canteenName;
				address ="";//unknown
				break;
		}	
	}
	
	private void updateMenu(String mensaName) {
		try {
			ProcessBuilder pb = new ProcessBuilder("./mensaUpdate.sh", mensaName);
			String path = System.getProperty("user.dir");
			path += "/resources/files/CanteenMenu/";
			pb.directory(new File(path));
			Process process = pb.start();
			process.waitFor();
		} catch (IOException e) {
			// TODO 
				//do nothing
			//e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
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
	
	/**
	 * Sets the lines of an Adenauerring type mensa (where the lines are named the same way as in the Adenauerring canteen).
	 * @param jsonString The json String containing the description of the Adenauerring canteen.
	 * @param timeOffset The time offset from the current time.
	 */
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
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
	}

	private void setMoltkeLines(String jsonString, int timeOffset) {
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
			//Gut und gunstig
			mealArray = day.getJSONArray("gut");
			lines.add(new LineData("Gut und gunstig", getMeals(mealArray)));
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
	
	private void setErzbergerLines(String jsonString, int timeOffset) {
		try {
			lines = new ArrayList<LineData>();
			JSONObject obj = new JSONObject(jsonString);
			JSONObject canteen = obj.getJSONObject("erzberger");
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
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
	}
	
	private void setGottesaueLines(String jsonString, int timeOffset) {
		try {
			lines = new ArrayList<LineData>();
			JSONObject obj = new JSONObject(jsonString);
			JSONObject canteen = obj.getJSONObject("gottesaue");
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
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
	}
	
	private void setTiefenbronnerLines(String jsonString, int timeOffset) {
		try {
			lines = new ArrayList<LineData>();
			JSONObject obj = new JSONObject(jsonString);
			JSONObject canteen = obj.getJSONObject("tiefenbronner");
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
			//Gut und gunstig
			mealArray = day.getJSONArray("gut");
			lines.add(new LineData("Gut und gunstig", getMeals(mealArray)));
			//Buffet
			mealArray = day.getJSONArray("buffet");
			lines.add(new LineData("buffet", getMeals(mealArray)));
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
	}

	private void setHolzgartenLines(String jsonString, int timeOffset) {
		try {
			lines = new ArrayList<LineData>();
			JSONObject obj = new JSONObject(jsonString);
			JSONObject canteen = obj.getJSONObject("holzgarten");
			String unixTime = manageTime(canteen, timeOffset);
			if (unixTime == null) {
				return; //canteen is closed, now + offset = Saturday or Sunday
			}
			JSONObject day = canteen.getJSONObject(unixTime);
			
			//Gut und gunstig
			JSONArray mealArray = day.getJSONArray("gut");
			lines.add(new LineData("Gut und gunstig", getMeals(mealArray)));
			//Gut und Gunstig 2
			mealArray = day.getJSONArray("gut2");
			lines.add(new LineData("Gut und gunstig two", getMeals(mealArray)));
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
	//	Iterator iterator = canteen.keys();
		String[] names = JSONObject.getNames(canteen);
		
		DateTime now = new DateTime(new Date());		
		if (timeOffset > 0 && timeOffset <= 7) {
			now = now.plusDays(timeOffset);
		}
		else if (timeOffset < 0 && timeOffset > -3) {
			now = now.minusDays(timeOffset);
		}
		else {
			//will not work correctly -> Exception??? when time offset is unacceptable
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
			if (now.getDayOfMonth() == dt.getDayOfMonth()) {
				i = names.length + 1; //break
			}
		}
		return unixTime;
	}
	
	/**
	 * Get the jsons String from file with path specified by parameter
	 * @param path The path of the file
	 * @return the read string
	 * @throws FileNotFoundException 
	 */
	private String getJsonString(String path) {
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
			float s_price = (float) meal.getDouble("price_1");//for students
			float e_price = (float) meal.getDouble("price_2");
			String name = meal.getString("meal"); //dish???
			String dish = meal.getString("dish");
			name = name + " " + dish;
			result = new MealData(name, getCategories(meal), s_price, e_price);
		} catch (JSONException e) {
			// TODO Auto-generated catch 
			e.printStackTrace();
		} 
		return result;
	}
	
	/**
	 * Returns a list with the categories to which the meal described by
	 * the JSONObject is part of.
	 * @param meal
	 * @return
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
	//	String[] name = {"./mensaUpdate.sh", "adenauerring"};
	//	ProcessBuilder pb = new ProcessBuilder("./mensaUpdate.sh", "adenauerring");
		//File dir = new File("resources/files/CanteenMenu/");
		//System.out.println(dir.exists());
	//	String path = System.getProperty("user.dir");
	//	path += "/resources/files/CanteenMenu/";
	//	pb.directory(new File(path));
		//System.out.println(path);
		
	//	try {
	//		Process proc = pb.start();
	//		Process proc2 = Runtime.getRuntime().exec("./mensaUpdate.sh", name);
	//		List<String> com = pb.command();
	//		for (int i = 0; i < com.size(); i++) {
	//			System.out.println(com.get(i));
	//		}
	//		System.out.println(pb.directory());
		//	System.out.println(System.getProperty("user.dir"));
	//	} catch (IOException e) {
			// TODO Auto-generated catch block
	//		e.printStackTrace();
	//	}
		CanteenData cd = new CanteenData(CanteenNames.ADENAUERRING, 0);
		System.out.println(cd.generateJSON());
		System.out.println("Done");
	}
	
}

