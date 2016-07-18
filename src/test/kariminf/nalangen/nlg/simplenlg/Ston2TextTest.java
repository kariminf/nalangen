package kariminf.nalangen.nlg.simplenlg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import kariminf.nalangen.nlg.UnivRealizer;
import kariminf.nalangen.nlg.simplenlg.EngRealizer;
import kariminf.nalangen.nlg.simplenlg.FraRealizer;
import kariminf.nalangen.ston.Ston2Text;
import kariminf.sentrep.UnivMap;
import kariminf.sentrep.ston.Ston2UnivMap;


public class Ston2TextTest {
	static String stonFile = "../SentRep/ston/exp/that.ston";
	//static String stonFile = "../SentRep/ston/exp/at.ston";
	//static String stonFile = "../SentRep/ston/exp/comp1.ston";
	//static String stonFile = "../SentRep/ston/exp/comp2.ston";
	//static String stonFile = "../SentRep/ston/exp/pn_quant.ston";
	static String wordnetPath = "../LangPi/wordnetDB/wordnet.sqlite";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String specif = readFile(stonFile);
		UnivMap stonMap = new Ston2UnivMap();
		UnivRealizer realizer;
		Ston2Text translator;
		
		realizer = new EngRealizer();
		translator = new Ston2Text(realizer, stonMap, "eng", wordnetPath);
		translator.parse(specif);
		if(translator.parsed()){
			System.out.println(realizer.getText());
		} else {
			System.out.println("English parse failed");
		}
		
		
		realizer = new FraRealizer();
		translator = new Ston2Text(realizer, stonMap, "fra", wordnetPath);
		translator.parse(specif);
		if(translator.parsed()){
			System.out.println(realizer.getText());
		} else {
			System.out.println("French parse failed");
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
