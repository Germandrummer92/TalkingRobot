package nlu;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PossibleKeywordAnalyzerTest {
	private PossibleKeywordAnalyzer possibleKw;
	
	@Before
	private void setUp() {
		this.possibleKw = new PossibleKeywordAnalyzer();
	}
	
	
	@After
	public void tearDown() {
		this.possibleKw = null;
	}

}
