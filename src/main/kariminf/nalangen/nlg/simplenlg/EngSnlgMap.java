package kariminf.nalangen.nlg.simplenlg;

import kariminf.sentrep.LangMap;
import kariminf.sentrep.univ.types.*;
import kariminf.sentrep.univ.types.Pronoun.Proximity;

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
	public String getAdposition(Relation adpos, String param) {
		
		switch (adpos){
		case SUBJ: 
			if (param.contains("person"))
				return "who";
			return "that";
		case OBJ: 
			if (param.contains("person"))
				return "who";
			return "that";
		case POSS: return "that";
		case REAS: return "why";
		
		case WHERE: return "where";
		case WHEN: return "when";
		
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
		//in
		case P_IN: return "in"; // wide space In London
		//out, outside
		case P_OUT: return "out";
		//exact place: at 
		case P_AT: return "at"; //narrow space
		case P_ON: return "on";
		case P_LOW: return "under";
		case P_UP: return "above";
		//by, next to, nesides, near
		case P_BY: return "near";
		case P_BET: return "between";
		case P_BEH: return "behind";
		case P_FRN: return "in front of";
		case P_THR: return "through";
		case ABOUT: return "about";
		case FROM: return "from";
		case WITH: return "with";
		case TO: return "to";
		case P_INS: return "inside";
		case T_IN: return "in";
		case AS: return "as";
		case UND: return "under";
		case ON: return "on";
		case FOR: return "for";
		default:
			break;
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

	@Override
	public String getPronoun(Pronoun pronoun) {
		
		switch (pronoun.getHead()) {
		case DEMONSTRATIVE:
			switch (pronoun.getNumber()) {
			case SINGLE:
				if (pronoun.getProximity() == Proximity.PROXIMAL){
					return "this";
				}
				return "that";

			default:
				if (pronoun.getProximity() == Proximity.PROXIMAL){
					return "these";
				}
				return "those";
			}

		case SUBJECTIVE:
			switch (pronoun.getPerson()){
			case FIRST:
				return "I";
			case SECOND:
				return "you";
			default:
				break;
			}
			
			switch (pronoun.getNumber()) {
			case NONE:
				return "no one";
				
			case SINGLE:
				switch (pronoun.getGender()) {
				case FEMALE:
					return "she";
				case MALE:
					return "he";
				default:
					return "it";
				}
			default:
				return "they";
			}
			
		case OBJECTIVE:
			switch (pronoun.getPerson()){
			case FIRST:
				return "me";
			case SECOND:
				return "you";
			default:
				break;
			}
			
			switch (pronoun.getNumber()) {
			case NONE:
				return "no one";
				
			case SINGLE:
				switch (pronoun.getGender()) {
				case FEMALE:
					return "her";
				case MALE:
					return "him";
				default:
					return "it";
				}
			default:
				return "them";
			}	
			
		case POSSESSIVE:
			switch (pronoun.getPerson()){
			case FIRST:
				return "my";
			case SECOND:
				return "your";
			default:
				break;
			}
			
			switch (pronoun.getNumber()) {
			case NONE:
				return "no one's";
				
			case SINGLE:
				switch (pronoun.getGender()) {
				case FEMALE:
					return "her";
				case MALE:
					return "his";
				default:
					return "its";
				}
			default:
				return "their";
			}
			
		default:
			return "";
		}

	}

	
	

}
