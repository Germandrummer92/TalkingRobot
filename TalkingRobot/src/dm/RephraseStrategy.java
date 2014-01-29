package dm;

import java.util.List;

/**
 * 
 * @author Meng Meng Yan
 * @version 1.0
 * The rephrase strategy: asks the user to rephrase his previous input
 */
public class RephraseStrategy extends ErrorStrategy {

	private ErrorHandlingState rephraseState;
	
	/**
	 * Constructor of RephraseStrategy.
	 */
	public RephraseStrategy() {
		this.rephraseState = new ErrorHandlingState(true, ErrorHandling.REPHRASE, "rephrase");
	}
	
	/**
	 * @see ErrorStrategy#handleError(List)
	 */
	@Override
	public ErrorHandlingState handleError(List<String> errorWords) {
		this.riseCounter();
		return rephraseState;
	}

}
