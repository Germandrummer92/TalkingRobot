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
import java.util.ResourceBundle;

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

	private final String   BUNDLE_NAME = "nlgOutput";

	private ResourceBundle RESOURCE_BUNDLE;
	
	public OutputCreator() {
		RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	}
  
	public String createOutput(DialogState dialogState) {
		generators = new ArrayList<Generator>();
		outputPhrases = new ArrayList<Phrase>();
	  
		// Maybe implementing 'DialogStates' and making all the enumerations extend it would be better.
		// Then it's not necessary to implement the following if-else conditions.
		
<<<<<<< HEAD
		// maybe make this file in properties like in SWT? that we don't have to load it every time
		// Xizhe
		//File sentencesFile = new File(""); //Import files with sentences
		//String output = dialogState.getOutputKeyword();
	  
		String key = null;
		int index = -1;
		if(dialogState.getClass().equals(StartState.class)) {
			StartState startState = (StartState) dialogState;
			index = startState.getCurrentState().getIndex();
			
			
			
			// should we make the switch in a private method?  Xizhe
			switch(startState.getCurrentState()) {
			case ENTRY:
				key = "startState." + "ENTRY.";
				break;
			case EXIT:
				break;
			case USER_DOESNT_WANT_TO_BE_SAVED:
				break;
			case USER_FOUND:
				break;
			case USER_NOT_FOUND:
				break;
			case USER_SAVED:
				break;
			case USER_WANTS_TO_BE_SAVED:
				break;
			case WAITING_FOR_EMPLOYEE_STATUS:
				break;
			case WAITING_FOR_USERNAME:
				break;
			default:
				break;	
			}
		} else if(dialogState.getClass().equals(RecipeAssistanceState.class)) {
			switch(((RecipeAssistanceState) dialogState).getCurrentState()){
			case ENTRY:
				break;
			case EXIT:
				break;
			case RECIPE_NOT_FOUND:
				break;
			case TELL_COUNTRY_OF_ORIGIN:
				break;
			case TELL_CREATOR:
				break;
			case TELL_INGFREDIENT_NOT_FOUND:
				break;
			case TELL_INGREDIENTS:
				break;
			case TELL_INGREDIENT_FOUND:
				break;
			case TELL_NUM_OF_STEPS:
				break;
			case TELL_STEPS:
				break;
			case TELL_TOOLS:
				break;
			case TELL_TOOL_FOUND:
				break;
			case TELL_TOOL_NOT_FOUND:
				break;
			case TELL_WHOLE_RECIPE:
				break;
			default:
				break;
			
			}
=======
//		String output = dialogState.getOutputKeyword();
//		findInFile(dialogState.getClass().getName(), output);
		if (dialogState.getClass().equals(StartState.class)) {
			StartState startState = (StartState) dialogState;
		} else if (dialogState.getClass().equals(RecipeAssistanceState.class)) {
			RecipeAssistanceState recipeAssistanceState = (RecipeAssistanceState) dialogState;
>>>>>>> 40a813ebb2a8773a11100311ffc642720849910f
		} else if (dialogState.getClass().equals(RecipeLearningState.class)) {
			RecipeLearningState recipeLearningState = (RecipeLearningState) dialogState;
		} else if (dialogState.getClass().equals(KitchenAssistanceState.class)) {
			KitchenAssistanceState kitchenAssistanceState = (KitchenAssistanceState) dialogState;
		} else if (dialogState.getClass().equals(CanteenInformationState.class)) {
			CanteenInformationState canteenInformationState = (CanteenInformationState) dialogState;
		} else if (dialogState.getClass().equals(CanteenRecommendationState.class)) {
			CanteenRecommendationState canteenRecommendationState = (CanteenRecommendationState) dialogState;
		}
<<<<<<< HEAD
	  
		key = key + "."+index;
		
		
		/*================*/
		//Phrase phrase = getOutputKeyword(dialogState);
		//TODO Check for SocialComponent (if yes, addSocialComponent()) (How can we check?)
=======
		
		findInFile(dialogState);
>>>>>>> 40a813ebb2a8773a11100311ffc642720849910f
	  
		return RESOURCE_BUNDLE.getString(key);
	}

	/**
	 * @param dialogState
	 */
	private void toPhrase(DialogState dialogState) {
		String keywords = dialogState.getOutputKeyword();
	  
		//TODO need to be changed when the parameter changed
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