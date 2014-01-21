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

  /**
 	 * @param session
 	 * @param dialogState
 	 */
 	public RecipeLearningDialog(Session session, DialogState dialogState) {
 		super(session, dialogState);
 		// TODO Auto-generated constructor stub
 	}
 	
@Override
public void updateState(List<String> keywords, List<String> terms,
		List<String> approval) throws WrongStateClassException {
	// TODO Auto-generated method stub
	
}

}