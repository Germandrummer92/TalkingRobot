package dm;

import generalControl.Main;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import data.Data;
import data.KeywordType;
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
		this.dialogModus = DialogModus.START;
	}
	/**
	 * Creates a new Start Dialog using the current Session and State parameters
	 * @param currentSession the Session of the new Dialog
	 * @param currentState the State of the new Dialog
	 */
	public StartDialog(Session currentSession, DialogState currentState) {
		super(currentSession, currentState);
		this.dialogModus = DialogModus.START;
	}

	/** 
	 * @throws WrongStateClassException if the state class doesn't fit the dialog
	 * @see dm.Dialog#updateState(java.util.List, java.util.List)
	 */
	@Override
	public void updateState(List<Keyword> keywords, List<String> terms, List<String> approval)
			throws WrongStateClassException {
		updateStateKeywordJump(keywords);
			if (getCurrentDialogState().getClass() != StartState.class || getCurrentDialogState().getCurrentState().getClass() != Start.class) {
				throw new WrongStateClassException(getCurrentDialogState().getCurrentState().getClass().getName());
			}
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
		
	

	/**
	 * Updates the State according to the keywords passed. Jumps to Reference with highest Priority.
	 * @param keywords
	 * @return if the jump was completed
	 */
	private boolean updateStateKeywordJump(List<Keyword> keywords) {
		boolean employee = false;
		if (((Start)getCurrentDialogState().getCurrentState()).equals(Start.S_ENTRY)) {
			getCurrentDialogState().setCurrentState(Start.S_WAITING_FOR_USERNAME);
			return true;
		}
		if (((Start)getCurrentDialogState().getCurrentState()).equals(Start.S_WAITING_FOR_EMPLOYEE_STATUS)) {
			employee = true;
		}
		if (keywords == null || keywords.isEmpty()) {
			return false;
		}
		//Check if all keywords pointing to same state
		else {
			boolean sameRef = true;
			Enum<?> ref = keywords.get(0).getReference().get(0).getCurrentState();
			for (Keyword kw : keywords) {
				for (DialogState d : kw.getReference()) {
				if (!ref.equals(d.getCurrentState())) {
					sameRef = false;
				}
				}
			}
			if (sameRef == true) {
				getCurrentDialogState().setCurrentState(ref);
			}
			//If not go to keyword with highest priority
			else {
				int priorityMax = keywords.get(0).getKeywordData().getPriority();
				Keyword curKW = keywords.get(0);
				DialogState curRef = keywords.get(0).getReference().get(0);
				for (Keyword kw : keywords) {
					for (DialogState d : kw.getReference()) {
					if (kw.getKeywordData().getPriority() > priorityMax) {
						curKW = kw;
						priorityMax = curKW.getKeywordData().getPriority();
						curRef = d;
					}
					if (d.getCurrentState().getClass().getName().equals("dm.Start")) {
						if (kw.getKeywordData().getPriority() + 3 > priorityMax) {
							curKW = kw;
							priorityMax = curKW.getKeywordData().getPriority() + 3;
							curRef = d;
						}
						}
					}
					}
				getCurrentDialogState().setCurrentState(curRef.getCurrentState());
				}
			}
		if (employee && !getCurrentDialogState().getCurrentState().equals(Start.S_WAITING_FOR_EMPLOYEE_STATUS)) {
			getCurrentSession().getCurrentUser().getUserData().setStudent(true);
			getCurrentSession().getCurrentUser().getUserData().writeFile();	
			ArrayList<DialogState> states = new ArrayList<DialogState>();
			ArrayList<Data> refs = new ArrayList<Data>();
			states.add(new StartState(Start.S_USER_FOUND));
			refs.add(getCurrentSession().getCurrentUser().getUserData());
			DialogManager.giveDialogManager().getDictionary().addKeyword(getCurrentSession().getCurrentUser().getUserData().getUserName(), 10, states , refs, KeywordType.USER);
			Main.giveMain().setUserLoggedIn(true);
		}
		return true;
	}
	/**
	 * Updates the state if its in the waitingForEmployeeStatus state.
	 * @param keywords keywords passed
	 * @param terms terms passed
	 */
	private void updateStateWaitingEmployee(List<Keyword> keywords,
			List<String> terms, List<String> approval) {
		if ((keywords == null || keywords.isEmpty()) && (terms == null || terms.isEmpty() && (approval == null || approval.isEmpty()))) {
			getCurrentSession().getCurrentUser().getUserData().setStudent(true);
		}
		else if ((keywords == null || keywords.isEmpty()) && approval.size() == 1) {
			if (approval.get(0).equals("yes")) {
				getCurrentSession().getCurrentUser().getUserData().setStudent(true);
			}
			else {
				getCurrentSession().getCurrentUser().getUserData().setStudent(false);
			}
		}
		else if ((keywords != null && !keywords.isEmpty())) {
			for (Keyword kw: keywords) {
				if (kw.getWord().equals("student")) {
					getCurrentSession().getCurrentUser().getUserData().setStudent(true);
				}
				if (kw.getWord().equals("employee")) {
					getCurrentSession().getCurrentUser().getUserData().setStudent(false);
				}
			}
		}
		else {
			DialogManager.giveDialogManager().setInErrorState(true);
		}
		getCurrentDialogState().setCurrentState(Start.S_USER_SAVED);
		getCurrentSession().getCurrentUser().getUserData().writeFile();	
		ArrayList<DialogState> states = new ArrayList<DialogState>();
		ArrayList<Data> refs = new ArrayList<Data>();
		states.add(new StartState(Start.S_USER_FOUND));
		refs.add(getCurrentSession().getCurrentUser().getUserData());
		DialogManager.giveDialogManager().getDictionary().addKeyword(getCurrentSession().getCurrentUser().getUserData().getUserName(), 10, states , refs, KeywordType.USER);
		Main.giveMain().setUserLoggedIn(true);
		
	}

	/**
	 * Updates the state if its in the Exit state.
	 * @param keywords keywords passed
	 * @param terms terms passed
	 */
	private void updateStateExit(List<Keyword> keywords, List<String> terms) {
		//State never Reached
		
	}

	/**
	 * Updates the state if its in the UserWantsNoSave state.
	 * @param keywords keywords passed
	 * @param terms terms passed
	 */
	private void updateStateNoSave(List<Keyword> keywords, List<String> terms) {
			getCurrentDialogState().setCurrentState(Start.S_ENTRY);
			getCurrentSession().setCurrentUser(new User());
		
		
	}

	/**
	 * Updates the state if its in the UserWantsToBeSaved state.
	 * @param keywords keywords passed
	 * @param terms terms passed
	 */
	private void updateStateWantsSave(List<Keyword> keywords, List<String> terms) {
		//State never Reached
		
	}

	/**
	 * Updates the state if its in the UserWasSaved state.
	 * @param keywords keywords passed
	 * @param terms terms passed
	 */
	private void updateStateSaved(List<Keyword> keywords, List<String> terms) {
		//Nothing needed, Jumping due to Keyword.
		}
		

	/**
	 * Updates the state if its in the UserNotFound state.
	 * @param keywords keywords passed
	 * @param terms terms passed
	 */
	private void updateStateNotFound(List<Keyword> keywords, List<String> terms, List<String> approval) {
		if (approval.size() == 1) {
			if (approval.get(0).equals("yes") || approval.get(0).equals("Yes")) {
				getCurrentDialogState().setCurrentState(Start.S_WAITING_FOR_EMPLOYEE_STATUS);
				
			}
			else {
				getCurrentDialogState().setCurrentState(Start.S_USER_DOESNT_WANT_TO_BE_SAVED);
				getCurrentSession().setCurrentUser(new User());
			}
		}
		
	}

	/**
	 * Updates the state if its in the UserFound state.
	 * @param keywords keywords passed
	 * @param terms terms passed
	 */
	private void updateStateFound(List<Keyword> keywords, List<String> terms) {
		for (Keyword kw: keywords) {
			for (DialogState d : kw.getReference()) {
				if (d.getCurrentState() == Start.S_USER_FOUND) {
					getCurrentSession().setCurrentUser(new User((UserData)(kw.getKeywordData().getDataReference().get(0))));
					getCurrentSession().getCurrentUser().getUserData().setLastAccess(new Date());
					getCurrentSession().getCurrentUser().getUserData().writeFile();
					Main.giveMain().setUserLoggedIn(true);
					return;
				}
			}
		}
		DialogManager.giveDialogManager().setInErrorState(true);
		//Jumping due to keyword
		}

	/**
	 * Updates the state if its in the WaitingForUsername state.
	 * @param keywords keywords passed
	 * @param terms terms passed
	 */
	private void updateStateWaiting(List<Keyword> keywords, List<String> terms) {
			//Came here due to Keyword, no data handling necessary
		//If no Keyword jump, then data handling has to be done
		if (keywords == null || keywords.isEmpty()) {
			if (terms != null && !terms.isEmpty()) {
				getCurrentSession().setCurrentUser(new User(terms.get(0), false));
				getCurrentDialogState().setCurrentState(Start.S_USER_NOT_FOUND);
			}
		}
		if (keywords != null && !keywords.isEmpty()) {
			int allkws = 0;
			for (Keyword kw: keywords) {
				boolean refInc = false;
				for (DialogState ref : kw.getReference()) {
					if (ref.getCurrentState().equals(Start.S_WAITING_FOR_USERNAME)) {
						refInc = true;
					}
				}
				if (refInc) {
					allkws++;
				}
			}
			if (allkws == keywords.size()) {
				if (terms != null && !terms.isEmpty()) {
					getCurrentSession().setCurrentUser(new User(terms.get(0), false));
					getCurrentDialogState().setCurrentState(Start.S_USER_NOT_FOUND);
				}
			}
		}
	}

	/**
	 * Updates the state if its in the Entry state.
	 * @param keywords keywords passed
	 * @param terms terms passed
	 */
	private void updateStateEntry(List<Keyword> keywords, List<String> terms) {
		if (keywords == null || keywords.isEmpty()) {
			DialogManager.giveDialogManager().setInErrorState(true);
		}
		getCurrentDialogState().setCurrentState(Start.S_WAITING_FOR_USERNAME);
	}
}


