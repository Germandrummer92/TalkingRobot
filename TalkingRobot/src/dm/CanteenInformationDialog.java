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
<<<<<<< HEAD
public void updateState(List<Keyword> keywords, List<String> terms) {
=======
public void updateState(List<String> keywords, List<String> terms,
		List<String> approval) throws WrongStateClassException {
>>>>>>> 72949fa36c389ac1d4c9ebf712cdfc771014857a
	// TODO Auto-generated method stub
	
}

}