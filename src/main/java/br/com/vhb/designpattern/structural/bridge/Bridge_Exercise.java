package br.com.vhb.designpattern.structural.bridge;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * CÓDIGO COM DESIGN ERRADO
 */
// abstract class Shape
// {
// public abstract String getName();
// }
//
// class Triangle extends Shape
// {
// @Override
// public String getName()
// {
// return "Triangle";
// }
// }
//
// class Square extends Shape
// {
// @Override
// public String getName()
// {
// return "Square";
// }
// }
//
// class VectorSquare extends Square
// {
// @Override
// public String toString()
// {
// return String.format("Drawing %s as lines", getName());
// }
// }
//
// class RasterSquare extends Square
// {
// @Override
// public String toString()
// {
// return String.format("Drawing %s as pixels", getName());
// }
// }

// imagine VectorTriangle and RasterTriangle are here too

abstract class Shape {
	protected Renderer renderer;

	public Shape(Renderer renderer) {
		this.renderer = renderer;
	}

	public abstract String getName();

	@Override
	public String toString() {
		return "Drawing " + getName() + " as " + renderer.whatToRenderAs();
	}

}

class Triangle extends Shape {
	public Triangle(Renderer renderer) {
		super(renderer);
	}

	@Override
	public String getName() {
		return "Triangle";
	}
}

class Square extends Shape {
	public Square(Renderer renderer) {
		super(renderer);
	}

	@Override
	public String getName() {
		return "Square";
	}
}

class VectorRenderer implements Renderer {

	@Override
	public String whatToRenderAs() {
		return "lines";
	}
}

class RasterRenderer implements Renderer {

	@Override
	public String whatToRenderAs() {
		return "pixels";
	}
}

interface Renderer {
	public String whatToRenderAs();
}

public class Bridge_Exercise {
	public static void main(String[] args) {
		System.out.println(new Triangle(new RasterRenderer()).toString());
	}

	@Test
	public void test() {
		assertEquals("Drawing Square as lines", new Square(new VectorRenderer()).toString());
	}
}
