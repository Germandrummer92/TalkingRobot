package nlg;
import dm.DialogState;
import generalControl.Main;
import generalControl.Phase;

/**
 * 
 * @author Luiz Henrique Soares Silva
 *
 */
public class NLGPhase extends Phase {

  private OutputCreator outputCreator;

  public void setPhaseResult(Main main) {
	  DialogState currentState = main.getDmResult();
	  String nlgResult = outputCreator.createOutput(currentState);
	  main.setNlgResult(nlgResult);
  }

  public Phase nextPhase(Main main) {
	  return main.getDmPhase();
  }

}