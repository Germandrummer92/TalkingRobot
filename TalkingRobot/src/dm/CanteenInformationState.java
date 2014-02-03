package dm;

import java.util.ArrayList;

import data.MealData;


/**
 * This Class represents a state of an Canteen Information Dialog.
 * @author Daniel Draper, Xizhe Lian
 * @version 1.5
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
	  String time = ((CanteenInformationDialog) currentDialog).getWishDate();
	  String wishMeal = ((CanteenInformationDialog) currentDialog).getWishMeal();
	  String output = "";
	  float price;
	  
	  switch ((CanteenInfo)getCurrentState()) {
	  case CI_ENTRY:
          setQuestion(false);
          return output;
	  case CI_ADEN_LINE_1_PRICE:
		  setQuestion(false);
		  ArrayList<MealData> l1meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(0).getTodayMeals();
		  int l1index = 0;
		  for(MealData wish : l1meals) {
			  if(wish.getMealName().equals(wishMeal)) {
				  l1index = l1meals.indexOf(wish);
				  break;
			  }
		  }
		  
		  price = l1meals.get(l1index).getPrice();
		  output = "<" + wishMeal+ ">";
		  output = output + " ,{ " + String.valueOf(price) + " }";	
		  return output;
		  
	  case CI_ADEN_LINE_2_PRICE:
		  setQuestion(false);
		  ArrayList<MealData> l2meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(1).getTodayMeals();
		  int l2index = 0;
		  for(MealData wish : l2meals) {
			  if(wish.getMealName().equals(wishMeal)) {
				  l1index = l2meals.indexOf(wish);
				  break;
			  }
		  }
		  price = l2meals.get(l2index).getPrice();
		  output = "< " + wishMeal + " >";
		  output = output + " ,{ " + String.valueOf(price) + " }";	
		  return output;
		  
	  case CI_ADEN_LINE_3_PRICE:
		  setQuestion(false);
		  ArrayList<MealData> l3meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(2).getTodayMeals();
		  int l3index = 0;
		  for(MealData wish : l3meals) {
			  if(wish.getMealName().equals(wishMeal)) {
				  l1index = l3meals.indexOf(wish);
				  break;
			  }
		  }
		  price = l3meals.get(l3index).getPrice();
		  output = "< " + wishMeal + " >";
		  output = output + " ,{ " + String.valueOf(price) + " }";	
		  return output;
		  
	  case CI_ADEN_LINE_45_PRICE:
		  setQuestion(false);
		  ArrayList<MealData> l4meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(3).getTodayMeals();
		  int l4index = 0;
		  for(MealData wish : l4meals) {
			  if(wish.getMealName().equals(wishMeal)) {
				  l1index = l4meals.indexOf(wish);
				  break;
			  }
		  }
		  price = l4meals.get(l4index).getPrice();
		  output = "< " + wishMeal + " >";
		  output = output + " ,{ " + String.valueOf(price) + " }";	
		  return output;
	  	  
	  case CI_ADEN_LINE_6_PRICE:
		  setQuestion(false);
		  ArrayList<MealData> l6meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(8).getTodayMeals();
		  int l6index = 0;
		  for(MealData wish : l6meals) {
			  if(wish.getMealName().equals(wishMeal)) {
				  l1index = l6meals.indexOf(wish);
				  break;
			  }
		  }
		  price = l6meals.get(l6index).getPrice();
		  output = "< " + wishMeal + " >";
		  output = output + " ,{ " + String.valueOf(price) + " }";	
		  return output; 
		  
	  case CI_ADEN_SCHNITBAR_PRICE:
		  setQuestion(false);
		  ArrayList<MealData> s_meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(4).getTodayMeals();
		  int s_index = 0;
		  for(MealData wish : s_meals) {
			  if(wish.getMealName().equals(wishMeal)) {
				  l1index = s_meals.indexOf(wish);
				  break;
			  }
		  }
		  price = s_meals.get(s_index).getPrice();
		  output = "<" + wishMeal + ">";
		  output = output + " ,{" + String.valueOf(price) + "}";	
		  return output;
		 
	  case CI_ADEN_CAFE_PRICE:
		  setQuestion(false);
		  ArrayList<MealData> meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(6).getTodayMeals();
		  int index = 0;
		  for(MealData wish : meals) {
			  if(wish.getMealName().equals(wishMeal)) {
				  index = meals.indexOf(wish);
				  break;
			  }
		  }
		  price = meals.get(index).getPrice();
		  output = "< " + wishMeal+  " >";
		  output = output + " ,{" + String.valueOf(price) + "}";	
		  return output;
		  
	  case CI_ADEN_CURRYQ_PRICE:
		  setQuestion(false);
		  ArrayList<MealData> curry_meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(6).getTodayMeals();
		  int curry_index = 0;
		  for(MealData wish : curry_meals) {
			  if(wish.getMealName().equals(wishMeal)) {
				  index = curry_meals.indexOf(wish);
				  break;
			  }
		  }
		  price = curry_meals.get(curry_index).getPrice();
		  output = "< " + wishMeal +  " >";
		  output = output + " ,{" + String.valueOf(price) + "}";	
		  return output;
	  
	  case CI_MOLTKE_CHOICE_1_PRICE:
		  
		  setQuestion(false);
		  ArrayList<MealData> c1meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(0).getTodayMeals();
		  int c1index = 0;
		  for(MealData wish : c1meals) {
			  if(wish.getMealName().equals(wishMeal)) {
				  index = c1meals.indexOf(wish);
				  break;
			  }
		  }
		  price = c1meals.get(c1index).getPrice();
		  output = "< " + wishMeal + " >";
		  output = output + " ,{" + String.valueOf(price) + "}";	
		  return output;
		 
	  case CI_MOLTKE_CHOICE_2_PRICE:

		  setQuestion(false);
		  ArrayList<MealData> c2meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(1).getTodayMeals();
		  int c2index = 0;
		  for(MealData wish : c2meals) {
			  if(wish.getMealName().equals(wishMeal)) {
				  index = c2meals.indexOf(wish);
				  break;
			  }
		  }
		  price = c2meals.get(c2index).getPrice();
		  output = "< " + wishMeal +  " >";
		  output = output + " ,{" + String.valueOf(price) + "}";	
		  return output;
		  
	  case CI_MOLTKE_ACTTHEK_PRICE:

		  setQuestion(false);
		  ArrayList<MealData> ameals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(2).getTodayMeals();
		  int aindex = 0;
		  for(MealData wish : ameals) {
			  if(wish.getMealName().equals(wishMeal)) {
				  index = ameals.indexOf(wish);
				  break;
			  }
		  }
		  price = ameals.get(aindex).getPrice();
		  output = "< " + wishMeal +  " >";
		  output = output + " ,{" + String.valueOf(price) + "}";	
		  return output;
		 
	  case CI_MOLTKE_SCHNITBAR_PRICE:

		  setQuestion(false);
		  ArrayList<MealData> sn_meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(5).getTodayMeals();
		  int sn_index = 0;
		  for(MealData wish : sn_meals) {
			  if(wish.getMealName().equals(wishMeal)) {
				  index = sn_meals.indexOf(wish);
				  break;
			  }
		  }
		  price = sn_meals.get(sn_index).getPrice();
		  output = "< " + wishMeal +  " >";
		  output = output + " ,{" + String.valueOf(price) + "}";	
		  return output;

	  case CI_MOLTKE_GG_PRICE:

		  setQuestion(false);
		  ArrayList<MealData> gmeals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(3).getTodayMeals();
		  int gindex = 0;
		  for(MealData wish : gmeals) {
			  if(wish.getMealName().equals(wishMeal)) {
				  index = gmeals.indexOf(wish);
				  break;
			  }
		  }
		  price = gmeals.get(gindex).getPrice();
		  output = "< " + wishMeal +  " >";
		  output = output + " ,{" + String.valueOf(price) + "}";	
		  return output;
		 

	  case CI_MOLTKE_BUFFET_PRICE:
		  setQuestion(false);
		  ArrayList<MealData> bmeals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(4).getTodayMeals();
		  int bindex = 0;
		  for(MealData wish : bmeals) {
			  if(wish.getMealName().equals(wishMeal)) {
				  index = bmeals.indexOf(wish);
				  break;
			  }
		  }
		  price = bmeals.get(bindex).getPrice();
		  output = "< " + wishMeal +  " >";
		  output = output + " ,{" + String.valueOf(price) + "}";	
		  return output;
		 
		  
	  case CI_ADEN_LINE_1_DISH:
			 
	  case CI_ADEN_LINE_2_DISH:
		  
	  case CI_ADEN_LINE_3_DISH:
		 
	  case CI_ADEN_LINE_45_DISH:
		
	  case CI_ADEN_LINE_6_DISH:
		  
	  case CI_MOLTKE_GG_DISH:
		  
	  case CI_ADEN_CURRYQ_DISH:
			
	  case CI_ADEN_CAFE_DISH:
		  
	  case CI_ADEN_SCHNITBAR_DISH:
		  
	  case CI_MOLTKE_CHOICE_1_DISH:
		  
	  case CI_MOLTKE_CHOICE_2_DISH:
		  
	  case CI_MOLTKE_SCHNITBAR_DISH:
		  
	  case CI_MOLTKE_ACTTHEK_DISH:
		  
	  case CI_MOLTKE_BUFFET_DISH:
		  setQuestion(false);
		  output = "<" + wishMeal + ">";
		  output = output + " ,{" + time + "}";	
		  return output;
	  case CI_TELL_LINE_NOT_EXIST:
		  setQuestion(false);
		  return null;
	  case CI_TELL_MEAL_NOT_EXIST:
		  setQuestion(false);
		  return null;
	  case CI_EXIT:
		  setQuestion(false);
		  return null;
	  }
  return null;
  }

}