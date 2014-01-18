package tts;
import asr.One4AllAdapter;

/**
 * 
 * @author Daniel Draper
 * @version 1.0
 *	This class is responsible for sending the complete string for output to Cepstral, and returning if cepstral was successful.
 */
//Currently a Stub, for cmdline output
public class SpeechCreator extends One4AllAdapter {

	/**
	 * Default Constructor
	 */
	public SpeechCreator() {
		super();
	}

/**
 * Sends the output created by the {@link NLGPhase} to Cepstral.
 * @param output
 * @return if the output was successful.
 */
  public Boolean sendOutput(String output) {
	  System.out.print("The robot says: ");
	  System.out.println(output);
	  return true;
  }

  //Not needed for Stub
  /**
   * private method to handle communication with Cepstral
   */
  private void operateCepstral() {
  }

}