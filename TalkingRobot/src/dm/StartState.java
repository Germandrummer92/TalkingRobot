package dm;

/**
 * This class represents the different states a StartDialog can have.
 * @author Daniel Draper
 * @version 1.0
 *
 */
public class StartState extends DialogState {


	
	/**
	 * Creates a new StartState in the ENTRY state.
	 */
  public StartState() {
	  super();
	  setCurrentState(Start.ENTRY);
  }
  /**
   * Creates a new StartState in the state specified.
   * @param the state of Start
   */
  public StartState(Start currentState) {
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