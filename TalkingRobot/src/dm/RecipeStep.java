package dm;


import data.RecipeStepData;

/**
 * This Class models a step in a recipe learned by the robot.
 * @author Daniel Draper
 * @version 1.0
 *
 */
public class RecipeStep {

  private RecipeStepData recipeStepData;

  /**
   * Creates a new Robot and saves its data.
   * @param robotName
   */
  public RecipeStep(String description) {
	  recipeStepData = new RecipeStepData(description);
  }
  
  /**
  /**
   * @return the RecipeStepData
   */
  public RecipeStepData getRecipeStepData() {
	  return recipeStepData;
  }

}