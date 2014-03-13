package dm;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.LocalDate;

import data.CanteenData;
import data.CanteenNames;
import data.LineData;
import data.MealData;

/**
 * This class represents a dialog about canteen information
 * @author Xizhe Lian, Daniel Draper
 * @version 3.0
 */
public class CanteenInformationDialog extends CanteenDialog {
	
	private String wishDate;
	
	private String wishMeal;
	
	private String location;
	
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
		this.location = "";
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



public String getLocation() {
	return location;
}


public void setLocation(String wishLoca) {
	this.location = wishLoca;
}


@Override
public void updateState(List<Keyword> keywords, List<String> terms,
		List<String> approval) throws WrongStateClassException {
	
	updateStateKeywordJump(keywords);
	/*
	if(terms.get(0).contains("where")) {
		getCurrentDialogState().setCurrentState(CanteenInfo.CI_ENTRY);
	}*/
	
	if (getCurrentDialogState().getClass() != CanteenInformationState.class || getCurrentDialogState().getCurrentState().getClass() != CanteenInfo.class) {
		throw new WrongStateClassException(getCurrentDialogState().getCurrentState().getClass().getName());
	}
	
	boolean inAden = true;
	if( currentCanteen.getCanteenData().getCanteenName()
			.equals(currentCanteen.getCanteenData().getCanteenName().MOLTKE) ||
			terms.get(0).contains("moltke")) {
		inAden = false;
	}
	
	boolean askPrice = false;
	
	
	/* find out first what the user wants to know */
	
		for( Keyword toDo : keywords ) {
			if( toDo.getWord().equals("price") ) {
				askPrice = true;
				break;
			}
		}
		
		if( !askPrice ){
				LocalDate date = LocalDate.now();
				int dateShift = 0; //0 for today, 1 for tomorrow, etc
				int requestedWeekDay = getRequestedWeekDay(keywords, date);
				
				if( (requestedWeekDay % 7) == 0 
						|| (requestedWeekDay % 7) == 6) { // 0 is Sunday, 6 is Saturday
					if( !askPrice ) {
						getCurrentDialogState().setCurrentState(CanteenInfo.CI_TELL_CANTEEN_CLOSED);
					}
				}
				
				dateShift = (requestedWeekDay - date.getDayOfWeek()) % 7;
				
				if (terms.get(0).contains("next") || terms.get(0).contains("coming")) {
					dateShift = dateShift + 7;
				} 
				
				
				if(inAden) { // so far we just consider canteen at adenauerring and moltke street
					curCanteen = new Canteen(new CanteenData(CanteenNames.ADENAUERRING, dateShift));
				   
				} else {
					curCanteen = new Canteen(new CanteenData(CanteenNames.MOLTKE, dateShift));
				}
				 super.setCurrentCanteen(curCanteen);
		}
	
	switch ((CanteenInfo)getCurrentDialogState().getCurrentState()) {
	case CI_ENTRY:
		updateStateEntry(keywords, terms, inAden, askPrice);
		break;
	case CI_ADEN_TELL_ALL_MEALS:
		generalUpdate(keywords, terms, approval, inAden, askPrice);
		break;
		
	case CI_MOLTKE_TELL_ALL_MEALS:
		generalUpdate(keywords, terms, approval, inAden, askPrice);
		break;
		
	case CI_ADEN_LINE_1_PRICE:
		generalUpdate(keywords, terms, approval, inAden, askPrice);	
		break;
	case CI_ADEN_LINE_2_PRICE:
		generalUpdate(keywords, terms, approval, inAden, askPrice);
		break;
	case CI_ADEN_LINE_3_PRICE:
		generalUpdate(keywords, terms, approval, inAden, askPrice);
		break;
	case CI_ADEN_LINE_45_PRICE:
		generalUpdate(keywords, terms, approval, inAden, askPrice);
		break;
	case CI_ADEN_LINE_1_DISH:
		generalUpdate(keywords, terms, approval, inAden, askPrice);
		break;
	case CI_ADEN_LINE_2_DISH:
		generalUpdate(keywords, terms, approval, inAden, askPrice);
		break;
	case CI_ADEN_LINE_3_DISH:
		generalUpdate(keywords, terms, approval, inAden, askPrice);
		break;
	case CI_ADEN_LINE_45_DISH:
		generalUpdate(keywords, terms, approval, inAden, askPrice);
		break;
	case CI_ADEN_LINE_6_DISH:
		generalUpdate(keywords, terms, approval, inAden, askPrice);
		break;
	case CI_ADEN_LINE_6_PRICE:
		generalUpdate(keywords, terms, approval, inAden, askPrice);
		break;
	case CI_ADEN_SCHNITBAR_PRICE:
		generalUpdate(keywords, terms, approval, inAden, askPrice);
		break;
	case CI_ADEN_SCHNITBAR_DISH:
		generalUpdate(keywords, terms, approval, inAden, askPrice);
		break;
	case CI_ADEN_CAFE_PRICE:
		generalUpdate(keywords, terms, approval, inAden, askPrice);
		break;
	case CI_ADEN_CURRYQ_PRICE:
		generalUpdate(keywords, terms, approval,inAden, askPrice);
		break;
	case CI_ADEN_CURRYQ_DISH:
		generalUpdate(keywords, terms, approval,inAden, askPrice);
		break;
	case CI_ADEN_CAFE_DISH:
		generalUpdate(keywords, terms, approval,inAden, askPrice);
		break;
	case CI_MOLTKE_CHOICE_1_PRICE:
		generalUpdate(keywords, terms, approval,inAden, askPrice);
		break;
	case CI_MOLTKE_CHOICE_1_DISH:
		generalUpdate(keywords, terms, approval,inAden, askPrice);
		break;
	case CI_MOLTKE_CHOICE_2_PRICE:
		generalUpdate(keywords, terms, approval,inAden, askPrice);
		break;
	case CI_MOLTKE_CHOICE_2_DISH:
		generalUpdate(keywords, terms, approval,inAden, askPrice);
		break;
	case CI_MOLTKE_ACTTHEK_PRICE:
		generalUpdate(keywords, terms, approval,inAden, askPrice);
		break;
	case CI_MOLTKE_ACTTHEK_DISH:
		generalUpdate(keywords, terms, approval,inAden, askPrice);
		break;
	case CI_MOLTKE_SCHNITBAR_PRICE:
		generalUpdate(keywords, terms, approval,inAden, askPrice);
		break;
	case CI_MOLTKE_SCHNITBAR_DISH:
		generalUpdate(keywords, terms, approval,inAden, askPrice);
		break;
	case CI_MOLTKE_GG_PRICE:
		generalUpdate(keywords, terms, approval,inAden, askPrice);
		break;
	case CI_MOLTKE_GG_DISH:
		generalUpdate(keywords, terms, approval,inAden, askPrice);
		break;
	case CI_MOLTKE_BUFFET_PRICE:
		generalUpdate(keywords, terms, approval,inAden, askPrice);
		break;
	case CI_MOLTKE_BUFFET_DISH:
		generalUpdate(keywords, terms, approval,inAden, askPrice);
		break;
	
	case CI_TELL_LINE_NOT_EXIST:
	//	updateStateTellNotExist(keywords, terms, approval,inAden, askPrice);
		break;
	case CI_TELL_MEAL_NOT_EXIST:
		updateStateTellNotExist(keywords, terms, approval,inAden, askPrice);
		break;
	case CI_LINE_LOCATION_INFO :
		updateStateLocation(keywords, terms);
		break;
	case  CI_EXIT:
		updateStateExit(keywords, terms);
		break;
	case CI_TELL_CANTEEN_CLOSED:
		// do nothing just output
		break;
	default:
		
		break;
	
	}
	
}

/**
 * help method to update tell location state,  in canteen adennauerring
 */
private void updateStateLocation(List<Keyword> keywords, List<String> terms) {
	// assume that we are in canteen adennauerring, others can't be implemented because of lack of infomations
	String[] lines = {"one", "two", "three", "four", "five", "six"}; 
	
	String ln = null;
	if(terms.get(0).contains("line")) {
		String[] strs = terms.get(0).split("line ");
		ln = strs[1]; // get line index
	}
	
	if(ln != null) {
		for(int i = 0 ; i < lines.length; i++) {
			if(ln.equals(lines[i])) {
				this.location = "line " + lines[i];
				break;
			}
		}
	}
	
	for( Keyword kw : keywords ) { // toString: normal string cannot be compared with json string
		if ( kw.getWord().toString().contains("cafe")
				|| kw.getWord().toString().contains("curry queen") || kw.getWord().toString().contains("schnitzelbar")) {
			this.location = kw.getWord().toString();
			break;
		}
	} 
	
	if( this.location.equals("")) {
		CanteenInformationState nextState = new CanteenInformationState();
		nextState.setCurrentState(CanteenInfo.CI_TELL_LINE_NOT_EXIST);
		setCurrentDialogState(nextState);
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
 * @param askPrice 
 */
private void generalUpdate(List<Keyword> keywords, List<String> terms,
		List<String> approval, boolean inAden, boolean askPrice) {
	 
	boolean finished = false;
	
	if( approval.size() == 1 && (approval.get(0).equals("yes"))) { 
		if( keywords == null && (terms == null)) {// user is satisfied
				CanteenInformationState next = new CanteenInformationState();
				next.setCurrentState(CanteenInfo.CI_EXIT);
				finished = true;
		} else { // user still has other requests
			CanteenInfo subState = matchSubState(keywords, terms, inAden, askPrice);
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
		CanteenInfo subState = matchSubState(keywords, terms, inAden, askPrice);
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
 * @param askPrice 
 */
private void updateStateTellNotExist(List<Keyword> keywords, List<String> terms, List<String> approval, boolean inAden, boolean askPrice) {
	
	if( approval.isEmpty()) {
		if( keywords.isEmpty() && (terms.isEmpty())) {
			DialogManager.giveDialogManager().setInErrorState(true);
			return;
		}
		
		CanteenInfo subState = matchSubState(keywords, terms, inAden, askPrice);
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
		
		CanteenInfo subState = matchSubState(keywords, terms, inAden, askPrice);
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
 * @param askPrice 
 */
private void updateStateEntry(List<Keyword> keywords, List<String> terms, boolean inAden, boolean askPrice) {
	 
	
	if(keywords.isEmpty() && ( terms.isEmpty())) {
	
		DialogManager.giveDialogManager().setInErrorState(true);
		return;
	}
	
	CanteenInfo subState = matchSubState(keywords, terms, inAden, askPrice);
	CanteenInformationState nextState = new CanteenInformationState();
	nextState.setCurrentState(subState);
	setCurrentDialogState(nextState);
}

/**
 * @author Daniel 
 * @author Xizhe
 * @see StartDialog#updateState(List, List, List);
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
			ArrayList<DialogState> possibleStates = new ArrayList<DialogState>();
			
			
			for (Keyword kw : keywords) {
				for (DialogState d : kw.getReference()) {
					if (kw.getKeywordData().getPriority() >= priorityMax) {// sometimes different keywords have the same priority
						curKW = kw;
						priorityMax = curKW.getKeywordData().getPriority();
						possibleStates.add(d);
						curRef = d;
					}
				}
			}
			
			if( priorityMax == keywords.get(0).getKeywordData().getPriority()) { 
				possibleStates.addAll(keywords.get(0).getReference());
			}
			
			if(curRef.getCurrentState().getClass() != CanteenInfo.class) {
				for( DialogState d : possibleStates) { // match a state from CI, to avoid set in error state later
					if(d.getCurrentState().getClass() == CanteenInfo.class) {
						curRef = d;
						break;
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
 * @param askPrice2 
 * @return canteenInfo
 */
private CanteenInfo matchSubState(List<Keyword> keywords, List<String> terms, boolean inAden, boolean askPrice) {
	CanteenInfo next = CanteenInfo.CI_ENTRY;
	boolean mayAskLoca = false;
	boolean existLineKW = false;
	
	Dialog prev = DialogManager.giveDialogManager().getPreviousDialog();
	// in case that the user ask dish info about a line then ask location
	if(prev.getCurrentDialogState().getCurrentState().name().contains("LINE") 
			|| prev.getCurrentDialogState().getCurrentState().name().contains("CAFE") 
			|| prev.getCurrentDialogState().getCurrentState().name().contains("CURRYQ")
			|| prev.getCurrentDialogState().getCurrentState().name().contains("SCHNITBAR")) {
		existLineKW = true;
	}
	
	for( Keyword kw : keywords ) {
		if (kw.getWord().toString().contains("where")) {
			mayAskLoca = true;
		}
		if(kw.getWord().toString().contains("line") || kw.getWord().toString().contains("cafe") 
				|| kw.getWord().toString().contains("curry queen") || kw.getWord().toString().contains("schnitzelbar")) {
			existLineKW = true;
		}
		
	}
	
	if(inAden && mayAskLoca && existLineKW) {
		updateStateLocation(keywords, terms);
		return CanteenInfo.CI_LINE_LOCATION_INFO;
	}
	
	boolean mealMatched = false;
	if( askPrice ) {
		this.curCanteen = ((CanteenDialog) DialogManager.giveDialogManager().getPreviousDialog()).getCurrentCanteen();
		super.setCurrentCanteen(curCanteen);
		
		// now to find out the required meal's name
		CanteenInfo matchedLine = mealMatched(keywords, terms, inAden);
		
		
		if( !matchedLine.equals(CanteenInfo.CI_TELL_MEAL_NOT_EXIST)) {
			mealMatched = true;
			return matchedLine;
		}
		
		
		if( !mealMatched ) { // then maybe the user ask the price of a line
			for( Keyword line : keywords) {
		
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
				}
			}
		}
		else { // the user is asking meals in lines
			
			for( Keyword line : keywords) {
				if( line.getWord().contains("canteen")) {
					if(inAden) {
						if(terms.get(0).contains("cafe")) { // sometimes "canteen" will can as keyword if we ask for cafeteria
							return CanteenInfo.CI_ADEN_CAFE_DISH;
						}
						return CanteenInfo.CI_ADEN_TELL_ALL_MEALS;
					}else {
						return CanteenInfo.CI_MOLTKE_TELL_ALL_MEALS;
					}
				}
				
				if( !line.getWord().contains("price") ) { // we found a line keyword
				
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

				} 
			}
		}
	
	
	if (!mealMatched && (askPrice)) { // when we are here, then it's nothing matched
		return CanteenInfo.CI_TELL_MEAL_NOT_EXIST;
	}
		
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
	
	
	//TreeMap<String, Integer> map = new TreeMap<String, Integer>();

	//List<MealNode> nodes = new ArrayList<MealNode>();
	
	CanteenInfo matched = CanteenInfo.CI_TELL_MEAL_NOT_EXIST;
	int id = 0;
	
	/* if the use ask a price of recommended meal */
	if( DialogManager.giveDialogManager().getPreviousDialog().getClass() == CanteenRecommendationDialog.class) {
		Dialog recom = DialogManager.giveDialogManager().getPreviousDialog();
		MealData wish = ((CanteenRecommendationDialog) recom).getWishMeal().getMealData();
		this.wishMeal = wish.getMealName();
		LineData line =  ((CanteenRecommendationDialog) recom).getWishMeal().getLineData();
		CanteenData cur = ((CanteenRecommendationDialog) recom).getWishMeal().getCanteenData();
		id = cur.getLines().indexOf(line);
		matched = findLineEnum(inAden, id);
		
	} else {
	
		String[] tms = terms.get(0).split("of "); // what's the price of ...
		if(tms.length == 1) {
			if(tms[0].contains("does")) {
				tms = terms.get(0).split("does "); // original sentence : how much does ... cost
			}else if(tms[0].contains("costs")) { // how much costs ...
				tms = terms.get(0).split("costs "); 
			}else if(tms[0].contains("is")) {// how much is...
				tms = terms.get(0).split("is "); 
			}else tms[1] = terms.get(0);
		}
		String name = tms[1];
		name = name.toLowerCase();
		/* if the name starts with empty char, match won't be succeed */
		if(name.startsWith(" ")){
			String temp = name.substring(1, name.length() - 1);
			name = temp;
		} 
		
		MealNode node = new MealNode();
		for( LineData line : curCanteen.getCanteenData().getLines()) {
			for( MealData meal : line.getTodayMeals()) {
				String str = meal.getMealName().toString().toLowerCase();
		
				if(str.contains(name)) { 
					
					node.setName(str);
					node.setMealData(meal);
					node.setLineData(line);
					//nodes.add(node);
				}
			}
		}
		
		LineData l = node.getLineData();
		id = curCanteen.getCanteenData().getLines().indexOf(l);
		
		String[] names = node.getName().split(","); // because in json data, a meal name also includes sideDishes
		this.wishMeal = names[0]; 
		matched = findLineEnum(inAden, id);
	}
	
	return matched;
	
}

/**
 * @param keywords, list of keywords
 * @param date, local time
 * @return int dateshift, how many 'days' between 'now' and wished time
 */
private int getRequestedWeekDay(List<Keyword> keywords, LocalDate date) {
	//int i = 0;
	
	for (Keyword dateOfWeek : keywords) {
		if (dateOfWeek.getWord().equals("today")) {
			this.wishDate = dateOfWeek.getWord();
			return date.getDayOfWeek();
		}
		else if (dateOfWeek.getWord().equals("tomorrow")) {
			this.wishDate = dateOfWeek.getWord();
			return  (date.getDayOfWeek() + 1);
		}
		else if (dateOfWeek.getWord().equals("day after tomorrow")) {
			this.wishDate = dateOfWeek.getWord();
			return (date.getDayOfWeek() + 2);
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

private class MealNode {
	private String name;
	private MealData md;
	private LineData l;
	
private MealNode() {}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public MealData getMealData() {
	return md;
}

public void setMealData(MealData md) {
	this.md = md;
}

public LineData getLineData() {
	return l;
}

public void setLineData(LineData l) {
	this.l = l;
}


}

/*
public static void main(String[] args) throws WrongStateClassException {
	List<Keyword> keywords = new ArrayList<Keyword>();
	List<String> terms = new ArrayList<String>();
	terms.add("The price of spaghetti napoli");
	List<String> approval = new ArrayList<String>();
	
	ArrayList<DialogState> ld = new ArrayList<DialogState>();
	DialogState s1 = new DialogState();
	s1.setCurrentState(CanteenInfo.CI_ADEN_LINE_1_PRICE);
	ld.add(s1);
	//DialogState s2 = new DialogState();
//	s2.setCurrentState(CanteenInfo.CI_ADEN_LINE_2_PRICE);
	//ld.add(s2);
	KeywordData l = new KeywordData("line1",ld , 0, null, null);
	
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