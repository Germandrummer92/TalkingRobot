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

  //Das bitte nicht aendern, die DmPhase sollte immer geoffnet sein, sonst muessten wir die Daten immer wieder laden, das ist glaub ich nicht so gut.
  protected Phase nextPhase(Main main) {
  		return main.getDmPhase();
  }

  private List<String> operateAnalyzer(String input, InputAnalyzer analyzer) {
  return null;
  }

}