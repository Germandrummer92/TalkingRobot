package nlg;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import data.*;
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

		String dialogStateClass = getStateClassFromEnum(dialogState.getCurrentState());
		
		//tempSentence contains the sentence obtained directly from the json file, without replacements. It's a raw sentence.
		String tempSentence = findInFile(dialogStateClass, dialogState.getCurrentState().toString());
		//Add error keywords
		if( DialogManager.giveDialogManager().isInErrorState()) {
			String eOut = tempSentence;
			if( !dialogState.getOutputKeyword().isEmpty() ) {
				eOut = addEKeywords(tempSentence, dialogState.getOutputKeyword(), dialogState);
			}
			return eOut;
		}
		String kw = dialogState.getOutputKeyword();
		//FIXME here dialgoState.getOutputKeyword() is always null!!!!!!
		if (kw != null) {
			output = addKeyword(tempSentence, kw);
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
	  			for (int j = 0; j < keywordPhrases.length; j++){
		  			if( keywordPhrases[j].contains("<") && keywordPhrases[j].contains(">") ) {
		  				objs[j] = keywordPhrases[j].substring(1, keywordPhrases[j].length() - 2);
		  			}
		  			if( keywordPhrases[j].contains("{") && keywordPhrases[j].contains("}")) {
		  				compl = keywordPhrases[j].substring(1, keywordPhrases[j].length() - 2);
		  				if(compl.contains("canteen")) {
		  					compl = "Eating in canteen";
		  				} 
		  				
		  				if(compl.contains("kitchen")) {
		  					compl = "Self cooking";
		  				}
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
  			System.out.println(className);
  			
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
				Integer randomNum = rn.nextInt(size - 1);
				
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
  	 * Add social component to the output 
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
  			String dialogStateClass = getStateClassFromEnum(dialogState.getCurrentState());
  			//added
  			JSONObject jsonObject = (JSONObject) obj;
  			JSONObject jsonState = (JSONObject) jsonObject.get(dialogStateClass);
  			JSONArray jsonSentences = (JSONArray) jsonState.get(dialogState.getCurrentState().toString());
  			Integer size = jsonSentences.size();
  			
  			if(size == 0) {
  				obj = parser.parse(new FileReader("resources/nlg/socialAfter.json"));
  				addBefore = false;
  				jsonObject = (JSONObject) obj;
  	  			jsonState = (JSONObject) jsonObject.get(dialogStateClass);
  	  			jsonSentences = (JSONArray) jsonState.get(dialogState.getCurrentState().toString());
  	  			size = jsonSentences.size();
  			}
  			//Nothing Changes if both Social parts are empty
  			if (size == 0) {
  				return output;
  			}
  			String temp;
  			
  			//Generates random number based on array size (number of sentences)
  			if(size < 2) {
  				temp =  (String) jsonSentences.get(0);
  			}
  			else {
  				Random rn = new Random();
  				Integer randomNum = rn.nextInt(size - 1);
			
				temp =  (String) jsonSentences.get(randomNum);
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
  	private String addKeyword(String text, String keyword){
  		String answer = "";
  		String evaluationObj = "<o>";
  		String evaluationCompl = "{c}";
  		//String evaluationTime = "{time}";
  		
  		//String[] tokens = sentences.split(" "); 
  	//	if(keyword != null) {
  		    
	  		String[] sentences = null;
	  		String[] keywordPhrases = null;
	  		
	  		if(keyword.contains(",")) {
	  			keywordPhrases = keyword.split(","); 
	  		} else {
	  			keywordPhrases = new String[1];
	  			keywordPhrases[0] = keyword;
	  		}
	  		String obj = "";
	  		String compl = "";
	  		
	  		//Critical point! From now on, dots (".") should only be used to separate sentences!!
	  		if(text.contains(".")) {
	  			sentences = text.split("\\.");
	  		} else {
	  			sentences = new String[1]; // nothing to split
	  			sentences[0] = text;
	  		}
	  		
	  		for(int i = 0; i < keywordPhrases.length; i++) {
	  		//	String[] keywords = keywordPhrases[i].split(" ");
	  			//for (int j = 0; j < keywords.length; j++){
		  			if(keywordPhrases[i].contains("<")) {
		  				obj = keywordPhrases[i].substring(1, keywordPhrases[i].length() - 1);
		  			}
		  			if(keywordPhrases[i].contains("{") ) {
		  				compl = keywordPhrases[i].substring(1, keywordPhrases[i].length() - 1);
		  			}
	  			}
	  			
	  		for(int i = 0; i < sentences.length; i++) {
	  			if(sentences[i].contains(evaluationObj)) {
	  				sentences[i] = sentences[i].replace(evaluationObj, obj);
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
  	//	} else {answer = text;} // don't need to add keywords
  		
  		
  		return answer;
  	}
  	
  	private String getStateClassFromEnum(Enum<?> num) {
		String dialogStateClass = "";
		switch (num.getClass().toString()) {
		case "class dm.Start":
			dialogStateClass = "dm.StartState";
			break;
		case "class dm.RecipeLearning":
			dialogStateClass = "dm.RecipeLearningState";
			break;
		case "class dm.RecipeAssistance":
			dialogStateClass = "dm.RecipeAssistanceState";
			break;
		case "class dm.CanteenInfo":
			dialogStateClass = "dm.CanteenInformationState";
			break;
		case "class dm.CanteenRecom":
			dialogStateClass = "dm.CanteenRecommendationState";
			break;
		case "class dm.KitchenAssistance":
			dialogStateClass = "dm.KitchenAssistanceState";
			break;
		case "class dm.ErrorHandling":
			dialogStateClass = "dm.ErrorHandlingState";
			break;
		}
		return dialogStateClass;
  	}
  	//Testing Why is there a null in front of the sentence if you run this? The Object can't be replaced since no robotData is loaded, but why is there the null????
  
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
	  	DialogManager.giveDialogManager().setCurrentDialog(cdia);
	  	ci.setCurrentState(CanteenInfo.CI_ADEN_TELL_ALL_MEALS);
	  	System.out.println(creator.createOutput(ci));
  	}
}
