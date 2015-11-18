package dz.aak.nalangen.wordnet;


public class WNRequestor {

	private String lang = "";
	
	/**
	 * This class needs the 
	 * @param lang
	 */
	public WNRequestor(String lang) {
		this.lang = lang;
	}
	
	/**
	 * 
	 * @param synset The synset of the word in Wordnet
	 * @param pos is a string which is the "Part of speech" of the word. 
	 * It can be: "VERB", "NOUN", "ADJECTIVE" or "ADVERB"
	 * @return 
	 */
	public String getWord(int synset, String pos){
		
		return "";
	}

}
