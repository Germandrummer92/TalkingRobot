package dm;

import java.util.List;
import java.util.Vector;

public abstract class ErrorStrategy {

  private Integer counter;

  public abstract ErrorHandlingState handleError(List<String> errorWords);

  public void clearCounter() {
  }

  public Integer getCounter() {
  return null;
  }

  protected void riseCounter() {
  }

}