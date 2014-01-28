package dm;


/**
 * This class represents the state a Canteen Recommendation Dialog can have.
 * @author Daniel Draper, Xizhe Lian
 * @version 1.2
 *
 */
public class CanteenRecommendationState extends DialogState {
    
    /**
     * Creates a new CanteenRecommendationState in the ENTRY state.
     */
    public CanteenRecommendationState() {
    	super();
    	setCurrentState(CanteenRecom.CR_ENTRY);
    }
    
    /**
     * Creates a new CanteenRecommendationState in the specified state.
     * @param currentState the new state.
     */
    public CanteenRecommendationState(CanteenRecom currentState) {
    	super();
    	setCurrentState(currentState);
    }
    
    
  

/**
    * @see DialogState#getOutputKeyword()
    */
  public String getOutputKeyword() {
	  Dialog currentDialog =(CanteenRecommendationDialog)DialogManager.giveDialogManager().getCurrentDialog();
	  //String time = ((CanteenRecommendationDialog) currentDialog).getWishDate();
	  String wishMeal = ((CanteenRecommendationDialog) currentDialog).getWishmeal();
	  
	  switch((CanteenRecom)getCurrentState()) {
	  case CR_ENTRY:
		  setQuestion(false);
		  return null;
	  case CR_ASK_PREFERENCE:
		  setQuestion(true);
		  return null;
	  case CR_ADEN_LINE_1_DISH:
		  setQuestion(false);
		  // we can just get todays meal?
		  String output = "<" + ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(0).getTodayMeals().toString() + ">";
		  output = output +" ,{" + wishMeal + "}";	  
		  return output;
	  case CR_ADEN_LINE_2_DISH:
		  setQuestion(false);
		  String output1 = "<" + ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(1).getTodayMeals().toString() + ">";
		  output1 = output1 + " ,{" + wishMeal + "}";	
		  return output1;
	  case CR_ADEN_LINE_3_DISH:
		  setQuestion(false);
		  String output2 = "<" + ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(2).getTodayMeals().toString() + ">";
		  output2 = output2 + " ,{" + wishMeal + "}";	
		  return output2;
	  case CR_ADEN_LINE_45_DISH:
		  setQuestion(false);
		  String output3 = "<" + ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(3).getTodayMeals().toString() + ">";
		  output3 = output3 + " ,{" + wishMeal + "}";	
		  return output3;
	  case CR_ADEN_LINE_6_DISH:
		  setQuestion(false);
		  
		  String output4 = "<" + ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(8).getTodayMeals().toString() + ">";
		  output4 = output4 + " ,{" + wishMeal + "}";	
		  return output4;
	  case CR_ADEN_SCHNITBAR_DISH:
		  setQuestion(false);
		  String output5 = "<" + ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(4).getTodayMeals().toString() + ">";
		  output5 = output5 + " ,{" + wishMeal + "}";	
		  return null;
	  case CR_ADEN_CURRYQ_DISH:
		  setQuestion(false);
		  String output6 = "<" + ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(9).getTodayMeals().toString() + ">";
		  output6 = output6 + " ,{" + wishMeal + "}";	
		  
		  return null;
	  case CR_ADEN_CAFE_DISH:
		  setQuestion(false);
		  String output7 = "<" + ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(6).getTodayMeals().toString() + ">";
		  output7 = output7 + " ,{" + wishMeal + "}";
		  return null;
	  case CR_MOLTKE_CHOICE_1_DISH:
		  setQuestion(false);
		  String output8 = "<" + ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(0).getTodayMeals().toString() + ">";
		  output8 = output8 + " ,{" + wishMeal + "}";
		  return null;
	  case CR_MOLTKE_CHOICE_2_DISH:
		  setQuestion(false);
		  String output9 = "<" + ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(1).getTodayMeals().toString() + ">";
		  output9 = output9 + " ,{" + wishMeal + "}";
		  return null;
	  case CR_MOLTKE_SCHNITBAR_DISH:
		  setQuestion(false);
		  String output10 = "<" + ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(5).getTodayMeals().toString() + ">";
		  output10 = output10 + " ,{" + wishMeal + "}";
		  return null;
	  case CR_MOLTKE_CAFE_DISH:
		  setQuestion(false);
		  //FIXME cafe in moltke gibts ned
		  String output11 = "<" + ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(0).getTodayMeals().toString() + ">";
		  output11 = output11 + " ,{" + wishMeal + "}";
		  return null;
	  case CR_MOLTKE_ACTTHEK_DISH:
		  setQuestion(false);
		  String output12 = "<" + ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(2).getTodayMeals().toString() + ">";
		  output12 = output12 + " ,{" + wishMeal + "}";
		  return null;
	  case CR_MOLTKE_GG_DISH:
		  setQuestion(false);
		  String output13 = "<" + ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(3).getTodayMeals().toString() + ">";
		  output13 = output13 + " ,{" + wishMeal + "}";
		  return null;
	  case CR_MOLTKE_BUFFET_DISH:
		  setQuestion(false);
		  String output14 = "<" + ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(4).getTodayMeals().toString() + ">";
		  output14 = output14 + " ,{" + wishMeal + "}";
		  return null;
	  case CR_TELL_MEAL_NOT_EXIST:
		  setQuestion(false);
		  return null;
	  case CR_EXIT:
		  setQuestion(false);
		  return null;
	  }
	  return null;
  }



}