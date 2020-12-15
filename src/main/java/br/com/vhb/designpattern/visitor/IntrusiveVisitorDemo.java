package br.com.vhb.designpattern.visitor;

abstract class Expression_1 {
	public abstract void print(StringBuilder sb);
}

class DoubleExpression_1 extends Expression_1 {
	private double value;

	public DoubleExpression_1(double value) {
		this.value = value;
	}

	@Override
	public void print(StringBuilder sb) {
		sb.append(value);
	}
}

class AdditionExpression_1 extends Expression_1 {
	private Expression_1 left, right;

	public AdditionExpression_1(Expression_1 left, Expression_1 right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public void print(StringBuilder sb) {
		sb.append("(");
		left.print(sb);
		sb.append("+");
		right.print(sb);
		sb.append(")");
	}
}

public class IntrusiveVisitorDemo {
	public static void main(String[] args) {
		// 1+(2+3)
		AdditionExpression_1 e = new AdditionExpression_1(new DoubleExpression_1(1),
				new AdditionExpression_1(new DoubleExpression_1(2), new DoubleExpression_1(3)));
		StringBuilder sb = new StringBuilder();
		e.print(sb);
		System.out.println(sb);
	}
}
