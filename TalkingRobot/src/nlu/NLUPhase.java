package nlu;
import generalControl.Main;
import generalControl.Phase;

import java.util.LinkedList;
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
   * Constructor of NLUPhase.
   */
  public NLUPhase() {
	  this.termAnalyzer = new TermAnalyzer();
	  this.kwAnalyzer = new KeywordAnalyzer();
	  this.possibleKwAnalyzer = new PossibleKeywordAnalyzer();
	  this.approvalAnalyzer = new ApprovalAnalyzer();
  }
  
  /**
   * sets the nluResult in the class main.
   * @param main: the main class of this system
   */
  public void setPhaseResult(Main main) {
	  String userInput = main.getAsrResult();
	  LinkedList<List<String>> nluResult = new LinkedList<List<String>>();
	  	  
	  nluResult.add(kwAnalyzer.analyze(userInput));
	  nluResult.add(termAnalyzer.analyze(userInput));
	  
	  if(nluResult.get(0).isEmpty()) {
		  nluResult.add(possibleKwAnalyzer.analyze(userInput));
	  }
	  else {
		  nluResult.add(new LinkedList<String>());
	  }
	  
	  nluResult.add(approvalAnalyzer.analyze(userInput));

      main.setNluResult(nluResult);
  }

  //eventuell public
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
