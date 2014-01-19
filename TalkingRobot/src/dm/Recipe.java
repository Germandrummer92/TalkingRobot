package dm;

import java.util.ArrayList;

import data.IngredientData;
import data.MealCategoryData;
import data.RecipeData;
import data.RecipeStepData;
import data.ToolData;

/**
 * This Class models a Recipe learned by the robot.
 * @author Daniel Draper
 * @version 1.0
 *
 */
public class Recipe {

  private RecipeData recipeData;
  
  /**
   * Creates a new Recipe from the specified parameters
   * @param name name of the recipe
   * @param ingredients Ingredients
   * @param recipeSteps Description of the Recipe Steps
   * @param tools Tools used
   * @param creator User entering the Recipe
   * @param origin Origin of the Recipe
   * @param category the Category it belongs to
   */
  public Recipe(String name, ArrayList<Ingredient> ingredients, ArrayList<RecipeStep> recipeSteps, ArrayList<Tool> tools, User creator, String origin, MealCategoryData category) {
	  ArrayList<IngredientData> ings = new ArrayList<IngredientData>();
	  ArrayList<RecipeStepData> rs = new ArrayList<RecipeStepData>();
	  ArrayList<ToolData> t = new ArrayList<ToolData>();
	  for (Ingredient i : ingredients) {
		  ings.add(i.getIngredientData());
	  }
	  for (RecipeStep r : recipeSteps) {
		  rs.add(r.getRecipeStepData());
	  }
	  for (Tool to : tools) {
		  t.add(to.getToolData());
	  }
	  recipeData = new RecipeData(name, ings, rs, t, creator.getUserData(), origin, category);
	  recipeData.writeFile();
  }

 /**
  * Creates a new Recipe from the data specified, only used by loadRecipes().
 * @param d
 */
  private Recipe(RecipeData d) {
	recipeData = d;
  }

/**
   * Returns true, if the Recipe contains the Ingredient specified by the parameter, false if not.
   * @param ingredient ingredient searched for
   * @return if the recipe contains it
   */
  public Boolean searchIngredient(Ingredient ingredient) {
  		return recipeData.getIngredients().contains(ingredient.getIngredientData());
  }

  /**
   * Returns true, if the Recipe contains the Tool specified by the parameter, false if not.
   * @param tool tool searched for
   * @return if the recipe contains it
   */  
  public Boolean searchTool(Tool tool) {
  		return recipeData.getTools().contains(tool.getToolData());
  }

  /**
   * 
   * @return the RecipeData of this recipe
   */
  public RecipeData getRecipeData() {
  		return recipeData;
  }

  /**
   * Loads the List of current Recipes known to the system from files. 
   * @return the List of current Recipes known to the system.
   */
  public static ArrayList<Recipe> loadRecipes() {
	  ArrayList<Recipe> res = new ArrayList<Recipe>();
	  for (RecipeData d : RecipeData.loadData()) {
		  res.add(new Recipe(d));
	  }
	  return res;
  }

}