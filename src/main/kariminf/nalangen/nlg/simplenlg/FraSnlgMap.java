package kariminf.nalangen.nlg.simplenlg;

import kariminf.sentrep.LangMap;
import kariminf.sentrep.univ.types.*;
import kariminf.sentrep.univ.types.Pronoun.Proximity;

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
	public String getAdposition(Relation adpos, String param) {
		
		switch (adpos){
		case SUBJ: return "qui";
		case OBJ: 
			if (param.contains("person"))
				return "qui";
			return "que";
		case POSS: return "dont";
		case REAS: return "pourquoi";
		
		case WHERE: return "où";
		case WHEN: return "quand";
		
		case OF: return "de";
		//exact time le 3 juillet, le matin
		case T_AT: return "le";
		case T_SNC: return "depuis";
		//I worked for 10 hours
		case T_FOR: return "durant";
		//2 years ago
		case T_AGO: return "depuis";
		case T_BEF: return "avant";
		case T_AFT: return "après";
		//till, untill
		case T_TILL: return "jusqu'à";
		case T_BY: return "environ";
		//in
		case P_IN: return "à";
		case P_INS: return "dans";
		//out, outside
		case P_OUT: return "dehors";
		//exact place: at
		case P_AT: return "à";
		case P_ON: return "sur";
		case P_LOW: return "sous";
		case P_UP: return "au dessus de";
		//by, next to, nesides, near
		case P_BY: return "près de";
		case P_BET: return "entre";
		case P_BEH: return "derrière";
		case P_FRN: return "devant";
		case P_THR: return "à travers";
		case ABOUT: return "à propos de";
		case FROM: return "de";
		case WITH: return "avec";
		case TO: return "vers";
		case T_IN: return "en";
		default:
			break;
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
		switch (pronoun.getHead()) {
		case DEMONSTRATIVE:
			switch (pronoun.getNumber()) {
			case SINGLE:
				switch (pronoun.getGender()) {
				case FEMALE:
					if (pronoun.getProximity() == Proximity.PROXIMAL){
						return "celle-ci";
					}
					return "celle-là";
				case MALE:
					if (pronoun.getProximity() == Proximity.PROXIMAL){
						return "celui-ci";
					}
					return "celui-là";
				default:
					if (pronoun.getProximity() == Proximity.PROXIMAL){
						return "ceci";
					}
					return "cela";
				
				}

			default:
				switch (pronoun.getGender()) {
				case FEMALE:
					if (pronoun.getProximity() == Proximity.PROXIMAL){
						return "celles-ci";
					}
					return "celles-là";
				case MALE:
					if (pronoun.getProximity() == Proximity.PROXIMAL){
						return "ceux-ci";
					}
					return "ceux-là";
				default:
					if (pronoun.getProximity() == Proximity.PROXIMAL){
						return "ceux-ci";
					}
					return "ceux-là";
				}
			}

		case PERSONNAL:
			switch (pronoun.getPerson()){
			case FIRST:
				return "je";
			case SECOND:
				return "tu";
			default:
				break;
			}
			
			switch (pronoun.getNumber()) {
			case NONE:
				return "on";
				
			case SINGLE:
				switch (pronoun.getGender()) {
				case FEMALE:
					return "elle";
				case MALE:
					return "il";
				default:
					return "il";
				}
			default:
				switch (pronoun.getGender()) {
				case FEMALE:
					return "elles";
				case MALE:
					return "ils";
				default:
					return "ils";
				}
			}
			
		default:
			return "";
		}
	}
	
	

}
