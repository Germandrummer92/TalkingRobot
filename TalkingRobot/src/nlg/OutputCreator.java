package nlg;


/**
 * 
 * @author Xizhe Lian, Luiz Henrique Soares Silva
 * @version 0.1
 */
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
import dm.DialogState;
import dm.KitchenAssistanceState;
import dm.RecipeAssistanceState;
import dm.RecipeLearningState;
import dm.StartState;

/**
 * 
 * @author Luiz Henrique Soares Silva, Xizhe Lian
 *
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
		generators = new ArrayList<Generator>();
		outputPhrases = new ArrayList<Phrase>();
		
		String temp = findInFile(dialogState.getClass().getName(), dialogState.getCurrentState().toString());
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
  			//TODO Sentences.json still has to be completely fullfiled... adieus time!
  			Object obj = parser.parse(new FileReader("resources/nlg/sentences.json"));
  	 
  			//Access to a sentence
  			JSONObject jsonObject = (JSONObject) obj;
  			JSONObject jsonState = (JSONObject) jsonObject.get(className);
  			JSONArray jsonSentences = (JSONArray) jsonState.get(stateName);
  			Integer size = jsonSentences.size();
  			
  			//Generates random number based on array size (number of sentences)
  			Random rn = new Random();
			Integer randomNum = rn.nextInt(size - 1);
			
			return (String) jsonSentences.get(randomNum);
  		
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
  	 *  
     * @param dialogState
     * @return
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
  			//Generates random number based on array size (number of sentences)
  			Random rn = new Random();
			Integer randomNum = rn.nextInt(size - 1);
			
			String temp =  (String) jsonSentences.get(randomNum);
			
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
  		String answer = null;
  		String evaluationObj = "<o>";
  		String evaluationCompl = "{c}";
  		//String evaluationTime = "{time}";
  		
  		//String[] tokens = sentences.split(" "); 
  		if(keyword != null) {
  		    
	  		String[] sentences = null;
	  		String[] keywordPhrases = keyword.split(","); 
	  		String obj = null;
	  		String compl = null;
	  		if(text.contains(".")) {
	  			sentences = text.split(".");
	  		} else sentences[0] = text;  // nothing to split
	  		
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
  	
  	//Testing
  	/*
  	public static void main (String args[]) {
	  	OutputCreator creator = new OutputCreator();
	  	StartState startState = new StartState();
	  	System.out.println(creator.createOutput(startState));
  	}*/
}