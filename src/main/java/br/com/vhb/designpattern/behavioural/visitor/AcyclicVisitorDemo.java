package br.com.vhb.designpattern.behavioural.visitor;

interface Visitor_4 {
}

interface ExpressionVisitor_4 extends Visitor_4 {
	void visit(Expression_4 obj);
}

interface DoubleExpressionVisitor_4 extends Visitor_4 {
	void visit(DoubleExpression obj);
}

interface AdditionExpressionVisitor extends Visitor_4 {
	void visit(AdditionExpression_4 obj);
}

abstract class Expression_4 {
	// optional
	public void accept(Visitor_4 visitor) {
		if (visitor instanceof ExpressionVisitor_4) {
			((ExpressionVisitor_4) visitor).visit(this);
		}
	}
}

class DoubleExpression extends Expression_4 {
	public double value;

	public DoubleExpression(double value) {
		this.value = value;
	}

	@Override
	public void accept(Visitor_4 visitor) {
		if (visitor instanceof ExpressionVisitor_4) {
			((ExpressionVisitor_4) visitor).visit(this);
		}
	}
}

class AdditionExpression_4 extends Expression_4 {
	public Expression_4 left, right;

	public AdditionExpression_4(Expression_4 left, Expression_4 right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public void accept(Visitor_4 visitor) {
		if (visitor instanceof AdditionExpressionVisitor) {
			((AdditionExpressionVisitor) visitor).visit(this);
		}
	}
}

class ExpressionPrinter_4 implements DoubleExpressionVisitor_4, AdditionExpressionVisitor {
	private StringBuilder sb = new StringBuilder();

	@Override
	public void visit(DoubleExpression obj) {
		sb.append(obj.value);
	}

	@Override
	public void visit(AdditionExpression_4 obj) {
		sb.append('(');
		obj.left.accept(this);
		sb.append('+');
		obj.right.accept(this);
		sb.append(')');
	}

	@Override
	public String toString() {
		return sb.toString();
	}
}

public class AcyclicVisitorDemo {
	public static void main(String[] args) {
		AdditionExpression_4 e = new AdditionExpression_4(new DoubleExpression(1),
				new AdditionExpression_4(new DoubleExpression(2), new DoubleExpression(3)));
		ExpressionPrinter_4 ep = new ExpressionPrinter_4();
		ep.visit(e);
		System.out.println(ep.toString());
	}
}
