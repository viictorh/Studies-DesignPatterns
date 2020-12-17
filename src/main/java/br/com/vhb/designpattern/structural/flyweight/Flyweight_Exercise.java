package br.com.vhb.designpattern.structural.flyweight;

import java.util.*;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

//MINHA SOLUÇÃO
class Sentence {
	private List<WordToken> wordTokens = new ArrayList<WordToken>();

	public Sentence(String plainText) {
		wordTokens = Arrays.stream(plainText.split(" ")).map(s -> new WordToken(s)).collect(Collectors.toList());
	}

	public WordToken getWord(int index) {
		return wordTokens.get(index);
	}

	@Override
	public String toString() {
		return wordTokens.stream().map(w -> w.toString()).collect(Collectors.joining(" "));
	}

	class WordToken {
		private String token;
		public boolean capitalize;

		public WordToken(String token) {
			this.token = token;
		}

		@Override
		public String toString() {
			if (capitalize) {
				return token.toUpperCase();
			}
			return token;
		}
	}
}

// SOLUÇÃO PROFESSOR:
class Sentence2 {
	private String[] words;
	private Map<Integer, WordToken2> tokens = new HashMap<>();

	public Sentence2(String plainText) {
		words = plainText.split(" ");
	}

	public WordToken2 getWord(int index) {
		WordToken2 wt = new WordToken2();
		tokens.put(index, wt);
		return tokens.get(index);
	}

	@Override
	public String toString() {
		ArrayList<String> ws = new ArrayList<>();
		for (int i = 0; i < words.length; ++i) {
			String w = words[i];
			if (tokens.containsKey(i) && tokens.get(i).capitalize)
				w = w.toUpperCase();
			ws.add(w);
		}
		return String.join(" ", ws);
	}

	class WordToken2 {
		public boolean capitalize;
	}
}

public class Flyweight_Exercise {

	@Test
	public void capitalized() {
		Sentence s = new Sentence("alpha beta gamma");
		s.getWord(1).capitalize = true;
		Assert.assertEquals("alpha BETA gamma", s.toString());
		s.getWord(2).capitalize = true;
		Assert.assertEquals("alpha BETA GAMMA", s.toString());
	}

}
