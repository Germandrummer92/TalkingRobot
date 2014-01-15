package nlg;
public class SimpleNLG {

  //private Lexikon lexikon;

  //private NLGFactory nlgFactory; 

  //private Realiser realiser; 
  
  //alle 3 nicht im Klassendiagramm 

  private SimpleNLG uniqueSimpleNLG;

      
  private SimpleNLG SimpleNLG() {
  return null;
  }

  public SimpleNLG getSimpleNLG() {
	  if (uniqueSimpleNLG == null) {
		  uniqueSimpleNLG = new SimpleNLG();
	  }
	  return uniqueSimpleNLG;
  }

}