package kariminf.nalangen.nlg.simplenlg;

import kariminf.nalangen.nlg.RealizerMap;
import kariminf.nalangen.nlg.Types.Comparison;
import kariminf.nalangen.nlg.Types.Coordination;
import kariminf.nalangen.nlg.Types.Determiner;
import kariminf.nalangen.nlg.Types.Modality;
import kariminf.nalangen.nlg.Types.Relation;
import kariminf.nalangen.nlg.Types.Tense;

public class EngSnlgMap extends RealizerMap {

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
		case SUBJ: return "that";
		case OBJ: return "that";
		case POSS: return "that";
		case REAS: return "why";
		case OF: return "of";
		//TODO time returns at, on, in
		//on sundays, in 1986, at 2 pm
		case T_TIME: return "at";
		//since 1986
		case T_PERIOD: return "since";
		//I worked for 10 hours
		case T_AMOUNT: return "for";
		//2 years ago
		case T_AGO: return "ago";
		case T_BEFORE: return "before";
		case T_AFTER: return "after";
		//till, untill
		case T_TILL: return "till";
		case T_BY: return "by";
		//in, inside
		case P_IN: return "in";
		//out, outside
		case P_OUT: return "out";
		//TODO place: in, at
		//exact place: at, in 
		case P_PLACE: return "at";
		case P_ON: return "on";
		case P_BELOW: return "under";
		case P_ABOVE: return "above";
		//TODO fix by
		//by, next to, nesides, near
		case P_BY: return "near";
		//
		case P_BETWEEN: return "between";
		case P_BEHIND: return "behind";
		case P_FRONT: return "in front of";
		case P_THROUGH: return "through";
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
