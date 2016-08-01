package kariminf.nalangen.nlg.simplenlg;

import simplenlg.lexicon.Lexicon;

public class EngRealizer extends SNLGRealizer {

	public EngRealizer() {
		super(new EngSnlgMap(), Lexicon.getDefaultLexicon());
	}

	@Override
	protected String getOrdinal(String number) {
		int nbr = 0;
		
		try {
			nbr = Integer.parseInt(number);
		}
		catch (NumberFormatException e){
			return "first";
		}
		
		switch(nbr){
		case 1: return "first";
		case 2: return "second";
		case 3: return "third";
		case 4: return "fourth";
		case 5: return "fifth";
		case 6: return "sixth";
		case 7: return "seventh";
		case 8: return "eighth";
		case 9: return "nineth";
		case 10: return "tenth";
		}
		
		return nbr + "th";
	}
	
}
