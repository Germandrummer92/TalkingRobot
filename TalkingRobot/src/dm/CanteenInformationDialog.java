package dm;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import data.CanteenData;
import data.CanteenNames;
import data.KeywordData;
import data.LineData;
import data.MealData;
import data.MealDatePair;

/**
 * This class represents a dialog about canteen information
 * @author Xizhe Lian, Daniel Draper
 * @version 1.2
 */
public class CanteenInformationDialog extends CanteenDialog {
	// TODO wishDate is not today
	private String wishDate;
	
	private String wishMeal;
	
	private Canteen curCanteen; // current canteen with wished date
	
	
/**
	 * @param session
	 * @dialogState
	 * @param currentCanteen
	 */
	public CanteenInformationDialog(Session session, DialogState dialogState, Canteen currentCanteen) {
		super(session, dialogState, currentCanteen);
		this.dialogModus = DialogModus.CANTEEN_INFORMATION;
		this.wishDate = "";
		this.wishMeal = "";
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
		throw new WrongStateClassException(getCurrentDialogState().getCurrentState().getClass().getName());
	}
	switch ((CanteenInfo)getCurrentDialogState().getCurrentState()) {
	case CI_ENTRY:
		updateStateEntry(keywords, terms);
		break;
	case CI_ADEN_LINE_1_PRICE:
		generalUpdate(keywords, terms, approval);
		break;
	case CI_ADEN_LINE_2_PRICE:
		generalUpdate(keywords, terms, approval);
		break;
	case CI_ADEN_LINE_3_PRICE:
		generalUpdate(keywords, terms, approval);
		break;
	case CI_ADEN_LINE_45_PRICE:
		generalUpdate(keywords, terms, approval);
		break;
	case CI_ADEN_LINE_1_DISH:
		generalUpdate(keywords, terms, approval);
		break;
	case CI_ADEN_LINE_2_DISH:
		generalUpdate(keywords, terms, approval);
		break;
	case CI_ADEN_LINE_3_DISH:
		generalUpdate(keywords, terms, approval);
		break;
	case CI_ADEN_LINE_45_DISH:
		generalUpdate(keywords, terms, approval);
		break;
	case CI_ADEN_LINE_6_DISH:
		generalUpdate(keywords, terms, approval);
		break;
	case CI_ADEN_LINE_6_PRICE:
		generalUpdate(keywords, terms, approval);
		break;
	case CI_ADEN_SCHNITBAR_PRICE:
		generalUpdate(keywords, terms, approval);
		break;
	case CI_ADEN_SCHNITBAR_DISH:
		generalUpdate(keywords, terms, approval);
		break;
	case CI_ADEN_CAFE_PRICE:
		generalUpdate(keywords, terms, approval);
		break;
	case CI_ADEN_CURRYQ_PRICE:
		generalUpdate(keywords, terms, approval);
		break;
	case CI_ADEN_CURRYQ_DISH:
		generalUpdate(keywords, terms, approval);
		break;
	case CI_ADEN_CAFE_DISH:
		generalUpdate(keywords, terms, approval);
		break;
	case CI_MOLTKE_CHOICE_1_PRICE:
		generalUpdate(keywords, terms, approval);
		break;
	case CI_MOLTKE_CHOICE_1_DISH:
		generalUpdate(keywords, terms, approval);
		break;
	case CI_MOLTKE_CHOICE_2_PRICE:
		generalUpdate(keywords, terms, approval);
		break;
	case CI_MOLTKE_CHOICE_2_DISH:
		generalUpdate(keywords, terms, approval);
		break;
	case CI_MOLTKE_ACTTHEK_PRICE:
		generalUpdate(keywords, terms, approval);
		break;
	case CI_MOLTKE_ACTTHEK_DISH:
		generalUpdate(keywords, terms, approval);
		break;
	case CI_MOLTKE_SCHNITBAR_PRICE:
		generalUpdate(keywords, terms, approval);
		break;
	case CI_MOLTKE_SCHNITBAR_DISH:
		generalUpdate(keywords, terms, approval);
		break;
	case CI_MOLTKE_GG_PRICE:
		generalUpdate(keywords, terms, approval);
		break;
	case CI_MOLTKE_GG_DISH:
		generalUpdate(keywords, terms, approval);
		break;
	case CI_MOLTKE_BUFFET_PRICE:
		generalUpdate(keywords, terms, approval);
		break;
	case CI_MOLTKE_BUFFET_DISH:
		generalUpdate(keywords, terms, approval);
		break;
	
	case CI_TELL_LINE_NOT_EXIST:
		updateStateTellNotExist(keywords, terms, approval);
		break;
	case CI_TELL_MEAL_NOT_EXIST:
		updateStateTellNotExist(keywords, terms, approval);
		break;
	case  CI_EXIT:
		updateStateExit(keywords, terms);
		break;
	default:
		
		break;
	
	}
	
}

