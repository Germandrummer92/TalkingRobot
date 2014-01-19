package nlu;
import generalControl.Main;
import generalControl.Phase;

import java.util.List;

/**
 * 
 * @author Meng Meng Yan
 * @version 1.0
 * The class coordinates the NLUPhase and decides which analyzers are to be used.
 */
public class NLUPhase extends Phase {

  private InputAnalyzer termAnalyzer;

  private InputAnalyzer kwAnalyzer;

  private InputAnalyzer possibleKwAnalyzer;

  private InputAnalyzer approvalAnalyzer;
  
  /**
   * sets the nluResult in the class main.
   * @param main: the main class of this system
   */
  public void setPhaseResult(Main main) {
	  String userInput = main.getAsrResult();
	  List<List<String>> nluResult = null;
	  
	  nluResult.set(0, kwAnalyzer.analyze(userInput));
	  nluResult.set(1, termAnalyzer.analyze(userInput));
	  
	  if(nluResult.get(0).isEmpty()) {
		  nluResult.set(2, possibleKwAnalyzer.analyze(userInput));
	  }
	  
	  nluResult.set(3, approvalAnalyzer.analyze(userInput));

      main.setNluResult(nluResult);
  }

  /**
   * returns the next phase the system must enter.
   * The DM-phase always follows after the NLU-phase
   */
  protected Phase nextPhase(Main main) {
  		return main.getDmPhase();
  }

  /*Die Methode scheint unnoetig zu sein
  private List<String> operateAnalyzer(String input, InputAnalyzer analyzer) {
  return null;
  }
  */
}
}
