package nlg;
import java.util.ArrayList;
import java.util.List;

import simplenlg.features.*;
import simplenlg.framework.PhraseElement;
import simplenlg.phrasespec.*;


/**
 * Class QuestionGenerator is responsible for generating a simple interrogative sentence.
 * @author Luiz Henrique Soares Silva, Xizhe Lian
 * @version 0.8
 *
 */
public class QuestionGenerator extends Generator {
	
	ArrayList<NPPhraseSpec> objects = new ArrayList<NPPhraseSpec>();
	ArrayList<NPPhraseSpec> subjects = new ArrayList<NPPhraseSpec>();
	ArrayList<NPPhraseSpec> indirectObjects = new ArrayList<NPPhraseSpec>();
	ArrayList<VPPhraseSpec> verbs = new ArrayList<VPPhraseSpec>();

	
	@SuppressWarnings("deprecation")
	@Override
	public String generateSentence(List<Phrase> phrases) {
		
		SimpleNLG simplenlg = SimpleNLG.getSimpleNLG();
		
		SPhraseSpec p = simplenlg.getNLGFactory().createClause();
		/* Iterates in the given phrases and find Subject, Objects, VErbs and Indirect Objects
		 * Since they have their attribute connection as null, they can/should be indexed firstly.
		 */
		for(Phrase phrase: phrases) {
			switch(phrase.getPhraseClass()) {
			case SUBJECT:
				subjects.add(simplenlg.getNLGFactory().createNounPhrase(phrase.getPhraseString()));
				break;
			case OBJECT:
				objects.add(simplenlg.getNLGFactory().createNounPhrase(phrase.getPhraseString()));
				break;
			case VERB:
				p.setFeature(Feature.MODAL, "can");
				p.setFeature(Feature.INTERROGATIVE_TYPE, InterrogativeType.YES_NO);
				verbs.add(simplenlg.getNLGFactory().createVerbPhrase(phrase.getPhraseString()));
				break;
			case VERB_HOW:
				VPPhraseSpec verb = simplenlg.getNLGFactory().createVerbPhrase(phrase.getPhraseString());
				p.setFeature(Feature.INTERROGATIVE_TYPE, InterrogativeType.HOW);
				verbs.add(verb);
				break;
			case INDIRECT_OBJECT:
				indirectObjects.add(simplenlg.getNLGFactory().createNounPhrase(phrase.getPhraseString()));
				break;
			default: break;
			}
		}
		
		/* Analyzing Complements, Modifiers and Determiners for their connections. */
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
					//TODO Handle exception - no connection for modifier!!!
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
		for (NPPhraseSpec indirectObj: indirectObjects) {
			p.setIndirectObject(indirectObj);
		}
		for (VPPhraseSpec verb: verbs) {
			p.setVerb(verb);
		}
		
		String output = simplenlg.getRealiser().realiseSentence(p);
		
		return output;
	}

	/**
	 * Looks for the right connection. For example, in the sentence "I have a red car", the
	 * connection in the phrase "red" is the phrase "car", because "red" is modifying "car".
	 * The list of PhraseElements already exist (this.subjects, this.objects, etc), so now
	 * the PhraseElement representation of the connectionPhrase attribute in class Phrase must
	 * be found.
	 * 
	 * @param connection
	 * @return
	 */
	private PhraseElement findConnection(Phrase connectionPhrase) {
		SimpleNLG simplenlg = SimpleNLG.getSimpleNLG();
		/* Looks for the right connection. */
		switch (connectionPhrase.getPhraseClass()) {
		case SUBJECT:
			for(NPPhraseSpec subject: subjects) {
				//Create a placeholder 'connect' and give it the information from connectionPhrase.
				NPPhraseSpec connect = simplenlg.getNLGFactory().createNounPhrase(connectionPhrase.getPhraseString());
				if (subject.getNoun().equals(connect.getNoun())) {
					return subject;
				}
			}
			break;
		case OBJECT:
			for(NPPhraseSpec object: objects) {
				NPPhraseSpec connect = simplenlg.getNLGFactory().createNounPhrase(connectionPhrase.getPhraseString());
				if (object.getNoun().equals(connect.getNoun())) {
					return object;
				}
			}
			break;
		case INDIRECT_OBJECT:
			for(NPPhraseSpec indirectObj: indirectObjects) {
				NPPhraseSpec connect = simplenlg.getNLGFactory().createNounPhrase(connectionPhrase.getPhraseString());
				if (indirectObj.getNoun().equals(connect.getNoun())) {
					return indirectObj;
				}
			}
			break;
		case VERB:
		case VERB_HOW:
			for(VPPhraseSpec verb: verbs) {
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


}