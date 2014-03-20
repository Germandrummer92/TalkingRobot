package dm;

import generalControl.Main;

import java.util.ArrayList;
import java.util.List;

import data.Data;
import data.IngredientData;
import data.KeywordData;
import data.KeywordType;
import data.RecipeData;
import data.ToolData;

/**
*
* @author Daniel Draper
* @version 1.0
* This class represents a KitchenAssistanceDialog responsible for the locating of ingredients or Tools in the Kitchen.
*/
public class KitchenAssistanceDialog extends KitchenDialog {

private Object requestedObject;
private String requestedObjectName;
private Class newObjectClass = null;

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
updateStateToolNotFound(keywords, approval, terms);
break;
case KA_WAITING_FOR_TYPE:
updateStateWaiting(keywords, terms);
break;
case KA_WAITING_FOR_LOCATION:
updateStateLocation(keywords, terms);
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
if (kw.getKeywordData().getPriority() > priorityMax) {
curKW = kw;
priorityMax = curKW.getKeywordData().getPriority();
curRef = kw.getReference().get(0);
}
for (DialogState d : kw.getReference()) {
if (d.getCurrentState().getClass().getName().equals("dm.KitchenAssistance")) {
if (kw.getKeywordData().getPriority() + 3 > priorityMax) {
curKW = kw;
priorityMax = curKW.getKeywordData().getPriority() + 3;
curRef = d;
}
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
private void updateStateToolNotFound(List<Keyword> keywords, List <String> approval, List<String> terms) {
if (approval != null && !approval.isEmpty()) {
if (approval.get(0).equals("yes")) {
getCurrentDialogState().setCurrentState(KitchenAssistance.KA_WAITING_FOR_TYPE);
return;
}
else {
getCurrentDialogState().setCurrentState(KitchenAssistance.KA_ENTRY);
}
}
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
							if (((Tool)requestedObject).getToolData().getLocation() == null || ((Tool)requestedObject).getToolData().getLocation().isEmpty()) {
								getCurrentDialogState().setCurrentState(KitchenAssistance.KA_WAITING_FOR_LOCATION);
							}
							return;
					}
	}
	}
	}
	if (requestedObject != null) {
		if (DialogManager.giveDialogManager().getPreviousDialog().getClass().equals(KitchenAssistanceDialog.class)) {
			if (((KitchenAssistanceDialog)DialogManager.giveDialogManager().getPreviousDialog()).requestedObjectName.equals(this.requestedObjectName)) {
				DialogManager.giveDialogManager().setInErrorState(true);
			}
		}
		return;
	}
		DialogManager.giveDialogManager().setInErrorState(true);

	}	

/**
* Updates the state if its in the IngredientNotFound state.
* @param keywords keywords passed
* @param terms terms passed
*/
private void updateStateIngNotFound(List<Keyword> keywords, List<String> terms) {
	DialogManager.giveDialogManager().setInErrorState(true);

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
if (((Ingredient)requestedObject).getIngredientData().getIngredientLocation() == null || ((Ingredient)requestedObject).getIngredientData().getIngredientLocation().isEmpty()) {
getCurrentDialogState().setCurrentState(KitchenAssistance.KA_WAITING_FOR_LOCATION);
}
return;
}
}
}
}
if (requestedObject != null) {
	if (DialogManager.giveDialogManager().getPreviousDialog().getClass().equals(KitchenAssistanceDialog.class)) {

		if (((KitchenAssistanceDialog)DialogManager.giveDialogManager().getPreviousDialog()).requestedObjectName.equals(this.requestedObjectName)) {
			DialogManager.giveDialogManager().setInErrorState(true);
		}
	}
return;
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
	DialogManager.giveDialogManager().setInErrorState(true);
}

