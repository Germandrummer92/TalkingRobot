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
	
  /**
	 * @param session the Current Session
	 * @param dialogState the dialogState of the new Dialog.
	 */
	public KitchenAssistanceDialog(Session session, DialogState dialogState) {
		super(session, dialogState);
		// TODO Auto-generated constructor stub
	}

@Override
/**
 * @See Dialog#updateState(keywords, terms, approval) throws WrongStateClassException
 */
public void updateState(List<Keyword> keywords, List<String> terms,
		List<String> approval) throws WrongStateClassException {
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
 * @param keywords
 * @param terms
 */
private void updateStateToolNotFound(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

/**
 * @param keywords
 * @param terms
 */
private void updateStateToolFound(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

/**
 * @param keywords
 * @param terms
 */
private void updateStateIngNotFound(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

/**
 * @param keywords
 * @param terms
 */
private void updateStateIngFound(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
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
private void updateStateEntry(List<Keyword> keywords, List<String> terms) {
	DialogState reference = null;
	boolean jump = true;
	for (Keyword kw : keywords) {
		if (reference != null && !kw.getReference().equals(reference)) {
			jump = false;
			break;
		}
	}
	if (jump) {
			setCurrentDialogState(reference);
			if (keywords.get(0).getKeywordData().getDataReference() instanceof IngredientData) {
				requestedObject = new Ingredient(((IngredientData)keywords.get(0).getKeywordData().getDataReference()));
			}
			if (keywords.get(0).getKeywordData().getDataReference() instanceof ToolData) {
				requestedObject = new Tool(((ToolData)keywords.get(0).getKeywordData().getDataReference()));
			}
	}
	if (!terms.isEmpty()) {
		getCurrentDialogState().setCurrentState(KitchenAssistance.KA_TELL_INGREDIENT_NOT_FOUND);
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

}