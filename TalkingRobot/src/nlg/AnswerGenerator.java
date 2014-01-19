package nlg;
import java.util.ArrayList;
import java.util.List;

import simplenlg.features.*;
import simplenlg.framework.PhraseElement;
import simplenlg.phrasespec.*;


/**
 * 
 * @author Luiz Henrique Soares Silva, Xizhe Lian
 * @version 0.5
 *
 */
public class AnswerGenerator extends Generator {
	
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
					//TODO find solution to determiner
					element.setDeterminer(phrase.getPhraseString());
				}
				catch(NullPointerException e){
					//TODO HAndle exception - no connection for modifier!!!
					e.printStackTrace();
				}
				break;
			default: break;
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
		
		String output = simplenlg.getRealiser().realiseSentence(p);
		
		return output;
	}

	/**
	 * 
	 * @param connection
	 * @return
	 */
	private PhraseElement findConnection(Phrase connectionPhrase) {
		SimpleNLG simplenlg = SimpleNLG.getSimpleNLG();
		switch (connectionPhrase.getPhraseClass()) {
		case SUBJECT:
			for(NPPhraseSpec subject: subjects) {
				///////// DON'T FREAK OUT HERE!!!! EXPLANATION LATER///////
				NPPhraseSpec connect = simplenlg.getNLGFactory().createNounPhrase(connectionPhrase.getPhraseString());
				if (subject.getNoun().equals(connect.getNoun())) {
					return subject;
				}
			}
			break;
		case OBJECT:
			for(NPPhraseSpec object: objects) {
				///////// DON'T FREAK OUT HERE!!!! EXPLANATION LATER///////
				NPPhraseSpec connect = simplenlg.getNLGFactory().createNounPhrase(connectionPhrase.getPhraseString());
				if (object.getNoun().equals(connect.getNoun())) {
					return object;
				}
			}
			break;
		case INDIRECT_OBJECT:
			for(NPPhraseSpec ind_object: ind_objects) {
				///////// DON'T FREAK OUT HERE!!!! EXPLANATION LATER///////
				NPPhraseSpec connect = simplenlg.getNLGFactory().createNounPhrase(connectionPhrase.getPhraseString());
				if (ind_object.getNoun().equals(connect.getNoun())) {
					return ind_object;
				}
			}
			break;
		case VERB:
			for(VPPhraseSpec verb: verbs) {
				///////// DON'T FREAK OUT HERE!!!! EXPLANATION LATER///////
				VPPhraseSpec connect = simplenlg.getNLGFactory().createVerbPhrase(connectionPhrase.getPhraseString());
				if (verb.getVerb().equals(connect.getVerb())) {
					return verb;
				}
			}
			break;
		default: return null;
		}
		return null;
	}

	public static void main (String args[]) {
		ArrayList<Phrase> phrases = new ArrayList<Phrase>();
		AnswerGenerator agenerator = new AnswerGenerator();
		
		Phrase p1 = new Phrase();
		p1.setPhraseString("Jurema");
		p1.setPhraseClass(PhraseClass.SUBJECT);
		phrases.add(p1);
		
		Phrase p2 = new Phrase();
		p2.setPhraseString("get");
		p2.setPhraseClass(PhraseClass.VERB);
		phrases.add(p2);
		
		Phrase p4 = new Phrase();
		p4.setPhraseString("day");
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
		p6.setPhraseString("me");
		p6.setPhraseClass(PhraseClass.INDIRECT_OBJECT);
		p6.setConnection(p4);
		phrases.add(p6);
		
		String output = agenerator.generateSentence(phrases);
		
		System.out.println(output);
	}

}