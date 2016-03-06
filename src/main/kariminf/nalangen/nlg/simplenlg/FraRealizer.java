package kariminf.nalangen.nlg.simplenlg;


import kariminf.nalangen.nlg.ModelingMap;
import simplenlg.framework.NLGFactory;
import simplenlg.realiser.Realiser;

public class FraRealizer extends SNLGRealizer {
	
	public FraRealizer(ModelingMap mdMap) {
		super(new FraSnlgMap(), mdMap, new simplenlg.lexicon.french.XMLLexicon());
	}

}
