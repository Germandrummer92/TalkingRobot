package dm;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import data.CanteenData;
import data.CanteenNames;
import data.LineData;
import data.MealData;


/**
 * This Class represents a state of an Canteen Information Dialog.
 * @author Daniel Draper, Xizhe Lian
 * @version 3.0
 *
 */
public class CanteenInformationState extends DialogState {
	
    /**
     * Creates a new CanteenInformationState in the ENTRY state.
     */
    public CanteenInformationState() {
    	super();
    	setCurrentState(CanteenInfo.CI_ENTRY);
    	
    }
    
    /**
     * Creates a new CanteenInformationState in the state specified
     * @param currentState the state of the new object
     */
    public CanteenInformationState(CanteenInfo currentState) {
    	super();
    	setCurrentState(currentState);
    
    }
  /**
   * @see DialogState#getOutputKeyword()
   */
  public String getOutputKeyword() {

	   Dialog currentDialog = (CanteenInformationDialog)DialogManager.giveDialogManager().getCurrentDialog();
		   ((CanteenDialog) currentDialog).setCurrentCanteen(((CanteenInformationDialog) currentDialog).getCurCanteen());
	 
	  String time =  ((CanteenInformationDialog) currentDialog).getWishDate();
	  String wishMeal =  ((CanteenInformationDialog) currentDialog).getWishMeal();
	  String loca = ((CanteenInformationDialog) currentDialog).getLocation();
	  User user = DialogManager.giveDialogManager().getCurrentDialog().getCurrentSession().getCurrentUser();
	  
	  boolean isStudent = true;
	  if( !user.getUserData().isStudent() ) {
		  isStudent = false;
	  }
	  
	  float price;
	  String output = "";
	  
	  switch ((CanteenInfo)getCurrentState()) {
	  case CI_ENTRY:
          setQuestion(false);
          return null;
	  case CI_ADEN_TELL_ALL_MEALS :
		  
		  setQuestion(false);
		  String out = getAllMealsName( ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData());
		  return out;
	  case CI_ADEN_LINE_1_PRICE:
		  setQuestion(false);
		  ArrayList<MealData> l1meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(0).getTodayMeals();
		  
		  return generateOutputWithPrice(l1meals, 0, wishMeal, isStudent);
		  
	  case CI_ADEN_LINE_2_PRICE:
		  setQuestion(false);
		  ArrayList<MealData> l2meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(1).getTodayMeals();
		  
		  int l2index = matchMealIndex(l2meals, wishMeal);
		  
		  return generateOutputWithPrice(l2meals, l2index, wishMeal, isStudent);
		  
	  case CI_ADEN_LINE_3_PRICE:
		  setQuestion(false);
		  ArrayList<MealData> l3meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(2).getTodayMeals();
		  
		  int l3index = matchMealIndex(l3meals, wishMeal);

		  return generateOutputWithPrice(l3meals, l3index, wishMeal, isStudent);
		  
	  case CI_ADEN_LINE_45_PRICE:
		  setQuestion(false);
		  ArrayList<MealData> l4meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(3).getTodayMeals();
		  int l4index = matchMealIndex(l4meals, wishMeal);
	
		  return generateOutputWithPrice(l4meals, l4index, wishMeal, isStudent);
 	  
	  case CI_ADEN_LINE_6_PRICE:
		  setQuestion(false);
		  ArrayList<MealData> l6meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(8).getTodayMeals();
		  int l6index = matchMealIndex(l6meals, wishMeal);
	
		  return generateOutputWithPrice(l6meals, l6index, wishMeal, isStudent);
		  
	  case CI_ADEN_SCHNITBAR_PRICE:
		  setQuestion(false);
		  ArrayList<MealData> s_meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(4).getTodayMeals();
		  int s_index = matchMealIndex(s_meals, wishMeal);

		  return generateOutputWithPrice(s_meals, s_index, wishMeal, isStudent);
		 
	  case CI_ADEN_CAFE_PRICE:
		  setQuestion(false);
		  ArrayList<MealData> meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(6).getTodayMeals();
		  int index = matchMealIndex(meals, wishMeal);
	
		  return generateOutputWithPrice(meals, index, wishMeal, isStudent);
		 
	  case CI_ADEN_CAFEABEND_PRICE:
		  setQuestion(false);
		  ArrayList<MealData> abmeals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(7).getTodayMeals();
		  int abindex = matchMealIndex(abmeals, wishMeal);
	
		  return generateOutputWithPrice(abmeals, abindex, wishMeal, isStudent);
		  
	  case CI_ADEN_CURRYQ_PRICE :
		  setQuestion(false);
		  ArrayList<MealData> curry_meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(9).getTodayMeals();
		  int curry_index = matchMealIndex(curry_meals, wishMeal);
		  
		  return generateOutputWithPrice(curry_meals, curry_index, wishMeal, isStudent);
	  
	  case CI_MOLTKE_TELL_ALL_MEALS :
		  
		  setQuestion(false);
		  String out1 = getAllMealsName( ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData());
		  return out1;
		  
	  case CI_MOLTKE_CHOICE_1_PRICE:
		  
		  setQuestion(false);
		  ArrayList<MealData> c1meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(0).getTodayMeals();
		  int c1index = matchMealIndex(c1meals, wishMeal);
		  
		  return generateOutputWithPrice(c1meals, c1index, wishMeal, isStudent);
		 
	  case CI_MOLTKE_CHOICE_2_PRICE :

		  setQuestion(false);
		  ArrayList<MealData> c2meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(1).getTodayMeals();
		  int c2index = matchMealIndex(c2meals, wishMeal);
		 
		  return generateOutputWithPrice(c2meals, c2index, wishMeal, isStudent);
		  
	  case CI_MOLTKE_ACTTHEK_PRICE:

		  setQuestion(false);
		  ArrayList<MealData> ameals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(2).getTodayMeals();
		  int aindex = matchMealIndex(ameals, wishMeal);

		  return generateOutputWithPrice(ameals, aindex, wishMeal, isStudent);
		 
	  case CI_MOLTKE_SCHNITBAR_PRICE:

		  setQuestion(false);
		  ArrayList<MealData> sn_meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(5).getTodayMeals();
		  int sn_index = matchMealIndex(sn_meals, wishMeal);

		  return generateOutputWithPrice(sn_meals, sn_index, wishMeal, isStudent);

	  case CI_MOLTKE_GG_PRICE:

		  setQuestion(false);
		  ArrayList<MealData> gmeals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(3).getTodayMeals();
		  int gindex = matchMealIndex(gmeals, wishMeal);

		  return generateOutputWithPrice(gmeals, gindex, wishMeal, isStudent);
		 

	  case CI_MOLTKE_BUFFET_PRICE:
		  setQuestion(false);
		  ArrayList<MealData> bmeals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(4).getTodayMeals();
		  int bindex = matchMealIndex(bmeals, wishMeal);
		  
		  price = bmeals.get(bindex).getS_price();
		  if( wishMeal.equals("")) {
			  wishMeal = bmeals.get(0).getMealName().toString();
		  }
		  output = "<" + wishMeal +  " >";
		  output = output + ";{" + String.valueOf(price) + "}";	
		  return output;
		 
		  
	  case CI_ADEN_LINE_1_DISH :
		  setQuestion(false);
		  
			  output = packMeals(((CanteenDialog) currentDialog).getCurrentCanteen()
					  .getCanteenData().getLines().get(0).getTodayMeals());
			  return output;
		 
	  case CI_ADEN_LINE_2_DISH :
		  setQuestion(false);
		  
			  output = packMeals(((CanteenDialog) currentDialog).getCurrentCanteen()
					  .getCanteenData().getLines().get(1).getTodayMeals());
			  return output;
		  
	  case CI_ADEN_LINE_3_DISH :
		  
		  setQuestion(false);
		  
			  output = packMeals(((CanteenDialog) currentDialog).getCurrentCanteen()
					  .getCanteenData().getLines().get(2).getTodayMeals());
			  return output;
		 
	  case CI_ADEN_LINE_45_DISH :
		  setQuestion(false);
		  
			  output = packMeals(((CanteenDialog) currentDialog).getCurrentCanteen()
					  .getCanteenData().getLines().get(3).getTodayMeals());
			  return output;
		
	  case CI_ADEN_LINE_6_DISH :
		  setQuestion(false);
		  
			  output = packMeals(((CanteenDialog) currentDialog).getCurrentCanteen()
					  .getCanteenData().getLines().get(8).getTodayMeals());
			  return output;
		  
	  case CI_MOLTKE_GG_DISH:
		  setQuestion(false);
		  
			  output = packMeals(((CanteenDialog) currentDialog).getCurrentCanteen()
					  .getCanteenData().getLines().get(3).getTodayMeals());
			  return output;

	  case CI_ADEN_CURRYQ_DISH :
		  setQuestion(false);
		  
			  output = packMeals(((CanteenDialog) currentDialog).getCurrentCanteen()
					  .getCanteenData().getLines().get(9).getTodayMeals());
			  return output;
		 
	  case CI_ADEN_CAFE_DISH :
		  setQuestion(false);
		  
			  output = packMeals(((CanteenDialog) currentDialog).getCurrentCanteen()
					  .getCanteenData().getLines().get(6).getTodayMeals());
			  return output;
		  
	  case CI_ADEN_SCHNITBAR_DISH :
		  setQuestion(false);
		  
			  output = packMeals(((CanteenDialog) currentDialog).getCurrentCanteen()
					  .getCanteenData().getLines().get(4).getTodayMeals());
			  return output;
			  
	  case CI_ADEN_CAFEABEND_DISH :
		  setQuestion(false);
		  
			  output = packMeals(((CanteenDialog) currentDialog).getCurrentCanteen()
					  .getCanteenData().getLines().get(7).getTodayMeals());
			  return output;
			  
		
	  case CI_MOLTKE_CHOICE_1_DISH:
		  setQuestion(false);
		  
			  output = packMeals(((CanteenDialog) currentDialog).getCurrentCanteen()
					  .getCanteenData().getLines().get(0).getTodayMeals());
			  return output;
		  
	  case CI_MOLTKE_CHOICE_2_DISH :
		  setQuestion(false);
		  
			  output = packMeals(((CanteenDialog) currentDialog).getCurrentCanteen()
					  .getCanteenData().getLines().get(1).getTodayMeals());
			  return output;
		  
	  case CI_MOLTKE_SCHNITBAR_DISH :
		  setQuestion(false);
		  
			  output = packMeals(((CanteenDialog) currentDialog).getCurrentCanteen()
					  .getCanteenData().getLines().get(5).getTodayMeals());
			  return output;
		  
	  case CI_MOLTKE_ACTTHEK_DISH :
		  setQuestion(false);
		  
			  output = packMeals(((CanteenDialog) currentDialog).getCurrentCanteen()
					  .getCanteenData().getLines().get(2).getTodayMeals());
			  return output;
		  
	  case CI_MOLTKE_BUFFET_DISH:
		  setQuestion(false);
		  
			  output = packMeals(((CanteenDialog) currentDialog).getCurrentCanteen()
					  .getCanteenData().getLines().get(2).getTodayMeals());
			  return output;
		
	  case CI_TELL_LINE_NOT_EXIST:
		  setQuestion(false);
		  return null;
	  case CI_TELL_MEAL_NOT_EXIST:
		  setQuestion(false);
		  return null;
	  case CI_TELL_CANTEEN_CLOSED:
		  setQuestion(false);
		  
		  if(time != null) {
			  output = "{" + time + "}";
		  }else output = "";
		  
		  return output;
	  case CI_LINE_LOCATION_INFO :
		  setQuestion(false);
		  String direction = "<";
		  if( loca != null){
			  direction = direction + loca + ">"; 
			  direction = direction + ";"	+  tellLoca(loca);
		  }
		  return direction;
	  case CI_TELL_LINE_CLOSED : 
		  setQuestion(false);
		  String op = "<";
		  if( loca != null){
			  op = op + loca + ">";
		  }
		  return op;
	  case CI_EXIT :
		  setQuestion(false);
		  return null;
		  default : 
			  return null;
	  }
  
  }
 
/**
   * private method to generate the direction for lines in canteen adennauerring
   * @param lineName name of line, which location needs to be pointed
   * @return a description about location of line
   */
  private String tellLoca(String lineName) {
	String direct = "{";
	switch (lineName) {
		case "line one" : 
			direct = direct + "turn left from canteen main entrance, and use the farrest stairs to go up";
			break;
		case "line two" :
			direct = direct + "turn left from canteen main entrance, and use the nearer stairs to go up";
			break;
		case "line three":
		case "line four" :
			direct = direct + "turn right from main entrance, use the stairs on left hand side and go up";
			break;
		case "line five" :
		    direct = direct + "turn right from main entrance, use the stairs on right hand side and go up"	;
		    break;
		case "line six" :
			direct = direct + "use the entrance on right hand side from main entrance, and use the stairs there to go up";
			break;
		case "schnitzelbar" :
			direct = direct + "there's another entrance with turnstile, use the stairs before turnstile to go up,";
			direct = direct + "then enter the bigger dinning hall, schnitzel bar is on your right hand side";
			break;
		case "cafeteria" :
			direct = direct + "turn right from main entrance, go and pass by the two stairs and reach another door, ";
			direct = direct + "after enter the door and ten steps more, then is cafeteria";
			break;
		case "curry queen" :
			direct = direct + "turn left from main entrance, there is a long confusing abrupt slope to curry queen,";
			direct = direct + "moreover , the slope turned a corner";
			break;
			default: 
				direct = direct + " ";
	}

	direct = direct + "}";
	return direct;
}

/**
   * Help to generate a sentence answering with price
   * @param meals the mealData from canteen
   * @param i index of meal
   * @param wishMeal name of wished meal from user
   * @param isStudent true if user a student
   * @return a sentence that answering the requirement of asking price
   */
  private String generateOutputWithPrice(ArrayList<MealData> meals, int i, String wishMeal, boolean isStudent) {
	 
	float priceS = 0;
	float priceE = 0;
	float price;
	boolean found = false;
	if( wishMeal != null ) {
		for (MealData m : meals) {
			if( m.getMealName().toString().toLowerCase().contains(wishMeal) ) {
				priceS = m.getS_price();
				priceE = m.getE_price();
				found = true;
				break;
			}
		}
	}
	if(!found) {
		priceS = meals.get(0).getS_price();
		priceE = meals.get(0).getE_price();  // when corresponding meal not found, return the first as default
	}
	if(isStudent) {
		  price = priceS;
	} else price = priceE;
	if( wishMeal.equals("")) {
	  wishMeal = meals.get(0).getMealName().toString(); // the first meal as default
   }
   String  output = "<" + wishMeal+ ">";
   output =  output + ";{ " + String.valueOf(price) + " }";	
	return output;
}

/**
   * Get the names of all line in a canteen
   * @param canteenData
   * @return a string with all the meals name
   */
 private String getAllMealsName(CanteenData canteenData) {
	String meals = "{";
	try {
		for( LineData ld : canteenData.getLines()) {
			for( MealData md : ld.getTodayMeals()) {
				if( !ld.getTodayMeals().isEmpty()) {
					if(( ld.getLineID() == (canteenData.getLines().size() - 1)) 
							&& ( md.getMealID() == (ld.getTodayMeals().size() - 1))) {
						meals = meals.toString() + " and " + md.getMealName().toString() + " at " + ld.getLineName().toString();
					}
					meals = meals + md.getMealName().toString() + " at " + ld.getLineName().toString() + ";";
				}
			}
		}
		meals = meals + "}";
		return meals;
	} catch (NullPointerException e) {
		e.printStackTrace();
	}
	return meals;
}

