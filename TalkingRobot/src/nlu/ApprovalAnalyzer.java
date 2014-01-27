package nlu;
import java.io.File;
import java.util.List;

/**
 *
 * @author Bettina Weller
 * @version 1.0
 * The class ApprovalAnalyzer analyzes if the input String is a statement of approval, disapproval or a
 * decision. The latest one is only needed for errorhandling.
 */
public class ApprovalAnalyzer extends InputAnalyzer {
  
  /**
	 * Creates a new ApprovalAnalyzer object.
	 */
	public ApprovalAnalyzer() {
		this.runParse = "run_parse_approval";
		this.extractFlag = 1;
		this.compile = new File ("resources/nlu/Phoenix/TalkingRobot/Approval/");
	}

  /**
     * @see InputAdapter#analyze()
     */
	public List<String> analyze(String input) {
		List<String> result = phoenix.operatePhoenix(input, this.runParse, this.extractFlag, this.compile);
		
		if(result == null) {
	    	System.out.println("hi");
	    }
	    if(!result.isEmpty()) {
		    System.out.println(result.size());
		    for(int i = 0; i < result.size(); i++){
		    }
	    }
		
		return result;
	}

}
