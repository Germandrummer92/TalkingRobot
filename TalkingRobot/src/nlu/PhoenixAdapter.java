package nlu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author Bettina Weller
 * @version 1.0
 * The class PhoenixAdapter is an adapter to use phoenix in a Java program.
 */
public class PhoenixAdapter {
          
  public LinkedList<String> operatePhoenix(String input, String runParse, int extractFlag, File compile) {
	  LinkedList<String> phoenixOutput = new LinkedList<String>();
	  
	  System.out.println(runParse);
	  
	  try {
		  Runtime rt = Runtime.getRuntime();
		  rt.exec("tcsh compile", null, compile.getAbsoluteFile());

		  File file = new File("resources/nlu/Phoenix/TalkingRobot/input");
		  PrintWriter writer = new PrintWriter(file, "UTF-8");
		  writer.println(input);
		  writer.close();
		  
		  ProcessBuilder phoenixBuilder = new ProcessBuilder("/bin/tcsh", runParse);
		  phoenixBuilder.directory(new File("resources/nlu/Phoenix/TalkingRobot"));
		  phoenixBuilder.redirectInput(new File("resources/nlu/Phoenix/TalkingRobot/input"));
		  Process phoenix = phoenixBuilder.start();
		  
		  
		  BufferedReader stdin2 = new BufferedReader(new InputStreamReader(phoenix.getInputStream()));
		  BufferedReader stderr2 = new BufferedReader(new InputStreamReader(phoenix.getErrorStream()));
		  
		  String phoenixLine2 = null;
		  
		  System.out.println("run error");
		  while ((phoenixLine2 = stderr2.readLine()) != null) {
			  
			  System.out.println(phoenixLine2);
		  }
		  System.out.println("run error end");

		  System.out.println("run regular");
		  while ((phoenixLine2 = stdin2.readLine()) != null) {

			  phoenixOutput = insertWords(extractFlag, phoenixOutput, phoenixLine2);
			  System.out.println(phoenixLine2);
		  }
		  System.out.println("run regular end");
		  
		  
	  } catch (IOException e) {
		  e.printStackTrace();
	  }
	  return phoenixOutput;
  }

private LinkedList<String> insertWords(int extractFlag,
		LinkedList<String> list, String phoenixLine) {
	if(extractFlag == 0) {
		if(phoenixLine.matches("Keyword:.+")) {
			String[] help = phoenixLine.split("Keyword:");
			String[] keyword = help[help.length - 1].split("[a-z()\\[\\]_]");
			for(int i = 0; i < keyword.length; i++) {
				if(!keyword[i].equals(" ")
						&& !keyword[i].equals("")
						&& !keyword[i].matches("[A-Z]"))  {
					System.out.println(keyword [i]);
					if(!list.contains(keyword[i].trim().toLowerCase())) {
						list.add(keyword[i].trim().toLowerCase());
					}
				}
			}
			
		}
	} else {		
		if(phoenixLine.matches("Approval:.+")) {
			String[] keyword = phoenixLine.split("Approval:");
			list.add(keyword[keyword.length - 1].trim());
		}
	}
	return list;
}

/**
 * 
 */

//public static void main(String[] args) {
//	PhoenixAdapter pa = new PhoenixAdapter();
//	pa.operatePhoenix("Hello,  can you tell me how to make Spaghetti?", null, 1, new File("resources/nlu/Phoenix/TalkingRobot/Keyword"));
//}
}