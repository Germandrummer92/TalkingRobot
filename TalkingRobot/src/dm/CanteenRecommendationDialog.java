package dm;

import java.util.List;

/**
 * This class represents a dialog about canteen recommendation
 * @author Xizhe Lian
 * @version 0.5
 */
public class CanteenRecommendationDialog extends CanteenDialog {
	
/**
	 * @param session
	 * @param currentCanteen
	 */
	public CanteenRecommendationDialog(Session session, DialogState dialogState, Canteen currentCanteen) {
		super(session, dialogState, currentCanteen);
	
	}

@Override
public void updateState(List<Keyword> keywords, List<String> terms,
		List<String> approval) throws WrongStateClassException {

	if (getCurrentDialogState().getClass() != CanteenRecommendationState.class) {
		throw new WrongStateClassException();
	}
	
	switch ((CanteenRecom)getCurrentDialogState().getCurrentState()) {
		case CR_ENTRY:
			break;
		case CR_ADEN_LINE_1_DISH:
			break;
		case CR_ADEN_LINE_2_DISH:
			break;
		case CR_ADEN_LINE_3_DISH:
			break;
		case CR_ADEN_LINE_45_DISH:
			break;
		case CR_ADEN_LINE_6_DISH:
			break;
		case CR_ADEN_SCHNITBAR_DISH:
			break;
		case CR_ADEN_CURRYQ_DISH:
			break;
		case CR_ADEN_CAFE_DISH:
			break;
		case CR_MOLTKE_CHOICE_1_DISH:
			break;
		case CR_MOLTKE_CHOICE_2_DISH:
			break;
		case CR_MOLTKE_SCHNITBAR_DISH:
			break;
		case CR_MOLTKE_CAFE_DISH:
			break;
		case CR_MOLTKE_GG_DISH:
			break;
		case CR_MOLTKE_BUFFET_DISH:
			break;
		case CR_TELL_MEAL_NOT_EXIST:
			break;
		case CR_EXIT:
			break;
	default:
		break;
	
	}
	
}

}