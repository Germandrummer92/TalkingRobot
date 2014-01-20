package dm;
import java.util.List;

import data.UserData;

public class RecipeLearningDialog extends KitchenDialog {

  /**
	 * @param session
	 */
	public RecipeLearningDialog(Session session) {
		super(session);
		// TODO Auto-generated constructor stub
	}

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
public void updateState(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

}