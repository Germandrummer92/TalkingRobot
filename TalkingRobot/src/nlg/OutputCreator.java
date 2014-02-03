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

import dm.CanteenInformationState;
import dm.CanteenRecommendationState;
import dm.DialogManager;
import dm.DialogState;
import dm.KitchenAssistanceState;
import dm.RecipeAssistanceState;
import dm.RecipeLearningState;
import dm.StartState;

/**
 * This class represents a creator of output 
 * @author Luiz Henrique Soares Silva, Xizhe Lian
 * @version 1.0
 */
public class OutputCreator {
	
	//TODO Still to be decided if following attributes will be needed.
	private List<Generator> generators;

	private List<Phrase> outputPhrases;

	private SocialComponent socialComponent;
	
	public OutputCreator() {
	}
  
	/**
	 * Generates final output for the Robot, in the current Dialog.
	 * 
	 * @param dialogState the current system's dialog state
	 * @return output as String
	 */
	public String createOutput(DialogState dialogState) {
		//generators = new ArrayList<Generator>();
		//outputPhrases = new ArrayList<Phrase>();
		System.out.println(dialogState.getCurrentState().toString());
		String temp = findInFile(dialogState.getClass().getName(), dialogState.getCurrentState().toString());
		
		if( DialogManager.giveDialogManager().isInErrorState()) {
			String eOut = temp;
			if( !dialogState.getOutputKeyword().isEmpty() ) {
				eOut = addEKeywords(temp, dialogState.getOutputKeyword(), dialogState);
			}
			return eOut;
		}

		String output = addKeyword(temp, dialogState.getOutputKeyword());
		
			
		// random decide whether to add a social component or not
		Random socialRandom = new Random();
		Integer toAdd = socialRandom.nextInt(2);  // 0 for no, 1 for yes
		
		if(toAdd == 1) {
			
			output = addSocialComponent(dialogState, output);
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
  			JSONObject jsonState = (JSONObject) jsonObject.get(className);
  			JSONArray jsonSentences = (JSONArray) jsonState.get(stateName);
  			Integer size = jsonSentences.size();
  			
  			//Generates random number based on array size (number of sentences)
  			if(size > 2) {
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
  			JSONObject jsonObject = (JSONObject) obj;
  			JSONObject jsonState = (JSONObject) jsonObject.get(dialogState.getClass().getName());
  			JSONArray jsonSentences = (JSONArray) jsonState.get(dialogState.getCurrentState().toString());
  			Integer size = jsonSentences.size();
  			
  			if(size == 0) {
  				obj = parser.parse(new FileReader("resources/nlg/socialAfter.json"));
  				addBefore = false;
  				jsonObject = (JSONObject) obj;
  	  			jsonState = (JSONObject) jsonObject.get(dialogState.getClass().getName());
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
  	 * Add keyword to sentences from template
  	 * @param text text from template
  	 * @param keyword keyword from dialogStates
  	 * @return a complete answer as string
  	 */
  	private String addKeyword(String text, String keyword){
  		String answer = " ";
  		String evaluationObj = "<o>";
  		String evaluationCompl = "{c}";
  		//String evaluationTime = "{time}";
  		
  		//String[] tokens = sentences.split(" "); 
  		if(keyword != null) {
  		    
	  		String[] sentences = null;
	  		String[] keywordPhrases = keyword.split(","); 
	  		String obj = "";
	  		String compl = "";
	  		if(text.contains(".")) {
	  			sentences = text.split(".");
	  		} else {
	  			sentences = new String[1]; // nothing to split
	  			sentences[0] = text;
	  		}
	  		
	  		for(int i = 0; i < keywordPhrases.length; i++) {
	  			String[] keywords = keywordPhrases[i].split(" ");
	  			for (int j = 0; j < keywords.length; j++){
		  			if(keywords[j].contains("<") && keywords[j].contains(">")) {
		  				obj = keywords[j].substring(1, keywords[j].length() - 2);
		  			}
		  			if(keywords[j].contains("{") && keywords[j].contains("}")) {
		  				compl = keywords[j].substring(1, keywords[j].length() - 2);
		  			}
	  			}
	  			
	  		}
	  		
	  		
	  		for(int i = 0; i < sentences.length; i++) {
	  			if(sentences[i].contains(evaluationObj)) {
	  				sentences[i].replace(evaluationObj, obj);
	  			}
	  			if(sentences[i].contains(evaluationCompl)) {
	  				sentences[i].replace(evaluationCompl, compl);
	  			}	  
	  			sentences[i] = sentences[i] + ". "; // blank between 2 sentences
	  		}
	  		
	  		for(int i = 0; i < sentences.length; i++) {
	  			answer = answer + sentences[i];
	  		}
  		} else {answer = text;} // don't need to add keywords
  		
  		
  		return answer;
  	}
  	
  	//Testing Why is there a null in front of the sentence if you run this? The Object can't be replaced since no robotData is loaded, but why is there the null????
  	
  /*public static void main (String args[]) {
	  	OutputCreator creator = new OutputCreator();
	  	StartState startState = new StartState();
	  	System.out.println(creator.createOutput(startState));
  	}*/
}