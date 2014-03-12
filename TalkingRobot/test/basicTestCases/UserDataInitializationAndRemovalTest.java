package basicTestCases;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.PrintStream;
import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import generalControl.Main;

/**
 * 
 * @author Meng Meng Yan
 * @version 1.0
 * 
 * this currently doesn't work properly. but maybe it can be used later on to test the modules
 * together with the class Main.
 */
public class UserDataInitializationAndRemovalTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	
	@Before
	public void setUp() {

	    System.setOut(new PrintStream(outContent));

	}
	
	@After
	public void cleanUp() {
	    System.setOut(null);
	    System.setErr(null);
	}
	
	class MainActivity implements Runnable {
		@Override
		public void run() {
				Main.main(null);
		}

		public void stop() {
			System.exit(0);
		}
	}
	
	class TestActivity implements Runnable {
		LinkedList<String> userInput = new LinkedList<String>();
		int inputCounter = 0;
		
		TestActivity(LinkedList<String> newUserInput) {
			userInput = newUserInput;
		}
		
		@Override
		public void run() {	
			while(inputCounter != userInput.size()) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ByteArrayInputStream in = new ByteArrayInputStream(baos.toByteArray());
				if(in.toString() == "") {
					System.out.println(userInput.get(inputCounter));
					inputCounter++;
				}
			}
		}
	}
	
	/*@Test
	public void userInitializationTest() {
		MainActivity main = new MainActivity();
		
		LinkedList<String> testInput = new LinkedList<String>();
		testInput.add("hello");
		testInput.add("abcd");
		testInput.add("yes");
		testInput.add("student");
		TestActivity test = new TestActivity(testInput);
		
		main.run();
		test.run();
		
		main.stop();
	}*/

}
