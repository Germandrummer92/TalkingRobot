package dm;


public abstract class DialogState {

  private DialogStates currentState;

  private Boolean question;

  public abstract String getOutputKeyword();

  public DialogStates getCurrentState() {
  	return currentState;
  }

  public abstract Boolean isQuestion();

  public void setCurrentState(DialogStates currentState) {
	  	this.currentState = currentState;
	  }
}