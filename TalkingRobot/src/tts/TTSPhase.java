package tts;

import asr.ASRPhase;
import generalControl.Main;
import generalControl.Phase;

/**
 * 
 * @author Daniel Draper
 * @version 1.0
 * TTSPhase, responsible for communicating with Cepstral for speech output, through the SpeechCreator.
 */
public class TTSPhase extends Phase {

  /**
   * @See {@link Phase#setPhaseResult(Main)}
   * @param main the main, the current system is running on.
   */
  public void setPhaseResult(Main main) {
	  main.setTTSResult(new SpeechCreator().sendOutput(main.getNlgResult()));
  }

  /**
   * @See {@link Phase#nextPhase(Main)}
   * @return the next Phase for the system.
   */
  public Phase nextPhase(Main main) {
	  	return new ASRPhase();
  }

}