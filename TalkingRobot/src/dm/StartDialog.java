package dm;

import java.util.List;

import data.UserData;

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
	public void updateState(List<Keyword> keywords, List<String> terms) throws WrongStateClassException {
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
	private void updateStateExit(List<Keyword> keywords, List<String> terms) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param keywords
	 * @param terms
	 */
	private void updateStateNoSave(List<Keyword> keywords, List<String> terms) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param keywords
	 * @param terms
	 */
	private void updateStateWantsSave(List<Keyword> keywords, List<String> terms) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param keywords
	 * @param terms
	 */
	private void updateStateSaved(List<Keyword> keywords, List<String> terms) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param keywords
	 * @param terms
	 */
	private void updateStateNotFound(List<Keyword> keywords, List<String> terms) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param keywords
	 * @param terms
	 */
	private void updateStateFound(List<Keyword> keywords, List<String> terms) {
		for (Keyword kw: keywords) {
			if (kw.getReference() instanceof RecipeAssistanceState) {
				if (((RecipeAssistanceState)kw.getReference()).getCurrentState() == RecipeAssistance.ENTRY) {
					((StartState)getCurrentDialogState()).setCurrentState(Start.EXIT);
					return;
				}
			}
		}
		
	}

	/**
	 * @param keywords
	 * @param terms
	 */
	private void updateStateWaiting(List<Keyword> keywords, List<String> terms) {
		for (Keyword kw: keywords) {
			if (kw.getReference() instanceof StartState) {
				if (((StartState)kw.getReference()).getCurrentState() == Start.USER_FOUND) {
					((StartState)getCurrentDialogState()).setCurrentState(Start.USER_FOUND);
					getCurrentSession().setCurrentUser(new User((UserData)(kw.getKeywordData().getDataReference())));
					return;
				}
			}
		}
		
	}

	/**
	 * @param keywords
	 * @param terms
	 */
	private void updateStateEntry(List<Keyword> keywords, List<String> terms) {
		for (Keyword kw: keywords) {
			if (kw.getReference() instanceof StartState) {
				if (((StartState)kw.getReference()).getCurrentState() == Start.WAITING_FOR_USERNAME) {
					((StartState)getCurrentDialogState()).setCurrentState(Start.WAITING_FOR_USERNAME);
					return;
				}
			}
		}
		/*for (Keyword kw: keywords) {
			((kw.getReference().getClass())getCurrentDialogState()).setCurrentState(kw.getReference().getClass().getName().)
		}*/
		
	}

}
