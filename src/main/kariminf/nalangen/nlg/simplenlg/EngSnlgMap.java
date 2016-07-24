package kariminf.nalangen.nlg.simplenlg;

import kariminf.sentrep.LangMap;
import kariminf.sentrep.univ.types.*;
import kariminf.sentrep.univ.types.Pronoun.Proximity;
import kariminf.sentrep.univ.types.Relation.Adpositional;
import kariminf.sentrep.univ.types.Relation.Adverbial;
import kariminf.sentrep.univ.types.Relation.Relative;

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
	public String getAdposition(Adpositional adpos, String param) {
		
		switch (adpos){
		case ABOVE: return "above";
		case ACCOMPANY: return "with";
		case AFTER:
			if (param.contains("place")) return "behind";
			return "after";
		case INTENTION: return "for";
		case BEFORE: 
			if (param.contains("place")) return "in front";
			return "before";
		case BELOW: return "below";
		case BETWEEN: return "between";
		case DESTINATION: return "to";
		case EXIST: //on sundays, in 1986, at 2 pm
			//place and time
			if (param.contains("small")) return "at";
			if (param.contains("many")) return "on";
			return "in";
		case INSIDE: return "inside";
		case OUTSIDE: return "outside";
		case PAST: return "ago";
		case POSSESSION: return "of";
		case PROXIMITY: return "by";
		case ROLE: return "as";
		case SINCE: return "since";
		case SITUATION: return "under";
		case SOURCE: return "from";
		case SUBJECT: return "about"; // or "on"
		case THROUGH: return "through";
		default:
			break;
		
		}
		
		return "";
	}
	
	@Override
	public String getAdverbial(Adverbial adv, String param) {
		switch (adv){
		case CONDITION: return "if";
		case CONSESSION: return "although";
		case MANNER: return "as";
		case PLACE: return "where";
		case PURPOSE: return "in order to";
		case REASON: return "because";
		case TIME: return "when";
		default:
			break;
		
		}
		return "";
	}

	@Override
	public String getRelative(Relative rel, String param) {
		
		if (rel.name().equals("IO_IN")){
			if (param.contains("place"))
				return "where";
			else if (param.contains("time"))
				return "when";
		}
		
		if (rel.name().startsWith("IO_")){
			String objPronoun = param.contains("person")? "whom": "which";
			Adpositional adp = Adpositional.valueOf(rel.name().split("_")[1]);
			return getAdposition(adp, param) + " " + objPronoun;
		}
		
		switch (rel){
		case OBJECT:
			if (param.contains("person"))
				return "whom";
			return "which";
		case POSSESSIVE: return "whose";
		case REASON: return "why";
		case SUBJECT:
			if (param.contains("person"))
				return "who";
			return "which";
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
