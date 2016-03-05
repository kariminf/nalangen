package dz.aak.nalangen.nlg.simplenlg;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dz.aak.nalangen.nlg.ModelingMap;
import dz.aak.nalangen.nlg.Types;
import dz.aak.nalangen.nlg.UnivRealizer;
import dz.aak.nalangen.ston.StonMap;

public class FrRealizerTest {

	
	private static void thatTest(){
		
		ModelingMap stonMap = new StonMap();
		UnivRealizer realizer = new FraRealizer(stonMap);
		
		//Roles
		realizer.beginNounPhrase("mother", "mère");
		realizer.beginNounPhrase("food", "nourriture");
		realizer.beginComplementizer("que");
		HashSet<String> compRefs = new HashSet<String>();
		compRefs.add("m_ate");
		realizer.addConjunctions(compRefs);
		realizer.endComplementizer();
		
		realizer.beginNounPhrase("+goodfood", "nourriture");
		HashSet<String> adv1= new HashSet<String>();
		adv1.add("extrêmement");
		realizer.addAdjective("bon", adv1);
		
		//Actions
		realizer.beginSentPhrase("m_ate", "manger");
		realizer.addVerbSpecif(Types.Tense.PAST, Types.Modality.NONE, false, false);
		realizer.beginSubject();
		HashSet<String> subjRefs = new HashSet<String>();
		subjRefs.add("mother");
		realizer.addConjunctions(subjRefs);
		realizer.endSubject();
		realizer.beginSentPhrase("was", "être");
		realizer.addVerbSpecif(Types.Tense.PAST, Types.Modality.NONE, false, false);
		
		realizer.beginSubject();
		HashSet<String> subjRefs2 = new HashSet<String>();
		subjRefs2.add("food");
		realizer.addConjunctions(subjRefs2);
		realizer.endSubject();
		
		realizer.beginObject();
		HashSet<String>  objRefs = new HashSet<String>();
		objRefs.add("+goodfood");
		realizer.addConjunctions(objRefs);
		realizer.endObject();
		
		realizer.endSentPhrase();
		
		realizer.beginSentence(Types.Mood.AFFIRMATIVE);
		HashSet<String>  sentRefs = new HashSet<String>();
		sentRefs.add("was");
		realizer.addConjunctions(sentRefs);
		realizer.endSentence();
			
		System.out.println(realizer.getText());
		
	}
	
	private static void simpleTest(){
		ModelingMap stonMap = new StonMap();
		UnivRealizer realizer = new FraRealizer(stonMap);
		
		//Roles
		realizer.beginNounPhrase("mother", "mère");
		
		realizer.beginNounPhrase("food", "nourriture");
		
		//Actions
		
		realizer.beginSentPhrase("ate", "manger");
		realizer.addVerbSpecif(Types.Tense.PAST, Types.Modality.NONE, false, false);
		
		realizer.beginSubject();
		HashSet<String> subjRefs2 = new HashSet<String>();
		subjRefs2.add("mother");
		realizer.addConjunctions(subjRefs2);
		realizer.endSubject();
		
		realizer.beginObject();
		HashSet<String>  objRefs = new HashSet<String>();
		objRefs.add("food");
		realizer.addConjunctions(objRefs);
		realizer.endObject();
		
		realizer.endSentPhrase();
		//realizer.linkComplimentizers();
		
		realizer.beginSentence(Types.Mood.AFFIRMATIVE);
		HashSet<String>  sentRefs = new HashSet<String>();
		sentRefs.add("ate");
		realizer.addConjunctions(sentRefs);
		realizer.endSentence();
		
		System.out.println(realizer.getText());
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		simpleTest();
		thatTest();

	}

}
