package kariminf.nalangen.nlg.simplenlg;

import kariminf.sentrep.LangMap;
import kariminf.sentrep.univ.types.*;

public class FraSnlgMap implements LangMap {

	@Override
	public String getTense(VerbTense tense) {
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
		case T_AT: return "à";
		case T_SNC: return "depuis";
		//I worked for 10 hours
		case T_FOR: return "durant";
		//2 years ago
		//TODO fr-ago
		case T_AGO: return "";
		case T_BEF: return "avant";
		case T_AFT: return "après";
		//till, untill
		case T_TILL: return "jusqu'à";
		case T_BY: return "environ";
		//in, inside
		case P_IN: return "dans";
		//out, outside
		case P_OUT: return "dehors";
		//TODO place: in, at
		//exact place: at, in 
		case P_AT: return "à";
		case P_ON: return "sur";
		case P_LOW: return "sous";
		case P_UP: return "au dessus de";
		//TODO fix by
		//by, next to, nesides, near
		case P_BY: return "près de";
		//
		case P_BET: return "entre";
		case P_BEH: return "derrière";
		case P_FRN: return "devant";
		case P_THR: return "à travers";
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
		case NONE: return "un";
		default:
			return "";
		}
	}

	@Override
	public String getCoordination(Coordination coord) {
		switch (coord){
		case AND: return "et";
		case OR: return "ou";
		default: return "et";
		}
	}

	@Override
	public String getPreComparison(Comparison comp, boolean hasAdj) {
		switch (comp){
		case MORE: return "plus";
		case LESS: return "moins";
		case MOST: return "le plus";
		case LEAST: return "le moins";
		case EQUAL: return "";
		default: return "";
		}
	}

	@Override
	public String getPostComparison(Comparison comp, boolean hasAdj) {
		switch (comp){
		case MORE: return "que";
		case LESS: return "que";
		case MOST: return "";
		case LEAST: return "";
		case EQUAL: return "comme";
		default: return "";
		}
	}

	@Override
	public String getPronoun(Pronoun pronoun) {
		return "";
	}
	
	

}
