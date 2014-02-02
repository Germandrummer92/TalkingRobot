package dm;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Meng Meng Yan
 * @version 1.0
 */
public class ChoiceStrategyTest {
	ChoiceStrategy choice;
	LinkedList<String> example;
	
	@Before
	public void setUp() {
		choice = new ChoiceStrategy();
		example = new LinkedList<String>();
	}
	
	@Test
	public void testHandleErrorFirstTime() {
		
		example.add("habger;hamburger;3");
		example.add("cantean;canteen;1");
		example.add("praise;price;2");
		
		ErrorHandlingState errorHandleState = choice.handleError(example);
		ErrorHandlingState comparison = new ErrorHandlingState(true, ErrorHandling.CHOICE, "<cantean>,<canteen>");
		
		assertEquals(comparison.getCurrentState(), errorHandleState.getCurrentState());
		assertEquals(comparison.getOutputKeyword(), errorHandleState.getOutputKeyword());
		assertEquals(comparison.isQuestion(), errorHandleState.isQuestion());
	}
	
	@Test
	public void testHandleErrorSecondTime() {
		example.add("habger;hamburger;3");
		example.add("cantean;canteen;1");
		example.add("praise;price;2");
		
		choice.handleError(example);
		ErrorHandlingState errorHandleState = choice.handleError(null);
		ErrorHandlingState comparison = new ErrorHandlingState(true, ErrorHandling.CHOICE, "<habger>,<hamburger>");
		
		assertEquals(comparison.getCurrentState(), errorHandleState.getCurrentState());
		assertEquals(comparison.getOutputKeyword(), errorHandleState.getOutputKeyword());
		assertEquals(comparison.isQuestion(), errorHandleState.isQuestion());
	}
	
	@Test
	public void testChoices() {
		example.add("cantean;canteen;1");
		example.add("habger;hamburger;3");
		example.add("praise;price;2");
		
		ErrorHandlingState errorHandleState = choice.handleError(example);
		String[] output = errorHandleState.getOutputKeyword().split(",");
		String expected1 = "<" + choice.getFirstChoice() + ">";
		String expected2 = "<" + choice.getSecondChoice() + ">";
		assertEquals(output[0], expected1);
		assertEquals(output[1], expected2);
	}
	
	@After
	public void tearDown() {
		choice = null;
		example = null;
	}

}