/**
 * To update the state after give some informations
 * @param keywords list of keywords
 * @param terms list of Strings
 * @param approval list of approval
 */
private void generalUpdate(List<Keyword> keywords, List<String> terms,
		List<String> approval) {
	 
	boolean finished = false;
	
	if( approval.size() == 1 && (approval.get(0).equals("yes"))) { 
		if( keywords == null && (terms == null)) {// user is satisfied
				DialogState next = new DialogState();
				next.setCurrentState(CanteenInfo.CI_EXIT);
				finished = true;
		} else { // user still has other requests
			CanteenInfo subState = matchSubState(keywords, terms);
			DialogState nextState = new DialogState();
			nextState.setCurrentState(subState);
			setCurrentDialogState(nextState);
			finished = true;
		}
	}	
	
	if( (approval.size() >= 2) && (!finished)) {
		DialogManager.giveDialogManager().setInErrorState(true);
		finished = true;
	}
	
	if( !finished ){
		CanteenInfo subState = matchSubState(keywords, terms);
		DialogState nextState = new DialogState();
		nextState.setCurrentState(subState);
		setCurrentDialogState(nextState);
	}
	
}





private void updateStateExit(List<Keyword> keywords, List<String> terms) {
	// TODO
	// maybe nothing to do?
}

/**
 * update state when user is told that wished meal doesn't exist
 * @param keywords 
 * @param terms
 * @param approval
 */
private void updateStateTellNotExist(List<Keyword> keywords, List<String> terms, List<String> approval) {
	// TODO
	if( approval.isEmpty()) {
		if( keywords.isEmpty() && (terms.isEmpty())) {
			DialogManager.giveDialogManager().setInErrorState(true);
			return;
		}
		
		CanteenInfo subState = matchSubState(keywords, terms);
		DialogState nextState = new DialogState();
		nextState.setCurrentState(subState);
		setCurrentDialogState(nextState);
		return;
	}
	
	if( approval.size() != 1) {
		DialogManager.giveDialogManager().setInErrorState(true);
		return;
	}
	
	if( approval.get(0).equals("yes")) { //user wants another information
		if( keywords.isEmpty() && (terms.isEmpty())) {
			DialogManager.giveDialogManager().setInErrorState(true);
			return;
		}
		
		CanteenInfo subState = matchSubState(keywords, terms);
		DialogState nextState = new DialogState();
		nextState.setCurrentState(subState);
		setCurrentDialogState(nextState);
		return;
		
	} else { // user don't need anything from canteen info
		DialogState  next = new DialogState();
		next.setCurrentState(CanteenInfo.CI_EXIT);
	}
}




/**
 * To update the entry state
 * @param keywords list of Keyword
 * @param terms list of Strings
 */
private void updateStateEntry(List<Keyword> keywords, List<String> terms) {
	 
	//boolean error = false;
	if(keywords == null && (terms) == null) {
		//error = true;
		DialogManager.giveDialogManager().setInErrorState(true);
		return;
	}
	
	CanteenInfo subState = matchSubState(keywords, terms);
	DialogState nextState = new DialogState();
	nextState.setCurrentState(subState);
	setCurrentDialogState(nextState);
}


/**
 * This method helps to match substate 
 * @param keywords list of keyword
 * @param terms list of string
 * @return canteenInfo
 */
