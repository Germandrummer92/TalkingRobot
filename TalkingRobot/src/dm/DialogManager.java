package dm;


import generalControl.Main;

import java.util.LinkedList;
import java.util.List;

import data.CanteenData;
import data.CanteenNames;



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
  
  private List<String> lastPossibleKeywords;


/**
   * Updates the current Dialog to switch it's state, according to the User's input.
   * @param keywords
   * @param terms
   * @param approval
 * @throws WrongStateClassException 
   */
  public void updateDialog(List<String> keywords, List<String> terms, List<String> approval){
//	  for(int i = 0; i < keywords.size(); i++) {
//		  System.out.println("working with: " + keywords.get(i));
//	  }
	  previousDialog = currentDialog;
	   List<Keyword> kws = dictionary.findKeywords(keywords);
	   do {
	  try {
		currentDialog.updateState(kws, terms, approval);
		break;
	//Make Sure current Class is right for the current StateClass
	} catch (WrongStateClassException e) {
		Enum<?> currentDialogState = currentDialog.getCurrentDialogState().getCurrentState();
		Session currentSession = currentDialog.getCurrentSession();
		Canteen currentCanteen = null;
		if (e.getNewClassName().contains("Canteen")) {
			currentCanteen = new Canteen(new CanteenData(CanteenNames.ADENAUERRING, 0));
		}
		//System.out.println(e.getNewClassName());
		//If not switching the class
		switch (e.getNewClassName()) {
		case "dm.Start" : currentDialog = new StartDialog(currentSession, new StartState((Start)currentDialogState)); break;
		case "dm.CanteenInfo" : currentDialog = new CanteenInformationDialog(currentSession, new CanteenInformationState((CanteenInfo)currentDialogState), currentCanteen); break;
		case "dm.CanteenRecom" : currentDialog = new CanteenRecommendationDialog(currentSession, new CanteenRecommendationState((CanteenRecom)currentDialogState), currentCanteen); break;
		case "dm.KitchenAssistance" : currentDialog = new KitchenAssistanceDialog(currentSession, new KitchenAssistanceState((KitchenAssistance)currentDialogState)); break;
		case "dm.RecipeAssistance" : currentDialog = new RecipeAssistanceDialog(currentSession, new RecipeAssistanceState((RecipeAssistance)currentDialogState)); break;
		case "dm.RecipeLearning" : currentDialog = new RecipeLearningDialog(currentSession, new RecipeLearningState((RecipeLearning)currentDialogState)); break;
		default: break;	
		}
		
	}
	   } while(true);

	   //?
	   if (isInErrorState) {
		   if(this.errorState != null && !Main.giveMain().getNluResult().get(3).isEmpty()) {
			   handleError(Main.giveMain().getNluResult().get(3));
		   } else {
			   handleError(Main.giveMain().getNluResult().get(2));
		   }
	   } else {
		   clearAllStrategies();
	   }
  }

  /**
   * Handles Errors, from the NLU Phase and sets the next dmResult if errors couldn't be solved.
   * @param possibleKeywords possibly wrongly written keywords.
 * @return 
   */
  public void handleError(List<String> possibleKeywords) {
//	  for(int i = 0; i < possibleKeywords.size(); i++) {
//		  System.out.println(possibleKeywords.get(i));
//	  }
	  ErrorHandlingState dmResult = new ErrorHandlingState(true, ErrorHandling.REPEAT, "repeat");;
	  boolean dialogIsUpdated = false;
	  float avg = 10;
	  
	  if(lastPossibleKeywords != null && lastPossibleKeywords.equals(possibleKeywords)) {
		  
		  this.errorState = ErrorState.RESTART;
		  dmResult = errorStrategy[5].handleError(possibleKeywords);
		  
	  } else {
		  lastPossibleKeywords = possibleKeywords;
		  if(!possibleKeywords.isEmpty() && possibleKeywords.get(0).matches(".*;.*;[0-9]")) {
//			  System.out.println("working with possible kw");
	  			avg = this.getAverageDistance(possibleKeywords);
		  } else if (!possibleKeywords.isEmpty()) {
//			  System.out.println("working with approval");
			  this.isInErrorState = false;
		  	dialogIsUpdated = this.handleResponseToPreviousErrorHandling(possibleKeywords);
		  		if(!dialogIsUpdated) {
		  			//error could not be solved; start with repetition/rephrasing again
		  			this.clearAllStrategies();
		  		} 
		  }  
	  }
	  
//	  System.out.println(avg);
	  //repeat || rephrase: more than 40% of the word is probably written wrong
	  if(avg >= (float) 0.4){
		  if(errorStrategy[0].getCounter() < 3) {
			  dmResult = errorStrategy[0].handleError(possibleKeywords);
		  } else if (errorStrategy[1].getCounter() < 3) {
			  dmResult = errorStrategy[1].handleError(possibleKeywords);
		  } else {
			  if(this.getCurrentDialog().getCurrentDialogState().isQuestion()) {
				  Main.giveMain().setDmResult(this.getCurrentDialog().getCurrentDialogState());
				  dialogIsUpdated = true;
			  } else {
				  this.errorState = ErrorState.RESTART;
				  dmResult = errorStrategy[5].handleError(possibleKeywords);;
			  }
		  }
	  }
	
	  //explicit verification || choice : the probability that we have a keyword lies between 60% and 75%
	  if(avg >= (float) 0.25 && avg < (float) 0.4) {
		  if(errorStrategy[2].getCounter() < 3) {
			  this.errorState = ErrorState.CHOICE;
			  dmResult = errorStrategy[2].handleError(possibleKeywords);
		  } else if(errorStrategy[3].getCounter() < 3) {
			  this.errorState = ErrorState.EXPLICIT_VERIFICATION;
			  dmResult = errorStrategy[3].handleError(possibleKeywords);
		  } else {
			  //if already tried numeral times than try with indirect verification.
			  avg = (float) 0.2;
		  }
	  }
	  
	  //indirect verification: the probability that we have a keyword lies between 75% and 85%
	  if(avg >= (float) 0.15 && avg < (float) 0.25) {
		  if(errorStrategy[4].getCounter() < 3) {
			  this.errorState = ErrorState.INDIRECT_VERIFICATION;
			  dmResult = errorStrategy[4].handleError(possibleKeywords);
		  } else {
			  //if already tried numeral times than try with ignoring
			  avg = (float) 0.1;
		  }
	  }
	  
	  //ignore: the probability that we have a keyword is more than 85%
	  if(avg < (float) 0.15){
//		  System.out.println("ignoring the problem");
		  LinkedList<String> assumedKeywords = new LinkedList<String>();
		  for(int i = 0; i < possibleKeywords.size(); i++) {
		  		if(possibleKeywords.get(i).matches(".*;.*;[0-9]")){
		  			String[] possibleKwSplit = possibleKeywords.get(i).split(";");
		  			assumedKeywords.add(possibleKwSplit[1]);
		  		}
		  }
		  
		  this.isInErrorState = false;
		  updateDialog(assumedKeywords, new LinkedList<String>(), new LinkedList<String>()); 
		  dialogIsUpdated = true;
	  }
	  
	  if(!dialogIsUpdated) { Main.giveMain().setDmResult(dmResult); }
	  this.isInErrorState = false;
	  
  }

  private boolean handleResponseToPreviousErrorHandling(List<String> possibleKeywords) {
	LinkedList<String> keyword = new LinkedList<String>();  
	  
	if(this.errorState == ErrorState.CHOICE) {
		for(int i = 0; i < possibleKeywords.size(); i++) {
  			if(possibleKeywords.get(i).equals("second")) {	
  				ChoiceStrategy strategy = (ChoiceStrategy)errorStrategy[2];
  				keyword.add(strategy.getSecondChoice());
  				this.updateDialog(keyword, new LinkedList<String>(), new LinkedList<String>());
  			} else if(possibleKeywords.get(i).equals("first")) {
  				ErrorHandlingState dmResult = errorStrategy[2].handleError(null);
  				if(dmResult != null) { Main.giveMain().setDmResult(dmResult); }	
  			}
  		}
  	} else if(this.errorState == ErrorState.EXPLICIT_VERIFICATION) {
  		for(int i = 0; i < possibleKeywords.size(); i++) {
  			if(possibleKeywords.get(i).equals("yes")) {
  				ExplicitVerificationStrategy strategy = (ExplicitVerificationStrategy)errorStrategy[3];
  				keyword.add(strategy.getQuestionableWord());
  			} else if(possibleKeywords.get(i).equals("no")) {
  				ErrorHandlingState dmResult = errorStrategy[3].handleError(null);
  				if(dmResult != null) { Main.giveMain().setDmResult(dmResult); }	
  			}
  		}
  	} else if(this.errorState == ErrorState.INDIRECT_VERIFICATION) {
  		for(int i = 0; i < possibleKeywords.size(); i++) {
  			if(possibleKeywords.get(i).equals("yes")) {
  				IndirectVerificationStrategy strategy = (IndirectVerificationStrategy)errorStrategy[3];
  				keyword.add(strategy.getQuestionableWords());
  			} else if(possibleKeywords.get(i).equals("no")) {
  				ErrorHandlingState dmResult = errorStrategy[4].handleError(null);
  				if(dmResult != null) { Main.giveMain().setDmResult(dmResult); }	
  			}
  		}
  	} else if(this.errorState == ErrorState.RESTART) {
  		RestartStrategy restart = (RestartStrategy) errorStrategy[5];
  		for(int i = 0; i < possibleKeywords.size(); i++) {
  			if(possibleKeywords.get(i).equals("yes")) {
  				if(restart.getErrorHandling() == ErrorHandling.RESTART_CI
  						|| restart.getErrorHandling() == ErrorHandling.RESTART_CR) {
  					keyword.add(restart.getMeal().getMealName());
  				} else if(restart.getErrorHandling() == ErrorHandling.RESTART_RA) {
  					keyword.add(restart.getRecipe().getRecipeName());
  				} else if(restart.getErrorHandling() == ErrorHandling.RESTART_RL) {
  					keyword.add("teach you");
  				}
  			} else {
  				ErrorHandlingState dmResult = errorStrategy[5].handleError(null);
  				if(dmResult != null) { Main.giveMain().setDmResult(dmResult); }	
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
	  
	  errorStrategy = new ErrorStrategy[6];
	  errorStrategy[0] = new RepeatStrategy();
	  errorStrategy[1] = new RephraseStrategy();
	  errorStrategy[2] = new ChoiceStrategy();
	  errorStrategy[3] = new ExplicitVerificationStrategy();
	  errorStrategy[4] = new IndirectVerificationStrategy();
	  errorStrategy[5] = new RestartStrategy();
	  
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
  private float getAverageDistance(List<String> possibleKw) {
	  	
	  	float overallDistance = 0;
	  	int counter = 0;
	  	
	  	for(int i = 0; i < possibleKw.size(); i++) {
	  		if(possibleKw.get(i).matches(".*;.*;[0-9]")){
	  			counter++;
	  			String[] possibleKwSplit = possibleKw.get(i).split(";");
	  			overallDistance = overallDistance 
	  					+ (float)Integer.parseInt(possibleKwSplit[2]) / possibleKwSplit[1].length();
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
