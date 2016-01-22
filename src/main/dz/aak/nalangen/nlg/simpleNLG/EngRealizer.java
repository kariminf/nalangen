package dz.aak.nalangen.nlg.simpleNLG;

import java.util.HashMap;
import java.util.Set;

import dz.aak.nalangen.nlg.UnivRealizer;

import simplenlg.features.Feature;
import simplenlg.features.LexicalFeature;
import simplenlg.features.Tense;
import simplenlg.framework.CoordinatedPhraseElement;
import simplenlg.framework.DocumentElement;
import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGFactory;
import simplenlg.framework.WordElement;
import simplenlg.lexicon.Lexicon;
import simplenlg.phrasespec.AdjPhraseSpec;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.PPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.phrasespec.VPPhraseSpec;
import simplenlg.realiser.english.Realiser;

public class EngRealizer implements UnivRealizer {
	
	private static Lexicon lexicon = Lexicon.getDefaultLexicon();
	private static NLGFactory nlgFactory = new NLGFactory(lexicon);
	private static Realiser realiser = new Realiser(lexicon);
	private static DocumentElement paragraph = nlgFactory.createParagraph();
	
	private NPPhraseSpec np;
	private HashMap<String, NPPhraseSpec> nps = new HashMap<String, NPPhraseSpec>();
	
	private SPhraseSpec sp;
	private HashMap<String, SPhraseSpec> sps = new HashMap<String, SPhraseSpec>();
	
	CoordinatedPhraseElement disjunctions;
	CoordinatedPhraseElement conjunctions;
	
	private String result = "";

	public EngRealizer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void beginSentence(String id, String verb) {
		VPPhraseSpec verbP = nlgFactory.createVerbPhrase(verb);
		sp = nlgFactory.createClause();
		
		sp.setVerb(verbP);
		
		sps.put(id, sp);
		System.out.println("Begin sentence: " + verb);
		
	}

	@Override
	public void endSentence() {
		paragraph.addComponent(sp);
		
		System.out.println("End sentence");
		
	}

	@Override
	public void addVerbSpecif(String tense, String modality, boolean progressive, boolean negated) {
		
		if(modality.matches("NONE")){
			sp.setFeature(Feature.TENSE, Tense.valueOf(tense));
		} else {
			String modal = modality.toLowerCase();
			if (tense.matches("PAST")){
				System.out.println(modal);
				WordElement pastmodal = lexicon.getWord(modal);
				modal = pastmodal.getFeatureAsString(LexicalFeature.PAST);
				//modal = "had to";
				System.out.println(modal);
			}
			sp.setFeature(Feature.MODAL, modal);
		}
		
		
		sp.setFeature(Feature.PROGRESSIVE, progressive);
		sp.setFeature(Feature.NEGATED, negated);

			
		System.out.println("\tadd tense and aspect");
	}


	@Override
	public void beginNounPhrase(String id, String noun) {
		np = nlgFactory.createNounPhrase("the", noun);
		
		nps.put(id, np);
		System.out.println("\tnoun: " + noun);
	}

	@Override
	public void addAdjective(String adjective, Set<String> adverbs) {
		AdjPhraseSpec adjectiveP = nlgFactory.createAdjectivePhrase(adjective);
		
		for(String adverb : adverbs){
			adjectiveP.addPreModifier(nlgFactory.createAdverbPhrase(adverb));
		}
		
		np.addModifier(adjectiveP);
		
		System.out.println("\t\tAdd adjective: " + adjective);
		
	}

	@Override
	public void endParagraph() {
		NLGElement el = realiser.realise(paragraph);
		result = el.getRealisation();
		
		//System.out.println("End Phrase");
	}

	@Override
	public void beginParagraph() {
		
	}

	@Override
	public String getText() {
		return result;
	}

	@Override
	public void beginDisjunction() {
		conjunctions = nlgFactory.createCoordinatedPhrase();
		System.out.println("\tbegin disjunction: ");
	}

	@Override
	public void addConjunction(String nounPhraseID) {
		System.out.println("\tadd conjunction: " + nounPhraseID);
		if (nps.containsKey(nounPhraseID)){
			conjunctions.addCoordinate(nps.get(nounPhraseID));
		}
		
	}

	@Override
	public void endDisjunction() {
		disjunctions.addCoordinate(conjunctions);
		System.out.println("\tend disjunction: ");
	}

	@Override
	public void beginSubject() {
		disjunctions = nlgFactory.createCoordinatedPhrase();
		disjunctions.setFeature(Feature.CONJUNCTION, "or");
		System.out.println("Add subject");
	}

	@Override
	public void beginObject() {
		disjunctions = nlgFactory.createCoordinatedPhrase();
		disjunctions.setFeature(Feature.CONJUNCTION, "or");
		System.out.println("Add object");
	}

	@Override
	public void endSubject() {
		sp.setSubject(disjunctions);
		System.out.println("end subject");
	}

	@Override
	public void endObject() {
		sp.setObject(disjunctions);
		System.out.println("end object");
	}

	@Override
	public void addPrepositionPhrase(String parentID, String preposition) {
		if (! sps.containsKey(parentID)) return;
		
		SPhraseSpec parent = sps.get(parentID);
		
		PPPhraseSpec prepositional = nlgFactory.createPrepositionPhrase(preposition);
		
		disjunctions = nlgFactory.createCoordinatedPhrase();
		disjunctions.setFeature(Feature.CONJUNCTION, "or");
		prepositional.addComplement(disjunctions);
		parent.addPostModifier(prepositional);
		
	}

}
