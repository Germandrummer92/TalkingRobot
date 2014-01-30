package dm;

/**
 * This class represents a canteenDialog and as such has a certain Canteen object
 * associated with it.
 * @author Aleksandar Andonov
 * @version 1.0
 */
public abstract class CanteenDialog extends Dialog {

  protected Canteen currentCanteen;

  /**
   * Creates a new CanteenDialog at the given canteen which is part of the session given and
   * in the defined state.
   * @param session The session which this dialog is part of.
   * @param currState The state of the Dialog.
   * @param currentCanteen The canteen where this dialog is taking place.
   */
  public CanteenDialog(Session session, DialogState currState, Canteen currentCanteen) {
	  //TODO The canteen specs document is needed in order to parse tha data to
	  //canteen object and load it into the system
	  super(session, currState);
	  this.currentCanteen = currentCanteen;
	  this.dialogModus = DialogModus.CANTEEN;
  }

  public Canteen getCurrentCanteen() {
	  return currentCanteen;
  }

  public void setCurrentCanteen(Canteen currentCanteen) {
	  this.currentCanteen = currentCanteen;
  }

}