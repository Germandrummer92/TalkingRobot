package nlg;

import java.util.ArrayList;
import java.util.List;
import dm.Dictionary;


public class SocialComponent {

  private SocialRemarkGenerator generator;

  private Dictionary dictionary;

  public String createSocialRemark(Phrase outputKeyword) {
	  
	  ArrayList<Phrase> phrases = new ArrayList<Phrase>();
	  phrases.add(outputKeyword);
	  //TODO Sth
	  String sentence = generator.generateSentence(phrases);
	  return sentence;
  }

  public List<Phrase> createSocialSupplement(Phrase outputKeyword) {
  return null;
  }

}