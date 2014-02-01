package dm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.joda.time.LocalDate;

import data.CanteenData;
import data.CanteenNames;
import data.LineData;
import data.MealCategoryData;
import data.MealData;

/**
 * This class represents a dialog about canteen recommendation
 * @author Xizhe Lian, Luiz Henrique S. Silva
 * @version 0.5
 */
public class CanteenRecommendationDialog extends CanteenDialog {
	
	private String wishmealCategory;
	private OneMealData wishMeal;
	//private String wishDate;
	
	/**
	 * @param session
	 * @param currentCanteen
	 */
	public CanteenRecommendationDialog(Session session, DialogState dialogState, Canteen currentCanteen) {
		super(session, dialogState, currentCanteen);
		this.dialogModus = DialogModus.CANTEEN_RECOMMENDATION;
	}

	public String getWishmealCategory() {
				return wishmealCategory;
	}
	public OneMealData getWishmeal() {
				return wishMeal;
	}
	
	public void setWishmeal(OneMealData wishmeal) {
			this.wishMeal = wishMeal;
	}
/*
public String getWishDate() {
	return wishDate;
}*/
/*
public void setWishDate(String wishDate) {
		this.wishDate = wishDate;
}*/

@Override
public void updateState(List<Keyword> keywords, List<String> terms,
		List<String> approval) throws WrongStateClassException {

	if (getCurrentDialogState().getClass() != CanteenRecommendationState.class) {
		throw new WrongStateClassException(getCurrentDialogState().getCurrentState().getClass().getName());
	}
	
	switch ((CanteenRecom)getCurrentDialogState().getCurrentState()) {
		case CR_ENTRY:
			updateStateEntry(keywords, terms, approval);
			break;
		case CR_ASK_PREFERENCE:
			updateStateAskPreference(keywords, terms, approval);
			break;
		/*case CR_ADEN_LINE_1_DISH:
			updateAdenLine1Dish(keywords, terms, approval);
			break;
		case CR_ADEN_LINE_2_DISH:
			updateAdenLine2Dish(keywords, terms, approval);
			break;
		case CR_ADEN_LINE_3_DISH:
			updateAdenLine3Dish(keywords, terms, approval); 
			break;
		case CR_ADEN_LINE_45_DISH:
			updateAdenLine45Dish(keywords, terms, approval);
			break;
		case CR_ADEN_LINE_6_DISH:
			updateAdenLine6Dish(keywords, terms, approval);
			break;
		case CR_ADEN_SCHNITBAR_DISH:
			updateAdenSchnitbarDish(keywords, terms, approval);
			break;
		case CR_ADEN_CURRYQ_DISH:
			updateAdenCurryqDish(keywords, terms, approval);
			break;
		case CR_ADEN_CAFE_DISH:
			updateAdenCafeDish(keywords, terms, approval);
			break;
		case CR_MOLTKE_CHOICE_1_DISH:
			updateMoltkeChoice1Dish(keywords, terms, approval);
			break;
		case CR_MOLTKE_CHOICE_2_DISH:
			updateMoltkeChoice2Dish(keywords, terms, approval);
			break;
		case CR_MOLTKE_SCHNITBAR_DISH:
			updateMoltkeSchnitbarDish(keywords, terms, approval);
			break;
		case CR_MOLTKE_CAFE_DISH:
			updateMoltkeCafeDish(keywords, terms, approval);
			break;
		case CR_MOLTKE_GG_DISH:
			updateMoltkeGGDish(keywords, terms, approval);
			break;
		case CR_MOLTKE_ACTTHEK_DISH:
			updateMoltkeActthekDish(keywords, terms, approval);
			break;
		case CR_MOLTKE_BUFFET_DISH:
			updateMoltkeBuffetDish(keywords, terms, approval);
			break;
			 */
		case CR_TELL_MEAL_NOT_EXIST:
			updateMealNotExist(keywords, terms, approval);
			break;
		case CR_EXIT:
			updateStatExit(keywords, terms, approval);
			break;
		default:
			break;
		}
	}
	
