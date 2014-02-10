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
	  	
	  	main.setDmResult(null);
	  	int i =0;
	  	for (String s : Main.giveMain().getNluResult().get(0)) {
	  		System.out.println("Keyword" + " " + i + ": " + s);
	  		i++;
	  	}
	  	if (dialogManager.getErrorState() != ErrorState.ZERO 
	  			&& main.getNluResult().get(0).isEmpty() 
	  			&& !main.getNluResult().get(3).isEmpty()) {
	  		/* in this case the system gave the user a choice or used a method of verification
	  		 * and expects an answer. Though, if the user answers with a sentence which contains
	  		 * a keyword, this case can be ignored and the system can continue with its
	  		 * previous task.
	  		 */
	  		dialogManager.handleError(main.getNluResult().get(3));
	  	} else {
		  	dialogManager.updateDialog(main.getNluResult().get(0), main.getNluResult().get(1),
				main.getNluResult().get(3));
	  	}
		
		//this is needed because ErrorHandlingState will not be saved in the dialog history
		//thus it will not be saved as current dialogstate
		if(main.getDmResult() == null) {
	  		main.setDmResult(dialogManager.getCurrentDialog().getCurrentDialogState());
	  	}
  }

  /**
   * @See {@link Phase#nextPhase(Main)}
   */
  public Phase nextPhase(Main main) {
  		return new NLGPhase();
  }

}
