package dm;

import java.util.List;

public class CanteenInformationDialog extends CanteenDialog {

/**
	 * @param session
	 * @dialogState
	 * @param currentCanteen
	 */
	public CanteenInformationDialog(Session session, DialogState dialogState, Canteen currentCanteen) {
		super(session, dialogState, currentCanteen);
		// TODO Auto-generated constructor stub
	}

@Override
public void updateState(List<String> keywords, List<String> terms,
		List<String> approval) throws WrongStateClassException {
	// TODO Auto-generated method stub
	
}

}