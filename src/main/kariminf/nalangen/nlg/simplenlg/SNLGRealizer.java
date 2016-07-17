package kariminf.nalangen.nlg.simplenlg;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import kariminf.nalangen.nlg.UnivRealizer;

import kariminf.sentrep.LangMap;
import kariminf.sentrep.univ.types.*;

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
import simplenlg.realiser.Realiser;

public abstract class SNLGRealizer implements UnivRealizer {
	
	private boolean debugMsg = false;
	
	private Lexicon lexicon;
	private NLGFactory nlgFactory;
	private Realiser realiser;
	
	//This is to prevent passive voice when we use relative subject "who"
	private boolean notRelSubject = true;
	
	
	//private HashMap<String, HashMap<String, Relation>> relations = new HashMap<String, HashMap<String, Relation>>();
	
	//private static DocumentElement paragraph = nlgFactory.createParagraph();
	
	private NPPhraseSpec np;
	private HashMap<String, NPPhraseSpec> nps = new HashMap<String, NPPhraseSpec>();
	
	private SPhraseSpec sp;
	private HashMap<String, SPhraseSpec> sps = new HashMap<String, SPhraseSpec>();
	
	//For complementizer: can be for sp or np
	private PhraseElement pe;
	
	private CoordinatedPhraseElement disjunctions;
	private CoordinatedPhraseElement conjunctions;
	
	/*
	private ArrayDeque<CoordinatedPhraseElement> disjunctionsQue =
			new ArrayDeque<CoordinatedPhraseElement>();
	private ArrayDeque<CoordinatedPhraseElement> conjunctionsQue =
			new ArrayDeque<CoordinatedPhraseElement>();
	*/
	
	private String lastNP = "";
	//private String lastVP = "";
	
	//To detect if the nominal phrase is the last to help the complementizer
	
	private String result = "";
	
	private LangMap nlMap;

	public SNLGRealizer(LangMap nlMap, Lexicon lexicon) {
		this.nlMap = nlMap;
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
		//sp.getSubject() == null
		
		if ( sp.getSubject() != null && sp.getFeatureAsBoolean(Feature.PASSIVE)){
			sp.setFeature(Feature.PASSIVE, false);
			//System.out.println("no subject= " + lastVP);
		}

		if (debugMsg)
			System.out.println("End verbal phrase");
		
	}

