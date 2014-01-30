package nlu;

import org.junit.After;
import org.junit.Before;

public class KeywordAnalyzerTest {
	private KeywordAnalyzer keyword;
	
	@Before
	private void setUp() {
		this.keyword = new KeywordAnalyzer();
	}

	@After
	public void tearDown() {
		this.keyword = null;
	}
}
