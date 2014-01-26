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
	  	ErrorHandlingState errorHandlingState = null;
	  	if (main.getNluResult().get(0).isEmpty() 
	  			&& main.getNluResult().get(1).isEmpty() 
	  			&& main.getNluResult().get(3).isEmpty()) {
	  		errorHandlingState = dialogManager.handleError(main.getNluResult().get(2));
	  	} else if (dialogManager.getErrorState() != ErrorState.ZERO 
	  			&& main.getNluResult().get(0).isEmpty() 
	  			&& main.getNluResult().get(1).isEmpty()
	  			&& !main.getNluResult().get(3).isEmpty()) {
	  		/* in this case the system gave the user a choice or used a method of verification
	  		 * and expects an answer. Though, if the user answers with a sentence which contains
	  		 * a keyword or a term, this case can be ignored and the system can continue with its
	  		 * previous task.
	  		 */
	  		errorHandlingState = dialogManager.handleError(main.getNluResult().get(3));
	  	}


	  	dialogManager.updateDialog(main.getNluResult().get(0), main.getNluResult().get(1),
			main.getNluResult().get(3));
	  	if(errorHandlingState == null) {
		 	main.setDmResult(dialogManager.getCurrentDialog().getCurrentDialogState());
	  	} else {
	  		main.setDmResult(errorHandlingState);
	  	}
  }

  /**
   * @See {@link Phase#nextPhase(Main)}
   */
  protected Phase nextPhase(Main main) {
  		return new NLGPhase();
  }

}
