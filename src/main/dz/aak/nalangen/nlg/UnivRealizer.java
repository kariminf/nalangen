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


package dz.aak.nalangen.nlg;

import java.util.Set;

public interface UnivRealizer {
	
	public void beginParagraph();
	
	public void beginSentence(String id, String verb);
	
	public void addVerbSpecif(String tense, String modality, boolean progressive, boolean negated);
	
	public void beginDisjunction();
	public void addConjunction(String nounPhraseID);
	public void endDisjunction();
	
	public void beginSubject();
	public void beginObject();
	public void endSubject();
	public void endObject();
	public void endSentence();
	
	
	public void beginNounPhrase(String id, String noun);
	public void addAdjective(String adjective, Set<String> adverbs);
	
	
	public void endParagraph();
	
	public void addPrepositionPhrase(String parentID, String preposition);
	
	public String getText();
	
	
}
