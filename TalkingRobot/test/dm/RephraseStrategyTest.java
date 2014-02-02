package dm;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Meng Meng Yan
 * @version 1.0
 */
public class RephraseStrategyTest {
	
	RephraseStrategy rephrase;
	
	@Before
	public void setUp() {
		rephrase = new RephraseStrategy();
	}
	
	@Test
	public void testHandleError() {
		ErrorHandlingState errorHandleState = rephrase.handleError(null);
		ErrorHandlingState comparison = new ErrorHandlingState(true, ErrorHandling.REPHRASE, null);
		
		assertEquals(comparison.getCurrentState(), errorHandleState.getCurrentState());
		assertEquals(comparison.getOutputKeyword(), errorHandleState.getOutputKeyword());
		assertEquals(comparison.isQuestion(), errorHandleState.isQuestion());
	}
	
	@Test
	public void testCounter() {
		int counter = rephrase.getCounter() + 1;
		rephrase.handleError(null);
		int counterAfter = rephrase.getCounter();
		assertEquals(counter, counterAfter);
	}
	
	@After
	public void tearDown() {
		rephrase = null;
	}
}
