package nlu;

import java.io.File;
import java.util.List;

import dm.Dictionary;

/**
 *
 * @author Bettina Weller
 * @version 1.0
 * The class KeywordAnalyzer analyzes the input String regarding keywords which can indicate the
 * next possible dialogstate.
 */
public class KeywordAnalyzer extends InputAnalyzer {

  private Dictionary currentDictionary;

  /**
	 * Creates a new KeywordAnalyzer object.
	 */
	public KeywordAnalyzer() {
		this.runParse = "run_parse_keyword";
		this.extractFlag = 0;
		this.compile = new File ("resources/nlu/Phoenix/TalkingRobot/Keyword/");
	}

  /**
   * @see InputAdapter#analyze()
   */
  public List<String> analyze(String input) {
    List<String> result = phoenix.operatePhoenix(this.runParse, this.extractFlag, this.compile);
    if(result == null) {
    	System.out.println("hi");
    }
    if(!result.isEmpty()) {
	    System.out.println(result.size());
	    for(int i = 0; i < result.size(); i++){
	    	System.out.println(result.get(i) + "hi \n");
	    }
    }
		return result;
  }

}
