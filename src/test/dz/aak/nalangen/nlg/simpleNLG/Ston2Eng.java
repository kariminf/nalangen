package dz.aak.nalangen.nlg.simpleNLG;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import dz.aak.nalangen.nlg.simpleNLG.EngRealizer;
import dz.aak.nalangen.ston.Ston2Text;

public class Ston2Eng {

	static String stonFile = "../SentRep/ston/at.ston";
	static String wordnetPath = "wordnet/wordnet.sqlite";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String specif = readFile(stonFile);
		EngRealizer realizer = new EngRealizer();
		Ston2Text translator = new Ston2Text(realizer, "eng", wordnetPath);
		
		//System.out.println(specif);
		
		translator.parse(specif);
		
		if(translator.parsed()){
			System.out.println(realizer.getText());
		} else {
			System.out.println("No");
		}

	}
	
	public static String readFile (String f) {
		try {
			String contents = "";

			BufferedReader input = new BufferedReader(new FileReader(f));

			
			for(String line = input.readLine(); line != null; line = input.readLine()) {
				contents += line + "\n";
			}
			input.close();

			return contents;

		} catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
			return null;
		} 
	}

}
