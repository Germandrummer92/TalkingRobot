package nlg;


/**
 * 
 * @author Xizhe Lian, Luiz Henrique Soares Silva
 * @version 0.1
 */
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import dm.CanteenInformationState;
import dm.CanteenRecommendationState;
import dm.DialogState;
import dm.KitchenAssistanceState;
import dm.RecipeAssistanceState;
import dm.RecipeLearningState;
import dm.StartState;
import dm.Start;


public class OutputCreator {
	private List<Generator> generators;

	private List<Phrase> outputPhrases;

	private SocialComponent socialComponent;
  
	public String createOutput(DialogState dialogState) {
		generators = new ArrayList<Generator>();
		outputPhrases = new ArrayList<Phrase>();
	  
		//First idea of how it should be. High probability of changing.
		/*================*/
		
		File sentencesFile = new File(""); //Import files with sentences
		String output = dialogState.getOutputKeyword();
	  
		if(dialogState.getClass().equals(StartState.class)) {
			StartState startState = (StartState) dialogState;
			switch(startState.getCurrentState()) {
			case ENTRY:
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
		} else if (dialogState.getClass().equals(RecipeLearningState.class)) {
			switch(((RecipeLearningState) dialogState).getCurrentState()) {
			case AKS_INGREDIENT_RIGHT:
				break;
			case ASK_COUNTRY_OF_ORIGIN:
				break;
			case ASK_FIRST_INGREDIENT:
				break;
			case ASK_FIRST_STEP:
				break;
			case ASK_FIRST_TOOL:
				break;
			case ASK_LAST_STEP:
				break;
			case ASK_NEXT_INGREDIENT:
				break;
			case ASK_NEXT_STEP:
				break;
			case ASK_NEXT_TOOL:
				break;
			case ASK_RECIPE_NAME:
				break;
			case ASK_STEP_RIGHT:
				break;
			case ASK_TOOL_RIGHT:
				break;
			case ENTRY:
				break;
			case EXIT:
				break;
			default:
				break;
			
			}
		} else if (dialogState.getClass().equals(KitchenAssistanceState.class)) {
			switch(((KitchenAssistanceState) dialogState).getCurrentState()) {
			case ENTRY:
				break;
			case EXIT:
				break;
			case TELL_INGREDIENT_FOUND:
				break;
			case TELL_INGREDIENT_NOT_FOUND:
				break;
			case TELL_TOOL_FOUND:
				break;
			case TELL_TOOL_NOT_FOUND:
				break;
			default:
				break;
			}
		} else if (dialogState.getClass().equals(CanteenInformationState.class)) {
			switch(((CanteenInformationState) dialogState).getCurrentState()) {
			case ADEN_CAFE_DISH:
				break;
			case ADEN_CAFE_PRICE:
				break;
			case ADEN_CURRYQ_DISH:
				break;
			case ADEN_CURRYQ_PRICE:
				break;
			case ADEN_LINE_1_DISH:
				break;
			case ADEN_LINE_1_PRICE:
				break;
			case ADEN_LINE_2_DISH:
				break;
			case ADEN_LINE_2_PRICE:
				break;
			case ADEN_LINE_3_DISH:
				break;
			case ADEN_LINE_3_PRICE:
				break;
			case ADEN_LINE_45_DISH:
				break;
			case ADEN_LINE_45_PRICE:
				break;
			case ADEN_LINE_6_DISH:
				break;
			case ADEN_LINE_6_PRICE:
				break;
			case ADEN_SCHNITBAR_DISH:
				break;
			case ADEN_SCHNITBAR_PRICE:
				break;
			case ENTRY:
				break;
			case EXIT:
				break;
			case MOLTKE_ACTTHEK_DISH:
				break;
			case MOLTKE_ACTTHEK_PRICE:
				break;
			case MOLTKE_BUFFET_DISH:
				break;
			case MOLTKE_BUFFET_PRICE:
				break;
			case MOLTKE_CHOICE_1_DISH:
				break;
			case MOLTKE_CHOICE_1_PRICE:
				break;
			case MOLTKE_CHOICE_2_DISH:
				break;
			case MOLTKE_CHOICE_2_PRICE:
				break;
			case MOLTKE_GG_DISH:
				break;
			case MOLTKE_GG_PRICE:
				break;
			case MOLTKE_SCHNITBAR_DISH:
				break;
			case MOLTKE_SCHNITBAR_PRICE:
				break;
			case TELL_LINE_NOT_EXIST:
				break;
			case TELL_MEAL_NOT_EXIST:
				break;
			default:
				break;
			}
		} else if (dialogState.getClass().equals(CanteenRecommendationState.class)) {
			switch(((CanteenRecommendationState) dialogState).getCurrentState()) {
			case ADEN_CAFE_DISH:
				break;
			case ADEN_CURRYQ_DISH:
				break;
			case ADEN_LINE_1_DISH:
				break;
			case ADEN_LINE_2_DISH:
				break;
			case ADEN_LINE_3_DISH:
				break;
			case ADEN_LINE_45_DISH:
				break;
			case ADEN_LINE_6_DISH:
				break;
			case ADEN_SCHNITBAR_DISH:
				break;
			case ENTRY:
				break;
			case EXIT:
				break;
			case MOLTKE_BUFFET_DISH:
				break;
			case MOLTKE_CAFE_DISH:
				break;
			case MOLTKE_CHOICE_1_DISH:
				break;
			case MOLTKE_CHOICE_2_DISH:
				break;
			case MOLTKE_GG_DISH:
				break;
			case MOLTKE_SCHNITBAR_DISH:
				break;
			case TELL_MEAL_NOT_EXIST:
				break;
			default:
				break;
			
			}
		}
	  
		/*================*/
		//Phrase phrase = getOutputKeyword(dialogState);
		//TODO Check for SocialComponent (if yes, addSocialComponent()) (How can we check?)
	  
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
		// Phrase phrase = this.toPhrase(dialogState);
		//  String socialRemark = socialComponent.createSocialRemark(phrase);
	  
		return null;
	}
  
  	public static void main (String args[]) {
	  	OutputCreator creator = new OutputCreator();
	  	StartState startState = new StartState();
	  	creator.createOutput(startState);
  	}

}