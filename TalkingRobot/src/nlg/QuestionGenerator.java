package nlg;

import java.util.ArrayList;
import java.util.List;

import simplenlg.features.Feature;
import simplenlg.features.InterrogativeType;
import simplenlg.framework.PhraseElement;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.phrasespec.VPPhraseSpec;

import nlg.Generator;
import nlg.Phrase;

public class QuestionGenerator extends Generator {

	/*
	private static final String DS = "dummy_subject";
	private static final String DV = "dummy_verb";
	private static final String DO = "dummy_object";
	*/
	ArrayList<NPPhraseSpec> objects = new ArrayList<NPPhraseSpec>();
	ArrayList<NPPhraseSpec> subjects = new ArrayList<NPPhraseSpec>();
	ArrayList<NPPhraseSpec> ind_objects = new ArrayList<NPPhraseSpec>();
	ArrayList<VPPhraseSpec> verbs = new ArrayList<VPPhraseSpec>();
	
@SuppressWarnings("deprecation")
@Override
public String generateSentence(List<Phrase> phrases) {
	
	SimpleNLG simplenlg = SimpleNLG.getSimpleNLG();
	
	SPhraseSpec p = simplenlg.getNLGFactory().createClause();
	
	//subject.setNoun("Xizhe");
	//p.setSubject(subject);
	//String output = simplenlg.getRealiser().realiseSentence(p);
	//System.out.println(output);
	
	for(Phrase phrase: phrases) {
		switch(phrase.getPhraseClass()) {
		case SUBJECT:
			NPPhraseSpec subj = simplenlg.getNLGFactory().createNounPhrase(phrase.getPhraseString());
			subjects.add(subj);
			break;
		case OBJECT:
			NPPhraseSpec obj = simplenlg.getNLGFactory().createNounPhrase(phrase.getPhraseString());
			objects.add(obj);
			break;
		case VERB:
			VPPhraseSpec verb = simplenlg.getNLGFactory().createVerbPhrase(phrase.getPhraseString());
			//verb.setFeature(Feature.MODAL, "can");
			verbs.add(verb);
			break;
		case INDIRECT_OBJECT:
			NPPhraseSpec ind_obj = simplenlg.getNLGFactory().createNounPhrase(phrase.getPhraseString());
			ind_objects.add(ind_obj);
			break;
		default: break;
		}
	}
	
	for (Phrase phrase: phrases) {
		switch(phrase.getPhraseClass()) {
		case COMPLEMENT: 
			p.addComplement(phrase.getPhraseString());
			break;
		case MODIFIER:
			try {
				PhraseElement element = findConnection(phrase.getConnection());
				element.addModifier(phrase.getPhraseString());
			}
			catch(NullPointerException e){
				//TODO HAndle exception - no connection for modifier!!!
				e.printStackTrace();
			}
			break;
		case DETERMINER: 
			try {
				PhraseElement element = findConnection(phrase.getConnection());
				element.setDeterminer(phrase.getPhraseString());
			}
			catch(NullPointerException e){
				//TODO HAndle exception - no connection for modifier!!!
				e.printStackTrace();
			}
			break;
		default: break;
		}
		
		if (phrase.getPhraseClass() == PhraseClass.OBJECT) {
			p.setObject(phrase.getPhraseString());
		}
	}
	
	for (NPPhraseSpec subj: subjects) {
		p.setSubject(subj);
	}
	for (NPPhraseSpec obj: objects) {
		p.setObject(obj);
	}
	for (VPPhraseSpec verb: verbs) {
		p.setVerb(verb);
	}
	
	
	p.setFeature(Feature.INTERROGATIVE_TYPE, InterrogativeType.YES_NO);
	p.setFeature(Feature.MODAL, "can");
	//p.setFeature(Feature.MODAL, "can");
	String output = simplenlg.getRealiser().realiseSentence(p);
	
	return output;
}

	private PhraseElement findConnection(Phrase connection) {
		for(NPPhraseSpec subject: subjects) {
			///////// DON'T FREAK OUT HERE!!!! EXPLANATION LATER///////
			String connectionString = "WordElement[" + connection.getPhraseString() + ":NOUN]";
			if (subject.getNoun().toString().equals(connectionString)) {
				return subject;
			}
		}
		for(NPPhraseSpec object: objects) {
			///////// DON'T FREAK OUT HERE!!!! EXPLANATION LATER///////
			String connectionString = "WordElement[" + connection.getPhraseString() + ":NOUN]";
			if (object.getNoun().toString().equals(connectionString)) {
				return object;
			}
		}
		for(NPPhraseSpec ind_object: ind_objects) {
			///////// DON'T FREAK OUT HERE!!!! EXPLANATION LATER///////
			String connectionString = "WordElement[" + connection.getPhraseString() + ":NOUN]";
			if (ind_object.getNoun().toString().equals(connectionString)) {
				return ind_object;
			}
		}
		for(VPPhraseSpec verb: verbs) {
			///////// DON'T FREAK OUT HERE!!!! EXPLANATION LATER///////
			String connectionString = "WordElement[" + connection.getPhraseString() + ":VERB]";
			if (verb.getVerb().toString().equals(connectionString)) {
				return verb;
			}
		}
		return null;
	}

	public static void main (String args[]) {
		ArrayList<Phrase> phrases = new ArrayList<Phrase>();
		AnswerGenerator agenerator = new AnswerGenerator();
		
		Phrase p1 = new Phrase();
		p1.setPhraseString("He");
		p1.setPhraseClass(PhraseClass.SUBJECT);
		phrases.add(p1);
		
		Phrase p2 = new Phrase();
		p2.setPhraseString("have");
		p2.setPhraseClass(PhraseClass.VERB);
		phrases.add(p2);
		
		Phrase p4 = new Phrase();
		p4.setPhraseString("a day");
		p4.setPhraseClass(PhraseClass.OBJECT);
		phrases.add(p4);
		
		Phrase p3 = new Phrase();
		p3.setPhraseString("good");
		p3.setPhraseClass(PhraseClass.MODIFIER);
		p3.setConnection(p4);
		phrases.add(p3);
		
//		Phrase p5 = new Phrase();
//		p5.setPhraseString("super");
//		p5.setPhraseClass(PhraseClass.MODIFIER);
//		p5.setConnection(p2);
//		phrases.add(p5);
		
		Phrase p6 = new Phrase();
		p6.setPhraseString("the");
		p6.setPhraseClass(PhraseClass.DETERMINER);
		p6.setConnection(p4);
		phrases.add(p6);
		
		String output = agenerator.generateSentence(phrases);
		
		System.out.println(output);
	}

}