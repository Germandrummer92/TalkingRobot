package nlu;
import generalControl.Main;
import generalControl.Phase;

import java.util.List;

public class NLUPhase extends Phase {

  private InputAnalyzer termAnalyzer;

  private InputAnalyzer kwAnalyzer;

  private InputAnalyzer possibleKwAnalyzer;

  private InputAnalyzer approvalAnalyzer;
  

  public void setPhaseResult(Main main) {
  }

  protected Phase nextPhase(Main main) {
  return null;
  }

  private List<String> operateAnalyzer(String input, InputAnalyzer analyzer) {
  return null;
  }

}