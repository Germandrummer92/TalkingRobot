package dm;
import java.util.List;

import data.UserData;

public class RecipeLearningDialog extends KitchenDialog {

  private UserData creator;

  private String countryOfOrigin;

  private List<Ingredient> ingredientsList;

  private List<Tool> toolsList;

  private String recipeName;

  private RecipeStep[] recipeSteps;

  private Integer numOfSteps;

  private void createRecipe() {
  }

@Override
public void updateState(List<String> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

}