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
	  
	  try {
		  Runtime rt = Runtime.getRuntime();
		  Process compileGra = rt.exec("tcsh compile", null, compile.getAbsoluteFile());
		  
		  BufferedReader stdin1 = new BufferedReader(new InputStreamReader(compileGra.getInputStream()));
		  BufferedReader stderr1 = new BufferedReader(new InputStreamReader(compileGra.getErrorStream()));
		  
		  String phoenixLine = null;
		  
		  System.out.println("compile regular");
		  while ((phoenixLine = stdin1.readLine()) != null) {
			  phoenixOutput.add(phoenixLine);
			  System.out.println(phoenixLine);
		  }
		  System.out.println("compile regular end");
//		  
		  System.out.println("compile error");
		  while ((phoenixLine = stderr1.readLine()) != null) {
			  System.out.println(phoenixLine);
		  }
		  System.out.println("compile error end");
		  
//		  compileGra.destroy();
//
//		  File file = new File("resources/nlu/Phoenix/TalkingRobot/input");
//		  PrintWriter writer = new PrintWriter(file, "UTF-8");
//		  writer.println(input);
//		  writer.close();
		  
//		  BufferedWriter output = new BufferedWriter(new FileWriter(file));
//		  output.write(input);
//		  output.close();
		  
		  

//		  @SuppressWarnings("resource")
//		Scanner sc = new Scanner(System.in);
		  
//		  Runtime rt2 = Runtime.getRuntime();
//		  Process phoenix = rt2.exec("tcsh " + runParse + " < input", null, new File("resources/nlu/Phoenix/TalkingRobot"));
		  
		  ProcessBuilder phoenixBuilder = new ProcessBuilder("/bin/tcsh", "run_parse_keyword < input");
		  phoenixBuilder.directory(new File("resources/nlu/Phoenix/TalkingRobot"));
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
			  phoenixOutput.add(phoenixLine2);
			  System.out.println(phoenixLine2);
		  }
		  System.out.println("run regular end");
		  
		  
		  System.out.println("exec done \n");
	  } catch (IOException e) {
		  e.printStackTrace();
	  }
	  return phoenixOutput;
  }

}