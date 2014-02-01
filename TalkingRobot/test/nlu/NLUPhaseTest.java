package nlu;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import generalControl.Main;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NLUPhaseTest {
	private NLUPhase nluPhase;
	private Main main;
	private KeywordAnalyzer keyword;
	private PossibleKeywordAnalyzer possible;
	private TermAnalyzer term;
	private ApprovalAnalyzer approval;
	
	@Before
	public void setUp() {
		this.nluPhase = new NLUPhase();
		this.main = main.giveMain();
		this.keyword = new KeywordAnalyzer();
		this.possible = new PossibleKeywordAnalyzer();
		this.term = new TermAnalyzer();
		this.approval = new ApprovalAnalyzer();
	}
	
	@Test
	public void greeting() {
		main.setAsrResult("Hello");
		nluPhase.setPhaseResult(main);
		LinkedList<List<String>> compare = new LinkedList<List<String>>();
		compare.add(keyword.analyze("Hello"));
		compare.add(term.analyze("Hello"));
		compare.add(possible.analyze("Hello"));
		compare.add(approval.analyze("Hello"));
		assertEquals(compare, main.getNluResult());
	}
	
	@After
	public void tearDown() {
		this.nluPhase = null;
		this.main = null;
		this.keyword = null;
		this.possible = null;
		this.term = null;
		this.approval = null;
	}

}