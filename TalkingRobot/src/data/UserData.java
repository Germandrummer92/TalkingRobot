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
 * @version 1.0
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
  private JSONObject userJSON;

  /**
   * Creates a new UserData object for a new User.
   * @param userID ID of the new User
   * @param userName Name of the new User
   * @param isStudent if the new User is a Student.
   */
  public UserData(int userID, String userName, boolean isStudent) {
	  this.userID = userID;
	  this.userName = userName;
	  this.isStudent = isStudent;
	  lastAccess = new Date();
	  requestedRecipes = new ArrayList<RecipeDatePair>();
	  acceptedSuggestions = new ArrayList<MealDatePair>();
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
	
	 
	 /* Test:
	    public static void main (String args[]) {
	  	UserData u = new UserData(0, "Daniel", true);
	  	ArrayList<RecipeDatePair> rd = u.getRequestedRecipes();
	  	rd.add(new RecipeDatePair(new Date(), new RecipeData()));
	  	u.setRequestedRecipes(rd);
	  	u.writeFile();
	  	Gson loader = new Gson();
	  	BufferedReader br = null;
	  	try {
			br = new BufferedReader(new FileReader("resources/files/UserData/0.json"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	  	UserData read = null;
	  		try {
				read = loader.fromJson(br.readLine(), UserData.class);
			} catch (JsonSyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  	System.out.println(read.getUserID());
	  	System.out.println(read.getLastAccess());
	  	System.out.println(read.getRequestedRecipes().get(0).toString());
	  	
	  	}*/
	 

}