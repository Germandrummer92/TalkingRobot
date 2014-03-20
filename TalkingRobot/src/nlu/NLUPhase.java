package nlu;
import generalControl.Main;
import generalControl.Phase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
  		String userInput = main.getAsrResult().toLowerCase();
  		//write given input in file input
  		File file = new File("resources/nlu/Phoenix/TalkingRobot/input");
  		PrintWriter writer;
  		try {
  			writer = new PrintWriter(file, "UTF-8");
  			writer.println(userInput);
  			writer.close();
  		} catch (FileNotFoundException | UnsupportedEncodingException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
  		LinkedList<List<String>> nluResult = new LinkedList<List<String>>();
	  	  
  		nluResult.add(kwAnalyzer.analyze(userInput));
  		nluResult.add(termAnalyzer.analyze(userInput));
  		nluResult.add(possibleKwAnalyzer.analyze(userInput));  
  		nluResult.add(approvalAnalyzer.analyze(userInput));

  		main.setNluResult(nluResult);
  	}

  	/**
  	 * returns the next phase the system must enter.
  	 * The DM-phase always follows after the NLU-phase
  	 */
  	public Phase nextPhase(Main main) {
  		return main.getDmPhase();
  	}

}
