package dm;

import java.util.List;

/**
 * 
 * @author Meng Meng Yan
 * @version 1.0
 * The indirect verification strategy: make a statement containing the possible keyword
 */
public class IndirectVerificationStrategy extends ErrorStrategy {

	private List<String> possibleWords;
	private String questionableWord;
	private Dictionary dictionary;
	
	/**
	 * Constructor of IndirectVerificationStrategy.
	 */
	public IndirectVerificationStrategy() {
		this.dictionary = new Dictionary();
	}	
	
	/**
	 * getter-method of the questionable word.
	 * @return the questionable word.
	 */
	public String getQuestionableWords() {
		return this.questionableWord;
	}

	/**
	 * @see ErrorStrategy#handleError(List)
	 */
	@Override
	public ErrorHandlingState handleError(List<String> errorWords) {
		this.riseCounter();
		if(errorWords != null) {
			searchGoodWord(errorWords);
		} else {
			searchGoodWord(possibleWords);
		}
	
		if(this.possibleWords == null) { return null; }
		else {
			return new ErrorHandlingState(
					false, ErrorHandling.Indirect_Verification, "{" + questionableWord + "}");	
		}
	}

	private void searchGoodWord(List<String> words) {
		if(words != null && !words.isEmpty()) {
			List<Keyword> keywordList = dictionary.getKeywordList();
			int priority = -1;
			float distance = 1;
			int elementId = -1;
			int elementIdRegardingDistance = 0;
			
			for(int i = 0; i < words.size(); i++) {
				String[] possibleWords = words.get(i).split(";");
				for(int j = 0; j < keywordList.size(); j++) {
					if(possibleWords[1].equals(keywordList.get(j).getWord())
							&& keywordList.get(j).getKeywordData().getPriority() > priority
							&& (float)Integer.parseInt(possibleWords[2]) / possibleWords[1].length() < 0.2) {
						priority = keywordList.get(j).getKeywordData().getPriority();
						elementId = i;
					}  else if ((float)Integer.parseInt(possibleWords[2]) / possibleWords[1].length() < distance) {
						distance = (float)Integer.parseInt(possibleWords[2]) / possibleWords[1].length();
						elementIdRegardingDistance = i;
					}
				}
			}

			if(elementId >= 0) {
				String[] possibleWord = words.get(elementId).split(";");
				this.questionableWord = possibleWord[1];
				words.remove(elementId);
				this.possibleWords = words;
			} else {
				String[] possibleWord = words.get(elementIdRegardingDistance).split(";");
				this.questionableWord = possibleWord[1];
				words.remove(elementIdRegardingDistance);
				this.possibleWords = words;
			}
		} else {
			this.possibleWords = null;
		}
	}
}