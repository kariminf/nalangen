package kariminf.nalangen.nlg;

import kariminf.nalangen.nlg.Types.Determiner;
import kariminf.nalangen.nlg.Types.Modality;
import kariminf.nalangen.nlg.Types.Relation;
import kariminf.nalangen.nlg.Types.Tense;

public abstract class RealizerMap {
	
	public abstract String getTense(Tense tense);
	public abstract String getModal(Modality modal);
	public abstract String getAdposition(Relation adpos, String name);
	public abstract String getDeterminer(Determiner det);

}
