package dm;

import generalControl.Main;

import java.util.List;

import data.Data;
import data.IngredientData;
import data.ToolData;

/**
 * 
 * @author Daniel Draper
 * @version 1.0
 * This class represents a KitchenAssistanceDialog responsible for the locating of ingredients or Tools in the Kitchen.
 */
public class KitchenAssistanceDialog extends KitchenDialog {

	private AssistanceState stateOfAssistance;
	private Object requestedObject;
	private String requestedObjectName;
	
  /**
	 * @param session the Current Session
	 * @param dialogState the dialogState of the new Dialog.
	 */
	public KitchenAssistanceDialog(Session session, DialogState dialogState) {
		super(session, dialogState);
		this.dialogModus = DialogModus.KITCHEN_ASSISTANCE;
	}

@Override
/**
 * @See Dialog#updateState(keywords, terms, approval) throws WrongStateClassException
 */
public void updateState(List<Keyword> keywords, List<String> terms,
		List<String> approval) throws WrongStateClassException {
	updateStateKeywordJump(keywords);
	if (getCurrentDialogState().getClass() != KitchenAssistanceState.class || getCurrentDialogState().getCurrentState().getClass() != KitchenAssistance.class) {
		throw new WrongStateClassException(getCurrentDialogState().getCurrentState().getClass().getName());
	}
	
	switch ((KitchenAssistance)getCurrentDialogState().getCurrentState()) {
	case KA_ENTRY:
		updateStateEntry(keywords, terms);
		break;
	case KA_EXIT:
		updateStateExit(keywords, terms);
		break;
	case KA_TELL_INGREDIENT_FOUND:
		updateStateIngFound(keywords, terms);
		break;
	case KA_TELL_INGREDIENT_NOT_FOUND:
		updateStateIngNotFound(keywords, terms);
		break;
	case KA_TELL_TOOL_FOUND:
		updateStateToolFound(keywords, terms);
		break;
	case KA_TELL_TOOL_NOT_FOUND:
		updateStateToolNotFound(keywords, terms);
		break;
	default:
		break;
	}
	
}

/**
 * Updates the State according to the keywords passed. Jumps to Reference with highest Priority.
 * @param keywords
 * @return if the jump was completed
 */
private boolean updateStateKeywordJump(List<Keyword> keywords) {
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
			return true;
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
				}
				}
			getCurrentDialogState().setCurrentState(curRef.getCurrentState());
			return true;
			}
		}
}

/**
 * Updates the state if its in the ToolNotFound state.
 * @param keywords keywords passed
 * @param terms terms passed
 */
private void updateStateToolNotFound(List<Keyword> keywords, List<String> terms) {
	if ( terms != null && !terms.isEmpty()) {
		requestedObjectName = terms.get(0);
	}
	else {
		DialogManager.giveDialogManager().setInErrorState(true);
	}
}

/**
 * Updates the state if its in the ToolFound state.
 * @param keywords keywords passed
 * @param terms terms passed
 */
private void updateStateToolFound(List<Keyword> keywords, List<String> terms) {
	if (keywords != null && !keywords.isEmpty()) {
	for (Keyword kw : keywords) {
		for (Data ref: kw.getKeywordData().getDataReference()) {
		if (ref.getClass().getName().equals("data.ToolData")) {
			requestedObject = new Tool((ToolData)kw.getKeywordData().getDataReference().get(0));
			requestedObjectName = ((Tool)requestedObject).getToolData().getToolName();
			return;
		}
		}
	}
	}
	DialogManager.giveDialogManager().setInErrorState(true);
	
}

/**
 * Updates the state if its in the IngredientNotFound state.
 * @param keywords keywords passed
 * @param terms terms passed
 */
private void updateStateIngNotFound(List<Keyword> keywords, List<String> terms) {
	//Not needed, only one State not found needed
	
}

/**
 * Updates the state if its in the IngredientFound state.
 * @param keywords keywords passed
 * @param terms terms passed
 */
private void updateStateIngFound(List<Keyword> keywords, List<String> terms) {
	if (keywords != null && !keywords.isEmpty()) {
	for (Keyword kw : keywords) {
		for (Data ref: kw.getKeywordData().getDataReference()) {
			if (ref.getClass().getName().equals("data.IngredientData")) {
			requestedObject = new Ingredient((IngredientData)kw.getKeywordData().getDataReference().get(0));
			requestedObjectName = ((Ingredient)requestedObject).getIngredientData().getIngredientName();
			return;
		}
		}
	}
	}
	DialogManager.giveDialogManager().setInErrorState(true);
	
}

/**
 * Updates the state if its in the Exit state.
 * @param keywords keywords passed
 * @param terms terms passed
 */
private void updateStateExit(List<Keyword> keywords, List<String> terms) {
	//State should never be reached
}

/**
 * Updates the state if its in the Entry state.
 * @param keywords keywords passed
 * @param terms terms passed
 */
private void updateStateEntry(List<Keyword> keywords, List<String> terms) {
	//only invoked if not already jumped to TOOL_" or ING_FOUND
	if (!terms.isEmpty()) {
		requestedObjectName = terms.get(0);
		getCurrentDialogState().setCurrentState(KitchenAssistance.KA_TELL_TOOL_NOT_FOUND);
	}
	else {
		DialogManager.giveDialogManager().setInErrorState(true);
	}
	
	
	
}

/**
 * @return the requestedObject
 */
public Object getRequestedObject() {
	return requestedObject;
}

/**
 * @param requestedObject the requestedObject to set
 */
public void setRequestedObject(Object requestedObject) {
	this.requestedObject = requestedObject;
}

/**
 * @return the stateOfAssistance
 */
public AssistanceState getStateOfAssistance() {
	return stateOfAssistance;
}

/**
 * @param stateOfAssistance the stateOfAssistance to set
 */
public void setStateOfAssistance(AssistanceState stateOfAssistance) {
	this.stateOfAssistance = stateOfAssistance;
}

/**
 * @return the name of the requested Object, needed in case no Object can be created as it wasn't found.
 */
public String getRequestedObjectName() {
	return requestedObjectName;
}

}