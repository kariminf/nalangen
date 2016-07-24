package kariminf.nalangen.nlg.simplenlg;


import kariminf.sentrep.UnivMap;

public class FraRealizer extends SNLGRealizer {
	
	public FraRealizer() {
		super(new FraSnlgMap(), new simplenlg.lexicon.french.XMLLexicon());
	}

	@Override
	protected String getOrdinal(String number) {
		int nbr = 0;
		
		try {
			nbr = Integer.parseInt(number);
		}
		catch (NumberFormatException e){
			return "premier";
		}
		
		switch(nbr){
		case 1: return "premier";
		case 2: return "deuxième";
		case 3: return "troisième";
		case 4: return "quatrième";
		case 5: return "cinquième";
		case 6: return "sixième";
		case 7: return "septième";
		case 8: return "huitième";
		case 9: return "neuvième";
		case 10: return "dixième";
		}
		
		return nbr + "ième";
	}

}
