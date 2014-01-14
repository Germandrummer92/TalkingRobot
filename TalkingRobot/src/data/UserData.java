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

import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.*;

/**
 * 
 * @author Daniel Draper
 * @version 1.1
 * This Class represents the data for one user of the system. 
 * @see Data
 */
public class UserData implements Data {

  private int userID;
  private String userName;
  private boolean isStudent;
  private ArrayList <RecipeDatePair>requestedRecipes;
  private ArrayList <MealDatePair> acceptedSuggestions;
  private Date lastAccess;
  private String userPreference;
  private JSONObject userJSON;

  /**
   * Creates a new UserData object for a new User.
   * @param userName Name of the new User
   * @param isStudent if the new User is a Student.
   */
  public UserData(String userName, boolean isStudent) {
	  this.userID = nextID();
	  this.userName = userName;
	  this.isStudent = isStudent;
	  lastAccess = new Date();
	  requestedRecipes = new ArrayList<RecipeDatePair>();
	  acceptedSuggestions = new ArrayList<MealDatePair>();
	  userPreference = null;
  }
  
  /**
   * Create the object directly from a jsonString
   * @param jsonString
   */
  public UserData(String jsonString) {
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
		UserData newData = creator.fromJson(jsonString, this.getClass());
		userID = newData.getUserID();
		userName = newData.getUserName();
		isStudent = newData.isStudent();
		lastAccess =  newData.getLastAccess();
		requestedRecipes = newData.getRequestedRecipes();
		acceptedSuggestions = newData.getAcceptedSuggestions();
		userPreference = newData.getUserPreference();
	}
		
	@Override
	/**
	 * @see Data#writeFile()
	 */
	public void writeFile() {
		String pathname = "resources/files/UserData/" + userID + ".json";
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
		File f = new File("resources/files/UserData/");
		return f.listFiles().length;
	}
	
	/**
	 * @return the userID
	 */
	public Integer getUserID() {
		return userID;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @return the isStudent
	 */
	public boolean isStudent() {
		return isStudent;
	}
	/**
	 * @return the requestedRecipes
	 */
	public ArrayList<RecipeDatePair> getRequestedRecipes() {
		return requestedRecipes;
	}
	/**
	 * @return the acceptedSuggestions
	 */
	public ArrayList<MealDatePair> getAcceptedSuggestions() {
		return acceptedSuggestions;
	}
	/**
	 * @return the lastAccess
	 */
	public Date getLastAccess() {
		return lastAccess;
	}
	/**
	 * @return the userJSON
	 */
	public JSONObject getUserJSON() {
		return userJSON;
	}
	/**
	 * @param userID the userID to set
	 */
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	
	/**
	 * @return the User's preference
	 */
	public String getUserPreference() {
		return userPreference;
	}
	
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @param isStudent the isStudent to set
	 */
	public void setStudent(boolean isStudent) {
		this.isStudent = isStudent;
	}
	/**
	 * @param requestedRecipes the requestedRecipes to set
	 */
	public void setRequestedRecipes(ArrayList<RecipeDatePair> requestedRecipes) {
		this.requestedRecipes = requestedRecipes;
	}
	/**
	 * @param acceptedSuggestions the acceptedSuggestions to set
	 */
	public void setAcceptedSuggestions(ArrayList<MealDatePair> acceptedSuggestions) {
		this.acceptedSuggestions = acceptedSuggestions;
	}
	/**
	 * @param lastAccess the lastAccess to set
	 */
	public void setLastAccess(Date lastAccess) {
		this.lastAccess = lastAccess;
	}
	/**
	 * @param userJSON the userJSON to set
	 */
	public void setUserJSON(JSONObject userJSON) {
		this.userJSON = userJSON;
	}

	/**
	 * @param userPreference the userPreference to set
	 */
	public void setUserPreference(String userPreference) {
		this.userPreference = userPreference;
	}
/*
	public boolean equal(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		UserData other = (UserData) obj;
		if (userID == other.getUserID() && userName == other.getUserName() 
				&& isStudent == other.isStudent() && lastAccess.equals(other.getLastAccess())
				&& userPreference == other.getUserPreference() && requestedRecipes.equals(other.getRequestedRecipes()) 
				&& acceptedSuggestions.equals(other.getAcceptedSuggestions())) {
			return true;
		}
		else {
			return false;
		}
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
		UserData other = (UserData) obj;
		if (acceptedSuggestions == null) {
			if (other.acceptedSuggestions != null)
				return false;
		} else if (!acceptedSuggestions.equals(other.acceptedSuggestions))
			return false;
		if (isStudent != other.isStudent)
			return false;
		if (requestedRecipes == null) {
			if (other.requestedRecipes != null)
				return false;
		} else if (!requestedRecipes.equals(other.requestedRecipes))
			return false;
		if (userID != other.userID)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (userPreference == null) {
			if (other.userPreference != null)
				return false;
		} else if (!userPreference.equals(other.userPreference))
			return false;
		return true;
	}

	/**
	 * This static method returns a List of all existing UserData files.
	 * @return a list of all existing UserData files
	 */
	public static ArrayList <UserData> loadData() {
		File load = new File("resources/files/UserData/");
		Gson loader = new Gson();
		ArrayList <UserData> res = new ArrayList <UserData>();
		for (File f : load.listFiles()) {
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(f));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		  	UserData read = null;
		  		try {
					read = loader.fromJson(br.readLine(), UserData.class);
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		  	res.add(read);
		}
		return res;
	}

	 

	    /*Test: 
	     * public static void main (String args[]) {
	  	
	  	System.out.println(UserData.loadData().get(0).getUserName());
	  	System.out.println(UserData.loadData().get(0).getLastAccess());
	  	System.out.println(UserData.loadData().get(0).getRequestedRecipes().get(0).toString());
	  	
	  	}*/
	 

}