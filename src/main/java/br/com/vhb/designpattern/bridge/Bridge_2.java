package br.com.vhb.designpattern.bridge;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

interface RendererExample {
	void renderCircle(float radius);
}

class VectorRendererExample implements RendererExample {
	@Override
	public void renderCircle(float radius) {
		System.out.println("Drawing a circle of radius " + radius);
	}
}

class RasterRendererExample implements RendererExample {
	@Override
	public void renderCircle(float radius) {
		System.out.println("Drawing pixels for a circle of radius " + radius);
	}
}

abstract class ShapeExample {
	protected RendererExample renderer;

	public ShapeExample(RendererExample renderer) {
		this.renderer = renderer;
	}

	public abstract void draw();

	public abstract void resize(float factor);
}

class Circle extends ShapeExample {
	public float radius;

	@Inject
	public Circle(RendererExample renderer) {
		super(renderer);
	}

	public Circle(RendererExample renderer, float radius) {
		super(renderer);
		this.radius = radius;
	}

	@Override
	public void draw() {
		renderer.renderCircle(radius);
	}

	@Override
	public void resize(float factor) {
		radius *= factor;
	}
}

class ShapeModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(RendererExample.class).to(VectorRendererExample.class);
	}
}

public class Bridge_2 {
	public static void main(String[] args) {
		// RasterRenderer rasterRenderer = new RasterRenderer();
		// VectorRenderer vectorRenderer = new VectorRenderer();
		// Circle circle = new Circle(vectorRenderer, 5);
		// circle.draw();
		// circle.resize(2);
		// circle.draw();

		// todo: Google Guice
		Injector injector = Guice.createInjector(new ShapeModule());
		Circle instance = injector.getInstance(Circle.class);
		instance.radius = 3;
		instance.draw();
		instance.resize(2);
		instance.draw();
	}
}
