package dm;

import java.util.ArrayList;

import data.IngredientData;

/**
 * Models an Ingredient learned by the system.
 * @author Daniel Draper
 * @version 1.0
 *
 */
public class Ingredient {

  private IngredientData ingredientData;

  /**
   * Creates a new Ingredient with the name specified, and saves it to a file.
   * @param name name of the new Ingredient
   */
  public Ingredient(String name, String location) {
	  ingredientData =  new IngredientData(name, location);
	  ingredientData.writeFile();
  }
  /**
   * Creates an Ingredient from the Data specified, just used by the loadIngredients() method.
 * @param d
 */
  public Ingredient(IngredientData d) {
	  ingredientData = d;
 }

/**
   * 
   * @return the Data of this Ingredient
   */
  public IngredientData getIngredientData() {
  		return ingredientData;
  }

  /**
   * Loads the List of current Ingredients known to the system from files. 
   * @return the List of current Ingredients known to the system.
   */
  public static ArrayList<Ingredient> loadIngredients() {
	  ArrayList<Ingredient> res = new ArrayList<Ingredient>();
	  for (IngredientData d : IngredientData.loadData()) {
		  res.add(new Ingredient(d));
	  }
	  return res;
  }
}