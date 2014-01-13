package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

/**
 * 
 * @author Luiz Henrique. Soares Silva, Aleksandar Andonov
 * @version 1.1
 * This class describes and implements a Recipe Data.
 *
 */
public class RecipeData implements Data {

	private int recipeID;

	private String recipeName;

	private Integer numOfSteps;

	private ArrayList<IngredientData> ingredients;

	private ArrayList<RecipeStepData> steps;

	private ArrayList<ToolData> tools;
	
	private UserData creator;
	
	private String originalCountry;
	
	private MealCategoryData mealCategory;

	public RecipeData(String recipeName, ArrayList<IngredientData> ingredients,
			ArrayList<RecipeStepData> steps, ArrayList<ToolData> tools, UserData creator,
			String originalCountry, MealCategoryData mealCategory) {
		recipeID = nextID();
		this.recipeName = recipeName;
		if (steps != null) {
			numOfSteps = steps.size();
		}
		this.ingredients = ingredients;
		this.steps = steps;
		this.tools = tools;
		this.creator = creator;
		this.originalCountry = originalCountry;
		this.mealCategory = mealCategory;
	}
	
	public RecipeData(String jsonString) {
		this.createFromJSONText(jsonString);
	}
	
@Override
	public String generateJSON() {
		Gson creator = new Gson();
		return creator.toJson(this);
	}

	@Override
	public void createFromJSONText(String jsonString) {
		Gson creator;
		creator =  new Gson();
		RecipeData newData = creator.fromJson(jsonString, this.getClass());
		this.recipeID = newData.getRecipeID();
		this.recipeName = newData.getRecipeName();
		this.numOfSteps = newData.getNumOfSteps();
		this.ingredients = newData.getIngredients();
		this.steps = newData.getSteps();
		this.tools = newData.getTools();
		this.creator = newData.getCreator();
		this.originalCountry = newData.getOriginalCountry();
		this.mealCategory = newData.getMealCategory();
	}
	
	@Override
	public void writeFile() {
		String pathname = "resources/files/RecipeData/" + recipeID + ".json";
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
	public JSONObject getJson() {
		JSONObject object = null;
	    try {
	            object = new JSONObject(this.generateJSON());
	    } catch (JSONException e) {
	            e.printStackTrace();
	    }
	    return object;
	}
	
	public Integer getRecipeID() {
		return recipeID;
	}
	
	public void setRecipeID(Integer recipeID) {
		this.recipeID = recipeID;
	}
	
	public String getRecipeName() {
		return recipeName;
	}
	
	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}
	
	public Integer getNumOfSteps() {
		return numOfSteps;
	}
	
	public void setNumOfSteps(Integer numOfSteps) {
		this.numOfSteps = numOfSteps;
	}
	
	public ArrayList<IngredientData> getIngredients() {
		return ingredients;
	}
	
	public void setIngredients(ArrayList<IngredientData> ingredients) {
		this.ingredients = ingredients;
	}
	
	public ArrayList<RecipeStepData> getSteps() {
		return steps;
	}
	
	public void setSteps(ArrayList<RecipeStepData> steps) {
		this.steps = steps;
	}
	
	public ArrayList<ToolData> getTools() {
		return tools;
	}
	
	public void setTools(ArrayList<ToolData> tools) {
		this.tools = tools;
	}
	
	public UserData getCreator() {
		return creator;
	}
	
	public void setCreator(UserData creator) {
		this.creator = creator;
	}
	
	public String getOriginalCountry() {
		return originalCountry;
	}
	
	public void setOriginalCountry(String originalCountry) {
		this.originalCountry = originalCountry;
	}
	
	public MealCategoryData getMealCategory() {
		return mealCategory;
	}
	
	public void setMealCategory(MealCategoryData mealCategory) {
		this.mealCategory = mealCategory;
	}
	
	/**
	 * Deletes the file associated with this object (if existing) and retains the
	 * id numeration.
	 */
	public void deleteFile() {
		String dirPath = "resources/files/RecipeData/" + recipeID + ".json";
		File currFile = new File(dirPath + recipeID + ".json");
		if (!currFile.exists()) {
			return; //there is nothing to delete
		}
		
		String jsonString = ""; //the json String representation of the last File
		RecipeData lastRecipeData;
		File f = new File(dirPath);
		int lastID = f.listFiles().length - 1; //with 2 files, last ID 1 -> -1
		File lastFile = new File("resources/files/RecipeData/" + lastID + ".json");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(lastFile));
			
			String lastReadLine = reader.readLine();
			while (lastReadLine != null) {
				jsonString += lastReadLine;
				lastReadLine = reader.readLine();
			} //Get the json String representation of the last File
			lastRecipeData = new RecipeData(jsonString); //load the last recipe
			lastRecipeData.setRecipeID(recipeID); //set the last recipe's ID to the curr one
			lastRecipeData.writeFile(); //overwrite the file to be deleted with the last one
			reader.close(); //close reader in order to delete the last file
			lastFile.delete(); //deletes the last file as it is already written at the currFile's id
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecipeData other = (RecipeData) obj;
		if (creator == null) {
			if (other.creator != null)
				return false;
		} else if (!creator.equals(other.creator))
			return false;
		if (ingredients == null) {
			if (other.ingredients != null)
				return false;
		} else if (!ingredients.equals(other.ingredients))
			return false;
		if (mealCategory == null) {
			if (other.mealCategory != null)
				return false;
		} else if (!mealCategory.equals(other.mealCategory))
			return false;
		if (numOfSteps == null) {
			if (other.numOfSteps != null)
				return false;
		} else if (!numOfSteps.equals(other.numOfSteps))
			return false;
		if (originalCountry == null) {
			if (other.originalCountry != null)
				return false;
		} else if (!originalCountry.equals(other.originalCountry))
			return false;
		if (recipeID != other.recipeID)
			return false;
		if (recipeName == null) {
			if (other.recipeName != null)
				return false;
		} else if (!recipeName.equals(other.recipeName))
			return false;
		if (steps == null) {
			if (other.steps != null)
				return false;
		} else if (!steps.equals(other.steps))
			return false;
		if (tools == null) {
			if (other.tools != null)
				return false;
		} else if (!tools.equals(other.tools))
			return false;
		return true;
	}

	/**
	 * @return the next unique ID
	 */
	private int nextID() {
		File f = new File("resources/files/RecipeData/");
		return f.listFiles().length;
	}
	/* //TEST:
	public static void main (String args[]) {
	  	
	  	RecipeData spags = new RecipeData("spags", null, null, null, null, "Italy", null);
		//spags.writeFile();
	  	//File newFile = new File("resources/files/RecipeData/0.json");
	  	//System.out.println(newFile.exists());
		RecipeData reis = new RecipeData("rice", null, null, null, null, "China", null);
		reis.writeFile();
		
		spags.deleteFile();
	  	}
	*/	
}