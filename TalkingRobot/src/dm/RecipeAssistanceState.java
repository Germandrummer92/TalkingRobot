package dm;

/**
 * This class represents the different States a Recipe Assistance Dialog can have.
 * @author Daniel Draper
 * @version 1.0
 *
 */
public class RecipeAssistanceState extends DialogState {

	
  /**
   * Creates a new Recipe Assistance State in the ENTRY state.
   */
  public RecipeAssistanceState() {
	  super();
	 setCurrentState(RecipeAssistance.ENTRY);
  }
  
  /**
   * Creates a new Recipe Assistance State in the state specified.
   * @param currentState
   */
  public RecipeAssistanceState(RecipeAssistance currentState) {
	  super();
	  setCurrentState(currentState);
  }
	
  /**
   * @see DialogState#getOutputKeyword()
   */
  public String getOutputKeyword() {
  return null;
  }



}