	@Override
	public void addVerbSpecif(VerbTense tense, Modality modality, boolean progressive, boolean negated) {
		
		//Types.Tense theTense = mdMap.mapTense(tense);
		String rTense = nlMap.getTense(tense);
		
		//Types.Modality theModality = mdMap.mapModal(modality);
		
		if(modality == Modality.NONE){
			//System.out.println(sp.getVerb().toString());
			sp.setFeature(Feature.TENSE, Tense.valueOf(rTense));
		} else {
			
			String modal = nlMap.getModal(modality);
			if (tense == VerbTense.PAST){
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
			System.out.println("(" + tense + ", " + modality + ", " 
					+ progressive + ", " + negated + ")");
	
		}
	}


	@Override
	public void beginNounPhrase(String id, String noun) {
		np = nlgFactory.createNounPhrase("", noun);
		pe = np;
		//A pronoun
		if (nps.containsKey(id)){
			np.addPreModifier(nps.get(id));
		}
		nps.put(id, np);
		lastNP = id;
	
		if (debugMsg)
			System.out.println("Begin noun phrase: " + id + ", noun= " + noun);
	}
	
	@Override
	public void beginNounPhrase(String id, Pronoun p) {
		String pronoun = nlMap.getPronoun(p);
		//System.out.println( p);
		np = nlgFactory.createNounPhrase(pronoun);
		pe = np;
		nps.put(id, np);
		lastNP = id;
		
		if (debugMsg)
			System.out.println("Begin noun phrase: " + id + ", pronoun= " + pronoun);
	}
	
	@Override
	public void addNPSpecifs(String name, Determiner det, String quantity){
		if (name != null && name.length() > 0){
			//System.out.println(">>" + name);
			if (nps.containsKey(lastNP));
				nps.remove(lastNP);
			np = nlgFactory.createNounPhrase("", name);
			nps.put(lastNP, np);
			pe = np;
		} else {		
			String determiner = nlMap.getDeterminer(det);
			np.setSpecifier(determiner);
		}
		
		quantity = quantity.toLowerCase();
		
		if(quantity.length() < 1 || quantity.equals("1")) return;
		
		if (quantity.startsWith("o")){
			quantity = quantity.substring(1);
			
			
			if (! quantity.endsWith("pl")){
				np.addPreModifier(getOrdinal(quantity));
				return;
			}
			quantity = quantity.substring(0, quantity.length()-2);
			quantity = getOrdinal(quantity);
		}
		
		np.setPlural(true);
		
		if(quantity.equals("pl")) return;
		
		np.addPreModifier(quantity);
		
	}

	@Override
	public void addAdjective(String adjective, List<String> adverbs) {
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
	public void addConjunctions(List<String> phraseIDs) {
		
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
		conjunctions.setFeature(Feature.CONJUNCTION, nlMap.getCoordination(Coordination.AND));

		for (String phraseID: phraseIDs){
			
			if (nps.containsKey(phraseID)){
				NPPhraseSpec npph = nps.get(phraseID);
				conjunctions.addCoordinate(npph);
				if(npph.isPlural())
					conjunctions.setPlural(true);
				if (debugMsg)
					System.out.println("       " + phraseID + ">> Nominal phrase");
				continue;
			}

			if (sps.containsKey(phraseID)){
				SPhraseSpec spTmp = sps.get(phraseID);
				
				if (spTmp.getSubject() == null && notRelSubject){//&& notRelSubject
					spTmp.setFeature(Feature.PASSIVE, true);
					//System.out.println("no subject= " + lastVP);
				}
				conjunctions.addCoordinate(spTmp);
				if (debugMsg)
					System.out.println("       " + phraseID + ">> Verbal phrase");
				continue;
			}
			
			//When not found; it is a late verbal phrase
			sp = nlgFactory.createClause();
			if (notRelSubject){//&& notRelSubject
				sp.setFeature(Feature.PASSIVE, true);
				//System.out.println("no subject= " + lastVP);
			}
			sps.put(phraseID, sp);
			conjunctions.addCoordinate(sp);
		}
		
		disjunctions.addCoordinate(conjunctions);
		
		//Coordinated phrase elements doesn't take the child's plural property
		if (conjunctions.isPlural())
			disjunctions.setPlural(true);
		
	}

	@Override
	public void beginSubject() {
		disjunctions = nlgFactory.createCoordinatedPhrase();
		disjunctions.setFeature(Feature.CONJUNCTION, nlMap.getCoordination(Coordination.OR));
		if (debugMsg)
			System.out.println("    Begin subject");
	}

	@Override
	public void beginObject() {
		disjunctions = nlgFactory.createCoordinatedPhrase();
		disjunctions.setFeature(Feature.CONJUNCTION, nlMap.getCoordination(Coordination.OR));
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
	public void addPrepositionPhrase(Relation preposition, String params) {
		
		if (! params.contains("parentID:"))
			return;
		String parentID = params.substring(params.indexOf("parentID") + 9);
		parentID = parentID.split(",")[0];
		
		if (! sps.containsKey(parentID)) return;
		
		String prep = nlMap.getAdposition(preposition, "");
		
		SPhraseSpec parent = sps.get(parentID);
		
		PPPhraseSpec prepositional = nlgFactory.createPrepositionPhrase(prep);
		
		disjunctions = nlgFactory.createCoordinatedPhrase();
		disjunctions.setFeature(Feature.CONJUNCTION, nlMap.getCoordination(Coordination.OR));
		prepositional.addComplement(disjunctions);
		parent.addPostModifier(prepositional);
		
		if (debugMsg)
			System.out.println("    Add preposition: " + prep);
		
	}

	@Override
	public void beginSentence(SentMood type) {
		disjunctions = nlgFactory.createCoordinatedPhrase();
		disjunctions.setFeature(Feature.CONJUNCTION, nlMap.getCoordination(Coordination.OR));
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
	public void beginComplementizer(Relation pronoun, String params) {
		//complementPronoun = pronoun;
		String relPron = nlMap.getAdposition(pronoun, params);
		disjunctions = nlgFactory.createCoordinatedPhrase();
		disjunctions.setFeature(Feature.CONJUNCTION, nlMap.getCoordination(Coordination.OR));
		//SPhraseSpec clause = nlgFactory.createClause();
		//clause.setFeature(Feature.COMPLEMENTISER, "lool");
		//clause.setComplement(disjunctions);
		//TODO complimentizer pronoun
		disjunctions.addPreModifier(relPron);
		pe.addPostModifier(disjunctions);
		//pe.addComplement(disjunctions);
		
		if (pronoun == Relation.SUBJ){
			notRelSubject = false;
		}
		else{
			notRelSubject = true;
		}
			
		
		if (debugMsg)
			System.out.println("    Begin complimentizer:" + relPron);
		
	}
	
	@Override
	public void addComparison(Comparison comp, List<String> adjs){
		
		boolean hasAdjectives = false;
		
		if (adjs != null && adjs.size() > 0)
			hasAdjectives = true;
		
		CoordinatedPhraseElement adjCoord = 
				nlgFactory.createCoordinatedPhrase();
		adjCoord.setFeature(Feature.CONJUNCTION, nlMap.getCoordination(Coordination.AND));
		
		String pre = nlMap.getPreComparison(comp, hasAdjectives);
		String post = nlMap.getPostComparison(comp, hasAdjectives);
		String feature = "";
		
		if (comp == Comparison.MORE)
			feature = Feature.IS_COMPARATIVE;
		else if (comp == Comparison.MOST)
			feature = Feature.IS_SUPERLATIVE;
		
		adjCoord.addPreModifier(pre);
		adjCoord.addPostModifier(post);
		
		
		for (String adj: adjs){
			AdjPhraseSpec adjPh = nlgFactory.createAdjectivePhrase(adj);
			
			if (feature.length() > 0)
				adjPh.setFeature(feature, true);
			
			adjCoord.addCoordinate(adjPh);
		}
		
		sp.addComplement(adjCoord);
		
		disjunctions = nlgFactory.createCoordinatedPhrase();
		disjunctions.setFeature(Feature.CONJUNCTION, nlMap.getCoordination(Coordination.OR));
		sp.addComplement(disjunctions);
		
	}

	@Override
	public void endComplementizer() {
		notRelSubject = true;
	}

	@Override
	public void showDebugMsg(boolean yes) {
		debugMsg = yes;
		
	}
	
	abstract protected String getOrdinal(String number);


}
