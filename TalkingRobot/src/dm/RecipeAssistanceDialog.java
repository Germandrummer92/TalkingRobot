package dm;

import java.util.List;

public class RecipeAssistanceDialog extends KitchenDialog {

	private Recipe currRecipe;

	
	/**
	 * @param session
	 */
	public RecipeAssistanceDialog(Session session, DialogState dialogState) {
		super(session, dialogState);
		// TODO Auto-generated constructor stub
	}

  public Recipe getCurrRecipe() {
  return null;
  }

@Override
public void updateState(List<String> keywords, List<String> terms,
		List<String> approval) throws WrongStateClassException {
	// TODO Auto-generated method stub
	
}

}