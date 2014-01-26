package dm;

/**
 * This class represents the different states a StartDialog can have.
 * @author Daniel Draper
 * @version 1.5
 *
 */
public class StartState extends DialogState {


	
	/**
	 * Creates a new StartState in the ENTRY state.
	 */
  public StartState() {
	  super();
	  setCurrentState(Start.S_ENTRY);
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
	  Dialog currentDialog = DialogManager.giveDialogManager().getCurrentDialog();
	  switch ((Start)getCurrentState()) {
	case S_ENTRY:
			setQuestion(false);
			return "<" + currentDialog.getCurrentSession().getCurrentRobot().getRobotData().getRobotName() + ">";
	case S_EXIT:
			setQuestion(false);
			return null;
	case S_USER_DOESNT_WANT_TO_BE_SAVED:
			setQuestion(false);
			return null;
	case S_USER_FOUND:
		setQuestion(true);
		return "<" + currentDialog.getCurrentSession().getCurrentUser().getUserData().getUserName() + ">";
	case S_USER_NOT_FOUND:
		setQuestion(true);
		return null;
	case S_USER_SAVED:
		setQuestion(true);
		return "<" + currentDialog.getCurrentSession().getCurrentUser().getUserData().getUserName() + ">";
	case S_USER_WANTS_TO_BE_SAVED:
		setQuestion(true);
		return "<" + currentDialog.getCurrentSession().getCurrentUser().getUserData().getUserName() + ">";
	case S_WAITING_FOR_EMPLOYEE_STATUS:
		setQuestion(false);
		return null;
	case S_WAITING_FOR_USERNAME:
			setQuestion(true);
			return null;
	default:
			return null;
	  }
  }


}