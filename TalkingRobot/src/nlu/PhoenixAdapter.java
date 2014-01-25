package nlu;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Bettina Weller
 * @version 1.0
 * The class PhoenixAdapter is an adapter to use phoenix in a Java program.
 */
public class PhoenixAdapter {
          
  public LinkedList<String> operatePhoenix(String input, String runParse, int extractFlag, File compile) {
	  try {
		  Runtime rt = Runtime.getRuntime();
		  rt.exec("/bin/tcsh compile", null, compile.getAbsoluteFile());
		  
		  System.out.println("exec done \n");
		  
		  File file = new File("resources/nlu/Phoenix/TalkingRobot/input");
		  PrintWriter writer = new PrintWriter(file, "UTF-8");
		  writer.println(input);
		  writer.close();
		  
//		  BufferedWriter output = new BufferedWriter(new FileWriter(file));
//		  output.write(input);
//		  output.close();
		  rt.exec("/bin/tcsh " + runParse + " < input", null, new File("resources/nlu/Phoenix"));
		  
	  } catch (IOException e) {
		  e.printStackTrace();
	  }
	  return new LinkedList<String>();
  }

}