package dm;


/**
 * This class represents the state a Canteen Recommendation Dialog can have.
 * @author Daniel Draper, Xizhe Lian, Luiz Henrique S. Silva
 * @version 1.3
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
    	//String wishMealCategory = ((CanteenRecommendationDialog) currentDialog).getWishmealCategory();
    	//String wishMeal = ((CanteenRecommendationDialog) currentDialog).getWishMeal().getMealData().getMealName();
	  
    	switch((CanteenRecom)getCurrentState()) {
    	case CR_ENTRY:
    		setQuestion(false);
    		return null;
    	case CR_ASK_PREFERENCE:
    		setQuestion(true);
    		return null;
    	//Since the Data is managed in CanteenRecommendationDialog, it's not necessary to handle it here.
    	//The information to be returned is the same.
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
    		setQuestion(false);
    		String wishMeal = ((CanteenRecommendationDialog) currentDialog).getWishMeal().getMealData().getMealName();
    		String wishMealCategory = ((CanteenRecommendationDialog) currentDialog).getWishmealCategory();
    		String output = "<" + wishMeal + ">" + ";{" + wishMealCategory + "}";
    		return output;
    	case CR_TELL_MEAL_NOT_EXIST:
    		setQuestion(false);
    		return null;
    	case CR_EXIT:
    		setQuestion(false);
    		return null;
		default:
			break;
    	}
    	return null;
    }
}