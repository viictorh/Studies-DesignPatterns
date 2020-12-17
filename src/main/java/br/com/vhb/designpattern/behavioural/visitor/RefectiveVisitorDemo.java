package br.com.vhb.designpattern.behavioural.visitor;

abstract class Expression_2 {
}

class DoubleExpression_2 extends Expression_2 {
	public double value;

	public DoubleExpression_2(double value) {
		this.value = value;
	}
}

class AdditionExpression_2 extends Expression_2 {
	public Expression_2 left, right;

	public AdditionExpression_2(Expression_2 left, Expression_2 right) {
		this.left = left;
		this.right = right;
	}
}

// separation of concerns
class ExpressionPrinter_2 {
	public static void print(Expression_2 e, StringBuilder sb) {
		if (e.getClass() == DoubleExpression_2.class) {
			sb.append(((DoubleExpression_2) e).value);
		} else if (e.getClass() == AdditionExpression_2.class) {
			AdditionExpression_2 ae = (AdditionExpression_2) e;
			sb.append("(");
			print(ae.left, sb);
			sb.append("+");
			print(ae.right, sb);
			sb.append(")");
		}
	}
}

public class RefectiveVisitorDemo {
	public static void main(String[] args) {
		// 1+(2+3)
		AdditionExpression_2 e = new AdditionExpression_2(new DoubleExpression_2(1),
				new AdditionExpression_2(new DoubleExpression_2(2), new DoubleExpression_2(3)));
		StringBuilder sb = new StringBuilder();
		ExpressionPrinter_2.print(e, sb);
		System.out.println(sb);
	}
}
