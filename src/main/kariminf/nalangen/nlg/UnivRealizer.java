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


package kariminf.nalangen.nlg;

import java.util.Set;

import kariminf.sentrep.LangMap;
import kariminf.sentrep.UnivMap;
import kariminf.sentrep.univ.types.*;
import kariminf.sentrep.univ.*;


public interface UnivRealizer {
	
	public abstract void beginSentPhrase(String id, String verb);
	
	public abstract void addVerbSpecif(VerbTense tense, Modality modality, boolean progressive, boolean negated);
	public abstract void addConjunctions(Set<String> phraseIDs);
	
	public abstract void beginSubject();
	public abstract void beginObject();
	public abstract void endSubject();
	public abstract void endObject();
	public abstract void endSentPhrase();
	
	
	public abstract void beginNounPhrase(String id, String noun);
	public abstract void beginNounPhrase(String id, Pronoun p);
	
	public abstract void addNPSpecifs(String name, Determiner det, String quantity);
	public abstract void addAdjective(String adjective, Set<String> adverbs);
	
	public abstract void addPrepositionPhrase(Relation preposition, String params);
	
	public abstract String getText();
	
	/*
	public abstract void addComplementizer(String id, String pronoun);
	
	public abstract void linkComplementizer(String parentId, String id);
	*/
	
	public abstract void beginComplementizer(Relation pronoun, String params);
	public abstract void endComplementizer();
	
	public abstract void beginSentence (SentMood type);
	public abstract void endSentence ();
	
	public abstract void addComparison(Comparison comp, Set<String> adjs);
	
	public abstract void showDebugMsg(boolean yes);
}
