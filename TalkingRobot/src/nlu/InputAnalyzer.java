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
	 * the perl-script which phonix has to use
	 */
	protected String runParse;
	
	/**
	 * a flag which decides if phoenix is using extracted form or not.
	 * 0 means no; 1 means yes
	 */
	protected int extractFlag;
	
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