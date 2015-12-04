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
	private boolean subject = true;
	
	
	public Ston2Text(UnivRealizer realizer, String lang, String basePath) {
		this.realizer = realizer;
		try {
			wordnet = SqliteRequestor.create(lang, basePath);
		} catch (NoSqliteBase | LangNotFound e) {
			System.out.println("Wordnet problem");
			e.printStackTrace();
		}
	}

	@Override
	protected void beginActions() {
		
	}

	@Override
	protected void beginAction(String id, int synSet) {
		String verb = wordnet.getWord(synSet, "VERB");
		realizer.beginSentence(id, verb);
	}

	@Override
	protected void addVerbSpecif(String tense, String aspect) {
		realizer.addVerbSpecif(tense, aspect);
		
	}

	@Override
	protected void endAction() {
		realizer.endSentence();
		
	}

	@Override
	protected void actionFail() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void endActions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beginRoles() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beginRole(String id, int synSet) {
		String noun = wordnet.getWord(synSet, "NOUN");
		realizer.beginNounPhrase(id, noun);
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
	protected void endRole() {
		// TODO Auto-generated method stub
		
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
	protected void endRoles() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void parseSuccess() {
		realizer.endParagraph();
		
	}

	@Override
	protected void beginSubject() {
		realizer.beginSubject();
		
	}

	@Override
	protected void beginObject() {
		realizer.beginObject();
	}

	@Override
	protected void beginDisjunction() {
		realizer.beginDisjunction();
		
	}

	@Override
	protected void addConjunction(String roleID) {
		realizer.addConjunction(roleID);
		
	}

	@Override
	protected void endDisjunction() {
		realizer.endDisjunction();
		
	}

	@Override
	protected void endSubject() {
		realizer.endSubject();
		
	}

	@Override
	protected void endObject() {
		realizer.endObject();
		
	}

}
