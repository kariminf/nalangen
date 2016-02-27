package dz.aak.nalangen.ston;

import dz.aak.nalangen.nlg.ModelingMap;
import dz.aak.nalangen.nlg.Types.Modality;
import dz.aak.nalangen.nlg.Types.Tense;
import dz.aak.sentrep.ston.StonLex;

public class StonMap extends ModelingMap {

	@Override
	public Tense mapTense(String langTense) {
		int idx = StonLex.getTenseIndex(langTense);
		
		switch (idx){
		case 0: return Tense.PAST;
		case 1: return Tense.PRESENT;
		case 2: return Tense.FUTURE;
		
		}
		
		return Tense.PRESENT;
	}

	@Override
	public Modality mapModal(String langModal) {
		
		int idx = StonLex.getModalIndex(langModal);
		
		switch (idx){
		case 0: return Modality.CAN;
		case 1: return Modality.MAY;
		case 2: return Modality.MUST;
		
		}
		return Modality.NONE;
	}

}
