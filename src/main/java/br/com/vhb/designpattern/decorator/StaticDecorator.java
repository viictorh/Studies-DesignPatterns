package br.com.vhb.designpattern.decorator;

import java.util.function.Supplier;

interface ShapeStatic {
	String info();
}

class CircleStatic implements ShapeStatic {
	private float radius;

	CircleStatic() {
	}

	public CircleStatic(float radius) {
		this.radius = radius;
	}

	void resize(float factor) {
		radius *= factor;
	}

	@Override
	public String info() {
		return "A circle of radius " + radius;
	}
}

class SquareStatic implements ShapeStatic {
	private float side;

	public SquareStatic() {
	}

	public SquareStatic(float side) {
		this.side = side;
	}

	@Override
	public String info() {
		return "A square with side " + side;
	}
}

// we are NOT altering the base class of these objects
// cannot make ColoredSquare, ColoredCircle

class ColoredShapeStatic<T extends ShapeStatic> implements ShapeStatic {
	private ShapeStatic shape;
	private String color;

	public ColoredShapeStatic(Supplier<? extends T> ctor, String color) {
		shape = ctor.get();
		this.color = color;
	}

	@Override
	public String info() {
		return shape + " has the color " + color;
	}
}

class TransparentShapeStatic<T extends ShapeStatic> implements ShapeStatic {
	private ShapeStatic shape;
	private int transparency;

	public TransparentShapeStatic(Supplier<? extends T> ctor, int transparency) {
		shape = ctor.get();
		this.transparency = transparency;
	}

	@Override
	public String info() {
		return shape + " has " + transparency + "% transparency";
	}
}

public class StaticDecorator {
	public static void main(String[] args) {
		Circle circle = new Circle(10);
		System.out.println(circle.info());

		ColoredShapeStatic<SquareStatic> blueSquare = new ColoredShapeStatic<>(() -> new SquareStatic(20), "blue");
		System.out.println(blueSquare.info());

		// ugly!
		TransparentShapeStatic<ColoredShapeStatic<CircleStatic>> myCircle = new TransparentShapeStatic<>(
				() -> new ColoredShapeStatic<>(() -> new CircleStatic(5), "green"), 50);
		System.out.println(myCircle.info());
		// cannot call myCircle.resize()
	}
}
