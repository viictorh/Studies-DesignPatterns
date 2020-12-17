package br.com.vhb.designpattern.behavioural.interpreter;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;

import org.junit.Test;

/**
 * <p>
 * You are asked to write an expression processor for simple numeric expressions
 * with the following constraints:
 * </p>
 * 
 * <ul>
 * <li>Expressions use integral values (e.g.,&nbsp;<code>'13'</code>&nbsp;),
 * single-letter variables defined in Variables, as well as + and - operators
 * only</li>
 * <li>There is no need to support braces or any other operations</li>
 * <li>If a variable is not found in&nbsp;<code>variables</code>&nbsp; (or if we
 * encounter a variable with &gt;1 letter, e.g. ab), the evaluator returns 0
 * (zero)</li>
 * <li>In case of any parsing failure, evaluator returns 0</li>
 * </ul>
 * 
 * <p>
 * Example:
 * </p>
 * 
 * <ul>
 * <li><code>calculate("1+2+3")</code>&nbsp; should return 6</li>
 * <li><code>calculate("1+2+xy")</code>&nbsp; should return 0</li>
 * <li><code>calculate("10-2-x")</code>&nbsp; when x=3 is in
 * <code>variables</code>&nbsp;should return 5</li>
 * </ul>
 */
public class InterpreterExercise {
	@Test
	public void test() {
		ExpressionProcessor_EU ep = new ExpressionProcessor_EU();
		ep.variables.put('x', 5);
		ep.variables.put('y', 3);

		assertEquals(1, ep.calculate("1"));
		assertEquals(3, ep.calculate("1+2"));
		assertEquals(6, ep.calculate("1+x"));
		assertEquals(54, ep.calculate("1+xy"));
		assertEquals(23, ep.calculate("1+5-1+18"));
		assertEquals(4, ep.calculate("-1+5"));
		assertEquals(3, ep.calculate("-1+5-1"));
	}
}

class MathBuilder {
	private Integer a;
	private BinaryOperator<Integer> op;

	public static MathBuilder start() {
		return new MathBuilder();
	}

	public static MathBuilder start(String number) {
		MathBuilder mathBuilder = new MathBuilder();
		mathBuilder.a = Integer.parseInt(number);
		return mathBuilder;
	}

	public MathBuilder add(String snumber) {
		process(snumber);
		op = (a, b) -> a + b;
		return this;
	}

	public MathBuilder sub(String snumber) {
		process(snumber);
		op = (a, b) -> a - b;
		return this;
	}

	public int build(String lasNumber) {
		int i = Integer.parseInt(lasNumber);
		if (op == null)
			return i;
		return op.apply(a, i);
	}

	private void process(String snumber) {
		int number = Integer.parseInt(snumber);
		if (a != null) {
			a = op.apply(a, number);
		} else {
			a = number;
		}
	}
}

class ExpressionEvalution {
	private Map<Character, Integer> variables;
	private String expression;

	public ExpressionEvalution(Map<Character, Integer> variables, String expression) {
		this.variables = variables;
		this.expression = expression;
	}

	public int calc() throws java.text.ParseException {
		char[] exp = expression.toCharArray();
		StringBuilder sb = new StringBuilder();
		MathBuilder mb = new MathBuilder();

		for (int i = 0; i < exp.length; i++) {
			char c = exp[i];
			Integer v = variables.get(c);
			if (v != null)
				sb.append(v);
			else if (sb.length() == 0 || Character.isDigit(c))
				sb.append(c);
			else if (c == '+') {
				mb.add(sb.toString());
				sb = new StringBuilder();
			} else if (c == '-') {
				mb.sub(sb.toString());
				sb = new StringBuilder();
			} else {
				throw new ParseException(expression, i);
			}
		}
		return mb.build(sb.toString());
	}
}

class ExpressionProcessor_EU {
	public Map<Character, Integer> variables = new HashMap<>();

	public int calculate(String expression) {
		try {
			int calc = new ExpressionEvalution(variables, expression).calc();
			System.out.println("Expression:" + expression + " result: " + calc);
			return calc;
		} catch (ParseException e) {
			return 0;
		}

	}
}
