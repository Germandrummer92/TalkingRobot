package generalControl;

/**
 * 
 * @author Daniel Draper
 * @version 1.0
 * Represents a Phase in the flow of the whole DM system.
 */
public abstract class Phase {

  /**
   * Sets the Result of the current Phase in the Main class.
   * @param main the main class the system is running in currently
   */
  public abstract void setPhaseResult(Main main);

  /**
   * Returns the next Phase.
   * @param main the main class is system is running in currently
   * @return the next Phase.
   */
  public abstract Phase nextPhase(Main main);

}