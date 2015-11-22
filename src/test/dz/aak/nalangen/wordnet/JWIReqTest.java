package dz.aak.nalangen.wordnet;

import java.io.File;
import java.sql.SQLException;

import dz.aak.nalangen.wordnet.SqliteReqExceptions.LangNotFound;
import dz.aak.nalangen.wordnet.SqliteReqExceptions.NoSqliteBase;

public class JWIReqTest {

	static String wordnetPath = "wordnet/dict/";
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		JWIRequestor req = JWIRequestor.create(wordnetPath);
		String mother = req.getWord(10332385, "NOUN");
		String child = req.getWord(9917593, "NOUN");
		String eat = req.getWord(1168468, "VERB");
		String extremely = req.getWord(89408, "ADVERB");
		String good = req.getWord(1123148, "ADJECTIVE");
		String food = req.getWord(21265, "NOUN");

		String res = mother + " and " + child;
		res += " " + eat + " " + extremely;
		res += " " + good + " " + food;

		System.out.println(res);

	}
}
