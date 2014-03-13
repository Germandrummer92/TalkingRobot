package basicTestCases;




import generalControl.Main;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

import nlg.NLGPhase;
import nlu.NLUPhase;

import org.junit.After;
import org.junit.Before;

import tts.TTSPhase;
import asr.ASRPhase;
import dm.DMPhase;
import dm.DialogManager;
import dm.Keyword;

/**
 * 
 * @author Meng Meng Yan
 * @version 1.0
 * 
 * useful functions for the test cases.
 */
abstract public class BasicTest {
	
	public LinkedList<String> userInput;
	public ArrayList<String> removableFiles; //new keywords which need to be deleted after the test
	public ArrayList<String> nlgResults;
	public ArrayList<String> dmResults;
	
	public int userDataCount;
	public int recipeDataCount;
	public int ingredientDataCount;
	public int toolDataCount;
	
	@Before
	public void setUp() {
		userInput = new LinkedList<String>();
		removableFiles = new ArrayList<String>();
		nlgResults = new ArrayList<String>();
		dmResults = new ArrayList<String>();
		
		userDataCount = new File("resources/files/UserData/").listFiles().length;
		recipeDataCount = new File("resources/files/RecipeData/").listFiles().length;
		ingredientDataCount = new File("resources/files/IngredientData/").listFiles().length;
		toolDataCount = new File("resources/files/ToolData/").listFiles().length;
		
	}
	
	@After
	public void tearDown() {
		userInput = null;
		nlgResults = null;
		dmResults = null;
		
		//deleting new userData
		File newFile = new File("resources/files/UserData/" + userDataCount + ".json");
		userDataCount++;
		while(newFile.exists()) {
//			System.out.println("deleting");
			newFile.delete();
			newFile = new File("resources/files/UserData/" + userDataCount + ".json");
			userDataCount++;
		}
		userDataCount = 0;
		
		//deleting new recipeData
		newFile = new File("resources/files/RecipeData/" + recipeDataCount + ".json");
		recipeDataCount++;
		while(newFile.exists()) {
//			System.out.println("deleting");
			newFile.delete();
			newFile = new File("resources/files/RecipeData/" + recipeDataCount + ".json");
			recipeDataCount++;
		}
		recipeDataCount = 0;
		
		//deleting new ingredientData
		newFile = new File("resources/files/IngredientData/" + ingredientDataCount + ".json");
		ingredientDataCount++;
		while(newFile.exists()) {
//			System.out.println("deleting");
			newFile.delete();
			newFile = new File("resources/files/IngredientData/" + ingredientDataCount + ".json");
			ingredientDataCount++;
		}
		ingredientDataCount = 0;
		
		//deleting new toolData
		newFile = new File("resources/files/ToolData/" + toolDataCount + ".json");
		toolDataCount++;
		while(newFile.exists()) {
//			System.out.println("deleting");
			newFile.delete();
			newFile = new File("resources/files/ToolData/" + toolDataCount + ".json");
			toolDataCount++;
		}
		toolDataCount = 0;
		
		
		//deleting new keywordData
		
		
		for(String s : removableFiles) {
			DialogManager.giveDialogManager().getDictionary().removeKeyword(s);
		//	newFile = new File("resources/files/KeywordData/" 
		//			+ kwList.get(j).getKeywordData().getWordID() + ".json");
		//	newFile.delete();
		}
		
		removableFiles = null;
		DialogManager.giveDialogManager().refresh();
	}
	
	public void runMainActivityWithoutConsoleOutput(LinkedList<String> userInput) {
		
		int inputCounter = 0;
		boolean run = true;
		Main.giveMain().setDmPhase(new DMPhase());
		
		do {
			if(Main.giveMain().getPhase() instanceof ASRPhase) {		
				Main.giveMain().setAsrResult(userInput.get(inputCounter));
				inputCounter++;
				
				if(inputCounter == userInput.size()) {
					run = false;
				}
				
				Main.giveMain().setPhase(new NLUPhase());
				
			} else if (Main.giveMain().getPhase() instanceof TTSPhase){
				
				Main.giveMain().setPhase(Main.giveMain().getPhase().nextPhase(Main.giveMain()));
				
			} else {
				
				Main.giveMain().getPhase().setPhaseResult(Main.giveMain());
				Main.giveMain().setPhase(Main.giveMain().getPhase().nextPhase(Main.giveMain()));
				
			}
			
			if (Main.giveMain().getPhase() instanceof NLGPhase) {
				
				dmResults.add(Main.giveMain().getDmResult().getOutputKeyword());
				
			}
			
		 }
		 while(run || !(Main.giveMain().getPhase() instanceof ASRPhase));
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
			
			if(Main.giveMain().getPhase() instanceof TTSPhase) {
				nlgResults.add(Main.giveMain().getNlgResult());
			} else if (Main.giveMain().getPhase() instanceof NLGPhase) {
				dmResults.add(Main.giveMain().getDmResult().getOutputKeyword());
			}
			
		 }
		 while(run || !(Main.giveMain().getPhase() instanceof ASRPhase));
	}
	
	/**
	 * returns random number between 0 and the limit (limit excluded)
	 * @param limit the limit
	 * @return a random number
	 */
	public int getRandomNum(int limit) {
		return (int) (Math.random() * (limit));
	}

}
