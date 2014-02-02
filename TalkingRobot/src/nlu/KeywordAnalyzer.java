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
		this.extractFlag = 1;
		this.compile = new File ("resources/nlu/Phoenix/TalkingRobot/Keyword/");
	}

  /**
   * @see InputAdapter#analyze()
   */
  public List<String> analyze(String input) {
    List<String> result = phoenix.operatePhoenix(this.runParse, this.extractFlag, this.compile);
		return result;
  }

}
