package kariminf.nalangen.nlg.simplenlg;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import kariminf.nalangen.nlg.UnivRealizer;
import kariminf.nalangen.nlg.simplenlg.FraRealizer;
import kariminf.sentrep.UnivMap;
import kariminf.sentrep.ston.Ston2UnivMap;
import kariminf.sentrep.univ.types.*;
import kariminf.sentrep.univ.types.Relation.Relative;


public class FrRealizerTest {

	
	private static void thatTest(){
		
		UnivRealizer realizer = new FraRealizer();
		
		//Roles
		realizer.beginNounPhrase("mother", "mère");
		realizer.addNPSpecifs("", Determiner.YES, "1");
		realizer.beginNounPhrase("food", "nourriture");
		realizer.beginRelative(Relative.OBJECT, "");
		ArrayList<String> compRefs = new ArrayList<String>();
		compRefs.add("m_ate");
		realizer.addConjunctions(compRefs);
		realizer.endRelative();
		
		realizer.beginNounPhrase("+goodfood", "nourriture");
		ArrayList<String> adv1= new ArrayList<String>();
		adv1.add("extrêmement");
		realizer.addAdjective("bon", adv1);
		
		//Actions
		realizer.beginSentPhrase("m_ate", "manger");
		realizer.addVerbSpecif(VerbTense.PAST, Modality.NONE, false, false);
		realizer.beginSubject();
		ArrayList<String> subjRefs = new ArrayList<String>();
		subjRefs.add("mother");
		realizer.addConjunctions(subjRefs);
		realizer.endSubject();
		realizer.beginSentPhrase("was", "être");
		realizer.addVerbSpecif(VerbTense.PAST, Modality.NONE, false, false);
		
		realizer.beginSubject();
		ArrayList<String> subjRefs2 = new ArrayList<String>();
		subjRefs2.add("food");
		realizer.addConjunctions(subjRefs2);
		realizer.endSubject();
		
		realizer.beginObject();
		ArrayList<String>  objRefs = new ArrayList<String>();
		objRefs.add("+goodfood");
		realizer.addConjunctions(objRefs);
		realizer.endObject();
		
		realizer.endSentPhrase();
		
		realizer.beginSentence(SentMood.AFFIRMATIVE);
		ArrayList<String>  sentRefs = new ArrayList<String>();
		sentRefs.add("was");
		realizer.addConjunctions(sentRefs);
		realizer.endSentence();
			
		System.out.println(realizer.getText());
		
	}
	
	private static void simpleTest(){
		UnivRealizer realizer = new FraRealizer();
		
		//Roles
		realizer.beginNounPhrase("mother", "mère");
		realizer.addNPSpecifs("", Determiner.YES, "1");
		realizer.beginNounPhrase("food", "nourriture");
		realizer.addNPSpecifs("", Determiner.YES, "1");
		//Actions
		
		realizer.beginSentPhrase("ate", "manger");
		realizer.addVerbSpecif(VerbTense.PAST, Modality.NONE, false, false);
		
		realizer.beginSubject();
		ArrayList<String> subjRefs2 = new ArrayList<String>();
		subjRefs2.add("mother");
		realizer.addConjunctions(subjRefs2);
		realizer.endSubject();
		
		realizer.beginObject();
		ArrayList<String>  objRefs = new ArrayList<String>();
		objRefs.add("food");
		realizer.addConjunctions(objRefs);
		realizer.endObject();
		
		realizer.endSentPhrase();
		//realizer.linkComplimentizers();
		
		realizer.beginSentence(SentMood.AFFIRMATIVE);
		ArrayList<String>  sentRefs = new ArrayList<String>();
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
