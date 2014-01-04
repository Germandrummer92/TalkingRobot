package dm;

import java.util.List;

public abstract class CanteenDialog extends Dialog {

  protected Canteen currentCanteen;

  public abstract void updateState(List<String> keywords, List<String> terms);

  private void loadCanteeenData() {
  }

}