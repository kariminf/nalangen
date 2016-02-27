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


package dz.aak.nalangen.ston;

import java.util.HashSet;
import java.util.Set;

import dz.aak.nalangen.nlg.UnivRealizer;
import dz.aak.nalangen.wordnet.SqliteReqExceptions.LangNotFound;
import dz.aak.nalangen.wordnet.SqliteReqExceptions.NoSqliteBase;
import dz.aak.nalangen.wordnet.SqliteRequestor;
import dz.aak.nalangen.wordnet.WNRequestor;
import dz.aak.sentrep.ston.Parser;

public class Ston2Text extends Parser {

	private UnivRealizer realizer;
	private WNRequestor wordnet;
	private String lastID = "";
	//private String prep ="";
	private String lang = "eng";
	
	
	public Ston2Text(UnivRealizer realizer, String lang, String basePath) {
		this.realizer = realizer;
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
	}

	@Override
	protected void addVerbSpecif(String tense, String modality, boolean progressive, boolean negated) {
		realizer.addVerbSpecif(tense, modality, progressive, negated);
		
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
	}

	@Override
	protected void addAdjective(int synSet, Set<Integer> advSynSets) {
		String adjective = wordnet.getWord(synSet, "ADJECTIVE");		
		Set<String> adverbs = new HashSet<String>();
		
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
		realizer.endParagraph();
		
	}

	@Override
	protected void addSubjects() {
		realizer.beginSubject();
	}

	@Override
	protected void addObjects() {
		realizer.beginObject();
	}

	@Override
	protected void addConjunctions(Set<String> IDs) {
		if (IDs.size() < 1) return;
		realizer.beginDisjunction();
		for(String ID: IDs)
			realizer.addConjunction(ID);
		realizer.endDisjunction();
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
		if (type.matches("p_at")){
			String prep = "at";
			if (lang == "fr") prep = "dans";
			realizer.addPrepositionPhrase(lastID, prep);
			realizer.beginDisjunction();
		}
		
	}


	@Override
	protected void endAction() {
		realizer.endSentPhrase();
	}


	@Override
	protected void endSubjects() {
		realizer.endSubject();
	}


	@Override
	protected void endObjects() {
		realizer.endObject();
	}


	@Override
	protected void beginSentence(String type) {
		realizer.beginSentence(type);
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
	protected void addActionAdverb(int advSynSet, Set<Integer> advSynSets) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void adverbFail() {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void addRoleSpecif(String name, boolean def, String quantity) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void addComparison(String type, Set<Integer> adjSynSets) {
		// TODO Auto-generated method stub
		
	}


}
