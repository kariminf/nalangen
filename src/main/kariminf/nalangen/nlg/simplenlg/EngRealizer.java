package kariminf.nalangen.nlg.simplenlg;

import kariminf.nalangen.nlg.ModelingMap;
import simplenlg.lexicon.Lexicon;

public class EngRealizer extends SNLGRealizer {

	public EngRealizer(ModelingMap mdMap) {
		super(new EngSnlgMap(), mdMap, Lexicon.getDefaultLexicon());
	}
	
}
