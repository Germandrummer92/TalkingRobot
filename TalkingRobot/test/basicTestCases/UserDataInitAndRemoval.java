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
	
	@Test
	public void userInitTest() {
		
		userInput.add("hello");
		userInput.add("abcd");
		removableFiles.add("abcd");
		
		userInput.add("yes");
		userInput.add("yes");
		
		this.runMainActivityWithTestInput(userInput);
		
		assertTrue(Main.giveMain().getNlgResult().contains("know you") 
				|| Main.giveMain().getNlgResult().contains("profile has been saved"));
		
	}
	
	
}