private CanteenInfo matchSubState(List<Keyword> keywords, List<String> terms) {
	CanteenInfo next = CanteenInfo.CI_ENTRY;
	boolean askPrice = false;
	// assume that line[name] is given in keywords, meals' name in terms
	// if user ask price, then price will be in terms
	
	
    //	boolean askMeal = false;
	//List<MealData> possibleMeals = new ArrayList<MealDatePair>();
	//List<CanteenInfo> possibleChoices = new ArrayList<CanteenInfo>();
	/* find out first what the user wants to know */
	if( terms != null) {
		for( Keyword toDo : keywords ) {
			if( toDo.getWord().equals("price")) {
				askPrice = true;
				break;
			}
		}
	}
	
	boolean inAden = true;
	if( currentCanteen.getCanteenData().getCanteenName()
			.equals(currentCanteen.getCanteenData().getCanteenName().MOLTKE)) {
		inAden = false;
	}
	
	LocalDate date = LocalDate.now();
	int dateShift = 0; //0 for today, 1 for tomorrow, etc
	int result = getRequestedWeekDay(keywords, date) - date.getDayOfWeek();
	//Normalize the shift according to the days of the week.
	if (result <= 0) { 
		dateShift = 7 + result; //7 days in the week
	} else {
		dateShift = result;
	}
	
	
	if(inAden) {
		curCanteen = new Canteen(new CanteenData(CanteenNames.ADENAUERRING, dateShift));
	} else {
		curCanteen = new Canteen(new CanteenData(CanteenNames.MOLTKE, dateShift));
	}
	
	
	int index = -1;
	if( askPrice ) {
		// now to find out the required meal's name
		for( String name : terms) {
			for( LineData line : curCanteen.getCanteenData().getLines()) {
				for( MealData meal : line.getTodayMeals()) {
					meal.getMealName().equals(name);
					index = curCanteen.getCanteenData().getLines().indexOf(line);
					//next = getLineEnum(line, askPrice);
					setWishMeal(name);
				}
			}
			
		}
		
		if( index == -1) { // then maybe the user ask the price of a line
			for( Keyword line : keywords) {
				if( line.getWord().contains("line") || line.getWord().matches(".*[0-9]")) { // we found a line keyword
					 ArrayList<DialogState> refs = line.getReference();
					if(inAden) {
						
                       for( DialogState ref : refs) {
                    	   if(ref.getCurrentState().name().contains("ADEN") 
   								&& ref.getCurrentState().name().contains("PRICE")) {
   							return (CanteenInfo) ref.getCurrentState();
                    	   }
                        }
					}
					// then is in Moltke
					 for( DialogState ref : refs) {
                  	   if(ref.getCurrentState().name().contains("MOLTKE") 
 								&& ref.getCurrentState().name().contains("PRICE")) {
 							return (CanteenInfo) ref.getCurrentState();
                  	   	}
                      }
					}
				}
			}
		}
		else { // the user is asking meals in lines
			for( Keyword line : keywords) {
				if( line.getWord().contains("line") || line.getWord().matches(".*[0-9]")) { // we found a line keyword
					 ArrayList<DialogState> refs = line.getReference();
					 if(inAden) {
							
	                       for( DialogState ref : refs) {
	                    	   if(ref.getCurrentState().name().contains("ADEN") 
	   								&& ref.getCurrentState().name().contains("DISH")) {
	   							return (CanteenInfo) ref.getCurrentState();
	                    	   }
	                        }
						}
					 
					 for( DialogState ref : refs) {
	                  	   if(ref.getCurrentState().name().contains("MOLTKE") 
	 								&& ref.getCurrentState().name().contains("DISH")) {
	 							return (CanteenInfo) ref.getCurrentState();
	                  	   	}
	                  }

				}
			}
		}
	if (index == -1 && (askPrice)) { // when we are here, then it's nothing matched
		return CanteenInfo.CI_TELL_MEAL_NOT_EXIST;
	}
	
	/* find the matched enum */
	next = findLineEnum(inAden, index);
		
	return next;
}


