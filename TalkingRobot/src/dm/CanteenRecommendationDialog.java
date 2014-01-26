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
		throw new WrongStateClassException(getCurrentDialogState().getClass().getName());
	}
	
	switch ((CanteenRecom)getCurrentDialogState().getCurrentState()) {
		case CR_ENTRY:
			updateStateEntry(keywords, terms);
			break;
		case CR_ADEN_LINE_1_DISH:
			updateAdenLine1Dish(keywords, terms);
			break;
		case CR_ADEN_LINE_2_DISH:
			updateAdenLine2Dish(keywords, terms);
			break;
		case CR_ADEN_LINE_3_DISH:
			updateAdenLine3Dish(keywords, terms);
			break;
		case CR_ADEN_LINE_45_DISH:
			updateAdenLine45Dish(keywords, terms);
			break;
		case CR_ADEN_LINE_6_DISH:
			updateAdenLine6Dish(keywords, terms);
			break;
		case CR_ADEN_SCHNITBAR_DISH:
			updateAdenSchnitbarDish(keywords, terms);
			break;
		case CR_ADEN_CURRYQ_DISH:
			updateAdenCurryqDish(keywords, terms);
			break;
		case CR_ADEN_CAFE_DISH:
			updateAdenCafeDish(keywords, terms);
			break;
		case CR_MOLTKE_CHOICE_1_DISH:
			updateMoltkeChoice1Dish(keywords, terms);
			break;
		case CR_MOLTKE_CHOICE_2_DISH:
			updateMoltkeChoice2Dish(keywords, terms);
			break;
		case CR_MOLTKE_SCHNITBAR_DISH:
			updateMoltkeSchnitbarDish(keywords, terms);
			break;
		case CR_MOLTKE_CAFE_DISH:
			updateMoltkeCafeDish(keywords, terms);
			break;
		case CR_MOLTKE_GG_DISH:
			updateMoltkeGGDish(keywords, terms);
			break;
		case CR_MOLTKE_ACTTHEK_DISH:
			updateMoltkeActthekDish(keywords, terms);
			break;
		case CR_MOLTKE_BUFFET_DISH:
			updateMoltkeBuffetDish(keywords, terms);
			break;
		case CR_TELL_MEAL_NOT_EXIST:
			updateMealNotExist(keywords, terms);
			break;
		case CR_EXIT:
			updateStatExit(keywords, terms);
			break;
	default:
		break;
	
	}
	
}


private void updateMoltkeActthekDish(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStatExit(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateMealNotExist(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateMoltkeBuffetDish(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateMoltkeGGDish(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateMoltkeCafeDish(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateMoltkeSchnitbarDish(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateMoltkeChoice2Dish(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateMoltkeChoice1Dish(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateAdenCafeDish(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateAdenCurryqDish(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateAdenSchnitbarDish(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateAdenLine6Dish(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateAdenLine45Dish(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateAdenLine3Dish(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateAdenLine2Dish(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateAdenLine1Dish(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

private void updateStateEntry(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}

}