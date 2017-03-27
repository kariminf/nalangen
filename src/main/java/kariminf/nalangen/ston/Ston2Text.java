/* NaLanGen: Natural Language Generation tool:
 * It contains tools to generate texts in many languages
 * --------------------------------------------------------------------
 * Copyright (C) 2015 Abdelkrime Aries (kariminfo0@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package kariminf.nalangen.ston;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import kariminf.sentrep.UnivMap;
import kariminf.nalangen.nlg.UnivRealizer;
import kariminf.langpi.wordnet.SqliteRequestor;
import kariminf.langpi.wordnet.WNRequestor;
import kariminf.langpi.wordnet.SqliteReqExceptions.LangNotFound;
import kariminf.langpi.wordnet.SqliteReqExceptions.NoSqliteBase;
import kariminf.sentrep.ston.Parser;
import kariminf.sentrep.ston.StonLex;
import kariminf.sentrep.types.*;
import kariminf.sentrep.types.Relation.*;


public class Ston2Text extends Parser {

	private UnivRealizer realizer;
	private WNRequestor wordnet;
	private String lastID = "";
	//private String prep ="";
	private String lang = "eng";
	
	private boolean isAction = false;
	
	private int currentRelID = 0;
	
	private boolean ignoreReferences = false;
	
	private HashMap<String, ArrayList<Integer>> refs = 
			new HashMap<String, ArrayList<Integer>>();
	
	private HashSet<String> rolesID = new HashSet<String>();
	
	private UnivMap uMap;
	
	
	/**
	 * 
	 * @param realizer
	 * @param uMap
	 * @param lang
	 * @param basePath
	 */
	public Ston2Text(UnivRealizer realizer, UnivMap uMap, String lang, String basePath) {
		this.realizer = realizer;
		this.uMap = uMap;
		this.lang = lang;
		try {
			wordnet = SqliteRequestor.create(lang, basePath);
		} catch (NoSqliteBase | LangNotFound e) {
			System.out.println("Wordnet problem");
			e.printStackTrace();
		}
	}

	
	//=====================================================================
	//================== Implementing methods =============================
	//=====================================================================
			
			
	//=====================================================================
	//======================== ACTION METHODS =============================
	//=====================================================================

	@Override
	protected void beginAction(String id, int synSet) {
		String verb = wordnet.getWord(synSet, "VERB");
		realizer.beginSentPhrase(id, verb);
		lastID = id;
		isAction = true;
	}
	
	@Override
	protected void endAction(String id, int synSet) {
		realizer.endSentPhrase();
	}
	
	@Override
	protected boolean actionFailure() {
		return true;
		
	}

	@Override
	protected void addVerbSpecif(String tense, String modality, boolean progressive, boolean perfect, boolean negated) {
		VerbTense theTense = uMap.mapTense(tense);
		Modality theModal = uMap.mapModal(modality);
		//System.out.println("Modal= " + theModal);
		realizer.addVerbSpecif(theTense, theModal, progressive, perfect, negated);
		
	}
	
	@Override
	protected void addActionAdverb(int advSynSet, List<Integer> advSynSets) {
		String adverb = wordnet.getWord(advSynSet, "ADVERB");		
		ArrayList<String> adverbs = new ArrayList<String>();
		
		for(int advsyn : advSynSets){
			String adv = wordnet.getWord(advsyn, "ADVERB");
			adverbs.add(adv);
		}
		
		realizer.addAdverb(adverb, adverbs);
		
	}


	@Override
	protected boolean adverbFailure() {
		return true;
	}
	
	@Override
	protected void beginAgents() {
		realizer.beginSubject();
	}
	
	
	@Override
	protected void endAgents() {
		realizer.endSubject();
	}

	@Override
	protected void beginThemes() {
		realizer.beginObject();
	}

	@Override
	protected void endThemes() {
		realizer.endObject();
	}
	
	@Override
	protected void beginComparison(String type, List<Integer> adjSynSets) {
		Comparison comp = uMap.mapComparison(type);
		
		ArrayList<String> adjectives = new ArrayList<String>();
		
		for (int synset: adjSynSets){
			String adjective = wordnet.getWord(synset, "ADJECTIVE");
			if (adjective != null && adjective.trim().length() >  0)
				adjectives.add(adjective);
		}
		
		realizer.addComparison(comp, adjectives);
		
	}
	
	
	@Override
	protected void endComparison(String type, List<Integer> adjSynSets) {
		// TODO Auto-generated method stub
		
	}
	
	
	//=====================================================================
	//========================= ROLE METHODS ==============================
	//=====================================================================

	@Override
	protected void beginRole(String id, int synSet) {
		String noun = wordnet.getWord(synSet, "NOUN");
		realizer.beginNounPhrase(id, noun);
		lastID = id;
		isAction = false;
		
		rolesID.add(id);
	}//beginRole
	
	@Override
	protected void beginRole(String id, int synSet, String pronoun) {
		Pronoun p = uMap.mapPronoun(pronoun);
		realizer.beginNounPhrase(id, p);
		lastID = id;
		isAction = false;
		
	}//beginRole (pronoun)
	
	@Override
	protected void endRole(String id, int synSet) {
		
	}
	
	@Override
	protected boolean roleFailure() {
		return true;
	}//roleFailure
	
	@Override
	protected void addRoleSpecif(String name, String def, String quantity) {
		Determiner det = uMap.mapDeterminer(def);
		realizer.addNPSpecifs(name.replace("_", " "), det, quantity);
		
	}//addRoleSpecif

	@Override
	protected void addAdjective(int synSet, List<Integer> advSynSets) {
		String adjective = wordnet.getWord(synSet, "ADJECTIVE");		
		ArrayList<String> adverbs = new ArrayList<String>();
		
		for(int advsyn : advSynSets){
			String adverb = wordnet.getWord(advsyn, "ADVERB");
			adverbs.add(adverb);
		}
		
		realizer.addAdjective(adjective, adverbs);
		
	}//addAdjective


	@Override
	protected boolean adjectiveFailure() {
		return true;
	}//adjectiveFailure

	@Override
	protected void beginPRelatives() {
		ignoreReferences = true;
	}


	@Override
	protected void endPRelatives() {
		ignoreReferences = false;
	}
	
	//=====================================================================
	//======================= SENTENCE METHODS ============================
	//=====================================================================
	
	@Override
	protected void beginSentence(String type) {
		SentMood mood = uMap.mapMood(type);
		realizer.beginSentence(mood);
	}


	@Override
	protected void endSentence(String type) {
		realizer.endSentence();
	}


	@Override
	protected void beginActions(boolean mainClause) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void endActions(boolean mainClause) {
		// TODO Auto-generated method stub
		
	}
	
	//=====================================================================
	//======================== SHARED METHODS =============================
	//=====================================================================
	
	@Override
	protected void addConjunctions(List<String> IDs) {
		if (ignoreReferences)
			return;
		if (IDs.size() < 1) return;
		realizer.addConjunctions(IDs);
	}//addConjunctions
	
	
	@Override
	protected void beginRelative(String type) {
		
		type = type.toUpperCase();
		
		String params = "parentID:" + lastID;
		
		//If the predicate (destination) is a role
		//We reach a role only by adpositionals
		if (StonLex.isPredicateRole(type)){
			
			Adpositional adp = uMap.mapAdposition(type);
			realizer.beginPrepositionPhrase(adp, params);
			//System.out.println("Prepositional: " + adp);
			return;
		}
		
		//The destination is an action
		
		//The main clause is an action
		if (isAction){
			//from action to action (adverbials)
			Adverbial adv = uMap.mapAdverbial(type);
			realizer.beginAdverbialClause(adv, params);
			//System.out.println("Adverbial: " + adv);
			return;
		}
		
		//The main clause is a role
		//from role to action
		Relative rel = uMap.mapRelative(type);
		realizer.beginRelative(rel, params);
		//System.out.println("Complementizer: " + rel);
		
	}//beginRelative
	
	@Override
	protected void endRelative(String SP) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected boolean relativeFailure() {
		return true;
	}//relativeFailure
	
	
	//=====================================================================
	//========================= PARSE METHODS =============================
	//=====================================================================
	
	@Override
	protected void parseSuccess() {
		
	}

	@Override
	protected void parseFailure() {
		// TODO Auto-generated method stub
		
	}


}
