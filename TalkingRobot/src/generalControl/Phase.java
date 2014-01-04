package generalControl;

public abstract class Phase {

  
  public abstract void setPhaseResult(Main main);

  protected abstract Phase nextPhase(Main main);

}