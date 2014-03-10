package asr;
import java.net.*;
import java.io.*;
/**
 * 
 * @author Daniel Draper
 * @version 1.0
 * Adapter to handle communication with the One4All Toolkit.
 */
public class One4AllAdapter {

  /**
   * Default Constructor
   */
  public One4AllAdapter() {
	  
  }
  /**
   * Function used to communicate with One4All through cmd-instructions passed.
   * @return if the instruction was completed successfully.
   */
  protected Boolean commandShell() {
	  return null;
  }

  /**
   * Starts the communication with One4All.
   */
  public void operateOne4All() {
  }
  
  /**
   * Closes opened communication.
   */
  public void closeOne4All() {
  }

  public static void main(String[] args) {
	  try {
		  Socket sock = new Socket("127.0.0.1", 1917);
		  InputStream in = sock.getInputStream();
		  OutputStream out = sock.getOutputStream();
		  PrintWriter print = new PrintWriter(out);
		  BufferedReader bin = new BufferedReader(new InputStreamReader(in));
		  print.println("MESSAGE {message inform :sender java :receiver one4all :language one4all :reply-with receiver-msg-1 :content {(subscribe nlin)}}");
		  String line; 
			  while ((line = bin.readLine()) != null) {
				  System.out.println(line);
			  }
		  sock.close();
	  }
	  catch (IOException ioe) {
		  System.err.println();
	  }
  }
}