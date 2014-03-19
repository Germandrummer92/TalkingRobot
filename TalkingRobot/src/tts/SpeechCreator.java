package tts;
import one4all.fipa.net.BasicMessage;
import asr.One4AllAdapter;

/**
 * 
 * @author Daniel Draper
 * @version 1.0
 *	This class is responsible for sending the complete string for output to Cepstral, and returning if cepstral was successful.
 */
//Currently a Stub, for cmdline output
public class SpeechCreator  {
	private One4AllAdapter one4all;
	/**
	 * Default Constructor
	 */
	public SpeechCreator() {
		one4all = One4AllAdapter.giveOne4AllAdapter();
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
	 /* '(inform
	  :sender TapasDialog
	  :receiver tts
	  :language one4all
	  :reply-with TapasDialog-msg-3
	  :content #149 "(speak :val (Hello! My name is Armar the third. I am a little robot who can help you in the kitchen. What do you want me to do?) :language (english))"
	)*/
	  //o4aAgent.sendContentMessage("(msg :X (some value))", "example2");
	 /* BasicMessage basic = new BasicMessage(null);
	  basic.setType("inform");
	  basic.addAttribute("sender", "TalkingRobotDM");
	  basic.addAttribute("receiver", "tts");
	  basic.addAttribute("language", "one4all");
	  basic.addAttribute("reply-with", "TalkingRobotDM-msg-1");
	  basic.addAttribute("content", "\"(speak :val (" + output + ") " + ":language (english))\"");
	  one4all.getOne4all().sendFormattedMessage(basic);
	  return true;*/
  }

  //Not needed for Stub
  /**
   * private method to handle communication with Cepstral
   */
  private void operateCepstral() {
  }

}