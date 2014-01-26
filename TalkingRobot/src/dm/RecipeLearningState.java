package dm;

/**
 * This class represents the state a Recipe Learning Dialog is in.
 * @author Daniel Draper
 * @version 1.0
 *
 */
public class RecipeLearningState extends DialogState {


	/**
	 * Creates a new RecipeLearningState in the ENTRY state.
	 */
	public RecipeLearningState() {
		super();
		setCurrentState(RecipeLearning.RL_ENTRY);
	}
	
	/**
	 * Creates a new RecipeLearningState in the State specified
	 * @param currentState
	 */
	public RecipeLearningState(RecipeLearning currentState) {
		super();
		setCurrentState(currentState);
	}
	
  /**
   * @see DialogState#getOutputKeyword()
   */
	@Override
  public String getOutputKeyword() {
  return null;
  }


}