/*
 * Created on 21.01.2008
 */
package one4all.fipa.example;

import java.io.IOException;

import one4all.fipa.net.BasicMessage;
import one4all.fipa.net.MessageListener;
import one4all.fipa.net.One4AllAgent;

public class ExampleO4AClient {

    private static One4AllAgent o4aAgent;
    
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		int commPort = 1917;
		String agentName = "TalkingRobotDM";
		String commHost = "localhost";
		One4AllAgent o4aAgent = new One4AllAgent(agentName,commHost,commPort);
		o4aAgent.connectToCommServer();
		
		//subscribe to msg groups
		o4aAgent.sendSubscription("nlin");
		
		//add msg listener
		o4aAgent.addMessageListener(new ExampleListener());
		
		//send example msg with content to receiver group
		Thread.sleep(2000);
		o4aAgent.sendContentMessage("(msg :X (some value))", "example2");
	}
	
	static class ExampleListener implements MessageListener {

		public void messageArrived(BasicMessage m) {
			if (m.getType().equals("query")) {
				System.out.println("got message: "+m);
				System.out.println("message type: "+m.getType());
				System.out.println("attribute text: "+m.getAttribute("text"));
			}
			
			
		}
		
	}

}
