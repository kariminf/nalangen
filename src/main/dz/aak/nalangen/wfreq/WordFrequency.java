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


package dz.aak.nalangen.wfreq;

public interface WordFrequency {
	
	/**
	 * 
	 * @param word
	 * @return
	 */
	public int getWordFrequency(String word);
	
	/**
	 * 
	 * @param corpusName
	 * @param word
	 * @return
	 */
	public int getWordFrequency(String corpusName, String word);
	
	/**
	 * 
	 * @return The license of the data
	 */
	public String getLicense();
	
	/**
	 * 
	 * @return The code of the language
	 */
	public String getLanguage();

}
