package asr;
import nlu.NLUPhase;
import generalControl.Main;
import generalControl.Phase;

/**
 * ASRPhase, responsible for communicating with JRTK through the InputGetter.
 * @author Daniel Draper
 * @version 1.0
 *
 */
public class ASRPhase extends Phase {

	private One4AllAdapter one4all;
  /**
   *  @See {@link Phase#setPhaseResult(Main)}
   * @param main the main on which the current system is operating on.
   */
  public void setPhaseResult(Main main) {
	  one4all = new One4AllAdapter();
	  one4all.operateOne4All();
	  main.setAsrResult(new InputGetter().getInput());
  }

  /**
   * @See {@link Phase#nextPhase(Main)}
   * @return the next Phase.
   */
  public Phase nextPhase(Main main) {
  	if(main.getAsrResult() == null || main.getAsrResult().isEmpty()) {
		  return new ASRPhase();
	 }
	 return new NLUPhase();
  }

}
