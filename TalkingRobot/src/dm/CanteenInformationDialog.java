package dm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.LocalDate;

import data.CanteenData;
import data.CanteenNames;
import data.LineData;
import data.MealData;

/**
 * This class represents a dialog about canteen information
 * @author Xizhe Lian, Daniel Draper
 * @version 1.5
 */
public class CanteenInformationDialog extends CanteenDialog {
	// TODO wishDate is not today
	private String wishDate;
	
	private String wishMeal;
	
	private Canteen curCanteen; // current canteen with wished date
	
	
/**
	 * @param session
	 * @dialogState current Dialogstate
	 * @param currentCanteen
	 */
	public CanteenInformationDialog(Session session, DialogState dialogState, Canteen currentCanteen) {
		super(session, dialogState, currentCanteen);
		//dialogState.setCurrentState(getDialogModus());
	//	super(session, new CanteenInformationState(), currentCanteen);
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
	
	updateStateKeywordJump(keywords);
	if (getCurrentDialogState().getClass() != CanteenInformationState.class || getCurrentDialogState().getCurrentState().getClass() != CanteenInfo.class) {
		throw new WrongStateClassException(getCurrentDialogState().getCurrentState().getClass().getName());
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
	 super.setCurrentCanteen(curCanteen);
	
	
	switch ((CanteenInfo)getCurrentDialogState().getCurrentState()) {
	case CI_ENTRY:
		updateStateEntry(keywords, terms, inAden);
		break;
	case CI_ADEN_TELL_ALL_MEALS:
		generalUpdate(keywords, terms, approval, inAden);
		break;
		
	case CI_MOLTKE_TELL_ALL_MEALS:
		generalUpdate(keywords, terms, approval, inAden);
		break;
		
	case CI_ADEN_LINE_1_PRICE:
		generalUpdate(keywords, terms, approval, inAden);
		break;
	case CI_ADEN_LINE_2_PRICE:
		generalUpdate(keywords, terms, approval, inAden);
		break;
	case CI_ADEN_LINE_3_PRICE:
		generalUpdate(keywords, terms, approval, inAden);
		break;
	case CI_ADEN_LINE_45_PRICE:
		generalUpdate(keywords, terms, approval, inAden);
		break;
	case CI_ADEN_LINE_1_DISH:
		generalUpdate(keywords, terms, approval, inAden);
		break;
	case CI_ADEN_LINE_2_DISH:
		generalUpdate(keywords, terms, approval, inAden);
		break;
	case CI_ADEN_LINE_3_DISH:
		generalUpdate(keywords, terms, approval, inAden);
		break;
	case CI_ADEN_LINE_45_DISH:
		generalUpdate(keywords, terms, approval, inAden);
		break;
	case CI_ADEN_LINE_6_DISH:
		generalUpdate(keywords, terms, approval, inAden);
		break;
	case CI_ADEN_LINE_6_PRICE:
		generalUpdate(keywords, terms, approval, inAden);
		break;
	case CI_ADEN_SCHNITBAR_PRICE:
		generalUpdate(keywords, terms, approval, inAden);
		break;
	case CI_ADEN_SCHNITBAR_DISH:
		generalUpdate(keywords, terms, approval, inAden);
		break;
	case CI_ADEN_CAFE_PRICE:
		generalUpdate(keywords, terms, approval, inAden);
		break;
	case CI_ADEN_CURRYQ_PRICE:
		generalUpdate(keywords, terms, approval,inAden);
		break;
	case CI_ADEN_CURRYQ_DISH:
		generalUpdate(keywords, terms, approval,inAden);
		break;
	case CI_ADEN_CAFE_DISH:
		generalUpdate(keywords, terms, approval,inAden);
		break;
	case CI_MOLTKE_CHOICE_1_PRICE:
		generalUpdate(keywords, terms, approval,inAden);
		break;
	case CI_MOLTKE_CHOICE_1_DISH:
		generalUpdate(keywords, terms, approval,inAden);
		break;
	case CI_MOLTKE_CHOICE_2_PRICE:
		generalUpdate(keywords, terms, approval,inAden);
		break;
	case CI_MOLTKE_CHOICE_2_DISH:
		generalUpdate(keywords, terms, approval,inAden);
		break;
	case CI_MOLTKE_ACTTHEK_PRICE:
		generalUpdate(keywords, terms, approval,inAden);
		break;
	case CI_MOLTKE_ACTTHEK_DISH:
		generalUpdate(keywords, terms, approval,inAden);
		break;
	case CI_MOLTKE_SCHNITBAR_PRICE:
		generalUpdate(keywords, terms, approval,inAden);
		break;
	case CI_MOLTKE_SCHNITBAR_DISH:
		generalUpdate(keywords, terms, approval,inAden);
		break;
	case CI_MOLTKE_GG_PRICE:
		generalUpdate(keywords, terms, approval,inAden);
		break;
	case CI_MOLTKE_GG_DISH:
		generalUpdate(keywords, terms, approval,inAden);
		break;
	case CI_MOLTKE_BUFFET_PRICE:
		generalUpdate(keywords, terms, approval,inAden);
		break;
	case CI_MOLTKE_BUFFET_DISH:
		generalUpdate(keywords, terms, approval,inAden);
		break;
	
	case CI_TELL_LINE_NOT_EXIST:
		updateStateTellNotExist(keywords, terms, approval,inAden);
		break;
	case CI_TELL_MEAL_NOT_EXIST:
		updateStateTellNotExist(keywords, terms, approval,inAden);
		break;
	case  CI_EXIT:
		updateStateExit(keywords, terms);
		break;
	default:
		
		break;
	
	}
	
}

public Canteen getCurCanteen() {
	return curCanteen;
}


public void setCurCanteen(Canteen curCanteen) {
	this.curCanteen = curCanteen;
}


/**
 * To update the state after give some informations
 * @param keywords list of keywords
 * @param terms list of Strings
 * @param approval list of approval
 */
private void generalUpdate(List<Keyword> keywords, List<String> terms,
		List<String> approval, boolean inAden) {
	 
	boolean finished = false;
	
	if( approval.size() == 1 && (approval.get(0).equals("yes"))) { 
		if( keywords == null && (terms == null)) {// user is satisfied
				CanteenInformationState next = new CanteenInformationState();
				next.setCurrentState(CanteenInfo.CI_EXIT);
				finished = true;
		} else { // user still has other requests
			CanteenInfo subState = matchSubState(keywords, terms, inAden);
			CanteenInformationState nextState = new CanteenInformationState();
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
		CanteenInfo subState = matchSubState(keywords, terms, inAden);
		CanteenInformationState nextState = new CanteenInformationState();
		nextState.setCurrentState(subState);
		setCurrentDialogState(nextState);
	}
	
}





private void updateStateExit(List<Keyword> keywords, List<String> terms) {
	
	CanteenInformationState next = new CanteenInformationState();
	next.setCurrentState(Start.S_USER_FOUND);
	setCurrentDialogState(next);
	
}

/**
 * update state when user is told that wished meal doesn't exist
 * @param keywords 
 * @param terms
 * @param approval
 */
private void updateStateTellNotExist(List<Keyword> keywords, List<String> terms, List<String> approval, boolean inAden) {
	
	if( approval.isEmpty()) {
		if( keywords.isEmpty() && (terms.isEmpty())) {
			DialogManager.giveDialogManager().setInErrorState(true);
			return;
		}
		
		CanteenInfo subState = matchSubState(keywords, terms, inAden);
		CanteenInformationState nextState = new CanteenInformationState();
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
		
		CanteenInfo subState = matchSubState(keywords, terms, inAden);
		CanteenInformationState nextState = new CanteenInformationState();
		nextState.setCurrentState(subState);
		setCurrentDialogState(nextState);
		return;
		
	} else { // user don't need anything from canteen info
		CanteenInformationState  next = new CanteenInformationState();
		next.setCurrentState(CanteenInfo.CI_EXIT);
		setCurrentDialogState(next);
		
	}
}




/**
 * To update the entry state
 * @param keywords list of Keyword
 * @param terms list of Strings
 */
private void updateStateEntry(List<Keyword> keywords, List<String> terms, boolean inAden) {
	 
	//boolean error = false;
	if(keywords.isEmpty() && ( terms.isEmpty())) {
		//error = true;
		DialogManager.giveDialogManager().setInErrorState(true);
		return;
	}
	
	CanteenInfo subState = matchSubState(keywords, terms, inAden);
	CanteenInformationState nextState = new CanteenInformationState();
	nextState.setCurrentState(subState);
	setCurrentDialogState(nextState);
}

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
 * This method helps to match substate 
 * @param keywords list of keyword
 * @param terms list of string
 * @return canteenInfo
 */
private CanteenInfo matchSubState(List<Keyword> keywords, List<String> terms, boolean inAden) {
	CanteenInfo next = CanteenInfo.CI_ENTRY;
	boolean askPrice = false;
	
	
	/* find out first what the user wants to know */
	
		for( Keyword toDo : keywords ) {
			if( toDo.getWord().equals("price")) {
				askPrice = true;
				break;
			}
		}
	
	
	
	//int index = -1;
		boolean mealMatched = false;
	if( askPrice ) {
		// now to find out the required meal's name
		
		CanteenInfo matchedLine = mealMatched(keywords, terms, inAden);
		if( !matchedLine.equals(CanteenInfo.CI_TELL_MEAL_NOT_EXIST)) {
			mealMatched = true;
			return matchedLine;
		}
		/*for( String name : terms) {
			for( LineData line : curCanteen.getCanteenData().getLines()) {
				for( MealData meal : line.getTodayMeals()) {
					Pattern p = Pattern.compile(name); // for comparing with json data
					Matcher m = p.matcher(meal.getMealName());
					if(m.find()) {
					//	meal.getMealName().equals(name);
						index = curCanteen.getCanteenData().getLines().indexOf(line);
						//next = getLineEnum(line, askPrice);
						setWishMeal(name);
						break;
					}
				}
			}
			
		}*/
		
		if( !mealMatched ) { // then maybe the user ask the price of a line
			for( Keyword line : keywords) {
				//if( line.getWord().contains("line")) { // we found a line keyword
					//List<DialogState> refs = new ArrayList<DialogState>();
										//refs = line.getReference();
					if(inAden) {
						
                       for( DialogState ref : line.getReference()) {
                    	   if(ref.getCurrentState().name().contains("ADEN") 
   								&& ref.getCurrentState().name().contains("PRICE")) {
   							return (CanteenInfo) ref.getCurrentState();
                    	   }
                        }
					}
					// then is in Moltke
					 for( DialogState ref : line.getReference()) {
                  	   if(ref.getCurrentState().name().contains("MOLTKE") 
 								&& ref.getCurrentState().name().contains("PRICE")) {
 							return (CanteenInfo) ref.getCurrentState();
                  	   	}
                      }
				//	}
				
				}
			}
		}
		else { // the user is asking meals in lines
			
			for( Keyword line : keywords) {
				if( line.getWord().contains("canteen")) {
					if(inAden) {
						return CanteenInfo.CI_ADEN_TELL_ALL_MEALS;
					}else {
						return CanteenInfo.CI_MOLTKE_TELL_ALL_MEALS;
					}
				}
				
				if( !line.getWord().contains("price") ) { // we found a line keyword
					// ArrayList<DialogState> refs = line.getReference();
					 if(inAden) {
							
	                       for( DialogState ref : line.getReference()) {
	                    	   if(ref.getCurrentState().name().contains("ADEN") 
	   								&& ref.getCurrentState().name().contains("DISH")) {
	   							return (CanteenInfo) ref.getCurrentState();
	                    	   }
	                        }
						}
					 
					 for( DialogState ref : line.getReference()) {
	                  	   if(ref.getCurrentState().name().contains("MOLTKE") 
	 								&& ref.getCurrentState().name().contains("DISH")) {
	 							return (CanteenInfo) ref.getCurrentState();
	                  	   	}
	                  }

				} //else { // the user ask the meals in whole canteen
					
				//}
			}
		}
	
	
	if (!mealMatched && (askPrice)) { // when we are here, then it's nothing matched
		return CanteenInfo.CI_TELL_MEAL_NOT_EXIST;
	}
	
	/* find the matched enum */ // do we need it?
	//next = findLineEnum(inAden, index);
		
	return next;
}

/**
 * Helps to match a wish meal from user 
 * @param keywords list of keywords
 * @param terms list of terms
 * @param inAden boolean, if current is in canteen Adennaurring, then it's true
 * @return enum of CanteenInfo
 */ 
private CanteenInfo mealMatched(List<Keyword> keywords, List<String> terms, boolean inAden) {
	
	//boolean matched = false;
	TreeMap<MealData, Integer> map = new TreeMap<MealData, Integer>();
	Map<MealData, LineData> meals = new HashMap<MealData, LineData>();
	
	CanteenInfo matched = CanteenInfo.CI_TELL_MEAL_NOT_EXIST;
	Integer id = 0;
	for(String mealName : terms) {
		String[] tms = mealName.split(" ");
		for( int i = tms.length -1; i >= 0; i--) {
			for( LineData line : curCanteen.getCanteenData().getLines()) {
				for( MealData meal : line.getTodayMeals()) {
					String str = meal.getMealName().toString().toLowerCase();
					
					if(str.contains(tms[i])) { // go through the terms and analyse which is the most possible meal
						Integer value = new Integer(1);
						//FIXME mealData is not compareble...
						if(map.containsKey(meal)) {
							value = map.get(meal);
							value ++;
							map.remove(str);
						}
						map.put(meal, value);
						meals.put(meal, line);
					}
				}
			}
		}
		
		
	}
	MealData meal = map.lastKey();
	LineData l = meals.get(meal);
	id = curCanteen.getCanteenData().getLines().indexOf(l);
	String str = meal.getMealName().toString().toLowerCase();
	String[] names = str.split(","); // because in json data, a meal name also includes sideDishes
	this.wishMeal = names[0]; 
	matched = findLineEnum(inAden, id);
	return matched;
	
}


private int getRequestedWeekDay(List<Keyword> keywords, LocalDate date) {
	//int i = 0;
	for (Keyword dateOfWeek : keywords) {
		if (dateOfWeek.getWord().equals("today")) {
			this.wishDate = dateOfWeek.getWord();
			return date.getDayOfWeek();
		}
		else if (dateOfWeek.getWord().equals("tomorrow")) {
			this.wishDate = dateOfWeek.getWord();
			return  date.getDayOfWeek() + 1;
		}
		else if (dateOfWeek.getWord().equals("day after tomorrow")) {
			this.wishDate = dateOfWeek.getWord();
			return date.getDayOfWeek() + 2;
		}
		else if (dateOfWeek.getWord().equals("sunday")) {
			this.wishDate = "on sunday";
			return 0;
		}
		else if (dateOfWeek.getWord().equals("monday")) {
			this.wishDate = "on monday";
			return 1;
		}
		else if (dateOfWeek.getWord().equals("tuesday")) {
			this.wishDate = "on tuesday";
			return 2;
		}
		else if (dateOfWeek.getWord().equals("wednesday")) {
			this.wishDate = "on wednesday";
			return 3;
		}
		else if (dateOfWeek.getWord().equals("thursday")) {
			this.wishDate = "on thursday";
			return 4;
		}
		else if (dateOfWeek.getWord().equals("friday")) {
			this.wishDate = "on friday";
			return 5;
		}
		else if (dateOfWeek.getWord().equals("saturday")) {
			this.wishDate = "on saturday";
			return 6;
		}
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
				if(wishMeal.equals("")) {
					this.wishMeal = curCanteen.getCanteenData().getLines().get(0).getLineName().toString();
				}
				return CanteenInfo.CI_ADEN_LINE_1_PRICE;
			case 1:
				if(wishMeal.equals("")) {
					this.wishMeal = curCanteen.getCanteenData().getLines().get(1).getLineName().toString();
				}
				return CanteenInfo.CI_ADEN_LINE_2_PRICE;
			case 2:
				if(wishMeal.equals("")) {
					this.wishMeal = curCanteen.getCanteenData().getLines().get(2).getLineName().toString();
				}
				return CanteenInfo.CI_ADEN_LINE_3_PRICE;
			case 3:
				if(wishMeal.equals("")) {
					this.wishMeal = curCanteen.getCanteenData().getLines().get(3).getLineName().toString();
				}
				return CanteenInfo.CI_ADEN_LINE_45_PRICE;
			case 4:
				if(wishMeal.equals("")) {
					this.wishMeal = curCanteen.getCanteenData().getLines().get(4).getLineName().toString();
				}
				return CanteenInfo.CI_ADEN_SCHNITBAR_PRICE;
			case 5:
				if(wishMeal.equals("")) {
					this.wishMeal = curCanteen.getCanteenData().getLines().get(5).getLineName().toString();
				}
				return CanteenInfo.CI_EXIT; // we don't have the enum and it doesn't that meaningful to have it
			case 6:
				if(wishMeal.equals("")) {
					this.wishMeal = curCanteen.getCanteenData().getLines().get(6).getLineName().toString();
				}
				return CanteenInfo.CI_ADEN_CAFE_PRICE;
			case 7:
				if(wishMeal.equals("")) {
					this.wishMeal = curCanteen.getCanteenData().getLines().get(7).getLineName().toString();
				}
				return CanteenInfo.CI_ADEN_CAFEABEND_PRICE;
			case 8:
				if(wishMeal.equals("")) {
					this.wishMeal = curCanteen.getCanteenData().getLines().get(8).getLineName().toString();
				}
				return CanteenInfo.CI_ADEN_LINE_6_PRICE;
			case 9:
				if(wishMeal.equals("")) {
					this.wishMeal = curCanteen.getCanteenData().getLines().get(9).getLineName().toString();
				}
				return CanteenInfo.CI_ADEN_CURRYQ_PRICE;
			}
		}else {
			switch (index) {
			case 0:
				if(wishMeal.equals("")) {
					this.wishMeal = curCanteen.getCanteenData().getLines().get(0).getLineName().toString();
				}
				return CanteenInfo.CI_MOLTKE_CHOICE_1_PRICE;
			case 1:
				if(wishMeal.equals("")) {
					this.wishMeal = curCanteen.getCanteenData().getLines().get(1).getLineName().toString();
				}
				return CanteenInfo.CI_MOLTKE_CHOICE_2_PRICE;
			case 2:
				if(wishMeal.equals("")) {
					this.wishMeal = curCanteen.getCanteenData().getLines().get(2).getLineName().toString();
				}
				return CanteenInfo.CI_MOLTKE_ACTTHEK_PRICE;
			case 3:
				if(wishMeal.equals("")) {
					this.wishMeal = curCanteen.getCanteenData().getLines().get(3).getLineName().toString();
				}
				return CanteenInfo.CI_MOLTKE_GG_PRICE;
			case 4:
				if(wishMeal.equals("")) {
					this.wishMeal = curCanteen.getCanteenData().getLines().get(4).getLineName().toString();
				}
				return CanteenInfo.CI_MOLTKE_BUFFET_PRICE;
			case 5:
				if(wishMeal.equals("")) {
					this.wishMeal = curCanteen.getCanteenData().getLines().get(5).getLineName().toString();
				}
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
	
	ArrayList<DialogState> ld = new ArrayList<DialogState>();
	DialogState s1 = new DialogState();
	s1.setCurrentState(CanteenInfo.CI_ADEN_LINE_2_DISH);
	ld.add(s1);
	DialogState s2 = new DialogState();
	s2.setCurrentState(CanteenInfo.CI_ADEN_LINE_2_PRICE);
	ld.add(s2);
	KeywordData l = new KeywordData("line2",ld , 0, null, null);
	
	Keyword line = new Keyword(l);
	
	KeywordData d = new KeywordData("today", null, 0, null, null);
	Keyword date = new Keyword(d);
	KeywordData p = new KeywordData("price", null, 0, null, null);
	Keyword price = new Keyword(p); 
	keywords.add(line);
	keywords.add(date);
	keywords.add(price);
	
	User user = new User();
	//session;
	Robot r = new Robot("L", false);
	Session s = new Session(user, r);
	CanteenData cd = new CanteenData(CanteenNames.ADENAUERRING, 0);
	Canteen c = new Canteen(cd);
	//List<LineData> ls = new ArrayList<LineData>();
	//LineData ll = new LineData("line1");
	//ls.add(ll);
	//cd.setLines((ArrayList<LineData>) ls);
	CanteenInformationState cstate = new CanteenInformationState();
	cstate.setCurrentState(CanteenInfo.CI_ENTRY);
	CanteenInformationDialog dialog = new CanteenInformationDialog(s, cstate, c);
//	dialog.updateStateAskPreference(keywords, terms, approval);
//	dialog.updateStateEntry(keywords, terms, approval);
	dialog.updateState(keywords, terms, approval);
	
	System.out.println(dialog.getCurrentDialogState().getCurrentState());
	
	System.out.println(dialog.getCurrentDialogState().getOutputKeyword());
 }*/

}