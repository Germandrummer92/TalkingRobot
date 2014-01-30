package dm;

import java.util.List;

public class ExplicitVerificationStrategy extends ErrorStrategy {

	private List<String> possibleWords;
	private String questionableWord;
	private Dictionary dictionary;
	
	public ExplicitVerificationStrategy() {
		this.dictionary = new Dictionary();
	}
	
	public String getQuestionableWord() {
		return this.questionableWord;
	}

	@Override
	public ErrorHandlingState handleError(List<String> errorWords) {
		this.riseCounter();
		if(errorWords != null) {
			searchGoodWord(errorWords);
		} else {
			searchGoodWord(possibleWords);
		}
		return null;
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
							&& (float)Integer.parseInt(possibleWords[2]) / possibleWords[2].length() < 0.5) {
						priority = keywordList.get(j).getKeywordData().getPriority();
						elementId = i;
					}  else if ((float)Integer.parseInt(possibleWords[2]) / possibleWords[2].length() < distance) {
						distance = (float)Integer.parseInt(possibleWords[2]) / possibleWords[2].length();
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