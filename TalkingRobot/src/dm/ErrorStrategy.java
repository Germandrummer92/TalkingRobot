package dm;

import java.util.List;

/**
 * 
 * @author Meng Meng Yan
 * @version 1.0
 */
public abstract class ErrorStrategy {

  private Integer counter = 0;

  public abstract ErrorHandlingState handleError(List<String> errorWords);

  public void clearCounter() {
	  counter = 0;
  }

  public Integer getCounter() {
	  return counter;
  }

  protected void riseCounter() {
	  counter++;
  }

}