package asr;
import java.net.*;

/*import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.tools.sniffer.Message;
*/
import java.io.*;
/**
 * 
 * @author Daniel Draper
 * @version 1.0
 * Adapter to handle communication with the One4All Toolkit.
 */
public class One4AllAdapter {

	protected void setup() {
	//	System.out.println("Hello! Buyer-Agent " + getAID().getName() + " is ready.");
	}
  /**
   * Default Constructor
   */
  public One4AllAdapter() {
	/*  ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
	  msg.addReceiver(new AID("one4all", AID.ISLOCALNAME));
	  msg.setLanguage("one4all");
	  msg.setContent("(init)");
	  send(msg);
	  ACLMessage msgRec = receive();
	  if (msg != null) {
		  System.out.println(msgRec);
	  }*/
  }
  /**
   * Function used to communicate with One4All through cmd-instructions passed.
   * @return if the instruction was completed successfully.
   */
  protected Boolean commandShell() {
	  return null;
  }

  /**
   * Starts the communication with One4All
   */
  public void operateOne4All() {
  }
  
  /**
   * Closes opened communication.
   */
  public void closeOne4All() {
	  /*receiver.tcl handling the par
	   * # ----------------------------------------
#  recog-done
# ----------------------------------------
proc actionRecogDone {args} {
    global par

    set percentage 0
    set hypo       ""
    if [catch {itfParseArgv actionRecogDone $args [list \
        [ list :percentage float  {} percentage   {} ""  ] \
        [ list :hypo       string {} hypo         {} ""  ] \
    ] } msg] {
        error "$msg"
    }

    set par(hypo) $hypo
}
	   */
	  /* receiver.tcl
	   * messages.tcl define par(name) and par(comserver)
	   * # ----------------------------------------
# connect to ComServer
# ----------------------------------------
putsInfo "CONNECT TO SERVER"

if { $par(commHost) == "X" } {
    if { $par(commPort) == "X" } {
	puts "call start comserver on default host with default port"
        set agent [ComAgent "$par(name)" -server "$par(comserver)"]
    } else {
	puts "call start comserver on default host with port $par(commPort)"
        set agent [ComAgent "$par(name)" -server "$par(comserver)" -port "$par(commPort)"]
    }
} else {
    if { $par(commPort) == "X" } {
	puts "call start comserver on host $par(commHost) with default port"
        set agent [ComAgent "$par(name)" -server "$par(comserver)" -host "$par(commHost)"]
    } else {
	puts "call start comserver on host $par(commHost) with port $par(commPort)"
        set agent [ComAgent "$par(name)" -server "$par(comserver)" -host "$par(commHost)" -port "$par(commPort)"]
    }
}
	   */
  }

  public static void main(String[] args) {
	  try {
		  Socket sock = new Socket("localhost", 1917);
		  InputStream in = sock.getInputStream();
		  OutputStream out = sock.getOutputStream();
		  PrintWriter print = new PrintWriter(out);
		  BufferedReader bin = new BufferedReader(new InputStreamReader(in));
		  print.println("(inform");
  print.println(":sender java2");
  print.println(":receiver one4all");
  print.println(":language one4all");
  print.println(":reply-with java2-msg-0");
  print.println(":content #6 \"(init)\"");
  print.println(")");
		  String line; 
				  while ((line = bin.readLine()) != null) {
					  System.out.println(line);
			  }
		  sock.close();
	  }
	  catch (IOException ioe) {
		  System.err.println();
	  }
	//  One4AllAdapter one4all = new One4AllAdapter();
  }
}