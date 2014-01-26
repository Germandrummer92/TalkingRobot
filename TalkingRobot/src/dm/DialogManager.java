package dm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import data.CanteenData;

/**
 * The DialogManager class, responsible for the switching of DialogStates for a certain input.
 * @author Daniel Draper
 * @author Aleksandar Andonov
 * @author Meng Meng Yan
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
	  
	  clearAllStrategies();
	  
	   List<Keyword> kws = dictionary.findKeywords(keywords);
	  try {
		currentDialog.updateState(kws, terms, approval);
	} catch (WrongStateClassException e) {
		// TODO
		e.printStackTrace();
	}

  }

  /**
   * Handles Errors, from the NLU Phase.
   * @param possibleKeywords possibly wrongly written keywords.
   */
  public void handleError(List<String> possibleKeywords) {
	  int avg = this.getAverageDistance(possibleKeywords);
	  ErrorHandlingState dmResult;
	  //repeat || rephrase: 8 <= avg
	  if(avg >= 8){
		  if(errorStrategy[0].getCounter() <= 3) {
			  dmResult = errorStrategy[0].handleError(possibleKeywords);
		  } else if (errorStrategy[1].getCounter() <= 3) {
			  dmResult = errorStrategy[1].handleError(possibleKeywords);
		  } else {
			  //TODO random?? or just passing down?
			  avg = 6;
		  }
	  }
	
	  //explicit verification || choice:  5 <= avg < 8
	  if(avg >= 5 && avg < 8) {
		  if(errorStrategy[2].getCounter() <= 3) {
			  dmResult = errorStrategy[2].handleError(possibleKeywords);
		  } else if(errorStrategy[3].getCounter() <= 3) {
			  dmResult = errorStrategy[3].handleError(possibleKeywords);
		  } else {
			  //if already tried numeral times than try with indirect verification.
			  avg = 3;
		  }
	  }
	  
	  //indirect verification: 3 <= avg < 5
	  if(avg >= 3 && avg < 5) {
		  if(errorStrategy[4].getCounter() <= 3) {
			  dmResult = errorStrategy[4].handleError(possibleKeywords);
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

}