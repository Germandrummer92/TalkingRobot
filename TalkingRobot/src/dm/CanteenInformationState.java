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
 * @version 1.5
 *
 */
public class CanteenInformationState extends DialogState {
	//private String output;
 

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
	 // currentDialog.setCurrentDialogState(new CanteenInformationDialog(null, null, null));
	  String time =  ((CanteenInformationDialog) currentDialog).getWishDate();
	  String wishMeal =  ((CanteenInformationDialog) currentDialog).getWishMeal();

	  float price;
	  
	  switch (currentDialog.getCurrentDialogState().getCurrentState().toString()) {
	  case "CI_ENTRY":
          setQuestion(false);
          return null;
	  case "CI_ADEN_TELL_ALL_MEALS" :
		  //TODO
		  setQuestion(false);
		  String out = "{" + getAllMealsName( ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData()) + "}";
		  return out;
	  case "CI_ADEN_LINE_1_PRICE":
		  setQuestion(false);
		  ArrayList<MealData> l1meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(0).getTodayMeals();
		  
		 // int l1index = matchMealIndex(l1meals,wishMeal);
		  
		  price = l1meals.get(0).getPrice(); // at line one, all price are the same
		  String  output = "<" + wishMeal+ ">";
		  output =  output + " ,{ " + String.valueOf(price) + " }";	
		  return output;
		  
	  case "CI_ADEN_LINE_2_PRICE":
		  setQuestion(false);
		  ArrayList<MealData> l2meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(1).getTodayMeals();
		  
		  int l2index = matchMealIndex(l2meals, wishMeal);
		  
		  price = l2meals.get(l2index).getPrice();
		  output = "<" + wishMeal + ">";
		  String output1 = output + ",{" + String.valueOf(price) + "}";	
		  return output1;
		  
	  case "CI_ADEN_LINE_3_PRICE":
		  setQuestion(false);
		  ArrayList<MealData> l3meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(2).getTodayMeals();
		  int l3index = matchMealIndex(l3meals, wishMeal);
		  
		  price = l3meals.get(l3index).getPrice();
		  output = "<" + wishMeal + ">";
		  output =  output + ",{" + String.valueOf(price) + "}";	
		  return output;
		  
	  case "CI_ADEN_LINE_45_PRICE":
		  setQuestion(false);
		  ArrayList<MealData> l4meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(3).getTodayMeals();
		  int l4index = matchMealIndex(l4meals, wishMeal);
		  
		  price = l4meals.get(l4index).getPrice();
		  output = "<" + wishMeal + ">";
		  output = output + ",{" + String.valueOf(price) + "}";	
		  return output;
 	  
	  case "CI_ADEN_LINE_6_PRICE":
		  setQuestion(false);
		  ArrayList<MealData> l6meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(8).getTodayMeals();
		  int l6index = matchMealIndex(l6meals, wishMeal);
		  
		  price = l6meals.get(l6index).getPrice();
		  output = "<" + wishMeal + ">";
		  output = output + ",{" + String.valueOf(price) + "}";	
		  return output; 
		  
	  case "CI_ADEN_SCHNITBAR_PRICE":
		  setQuestion(false);
		  ArrayList<MealData> s_meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(4).getTodayMeals();
		  int s_index = matchMealIndex(s_meals, wishMeal);
		  
		  price = s_meals.get(s_index).getPrice();
		  output = "<" + wishMeal + ">";
		  output = output + ",{" + String.valueOf(price) + "}";	
		  return output;
		 
	  case "CI_ADEN_CAFE_PRICE":
		  setQuestion(false);
		  ArrayList<MealData> meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(6).getTodayMeals();
		  int index = matchMealIndex(meals, wishMeal);
		  
		  price = meals.get(index).getPrice();
		  output = "<" + wishMeal+  ">";
		  output = output + ",{" + String.valueOf(price) + "}";	
		  return output;
		  
	  case "CI_ADEN_CURRYQ_PRICE" :
		  setQuestion(false);
		  ArrayList<MealData> curry_meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(6).getTodayMeals();
		  int curry_index = matchMealIndex(curry_meals, wishMeal);
		  
		  price = curry_meals.get(curry_index).getPrice();
		  output = "<" + wishMeal +  ">";
		  output = output + ",{" + String.valueOf(price) + "}";	
		  return output;
	  
	  case "CI_MOLTKE_TELL_ALL_MEALS" :
		  //TODO
		  setQuestion(false);
		  String out1 = "{" + getAllMealsName( ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData()) + "}";
		  return out1;
		  
	  case "CI_MOLTKE_CHOICE_1_PRICE":
		  
		  setQuestion(false);
		  ArrayList<MealData> c1meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(0).getTodayMeals();
		  int c1index = matchMealIndex(c1meals, wishMeal);
		  
		  price = c1meals.get(c1index).getPrice();
		  output = "<" + wishMeal + " >";
		  output = output + ",{" + String.valueOf(price) + "}";	
		  return output;
		 
	  case "CI_MOLTKE_CHOICE_2_PRICE" :

		  setQuestion(false);
		  ArrayList<MealData> c2meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(1).getTodayMeals();
		  int c2index = matchMealIndex(c2meals, wishMeal);
		 
		  price = c2meals.get(c2index).getPrice();
		  output = "<" + wishMeal +  ">";
		  output = output + ",{" + String.valueOf(price) + "}";	
		  return output;
		  
	  case "CI_MOLTKE_ACTTHEK_PRICE":

		  setQuestion(false);
		  ArrayList<MealData> ameals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(2).getTodayMeals();
		  int aindex = matchMealIndex(ameals, wishMeal);

		  price = ameals.get(aindex).getPrice();
		  output = "<" + wishMeal +  ">";
		  output = output + ",{" + String.valueOf(price) + "}";	
		  return output;
		 
	  case "CI_MOLTKE_SCHNITBAR_PRICE":

		  setQuestion(false);
		  ArrayList<MealData> sn_meals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(5).getTodayMeals();
		  int sn_index = matchMealIndex(sn_meals, wishMeal);

		  price = sn_meals.get(sn_index).getPrice();
		  output = "<" + wishMeal +  ">";
		  output = output + ",{" + String.valueOf(price) + "}";	
		  return output;

	  case "CI_MOLTKE_GG_PRICE":

		  setQuestion(false);
		  ArrayList<MealData> gmeals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(3).getTodayMeals();
		  int gindex = matchMealIndex(gmeals, wishMeal);

		  price = gmeals.get(gindex).getPrice();
		  output = "<" + wishMeal +  ">";
		  output = output + ",{" + String.valueOf(price) + "}";	
		  return output;
		 

	  case "CI_MOLTKE_BUFFET_PRICE":
		  setQuestion(false);
		  ArrayList<MealData> bmeals = ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(4).getTodayMeals();
		  int bindex = matchMealIndex(bmeals, wishMeal);
		  
		  price = bmeals.get(bindex).getPrice();
		  output = "<" + wishMeal +  ">";
		  output = output + ",{" + String.valueOf(price) + "}";	
		  return output;
		 
		  
	  case "CI_ADEN_LINE_1_DISH" :
		  setQuestion(false);
		  
		  if(wishMeal.isEmpty()){
			  output = packMeals(((CanteenDialog) currentDialog).getCurrentCanteen()
					  .getCanteenData().getLines().get(0).getTodayMeals());
			  return output;
		  }
		 return "<" + wishMeal +">";
		 
	  case "CI_ADEN_LINE_2_DISH" :
		  setQuestion(false);
		  
		  if(wishMeal.isEmpty()){
			  output = packMeals(((CanteenDialog) currentDialog).getCurrentCanteen()
					  .getCanteenData().getLines().get(1).getTodayMeals());
			  return output;
		  }
		 return "<" + wishMeal +">";
		  
	  case "CI_ADEN_LINE_3_DISH" :
		  
		  setQuestion(false);
		  
		  if(wishMeal.isEmpty()){
			  output = packMeals(((CanteenDialog) currentDialog).getCurrentCanteen()
					  .getCanteenData().getLines().get(2).getTodayMeals());
			  return output;
		  }
		 return "<" + wishMeal +">";
		 
	  case "CI_ADEN_LINE_45_DISH" :
		  setQuestion(false);
		  
		  if(wishMeal.isEmpty()){
			  output = packMeals(((CanteenDialog) currentDialog).getCurrentCanteen()
					  .getCanteenData().getLines().get(3).getTodayMeals());
			  return output;
		  }
		 return "<" + wishMeal +">";
		
	  case "CI_ADEN_LINE_6_DISH" :
		  setQuestion(false);
		  
		  if(wishMeal.isEmpty()){
			  output = packMeals(((CanteenDialog) currentDialog).getCurrentCanteen()
					  .getCanteenData().getLines().get(8).getTodayMeals());
			  return output;
		  }
		 return "<" + wishMeal +">";
		  
	  case "CI_MOLTKE_GG_DISH":
		  setQuestion(false);
		  
		  if(wishMeal.isEmpty()){
			  output = packMeals(((CanteenDialog) currentDialog).getCurrentCanteen()
					  .getCanteenData().getLines().get(3).getTodayMeals());
			  return output;
		  }
		 return "<" + wishMeal +">";
	
	  case "CI_ADEN_CURRYQ_DISH" :
		  setQuestion(false);
		  
		  if(wishMeal.isEmpty()){
			  output = packMeals(((CanteenDialog) currentDialog).getCurrentCanteen()
					  .getCanteenData().getLines().get(9).getTodayMeals());
			  return output;
		  }
		 return "<" + wishMeal +">";
		 
	  case "CI_ADEN_CAFE_DISH" :
		  setQuestion(false);
		  
		  if(wishMeal.isEmpty()){
			  output = packMeals(((CanteenDialog) currentDialog).getCurrentCanteen()
					  .getCanteenData().getLines().get(6).getTodayMeals());
			  return output;
		  }
		 return "<" + wishMeal +">";
		  
	  case "CI_ADEN_SCHNITBAR_DISH" :
		  setQuestion(false);
		  
		  if(wishMeal.isEmpty()){
			  output = packMeals(((CanteenDialog) currentDialog).getCurrentCanteen()
					  .getCanteenData().getLines().get(4).getTodayMeals());
			  return output;
		  }
		 return "<" + wishMeal +">";
		  
	  case "CI_MOLTKE_CHOICE_1_DISH":
		  setQuestion(false);
		  
		  if(wishMeal.isEmpty()){
			  output = packMeals(((CanteenDialog) currentDialog).getCurrentCanteen()
					  .getCanteenData().getLines().get(0).getTodayMeals());
			  return output;
		  }
		 return "<" + wishMeal +">";
		  
	  case "CI_MOLTKE_CHOICE_2_DISH" :
		  setQuestion(false);
		  
		  if(wishMeal.isEmpty()){
			  output = packMeals(((CanteenDialog) currentDialog).getCurrentCanteen()
					  .getCanteenData().getLines().get(1).getTodayMeals());
			  return output;
		  }
		 return "<" + wishMeal +">";
		  
	  case "CI_MOLTKE_SCHNITBAR_DISH" :
		  setQuestion(false);
		  
		  if(wishMeal.isEmpty()){
			  output = packMeals(((CanteenDialog) currentDialog).getCurrentCanteen()
					  .getCanteenData().getLines().get(5).getTodayMeals());
			  return output;
		  }
		 return "<" + wishMeal +">";
		  
	  case "CI_MOLTKE_ACTTHEK_DISH" :
		  setQuestion(false);
		  
		  if(wishMeal.isEmpty()){
			  output = packMeals(((CanteenDialog) currentDialog).getCurrentCanteen()
					  .getCanteenData().getLines().get(2).getTodayMeals());
			  return output;
		  }
		 return "<" + wishMeal +">";
		  
	  case "CI_MOLTKE_BUFFET_DISH":
		  setQuestion(false);
		  
		  if(wishMeal.isEmpty()){
			  output = packMeals(((CanteenDialog) currentDialog).getCurrentCanteen()
					  .getCanteenData().getLines().get(2).getTodayMeals());
			  return output;
		  }
		  output = "<" + wishMeal +">";
		  output = output + ",{" + time + "}";	
		  return output;
	  case "CI_TELL_LINE_NOT_EXIST":
		  setQuestion(false);
		  return null;
	  case "CI_TELL_MEAL_NOT_EXIST":
		  setQuestion(false);
		  return null;
	  case "CI_EXIT" :
		  setQuestion(false);
		  return null;
		  default : 
			  return null;
	  }
  
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
				if(( ld.getLineID() == (canteenData.getLines().size() - 1)) 
						&& ( md.getMealID() == (ld.getTodayMeals().size() - 1))) {
					meals = meals + " and " + md.getMealName() + " at " + ld.getLineName();
				}
				meals = meals + md.getMealName() + " at " + ld.getLineName() + ";";
			}
		}
		meals = meals + "}";
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
		  Pattern p = Pattern.compile(wishMeal); // compares string and json string
		  for(MealData wish : meals) {
			  Matcher matcher = p.matcher(wish.getMealName()); 
			  if(matcher.find()) {
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
	 // c.setWishMeal("Makkaroni mit Gem¨¹sebolognese, auf Wunsch mit Reibek?se und Salat");
	  DialogManager.giveDialogManager().setCurrentDialog(c);
	  System.out.println(s.getOutputKeyword());
	//  System.out.println(curC.getCanteenData().getLines().get(0).getTodayMeals().get(1).getMealName());
  }*/
}