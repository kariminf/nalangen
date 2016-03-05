package dz.aak.nalangen.nlg.simplenlg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import simplenlg.realiser.english.Realiser;

public class EngRealizer extends UnivRealizer {
	
	private static final boolean debugMsg = true;
	
	private static Lexicon lexicon = Lexicon.getDefaultLexicon();
	private static NLGFactory nlgFactory = new NLGFactory(lexicon);
	private static Realiser realiser = new Realiser(lexicon);
	//private static DocumentElement paragraph = nlgFactory.createParagraph();
	
	private NPPhraseSpec np;
	private HashMap<String, NPPhraseSpec> nps = new HashMap<String, NPPhraseSpec>();
	
	private SPhraseSpec sp;
	private HashMap<String, SPhraseSpec> sps = new HashMap<String, SPhraseSpec>();
	
	private HashMap<String, ArrayList<String>> refs = 
			new HashMap<String, ArrayList<String>>();
	
	private String complementPronoun = "";
	
	private CoordinatedPhraseElement disjunctions;
	private CoordinatedPhraseElement conjunctions;
	
	
	private String lastNP = "";
	private String lastVP = "";
	
	private String result = "";

	public EngRealizer(ModelingMap mdMap) {
		super(new EngSnlgMap(), mdMap);
	}

	@Override
	public void beginSentPhrase(String id, String verb) {
		VPPhraseSpec verbP = nlgFactory.createVerbPhrase(verb);
		sp = nlgFactory.createClause();
		
		sp.setVerb(verbP);
		
		sps.put(id, sp);
		lastVP = id;
		if (debugMsg)
			System.out.println("Begin verbal phrase: " + id + ", verb= " + verb);
		
	}

	@Override
	public void endSentPhrase() {
		if (debugMsg)
			System.out.println("End verbal phrase: " + lastVP);
		
	}

	@Override
	public void addVerbSpecif(Types.Tense tense, Types.Modality modality, boolean progressive, boolean negated) {
		
		//Types.Tense theTense = mdMap.mapTense(tense);
		String rTense = nlMap.getTense(tense);
		
		//Types.Modality theModality = mdMap.mapModal(modality);
				
		if(modality == Types.Modality.NONE){
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
		np = nlgFactory.createNounPhrase("the", noun);
		
		nps.put(id, np);
		lastNP = id;
		
		if (debugMsg)
			System.out.println("Begin noun phrase: " + id + ", noun= " + noun);
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
	public void endParagraph() {
		//NLGElement el = realiser.realise(paragraph);
		//result = el.getRealisation();
		
		//System.out.println("End Phrase");
	}

	@Override
	public void beginParagraph() {
		
	}

	@Override
	public String getText() {
		/*NLGElement el = realiser.realise(paragraph);
		result = el.getRealisation();*/
		//return realiser.realiseSentence(paragraph);
		return result;
	}

	@Override
	public void addConjunctions(List<String> phraseIDs) {
		
		if (debugMsg)
			System.out.println("        add conjunctions: " + phraseIDs);
		
		if (complementPronoun.length() > 0){
			complementPronoun += "|";
			for (String phraseID: phraseIDs)
				complementPronoun += phraseID + ",";
			return;
		}
		
		if (debugMsg)
			System.out.println("       ...");

		conjunctions = nlgFactory.createCoordinatedPhrase();
		conjunctions.setFeature(Feature.CONJUNCTION, "and");

		for (String phraseID: phraseIDs){
			if (nps.containsKey(phraseID)){
				conjunctions.addCoordinate(nps.get(phraseID));
				if (debugMsg)
					System.out.println("       " + phraseID + ">> Nominal phrase");
			}

			if (sps.containsKey(phraseID)){
				conjunctions.addCoordinate(sps.get(phraseID));
				if (debugMsg)
					System.out.println("       " + phraseID + ">> Verbal phrase");
			}
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
		complementPronoun = pronoun;
		if (debugMsg)
			System.out.println("    Begin complimentizer:" + pronoun);
		
	}

	@Override
	public void endComplementizer() {
		
		if (lastNP.length() < 1) return;
		
		ArrayList<String> npComp = (refs.containsKey(lastNP))?
				refs.get(lastNP):
					new ArrayList<String>();
		
		refs.put(lastNP, npComp);
		
		npComp.add(complementPronoun);
		
		if (debugMsg)
			System.out.println("    End complimentizer");
		
		complementPronoun = "";
		
	}

	@Override
	public void linkComplimentizers() {
		
		
		if (debugMsg)
			System.out.println("... Linking complimentizers");
		
		for (String mainC : refs.keySet()){
			if (! nps.containsKey(mainC)) continue;
			NPPhraseSpec np = nps.get(mainC);
			CoordinatedPhraseElement relPhEls = //relative phrase elements
					nlgFactory.createCoordinatedPhrase();
			relPhEls.setFeature(Feature.CONJUNCTION, "and");
			System.out.println(refs.get(mainC));
			
			for (String disConj: refs.get(mainC)){
				//split using | then using ,
				//The first string before | is the pronoun
				String[] disjs = disConj.split("\\|");
				if (disjs.length < 2) continue;
				String pronoun = disjs[0];
				CoordinatedPhraseElement disjEl = //disjuncted elements
						nlgFactory.createCoordinatedPhrase();
				disjEl.setFeature(Feature.CONJUNCTION, "or");
				disjEl.setFeature(Feature.COMPLEMENTISER, pronoun);
				for (String disj: disjs){
					String[] conjs = disj.split(",");
					if (conjs.length < 1) continue;
					CoordinatedPhraseElement conjEl = //conjuncted elements
							nlgFactory.createCoordinatedPhrase();
					conjEl.setFeature(Feature.CONJUNCTION, "and");
					for (String conj: conjs){
						if (conj.trim().length() < 1) continue;
						
						if (! sps.containsKey(conj)) continue;
						
						SPhraseSpec sp = sps.get(conj);
						conjEl.addCoordinate(sp);
						
					}
					disjEl.addCoordinate(conjEl);
					
				}
				relPhEls.addCoordinate(disjEl);	
			}
			
			np.addComplement(relPhEls);	
		}
		
		refs.clear();
		
	}


}
