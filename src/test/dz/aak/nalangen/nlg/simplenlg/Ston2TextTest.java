package dz.aak.nalangen.nlg.simplenlg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import dz.aak.nalangen.nlg.ModelingMap;
import dz.aak.nalangen.nlg.UnivRealizer;
import dz.aak.nalangen.nlg.simplenlg.SNLGRealizer;
import dz.aak.nalangen.ston.Ston2Text;
import dz.aak.nalangen.ston.StonMap;

public class Ston2TextTest {

	static String stonFile = "../SentRep/ston/that.ston";
	static String wordnetPath = "wordnet/wordnet.sqlite";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String specif = readFile(stonFile);
		ModelingMap stonMap = new StonMap();
		UnivRealizer realizer;
		Ston2Text translator;
		
		realizer = new SNLGRealizer(stonMap);
		translator = new Ston2Text(realizer, "eng", wordnetPath);
		translator.parse(specif);
		if(translator.parsed()){
			System.out.println(realizer.getText());
		} else {
			System.out.println("English parse failed");
		}
		
		/*
		
		realizer = new FraRealizer(stonMap);
		translator = new Ston2Text(realizer, "fra", wordnetPath);
		translator.parse(specif);
		if(translator.parsed()){
			System.out.println(realizer.getText());
		} else {
			System.out.println("French parse failed");
		}
		
		*/

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
