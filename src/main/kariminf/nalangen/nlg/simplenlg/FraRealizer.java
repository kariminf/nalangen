package kariminf.nalangen.nlg.simplenlg;


import kariminf.sentrep.UnivMap;

public class FraRealizer extends SNLGRealizer {
	
	public FraRealizer() {
		super(new FraSnlgMap(), new simplenlg.lexicon.french.XMLLexicon());
	}

}
