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
public void updateState(List<String> keywords, List<String> terms,
		List<String> approval) throws WrongStateClassException {
	// TODO Auto-generated method stub
	
}

}