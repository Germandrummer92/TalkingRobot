package nlu;

import java.io.File;
import java.util.List;

/**
 * 
 * @author Meng Meng Yan
 * @version 1.0
 * The class TermAnalyzer analyzes the input String regarding terms/names which might are not part of
 * the vocabulary. Will especially be needed for Recipe Learning.
 */
public class TermAnalyzer extends InputAnalyzer {

	/**
	 * @see InputAnalyzer#grammarFile
	 */
	protected File grammarFile = new File("resources/nlu/Phoenix/TalkingRobot/Grammar/term.gra");
	
	/**
	 * Creates a new TermAnalyzer object.
	 */
	public TermAnalyzer() {
	
	}

	/**
	 * @see InputAnalyzer#analyze()
	 */
	public List<String> analyze(String input) {
		List<String> result = phoenix.operatePhoenix(grammarFile);
		return result;
	}

}
