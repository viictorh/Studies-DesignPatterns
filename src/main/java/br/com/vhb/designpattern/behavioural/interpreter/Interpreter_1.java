package br.com.vhb.designpattern.behavioural.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Token {
	public enum Type {
		INTEGER, PLUS, MINUS, LPAREN, RPAREN
	}

	Type type;
	String text;

	public Token(Type type, String text) {
		this.type = type;
		this.text = text;
	}

	@Override
	public String toString() {
		return "`" + text + "`";
	}
}

interface Element {
	int eval();
}

class IntegerEx implements Element {

	int value;

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public int eval() {
		return value;
	}

	public IntegerEx(int value) {
		this.value = value;
	}
}

class BinaryOperationIt implements Element {

	public enum Type {
		ADDITION, SUBTRACTION
	}

	Type type;
	Element left, right;

	@Override
	public int eval() {
		switch (type) {
		case ADDITION:
			return left.eval() + right.eval();
		case SUBTRACTION:
			return left.eval() - right.eval();
		default:
			return 0;
		}
	}
}

class Lexing {

	static Element parse(List<Token> tokens) {
		BinaryOperationIt result = new BinaryOperationIt();
		boolean haveLHS = false;

		for (int i = 0; i < tokens.size(); i++) {
			Token token = tokens.get(i);
			switch (token.type) {
			case INTEGER:
				IntegerEx integer = new IntegerEx(java.lang.Integer.parseInt(token.text));
				if (!haveLHS) {
					result.left = integer;
					haveLHS = true;
				} else
					result.right = integer;
				break;
			case PLUS:
				result.type = BinaryOperationIt.Type.ADDITION;
				break;
			case MINUS:
				result.type = BinaryOperationIt.Type.SUBTRACTION;
				break;
			case LPAREN:
				int j = i;
				for (; j < tokens.size(); ++j) {
					if (tokens.get(j).type.equals(Token.Type.RPAREN))
						break;
				}
				List<Token> subTokens = tokens.stream().skip(i + 1).limit(j - i - 1).collect(Collectors.toList());
				Element element = parse(subTokens);

				if (!haveLHS) {
					result.left = element;
					haveLHS = true;
				} else
					result.right = element;
				i = j++;
				break;

			}
		}

		return result;

	}

	static List<Token> lex(String input) {
		List<Token> result = new ArrayList();
		for (int i = 0; i < input.length(); i++) {
			switch (input.charAt(i)) {
			case '+':
				result.add(new Token(Token.Type.PLUS, "+"));
				break;
			case '-':
				result.add(new Token(Token.Type.MINUS, "-"));
				break;
			case '(':
				result.add(new Token(Token.Type.LPAREN, "("));
				break;
			case ')':
				result.add(new Token(Token.Type.RPAREN, ")"));
				break;
			default:
				StringBuilder sb = new StringBuilder(input.charAt(i));
				for (int j = i; j < input.length(); j++) {
					if (Character.isDigit(input.charAt(j))) {
						sb.append(input.charAt(j));
						i = j;
					} else {
						result.add(new Token(Token.Type.INTEGER, sb.toString()));
						break;
					}
				}
				break;
			}
		}
		return result;
	}
}

public class Interpreter_1 {
	public static void main(String[] args) {
		final String input = "(13+4)-(12+1)";
		List<Token> tokens = Lexing.lex(input);
		System.out.println("tokens = " + tokens.stream().map(t -> t.toString()).collect(Collectors.joining("\t")));

		Element results = Lexing.parse(tokens);
		System.out.println(input + " = " + results.eval());
	}

}
