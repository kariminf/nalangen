package kariminf.nalangen.nlg;

import kariminf.nalangen.nlg.Types.Determiner;
import kariminf.nalangen.nlg.Types.Modality;
import kariminf.nalangen.nlg.Types.Relation;
import kariminf.nalangen.nlg.Types.Tense;

public abstract class ModelingMap {
	
	public abstract Tense mapTense(String langTense);
	public abstract Modality mapModal(String langModal);
	public abstract Relation mapAdposition(String langAdpos);
	public abstract Determiner mapDeterminer(String langDet);

}
