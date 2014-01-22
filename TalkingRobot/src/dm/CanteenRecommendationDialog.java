package dm;

import java.util.List;

public class CanteenRecommendationDialog extends CanteenDialog {
	
/**
	 * @param session
	 * @param currentCanteen
	 */
	public CanteenRecommendationDialog(Session session, DialogState dialogState, Canteen currentCanteen) {
		super(session, dialogState, currentCanteen);
		// TODO Auto-generated constructor stub
	}

@Override
public void updateState(List<Keyword> keywords, List<String> terms,
		List<String> approval) throws WrongStateClassException {

	// TODO Auto-generated method stub
	
}

}