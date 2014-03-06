package basicTestCases;


import static org.junit.Assert.*;
import generalControl.Main;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

import nlu.NLUPhase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tts.TTSPhase;
import dm.DMPhase;
import dm.DialogManager;
import dm.Keyword;
import asr.ASRPhase;

/**
 * 
 * @author Meng Meng Yan
 * @version 1.0
 */
public class UserDataInitAndRemoval extends BasicTest{
	
	@After
	public void tearDown2() {
		File newFile = new File("resources/files/UserData/6.json");
		int i = 7;
		while(newFile.exists()) {
			System.out.println("deleting");
			newFile.delete();
			newFile = new File("resources/files/UserData/" + i + ".json");
			i++;
		}
		
		System.out.println("doing smth");
		ArrayList<Keyword> kwList = (ArrayList<Keyword>) DialogManager.giveDialogManager().getDictionary().getKeywordList();
		for(int j = 0; j < kwList.size(); j++) {
			if(kwList.get(i).getKeywordData().getWord().equals("abcd")) {
				System.out.println("doing smth");
				newFile = new File("resources/files/KeywordData/" 
						+ kwList.get(i).getKeywordData().getWordID()
						+ ".json");
				newFile.delete();
			}
		}
	}
	
	
	@Test
	public void userInitTest() {
		
		userInput.add("hello");
		userInput.add("abcd");
		userInput.add("yes");
		userInput.add("yes");
		
		this.runMainActivityWithTestInput(userInput);
		
		assertTrue(Main.giveMain().getNlgResult().contains("know you") 
				|| Main.giveMain().getNlgResult().contains("profile has been saved"));
		
	}
	
	
}
