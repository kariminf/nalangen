package dz.aak.nalangen.ston;

import dz.aak.nalangen.nlg.ModelingMap;
import dz.aak.nalangen.nlg.Types.Relation;
import dz.aak.nalangen.nlg.Types.Modality;
import dz.aak.nalangen.nlg.Types.Tense;
import dz.aak.sentrep.ston.StonLex;

public class StonMap extends ModelingMap {

	@Override
	public Tense mapTense(String langTense) {
		int idx = StonLex.getTenseIndex(langTense);
		
		switch (idx){
		case 1: return Tense.PAST;
		case 2: return Tense.FUTURE;
		
		}
		
		return Tense.PRESENT;
	}

	@Override
	public Modality mapModal(String langModal) {
		
		int idx = StonLex.getModalIndex(langModal);
		
		switch (idx){
		case 1: return Modality.CAN;
		case 2: return Modality.MAY;
		case 3: return Modality.MUST;
		
		}
		return Modality.NONE;
	}

	@Override
	public Relation mapAdposition(String langAdpos) {
		
		Relation adpos = Relation.valueOf(langAdpos);
		
		return adpos;
	}

}
