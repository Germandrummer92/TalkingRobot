package dm;


import generalControl.Main;

import java.util.LinkedList;
import java.util.List;



/**
 * @author Daniel Draper
 * @author Aleksandar Andonov
 * @author Meng Meng Yan
 * @version 1.2
 * This Class represents the DialogManager responsible for switching states for certain inputs and handling errors. Implements the Singleton Pattern.
 */
public class DialogManager {

  private ErrorState errorState;

  private static DialogManager uniqueDM;

  private Dialog currentDialog;

  private List<User> userList;
  
  private Dictionary dictionary;

  public Dialog previousDialog;

  private ErrorStrategy[] errorStrategy;

  private boolean isInErrorState; //dialogs set this to true when error handling is needed


/**
   * Updates the current Dialog to switch it's state, according to the User's input.
   * @param keywords
   * @param terms
   * @param approval
 * @throws WrongStateClassException 
   */
  public void updateDialog(List<String> keywords, List<String> terms, List<String> approval){
	  
	  clearAllStrategies();
	  previousDialog = currentDialog;
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
		//If not switching the class
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

	   //?
	   if (isInErrorState) {
		   handleError(Main.giveMain().getNluResult().get(2));
	   }
  }

  /**
   * Handles Errors, from the NLU Phase.
   * @param possibleKeywords possibly wrongly written keywords.
 * @return 
   */
  public ErrorHandlingState handleError(List<String> possibleKeywords) {
	  
	  int avg = 10;
	  
	  if(!possibleKeywords.isEmpty() && possibleKeywords.get(0).matches(".*;.*;[0-9]")) {
	  		avg = this.getAverageDistance(possibleKeywords);
	  } else if (!possibleKeywords.isEmpty()) {
		  	boolean errorSolved = handleResponseToPreviousErrorHandling(possibleKeywords);
		  	if(!errorSolved) {
		  		//error could not be solved; start with repetition/rephrasing again
		  		this.clearAllStrategies();
		  	} else {
		  		return null;
		  	}
	  }
	  
	  ErrorHandlingState dmResult;
	  
	  //repeat || rephrase: 8 <= avg
	  if(avg >= 8){
		  if(errorStrategy[0].getCounter() <= 3) {
			  dmResult = errorStrategy[0].handleError(possibleKeywords);
			  return dmResult;
		  } else if (errorStrategy[1].getCounter() <= 3) {
			  dmResult = errorStrategy[1].handleError(possibleKeywords);
			  return dmResult;
		  } else {
			  //TODO random?? or just passing down?
			  avg = 6;
		  }
	  }
	
	  //explicit verification || choice:  5 <= avg < 8
	  if(avg >= 5 && avg < 8) {
		  if(errorStrategy[2].getCounter() <= 3) {
			  this.errorState = ErrorState.CHOICE;
			  dmResult = errorStrategy[2].handleError(possibleKeywords);
			  return dmResult;
		  } else if(errorStrategy[3].getCounter() <= 3) {
			  this.errorState = ErrorState.EXPLICIT_VERIFICATION;
			  dmResult = errorStrategy[3].handleError(possibleKeywords);
			  return dmResult;
		  } else {
			  //if already tried numeral times than try with indirect verification.
			  avg = 3;
		  }
	  }
	  
	  //indirect verification: 3 <= avg < 5
	  if(avg >= 3 && avg < 5) {
		  if(errorStrategy[4].getCounter() <= 3) {
			  this.errorState = ErrorState.INDIRECT_VERIFICATION;
			  dmResult = errorStrategy[4].handleError(possibleKeywords);
			  return dmResult;
		  } else {
			  //if already tried numeral times than try with ignoring
			  avg = 1;
		  }
	  }
	  
	  //ignore: avg < 3
	  if(avg < 3){
		  LinkedList<String> assumedKeywords = new LinkedList<String>();
		  for(int i = 0; i < possibleKeywords.size(); i++) {
		  		if(possibleKeywords.get(i).matches(".*;.*;[0-9]")){
		  			String[] possibleKwSplit = possibleKeywords.get(i).split(";");
		  			assumedKeywords.add(possibleKwSplit[1]);
		  		}
		  }
		  
		  updateDialog(assumedKeywords, new LinkedList<String>(), new LinkedList<String>());  
	  }
	  return null;
  }

