package br.com.vhb.designpattern.visitor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * <p>
 * You are asked to implement a double-dispatch visitor called
 * <code>ExpressionPrinter</code> for printing different mathematical
 * expressions. The range of expressions covers addition and multiplication -
 * please put round brackets after around addition but <em>not</em> around
 * multiplication! Also, please avoid any blank spaces in output.
 * </p>
 * <p>
 * Example:
 * </p>
 * <ul>
 * <li>
 * <p>
 * Input: <code>AdditionExpression(Value(2), Value(3))</code>
 * </p>
 * </li>
 * <li>
 * <p>
 * Output: <code>(2+3)</code>
 * </p>
 * </li>
 * </ul>
 * <p>
 * Here is the corresponding unit test:
 * </p>
 * <div class="ud-component--base-components--code-block"><div>
 * 
 * <pre class="prettyprint linenums prettyprinted" role="presentation" style="">
 * <ol class="linenums"><li class="L0"><span class=
 * "typ">AdditionExpression</span><span class="pln"> simple </span><span class=
 * "pun">=</span><span class="pln"> </span><span class=
 * "kwd">new</span><span class="pln"> </span><span class=
 * "typ">AdditionExpression</span><span class="pun">(</span><span class=
 * "kwd">new</span><span class="pln"> </span><span class=
 * "typ">Value</span><span class="pun">(</span><span class=
 * "lit">2</span><span class="pun">),</span><span class=
 * "pln"> </span><span class="kwd">new</span><span class=
 * "pln"> </span><span class="typ">Value</span><span class=
 * "pun">(</span><span class="lit">3</span><span class=
 * "pun">));</span></li><li class="L1"><span class=
 * "typ">ExpressionPrinter</span><span class="pln"> ep </span><span class=
 * "pun">=</span><span class="pln"> </span><span class=
 * "kwd">new</span><span class="pln"> </span><span class=
 * "typ">ExpressionPrinter</span><span class="pun">();</span></li><li class=
 * "L2"><span class="pln">ep</span><span class="pun">.</span><span class=
 * "pln">visit</span><span class="pun">(</span><span class=
 * "pln">simple</span><span class="pun">);</span></li><li class="L3"><span class
 * ="pln">assertEquals</span><span class="pun">(</span><span class=
 * "str">"(2+3)"</span><span class="pun">,</span><span class=
 * "pln"> ep</span><span class="pun">.</span><span class=
 * "pln">toString</span><span class="pun">());</span></li></ol>
 * </pre>
 * 
 * @author victor
 *
 */
abstract class ExpressionVisitor {
	abstract void visit(Value value);

	abstract void visit(AdditionExpression ae);

	abstract void visit(MultiplicationExpression me);
}

abstract class Expression {
	abstract void accept(ExpressionVisitor ev);
}

class Value extends Expression {
	public int value;

	public Value(int value) {
		this.value = value;
	}

	@Override
	void accept(ExpressionVisitor ev) {
		ev.visit(this);
	}
}

class AdditionExpression extends Expression {
	public Expression lhs, rhs;

	public AdditionExpression(Expression lhs, Expression rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	void accept(ExpressionVisitor ev) {
		ev.visit(this);
	}
}

class MultiplicationExpression extends Expression {
	public Expression lhs, rhs;

	public MultiplicationExpression(Expression lhs, Expression rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	void accept(ExpressionVisitor ev) {
		ev.visit(this);
	}
}

class ExpressionCalculator extends ExpressionVisitor {
	private int result;

	@Override
	void visit(Value value) {
		result = value.value;
	}

	@Override
	void visit(AdditionExpression ae) {
		int aux = result;
		ae.lhs.accept(this);
		int aux1 = result;
		ae.rhs.accept(this);
		int aux2 = result;
		result = aux + aux1 + aux2;
	}

	@Override
	void visit(MultiplicationExpression me) {
		int aux = result;
		me.lhs.accept(this);
		int aux1 = result;
		me.rhs.accept(this);
		int aux2 = result;
		result = aux + (aux1 * aux2);
	}

	public int getResult() {
		return result;
	}

}

class ExpressionPrinter extends ExpressionVisitor {
	private StringBuilder sb = new StringBuilder();

	@Override
	public String toString() {
		return sb.toString();
	}

	@Override
	void visit(Value value) {
		sb.append(value.value);
	}

	@Override
	void visit(AdditionExpression ae) {
		sb.append("(");
		ae.lhs.accept(this);
		sb.append("+");
		ae.rhs.accept(this);
		sb.append(")");
	}

	@Override
	void visit(MultiplicationExpression me) {
		me.lhs.accept(this);
		sb.append("*");
		me.rhs.accept(this);

	}
}

public class VisitorExercise {

	@Test
	public void simpleAddition() {
		AdditionExpression simple = new AdditionExpression(new Value(2), new Value(3));
		ExpressionPrinter ep = new ExpressionPrinter();
		ExpressionCalculator ec = new ExpressionCalculator();
		ec.visit(simple);
		ep.visit(simple);
		assertEquals("(2+3)", ep.toString());
		assertEquals(5, ec.getResult());
	}

	@Test
	public void productOfAdditionAndValue() {
		MultiplicationExpression expr = new MultiplicationExpression(new AdditionExpression(new Value(2), new Value(3)),
				new Value(4));
		ExpressionPrinter ep = new ExpressionPrinter();
		ExpressionCalculator ec = new ExpressionCalculator();
		ec.visit(expr);
		ep.visit(expr);
		assertEquals("(2+3)*4", ep.toString());
		assertEquals(20, ec.getResult());
	}

	@Test
	public void productOfAdditionAndValueAndProduct() {
		MultiplicationExpression expr = new MultiplicationExpression(
				new MultiplicationExpression(new AdditionExpression(new Value(2), new Value(3)), new Value(4)),
				new Value(15));
		ExpressionPrinter ep = new ExpressionPrinter();
		ExpressionCalculator ec = new ExpressionCalculator();
		ec.visit(expr);
		ep.visit(expr);
		assertEquals("(2+3)*4*15", ep.toString());
		assertEquals(300, ec.getResult());
	}
}
