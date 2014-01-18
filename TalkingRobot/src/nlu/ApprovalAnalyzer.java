package nlu;
import java.io.File;
import java.util.List;

/**
 *
 * @author Bettina Weller
 * @version 1.0
 * The class ApprovalAnalyzer analyzes if the input String is a statement of approval, disapproval or a
 * decision. The latest one is only needes for errorhandling.
 */
public class ApprovalAnalyzer extends InputAnalyzer {

  grammarFile  = new File("resources/nlu/Phoenix/TalkingRobot/Grammar/approval.gra/"); //approvalGrammar.gra
  
  /**
	 * Creates a new ApprovalAnalyzer object.
	 */
	public ApprovalAnalyzer() {
	
	}

  /**
     * @see InputAdapter#analyze()
     */
	public List<String> analyze(String input) {
		List<String> result = phoenix.operatePhoenix(grammarFile);
		return result;
	}

}
