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
public class ExplicitVerificationStrategyTest {
	ExplicitVerificationStrategy exVerify;
	LinkedList<String> example;
	
	@Before
	public void setUp() {
		exVerify = new ExplicitVerificationStrategy();
		example = new LinkedList<String>();
	}
	
	@Test
	public void testHandleErrorFirstTime() {
		example.add("cantean;canteen;1");
		example.add("recipee;recipe;1");
		example.add("teechyou;teach you;2");
		
		ErrorHandlingState errorHandleState =exVerify.handleError(example);
		ErrorHandlingState comparison = new ErrorHandlingState(
				true, ErrorHandling.Explicit_Verification, "<teach you>");
		
		System.out.println(errorHandleState.getOutputKeyword());
		assertEquals(comparison.getCurrentState(), errorHandleState.getCurrentState());
		assertEquals(comparison.getOutputKeyword(), errorHandleState.getOutputKeyword());
		assertEquals(comparison.isQuestion(), errorHandleState.isQuestion());
	}
	
	@Test
	public void testHandleErrorSecondTime() {
		example.add("cantean;canteen;1");
		example.add("recipee;recipe;1");
		example.add("teechyou;teach you;2");
		
		exVerify.handleError(example);
		ErrorHandlingState errorHandleState = exVerify.handleError(null);
		ErrorHandlingState comparison = new ErrorHandlingState(
				true, ErrorHandling.Explicit_Verification, "<canteen>");
		
		assertEquals(comparison.getCurrentState(), errorHandleState.getCurrentState());
		assertEquals(comparison.getOutputKeyword(), errorHandleState.getOutputKeyword());
		assertEquals(comparison.isQuestion(), errorHandleState.isQuestion());		
	}
	
	@After
	public void tearDown() {
		exVerify = null;
		example = null;
	}
	
}
