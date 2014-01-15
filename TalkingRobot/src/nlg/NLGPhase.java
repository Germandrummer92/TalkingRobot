package nlg;
import dm.DialogState;
import generalControl.Main;
import generalControl.Phase;


public class NLGPhase extends Phase {

  private OutputCreator outputCreator;

  public void setPhaseResult(Main main) {
	  DialogState currentState = main.getDmResult();
	  String nlgResult = outputCreator.createOutput(currentState);
	  main.setNlgResult(nlgResult);
  }

  protected Phase nextPhase(Main main) {
  return null;
  }

}