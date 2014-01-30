package nlu;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 * 
 * @author Bettina Weller
 * @version 1.0
 * The class PhoenixAdapter is an adapter to use phoenix in a Java program.
 */
public class PhoenixAdapter {
          
  public LinkedList<String> operatePhoenix(String runParse, int extractFlag, File compile) {
	  LinkedList<String> phoenixOutput = new LinkedList<String>();
	  
	  System.out.println(runParse);
	  
	  try {
		  //compile grammar
		  Runtime rt = Runtime.getRuntime();
		  rt.exec("tcsh compile", null, compile.getAbsoluteFile());
		  
		  //run parse for file input
		  ProcessBuilder phoenixBuilder = new ProcessBuilder("/bin/tcsh", runParse);
		  phoenixBuilder.directory(new File("resources/nlu/Phoenix/TalkingRobot"));
		  phoenixBuilder.redirectInput(new File("resources/nlu/Phoenix/TalkingRobot/input"));
		  Process phoenix = phoenixBuilder.start();
		  
		  
		  BufferedReader stdin2 = new BufferedReader(new InputStreamReader(phoenix.getInputStream()));
		  BufferedReader stderr2 = new BufferedReader(new InputStreamReader(phoenix.getErrorStream()));
		  
		  String phoenixLine2 = null;
		  
		  while ((phoenixLine2 = stderr2.readLine()) != null) {
			  
			  System.out.println(phoenixLine2);
		  }

		  while ((phoenixLine2 = stdin2.readLine()) != null) {

			  phoenixOutput = insertWords(extractFlag, phoenixOutput, phoenixLine2);
			  System.out.println(phoenixLine2);
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
			String[] keywords1 = phoenixLine.split("Keyword:");
			String[] keywords2 = keywords1[keywords1.length - 1].split("_");
			String keywords3 = "";
			for(int i = 0; i < keywords2.length; i++) {
				keywords3 = keywords3.trim() + " " + keywords2[i].trim().toLowerCase();
			}
			keywords3.trim();
			if(!list.contains(keywords3)) {
				list.add(keywords3);	
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
			String[] keywords1 = help[help.length - 1].split("[a-z()\\[\\]_]+");
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
				String[] keywords = help[help.length - 1].split("[a-z()\\[\\]_ ]+");
				for(int i = 0; i < keywords.length; i++) {
					if(keywords[i].equals("[A-Z]")) {
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
 
}