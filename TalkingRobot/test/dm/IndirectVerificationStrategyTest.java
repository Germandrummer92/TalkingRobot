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
public class IndirectVerificationStrategyTest {
	IndirectVerificationStrategy indVerify;
	LinkedList<String> example;
	
	@Before
	public void setUp() {
		indVerify = new IndirectVerificationStrategy();
		example = new LinkedList<String>();
	}
	
	@Test
	public void testHandleErrorFirstTime() {
		example.add("cantean;canteen;1");
		example.add("recipee;recipe;1");
		example.add("teechyou;teach you;2");
		
		ErrorHandlingState errorHandleState =indVerify.handleError(example);
		ErrorHandlingState comparison = new ErrorHandlingState(
				false, ErrorHandling.Indirect_Verification, "{canteen}");
		
		assertEquals(comparison.getCurrentState(), errorHandleState.getCurrentState());
		assertEquals(comparison.getOutputKeyword(), errorHandleState.getOutputKeyword());
		assertEquals(comparison.isQuestion(), errorHandleState.isQuestion());
	}
	
	@Test
	public void testHandleErrorSecondTime() {
		example.add("cantean;canteen;1");
		example.add("recipee;recipe;1");
		example.add("teechyou;teach you;2");
		
		indVerify.handleError(example);
		ErrorHandlingState errorHandleState = indVerify.handleError(null);
		ErrorHandlingState comparison = new ErrorHandlingState(
				false, ErrorHandling.Indirect_Verification, "{recipe}");
		
		assertEquals(comparison.getCurrentState(), errorHandleState.getCurrentState());
		assertEquals(comparison.getOutputKeyword(), errorHandleState.getOutputKeyword());
		assertEquals(comparison.isQuestion(), errorHandleState.isQuestion());		
	}
	
	@After
	public void tearDown() {
		indVerify = null;
		example = null;
	}
}
