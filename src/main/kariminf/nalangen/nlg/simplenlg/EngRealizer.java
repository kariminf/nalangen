package kariminf.nalangen.nlg.simplenlg;

import kariminf.sentrep.UnivMap;
import simplenlg.lexicon.Lexicon;

public class EngRealizer extends SNLGRealizer {

	public EngRealizer(UnivMap mdMap) {
		super(new EngSnlgMap(), mdMap, Lexicon.getDefaultLexicon());
	}
	
}
