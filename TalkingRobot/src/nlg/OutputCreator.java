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
		String output = "empty";
		
		//Ugly implementation! Note: It works so far!
		//Certifies in which dialogState the system is, in order to generate the correct Sub-state.
		//Too bad this is only need because access to the state-enums is needed!!!! Otherwise no if-else
		//would be needed....
		if (dialogState.getClass().equals(StartState.class)) {
			
			StartState startState = (StartState) dialogState;
			output = findInFile(startState.getClass().getName(), startState.getCurrentState().toString());
			
		} else if (dialogState.getClass().equals(RecipeAssistanceState.class)) {
			
			RecipeAssistanceState recipeAssistanceState = (RecipeAssistanceState) dialogState;
			output = findInFile(recipeAssistanceState.getClass().getName(), recipeAssistanceState.getCurrentState().toString());
			
		} else if (dialogState.getClass().equals(RecipeLearningState.class)) {
			
			RecipeLearningState recipeLearningState = (RecipeLearningState) dialogState;
			output = findInFile(recipeLearningState.getClass().getName(), recipeLearningState.getCurrentState().toString());
			
		} else if (dialogState.getClass().equals(KitchenAssistanceState.class)) {
			
			KitchenAssistanceState kitchenAssistanceState = (KitchenAssistanceState) dialogState;
			output = findInFile(kitchenAssistanceState.getClass().getName(), kitchenAssistanceState.getCurrentState().toString());
			
		} else if (dialogState.getClass().equals(CanteenInformationState.class)) {
			
			CanteenInformationState canteenInformationState = (CanteenInformationState) dialogState;
			output = findInFile(canteenInformationState.getClass().getName(), canteenInformationState.getCurrentState().toString());
			
		} else if (dialogState.getClass().equals(CanteenRecommendationState.class)) {
			
			CanteenRecommendationState canteenRecommendationState = (CanteenRecommendationState) dialogState;
			output = findInFile(canteenRecommendationState.getClass().getName(), canteenRecommendationState.getCurrentState().toString());
		}
		
		//TODO Still have to add the keywords into the output!!!
		
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
  		//String sentence = "empty";
  		
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
			
			//sentence = (String) jsonSentences.get(randomNum);
			
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
     * When should we add social component?
     * we need so database for social components // Xizhe  
     * @param dialogState
     * @return
     */
  	private String addSocialComponent(DialogState dialogState) {
  		String keyword = dialogState.getOutputKeyword();
  		///TODO this sweet method
  		//Phrase phrase = this.toPhrase(dialogState);
  		//String socialRemark = socialComponent.createSocialRemark(phrase);
  	  
  		return null;
  	}
  	
  	//Testing
  	public static void main (String args[]) {
	  	OutputCreator creator = new OutputCreator();
	  	StartState startState = new StartState();
	  	System.out.println(creator.createOutput(startState));
  	}
}