	public void setWishmealCategory(String wishmealCategory) {
			this.wishmealCategory = wishmealCategory;
	}
	
	public void setWishMeal(OneMealData wishMeal) {
		this.wishMeal = wishMeal;
	}
	
	public OneMealData getWishMeal() {
		return this.wishMeal;
	}
	/*
	public String getWishDate() {
		return wishDate;
	}*/
	/*
	public void setWishDate(String wishDate) {
			this.wishDate = wishDate;
	}*/


	private boolean updateStateKeywordJump(List<Keyword> keywords) {
		if (keywords == null || keywords.isEmpty()) {
			return false;
		}
		//Check if all keywords pointing to same state
		else {
			boolean sameRef = true;
			Enum<?> ref = keywords.get(0).getReference().get(0).getCurrentState();
			for (Keyword kw : keywords) {
				for (DialogState d : kw.getReference()) {
					if (!ref.equals(d.getCurrentState())) {
						sameRef = false;
					}
				}
			}
			if (sameRef == true) {
				getCurrentDialogState().setCurrentState(ref);
				return true;
			}
			//If not go to keyword with highest priority
			else {
				int priorityMax = keywords.get(0).getKeywordData().getPriority();
				Keyword curKW = keywords.get(0);
				DialogState curRef = keywords.get(0).getReference().get(0);
				for (Keyword kw : keywords) {
					for (DialogState d : kw.getReference()) {
						if (kw.getKeywordData().getPriority() > priorityMax) {
							curKW = kw;
							priorityMax = curKW.getKeywordData().getPriority();
							curRef = d;
						}
					}
				}
				getCurrentDialogState().setCurrentState(curRef.getCurrentState());
				return true;
			}
		}
	}

	private void updateStateAskPreference(List<Keyword> keywords, List<String> terms, List<String> approval) {
		if (keywords.isEmpty()) {
			//Didn't get any keyword for preference
			DialogManager.giveDialogManager().setInErrorState(true);
		}
		else {
			LocalDate date = LocalDate.now();
			int dateShift = 0; //0 for today, 1 for tomorrow, etc
			int result = getRequestedWeekDay(keywords, date) - date.getDayOfWeek();
			//Normalize the shift according to the days of the week.
			if (result <= 0) { 
				dateShift = 7 + result; //7 days in the week
			} else {
				dateShift = result;
			}
			
			
			//The request type of food (MealCategory) is in keyword.
			
			this.setWishmealCategory(keywords.get(0).getWord());
//			this.setWishmealCategory(keywords.get(0));
			
			//Need to search in data then decide what's next state
			ArrayList<Canteen> canteens = new ArrayList<Canteen>();
			canteens.add(new Canteen(new CanteenData(CanteenNames.ADENAUERRING, dateShift)));
			canteens.add(new Canteen(new CanteenData(CanteenNames.MOLTKE, dateShift)));
			canteens.add(new Canteen(new CanteenData(CanteenNames.HOLZGARTEN, dateShift)));
			canteens.add(new Canteen(new CanteenData(CanteenNames.GOTTESAUE, dateShift)));
			canteens.add(new Canteen(new CanteenData(CanteenNames.TIEFENBRONNER, dateShift)));
			canteens.add(new Canteen(new CanteenData(CanteenNames.ERZBERGER, dateShift)));
			
			
			ArrayList<OneMealData> matchedMeals = new ArrayList<OneMealData>();
			//Not pretty at all!!
			for (Canteen canteen : canteens) {
				for (LineData lineData : canteen.getCanteenData().getLines()) {
					for (MealData mealData : lineData.getTodayMeals()) {
						for (MealCategoryData mealCategory : mealData.getMealCategory()) {
							if (mealCategory.getMealCategoryName().equals(wishmealCategory)) {
								//Category found
								matchedMeals.add(new OneMealData(mealData, canteen.getCanteenData(), lineData));
							}
						}	
					}
				}
			}
			
			//Now randomly (or based on User History) choose one Meal
			setWishMeal(chooseMeal(matchedMeals));
			
			
			//Based on choosed wishMeal
			this.setCurrentDialogState(selectNextState());
		}
	}
	
