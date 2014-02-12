package nlg;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import dm.*;

/**
 * This class represents a creator of output 
 * @author Luiz Henrique Soares Silva, Xizhe Lian
 * @version 1.2
 */
public class OutputCreator {
	
	public OutputCreator() {
	}
  
	/**
	 * Generates final output for the Robot, in the current Dialog.
	 * 
	 * @param dialogState the current system's dialog state
	 * @return output as String
	 */
	public String createOutput(DialogState dialogState) {
		//get the dialog state class, old implementation gave out just the abstract name dm.dialogState
		String output = "";

		String dialogStateClass = dialogState.getClass().getCanonicalName();//getStateClassFromEnum(dialogState.getCurrentState());
		
		//tempSentence contains the sentence obtained directly from the json file, without replacements. It's a raw sentence.
		String tempSentence = findInFile(dialogStateClass, dialogState.getCurrentState().toString());
		//Add error keywords
		if( DialogManager.giveDialogManager().isInErrorState()) {
			String eOut = tempSentence;
			//if( dialogState.getOutputKeyword() != null ) {
				eOut = addEKeywords(tempSentence, dialogState.getOutputKeyword(), dialogState);
			//}
			return eOut;
		}
		String kw = dialogState.getOutputKeyword();
		
		if (kw != null) {
			output = addKeyword(tempSentence, kw, dialogState);
		} else {
			output = tempSentence;
		}
			
		// random decide whether to add a social component or not
		Random socialRandom = new Random();
		Integer toAdd = socialRandom.nextInt(2);  // 0 for no, 1 for yes
		
		if(toAdd == 1) {
			String outputWithSocialComponent = addSocialComponent(dialogState, output);
			if (outputWithSocialComponent != null) {
				output = outputWithSocialComponent;
			}
		}
		
		return output;
	}

