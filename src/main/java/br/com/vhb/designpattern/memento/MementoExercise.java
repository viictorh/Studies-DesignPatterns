package br.com.vhb.designpattern.memento;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * <p>
 * A <code>TokenMachine</code> is in charge of keeping tokens. Each
 * <code>Token</code> is a reference type with a single numerical value. The
 * machine supports adding tokens and, when it does, it returns a memento
 * representing the state of that system <em>at that given time</em>.
 * </p>
 * 
 * <p>
 * You are asked to fill in the gaps and implement the Memento design pattern
 * for this scenario. Pay close attention to the situation where a token is fed
 * in as a reference and its value is subsequently changed on that reference -
 * you still need to return the correct system snapshot!
 * </p>
 * 
 */
class Token {
	public int value = 0;

	public Token(int value) {
		this.value = value;
	}
}

class Memento {
	public List<Token> tokens;

	public Memento(List<Token> tokens) {
		this.tokens = new ArrayList<>(tokens);
	}

}

class TokenMachine {
	public List<Token> tokens = new ArrayList<>();

	public Memento addToken(int value) {
		Token token = new Token(value);
		tokens.add(token);
		return new Memento(tokens);
	}

	public Memento addToken(Token token) {
		return addToken(token.value);
	}

	public void revert(Memento m) {
		tokens = new ArrayList<>(m.tokens);
	}
}

public class MementoExercise {
	@Test
	public void singleTokenTest() {
		TokenMachine tm = new TokenMachine();
		Memento m = tm.addToken(123);
		tm.addToken(456);
		tm.revert(m);

		assertEquals(1, tm.tokens.size());
		assertEquals(123, tm.tokens.get(0).value);
	}

	@Test
	public void twoTokenTest() {
		TokenMachine tm = new TokenMachine();
		tm.addToken(1);
		Memento m = tm.addToken(2);
		tm.addToken(3);
		tm.revert(m);
		assertEquals(2, tm.tokens.size());
		assertEquals("First token should have value 1", 1, tm.tokens.get(0).value);
		assertEquals(2, tm.tokens.get(1).value);
	}

	@Test
	public void fiddledTokenTest() {
		TokenMachine tm = new TokenMachine();
		System.out.println("Made a token with value 111 and kept a reference");
		Token token = new Token(111);
		System.out.println("Added this token to the list");
		tm.addToken(token);
		Memento m = tm.addToken(222);
		System.out.println("Changed this token's value to 333 :)");
		token.value = 333;
		tm.revert(m);

		assertEquals(
				"At this point, token machine should have exactly two tokens, " + "but you got " + tm.tokens.size(), 2,
				tm.tokens.size());

		assertEquals("You got the token value wrong here. " + "Hint: did you init the memento by value?", 111,
				tm.tokens.get(0).value);
	}
}
