package dz.aak.nalangen.nlg.simplenlg;

import simplenlg.lexicon.Lexicon;
import dz.aak.nalangen.nlg.ModelingMap;

public class EngRealizer extends SNLGRealizer {

	public EngRealizer(ModelingMap mdMap) {
		super(new EngSnlgMap(), mdMap, Lexicon.getDefaultLexicon());
	}
	
}
