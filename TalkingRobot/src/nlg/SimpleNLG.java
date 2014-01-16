package nlg;


import simplenlg.framework.*;
import simplenlg.lexicon.*;
import simplenlg.realiser.english.*;


/**
 * Class SimpleNLG is a Singleton. Gives access to further features of the library simplenlg.
 * 
 * @author Xizhe Lian, Luiz Henrique Soares Silva
 * @version 1.1
 *
 */
public class SimpleNLG {

  private Lexicon lexicon;

  private NLGFactory nlgFactory; 

  private Realiser realiser; 

  private static SimpleNLG uniqueSimpleNLG;

  /**
  * Private constructor assures that no other class will create a new instance of SimpleNLG 
  */
  private SimpleNLG() {
	  this.lexicon = Lexicon.getDefaultLexicon();
	  this.nlgFactory = new NLGFactory(lexicon);
      this.realiser = new Realiser(lexicon);
  }

  /**
   * Singleton method ensures that only one SimpleNLG (Singleton) exists.
   * @return the uniqueSimpleNLG
   */
  public static SimpleNLG getSimpleNLG() {
	  if (uniqueSimpleNLG == null) {
		  uniqueSimpleNLG = new SimpleNLG();
	  }
	  return uniqueSimpleNLG;
  }
  
  public Realiser getRealiser() {
	  return this.realiser;
  }
  
  public NLGFactory getNlgFactory() {
	  return this.nlgFactory;
  }

}