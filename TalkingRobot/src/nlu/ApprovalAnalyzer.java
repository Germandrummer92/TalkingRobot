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
  
  /**
	 * Creates a new ApprovalAnalyzer object.
	 */
	public ApprovalAnalyzer() {
		this.grammarFile  = new File("resources/nlu/Phoenix/TalkingRobot/Grammar/approval.gra/");
	}

  /**
     * @see InputAdapter#analyze()
     */
	public List<String> analyze(String input) {
		List<String> result = phoenix.operatePhoenix(grammarFile);
		return result;
	}

}
