package kariminf.nalangen.nlg.simplenlg;

import kariminf.sentrep.LangMap;
import kariminf.sentrep.univ.types.*;
import kariminf.sentrep.univ.types.Pronoun.Proximity;
import kariminf.sentrep.univ.types.Relation.Adpositional;
import kariminf.sentrep.univ.types.Relation.Adverbial;
import kariminf.sentrep.univ.types.Relation.Relative;

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
	public String getAdposition(Adpositional adpos, String param) {
		
		switch (adpos){
		case ABOVE: return "sur";
		case ACCOMPANY: return "avec";
		case AFTER:
			if (param.contains("place")) return "derrière";
			return "après";
		case INTENTION: return "pour";
		case BEFORE: 
			if (param.contains("place")) return "devant";
			return "avant";
		case BELOW: return "sous";
		case BETWEEN: return "entre";
		case DESTINATION: return "vers";
		case EXIST: //on sundays, in 1986, at 2 pm
			return "à";
		case INSIDE: return "dans";
		case OUTSIDE: return "dehors";
		case PAST: return "il y a";
		case POSSESSION: return "de";
		case PROXIMITY: return "par";
		case ROLE: return "comme";
		case SINCE: return "depuis";
		case SITUATION: return "sous";
		case SOURCE: return "de";
		case SUBJECT: return "sur"; //
		case THROUGH: return "par"; // à travers
		default:
			break;
		
		}
		
		return "";
	}
	
	@Override
	public String getAdverbial(Adverbial adv, String param) {
		switch (adv){
		case CONDITION: return "si";
		case CONSESSION: return "bien que";
		case MANNER: return "comme";
		case PLACE: return "où";
		case PURPOSE: return "afin de";
		case REASON: return "puisque";
		case TIME: return "quand";
		default:
			break;
		
		}
		return "";
	}

	@Override
	public String getRelative(Relative rel, String param) {
		
		if (rel.name().startsWith("IO_")){
			String objPronoun = param.contains("person")? "whom": "which";
			Adpositional adp = Adpositional.valueOf(rel.name().split("_")[1]);
			return getAdposition(adp, param) + " " + objPronoun;
		}
		
		//lequel, laquelle, etc.
		switch (rel){
		case OBJECT:
			if (param.contains("person"))
				return "qui";
			return "que";
		case POSSESSIVE: return "dont";
		//TODO lequel, laquelle
		case REASON: return "pour lequel"; 
		case SUBJECT:
			if (param.contains("person"))
				return "qui";
			return "qui";
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

		case SUBJECTIVE:
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
		
		case OBJECTIVE:
			switch (pronoun.getPerson()){
			case FIRST:
				return "moi";
			case SECOND:
				return "toi";
			default:
				break;
			}
			
			switch (pronoun.getNumber()) {
			case NONE:
				return "lui";
				
			case SINGLE:
				switch (pronoun.getGender()) {
				case FEMALE:
					return "elle";
				case MALE:
					return "lui";
				default:
					return "lui";
				}
			default:
				switch (pronoun.getGender()) {
				case FEMALE:
					return "elles";
				case MALE:
					return "eux";
				default:
					return "eux";
				}
			}
			
		case POSSESSIVE:
			switch (pronoun.getPerson()){
			case FIRST:
				return "mon";
			case SECOND:
				return "ton";
			default:
				break;
			}
			
			switch (pronoun.getNumber()) {
			case NONE:
				return "son";
				
			case SINGLE:
				switch (pronoun.getGender()) {
				case FEMALE:
					return "son";
				case MALE:
					return "son";
				default:
					return "son";
				}
			default:
				switch (pronoun.getGender()) {
				case FEMALE:
					return "leur";
				case MALE:
					return "leur";
				default:
					return "leur";
				}
			}
			
		default:
			return "";
		}
	}
	
	

}
