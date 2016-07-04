package kariminf.nalangen.nlg.simplenlg;


import kariminf.sentrep.UnivMap;

public class FraRealizer extends SNLGRealizer {
	
	public FraRealizer(UnivMap mdMap) {
		super(new FraSnlgMap(), mdMap, new simplenlg.lexicon.french.XMLLexicon());
	}

}
