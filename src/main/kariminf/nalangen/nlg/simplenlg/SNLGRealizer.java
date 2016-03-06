package kariminf.nalangen.nlg.simplenlg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import kariminf.nalangen.nlg.ModelingMap;
import kariminf.nalangen.nlg.RealizerMap;
import kariminf.nalangen.nlg.Types;
import kariminf.nalangen.nlg.UnivRealizer;
import kariminf.nalangen.nlg.Types.Determiner;


import simplenlg.features.ClauseStatus;
import simplenlg.features.DiscourseFunction;
import simplenlg.features.Feature;
import simplenlg.features.LexicalFeature;
import simplenlg.features.Tense;
import simplenlg.framework.CoordinatedPhraseElement;
import simplenlg.framework.NLGFactory;
import simplenlg.framework.PhraseElement;
import simplenlg.framework.WordElement;
import simplenlg.lexicon.Lexicon;
import simplenlg.phrasespec.AdjPhraseSpec;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.PPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.phrasespec.VPPhraseSpec;
import simplenlg.realiser.Realiser;

public abstract class SNLGRealizer extends UnivRealizer {
	
	private boolean debugMsg = false;
	
	private Lexicon lexicon;
	private NLGFactory nlgFactory;
	private Realiser realiser;
	//private static DocumentElement paragraph = nlgFactory.createParagraph();
	
	private NPPhraseSpec np;
	private HashMap<String, NPPhraseSpec> nps = new HashMap<String, NPPhraseSpec>();
	
	private SPhraseSpec sp;
	private HashMap<String, SPhraseSpec> sps = new HashMap<String, SPhraseSpec>();
	
	
	private PhraseElement pe;
	
	private CoordinatedPhraseElement disjunctions;
	private CoordinatedPhraseElement conjunctions;
	
	
	private String lastNP = "";
	//private String lastVP = "";
	
	private String result = "";

	public SNLGRealizer(RealizerMap rlMap, ModelingMap mdMap, Lexicon lexicon) {
		super(rlMap, mdMap);
		this.lexicon = lexicon;
		nlgFactory = new NLGFactory(lexicon);
		realiser = new Realiser();
	}

	@Override
	public void beginSentPhrase(String id, String verb) {
		
		if (sps.containsKey(id)){
			sp = sps.get(id);
		} else {
			sp = nlgFactory.createClause();
			sps.put(id, sp);
		}
		pe = sp;	
		sp.setVerb(verb);
		//lastVP = id;
		if (debugMsg)
			System.out.println("Begin verbal phrase: " + id + ", verb= " + verb);
		
	}

	@Override
	public void endSentPhrase() {
		if (debugMsg)
			System.out.println("End verbal phrase");
		
	}

	@Override
	public void addVerbSpecif(Types.Tense tense, Types.Modality modality, boolean progressive, boolean negated) {
		
		//Types.Tense theTense = mdMap.mapTense(tense);
		String rTense = nlMap.getTense(tense);
		
		//Types.Modality theModality = mdMap.mapModal(modality);
		
		if(modality == Types.Modality.NONE){
			//System.out.println(sp.getVerb().toString());
			sp.setFeature(Feature.TENSE, Tense.valueOf(rTense));
		} else {
			
			String modal = nlMap.getModal(modality);
			if (tense == Types.Tense.PAST){
				WordElement pastmodal = lexicon.getWord(modal);
				modal = pastmodal.getFeatureAsString(LexicalFeature.PAST);
				//modal = "had to";
				if (debugMsg)
					System.out.println("    the modal=" +  modal);
			}
			sp.setFeature(Feature.MODAL, modal);
		}
		
		
		sp.setFeature(Feature.PROGRESSIVE, progressive);
		sp.setFeature(Feature.NEGATED, negated);

		if (debugMsg){
			System.out.print("    (tense, modal, progressive, negated): ");
		System.out.println("(" + tense + ", " + modality + ", " + progressive + ", " + negated + ")");
	
		}
	}


	@Override
	public void beginNounPhrase(String id, String noun) {
		np = nlgFactory.createNounPhrase("", noun);
		pe = np;
		nps.put(id, np);
		lastNP = id;
		
		if (debugMsg)
			System.out.println("Begin noun phrase: " + id + ", noun= " + noun);
	}
	