	private DialogState selectNextState() {
		DialogState nextState = new DialogState();
		if (wishMeal.canteenData.getCanteenName().equals(CanteenNames.ADENAUERRING)) {
			if (wishMeal.lineData.getLineName().equals("l1")) {
				nextState.setCurrentState(CanteenRecom.CR_ADEN_LINE_1_DISH);
			}
			else if(wishMeal.lineData.getLineName().equals("l2")) { 
				nextState.setCurrentState(CanteenRecom.CR_ADEN_LINE_2_DISH);
			}
			else if(wishMeal.lineData.getLineName().equals("l3")) {
				nextState.setCurrentState(CanteenRecom.CR_ADEN_LINE_3_DISH);
			}
			else if(wishMeal.lineData.getLineName().equals("l45")) {
				nextState.setCurrentState(CanteenRecom.CR_ADEN_LINE_45_DISH);
			}
			else if(wishMeal.lineData.getLineName().equals("update")) {
				nextState.setCurrentState(CanteenRecom.CR_ADEN_LINE_6_DISH);
			}
			else if(wishMeal.lineData.getLineName().equals("schnitzelbar")) {
				nextState.setCurrentState(CanteenRecom.CR_ADEN_SCHNITBAR_DISH);
			}
			else if(wishMeal.lineData.getLineName().equals("aktion")) {
				nextState.setCurrentState(CanteenRecom.CR_ADEN_CURRYQ_DISH);
			} //curry queen
			else if(wishMeal.lineData.getLineName().equals("heisstheke")) {
				nextState.setCurrentState(CanteenRecom.CR_ADEN_CAFE_DISH);
			} //cafe
		}
		//TODO not yet implemented to the other Canteens
		else if (wishMeal.canteenData.getCanteenName().equals(CanteenNames.MOLTKE)) { }
		else if (wishMeal.canteenData.getCanteenName().equals(CanteenNames.HOLZGARTEN)) { }
		else if (wishMeal.canteenData.getCanteenName().equals(CanteenNames.GOTTESAUE)) { }
		else if (wishMeal.canteenData.getCanteenName().equals(CanteenNames.TIEFENBRONNER)) { }
		else if (wishMeal.canteenData.getCanteenName().equals(CanteenNames.ERZBERGER)) { }
		return null;
	}

	private OneMealData chooseMeal(ArrayList<OneMealData> matchedMeals) {
		//FIXME Temporarily choosing at random
		Random mealRandom = new Random();
		Integer index = mealRandom.nextInt(matchedMeals.size());
		
		return matchedMeals.get(index);
	}

	private int getRequestedWeekDay(List<Keyword> keywords, LocalDate today) {
		for (Keyword dateOfWeek : keywords) {
			if (dateOfWeek.getWord().equals("today")) return today.getDayOfWeek();
			else if (dateOfWeek.getWord().equals("tomorrow")) return today.getDayOfWeek() + 1;
			else if (dateOfWeek.getWord().equals("day after tomorrow")) return today.getDayOfWeek() + 2;
			else if (dateOfWeek.getWord().equals("sunday")) return 0;
			else if (dateOfWeek.getWord().equals("monday")) return 1;
			else if (dateOfWeek.getWord().equals("tuesday")) return 2;
			else if (dateOfWeek.getWord().equals("wednesday")) return 3;
			else if (dateOfWeek.getWord().equals("thursday")) return 4;
			else if (dateOfWeek.getWord().equals("friday")) return 5;
			else if (dateOfWeek.getWord().equals("saturday")) return 6;
		}
		return 0;
	}
	

	private void updateMoltkeActthekDish(List<Keyword> keywords, List<String> terms, List<String> approval) {
		//Does nothing
	}
	
	private void updateStatExit(List<Keyword> keywords, List<String> terms, List<String> approval) {
		
	}
	
