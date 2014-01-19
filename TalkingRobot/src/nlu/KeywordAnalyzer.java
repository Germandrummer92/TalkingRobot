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

  protected File grammarFile = new File("resources/nlu/Phoenix/TalkingRobot/Grammar/keyword.gra/"); //keywordGrammar.gra

  /**
	 * Creates a new KeywordAnalyzer object.
	 */
	public KeywordAnalyzer() {
	
	}

  /**
   * @see InputAdapter#analyze()
   */
  public List<String> analyze(String input) {
    List<String> result = phoenix.operatePhoenix(grammarFile);
		return result;
  }

}
