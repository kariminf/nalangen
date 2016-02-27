package dz.aak.nalangen.nlg;

import dz.aak.nalangen.nlg.Types.Modality;
import dz.aak.nalangen.nlg.Types.Tense;

public abstract class ModelingMap {
	
	public abstract Tense mapTense(String langTense);
	public abstract Modality mapModal(String langModal);

}
