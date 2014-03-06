package basicTestCases;


import generalControl.Main;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

import nlu.NLUPhase;

import org.junit.After;
import org.junit.Before;

import asr.ASRPhase;
import dm.DMPhase;
import dm.DialogManager;
import dm.Keyword;

/**
 * 
 * @author Meng Meng Yan
 * @version 1.0
 * 
 * A template for the test cases.
 */
abstract public class BasicTest {
	
	public LinkedList<String> userInput;
	public ArrayList<String> removableFiles; //new keywords which need to be deleted after the test
	
	@Before
	public void setUp() {
		userInput = new LinkedList<String>();
		removableFiles = new ArrayList<String>();
	}
	
	@After
	public void tearDown() {
		userInput = null;
		
		File newFile = new File("resources/files/UserData/6.json");
		int i = 7;
		while(newFile.exists()) {
//			System.out.println("deleting");
			newFile.delete();
			newFile = new File("resources/files/UserData/" + i + ".json");
			i++;
		}
		
		ArrayList<Keyword> kwList = 
				(ArrayList<Keyword>) DialogManager.giveDialogManager().getDictionary().findKeywords(removableFiles);
		
		for(int j = 0; j < kwList.size(); j++) {
//			System.out.println(kwList.get(j).getKeywordData().getWordID());
			newFile = new File("resources/files/KeywordData/" 
					+ kwList.get(j).getKeywordData().getWordID() + ".json");
			newFile.delete();
		}
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
