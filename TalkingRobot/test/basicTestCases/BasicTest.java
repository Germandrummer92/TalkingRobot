package basicTestCases;


import generalControl.Main;

import java.util.LinkedList;

import nlu.NLUPhase;

import org.junit.After;
import org.junit.Before;

import asr.ASRPhase;
import dm.DMPhase;

/**
 * 
 * @author Meng Meng Yan
 * @version 1.0
 * 
 * A template for the test cases.
 */
abstract public class BasicTest {
	
	public LinkedList<String> userInput;
	
	@Before
	public void setUp() {
		userInput = new LinkedList<String>();
	}
	
	@After
	public void tearDown() {
		userInput = null;
	}
	
	public void runMainActivityWithTestInput(LinkedList<String> userInput) {
		
		int inputCounter = 0;
		boolean run = true;
		Main.giveMain().setDmPhase(new DMPhase());
		
		do {
			if(Main.giveMain().getPhase() instanceof ASRPhase) {		
				System.out.print("Your next Input:");
				Main.giveMain().setAsrResult(userInput.get(inputCounter));
				System.out.println(userInput.get(inputCounter));
				inputCounter++;
				
				if(inputCounter == userInput.size()) {
					run = false;
				}
				
				Main.giveMain().setPhase(new NLUPhase());
				
			} else {
				Main.giveMain().getPhase().setPhaseResult(Main.giveMain());
				Main.giveMain().setPhase(Main.giveMain().getPhase().nextPhase(Main.giveMain()));
			}
			
		 }
		 while(run || !(Main.giveMain().getPhase() instanceof ASRPhase));
	}

}
