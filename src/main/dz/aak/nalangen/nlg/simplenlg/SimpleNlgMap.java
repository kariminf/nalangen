package dz.aak.nalangen.nlg.simplenlg;

import dz.aak.nalangen.nlg.RealizerMap;
import dz.aak.nalangen.nlg.Types.Modality;
import dz.aak.nalangen.nlg.Types.Tense;

public class SimpleNlgMap extends RealizerMap {

	@Override
	public String getTense(Tense tense) {
		return tense.toString();
	}

	@Override
	public String getModal(Modality modal) {
		return modal.toString().toLowerCase();
	}
	
	

}