	@Override
	public void addNPSpecifs(String name, String def, String quantity){
		if (name != null && name.length() > 0){
			System.out.println(">>" + name);
			np = nlgFactory.createNounPhrase("", name);
			nps.put(lastNP, np);
		}
		
		Types.Determiner det= mdMap.mapDeterminer(def);
		
		String determiner = nlMap.getDeterminer(det);
		np.setSpecifier(determiner);
		
		if(quantity.length()<1 || quantity.matches("1")) return;
		np.setPlural(true);
		
		if(quantity.matches("PL")) return;
		
		np.addPreModifier(quantity);
		
	}

	@Override
	public void addAdjective(String adjective, Set<String> adverbs) {
		AdjPhraseSpec adjectiveP = nlgFactory.createAdjectivePhrase(adjective);
		
		for(String adverb : adverbs){
			adjectiveP.addPreModifier(nlgFactory.createAdverbPhrase(adverb));
		}
		
		np.addModifier(adjectiveP);
		
		if (debugMsg)
			System.out.println("    Add adjective: " + adjective);
		
	}

	@Override
	public String getText() {
		/*NLGElement el = realiser.realise(paragraph);
		result = el.getRealisation();*/
		//return realiser.realiseSentence(paragraph);
		return result;
	}

	@Override
	public void addConjunctions(Set<String> phraseIDs) {
		
		if (debugMsg)
			System.out.println("        add conjunctions: " + phraseIDs);
		/*
		if (complementPronoun.length() > 0){
			complementPronoun += "|";
			for (String phraseID: phraseIDs)
				complementPronoun += phraseID + ",";
			return;
		}
		
		if (debugMsg)
			System.out.println("       ...");
		*/
		conjunctions = nlgFactory.createCoordinatedPhrase();
		conjunctions.setFeature(Feature.CONJUNCTION, "and");

		for (String phraseID: phraseIDs){
			if (nps.containsKey(phraseID)){
				conjunctions.addCoordinate(nps.get(phraseID));
				if (debugMsg)
					System.out.println("       " + phraseID + ">> Nominal phrase");
				continue;
			}

			if (sps.containsKey(phraseID)){
				conjunctions.addCoordinate(sps.get(phraseID));
				if (debugMsg)
					System.out.println("       " + phraseID + ">> Verbal phrase");
				continue;
			}
			
			//When not found; it is a late verbal phrase
			sp = nlgFactory.createClause();
			sps.put(phraseID, sp);
			conjunctions.addCoordinate(sp);
		}
		
		disjunctions.addCoordinate(conjunctions);
		
	}

	@Override
	public void beginSubject() {
		disjunctions = nlgFactory.createCoordinatedPhrase();
		disjunctions.setFeature(Feature.CONJUNCTION, "or");
		if (debugMsg)
			System.out.println("    Begin subject");
	}

	@Override
	public void beginObject() {
		disjunctions = nlgFactory.createCoordinatedPhrase();
		disjunctions.setFeature(Feature.CONJUNCTION, "or");
		if (debugMsg)
			System.out.println("    Begin object");
	}

	@Override
	public void endSubject() {
		sp.setSubject(disjunctions);
		disjunctions = null;
		if (debugMsg)
			System.out.println("    End subject");
	}

	@Override
	public void endObject() {
		sp.setObject(disjunctions);
		disjunctions = null;
		if (debugMsg)
			System.out.println("    End object");
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
		
		if (debugMsg)
			System.out.println("    Add preposition: " + preposition);
		
	}

	@Override
	public void beginSentence(Types.Mood type) {
		disjunctions = nlgFactory.createCoordinatedPhrase();
		disjunctions.setFeature(Feature.CONJUNCTION, "or");
		if (debugMsg)
			System.out.println("Begin sentence type:" + type);
		
	}

	@Override
	public void endSentence() {
		//paragraph.addComponent(disjunctions);
		result += realiser.realiseSentence(disjunctions) + " ";
		if (debugMsg){
			System.out.println("Sent:" + realiser.realiseSentence(disjunctions));
			System.out.println("End sentence");
		}
		
	}

	@Override
	public void beginComplementizer(String pronoun) {
		//complementPronoun = pronoun;
		
		disjunctions = nlgFactory.createCoordinatedPhrase();
		disjunctions.setFeature(Feature.CONJUNCTION, "or");
		//SPhraseSpec clause = nlgFactory.createClause();
		//clause.setFeature(Feature.COMPLEMENTISER, "lool");
		//clause.setComplement(disjunctions);
		//TODO complimentizer pronoun
	
		pe.addComplement(disjunctions);
		if (debugMsg)
			System.out.println("    Begin complimentizer:" + pronoun);
		
	}

	@Override
	public void endComplementizer() {
		
	}

	@Override
	public void showDebugMsg(boolean yes) {
		debugMsg = yes;
		
	}


}
