package nlu;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Meng Meng Yan
 * @version 1.2
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
		List<String> result = phoenix.operatePhoenix(this.runParse, this.extractFlag, this.compile);
//		
//		List<String> result = new LinkedList<String>();
//		result.add("we need");
//		result.add("and");
		for(int i = 0; i < result.size(); i++) {
			System.out.println("result: " + result.get(i));
		}
		List<String> term = new LinkedList<String>();
		term.add(input);
		
		for(int i = 0; i < result.size(); i++) {
			if(!result.get(i).equals("")) {
				if(result.get(i).contains("for")) {
					String[] currentResult = null;
					if(result.get(i).equals("for")) {
						currentResult = input.split("for");
						term.set(0, currentResult[0]);
					} else if (result.get(i).matches(".*for .* need .*")) {
						currentResult = input.split("need");
						term.set(0, currentResult[1]);
					} else if (result.get(i).matches(".*needed.*for.*")) {
						String[] words = result.get(i).split(" ");
						currentResult = input.split(words[0]);
						term.set(0, currentResult[0]);
					} else if (result.get(i).matches(".*for.*needed.*")) {
						String words = result.get(i).substring(5, result.get(i).length());
						currentResult = this.removeWords(input, words);
						currentResult = currentResult[0].split(".*for ");
						
						if(currentResult[0].equals("")) {
							if (currentResult.length >= 1) { words = currentResult[1]; }
						} else { words = currentResult[0]; }
						
						currentResult = words.split(" ");
						term.set(0, currentResult[currentResult.length]);
					}
					input = term.get(0);
				} else {
					if(result.get(i).contains("and")) {
		
						String[] currentResult = this.removeWords(input, result.get(i));
						
						if(currentResult != null) {
							for(int j = 0; j < currentResult.length; j++) {
								if(term.size() <= j) {
									term.add(currentResult[j].trim());
								} else {
									term.set(j, currentResult[j].trim());
								}
							}
						}
						
					} else {
						
						String[] currentResult = this.removeWords(input, result.get(i));
						
						if(currentResult != null) {
							if(currentResult[0].equals("")) {
								if(currentResult.length > 1) {input = currentResult[1].trim();}
							} else {input = currentResult[0].trim();}
						}
						term.set(0, input);
					}
				}
					
			}
		} 
		return term;
	}
	
	private String[] removeWords(String input, String words) {
		String[] wordsSplit = null;
	
		if(words.contains(" ")) {wordsSplit = words.split(" ");} 
		else {
			wordsSplit = new String[1];
			wordsSplit[0] = words;
		}
		
		String regex = wordsSplit[0];
		
		for(int j = 1; j < wordsSplit.length; j++) {
			regex = regex + ".*" + wordsSplit[j];
		}
		
		String[] currentResult = null;
		if(input.matches(".*" + regex + ".*")) {
			currentResult = input.split(regex);
		}
		
		return currentResult;
		
	}
	
	public static void main(String[] args) {
		TermAnalyzer ta = new TermAnalyzer();

		List<String> result = ta.analyze("My very pretty name is Nicole");
		for(int i = 0; i < result.size(); i++) {
			System.out.println("and now: " + result.get(i));
		}
//		System.out.println("now in main");
//		String test = "cheese is needed for fodue";
//		System.out.println(test.matches(".*needed.*for.*"));
	}
}
