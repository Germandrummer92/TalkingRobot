package dm;

import java.util.List;

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
	private DialogModus dialogModus;
	
  /**
	 * @param session the Current Session
	 * @param dialogState the dialogState of the new Dialog.
	 */
	public KitchenAssistanceDialog(Session session, DialogState dialogState) {
		super(session, dialogState);
		this.dialogModus = DialogModus.KITCHEN_ASSISTANCE;
		// TODO Auto-generated constructor stub
	}

@Override
/**
 * @See Dialog#updateState(keywords, terms, approval) throws WrongStateClassException
 */
public void updateState(List<Keyword> keywords, List<String> terms,
		List<String> approval) throws WrongStateClassException {
	if (getCurrentDialogState().getClass() != KitchenAssistanceState.class) {
		throw new WrongStateClassException(getCurrentDialogState().getClass().getName());
	}
	if (!updateStateKeywordJump(keywords)) { 
	
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
	if (getCurrentDialogState().getClass() != KitchenAssistanceState.class) {
		throw new WrongStateClassException(getCurrentDialogState().getClass().getName());
	}
}

/**
 * @param keywords
 * @return if the State was changed since all Keywords were pointing to the same State
 */
private boolean updateStateKeywordJump(List<Keyword> keywords) {
	if (keywords.isEmpty()) {
		return false;
	}
	else {
		boolean sameRef = true;
		Enum<?> ref = keywords.get(0).getReference().getCurrentState();
		for (Keyword kw : keywords) {
			if (!ref.equals(kw.getReference().getCurrentState())) {
				sameRef = false;
			}
		}
		if (sameRef == true) {
			getCurrentDialogState().setCurrentState(ref);
			return true;
		}
		else {
			int priorityMax = keywords.get(0).getKeywordData().getPriority();
			Keyword curKW = keywords.get(0);
			for (Keyword kw : keywords) {
				if (kw.getKeywordData().getPriority() > priorityMax) {
					curKW = kw;
					priorityMax = curKW.getKeywordData().getPriority();
				}
			}
			getCurrentDialogState().setCurrentState(curKW.getReference().getCurrentState());
			return true;
			}
		}
}

/**
 * @param keywords
 * @param terms
 */
private void updateStateToolNotFound(List<Keyword> keywords, List<String> terms) {
	if (!terms.isEmpty() && requestedObjectName != null) {
		requestedObjectName = terms.get(0);
	}
	else {
		DialogManager.giveDialogManager().setInErrorState(true);
	}
}

/**
 * @param keywords
 * @param terms
 */
private void updateStateToolFound(List<Keyword> keywords, List<String> terms) {
	for (Keyword kw : keywords) {
		if (kw.getKeywordData().getDataReference().getClass().getName().equals("data.ToolData")) {
			requestedObject = new Tool((ToolData)kw.getKeywordData().getDataReference());
			requestedObjectName = ((Tool)requestedObject).getToolData().getToolName();
			return;
		}
	}
	
}

/**
 * @param keywords
 * @param terms
 */
private void updateStateIngNotFound(List<Keyword> keywords, List<String> terms) {
	//Not needed, only one State not found needed
	
}

/**
 * @param keywords
 * @param terms
 */
private void updateStateIngFound(List<Keyword> keywords, List<String> terms) {
	for (Keyword kw : keywords) {
		if (kw.getKeywordData().getDataReference().getClass().getName().equals("data.IngredientData")) {
			requestedObject = new Ingredient((IngredientData)kw.getKeywordData().getDataReference());
			requestedObjectName = ((Ingredient)requestedObject).getIngredientData().getIngredientName();
			return;
		}
	}
	
}

/**
 * @param keywords
 * @param terms
 */
private void updateStateExit(List<Keyword> keywords, List<String> terms) {
	//State should never be reached
}

/**
 * @param keywords
 * @param terms
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