package nlg;


/**
 * 
 * @author Xizhe Lian, Luiz Henrique Soares Silva
 * @version 0.1
 */
import java.util.ArrayList;
import java.util.List;

import dm.DialogState;


public class OutputCreator {

  private List<Generator> generators;

  private List<Phrase> outputPhrases;

  private SocialComponent socialComponent;
  
  public String createOutput(DialogState dialogState) {
	  generators = new ArrayList<Generator>();
	  outputPhrases = new ArrayList<Phrase>();
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

}