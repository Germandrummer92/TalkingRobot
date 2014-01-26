package dm;

import java.util.List;

/**
 * This class represents a dialog about canteen information
 * @author Xizhe Lian, Daniel Draper
 * @version 0.6
 */
public class CanteenInformationDialog extends CanteenDialog {

/**
	 * @param session
	 * @dialogState
	 * @param currentCanteen
	 */
	public CanteenInformationDialog(Session session, DialogState dialogState, Canteen currentCanteen) {
		super(session, dialogState, currentCanteen);
	}


@Override

public void updateState(List<Keyword> keywords, List<String> terms,
		List<String> approval) throws WrongStateClassException {
	if (getCurrentDialogState().getClass() != CanteenInformationState.class) {
		throw new WrongStateClassException(getCurrentDialogState().getClass().getName());
	}
	switch ((CanteenInfo)getCurrentDialogState().getCurrentState()) {
	case CI_ENTRY:
		break;

	case CI_ADEN_LINE_1_PRICE:
		break;
	case CI_ADEN_LINE_2_PRICE:
		break;
	case CI_ADEN_LINE_3_PRICE:
		break;
	case CI_ADEN_LINE_45_PRICE:
		break;
	case CI_ADEN_LINE_1_DISH:
		break;
	case CI_ADEN_LINE_2_DISH:
		break;
	case CI_ADEN_LINE_3_DISH:
		break;
	case CI_ADEN_LINE_45_DISH:
		break;
	case CI_ADEN_LINE_6_DISH:
		break;
	case CI_ADEN_LINE_6_PRICE:
		break;
	case CI_ADEN_SCHNITBAR_PRICE:
		break;
	case CI_ADEN_SCHNITBAR_DISH:
		break;
	case CI_ADEN_CAFE_PRICE:
		break;
	case CI_ADEN_CURRYQ_PRICE:
		break;
	case CI_ADEN_CAFE_DISH:
		break;
	case CI_MOLTKE_CHOICE_1_PRICE:
		break;
	case CI_MOLTKE_CHOICE_1_DISH:
		break;
	case CI_MOLTKE_CHOICE_2_PRICE:
		break;
	case CI_MOLTKE_CHOICE_2_DISH:
		break;
	case CI_MOLTKE_ACTTHEK_PRICE:
		break;
	case CI_MOLTKE_ACTTHEK_DISH:
		break;
	case CI_MOLTKE_SCHNITBAR_PRICE:
		break;
	case CI_MOLTKE_SCHNITBAR_DISH:
		break;
	case CI_MOLTKE_GG_PRICE:
		break;
	case CI_MOLTKE_GG_DISH:
		break;
	case CI_MOLTKE_BUFFET_PRICE:
		break;
	case CI_MOLTKE_BUFFET_DISH:
		break;
	case CI_ADEN_CURRYQ_DISH:
		break;
	case CI_TELL_LINE_NOT_EXIST:
		break;
	case CI_TELL_MEAL_NOT_EXIST:
		break;
	case  CI_EXIT:
		break;
	default:
		
		break;
	
	}
	
}

}