	private void updateMealNotExist(List<Keyword> keywords, List<String> terms, List<String> approval) {
		
	}
	
	/*
	private void updateMoltkeBuffetDish(List<Keyword> keywords, List<String> terms, List<String> approval) {
		
	}
	
	private void updateMoltkeGGDish(List<Keyword> keywords, List<String> terms, List<String> approval) {
		
	}
	
	private void updateMoltkeCafeDish(List<Keyword> keywords, List<String> terms, List<String> approval) {
		
	}
	
	private void updateMoltkeSchnitbarDish(List<Keyword> keywords, List<String> terms, List<String> approval) {
		
	}
	
	private void updateMoltkeChoice2Dish(List<Keyword> keywords, List<String> terms, List<String> approval) {
		
	}
	
	private void updateMoltkeChoice1Dish(List<Keyword> keywords, List<String> terms, List<String> approval) {
		
	}
	
	private void updateAdenCafeDish(List<Keyword> keywords, List<String> terms, List<String> approval) {
		
	}
	
	private void updateAdenCurryqDish(List<Keyword> keywords, List<String> terms, List<String> approval) {
		
	}
	
	private void updateAdenSchnitbarDish(List<Keyword> keywords, List<String> terms, List<String> approval) {
		
	}
	
	private void updateAdenLine6Dish(List<Keyword> keywords, List<String> terms, List<String> approval) {

	}
	
	private void updateAdenLine45Dish(List<Keyword> keywords, List<String> terms, List<String> approval) {
		
	}
	
	private void updateAdenLine3Dish(List<Keyword> keywords, List<String> terms, List<String> approval) {
		
	}
	
	private void updateAdenLine2Dish(List<Keyword> keywords, List<String> terms, List<String> approval) {
		
	}
	
	private void updateAdenLine1Dish(List<Keyword> keywords, List<String> terms, List<String> approval) {
		
		
	}
	*/
	/**
	 * Will forward to next state (CR_ASK_PREFERENCE)
	 * @param keywords
	 * @param terms
	 * @param approval
	 */
	private void updateStateEntry(List<Keyword> keywords, List<String> terms, List<String> approval) {
		if (keywords == null || keywords.isEmpty()) {
			DialogManager.giveDialogManager().setInErrorState(true);
		}
		// TODO Check for User History!
		getCurrentDialogState().setCurrentState(CanteenRecom.CR_ASK_PREFERENCE);
	}
	
	//Mini helper class to keep track of a specific meal's information.
	class OneMealData {
		MealData mealData;
		CanteenData canteenData;
		LineData lineData;
		
		public OneMealData(MealData mealData, CanteenData canteenData, LineData lineData) {
			this.mealData = mealData;
			this.canteenData = canteenData;
			this.lineData = lineData;
		}

		public MealData getMealData() {
			return mealData;
		}

		public CanteenData getCanteenData() {
			return canteenData;
		}

		public LineData getLineData() {
			return lineData;
		}
	}
	
//	public static void main(String[] args) throws WrongStateClassException {
//		ArrayList<String> keywords = new ArrayList<String>();
//		ArrayList<String> terms = new ArrayList<String>();
//		ArrayList<String> approval = new ArrayList<String>();
//		
////		Keyword fish = new Keyword(new K));
////		Keyword date = new Keyword(new KeywordData("today"));
//		keywords.add("fish");
//		keywords.add("today");
//		
//		User user = new User();
//		session;
//		Session s = new Session(user, null);
//		Canteen c = new Canteen(null);
//		CanteenRecommendationState cstate = new CanteenRecommendationState();
//		cstate.setCurrentState(CanteenRecom.CR_ASK_PREFERENCE);
//		CanteenRecommendationDialog dialog = new CanteenRecommendationDialog(s, cstate, c);
//		dialog.updateStateAskPreference(keywords, terms, approval);
//		
//		
//		System.out.println(dialog.getWishMeal().getMealData().getMealName());
//	}

}