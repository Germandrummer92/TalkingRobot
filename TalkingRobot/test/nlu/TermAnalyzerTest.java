package nlu;

import org.junit.After;
import org.junit.Before;

public class TermAnalyzerTest {
	private TermAnalyzer term = new TermAnalyzer();
	
	@Before
	private void setUp() {
		this.term = new TermAnalyzer();
	}

	@After
	public void tearDown() {
		this.term = null;
	}
}
