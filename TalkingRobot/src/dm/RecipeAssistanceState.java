package dm;

/**
 * This class represents the different States a Recipe Assistance Dialog can have.
 * @author Daniel Draper
 * @version 1.0
 *
 */
public class RecipeAssistanceState extends DialogState {

  private RecipeAssistance currentState;
	
  /**
   * Creates a new Recipe Assistance State in the ENTRY state.
   */
  public RecipeAssistanceState() {
	  super();
	  currentState = RecipeAssistance.ENTRY;
  }
  
  /**
   * Creates a new Recipe Assistance State in the state specified.
   * @param currentState
   */
  public RecipeAssistanceState(RecipeAssistance currentState) {
	  super();
	  this.currentState = currentState;
  }
	
  /**
   * @see DialogState#getOutputKeyword()
   */
  public String getOutputKeyword() {
  return null;
  }


/**
 * @return the currentState
 */
public RecipeAssistance getCurrentState() {
	return currentState;
}

/**
 * @param currentState the currentState to set
 */
public void setCurrentState(RecipeAssistance currentState) {
	this.currentState = currentState;
}

}