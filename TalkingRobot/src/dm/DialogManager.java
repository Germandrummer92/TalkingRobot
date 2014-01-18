package dm;

import java.util.List;
import java.util.Vector;

/**
 * The DialogManager class, responsible for the switching of DialogStates for a certain input.
 * @author Daniel Draper
 * @version 1.0
 *
 */
public class DialogManager {

  private ErrorState errorState;

  private static DialogManager uniqueDM;

  private Dialog currentDialog;

  private List<User> userList;

  public Dialog previousDialog;

  private ErrorStrategy[] errorStrategy;

  public void updateDialog(List<String> keywords, List<String> terms) {
  }

  public void handleError(List<String> possibleKeywords) {
  }

  /**
   * private Constructor, just used by giveDialogManager()
   */
  private DialogManager() {
	  currentDialog = null;
	  loadUsers();
	  previousDialog = null;
	  errorStrategy = null;
	  errorState = ErrorState.ZERO;
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

}