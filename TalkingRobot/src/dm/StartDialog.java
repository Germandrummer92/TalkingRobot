package dm;

import generalControl.Main;

import java.util.List;

import data.UserData;

/**
 * @author Daniel Draper
 * @version 1.5
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
	 * Creates a new Start Dialog using the current Session and State parameters
	 * @param currentSession the Session of the new Dialog
	 * @param currentState the State of the new Dialog
	 */
	public StartDialog(Session currentSession, DialogState currentState) {
		super(currentSession, currentState);
	}

	/** 
	 * @throws WrongStateClassException if the state class doesn't fit the dialog
	 * @see dm.Dialog#updateState(java.util.List, java.util.List)
	 */
	@Override
	public void updateState(List<Keyword> keywords, List<String> terms, List<String> approval)
			throws WrongStateClassException {
		//needed for Cast in switch statement
		if (getCurrentDialogState().getClass() != StartState.class) {
			throw new WrongStateClassException(getCurrentDialogState().getClass().getName());
		}
		if (!updateStateKeywordJump(keywords)) { 
		
		switch ((Start)getCurrentDialogState().getCurrentState()) {
		case S_ENTRY : updateStateEntry(keywords, terms); break;
		case S_WAITING_FOR_USERNAME : updateStateWaiting(keywords, terms); break;
		case S_USER_FOUND : updateStateFound(keywords, terms); break;
		case S_WAITING_FOR_EMPLOYEE_STATUS : updateStateWaitingEmployee(keywords, terms, approval); break;
		case S_USER_NOT_FOUND : updateStateNotFound(keywords, terms, approval); break;
		case S_USER_SAVED : updateStateSaved(keywords, terms); break;
		case S_USER_WANTS_TO_BE_SAVED : updateStateWantsSave(keywords, terms); break;
		case S_USER_DOESNT_WANT_TO_BE_SAVED : updateStateNoSave(keywords, terms); break;
		case S_EXIT : updateStateExit(keywords, terms); break;
		default: break;
		}
		}
		//needed again to check for possible jumps due to updateStateKeywordJump()
		if (getCurrentDialogState().getClass() != StartState.class) {
			throw new WrongStateClassException(getCurrentDialogState().getClass().getName());
		}
	}

	/**
	 * @param keywords
	 * @return
	 */
	private boolean updateStateKeywordJump(List<Keyword> keywords) {
		if (keywords.isEmpty()) {
			return false;
		}
		else {
			Enum<?> ref = keywords.get(0).getReference().getCurrentState();
			for (Keyword kw : keywords) {
				if (!ref.equals(kw.getReference().getCurrentState())) {
					return false;
				}
			}
			getCurrentDialogState().setCurrentState(ref);
			return true;
		}
	}
	/**
	 * @param keywords
	 * @param terms
	 */
	private void updateStateWaitingEmployee(List<Keyword> keywords,
			List<String> terms, List<String> approval) {
		if (keywords.isEmpty() && terms.isEmpty() && approval.size() == 1) {
			if (approval.get(0).equals("Yes")) {
				getCurrentDialogState().setCurrentState(Start.S_USER_SAVED);
				getCurrentSession().getCurrentUser().getUserData().setStudent(true);
				getCurrentSession().getCurrentUser().getUserData().writeFile();
			}
			else {
				getCurrentDialogState().setCurrentState(Start.S_USER_SAVED);
				getCurrentSession().getCurrentUser().getUserData().setStudent(false);
				getCurrentSession().getCurrentUser().getUserData().writeFile();
			}
		}
		Main.giveMain().setUserLoggedIn(true);
		
	}

	/**
	 * @param keywords
	 * @param terms
	 */
	private void updateStateExit(List<Keyword> keywords, List<String> terms) {
		//State never Reached
		
	}

	/**
	 * @param keywords
	 * @param terms
	 */
	private void updateStateNoSave(List<Keyword> keywords, List<String> terms) {
			getCurrentDialogState().setCurrentState(Start.S_ENTRY);
			getCurrentSession().setCurrentUser(new User());
		
		
	}

	/**
	 * @param keywords
	 * @param terms
	 */
	private void updateStateWantsSave(List<Keyword> keywords, List<String> terms) {
		//State never Reached
		
	}

	/**
	 * @param keywords
	 * @param terms
	 */
	private void updateStateSaved(List<Keyword> keywords, List<String> terms) {
		//Nothing needed, Jumping due to Keyword.
		}
		

	/**
	 * @param keywords
	 * @param terms
	 */
	private void updateStateNotFound(List<Keyword> keywords, List<String> terms, List<String> approval) {
		if (keywords.isEmpty() && terms.isEmpty() && approval.size() == 1) {
			if (approval.get(0).equals("yes")) {
				getCurrentDialogState().setCurrentState(Start.S_WAITING_FOR_EMPLOYEE_STATUS);
				
			}
			else {
				getCurrentDialogState().setCurrentState(Start.S_USER_DOESNT_WANT_TO_BE_SAVED);
				getCurrentSession().setCurrentUser(new User());
			}
		}
		
	}

	/**
	 * @param keywords
	 * @param terms
	 */
	private void updateStateFound(List<Keyword> keywords, List<String> terms) {
		//Jumping due to keyword
		}

		

	/**
	 * @param keywords
	 * @param terms
	 */
	private void updateStateWaiting(List<Keyword> keywords, List<String> terms) {
			for (Keyword kw: keywords) {
				if (kw.getReference().getCurrentState() == Start.S_USER_FOUND) {
					getCurrentDialogState().setCurrentState(Start.S_USER_FOUND);
					getCurrentSession().setCurrentUser(new User((UserData)(kw.getKeywordData().getDataReference())));
					Main.giveMain().setUserLoggedIn(true);
					return;
				}
			}
		if (terms.size() == 1) {
			for (String s : terms) {
				getCurrentDialogState().setCurrentState(Start.S_USER_NOT_FOUND);
				getCurrentSession().getCurrentUser().getUserData().setUserName(s);
				
			}
		}
	}

	/**
	 * @param keywords
	 * @param terms
	 */
	private void updateStateEntry(List<Keyword> keywords, List<String> terms) {
		for (Keyword kw: keywords) {
				if (kw.getReference().getCurrentState() == Start.S_WAITING_FOR_USERNAME) {
					getCurrentDialogState().setCurrentState(Start.S_WAITING_FOR_USERNAME);
					return;
				}
			}
		}
		
	}


