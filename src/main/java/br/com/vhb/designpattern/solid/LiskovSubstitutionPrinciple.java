package br.com.vhb.designpattern.solid;

import java.util.Arrays;

interface GeometricShape {

	public double calcArea();
}

class Rectangle implements GeometricShape {
	protected int width, height;

	public Rectangle() {
	}

	public Rectangle(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public double calcArea() {
		return width * height;
	}

	@Override
	public String toString() {
		return "Rectangle{" + "width=" + width + ", height=" + height + '}';
	}

}

class Square implements GeometricShape {
	private int size;

	public Square() {
	}

	public Square(int size) {
		this.size = size;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public double calcArea() {
		return Math.pow(this.size, 2);
	}

	@Override
	public String toString() {
		return "Square [size=" + size + "]";
	}
}

class SquareBroken extends Rectangle {

	public SquareBroken(int size) {
		super.setHeight(size);
		super.setWidth(size);
	}

	@Override
	public void setHeight(int height) {
		super.setHeight(height);
		super.setWidth(height);
	}

	@Override
	public void setWidth(int width) {
		super.setWidth(width);
		super.setHeight(width);
	}

	@Override
	public String toString() {
		return "SquareBroken{" + "width=" + width + ", height=" + height + '}';
	}

}

public class LiskovSubstitutionPrinciple {

	public static void useIt(Rectangle rect) {
		int width = rect.getWidth();
		int height = 10;
		rect.setHeight(height);
		System.out.println("Expected heigh of rectangle: " + width * height);
		System.out.println("Got: " + rect.calcArea());
	}

	public static void main(String[] args) {
		GeometricShape rc = new Rectangle(2, 3);
		GeometricShape sq = new Square(5);
		Arrays.asList(rc, sq).forEach(gs -> System.out.println(gs + " - Area = " + gs.calcArea()));

		System.out.println("BROKEN LSP");
		/*
		 * por mais que o código execute corretamente, neste caso, caso ocorra uma
		 * instanciação desta classe por engano, o usuário precisaria saber como é o
		 * funcionamento da classe "SquareBroken" para saber que ao alterar a altura,
		 * também altera-se a largura. Além disso, um método, por exemplo, que recebe um
		 * "retangulo" poderia alterar uma das propriedades sem saber que também está,
		 * consequentemente, alterando a outra, por exemplo no metodo #useIt()
		 */
		Rectangle brc = new Rectangle(2, 3);
		useIt(brc);
		Rectangle bsq = new SquareBroken(5);
		bsq.setHeight(4);
		bsq.setWidth(5);
		useIt(bsq);
		System.out.println("");
		Arrays.asList(brc, bsq).forEach(gs -> System.out.println(gs + " - Area = " + gs.calcArea()));
	}
}
