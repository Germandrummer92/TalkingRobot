package generalControl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import asr.ASRPhase;

import dm.DMPhase;
import dm.DialogState;

/**
 * 
 * @author Daniel Draper
 * @version 1.0
 * The main class of the whole system, responsible for the total System flow.
 */
public class Main {

  private Phase phase;
  
  private DMPhase dmPhase;

  private String asrResult;

  private List<String>[] nluResult;

  private DialogState dmResult;

  private String nlgResult;

  private Boolean ttsResult;

  private Boolean userLoggedIn;

  private static Main uniqueMain = null;

  private boolean isRunning;

 

  /**
   * First Dialog by the system, and loading of Data beforehand.
   */
  private void startDialog() {
	 isRunning = true;
	 checkFiles(); //Checks if all Data directories exist.
	 dmPhase = new DMPhase();	//Creates the DMPhase, which loads all data
	 //Goes through the StartDialog.
	 do {
		 phase.setPhaseResult(this);
		 phase = phase.nextPhase(this);
	 } while(!userLoggedIn && isRunning);
	 if (userLoggedIn && isRunning) {
		 return;
	 }
	 else {
		 isRunning = true;
		 userLoggedIn = false;
		 return;
	 }
  }

  /**
 * Checks if the directories needed for Data loading/saving exist, and creates them if they don't.
 */
private void checkFiles() {
	 File f = new File("/resources/files/CanteenData");
	  if (!f.exists()) {
		  f.mkdirs();
	  }
	  f = new File("resources/files/IngredientData");
	  if (!f.exists()) {
		  f.mkdirs();
	  }
	  f = new File("resources/files/KeywordData");
	  if (!f.exists()) {
		  f.mkdirs();
	  }
	  f = new File("resources/files/LineData");
	  if (!f.exists()) {
		  f.mkdirs();
	  }
	  f = new File("resources/files/MealCategoryData");
	  if (!f.exists()) {
		  f.mkdirs();
	  }
	  f = new File("resources/files/MealData");
	  if (!f.exists()) {
		  f.mkdirs();
	  }
	  f = new File("resources/files/RecipeData");
	  if (!f.exists()) {
		  f.mkdirs();
	  }
	  f = new File("resources/files/RobotData");
	  if (!f.exists()) {
		  f.mkdirs();
	  }
	  f = new File("resources/files/ToolData");
	  if (!f.exists()) {
		  f.mkdirs();
	  }
	  f = new File("resources/files/UserData");
	  if (!f.exists()) {
		  f.mkdirs();
	  }	
}

/**
   * Private Constructor only used by {@link Main#giveMain()}giveMain().
   */
  private Main() {
	  phase = new ASRPhase();
	  asrResult = null;
	  nluResult = null;
	  dmResult = null;
	  nlgResult = null;
	  ttsResult = null;
	  userLoggedIn = false;
	  isRunning = true;
  }

  /**
   * Singleton creation/Access.
   * @return the single unique instance of Main.
   */
  public static Main giveMain() {
	  if (uniqueMain == null) {
		  uniqueMain = new Main();
		  return uniqueMain;
	  }
	  else {
		  return uniqueMain;
	  }
  }

  /**
   *
   * @return the DMResult as the DialogState the system switched to.
   */
  public DialogState getDmResult() {
	  return dmResult;
  }

  /**
   * 
   * @return the NLG Result, the string for output.
   */
  	public String getNlgResult() {
  		return nlgResult;
  	}

  	/**
  	 * 
  	 * @param nlgResult the NLG Result to be set.
  	 */
  	public void setNlgResult(String nlgResult) {
  		this.nlgResult = nlgResult;
  	}

	/**
	 * Sets the ttsResult to the parameter.
	 * @param ttsResult
	 */
	public void setTTSResult(Boolean ttsResult) {
		this.ttsResult = ttsResult;
		
	}
	
	/**
	 * @return the phase
	 */
	public Phase getPhase() {
		return phase;
	}

	/**
	 * @return the asrResult
	 */
	public String getAsrResult() {
		return asrResult;
	}

	/**
	 * @return the nluResult
	 */
	public List<String>[] getNluResult() {
		return nluResult;
	}

	/**
	 * @return the ttsResult
	 */
	public Boolean getTtsResult() {
		return ttsResult;
	}

	/**
	 * @return the userLoggedIn
	 */
	public Boolean isUserLoggedIn() {
		return userLoggedIn;
	}

	/**
	 * @return the isRunning
	 */
	public boolean isRunning() {
		return isRunning;
	}

	/**
	 * @param phase the phase to set
	 */
	public void setPhase(Phase phase) {
		this.phase = phase;
	}

	/**
	 * @param asrResult the asrResult to set
	 */
	public void setAsrResult(String asrResult) {
		this.asrResult = asrResult;
	}

	/**
	 * @param nluResult the nluResult to set
	 */
	public void setNluResult(List<String>[] nluResult) {
		this.nluResult = nluResult;
	}

	/**
	 * @param dmResult the dmResult to set
	 */
	public void setDmResult(DialogState dmResult) {
		this.dmResult = dmResult;
	}

	/**
	 * @param ttsResult the ttsResult to set
	 */
	public void setTtsResult(Boolean ttsResult) {
		this.ttsResult = ttsResult;
	}

	/**
	 * @param userLoggedIn the userLoggedIn to set
	 */
	public void setUserLoggedIn(Boolean userLoggedIn) {
		this.userLoggedIn = userLoggedIn;
	}

	/**
	 * @param isRunning the isRunning to set
	 */
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	/**
	 * @return the dmPhase
	 */
	public DMPhase getDmPhase() {
		return dmPhase;
	}

	/**
	 * @param dmPhase the dmPhase to set
	 */
	public void setDmPhase(DMPhase dmPhase) {
		this.dmPhase = dmPhase;
	}

	/**
	 * Starts the whole System and then goes through each phase, as long as Main is running.
	 * @param args cmd-line arguments.
	 */
	 public static void main(String args[]) {
		 //main Loop
		 do {
			 giveMain().startDialog();
			 //Loop through phases, after User is logged in, and system has started (possible to not have started if user enters "good bye" in login process)
			 while (giveMain().isRunning && giveMain().userLoggedIn) {
				 giveMain().phase.setPhaseResult(giveMain());
				 giveMain().phase = giveMain().phase.nextPhase(giveMain());
			 }
			 if (!giveMain().isUserLoggedIn()) {
				  giveMain().startDialog();
				 }
			 
		 }
		 while(true);
	  }
}