 /**
  * Get the meals of a line 
  * @param meals a list of MealData of this line
  * @return a string with all meal names of a line
  */
private String packMeals(ArrayList<MealData> meals) {
	String s = "<";
	for(int i = 0; i < meals.size(); i++) {
		if( i > 0 ) {
			s = s + " and " + meals.get(i).getMealName(); 
		} else {
			s = s +  meals.get(i).getMealName();
		}
	}
	s = s + ">"; 
	return s;
}


/**
  * Helps to find out the index of wish meal
  * @param meals the list of meal data in line
  * @param wishMeal wish from user
  * @return the index of matched meal, otherwise 0 as default
  */
  private int matchMealIndex(ArrayList<MealData> meals, String wishMeal) {
	  if( wishMeal != null) {
		
		  for(MealData wish : meals) {
			  String str = wish.getMealName().toString().toLowerCase();
			  if( str.contains(wishMeal)) {
				  return meals.indexOf(wish);
			  }
		  }
		  
	  }
	return 0;
}

 /*
public static void main(String[] args) {
	  DialogState s = new CanteenInformationState();
	  s.setCurrentState(CanteenInfo.CI_MOLTKE_GG_DISH);
	 // DialogManager.giveDialogManager().getCurrentDialog();
	  DialogManager.giveDialogManager();
	 // MealData meal = new MealData("ungarisched rindergulash");
	  
	 //Dialog cd =  (CanteenDialog)DialogManager.giveDialogManager().getCurrentDialog();
	  Canteen curC = new Canteen(new CanteenData(CanteenNames.MOLTKE, 0));
	 CanteenInformationDialog c = new CanteenInformationDialog(null, s, curC);
	 // c.setWishMeal("Makkaroni mit Gem��sebolognese, auf Wunsch mit Reibek?se und Salat");
	  DialogManager.giveDialogManager().setCurrentDialog(c);
	  System.out.println(s.getOutputKeyword());
	//  System.out.println(curC.getCanteenData().getLines().get(0).getTodayMeals().get(1).getMealName());
  }*/
}