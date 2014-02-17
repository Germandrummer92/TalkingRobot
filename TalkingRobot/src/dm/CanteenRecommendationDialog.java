package dm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.joda.time.LocalDate;

import data.CanteenData;
import data.CanteenNames;
import data.LineData;
import data.MealCategoryData;
import data.MealData;
import data.MealDatePair;

/**
 * This class represents a dialog about canteen recommendation
 * @author Xizhe Lian, Luiz Henrique S. Silva
 * @version 1.0
 */
public class CanteenRecommendationDialog extends CanteenDialog {
	
	private String wishmealCategory;
	
	/** A structure with temporary information of a specific meal. */
	private OneMealData wishMeal;
	
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
	
	public void setWishmealCategory(String wishmealCategory) {
		this.wishmealCategory = wishmealCategory;
	}

	public void setWishMeal(OneMealData wishMeal) {
		this.wishMeal = wishMeal;
	}

	public OneMealData getWishMeal() {
		return this.wishMeal;
	}

	@Override
	public void updateState(List<Keyword> keywords, List<String> terms,
			List<String> approval) throws WrongStateClassException {
	
		updateStateKeywordJump(keywords);
		
		if (getCurrentDialogState().getClass() != CanteenRecommendationState.class || getCurrentDialogState().getCurrentState().getClass() != CanteenRecom.class) {
			throw new WrongStateClassException(getCurrentDialogState().getCurrentState().getClass().getName());
		}
		
		switch ((CanteenRecom)getCurrentDialogState().getCurrentState()) {
			case CR_ENTRY:
				updateStateEntry(keywords, terms, approval);
				break;
			case CR_ASK_PREFERENCE:
				updateStateAskPreference(keywords, terms, approval);
				break;
			case CR_TELL_MEAL_NOT_EXIST:
				updateMealNotExist(keywords, terms, approval);
				break;
			case CR_EXIT:
				updateStatExit(keywords, terms, approval);
				break;
	    	case CR_ADEN_LINE_1_DISH:
	    	case CR_ADEN_LINE_2_DISH:
	    	case CR_ADEN_LINE_3_DISH:
	    	case CR_ADEN_LINE_45_DISH:
	    	case CR_ADEN_LINE_6_DISH:
	    	case CR_ADEN_SCHNITBAR_DISH:
	    	case CR_ADEN_CURRYQ_DISH:
	    	case CR_ADEN_CAFE_DISH:
	    	case CR_MOLTKE_CHOICE_1_DISH:
	    	case CR_MOLTKE_CHOICE_2_DISH:
	    	case CR_MOLTKE_SCHNITBAR_DISH:
	    	case CR_MOLTKE_CAFE_DISH:
	    	case CR_MOLTKE_ACTTHEK_DISH:
	    	case CR_MOLTKE_GG_DISH:
	    	case CR_MOLTKE_BUFFET_DISH:
	    		updateMealFound(keywords, terms, approval);
			default:
				break;
		}
	}

	/**
	 * Updates the State according to the keywords passed. Jumps to Reference with highest Priority.
	 * @param keywords
	 * @return if the jump was completed
	 */
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

	/**
	 * Does most of the job of finding a meal to recommend to the User, based on his preference/ on the meal category he wants
	 * to have, and on the date he would like to have it.
	 * @param keywords
	 * @param terms
	 * @param approval
	 */
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
			if (result < 0) { 
				dateShift = 7 + result; //7 days in the week
			} else {
				dateShift = result;
			}
			
			
			//Find a keyword that represents the category
			String category = "no_category";
			for (Keyword kw : keywords) {
				String possibleCategory = this.verifyCategories(kw.getWord());
				if (!possibleCategory.equals("no_category")) {
					category = possibleCategory;
				}
			}
			//If no category found, there is a problem.
			if (category.equals("no_category")) {
				DialogManager.giveDialogManager().setInErrorState(true);
				return;
			}
			//else
			this.setWishmealCategory(category);
			
			//Fetching canteen data, in order to search the category
			ArrayList<Canteen> canteens = new ArrayList<Canteen>();
			canteens.add(new Canteen(new CanteenData(CanteenNames.ADENAUERRING, dateShift)));
//			canteens.add(new Canteen(new CanteenData(CanteenNames.MOLTKE, dateShift)));
//			canteens.add(new Canteen(new CanteenData(CanteenNames.HOLZGARTEN, dateShift)));
//			canteens.add(new Canteen(new CanteenData(CanteenNames.GOTTESAUE, dateShift)));
//			canteens.add(new Canteen(new CanteenData(CanteenNames.TIEFENBRONNER, dateShift)));
//			canteens.add(new Canteen(new CanteenData(CanteenNames.ERZBERGER, dateShift)));
			
