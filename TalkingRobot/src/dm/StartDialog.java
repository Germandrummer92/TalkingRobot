package dm;

import java.util.List;

/**
 * @author Daniel Draper
 * @version 1.0
 * Represents the Dialog started at the start of any conversation with a certain User.
 */
public class StartDialog extends Dialog {

	/**
	 * Creates a new StartDialog, using
	 * @param currentSession the Session this dialog runs on.
	 */
	public StartDialog(Session currentSession) {
		super(currentSession);
		
	}

	/** 
	 * @throws WrongStateClassException if the state class doesn't fit the dialog
	 * @see dm.Dialog#updateState(java.util.List, java.util.List)
	 */
	@Override
	public void updateState(List<String> keywords, List<String> terms, List<String> approval)
			throws WrongStateClassException {
		StartState currentStateStart;
		if (getCurrentDialogState().getClass() != StartState.class) {
			throw new WrongStateClassException();
		}
		else {
			currentStateStart = ((StartState)getCurrentDialogState());
		}
		switch (currentStateStart.getCurrentState()) {
		case ENTRY : updateStateEntry(keywords, terms); break;
		case WAITING_FOR_USERNAME : updateStateWaiting(keywords, terms); break;
		case USER_FOUND : updateStateFound(keywords, terms); break;
		case WAITING_FOR_EMPLOYEE_STATUS : updateStateWaiting(keywords, terms); break;
		case USER_NOT_FOUND : updateStateNotFound(keywords, terms); break;
		case USER_SAVED : updateStateSaved(keywords, terms); break;
		case USER_WANTS_TO_BE_SAVED : updateStateWantsSave(keywords, terms); break;
		case USER_DOESNT_WANT_TO_BE_SAVED : updateStateNoSave(keywords, terms); break;
		case EXIT : updateStateExit(keywords, terms); break;
		default: break;
		}

	}

	/**
	 * @param keywords
	 * @param terms
	 */
	private void updateStateExit(List<String> keywords, List<String> terms) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param keywords
	 * @param terms
	 */
	private void updateStateNoSave(List<String> keywords, List<String> terms) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param keywords
	 * @param terms
	 */
	private void updateStateWantsSave(List<String> keywords, List<String> terms) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param keywords
	 * @param terms
	 */
	private void updateStateSaved(List<String> keywords, List<String> terms) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param keywords
	 * @param terms
	 */
	private void updateStateNotFound(List<String> keywords, List<String> terms) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param keywords
	 * @param terms
	 */
	private void updateStateFound(List<String> keywords, List<String> terms) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param keywords
	 * @param terms
	 */
	private void updateStateWaiting(List<String> keywords, List<String> terms) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param keywords
	 * @param terms
	 */
	private void updateStateEntry(List<String> keywords, List<String> terms) {
		// TODO Auto-generated method stub
		
	}

}
