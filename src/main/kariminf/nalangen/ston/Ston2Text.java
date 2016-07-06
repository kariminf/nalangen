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
import java.util.Set;

import kariminf.sentrep.UnivMap;
import kariminf.sentrep.univ.types.*;

import kariminf.nalangen.nlg.UnivRealizer;
import kariminf.langpi.wordnet.SqliteRequestor;
import kariminf.langpi.wordnet.WNRequestor;
import kariminf.langpi.wordnet.SqliteReqExceptions.LangNotFound;
import kariminf.langpi.wordnet.SqliteReqExceptions.NoSqliteBase;
import kariminf.sentrep.ston.Parser;
import kariminf.sentrep.ston.types.SPronoun;


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
	private UnivMap uMap;
	
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


	@Override
	protected void addAction(String id, int synSet) {
		String verb = wordnet.getWord(synSet, "VERB");
		realizer.beginSentPhrase(id, verb);
		lastID = id;
		isAction = true;
	}

	@Override
	protected void addVerbSpecif(String tense, String modality, boolean progressive, boolean negated) {
		VerbTense theTense = uMap.mapTense(tense);
		Modality theModal = uMap.mapModal(modality);
		//System.out.println("Modal= " + theModal);
		realizer.addVerbSpecif(theTense, theModal, progressive, negated);
		
	}

	@Override
	protected void actionFail() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addRole(String id, int synSet) {
		String noun = wordnet.getWord(synSet, "NOUN");
		realizer.beginNounPhrase(id, noun);
		lastID = id;
		isAction = false;
	}

	@Override
	protected void addAdjective(int synSet, List<Integer> advSynSets) {
		String adjective = wordnet.getWord(synSet, "ADJECTIVE");		
		ArrayList<String> adverbs = new ArrayList<String>();
		
		for(int advsyn : advSynSets){
			String adverb = wordnet.getWord(advsyn, "ADVERB");
			adverbs.add(adverb);
		}
		
		realizer.addAdjective(adjective, adverbs);
		
	}


	@Override
	protected void adjectiveFail() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void roleFail() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void parseSuccess() {
		
	}

	@Override
	protected void beginAgents() {
		realizer.beginSubject();
	}

	@Override
	protected void beginThemes() {
		realizer.beginObject();
	}

	@Override
	protected void addConjunctions(List<String> IDs) {
		if (ignoreReferences)
			return;
		if (IDs.size() < 1) return;
		realizer.addConjunctions(IDs);
	}


	@Override
	protected void parseFail() {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void relativeFail() {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void addRelative(String type) {
		// TODO complete, for now just adpositional phrases
		type = type.toUpperCase();
		Relation rel = uMap.mapAdposition(type);
		
		String params = "";
		if (isAction){
			params = "parentID:" + lastID;
			realizer.addPrepositionPhrase(rel, params);
		} else {
			//TODO modify according to the agent (person)
			params = "person";
			realizer.beginComplementizer(rel, params);
			/*
			String relID = "rel" + currentRelID;
			//realizer.addComplementizer(relID, prep);
			if (refs.containsKey(lastID)){
				refs.get(lastID).add(currentRelID);
			} else {
				ArrayList<Integer> r = new ArrayList<Integer>();
				r.add(currentRelID);
				refs.put(lastID, r);
			}
			currentRelID++;*/
			
		}
		
		
	}


	@Override
	protected void endAction() {
		realizer.endSentPhrase();
	}


	@Override
	protected void endAgents() {
		realizer.endSubject();
	}


	@Override
	protected void endThemes() {
		realizer.endObject();
	}


	@Override
	protected void beginSentence(String type) {
		SentMood mood = uMap.mapMood(type);
		realizer.beginSentence(mood);
	}


	@Override
	protected void endSentence() {
		realizer.endSentence();
	}


	@Override
	protected void beginActions(boolean mainClause) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void endActions() {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void addActionAdverb(int advSynSet, List<Integer> advSynSets) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void adverbFail() {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void addRoleSpecif(String name, String def, String quantity) {
		Determiner det = uMap.mapDeterminer(def);
		realizer.addNPSpecifs(name.replace("_", " "), det, quantity);
		
	}


	@Override
	protected void addComparison(String type, List<Integer> adjSynSets) {
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
	protected void addPRole(String id, String pronoun) {
		Pronoun p = uMap.mapPronoun(pronoun);
		realizer.beginNounPhrase(id, p);
		lastID = id;
		isAction = false;
		
	}


	@Override
	protected void beginPRelatives() {
		ignoreReferences = true;
	}


	@Override
	protected void endPRelatives() {
		ignoreReferences = false;
	}


}