  private boolean handleResponseToPreviousErrorHandling(List<String> possibleKeywords) {
	LinkedList<String> keyword = new LinkedList<String>();  
	  
	if(this.errorState == ErrorState.CHOICE) {
		for(int i = 0; i < possibleKeywords.size(); i++) {
  			if(possibleKeywords.get(i).equals("second")) {	
  				ChoiceStrategy strategy = (ChoiceStrategy)errorStrategy[2];
  				keyword.add(strategy.getSecondChoice());
  				this.updateDialog(keyword, new LinkedList<String>(), new LinkedList<String>());
  			}
  		}
  	} else if(this.errorState == ErrorState.EXPLICIT_VERIFICATION) {
  		for(int i = 0; i < possibleKeywords.size(); i++) {
  			if(possibleKeywords.get(i).equals("yes")) {
  				ExplicitVerificationStrategy strategy = (ExplicitVerificationStrategy)errorStrategy[3];
  				keyword = (LinkedList<String>) strategy.getQuestionableWords();
  			}
  		}
  	} else if(this.errorState == ErrorState.INDIRECT_VERIFICATION) {
  		for(int i = 0; i < possibleKeywords.size(); i++) {
  			if(possibleKeywords.get(i).equals("yes")) {
  				IndirectVerificationStrategy strategy = (IndirectVerificationStrategy)errorStrategy[3];
  				keyword = (LinkedList<String>) strategy.getQuestionableWords();
  			}
  		}
  	}
	
	if(!keyword.isEmpty()) {
		this.updateDialog(keyword, new LinkedList<String>(), new LinkedList<String>());
		return true;
	} else {
		return false;
	}
	
}

/**
   * private Constructor, just used by giveDialogManager()
   */
  private DialogManager() {
	  currentDialog = new StartDialog(new Session(new User(), Robot.loadRobots().get(0)));
	  loadUsers();
	  previousDialog = null;
	  
	  errorStrategy = new ErrorStrategy[5];
	  errorStrategy[0] = new RepeatStrategy();
	  errorStrategy[1] = new RephraseStrategy();
	  errorStrategy[2] = new ChoiceStrategy();
	  errorStrategy[3] = new ExplicitVerificationStrategy();
	  errorStrategy[4] = new IndirectVerificationStrategy();
	  
	  errorState = ErrorState.ZERO;
	  dictionary = new Dictionary();
	  isInErrorState = false;
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
   * Clears the counters of the Error Strategies back to zero and changes errorState to ZERO.
   */
  private void clearAllStrategies() {
	  	errorState = ErrorState.ZERO;
	  	for( int i = 0; i < this.errorStrategy.length; i++ ) {
	  		errorStrategy[i].clearCounter();
	  	}
  }

  /**
   * Gets the average Levenshtein Distance between two words.
   * @return the Levenshtein Distance.
   */
  private Integer getAverageDistance(List<String> possibleKw) {
	  	
	  	int overallDistance = 0;
	  	int counter = 0;
	  	
	  	for(int i = 0; i < possibleKw.size(); i++) {
	  		if(possibleKw.get(i).matches(".*;.*;[0-9]")){
	  			counter++;
	  			String[] possibleKwSplit = possibleKw.get(i).split(";");
	  			overallDistance = overallDistance + Integer.parseInt(possibleKwSplit[2]);
	  		}
	  	}
	  
	  	return overallDistance / counter;
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
private void setErrorState(ErrorState errorState) {
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

public boolean isInErrorState() {
		return isInErrorState;
}

public void setInErrorState(boolean isInErrorState) {
	this.isInErrorState = isInErrorState;
}

}
