package dm;

import java.util.List;


public class KitchenAssistanceDialog extends KitchenDialog {

	private AssistanceState stateOfAssistance;
	
  /**
	 * @param session
	 */
	public KitchenAssistanceDialog(Session session, DialogState dialogState) {
		super(session, dialogState);
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