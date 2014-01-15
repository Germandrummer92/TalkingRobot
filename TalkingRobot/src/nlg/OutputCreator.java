package nlg;

import java.util.List;

import dm.DialogState;


public class OutputCreator {

  private List<Generator> generators;

  private List<Phrase> outputPhrases;

  private SocialComponent socialComponent;
  
  public String createOutput(DialogState dialogState) {
	  
	  Phrase phrase = getOutputKeyword(dialogState);
	  //TODO Check for SocialComponent (if yes, addSocialComponent())
	  
	  return null;
  }

  private Phrase getOutputKeyword(DialogState dialogState) {
	  String keyword = dialogState.getOutputKeyword();
	  //TODO String to Phrase
	  
	  return null;
  }

  private String addSocialComponent(DialogState dialogState) {
	  String keyword = dialogState.getOutputKeyword();
	  ///TODO Sth
	  Phrase phrase = this.getOutputKeyword(dialogState);
	  String socialRemark = socialComponent.createSocialRemark(phrase);
	  
  return socialRemark;
  }

}