package dm;


import java.util.List;


/**
 * The DialogManager class, responsible for the switching of DialogStates for a certain input.
 * @author Daniel Draper, Aleksandar Andonov
 * @version 1.2
 *
 */
public class DialogManager {

  private ErrorState errorState;

  private static DialogManager uniqueDM;

  private Dialog currentDialog;

  private List<User> userList;
  
  private Dictionary dictionary;

  public Dialog previousDialog;

  private ErrorStrategy[] errorStrategy;

  /**
   * Updates the current Dialog to switch it's state, according to the User's input.
   * @param keywords
   * @param terms
   * @param approval
 * @throws WrongStateClassException 
   */


  public void updateDialog(List<String> keywords, List<String> terms, List<String> approval){
	   List<Keyword> kws = dictionary.findKeywords(keywords);
	   do {
	  try {
		currentDialog.updateState(kws, terms, approval);
		break;
	//Make Sure current Class is right for the current StateClass
	} catch (WrongStateClassException e) {
		DialogState currentDialogState = currentDialog.getCurrentDialogState();
		Session currentSession = currentDialog.getCurrentSession();
		Canteen currentCanteen = null;
		if (e.getNewClassName().contains("Canteen")) {
			currentCanteen = ((CanteenDialog)currentDialog).getCurrentCanteen();
		}
		switch (e.getNewClassName()) {
		case "dm.StartState" : currentDialog = new StartDialog(currentSession, currentDialogState); break;
		case "dm.CanteenInformationState" : currentDialog = new CanteenInformationDialog(currentSession, currentDialogState, currentCanteen); break;
		case "dm.CanteenRecommendationState" : currentDialog = new CanteenRecommendationDialog(currentSession, currentDialogState, currentCanteen); break;
		case "dm.KitchenAssistanceState" : currentDialog = new KitchenAssistanceDialog(currentSession, currentDialogState);
		case "dm.RecipeAssistanceState" : currentDialog = new RecipeAssistanceDialog(currentSession, currentDialogState);
		case "dm.RecipeLearningState" : currentDialog = new RecipeLearningDialog(currentSession, currentDialogState);
		default: break;
			
		}
	}
	   } while(true);

  }

  /**
   * Handles Errors, from the NLU Phase.
   * @param possibleKeywords possibly wrongly written keywords.
   */
  public void handleError(List<String> possibleKeywords) {
  }

  /**
   * private Constructor, just used by giveDialogManager()
   */
  private DialogManager() {
	  currentDialog = new StartDialog(new Session(new User(), Robot.loadRobots().get(0)));
	  loadUsers();
	  previousDialog = null;
	  errorStrategy = null;
	  errorState = ErrorState.ZERO;
	  dictionary = new Dictionary();
  }

  /**
   * Creation and access of singleton DialogManager.
   * @return the unique instance of DialogManager
   */
  public static DialogManager giveDialogManager() {
	  if (uniqueDM == null) {
		  uniqueDM = new DialogManager();
		  return uniqueDM;
	  }
	  else {
		  return uniqueDM;
	  }
  }

  /**
   * Loads and sets the User List.
   */
  private void loadUsers() {
	  userList = User.loadUsers();
  }

  /**
   * Finds the User specified by the userName and returns it.
   * @param userName
   * @return the userName with the name userName
   */
  public User findUser(String userName) {
  		if (userList == null) {
  			loadUsers();
  		}
  		for (User u : userList) {
  			if (u.getUserData().getUserName().equals(userName)) {
  				return u;
  				}
  		}
  		return null;
  }

  /**
   * Clears the Error Strategies.
   */
  private void clearAllStrategies() {
	  errorStrategy = null;
  }

  /**
   * Gets the average Levenshtein Distance between two words.
   * @return the Levenshtein Distance.
   */
  private Integer getAverageDistance() {
	  return null;
  }

/**
 * @return the current Dialog.
 */
public Dialog getCurrentDialog() {
	return currentDialog;
}

/**
 * @return the errorState
 */
public ErrorState getErrorState() {
	return errorState;
}

/**
 * @return the userList
 */
public List<User> getUserList() {
	return userList;
}

/**
 * @return the previousDialog
 */
public Dialog getPreviousDialog() {
	return previousDialog;
}

/**
 * @return the errorStrategy
 */
public ErrorStrategy[] getErrorStrategy() {
	return errorStrategy;
}

/**
 * @param errorState the errorState to set
 */
public void setErrorState(ErrorState errorState) {
	this.errorState = errorState;
}

/**
 * @param currentDialog the currentDialog to set
 */
public void setCurrentDialog(Dialog currentDialog) {
	this.currentDialog = currentDialog;
}

/**
 * @param userList the userList to set
 */
public void setUserList(List<User> userList) {
	this.userList = userList;
}

/**
 * @param previousDialog the previousDialog to set
 */
public void setPreviousDialog(Dialog previousDialog) {
	this.previousDialog = previousDialog;
}

/**
 * @param errorStrategy the errorStrategy to set
 */
public void setErrorStrategy(ErrorStrategy[] errorStrategy) {
	this.errorStrategy = errorStrategy;
}

/**
 * @return the dictionary
 */
public Dictionary getDictionary() {
	return dictionary;
}

/**
 * @param dictionary the dictionary to set
 */
public void setDictionary(Dictionary dictionary) {
	this.dictionary = dictionary;
}

}