			//Find meals based on what the user said he wants, and on the canteen information
			ArrayList<OneMealData> matchedMeals = findMatchedMeals(canteens);
			
			if (matchedMeals.isEmpty()) {
				DialogState nextState = new DialogState();
				nextState.setCurrentState(CanteenRecom.CR_TELL_MEAL_NOT_EXIST);
				getCurrentDialogState().setCurrentState(nextState.getCurrentState());
				return;
			}
			
			//Now randomly choose one Meal (based on User History for later implementation?)
			setWishMeal(chooseMeal(matchedMeals));
			
			
			//Based on chosen wishMeal
			getCurrentDialogState().setCurrentState(selectNextState().getCurrentState());
		}
	}
	
	/**
	 * When meal is found in the canteen.
	 * @param keywords
	 * @param terms
	 * @param approval
	 */
	private void updateMealFound(List<Keyword> keywords, List<String> terms,
			List<String> approval) {
		//Since the user is asked for an approval, if he says yes, his history will be saved.
		if (!approval.isEmpty()) {
			if (approval.get(0).equals("yes")) {
				//Save user history
				//DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				Date date = new Date();
				MealDatePair mealAndDate = new MealDatePair(date, wishMeal.getMealData());
				this.getCurrentSession().getCurrentUser().getUserData().addAcceptedSuggestion(mealAndDate);
			}
		}
		DialogState nextState = new DialogState();
		nextState.setCurrentState(CanteenRecom.CR_EXIT);
		getCurrentDialogState().setCurrentState(nextState.getCurrentState());
		
	}
	
	/**
	 * Given the canteens information, it looks for the meals that match the mealCategory setted in the class earlier.
	 * @param canteens
	 * @return
	 */
	private ArrayList<OneMealData> findMatchedMeals(ArrayList<Canteen> canteens) {
		
		ArrayList<OneMealData> matchedMeals = new ArrayList<OneMealData>();
		//Not pretty at all!!
		for (Canteen canteen : canteens) {
			for (LineData lineData : canteen.getCanteenData().getLines()) {
				if (!lineData.getLineName().equals("nmtisch")) { //prevents future NullExceptions, since we're not using it
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
		}
		return matchedMeals;
	}

	/**
	 * Selects the next state, according to the meal that is going to be recommended, so that the right
	 * sentence can be selected for the output.
	 * @return
	 */
	private DialogState selectNextState() {
		DialogState nextState = new DialogState();
		if (wishMeal.canteenData.getCanteenName().equals(CanteenNames.ADENAUERRING)) {
			if (wishMeal.lineData.getLineName().equals("line one")) {
				nextState.setCurrentState(CanteenRecom.CR_ADEN_LINE_1_DISH);
			}
			else if(wishMeal.lineData.getLineName().equals("line two")) { 
				nextState.setCurrentState(CanteenRecom.CR_ADEN_LINE_2_DISH);
			}
			else if(wishMeal.lineData.getLineName().equals("line three")) {
				nextState.setCurrentState(CanteenRecom.CR_ADEN_LINE_3_DISH);
			}
			else if(wishMeal.lineData.getLineName().equals("line four")) {
				nextState.setCurrentState(CanteenRecom.CR_ADEN_LINE_45_DISH);
			}
			else if(wishMeal.lineData.getLineName().equals("line six")) {
				nextState.setCurrentState(CanteenRecom.CR_ADEN_LINE_6_DISH);
			}
			else if(wishMeal.lineData.getLineName().equals("schnitzel bar")) {
				nextState.setCurrentState(CanteenRecom.CR_ADEN_SCHNITBAR_DISH);
			}
			else if(wishMeal.lineData.getLineName().equals("Curry Queen")) {
				nextState.setCurrentState(CanteenRecom.CR_ADEN_CURRYQ_DISH);
			} //curry queen
			else if(wishMeal.lineData.getLineName().equals("theke")) {
				nextState.setCurrentState(CanteenRecom.CR_ADEN_CAFE_DISH);
			} //cafe
//			else if(wishMeal.lineData.getLineName().equals("nmtisch")) {
//				nextState.setCurrentState(CanteenRecom.CR_ADEN_CAFEABEND_DISH);
//			} //cafe
		}
		//TODO not yet implemented to the other Canteens
		else if (wishMeal.canteenData.getCanteenName().equals(CanteenNames.MOLTKE)) { }
		else if (wishMeal.canteenData.getCanteenName().equals(CanteenNames.HOLZGARTEN)) { }
		else if (wishMeal.canteenData.getCanteenName().equals(CanteenNames.GOTTESAUE)) { }
		else if (wishMeal.canteenData.getCanteenName().equals(CanteenNames.TIEFENBRONNER)) { }
		else if (wishMeal.canteenData.getCanteenName().equals(CanteenNames.ERZBERGER)) { }
		return nextState;
	}

	/**
	 * Chooses a meal to be recommended to the user, among a list of meals that could be recommended.
	 * Random choice, at the moment.
	 * @param matchedMeals
	 * @return
	 */
	private OneMealData chooseMeal(ArrayList<OneMealData> matchedMeals) {
		//FIXME Temporarily choosing at random
		Random mealRandom = new Random();
		Integer index = mealRandom.nextInt(matchedMeals.size());
		
		return matchedMeals.get(index);
	}

	/**
	 * Selects the day of the week given through keywords and returns it's value int Integer
	 * @param keywords
	 * @param today
	 * @return the value in Integer, according to joda library definition for the weekdays
	 */
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
		return today.getDayOfWeek();
	}
	
	/**
	 * It both checks if keyword is a meal category and returns the correct term for it.
	 * @param keyword
	 * @return
	 */
	private String verifyCategories(String keyword) {
		switch (keyword) {
		case "beef":
		case "cow":
			return "cow";
		case "fish":
			return keyword; // no changes
		case "veg":
		case "vegetarian":
			return "veg";
		case "vegan":
		case "pork":
			return keyword; // no changes
		default:
			return "no_category";
		}
	}
	
	private void updateStatExit(List<Keyword> keywords, List<String> terms, List<String> approval) {
		//...
	}
	
	/**
	 * If meal wasn't found in the canteens for the given keywords.
	 * @param keywords
	 * @param terms
	 * @param approval
	 */
	private void updateMealNotExist(List<Keyword> keywords, List<String> terms, List<String> approval) {
		//Doesn't expect anything. Maybe that the user says something else?
		if (!approval.isEmpty()) {
			if (approval.get(0).equals("no")) {
				getCurrentDialogState().setCurrentState(CanteenRecom.CR_EXIT);
				return;
			}
		}
		getCurrentDialogState().setCurrentState(CanteenRecom.CR_ASK_PREFERENCE);
	}
	
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
		//If User has History
		if (!this.getCurrentSession().getCurrentUser().getUserData().getAcceptedSuggestions().isEmpty()) {
			//find based on User History
			
		}
		
		getCurrentDialogState().setCurrentState(CanteenRecom.CR_ASK_PREFERENCE);
	}
	
	
	//Mini helper class to keep track of a specific meal's information.
	/**
	 * Structure to be able to store the information related to a specific meal. Used internally only.
	 */
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
//		ArrayList<Keyword> keywords = new ArrayList<Keyword>();
//		ArrayList<String> terms = new ArrayList<String>();
//		ArrayList<String> approval = new ArrayList<String>();
//		
////		Keyword fish = new Keyword(new K));
////		Keyword date = new Keyword(new KeywordData("today"));
//		keywords.add(new Keyword("pork", null, 0, null, null));
//		keywords.add(new Keyword("today", null, 0, null, null));
//		
//		User user = new User();
//		Robot robot = new Robot("Teddy", true);
//		Session s = new Session(user, robot);
//		Canteen c = new Canteen(null);
//		CanteenRecommendationState cstate = new CanteenRecommendationState();
//		cstate.setCurrentState(CanteenRecom.CR_ASK_PREFERENCE);
//		CanteenRecommendationDialog dialog = new CanteenRecommendationDialog(s, cstate, c);
//		dialog.updateStateAskPreference(keywords, terms, approval);
//		
//		String result = "No meal found!";
//		if (dialog.getWishmeal() != null) {
//			result = dialog.getWishMeal().getMealData().getMealName();
//		}
//		System.out.println(result);
//	}

}