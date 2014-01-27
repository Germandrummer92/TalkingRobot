package dm;


/**
 * This class represents the state a Canteen Recommendation Dialog can have.
 * @author Daniel Draper, Xizhe Lian
 * @version 1.0
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
	  Dialog currentDialog = DialogManager.giveDialogManager().getCurrentDialog();
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
		  output = output + "{today}";	  
		  return output;
	  case CR_ADEN_LINE_2_DISH:
		  setQuestion(false);
		  String output1 = "<" + ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(1).getTodayMeals().toString() + ">";
		  output1 = output1 + "{today}";	
		  return output1;
	  case CR_ADEN_LINE_3_DISH:
		  setQuestion(false);
		  String output2 = "<" + ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(2).getTodayMeals().toString() + ">";
		  output2 = output2 + "{today}";	
		  return output2;
	  case CR_ADEN_LINE_45_DISH:
		  setQuestion(false);
		  String output3 = "<" + ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(3).getTodayMeals().toString() + ">";
		  output3 = output3 + "{today}";	
		  return output3;
	  case CR_ADEN_LINE_6_DISH:
		  setQuestion(false);
		  String output4 = "<" + ((CanteenDialog) currentDialog).getCurrentCanteen()
				  .getCanteenData().getLines().get(4).getTodayMeals().toString() + ">";
		  output4 = output4 + "{today}";	
		  return output4;
	  case CR_ADEN_SCHNITBAR_DISH:
		  setQuestion(false);
		  return null;
	  case CR_ADEN_CURRYQ_DISH:
		  setQuestion(false);
		  return null;
	  case CR_ADEN_CAFE_DISH:
		  setQuestion(false);
		  return null;
	  case CR_MOLTKE_CHOICE_1_DISH:
		  setQuestion(false);
		  return null;
	  case CR_MOLTKE_CHOICE_2_DISH:
		  setQuestion(false);
		  return null;
	  case CR_MOLTKE_SCHNITBAR_DISH:
		  setQuestion(false);
		  return null;
	  case CR_MOLTKE_CAFE_DISH:
		  setQuestion(false);
		  return null;
	  case CR_MOLTKE_ACTTHEK_DISH:
		  setQuestion(false);
		  return null;
	  case CR_MOLTKE_GG_DISH:
		  setQuestion(false);
		  return null;
	  case CR_MOLTKE_BUFFET_DISH:
		  setQuestion(false);
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