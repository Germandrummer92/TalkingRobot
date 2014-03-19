package asr;

import generalControl.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * InputGetter is responsible for communicating with the JRTK to retrieve the next User Input.
 * @author Daniel Draper
 * @version 1.0
 * 
 */
	//Currently a STUB!
public class InputGetter {

	/**
	 * Default Constructor
	 */
	public InputGetter() {
		One4AllAdapter.giveOne4AllAdapter();
	}
  
	/**
	 * Gets the current User Input from the JRTK as a String.
	 * @return the parsed input by the user.
	 */
  public String getInput() {
	/*//  Scanner in = new Scanner(System.in);
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 	System.out.print("Your next Input:");
 	try {
 		return br.readLine();

	} catch (IOException e) {
		e.printStackTrace();
	}
 	return null;*/
	  do {
		  if (Main.giveMain().isAsrReceived() == false) {
			  try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
		  else {
			  return Main.giveMain().getAsrResult();
		  }
	  } while (true);
  }

  //Not used by the stub
  /**
   * private function to handle communication with the JRTK.
   */
  private void operateJanus() {
  }

}