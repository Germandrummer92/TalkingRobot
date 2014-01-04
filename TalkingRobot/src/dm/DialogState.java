package dm;


public abstract class DialogState {

  private DialogStates currentState;

  private Boolean question;

  public abstract String getOutputKeyword();

  public DialogStates getCurrentState() {
  return null;
  }

  public abstract Boolean isQuestion();

}