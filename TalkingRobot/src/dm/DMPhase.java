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
<<<<<<< HEAD
	  //If nothing has been parsed, handle Error
	  if (main.getNluResult().get(0).isEmpty() && main.getNluResult().get(1).isEmpty() && main.getNluResult().get(3).isEmpty()) {
		  dialogManager.handleError(main.getNluResult().get(2));
	  }
	  //Convert List<String> to List<Keyword>
	  List<Keyword> kws = dialogManager.getDictionary().findKeywords(main.getNluResult().get(0));
	  //update the current Dialog
	  dialogManager.updateDialog(kws, main.getNluResult().get(1));
	  //set the Result
=======
	  dialogManager.updateDialog(main.getNluResult().get(0), main.getNluResult().get(1),
			  main.getNluResult().get(2));
>>>>>>> 72949fa36c389ac1d4c9ebf712cdfc771014857a
	  main.setDmResult(dialogManager.getCurrentDialog().getCurrentDialogState());
  }

  /**
   * @See {@link Phase#nextPhase(Main)}
   */
  protected Phase nextPhase(Main main) {
  		return new NLGPhase();
  }

}
