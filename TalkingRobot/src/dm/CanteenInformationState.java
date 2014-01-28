package dm;

import java.util.ArrayList;

import data.MealData;


/**
 * This Class represents a state of an Canteen Information Dialog.
 * @author Daniel Draper, Xizhe Lian
 * @version 1.0
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
	  Dialog currentDialog =(CanteenRecommendationDialog)DialogManager.giveDialogManager().getCurrentDialog();
	  String time = ((CanteenRecommendationDialog) currentDialog).getWishDate();
	  String wishMeal = ((CanteenRecommendationDialog) currentDialog).getWishmeal();
	  String output;
	  float price;
	  switch ((CanteenInfo)getCurrentState()) {
	  case CI_ENTRY:
          setQuestion(false);
          return null;
	  case CI_ADEN_LINE_1_PRICE:
		  setQuestion(false);
		  price = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(0).getTodayMeals().get(0).getPrice();
		  output = "<" + wishMeal+ ">";
		  output = output + " ,{ " + String.valueOf(price) + " }";	
		  return output;
	  case CI_ADEN_LINE_2_PRICE:
		  setQuestion(false);
		  price = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(1).getTodayMeals().get(0).getPrice();
		  output = "<" + wishMeal + ">";
		  output = output + " ,{" + String.valueOf(price) + "}";	
		  return output;
	  case CI_ADEN_LINE_3_PRICE:
		  setQuestion(false);
		  price = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(2).getTodayMeals().get(0).getPrice();
		  //FIXME wrong format
		  output = "<" + String.valueOf(price) + ">";
		  output = output + " ,{" + time + "}";	
		  return output;
	  case CI_ADEN_LINE_45_PRICE:
		  setQuestion(false);
		  price = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(3).getTodayMeals().get(0).getPrice();
		  output = "<" + String.valueOf(price) + ">";
		  output = output + " ,{" + time + "}";	
		  return output;
	  case CI_ADEN_LINE_1_DISH:
		  setQuestion(false);
		  output = "<" + ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(0).getTodayMeals().toString() + ">";
		  output = output + " ,{" + time + "}";	
		  return output;
	  case CI_ADEN_LINE_2_DISH:
		  setQuestion(false);
		  output = "<" + ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(1).getTodayMeals().toString() + ">";
		  output = output + " ,{" + time + "}";	
		  return output;
	  case CI_ADEN_LINE_3_DISH:
		  setQuestion(false);
		  output = "<" + ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(2).getTodayMeals().toString() + ">";
		  output = output + " ,{" + time + "}";	
		  return output;
	  case CI_ADEN_LINE_45_DISH:
		  setQuestion(false);
		  output = "<" + ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(3).getTodayMeals().toString() + ">";
		  output = output + " ,{" + time + "}";	
		  return output;
	  case CI_ADEN_LINE_6_DISH:
          //FIXME 
	  case CI_ADEN_LINE_6_PRICE:
		  //FIXME
	  case CI_ADEN_SCHNITBAR_PRICE:
		  setQuestion(false);
		  price = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(4).getTodayMeals().get(0).getPrice();
		  output = "<" + String.valueOf(price) + ">";
		  output = output + " ,{" + time + "}";	
		  return output;
	  case CI_ADEN_SCHNITBAR_DISH:
		  setQuestion(false);
		  output = "<" + ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(4).getTodayMeals().toString() + ">";
		  output = output + " ,{" + time + "}";	
		  return output;
	  case CI_ADEN_CAFE_PRICE:
		  setQuestion(false);
		  ArrayList<MealData> meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(6).getTodayMeals();
		  int index = -1;
		  for(MealData wish : meals) {
			  if(wish.getMealName().equals(wishMeal)) {
				  index = meals.indexOf(wish);
				  break;
			  }
		  }
		  price = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(5).getTodayMeals().get(index).getPrice();
		  output = "< " + String.valueOf(price) + " for " + wishMeal+  " >";
		  output = output + " ,{" + time + "}";	
		  return output;
	  case CI_ADEN_CURRYQ_PRICE:
	  
	  case CI_ADEN_CURRYQ_DISH:
	  
	  case CI_ADEN_CAFE_DISH:

	  case CI_MOLTKE_CHOICE_1_PRICE:

	  case CI_MOLTKE_CHOICE_1_DISH:

	  case CI_MOLTKE_CHOICE_2_PRICE:

	  case CI_MOLTKE_CHOICE_2_DISH:

	  case CI_MOLTKE_ACTTHEK_PRICE:

	  case CI_MOLTKE_ACTTHEK_DISH:

	  case CI_MOLTKE_SCHNITBAR_PRICE:

	  case CI_MOLTKE_SCHNITBAR_DISH:

	  case CI_MOLTKE_GG_PRICE:

	  case CI_MOLTKE_GG_DISH:

	  case CI_MOLTKE_BUFFET_PRICE:

	  case CI_MOLTKE_BUFFET_DISH:

	  case CI_TELL_LINE_NOT_EXIST:

	  case CI_TELL_MEAL_NOT_EXIST:

	  case CI_EXIT:
	  }
  return null;
  }

}