private int getRequestedWeekDay(List<Keyword> keywords, LocalDate date) {
	for (Keyword dateOfWeek : keywords) {
		if (dateOfWeek.getWord().equals("today")) return date.getDayOfWeek();
		else if (dateOfWeek.getWord().equals("tomorrow")) return date.getDayOfWeek() + 1;
		else if (dateOfWeek.getWord().equals("day after tomorrow")) return date.getDayOfWeek() + 2;
		else if (dateOfWeek.getWord().equals("sunday")) return 0;
		else if (dateOfWeek.getWord().equals("monday")) return 1;
		else if (dateOfWeek.getWord().equals("tuesday")) return 2;
		else if (dateOfWeek.getWord().equals("wednesday")) return 3;
		else if (dateOfWeek.getWord().equals("thursday")) return 4;
		else if (dateOfWeek.getWord().equals("friday")) return 5;
		else if (dateOfWeek.getWord().equals("saturday")) return 6;
	}
	
	return date.getDayOfWeek(); // if there's no keyword for time, default is "today"
}


/* it's dirty here...but I can't find a way to combin string name and Enums :( */
/**
 * Find out the enum with given index 
 * @param inAden if the current canteen in Adennaurring, then true
 * @param index the index of lines in
 * @return
 */
private CanteenInfo findLineEnum(boolean inAden, int index) {
		if(inAden) {
			switch (index) {
			case 0:
				return CanteenInfo.CI_ADEN_LINE_1_PRICE;
			case 1:
				return CanteenInfo.CI_ADEN_LINE_2_PRICE;
			case 2:
				return CanteenInfo.CI_ADEN_LINE_3_PRICE;
			case 3:
				return CanteenInfo.CI_ADEN_LINE_45_PRICE;
			case 4:
				return CanteenInfo.CI_ADEN_SCHNITBAR_PRICE;
			case 5:
				return CanteenInfo.CI_EXIT; // we don't have the enum and it doesn't that meaningful to have it
			case 6:
				return CanteenInfo.CI_ADEN_CAFE_PRICE;
			case 7:
				return CanteenInfo.CI_ADEN_CAFEABEND_PRICE;
			case 8:
				return CanteenInfo.CI_ADEN_LINE_6_PRICE;
			case 9:
				return CanteenInfo.CI_ADEN_CURRYQ_PRICE;
			}
		}else {
			switch (index) {
			case 0:
				return CanteenInfo.CI_MOLTKE_CHOICE_1_PRICE;
			case 1:
				return CanteenInfo.CI_MOLTKE_CHOICE_2_PRICE;
			case 2:
				return CanteenInfo.CI_MOLTKE_ACTTHEK_PRICE;
			case 3:
				return CanteenInfo.CI_MOLTKE_GG_PRICE;
			case 4:
				return CanteenInfo.CI_MOLTKE_BUFFET_PRICE;
			case 5:
				return CanteenInfo.CI_MOLTKE_SCHNITBAR_PRICE;
			}
		}	
	return CanteenInfo.CI_EXIT;
}

/*
public static void main(String[] args) throws WrongStateClassException {
	List<Keyword> keywords = new ArrayList<Keyword>();
	List<String> terms = new ArrayList<String>();
	List<String> approval = new ArrayList<String>();
	
	Keyword line = new Keyword(new KeywordData("line1"));
	Keyword date = new Keyword(new KeywordData("today"));
	Keyword price = new Keyword(new KeywordData("price")); 
	keywords.add(line);
	keywords.add(date);
	keywords.add(price);
	
	User user = new User();
	//session;
	Session s = new Session(user, null);
	Canteen c = new Canteen(null);
	CanteenRecommendationState cstate = new CanteenRecommendationState();
	cstate.setCurrentState(CanteenInfo.CI_ENTRY);
	CanteenRecommendationDialog dialog = new CanteenRecommendationDialog(s, cstate, c);
//	dialog.updateStateAskPreference(keywords, terms, approval);
//	dialog.updateStateEntry(keywords, terms, approval);
	dialog.updateState(keywords, terms, approval);
	
	System.out.println(dialog.getWishMeal().getMealData().getMealName());
 }*/

}