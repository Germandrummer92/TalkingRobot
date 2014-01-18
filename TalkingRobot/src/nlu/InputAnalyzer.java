package nlu;

import java.io.File;
import java.util.List;

/**
 * 
 * @author Meng Meng Yan
 * @version 1.0
 * The abstract class InputAnalyzer is a template for different analyzers such as the keyword- and the
 * term-analyzer
 */
public abstract class InputAnalyzer {

	/**
	 * a gra-File needed to use Phoenix in order to provide it with a grammar.
	 */
	protected File grammarFile;

	/**
	 * an instance of PhoenixAdapter so InputAnalyzer can
	 * use Phoenix to analyze the input String.
	 */
	protected PhoenixAdapter phoenix = new PhoenixAdapter();

	/**
	 * analyzes the input String using Phoenix according to the given gra-File.
	 * 
	 * @param input the user input; result of the ASR phase
	 * @return a list Strings which can be (possible) keywords, terms or a statement of
	 * approval/disapproval 
	 */
	public abstract List<String> analyze(String input);

}