/**
* Updates the state if its in the Entry state.
* @param keywords keywords passed
* @param terms terms passed
*/
private void updateStateEntry(List<Keyword> keywords, List<String> terms) {
//only invoked if not already jumped to TOOL_" or ING_FOUND or specifically came here
boolean bring = false;
boolean something = false;

if (keywords != null && !keywords.isEmpty()) {
for (Keyword kw : keywords) {
for (DialogState ds : kw.getReference()) {

if (kw.getWord().equals("bring")) {
bring = true;
}
else if (kw.getWord().equals("something")) {
something = true;
}
else if (ds.getCurrentState().equals(KitchenAssistance.KA_ENTRY)) {
return;
}
}

}
}
if (something) {
return;
}
else if (terms != null && !terms.isEmpty()) {
requestedObjectName = terms.get(0);
getCurrentDialogState().setCurrentState(KitchenAssistance.KA_TELL_TOOL_NOT_FOUND);
}
else {
DialogManager.giveDialogManager().setInErrorState(true);
}



}
/**
* Updates the state if its in the Waiting for Type state.
* @param keywords keywords passed
* @param terms terms passed
*/
private void updateStateWaiting(List<Keyword> keywords, List<String> terms) {
if (keywords!= null && !keywords.isEmpty()) {
for (Keyword kw : keywords) {
if (kw.getWord().equals("ingredient") || kw.getWord().equals("ingredients")) {
newObjectClass = Ingredient.class;
}
if (kw.getWord().equals("tool") || kw.getWord().equals("tools")) {
newObjectClass = Tool.class;
}
}
if (newObjectClass != null) {
getCurrentDialogState().setCurrentState(KitchenAssistance.KA_WAITING_FOR_LOCATION);
}
}
else {
DialogManager.giveDialogManager().setInErrorState(true);
}
}

private void updateStateLocation(List<Keyword> keywords, List<String> terms) {
if (keywords == null || keywords.isEmpty()) {
if (terms != null && !terms.isEmpty()) {
if (newObjectClass != null && newObjectClass.equals(Tool.class)) {
ToolData d = new ToolData(requestedObjectName, terms.get(0), new ArrayList<RecipeData>());
d.writeFile();
requestedObject = new Tool(d);
getCurrentDialogState().setCurrentState(KitchenAssistance.KA_TELL_TOOL_FOUND);
ArrayList<DialogState> ds = new ArrayList<DialogState>();
ds.add(new KitchenAssistanceState(KitchenAssistance.KA_TELL_TOOL_FOUND));
ArrayList<Data> refs = new ArrayList<Data>();
refs.add(((Tool)requestedObject).getToolData());
DialogManager.giveDialogManager().getDictionary().addKeyword(requestedObjectName, 10, ds, refs, KeywordType.TOOL);

}
else {
if (newObjectClass == null && requestedObject != null && terms != null && !terms.isEmpty()) {
if (requestedObject.getClass().equals(Tool.class)) {
((Tool)requestedObject).getToolData().setLocation(terms.get(0));
((Tool)requestedObject).getToolData().writeFile();
ArrayList<String> tool = new ArrayList<String>();
tool.add(requestedObjectName);
KeywordData toolData = DialogManager.giveDialogManager().getDictionary().findKeywords(tool).get(0).getKeywordData();
((ToolData)toolData.getDataReference().get(0)).setLocation(terms.get(0));
toolData.writeFile();
}
if (requestedObject.getClass().equals(Ingredient.class)) {
((Ingredient)requestedObject).getIngredientData().setIngredientLocation(terms.get(0));
((Ingredient)requestedObject).getIngredientData().writeFile();
ArrayList<String> ing = new ArrayList<String>();
ing.add(requestedObjectName);
KeywordData ingData = DialogManager.giveDialogManager().getDictionary().findKeywords(ing).get(0).getKeywordData();
((IngredientData)ingData.getDataReference().get(0)).setIngredientLocation(terms.get(0));
ingData.writeFile();
}
getCurrentDialogState().setCurrentState(KitchenAssistance.KA_ENTRY);
return;
}

IngredientData d = new IngredientData(requestedObjectName, terms.get(0));
d.writeFile();
requestedObject = new Ingredient(d);
getCurrentDialogState().setCurrentState(KitchenAssistance.KA_TELL_INGREDIENT_FOUND);
ArrayList<DialogState> ds = new ArrayList<DialogState>();
ds.add(new KitchenAssistanceState(KitchenAssistance.KA_TELL_INGREDIENT_FOUND));
ds.add(new RecipeLearningState(RecipeLearning.RL_ASK_INGREDIENT_RIGHT));
ds.add(new RecipeAssistanceState(RecipeAssistance.RA_TELL_INGREDIENT_FOUND));
ArrayList<Data> refs = new ArrayList<Data>();
refs.add(((Ingredient)requestedObject).getIngredientData());
DialogManager.giveDialogManager().getDictionary().addKeyword(requestedObjectName, 10, ds, refs, KeywordType.INGREDIENT);
}

}
getCurrentDialogState().setCurrentState(KitchenAssistance.KA_ENTRY);
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
* @return the name of the requested Object, needed in case no Object can be created as it wasn't found.
*/
public String getRequestedObjectName() {
return requestedObjectName;
}

}