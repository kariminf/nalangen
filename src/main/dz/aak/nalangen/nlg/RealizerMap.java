package dz.aak.nalangen.nlg;

import dz.aak.nalangen.nlg.Types.Determiner;
import dz.aak.nalangen.nlg.Types.Relation;
import dz.aak.nalangen.nlg.Types.Modality;
import dz.aak.nalangen.nlg.Types.Tense;

public abstract class RealizerMap {
	
	public abstract String getTense(Tense tense);
	public abstract String getModal(Modality modal);
	public abstract String getAdposition(Relation adpos, String name);
	public abstract String getDeterminer(Determiner det);

}
