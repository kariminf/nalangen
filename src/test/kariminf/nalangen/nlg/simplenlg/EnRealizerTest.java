package kariminf.nalangen.nlg.simplenlg;

import java.util.HashSet;
import java.util.Set;

import kariminf.nalangen.nlg.UnivRealizer;
import kariminf.nalangen.nlg.simplenlg.EngRealizer;
import kariminf.sentrep.UnivMap;
import kariminf.sentrep.ston.Ston2UnivMap;
import kariminf.sentrep.univ.types.*;


public class EnRealizerTest {

	
	private static void thatTest(){
		
		UnivRealizer realizer = new EngRealizer();
		
		//Roles
		realizer.beginNounPhrase("mother", "mother");
		realizer.addNPSpecifs("", Determiner.YES, "PL");
		realizer.beginNounPhrase("food", "food");
		realizer.addNPSpecifs("", Determiner.YES, "1");
		realizer.beginComplementizer(Relation.OBJ, "");
		Set<String> compRefs = new HashSet<String>();
		compRefs.add("m_ate");
		realizer.addConjunctions(compRefs);
		realizer.endComplementizer();
		
		realizer.beginNounPhrase("+goodfood", "food");
		HashSet<String> adv1= new HashSet<String>();
		adv1.add("extremely");
		realizer.addAdjective("good", adv1);
		
		//Actions
		realizer.beginSentPhrase("m_ate", "eat");
		realizer.addVerbSpecif(VerbTense.PAST, Modality.NONE, false, false);
		realizer.beginSubject();
		Set<String> subjRefs = new HashSet<String>();
		subjRefs.add("mother");
		realizer.addConjunctions(subjRefs);
		realizer.endSubject();
		realizer.beginSentPhrase("was", "be");
		realizer.addVerbSpecif(VerbTense.PAST, Modality.NONE, false, false);
		
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
		
		realizer.beginSentence(SentMood.AFFIRMATIVE);
		HashSet<String>  sentRefs = new HashSet<String>();
		sentRefs.add("was");
		realizer.addConjunctions(sentRefs);
		realizer.endSentence();
			
		System.out.println(realizer.getText());
		
	}
	
	private static void simpleTest(){
		UnivRealizer realizer = new EngRealizer();
		//realizer.showDebugMsg(true);
		//Roles
		realizer.beginNounPhrase("mother", "mother");
		realizer.addNPSpecifs("", Determiner.YES, "PL");
		
		realizer.beginNounPhrase("food", "food");
		
		//Actions
		
		realizer.beginSentPhrase("ate", "eat");
		realizer.addVerbSpecif(VerbTense.PRESENT, Modality.NONE, false, false);
		
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
		
		realizer.beginSentence(SentMood.AFFIRMATIVE);
		HashSet<String>  sentRefs = new HashSet<String>();
		sentRefs.add("ate");
		realizer.addConjunctions(sentRefs);
		realizer.endSentence();
		
		System.out.println(realizer.getText());
	}
	
	private static void compTest(){
		UnivRealizer realizer = new EngRealizer();
		
		//Roles
		realizer.beginNounPhrase("karim", "karim");
		
		realizer.beginNounPhrase("bother", "his brother");
		
		//Actions
		
		realizer.beginSentPhrase("is", "be");
		realizer.addVerbSpecif(VerbTense.PRESENT, Modality.NONE, false, false);
		
		realizer.beginSubject();
		HashSet<String> subjRefs2 = new HashSet<String>();
		subjRefs2.add("karim");
		realizer.addConjunctions(subjRefs2);
		realizer.endSubject();
		
		/*realizer.beginObject();
		HashSet<String>  objRefs = new HashSet<String>();
		objRefs.add("food");
		realizer.addConjunctions(objRefs);
		realizer.endObject();*/
		
		realizer.endSentPhrase();
		
		
		//realizer.linkComplimentizers();
		
		realizer.beginSentence(SentMood.AFFIRMATIVE);
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
