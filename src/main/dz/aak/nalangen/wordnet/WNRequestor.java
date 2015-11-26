package dz.aak.nalangen.wordnet;

import java.util.List;

public interface WNRequestor {
	
	public String getWord(int synset, String pos);
	
	public List<String> getWords(int synset, String pos);
	
	public int getSynset(String word, String pos);

}
