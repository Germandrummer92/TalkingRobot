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
		this.dictionary = new Dictionary();
	}
	
	private String createChoice(String s1, String s2) {
		return "<" + s1 + ">,<" + s2 + ">";
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
		this.riseCounter();
		if(errorWords != null) {
			this.searchGoodChoice(errorWords);
		} else {
			this.searchGoodChoice(listOfChoices);
		}
		
		if(listOfChoices == null) { return null; }
		else {
			return new ErrorHandlingState(true, ErrorHandling.CHOICE,
					this.createChoice(firstChoice, secondChoice));
		}
			
	}
	
	private void searchGoodChoice(List<String> choices) {
		if(choices != null && !choices.isEmpty()) {
			List<Keyword> keywordList = dictionary.getKeywordList();
			int priority = -1;
			float distance = 1;
			int elementId = -1;
			int elementIdRegardingDistance = 0;
			
			for(int i = 0; i < choices.size(); i++) {
				String[] words = choices.get(i).split(";");
				for(int j = 0; j < keywordList.size(); j++) {
					if(words[1].equals(keywordList.get(j).getWord())
							&& keywordList.get(j).getKeywordData().getPriority() > priority
							&& (float)Integer.parseInt(words[2]) / words[1].length() < 0.3) {
						priority = keywordList.get(j).getKeywordData().getPriority();
						elementId = i;
					}  else if ((float)Integer.parseInt(words[2]) / words[1].length() < distance) {
						distance = (float)Integer.parseInt(words[2]) / words[1].length();
						elementIdRegardingDistance = i;
					}
				}
			}

			if(elementId >= 0) {

				String[] choice = choices.get(elementId).split(";");
				firstChoice = choice[0];
				secondChoice = choice[1];
				choices.remove(elementId);
				listOfChoices = choices;
			} else {

				String[] choice = choices.get(elementIdRegardingDistance).split(";");
				firstChoice = choice[0];
				secondChoice = choice[1];
				choices.remove(elementIdRegardingDistance);
				listOfChoices = choices;
			}
			
		} else {
			listOfChoices = null;
		}
	}
}

