package nlg;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class QuestionGeneratorComponentTest {

	QuestionGenerator qgenerator;
	ArrayList<Phrase> phrases;
	
	@Before
	public void setUpNewPhraseArray() {
		qgenerator = new QuestionGenerator();
		phrases = new ArrayList<Phrase>();
	}
	
	@Test
	public void testHow() {
		Phrase p1 = new Phrase();
		Phrase p2 = new Phrase();
		Phrase p3 = new Phrase();
		
		p1.setPhraseString("I");
		p1.setPhraseClass(PhraseClass.SUBJECT);
		
		p2.setPhraseString("cook");
		p2.setPhraseClass(PhraseClass.VERB_HOW);
		
		p3.setPhraseString("this recipe");
		p3.setPhraseClass(PhraseClass.OBJECT);
		
		phrases.add(p1);
		phrases.add(p2);
		phrases.add(p3);
		
		
		String output = qgenerator.generateSentence(phrases);
		
		assertEquals("How do I cook this recipe?", output);
	}
	
	@Test
	public void testCan() {
		Phrase p1 = new Phrase();
		Phrase p2 = new Phrase();
		Phrase p3 = new Phrase();
		Phrase p4 = new Phrase();
		
		p1.setPhraseString("you");
		p1.setPhraseClass(PhraseClass.SUBJECT);
		
		p2.setPhraseString("teach");
		p2.setPhraseClass(PhraseClass.VERB);
		
		p3.setPhraseString("this recipe");
		p3.setPhraseClass(PhraseClass.OBJECT);
		
		p4.setPhraseString("me");
		p4.setPhraseClass(PhraseClass.INDIRECT_OBJECT);
		
		phrases.add(p1);
		phrases.add(p2);
		phrases.add(p3);
		phrases.add(p4);
		
		String output = qgenerator.generateSentence(phrases);
		
		assertEquals("Can you teach me this recipe?", output);
	}
	
	@Test
	public void testModifier() {
		Phrase p1 = new Phrase();
		Phrase p2 = new Phrase();
		Phrase p3 = new Phrase();
		Phrase p4 = new Phrase();
		
		p1.setPhraseString("the boy");
		p1.setPhraseClass(PhraseClass.SUBJECT);
		
		p2.setPhraseString("like");
		p2.setPhraseClass(PhraseClass.VERB_HOW);
		
		p3.setPhraseString("shoe");
		p3.setPhraseClass(PhraseClass.OBJECT);
		
		p4.setPhraseString("red");
		p4.setPhraseClass(PhraseClass.MODIFIER);
		p4.setConnection(p3);

		phrases.add(p1);
		phrases.add(p2);
		phrases.add(p3);
		phrases.add(p4);
		
		String output = qgenerator.generateSentence(phrases);
		
		assertEquals("How does the boy like red shoe?", output);
	}

}
