package nlg;


import simplenlg.framework.*;
import simplenlg.lexicon.*;
import simplenlg.realiser.english.*;
import simplenlg.phrasespec.*;
import simplenlg.features.*;

/**
 * 
 * @author Xizhe Lian, Luiz Henrique. Soares Silva
 * @version 1.1
 *
 */
public class SimpleNLG {

  //private Lexicon lexicon;

  //private NLGFactory nlgFactory; 

  //private Realiser realiser; 
  
  //alle 3 nicht im Klassendiagramm 

  private SimpleNLG uniqueSimpleNLG;

 /**
  * ich dachte hier sollte ne konstruktor sein oder? Xixi   
  * sollen wir mit attributen machen oder eher so?
  */
  private SimpleNLG() {
	  Lexicon lexicon = Lexicon.getDefaultLexicon();
      NLGFactory nlgFactory = new NLGFactory(lexicon);
      Realiser realiser = new Realiser(lexicon);
  }

  /**
   * singleton method, ensure that just one simpleNlg available to be used
   * @return the uniqueSimpleNLG
   */
  public SimpleNLG getSimpleNLG() {
	  if (uniqueSimpleNLG == null) {
		  uniqueSimpleNLG = new SimpleNLG();
	  }
	  return uniqueSimpleNLG;
  }

}