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


package dz.aak.nalangen.wordnet;

import java.io.File;
import java.sql.SQLException;

import dz.aak.nalangen.wordnet.SqliteReqExceptions.LangNotFound;
import dz.aak.nalangen.wordnet.SqliteReqExceptions.NoSqliteBase;

public class SqliteReqTest {

	static String lang = "arb";
	static String basePath = "wordnet/wordnet.sqlite";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		//Wrong path (file don't exist)
		try {
			SqliteRequestor req = SqliteRequestor.create(lang, "wordnet/somewhere");
		} catch (NoSqliteBase e) {
			e.printStackTrace();
		} catch (LangNotFound e) {
			e.printStackTrace();
		} 

		//File exists, but not sqlite file
		try {
			SqliteRequestor req = SqliteRequestor.create(lang, "wordnet/arb.zip");
		} catch (NoSqliteBase e) {
			e.printStackTrace();
		} catch (LangNotFound e) {
			e.printStackTrace();
		} 
		 */
		//Sqlite file exists, but no table for the specified language
		try {
			SqliteRequestor req = SqliteRequestor.create("arb", basePath);
			String mother = req.getWord(10332385, "NOUN");
			String child = req.getWord(9917593, "NOUN");
			String eat = req.getWord(1168468, "VERB");
			String extremely = req.getWord(89408, "ADVERB");
			String good = req.getWord(1123148, "ADJECTIVE");
			String food = req.getWord(21265, "NOUN");

			String res = mother + " و" + child;
			res += " " + eat + " " + food;
			res += " " + good + " " + extremely;

			System.out.println(res);

		} catch (NoSqliteBase e) {
			e.printStackTrace();
		} catch (LangNotFound e) {
			e.printStackTrace();
		} 

		try {
			SqliteRequestor req = SqliteRequestor.create("fra", basePath);
			String mother = req.getWord(10332385, "NOUN");
			String child = req.getWord(9917593, "NOUN");
			String eat = req.getWord(1168468, "VERB");
			String extremely = req.getWord(89408, "ADVERB");
			String good = req.getWord(1123148, "ADJECTIVE");
			String food = req.getWord(21265, "NOUN");

			String res = mother + " et " + child;
			res += " " + eat + " " + food;
			res += " " + extremely + " " + good ;

			System.out.println(res);

		} catch (NoSqliteBase e) {
			e.printStackTrace();
		} catch (LangNotFound e) {
			e.printStackTrace();
		} 

	}

}
