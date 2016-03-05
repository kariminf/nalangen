package dz.aak.nalangen.nlg.simplenlg;

import dz.aak.nalangen.nlg.RealizerMap;
import dz.aak.nalangen.nlg.Types.Determiner;
import dz.aak.nalangen.nlg.Types.Relation;
import dz.aak.nalangen.nlg.Types.Modality;
import dz.aak.nalangen.nlg.Types.Tense;

public class FraSnlgMap extends RealizerMap {

	@Override
	public String getTense(Tense tense) {
		return tense.toString();
	}

	@Override
	public String getModal(Modality modal) {
		return modal.toString().toLowerCase();
	}

	@Override
	public String getAdposition(Relation adpos, String name) {
		
		switch (adpos){
		case SUBJ: return "qui";
		case OBJ: return "que";
		case POSS: return "dont";
		case REAS: return "pourquoi";
		case OF: return "de";
		case T_TIME: return "à";
		case T_PERIOD: return "depuis";
		//I worked for 10 hours
		case T_AMOUNT: return "durant";
		//2 years ago
		//TODO fr-ago
		case T_AGO: return "";
		case T_BEFORE: return "avant";
		case T_AFTER: return "après";
		//till, untill
		case T_TILL: return "jusqu'à";
		case T_BY: return "environ";
		//in, inside
		case P_IN: return "dans";
		//out, outside
		case P_OUT: return "dehors";
		//TODO place: in, at
		//exact place: at, in 
		case P_PLACE: return "à";
		case P_ON: return "sur";
		case P_BELOW: return "sous";
		case P_ABOVE: return "au dessus de";
		//TODO fix by
		//by, next to, nesides, near
		case P_BY: return "près de";
		//
		case P_BETWEEN: return "entre";
		case P_BEHIND: return "derrière";
		case P_FRONT: return "devant";
		case P_THROUGH: return "à travers";
		case ABOUT: return "à propos de";
		case FROM: return "de";
		case WITH: return "avec";
		case TO: return "vers";
		}
		
		return "";
	}

	@Override
	public String getDeterminer(Determiner det) {
		switch (det){
		case YES: return "le";
		case NO: return "un";
		case NONE: return "";
		default:
			return "";
		}
	}
	
	

}
