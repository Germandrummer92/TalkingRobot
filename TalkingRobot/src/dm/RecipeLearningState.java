package dm;

/**
 * This class represents the state a Recipe Learning Dialog is in.
 * @author Daniel Draper
 * @version 1.0
 *
 */
public class RecipeLearningState extends DialogState {

	private RecipeLearning currentState;

	/**
	 * Creates a new RecipeLearningState in the ENTRY state.
	 */
	public RecipeLearningState() {
		super();
		currentState = RecipeLearning.ENTRY;
	}
	
	/**
	 * Creates a new RecipeLearningState in the State specified
	 * @param currentState
	 */
	public RecipeLearningState(RecipeLearning currentState) {
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
public RecipeLearning getCurrentState() {
	return currentState;
}

/**
 * @param currentState the currentState to set
 */
public void setCurrentState(RecipeLearning currentState) {
	this.currentState = currentState;
}


}