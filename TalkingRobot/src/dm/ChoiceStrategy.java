package dm;

import java.util.List;

/**
 * 
 * @author Meng Meng Yan
 * @version 1.0
 * The choice strategy: asks the user to choose between two words.
 */
public class ChoiceStrategy extends ErrorStrategy {

	private String firstChoice;
	private String secondChoice;
	private List<String> listOfChoices;
	private Dictionary dictionary;
	
	/**
	 * Constructor of RepeatStrategy.
	 */
	public ChoiceStrategy() {
		dictionary = new Dictionary();
	}
	
	private String createChoice(String s1, String s2) {
		return s1 + ";" + s2;
	}
	
	/**
	 * getter-method for the first choice.
	 * @return the first choice
	 */
	public String getFirstChoice() {
		return this.firstChoice;
	}
	
	/**
	 * getter-method for the secnd choice.
	 * @return the second choice
	 */
	public String getSecondChoice() {
		return this.secondChoice;
	}

	/**
	 * @see ErrorStrategy#handleError(List)
	 */
	@Override
	public ErrorHandlingState handleError(List<String> errorWords) {
		if(errorWords != null) {
			searchGoodChoice(errorWords);
		} else {
			searchGoodChoice(listOfChoices);
		}
		
		if(listOfChoices == null) { return null; }
		else {
			return new ErrorHandlingState(true, ErrorHandling.CHOICE,
					this.createChoice(firstChoice, secondChoice));
		}
			
	}
	
	private void searchGoodChoice(List<String> choices) {
		if(!choices.isEmpty()) {
			List<Keyword> keywordList = dictionary.getKeywordList();
			int priority = -1;
			int elementId = 0;
			for(int i = 0; i < choices.size(); i++) {
				String[] words = choices.get(i).split(";");
				for(int j = 0; j < keywordList.size(); j++) {
					if(words[1].equals(keywordList.get(j).getWord())
							&& keywordList.get(j).getKeywordData().getPriority() > priority
							&& Integer.parseInt(words[2]) < 5) {
						priority = keywordList.get(j).getKeywordData().getPriority();
						elementId = i;
					}
				}
			}
				
			String[] choice = choices.get(elementId).split(";");
			firstChoice = choice[0];
			secondChoice = choice[1];
			choices.remove(elementId);
			listOfChoices = choices;
		} else {
			listOfChoices = null;
		}
	}
}
