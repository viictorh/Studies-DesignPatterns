package br.com.vhb.designpattern.interpreter;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ExpressionProcessor_Prof {

	@Test
	public void test() {
		ExpressionProcessor_Prof ep = new ExpressionProcessor_Prof();
		ep.variables.put('x', 5);
		ep.variables.put('y', 3);

		assertEquals(1, ep.calculate("1"));
		assertEquals(3, ep.calculate("1+2"));
		assertEquals(6, ep.calculate("1+x"));
		assertEquals(23, ep.calculate("1+5-1+18"));
		assertEquals(23, ep.calculate("1+5-1+18"));
		assertEquals(4, ep.calculate("-1+5"));
		assertEquals(3, ep.calculate("-1+5-1"));
	}

	public Map<Character, Integer> variables = new HashMap<>();

	enum NextOp {
		NOTHING, PLUS, MINUS
	}

	public static Integer tryParse(String text) {
		try {
			return Integer.parseInt(text);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public int calculate(String expression) {
		int current = 0;
		NextOp nextOp = NextOp.NOTHING;
		System.out.println("Expression: " + expression);
		String[] parts = expression.split("(?<=[+-])");

		for (String part : parts) {
			String[] noOp = part.split("[+-]");
			String first = noOp[0];
			int value;

			Integer z = tryParse(first);
			if (z != null) {
				value = z.intValue();
			} else if (first.length() == 1 && variables.containsKey(first.charAt(0))) {
				value = variables.get(first.charAt(0));
			} else
				return 0;

			switch (nextOp) {
			case NOTHING:
				current = value;
				break;
			case PLUS:
				current += value;
				break;
			case MINUS:
				current -= value;
				break;
			}

			if (part.endsWith("+"))
				nextOp = NextOp.PLUS;
			else if (part.endsWith("-"))
				nextOp = NextOp.MINUS;
		}
		return current;
	}
}
