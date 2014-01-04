package dm;
import java.util.List;


public abstract class Dialog {

  private Session currentSession;

  private DialogState currentState;

  public Session getCurrentSession() {
  return null;
  }

  public abstract void updateState(List<String> keywords, List<String> terms);

  public DialogState getCurrentDialogState() {
  return null;
  }

}