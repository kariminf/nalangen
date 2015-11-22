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
