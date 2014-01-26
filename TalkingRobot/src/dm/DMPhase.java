package dm;

import java.util.List;

import generalControl.Main;
import generalControl.Phase;

import nlg.NLGPhase;

/**
 * The Phase responsible for switching the state of the current Dialog according to the input.
 * @author Daniel Draper
 * @version 1.0
 *
 */
public class DMPhase extends Phase {

  private DialogManager dialogManager;

  /**
   * creates the DMPhase and DialogManager used by it.
   */
  public DMPhase() {
	  dialogManager = DialogManager.giveDialogManager();
  }
  /**
   * @See {@link Phase#setPhaseResult(Main)}
   */
  public void setPhaseResult(Main main) {
	  //If nothing has been parsed, handle Error
	  if (main.getNluResult().get(0).isEmpty() && main.getNluResult().get(1).isEmpty() && main.getNluResult().get(3).isEmpty()) {
		  dialogManager.handleError(main.getNluResult().get(2));
	  }


	  dialogManager.updateDialog(main.getNluResult().get(0), main.getNluResult().get(1),
			  main.getNluResult().get(3));
	  main.setDmResult(dialogManager.getCurrentDialog().getCurrentDialogState());
  }

  /**
   * @See {@link Phase#nextPhase(Main)}
   */
  protected Phase nextPhase(Main main) {
  		return new NLGPhase();
  }

}
