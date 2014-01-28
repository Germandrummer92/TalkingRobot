package dm;

import java.util.List;

/**
 * This class represents a dialog about canteen information
 * @author Xizhe Lian, Daniel Draper
 * @version 0.6
 */
public class CanteenInformationDialog extends CanteenDialog {
	private String wishDate;
	private String wishMeal;
	
/**
	 * @param session
	 * @dialogState
	 * @param currentCanteen
	 */
	public CanteenInformationDialog(Session session, DialogState dialogState, Canteen currentCanteen) {
		super(session, dialogState, currentCanteen);
	}


	
public String getWishDate() {
	return wishDate;
}



public void setWishDate(String wishDate) {
	this.wishDate = wishDate;
}



public String getWishMeal() {
	return wishMeal;
}



public void setWishMeal(String wishMeal) {
	this.wishMeal = wishMeal;
}



@Override
public void updateState(List<Keyword> keywords, List<String> terms,
		List<String> approval) throws WrongStateClassException {
	if (getCurrentDialogState().getClass() != CanteenInformationState.class) {
		throw new WrongStateClassException(getCurrentDialogState().getClass().getName());
	}
	switch ((CanteenInfo)getCurrentDialogState().getCurrentState()) {
	case CI_ENTRY:
		updateStateEntry();
		break;

	case CI_ADEN_LINE_1_PRICE:
		updateStateAdenLine1Price(keywords, terms);
		break;
	case CI_ADEN_LINE_2_PRICE:
		updateStateAdenLine2Price(keywords, terms);
		break;
	case CI_ADEN_LINE_3_PRICE:
		updateStateAdenLine3Price(keywords, terms);
		break;
	case CI_ADEN_LINE_45_PRICE:
		updateStateAdenLine45Price(keywords, terms);
		break;
	case CI_ADEN_LINE_1_DISH:
		updateStateAdenLine1Dish(keywords, terms);
		break;
	case CI_ADEN_LINE_2_DISH:
		updateStateAdenLine2Dish(keywords, terms);
		break;
	case CI_ADEN_LINE_3_DISH:
		updateStateAdenLine3Dish(keywords, terms);
		break;
	case CI_ADEN_LINE_45_DISH:
		updateStateAdenLine45Dish(keywords, terms);
		break;
	case CI_ADEN_LINE_6_DISH:
		updateStateAdenLine6Dish(keywords, terms);
		break;
	case CI_ADEN_LINE_6_PRICE:
		updateStateAdenLine6Price(keywords, terms);
		break;
	case CI_ADEN_SCHNITBAR_PRICE:
		updateStateAdenSchnitbarPrice(keywords, terms);
		break;
	case CI_ADEN_SCHNITBAR_DISH:
		updateStateAdenSchnitbarDish(keywords, terms);
		break;
	case CI_ADEN_CAFE_PRICE:
		updateStateAdenCafePrice(keywords, terms);
		break;
	case CI_ADEN_CURRYQ_PRICE:
		updateStateAdenCurryqPrice(keywords, terms);
		break;
	case CI_ADEN_CURRYQ_DISH:
		updateStateAdenCurryqDish(keywords, terms);
		break;
	case CI_ADEN_CAFE_DISH:
		updateStateAdenCafeDish(keywords, terms);
		break;
	case CI_MOLTKE_CHOICE_1_PRICE:
		updateMoltkeChoice1Price(keywords, terms);
		break;
	case CI_MOLTKE_CHOICE_1_DISH:
		updateMoltkeChoice1Dish(keywords, terms);
		break;
	case CI_MOLTKE_CHOICE_2_PRICE:
		updateMoltkeChoice2Price(keywords, terms);
		break;
	case CI_MOLTKE_CHOICE_2_DISH:
		updateMoltkeChoice2Dish(keywords, terms);
		break;
	case CI_MOLTKE_ACTTHEK_PRICE:
		updateMoltkeActthekPrice(keywords, terms);
		break;
	case CI_MOLTKE_ACTTHEK_DISH:
		updateMoltkeActthekDish(keywords, terms);
		break;
	case CI_MOLTKE_SCHNITBAR_PRICE:
		updateMoltkeSchnitbarPrice(keywords, terms);
		break;
	case CI_MOLTKE_SCHNITBAR_DISH:
		updateMoltkeSchnitbarDish(keywords, terms);
		break;
	case CI_MOLTKE_GG_PRICE:
		updateMoltkeGGPrice(keywords, terms);
		break;
	case CI_MOLTKE_GG_DISH:
		updateMoltkeGGDish(keywords, terms);
		break;
	case CI_MOLTKE_BUFFET_PRICE:
		updateMoltkeBuffetPrice(keywords, terms);
		break;
	case CI_MOLTKE_BUFFET_DISH:
		updateMoltkeBuffetDish(keywords, terms);
		break;
	
	case CI_TELL_LINE_NOT_EXIST:
		updateStateTellLineNotExist(keywords, terms);
		break;
	case CI_TELL_MEAL_NOT_EXIST:
		updateStateTellMealNotExist(keywords, terms);
		break;
	case  CI_EXIT:
		updateStateExit(keywords, terms);
		break;
	default:
		
		break;
	
	}
	
}


private void updateStateAdenCafePrice(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}


private void updateStateExit(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}


private void updateStateTellMealNotExist(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}


private void updateStateTellLineNotExist(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}


private void updateMoltkeBuffetDish(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}


private void updateMoltkeBuffetPrice(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}


private void updateMoltkeGGDish(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}


private void updateMoltkeGGPrice(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}


private void updateMoltkeSchnitbarDish(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}


private void updateMoltkeSchnitbarPrice(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}


private void updateMoltkeActthekDish(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}


private void updateMoltkeActthekPrice(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}


private void updateMoltkeChoice2Dish(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}


private void updateMoltkeChoice2Price(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}


private void updateMoltkeChoice1Dish(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}


private void updateMoltkeChoice1Price(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}


private void updateStateAdenCafeDish(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}


private void updateStateAdenCurryqDish(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}


private void updateStateAdenCurryqPrice(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}


private void updateStateAdenSchnitbarDish(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}


private void updateStateAdenSchnitbarPrice(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}


private void updateStateAdenLine6Price(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}


private void updateStateAdenLine6Dish(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}


private void updateStateAdenLine45Dish(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}


private void updateStateAdenLine3Dish(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}


private void updateStateAdenLine2Dish(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}


private void updateStateAdenLine1Dish(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}


private void updateStateAdenLine45Price(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}


private void updateStateAdenLine3Price(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}


private void updateStateAdenLine2Price(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}


private void updateStateAdenLine1Price(List<Keyword> keywords, List<String> terms) {
	// TODO Auto-generated method stub
	
}


private void updateStateEntry() {
	// TODO Auto-generated method stub
	
}

}