package nlg;

public class Phrase {

  private PhraseClass phraseClass;

  private String phraseString;

  private Phrase connection;
  
  public Phrase Phrase(String phrase, PhraseClass phraseClass) {
  return null;
  }

  	public PhraseClass getPhraseClass() {
  		return phraseClass;
  	}

  	public void setPhraseClass(PhraseClass phraseClass) {
  		this.phraseClass = phraseClass;
  	}

  	public String getPhraseString() {
  		return phraseString;
  	}

  	public void setPhraseString(String phraseString) {
  		this.phraseString = phraseString;
  	}

  	public Phrase getConnection() {
  		return connection;
  	}

  	public void setConnection(Phrase connection) {
  		this.connection = connection;
  	}

}