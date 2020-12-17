package br.com.vhb.designpattern.behavioural.visitor;

interface ExpressionVisitor_3 {
	void visit(DoubleExpression_3 e);

	void visit(AdditionExpression_3 e);
}

abstract class Expression_3 {
	public abstract void accept(ExpressionVisitor_3 visitor);
}

class DoubleExpression_3 extends Expression_3 {
	public double value;

	public DoubleExpression_3(double value) {
		this.value = value;
	}

	@Override
	public void accept(ExpressionVisitor_3 visitor) {
		visitor.visit(this);
	}
}

class AdditionExpression_3 extends Expression_3 {
	public Expression_3 left, right;

	public AdditionExpression_3(Expression_3 left, Expression_3 right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public void accept(ExpressionVisitor_3 visitor) {
		visitor.visit(this);
	}
}

// separation of concerns
class ExpressionPrinter_3 implements ExpressionVisitor_3 {
	private StringBuilder sb = new StringBuilder();

	@Override
	public void visit(DoubleExpression_3 e) {
		sb.append(e.value);
	}

	@Override
	public void visit(AdditionExpression_3 e) {
		sb.append("(");
		e.left.accept(this);
		sb.append("+");
		e.right.accept(this);
		sb.append(")");
	}

	@Override
	public String toString() {
		return sb.toString();
	}
}

class ExpressionCalculator_3 implements ExpressionVisitor_3 {
	public double result;

	@Override
	public void visit(DoubleExpression_3 e) {
		result = e.value;
	}

	@Override
	public void visit(AdditionExpression_3 e) // this is a test too
	{
		e.left.accept(this);
		double a = result;
		e.right.accept(this);
		double b = result;
		result = a + b; // this is a test
	}
}

/**
 * Classic Visitor ou Double Dispatch
 * 
 * @author victor
 *
 */
public class ClassicVisitorDemo {
	public static void main(String[] args) {
		// 1+(2+3)
		AdditionExpression_3 e = new AdditionExpression_3(new DoubleExpression_3(1),
				new AdditionExpression_3(new DoubleExpression_3(2), new DoubleExpression_3(3)));
		ExpressionPrinter_3 ep = new ExpressionPrinter_3();
		ep.visit(e);
		System.out.println(ep);

		ExpressionCalculator_3 calc = new ExpressionCalculator_3();
		calc.visit(e);
		System.out.println(ep + " = " + calc.result);
	}
}
