package asr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * InputGetter is responsible for communicating with the JRTK to retrieve the next User Input.
 * @author Daniel Draper
 * @version 1.0
 * 
 */
	//Currently a STUB!
public class InputGetter extends One4AllAdapter {

	/**
	 * Default Constructor
	 */
	public InputGetter() {
		super();
	}
  
	/**
	 * Gets the current User Input from the JRTK as a String.
	 * @return the parsed input by the user.
	 */
  public String getInput() {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 	System.out.print("Your next Input:");
 	try {
		return br.readLine();
	} catch (IOException e) {
		e.printStackTrace();
	}
 	return null;
  }

  //Not used by the stub
  /**
   * private function to handle communication with the JRTK.
   */
  private void operateJanus() {
  }

}