package dm;

/**
 * 
 * @author Meng Meng Yan
 * @version 1.0
 * 
 */
public class ErrorHandlingState extends DialogState {

	  private String outputKeyword;
	
	  /**
	   * Constructor of ErrorHandlingState.
	   * @param question indicates the State as a questiom
	   * @param strategy indicates the chosen strategy
	   * @param outputKeyword the output keywords
	   */
	  public ErrorHandlingState(boolean question, ErrorHandling strategy, String outputKeyword) {
		  	this.setQuestion(question);
		  	this.setCurrentState(strategy);
		  	this.outputKeyword = outputKeyword;
	  }
	  
	  public String getOutputKeyword() {
		  	return outputKeyword;
	  }

}