package dm;

import java.util.List;
import java.util.Vector;


public class DialogManager {

  private ErrorState errorState;

  private DialogManager uniqueDM;

  private Dialog currentDialog;

  private List<User> userList;

  public Dialog previousDialog;

  private ErrorStrategy[] errorStrategy;

  public void updateDialog(List<String> keywords, List<String> terms) {
  }

  public void handleError(List<String> possibleKeywords) {
  }

  private DialogManager DialogManager() {
  return null;
  }

  public DialogManager giveDialogManager() {
  return null;
  }

  private void loadUsers() {
  }

  public User findUser(String userName) {
  return null;
  }

  private void clearAllStrategies() {
  }

  private Integer getAverageDistance() {
  return null;
  }

}