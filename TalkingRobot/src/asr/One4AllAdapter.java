package asr;



import generalControl.Main;

import java.io.*;


import one4all.fipa.net.BasicMessage;
import one4all.fipa.net.MessageListener;
import one4all.fipa.net.One4AllAgent;
/**
 * 
 * @author Daniel Draper
 * @version 1.0
 * Adapter to handle communication with the One4All Toolkit.
 */
public class One4AllAdapter {
	private One4AllAgent one4all;
	private static One4AllAdapter uniqueAdapter;
  /**
   * Default Constructor
   */
  private One4AllAdapter() {
	  	int commPort = 1917;
		String agentName = "TalkingRobotDM";
		String commHost = "localhost";
		One4AllAgent o4aAgent = new One4AllAgent(agentName,commHost,commPort);
		one4all = o4aAgent;
		try {
			o4aAgent.connectToCommServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//subscribe to msg groups
		o4aAgent.sendSubscription("nlin");
		
		//add msg listener
		o4aAgent.addMessageListener(new ExampleListener());
		
		//send example msg with content to receiver group
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
  }
  public static One4AllAdapter giveOne4AllAdapter() {
	  if (uniqueAdapter == null) {
		  uniqueAdapter = new One4AllAdapter();
		  return uniqueAdapter;
	  }
	  else {
		  return uniqueAdapter;
	  }
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
	
  }

  static class ExampleListener implements MessageListener {

		public void messageArrived(BasicMessage m) {
			if (m.getType().equals("query")) {
			//	System.out.println("got message: "+m);
			//	System.out.println("message type: "+m.getType());
				System.out.println("attribute text: "+m.getAttribute("text"));
				String asrString = m.getAttribute("text").toString();
				asrString = asrString.replace("(", "");
				asrString = asrString.replace(")", "");
				asrString = asrString.toLowerCase();
				//asrString = asrString.replace(" ", "");
				System.out.println("passed to asr: " + asrString);
				Main.giveMain().setAsrResult(asrString);
				Main.giveMain().setAsrReceived(true);
			}
			
			
		}
		
	}
  public static void main(String[] args) {
	  new One4AllAdapter();
  }
public One4AllAgent getOne4all() {
	return one4all;
}
public void setOne4all(One4AllAgent one4all) {
	this.one4all = one4all;
}
}