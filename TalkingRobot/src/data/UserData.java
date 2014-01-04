package data;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import org.json.JSONObject;

public class UserData implements Data {

  private Integer userID;

  private String userName;

  private boolean isStudent;

  private ArrayList <RecipeDatePair>requestedRecipes;

  private ArrayList <MealDatePair> acceptedSuggestions;

  private Date lastAccess;

  private JSONObject userJSON;

    public Vector  myRecipeDatePair;
    public Vector  myMealDatePair;
	@Override
	public String generateJSON() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void createFromJSONText(String jsonString) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void writeFile() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public JSONObject getJson() {
		// TODO Auto-generated method stub
		return null;
	}

}