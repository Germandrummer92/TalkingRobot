package dm;

import java.util.List;

/**
 * 
 * @author Meng Meng Yan
 * @version 1.0
 * The repeat strategy: asks the user to repeat his previous input
 */
public class RepeatStrategy extends ErrorStrategy {

	private ErrorHandlingState repeatState;

	/**
	 * Constructor of RepeatStrategy.
	 */
	public RepeatStrategy() {
		repeatState = new ErrorHandlingState(true, ErrorHandling.REPEAT, "repeat");
	}
  
	/**
	 * @see ErrorStrategy#handleError(List)
	 */
	@Override
	public ErrorHandlingState handleError(List<String> errorWords) {
		this.riseCounter();
		return repeatState;
	}

}
