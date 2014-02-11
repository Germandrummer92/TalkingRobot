package nlu;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

/**
 * 
 * @author Bettina Weller
 * @version 1.0
 * The class PhoenixAdapter is an adapter to use phoenix in a Java program.
 */
public class PhoenixAdapter {
          
	/**
	 * Runs Phoenix and gives back the data from input, parsed according to the parameter runParse. 
	 * @param runParse the parse to be used
	 * @param extractFlag shows if parse output will be in extracted form
	 * @param compile the used grammar
	 * @return
	 */
	public LinkedList<String> operatePhoenix(String runParse, int extractFlag, File compile) {
		LinkedList<String> phoenixOutput = new LinkedList<String>();
		  
		try {
			//compile grammar
			compile(compile);
		  
			//run parse for file input
			ProcessBuilder phoenixBuilder = new ProcessBuilder("/bin/tcsh", runParse);
			phoenixBuilder.directory(new File("resources/nlu/Phoenix/TalkingRobot"));
			phoenixBuilder.redirectInput(new File("resources/nlu/Phoenix/TalkingRobot/input"));
			Process phoenix = phoenixBuilder.start();
		  
		  
		  	BufferedReader stdin2 = new BufferedReader(new InputStreamReader(phoenix.getInputStream()));
		  	BufferedReader stderr2 = new BufferedReader(new InputStreamReader(phoenix.getErrorStream()));
		  
		  	String phoenixLine2 = null;
		  
		  	while ((phoenixLine2 = stderr2.readLine()) != null && (!phoenixLine2.equals("READY"))) {
		  		stdin2.close();
		  		stderr2.close();
		  		phoenix.destroy();
		  		compile(compile);
		  		return operatePhoenix(runParse, extractFlag, compile);
		  	}

		  	while ((phoenixLine2 = stdin2.readLine()) != null) {
		  		phoenixOutput = insertWords(extractFlag, phoenixOutput, phoenixLine2);
		  	}	  
		} catch (IOException e) {
			e.printStackTrace();
		}
		return phoenixOutput;
	}

	private LinkedList<String> insertWords(int extractFlag,
			LinkedList<String> list, String phoenixLine) {
		if(extractFlag == 1) {
			if(phoenixLine.matches("Keyword:.+")) {
				String keywords4 = "";
				String[] keywords1 = phoenixLine.split("Keyword:");
				String[] keywords3 = null;
				String[] keywords2;
				if(keywords1[keywords1.length - 1].contains("[Number]")) {
					if(keywords1[keywords1.length - 1].contains("[Line]")) {
						keywords4 = "line";
					}
				}
				if(keywords1[keywords1.length - 1].matches(".+\\..+")) {
					keywords2 = keywords1[keywords1.length - 1].split("[\\.]");
					if(keywords2[keywords2.length - 1].matches(".+_.+")) {
						keywords3 = keywords2[keywords2.length - 1].split("[_]");
					}
					else {
						keywords3 = new String[1];
						keywords3[0] = keywords2[keywords2.length - 1];
					}
				}
				else {
					if(keywords1[keywords1.length - 1].matches(".+_.+")) {
						keywords3 = keywords1[keywords1.length - 1].split("[_]");
					}
					else {
						keywords3 = keywords1;
					}
				}
				for(int i = 0; i < keywords3.length; i++) {
					if(!keywords3[i].isEmpty() && !keywords3[i].equals(null)) {
						keywords4 = keywords4 + " " + keywords3[i].trim();
						keywords4 = keywords4.trim();
					}
				}
				keywords4 = keywords4.toLowerCase();
				if(!list.contains(keywords4) && !keywords4.isEmpty() && !keywords4.equals(null)) {
					list.add(keywords4);
				}
			}
			else{
				if(phoenixLine.matches("Approval:.+")) {
					String[] keywords = phoenixLine.split("Approval:");
					if(!list.contains(keywords[keywords.length - 1].trim().toLowerCase())) {
						list.add(keywords[keywords.length - 1].trim().toLowerCase());
					}
				}
			}
		}
		else {		
			if(phoenixLine.matches("Term:.+")) {
				String[] help = phoenixLine.split("Term:");
				String[] keywords1 = help[help.length - 1].split("[0-9a-z()\\[\\]_]+");
				String keywords2 = "";
				for (int i = 0; i < keywords1.length; i++) {
					if(!keywords1[i].isEmpty()) {
						keywords2 = keywords2.trim() + " " + keywords1[i].trim().toLowerCase();
					}
				}
				keywords2 = keywords2.trim();
				boolean containsFor = false;
				if (keywords2.equals("for")) {
					for(int i = 0; i < list.size(); i++) {
						if(list.get(i).matches(".*for.*")) {
							containsFor = true;
							break;
						}
					}
					if(containsFor != true) {
						list.add(keywords2);
					}
				}
				else {
					list.add(keywords2);
				}
			
			}
			else {
				if(phoenixLine.matches("PossibleKeyword:.+")) {
					String[] help = phoenixLine.split("PossibleKeyword:");
					String[] keywords = help[help.length - 1].split("[0-9a-z()\\[\\]_ ]+");
					for(int i = 0; i < keywords.length; i++) {
						if(!keywords[i].isEmpty() && !keywords.equals(null)) {
							keywords[i] = keywords[i].toLowerCase();
							if(!list.contains(keywords[i])) {
								list.add(keywords[i]);
							}
						}
					}
				}
			}
		}
		return list;
	}
	
	private void compile(File compile) {
		ProcessBuilder grammarBuilder = new ProcessBuilder("/bin/tcsh", "compile");
		grammarBuilder.directory(compile);
		
		try {
			Process phoenix =grammarBuilder.start();
			BufferedReader stderr2 = new BufferedReader(new InputStreamReader(phoenix.getErrorStream()));
			String phoenixLine2 = null;
			  
		  	while ((phoenixLine2 = stderr2.readLine()) != null && (!phoenixLine2.equals("READY"))) {
		  		stderr2.close();
		  		phoenix.destroy();
		  		compile(compile);
		  	}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 
}
