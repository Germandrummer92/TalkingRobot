package dm;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;


/**
 * 
 * @author Meng Meng Yan
 * @version
 */
public class RepeatStrategyTest {
	
	RepeatStrategy repeat;
	
	@Before
	public void setUp() {
		repeat = new RepeatStrategy();
	}
	
	@Test
	public void testHandleError() {
		ErrorHandlingState errorHandleState = repeat.handleError(null);
		ErrorHandlingState comparison = new ErrorHandlingState(true, ErrorHandling.REPEAT, "repeat");
		
		assertEquals(comparison.getCurrentState(), errorHandleState.getCurrentState());
		assertEquals(comparison.getOutputKeyword(), errorHandleState.getOutputKeyword());
		assertEquals(comparison.isQuestion(), errorHandleState.isQuestion());
	}
	
	@Test
	public void testCounter() {
		int counter = repeat.getCounter() + 1;
		repeat.handleError(null);
		int counterAfter = repeat.getCounter();
		assertEquals(counter, counterAfter);
	}
	
	@After
	public void tearDown() {
		repeat = null;
	}
	
}
