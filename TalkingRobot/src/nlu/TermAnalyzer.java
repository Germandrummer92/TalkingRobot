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
	 * Creates a new TermAnalyzer object.
	 */
	public TermAnalyzer() {
		this.runParse = "run_parse_term";
		this.extractFlag = 0;
		this.compile = new File ("resources/nlu/Phoenix/TalkingRobot/Term/");
	}

	/**
	 * @see InputAnalyzer#analyze()
	 */
	public List<String> analyze(String input) {
		List<String> result = phoenix.operatePhoenix(input, this.runParse, this.extractFlag, this.compile);
		return result;
	}

}
