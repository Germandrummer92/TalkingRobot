package nlg;


/**
 * 
 * @author Xizhe Lian, Luiz Henrique Soares Silva
 * @version 0.1
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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


public class OutputCreator {
	private List<Generator> generators;

	private List<Phrase> outputPhrases;

	private SocialComponent socialComponent;
  
	public String createOutput(DialogState dialogState) {
		generators = new ArrayList<Generator>();
		outputPhrases = new ArrayList<Phrase>();
	  
		// Maybe implementing 'DialogStates' and making all the enumerations extend it would be better.
		// Then it's not necessary to implement the following if-else conditions.
		
//		String output = dialogState.getOutputKeyword();
//		findInFile(dialogState.getClass().getName(), output);
		if (dialogState.getClass().equals(StartState.class)) {
			StartState startState = (StartState) dialogState;
		} else if (dialogState.getClass().equals(RecipeAssistanceState.class)) {
			RecipeAssistanceState recipeAssistanceState = (RecipeAssistanceState) dialogState;
		} else if (dialogState.getClass().equals(RecipeLearningState.class)) {
			RecipeLearningState recipeLearningState = (RecipeLearningState) dialogState;
		} else if (dialogState.getClass().equals(KitchenAssistanceState.class)) {
			KitchenAssistanceState kitchenAssistanceState = (KitchenAssistanceState) dialogState;
		} else if (dialogState.getClass().equals(CanteenInformationState.class)) {
			CanteenInformationState canteenInformationState = (CanteenInformationState) dialogState;
		} else if (dialogState.getClass().equals(CanteenRecommendationState.class)) {
			CanteenRecommendationState canteenRecommendationState = (CanteenRecommendationState) dialogState;
		}
		
		findInFile(dialogState);
	  
		return null;
	}

	/**
	 * I dont know how to decide which type belongs a token to
	 * for example: 'give me spagetti', then the string[] tokens will be {give, me, spagetti}
	 * I think if we can set the phrase type in dialogstate.getOutputKeywords will be nice
	 * Xizhe
	 * @param dialogState
	 */
	private void toPhrase(DialogState dialogState) {
		String keywords = dialogState.getOutputKeyword();
	  
		String delims = " "; // assume that exactly one space between each word
		String[] tokens = keywords.split(delims);
	  
		for(int i = 0; i < tokens.length; i++) {
			Phrase phrase = new Phrase();
			phrase.setPhraseString(tokens[i]);
			outputPhrases.add(phrase);
		}	  
	
	}

  /**
   * When should we add social component?
   * we need so database for social components // Xizhe  
   * @param dialogState
   * @return
   */
	private String addSocialComponent(DialogState dialogState) {
		String keyword = dialogState.getOutputKeyword();
		///TODO Sth
		//Phrase phrase = this.toPhrase(dialogState);
		//String socialRemark = socialComponent.createSocialRemark(phrase);
	  
		return null;
	}
  
  	public static void main (String args[]) {
	  	OutputCreator creator = new OutputCreator();
	  	StartState startState = new StartState();
	  	creator.createOutput(startState);
  	}

  	private String findInFile(DialogState dialogState) {
  		String sentence = "empty";
  		
  		JSONParser parser = new JSONParser();
  		 
  		try {
  	 
  			Object obj = parser.parse(new FileReader("resources/nlg/sentences.json"));
  	 
  			JSONObject jsonObject = (JSONObject) obj;
  			JSONObject jsonState = (JSONObject) jsonObject.get(dialogState.getClass().getName());
  			
  			System.out.println(((JSONObject) jsonState.get("ENTRY")).get("answer2"));
  			
  			/*
  			String name = (String) jsonObject.get("name");
  			System.out.println(name);
  	 
  			long age = (Long) jsonObject.get("age");
  			System.out.println(age);
  	 
  			// loop array
  			JSONArray msg = (JSONArray) jsonObject.get("messages");
  			Iterator<String> iterator = msg.iterator();
  			while (iterator.hasNext()) {
  				System.out.println(iterator.next());
  			} */
  	 
  		} catch (FileNotFoundException e) {
  			e.printStackTrace();
  		} catch (IOException e) {
  			e.printStackTrace();
  		} catch (ParseException e) {
  			e.printStackTrace();
  		}
  		
  		return sentence;
  	}
}