	/**
	 * Add keywords for error handling to template sentences
	 * @param temp template sentence
	 * @param dialogState 
	 * @param outputKeyword, a string with keywords 
	 * @return the right sentence for output
	 */
	private String addEKeywords(String text, String keywords, DialogState dialogState) {
		String answer = " ";
		try {
			
			String[] evaObjs = {"<1>", "<2>", "<3>"};
	  		//String evaObj1 = "<1>";
	  		//String evaObj2 = "<2>";
	  		//String evaObj3 = "<3>";
	  		String evaluationCompl = "{c}";
	  		    
	  		String[] sentences = null;
	  		String[] keywordPhrases = keywords.split(","); 
	  		String[] objs = { "", "", "" };
	  		String compl = "";
	  		if(text.contains(".")) {
	  			sentences = text.split(".");
	  		} else {
	  			sentences = new String[1]; // nothing to split
	  			sentences[0] = text;
	  		}
	  		
	  		for(int i = 0; i < keywordPhrases.length; i++) {
	  			//String[] keywordArray = keywordPhrases[i].split(" ");
	  			//for (int j = 0; j < keywordPhrases.length; j++){
		  			if( keywordPhrases[i].contains("<") && keywordPhrases[i].contains(">") ) {
		  				objs[i] = keywordPhrases[i].substring(1, keywordPhrases[i].length() - 1);
		  			}
		  			if( keywordPhrases[i].contains("{") && keywordPhrases[i].contains("}")) {
		  				compl = keywordPhrases[i].substring(1, keywordPhrases[i].length() - 1);
		  				if(compl.contains("canteen")) {
		  					compl = "Eating in canteen";
		  				} 
		  				
		  				if(compl.contains("kitchen")) {
		  					compl = "Self cooking";
		  				}
		  			}
	  			}
	  			
	  	
		  		
	  		for(int i = 0; i < sentences.length; i++) {
	  			for(int j = 0; j < objs.length; j++) {
		  			if(sentences[i].contains(evaObjs[j])) {
		  				sentences[i].replace(evaObjs[j], objs[j]);
		  			}
	  			}
	  			if(sentences[i].contains(evaluationCompl)) {
	  				sentences[i].replace(evaluationCompl, compl);
	  			}	  
	  			sentences[i] = sentences[i] + ". "; // blank between 2 sentences
	  		}
	  		
	  		for(int i = 0; i < sentences.length; i++) {
	  			answer = answer + sentences[i];
	  		}
  	
	  		return answer;
		
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return answer;
	}

	/**
	 * Searches in the sentences.json file, for the specified parameters.
	 *  
	 * @param className the class Name of which DialogState implement is the currentState in the DMPhase
	 * @param stateName the Sub-state name (e.g. ENTRY, WAITING_FOR_ANSWER) represented by an enum.
	 * @param num a random generated number to retrieve one of the possible options of sentence.
	 * @return the matching String
	 */
  	private String findInFile(String className, String stateName) {
  		
  		JSONParser parser = new JSONParser();
  		 
  		try {
  	 
  			//Location of sentences.json file
  			Object obj = parser.parse(new FileReader("resources/nlg/sentences.json"));
  	 
  			//Access to a sentence
  			JSONObject jsonObject = (JSONObject) obj;
  			
  			//TODO Remove this print!
  			//System.out.println(className);
  			
  			JSONObject jsonState = (JSONObject) jsonObject.get(className);
  			JSONArray jsonSentences = (JSONArray) jsonState.get(stateName);
  			if (jsonSentences == null) {
  				//Error!
  				throw new NullPointerException("State " + stateName + " not found!");
  			}
  			Integer size = jsonSentences.size();
  			
  			//Generates random number based on array size (number of sentences)
  			if(size > 1) {
	  			Random rn = new Random();
				Integer randomNum = rn.nextInt(size);
				
				return (String) jsonSentences.get(randomNum);
  			}
  			return (String) jsonSentences.get(0);
  		
  		} catch (FileNotFoundException e) {
  			e.printStackTrace();
  		} catch (IOException e) {
  			e.printStackTrace();
  		} catch (ParseException e) {
  			e.printStackTrace();
  		} catch (NullPointerException e) {
  			System.out.println(e.getMessage());
  		}
  		
  		return null;
  	}
  	
  	 /**
  	 * Add social component to the output. If it doesn't find anything to add before, it tries to find
  	 * a social component to add after the sentence.
     * @param dialogState
     * @return a social component in string
     */
  	private String addSocialComponent(DialogState dialogState, String output) {
  		//String keyword = dialogState.getOutputKeyword();
  		JSONParser parser = new JSONParser();
 		boolean addBefore = true;
  		try {
  	 
  			//Location of sentences.json file
  			Object obj = parser.parse(new FileReader("resources/nlg/socialBefore.json"));
  	 
  			//Access to a sentence
  			//added
  			String dialogStateClass = dialogState.getClass().getCanonicalName();//getStateClassFromEnum(dialogState.getCurrentState());
  			//added
  			JSONObject jsonObject = (JSONObject) obj;
  			JSONObject jsonState = (JSONObject) jsonObject.get(dialogStateClass);
  			JSONArray jsonSentences = (JSONArray) jsonState.get(dialogState.getCurrentState().toString());
  			Integer size = jsonSentences.size();
  			
  			//selects social component randomly
  			String temp;
  			Random rn = new Random();
  			Integer randomNum = rn.nextInt(size);
			temp =  (String) jsonSentences.get(randomNum);
			
			//search for a social component to be added after, in case there's no social component.
			if (temp.equals("")) {
  				obj = parser.parse(new FileReader("resources/nlg/socialAfter.json"));
  				addBefore = false;
  				jsonObject = (JSONObject) obj;
  	  			jsonState = (JSONObject) jsonObject.get(dialogStateClass);
  	  			jsonSentences = (JSONArray) jsonState.get(dialogState.getCurrentState().toString());
  	  			
	  			rn = new Random();
	  			randomNum = rn.nextInt(size);
				temp = (String) jsonSentences.get(randomNum);
				
				if (temp.equals("")) {
					return output;
				}
			}
			
			if(addBefore) { // add social component before the 
				output = temp + ". " + output; 
			} else {
				output = output + ". " + temp;
			}
  		
  		} catch (FileNotFoundException e) {
  			e.printStackTrace();
  		} catch (IOException e) {
  			e.printStackTrace();
  		} catch (ParseException e) {
  			e.printStackTrace();
  		}
  	  
  		return output;
  	}
  	
  	/**
  	 * Add keyword to sentences from template. It replaces placeholder in the sentences, such as <o>.
  	 * @param text text from template
  	 * @param keyword keyword from dialogStates
  	 * @return a complete answer as string
  	 */
  	private String addKeyword(String text, String keyword, DialogState s){
  		String answer = "";
  		String[] evaluationObjs = {"<o>", "<1>", "<2>", "<3>"};
  		String evaluationCompl = "{c}";
  		ArrayList<String> objs = new ArrayList<String>();
  		String compl = "";
	
  		String[] sentences = null;
  		String[] keywordPhrases = null;
  		
  		if(s.getCurrentState().equals(CanteenInfo.CI_ADEN_TELL_ALL_MEALS) 
  				|| s.getCurrentState().equals(CanteenInfo.CI_MOLTKE_TELL_ALL_MEALS)) {
	  		
	  			keywordPhrases = new String[1];
	  			keywordPhrases[0] = keyword; // nothing to split
	  		
  		} else {
  			if(keyword.contains(",")) {
  				keywordPhrases = keyword.split(",");
  			} else {
  				keywordPhrases = new String[1];
  				keywordPhrases[0] = keyword;
  			}
  		}
  		
  		//Critical point! From now on, dots (".") should only be used to separate sentences!!
  		if(text.contains(".")) {
  			sentences = text.split("\\.");
  		} else {
  			sentences = new String[1]; // nothing to split
  			sentences[0] = text;
  		}
  		
  		for(int i = 0; i < keywordPhrases.length; i++) {
  			if(keywordPhrases[i].contains("<")) {
  				//obj. = keywordPhrases[i].substring(1, keywordPhrases[i].length() - 1);
  				objs.add(keywordPhrases[i].substring(1, keywordPhrases[i].length() -1));
  			}
  			if(keywordPhrases[i].contains("{") ) {
  				compl = keywordPhrases[i].substring(1, keywordPhrases[i].length() - 1);
  			}
  		}
  			
  		for(int i = 0; i < sentences.length; i++) {
  			//int j = 0;
  			for (String evalObj : evaluationObjs) {
  				if(sentences[i].contains(evalObj)) {
  					if (!objs.isEmpty()) //So that it doesnt throws a NullPointerException
  						sentences[i] = sentences[i].replace(evalObj, objs.remove(0));
  				}
  			}
  			if(sentences[i].contains(evaluationCompl)) {
  				sentences[i] = sentences[i].replace(evaluationCompl, compl);
  			}	
  			
  			//sentences[i] = sentences[i] + ". "; // blank between 2 sentences
  		}
  		
  		for(int i = 0; i < sentences.length; i++) {
  			if (answer.equals("")) {
  				answer = sentences[i];
  			}
  			else {
  				answer = answer + ". " + sentences[i];
  			}
  		}
  		
  		return answer;
  	}
  	
  	//Testing Why is there a null in front of the sentence if you run this? The Object can't be replaced since no robotData is loaded, but why is there the null????
 /* 
  public static void main (String args[]) {
	  	OutputCreator creator = new OutputCreator();
	  	//StartState startState = new StartState();
	  	User user = new User();
	  	Robot r = new Robot("L", false);
		Session s = new Session(user, r);
		CanteenData cd = new CanteenData(CanteenNames.ADENAUERRING, 0);
		Canteen c = new Canteen(cd);
	  	CanteenInformationState ci = new CanteenInformationState();
	  	Dialog cdia = new CanteenInformationDialog(s, ci, c);
	  	((CanteenInformationDialog) cdia).setWishMeal(c.getCanteenData().getLines().get(0).getTodayMeals().get(0).getMealName());
	  	DialogManager.giveDialogManager().setCurrentDialog(cdia);
	  	//ci.setCurrentState(CanteenInfo.CI_ADEN_LINE_1_PRICE);
	  	ci.setCurrentState(CanteenInfo.CI_ADEN_TELL_ALL_MEALS);
	  	System.out.println(creator.createOutput(ci));
  	}*/
}
