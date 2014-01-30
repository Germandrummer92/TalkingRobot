package dm;

import java.util.List;
/**
 * This class represents a kitchen Dialog and hold lists which contain all tools, ingredients
 * and recipes known to the system
 * @author Aleksandar Andonov
 * @version 1.0
 */
public abstract class KitchenDialog extends Dialog {

  private List<Tool> toolDatabase;

  private List<Ingredient> ingredientDatabase;

  private List<Recipe> recipeDatabase;
  
  private DialogModus dialogModus;
  
  /**
   * Creates a new Kitchen Dialog object in the given session and at the defined state.
   * @param session the current session (The session which this dialog is part of)
   * @param currState the state of the dialog
   */
  public KitchenDialog(Session session, DialogState currState) {
	  super(session, currState);
	  loadLists();
	  this.dialogModus = DialogModus.KITCHEN;
  }
  
  /**
   * Loads the listd of tool, recipes and ingredients from the system in order to
   * start a new kitchen Dialog
   */
  private void loadLists() {
	  toolDatabase = Tool.loadTools();
	  ingredientDatabase = Ingredient.loadIngredients();
	  recipeDatabase = Recipe.loadRecipes();
  }

public List<Tool> getToolDatabase() {
	return toolDatabase;
}

public void setToolDatabase(List<Tool> toolDatabase) {
	this.toolDatabase = toolDatabase;
}

public List<Ingredient> getIngredientDatabase() {
	return ingredientDatabase;
}

public void setIngredientDatabase(List<Ingredient> ingredientDatabase) {
	this.ingredientDatabase = ingredientDatabase;
}

public List<Recipe> getRecipeDatabase() {
	return recipeDatabase;
}

public void setRecipeDatabase(List<Recipe> recipeDatabase) {
	this.recipeDatabase = recipeDatabase;
}
  

}