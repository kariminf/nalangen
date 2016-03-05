package dz.aak.nalangen.nlg.simplenlg;

import java.util.HashMap;
import java.util.Set;

import dz.aak.nalangen.nlg.ModelingMap;
import dz.aak.nalangen.nlg.UnivRealizer;
import dz.aak.nalangen.nlg.Types;

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
import simplenlg.realiser.Realiser;

public class FraRealizer extends UnivRealizer {
	
	private static Lexicon lexicon = new simplenlg.lexicon.french.XMLLexicon();
	private static NLGFactory nlgFactory = new NLGFactory(lexicon);
	private static Realiser realiser = new Realiser();
	private static DocumentElement paragraph = nlgFactory.createParagraph();
	
	private NPPhraseSpec np;
	private HashMap<String, NPPhraseSpec> nps = new HashMap<String, NPPhraseSpec>();
	
	private SPhraseSpec sp;
	private HashMap<String, SPhraseSpec> sps = new HashMap<String, SPhraseSpec>();
	
	CoordinatedPhraseElement disjunctions;
	CoordinatedPhraseElement conjunctions;
	
	private String result = "";

	public FraRealizer(ModelingMap mdMap) {
		super(new FraSnlgMap(), mdMap);
	}

	@Override
	public void beginSentPhrase(String id, String verb) {
		VPPhraseSpec verbP = nlgFactory.createVerbPhrase(verb);
		sp = nlgFactory.createClause();
		
		sp.setVerb(verbP);
		
		sps.put(id, sp);
		//System.out.println("Begin phrase: " + verb);
		
	}

	@Override
	public void endSentPhrase() {
		paragraph.addComponent(sp);
		
		//System.out.println("End phrase");
		
	}

	@Override
	public void addVerbSpecif(String tense, String modality, boolean progressive, boolean negated) {
		
		Types.Tense theTense = mdMap.mapTense(tense);
		String rTense = nlMap.getTense(theTense);
		
		Types.Modality theModality = mdMap.mapModal(modality);
				
		if(theModality == Types.Modality.NONE){
			sp.setFeature(Feature.TENSE, Tense.valueOf(rTense));
		} else {
			
			String modal = nlMap.getModal(theModality);
			if (theTense == Types.Tense.PAST){
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

			
		//System.out.println("\tadd tense and aspect");
	}


	@Override
	public void beginNounPhrase(String id, String noun) {
		np = nlgFactory.createNounPhrase("le", noun);
		
		nps.put(id, np);
		//System.out.println("\tnoun: " + noun);
	}

	@Override
	public void addAdjective(String adjective, Set<String> adverbs) {
		AdjPhraseSpec adjectiveP = nlgFactory.createAdjectivePhrase(adjective);
		
		for(String adverb : adverbs){
			adjectiveP.addPreModifier(nlgFactory.createAdverbPhrase(adverb));
		}
		
		np.addModifier(adjectiveP);
		
		//System.out.println("\t\tAdd adjective: " + adjective);
		
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
		//System.out.println("\tbegin disjunction: ");
	}

	@Override
	public void addConjunction(String nounPhraseID) {
		//System.out.println("\tadd conjunction: " + nounPhraseID);
		if (nps.containsKey(nounPhraseID)){
			conjunctions.addCoordinate(nps.get(nounPhraseID));
		}
		
	}

	@Override
	public void endDisjunction() {
		disjunctions.addCoordinate(conjunctions);
		//System.out.println("\tend disjunction: ");
	}

	@Override
	public void beginSubject() {
		disjunctions = nlgFactory.createCoordinatedPhrase();
		disjunctions.setFeature(Feature.CONJUNCTION, "ou");
		//System.out.println("Add subject");
	}

	@Override
	public void beginObject() {
		disjunctions = nlgFactory.createCoordinatedPhrase();
		disjunctions.setFeature(Feature.CONJUNCTION, "ou");
		//System.out.println("Add object");
	}

	@Override
	public void endSubject() {
		sp.setSubject(disjunctions);
		//System.out.println("end subject");
	}

	@Override
	public void endObject() {
		sp.setObject(disjunctions);
		disjunctions = null;
		//System.out.println("end object");
	}

	@Override
	public void addPrepositionPhrase(String parentID, String preposition) {
		if (! sps.containsKey(parentID)) return;
		
		SPhraseSpec parent = sps.get(parentID);
		
		PPPhraseSpec prepositional = nlgFactory.createPrepositionPhrase(preposition);
		
		disjunctions = nlgFactory.createCoordinatedPhrase();
		disjunctions.setFeature(Feature.CONJUNCTION, "ou");
		prepositional.addComplement(disjunctions);
		parent.addPostModifier(prepositional);
		
	}

	@Override
	public void beginSentence(String type) {
		// TODO Auto-generated method stub
		
		disjunctions = nlgFactory.createCoordinatedPhrase();
		disjunctions.setFeature(Feature.CONJUNCTION, "ou");
		
	}

	@Override
	public void endSentence() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beginComplementizer(String pronoun) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endComplementizer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void linkComplimentizers() {
		// TODO Auto-generated method stub
		
	}

}
