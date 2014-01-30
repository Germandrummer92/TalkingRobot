package nlu;

import org.junit.After;
import org.junit.Before;

public class NLUPhaseTest {
	private NLUPhase nluPhase;
	
	@Before
	private void setUp() {
		this.nluPhase = new NLUPhase();
	}
	
	@After
	public void tearDown() {
		this.nluPhase = null;
	}

}
