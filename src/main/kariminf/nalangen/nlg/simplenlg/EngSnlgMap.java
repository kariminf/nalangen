package kariminf.nalangen.nlg.simplenlg;

import kariminf.sentrep.univ.types.*;
import kariminf.sentrep.univ.LangMap;

public class EngSnlgMap implements LangMap {

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
		case SUBJ: return "that";
		case OBJ: return "that";
		case POSS: return "that";
		case REAS: return "why";
		case OF: return "of";
		//TODO time returns at, on, in
		//on sundays, in 1986, at 2 pm
		case T_AT: return "at";
		//since 1986
		case T_SNC: return "since";
		//I worked for 10 hours
		case T_FOR: return "for";
		//2 years ago
		case T_AGO: return "ago";
		case T_BEF: return "before";
		case T_AFT: return "after";
		//till, untill
		case T_TILL: return "till";
		case T_BY: return "by";
		//in, inside
		case P_IN: return "in";
		//out, outside
		case P_OUT: return "out";
		//TODO place: in, at
		//exact place: at, in 
		case P_AT: return "at";
		case P_ON: return "on";
		case P_LOW: return "under";
		case P_UP: return "above";
		//TODO fix by
		//by, next to, nesides, near
		case P_BY: return "near";
		//
		case P_BET: return "between";
		case P_BEH: return "behind";
		case P_FRN: return "in front of";
		case P_THR: return "through";
		case ABOUT: return "about";
		case FROM: return "from";
		case WITH: return "with";
		case TO: return "to";
		}
		
		return "";
	}

	@Override
	public String getDeterminer(Determiner det) {
		switch (det){
		case YES: return "the";
		case NO: return "a";
		case NONE: return "";
		default:
			return "";
		}
	}

	@Override
	public String getCoordination(Coordination coord) {
		switch (coord){
		case AND: return "and";
		case OR: return "or";
		default: return "";
		}
	}

	@Override
	public String getPreComparison(Comparison comp, boolean hasAdj) {
		switch (comp){
		case MORE: 
			if (hasAdj)	return "";
			else return "more";
		case LESS: return "less";
		case MOST: return "the";
		case LEAST: return "the least";
		case EQUAL: return "as";
		default: return "";
		}
	}

	@Override
	public String getPostComparison(Comparison comp, boolean hasAdj) {
		switch (comp){
		case MORE: return "than";
		case LESS: return "than";
		case MOST: return "";
		case LEAST: return "";
		case EQUAL: return "as";
		default: return "";
		}
	}
	
	

}
