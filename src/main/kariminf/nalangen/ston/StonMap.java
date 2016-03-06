package kariminf.nalangen.ston;

import kariminf.nalangen.nlg.ModelingMap;
import kariminf.nalangen.nlg.Types.Determiner;
import kariminf.nalangen.nlg.Types.Modality;
import kariminf.nalangen.nlg.Types.Relation;
import kariminf.nalangen.nlg.Types.Tense;
import kariminf.sentrep.ston.StonLex;

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

	@Override
	public Determiner mapDeterminer(String langDet) {
		int idx = StonLex.getDetIndex(langDet);
		switch (idx){
		case 1: return Determiner.YES;
		case 2: return Determiner.NO;
		default: return Determiner.NONE;
		}
	}

}
