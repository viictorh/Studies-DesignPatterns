package br.com.vhb.designpattern.behavioural.strategy;

import java.util.Objects;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * 
 * <p>
 * (fórmula bhaskara) <img src=
 * "https://img-a.udemycdn.com/redactor/raw/2017-07-25_13-02-23-6d1ea227c732e9ec42ca34d020236894.png?0R0gBILMak5OMD2tsUT4A7p--we1oCr04tIyd5FIw19fB-o6pG_FVmhSLkYhlOjEtXx2unoyaxSbZE02dxkOPXW-qQIa42Gl_N_EKlbCJlbrDhCrZbogay6y2_cawhimzk4619W_uipkZ0mKB--O7SN6-DEtW21v9sWTA1-n4rDz4xyq">
 * </p>
 * 
 * <p>
 * The part b^2-4*a*c is called the <em>discriminant</em>. Suppose we want to
 * provide an API with two different strategies for calculating the
 * discriminant:
 * </p>
 * 
 * <ol>
 * <li>In <code>OrdinaryDiscriminantStrategy</code> , If the discriminant is
 * negative, we return it as-is. This is OK, since our main API returns
 * <code>Complex</code> numbers anyway.</li>
 * <li>In <code>RealDiscriminantStrategy</code> , if the discriminant is
 * negative, the return value is NaN (not a number). NaN propagates throughout
 * the calculation, so the equation solver gives two NaN values.</li>
 * </ol>
 * 
 * <p>
 * Please implement both of these strategies as well as the equation solver
 * itself. With regards to plus-minus in the formula, please return the + result
 * as the first element and - as the second.
 * </p>
 * <p>
 * <strong>Note:</strong> the implementation uses two classes called Pair and
 * Complex -- complete source for both classes is provided in the exercise.<br>
 * </p>
 * 
 * @author victor
 *
 */
interface DiscriminantStrategy {
	double calculateDiscriminant(double a, double b, double c);
}

abstract class AbsDiscriminantStrategy implements DiscriminantStrategy {
	public double calculateDiscriminant(double a, double b, double c) {
		return Math.pow(b, 2) - 4 * a * c;
	}
}

class OrdinaryDiscriminantStrategy extends AbsDiscriminantStrategy {
	@Override
	public double calculateDiscriminant(double a, double b, double c) {
		return super.calculateDiscriminant(a, b, c);

	}
}

class RealDiscriminantStrategy extends AbsDiscriminantStrategy {
	@Override
	public double calculateDiscriminant(double a, double b, double c) {
		double result = super.calculateDiscriminant(a, b, c);
		return result < 0 ? Double.NaN : result;
	}
}

class QuadraticEquationSolver {
	private DiscriminantStrategy strategy;

	public QuadraticEquationSolver(DiscriminantStrategy strategy) {
		this.strategy = strategy;
	}

	public Pair<Complex, Complex> solve(double a, double b, double c) {
		double delta = strategy.calculateDiscriminant(a, b, c);
		Complex sqrt = Complex.sqrt(delta);
		return new Pair<>(new Complex(-b, 0).plus(sqrt).divides(new Complex(2, 0)).divides(new Complex(a, 0)),
				new Complex(-b, 0).minus(sqrt).divides(new Complex(2, 0)).divides(new Complex(a, 0)));
	}
}

// complex number implementation taken from here:
// https://introcs.cs.princeton.edu/java/32class/Complex.java.html
class Complex {
	private final double re; // the real part
	private final double im; // the imaginary part

	// create a new object with the given real and imaginary parts
	public Complex(double real, double imag) {
		re = real;
		im = imag;
	}

	// return a string representation of the invoking Complex object
	public String toString() {
		if (im == 0)
			return re + "";
		if (re == 0)
			return im + "i";
		if (im < 0)
			return re + " - " + (-im) + "i";
		return re + " + " + im + "i";
	}

	// return abs/modulus/magnitude
	public double abs() {
		return Math.hypot(re, im);
	}

	// return angle/phase/argument, normalized to be between -pi and pi
	public double phase() {
		return Math.atan2(im, re);
	}

	// return a new Complex object whose value is (this + b)
	public Complex plus(Complex b) {
		Complex a = this; // invoking object
		double real = a.re + b.re;
		double imag = a.im + b.im;
		return new Complex(real, imag);
	}

	// return a new Complex object whose value is (this - b)
	public Complex minus(Complex b) {
		Complex a = this;
		double real = a.re - b.re;
		double imag = a.im - b.im;
		return new Complex(real, imag);
	}

	// return a new Complex object whose value is (this * b)
	public Complex times(Complex b) {
		Complex a = this;
		double real = a.re * b.re - a.im * b.im;
		double imag = a.re * b.im + a.im * b.re;
		return new Complex(real, imag);
	}

	// return a new object whose value is (this * alpha)
	public Complex scale(double alpha) {
		return new Complex(alpha * re, alpha * im);
	}

	// return a new Complex object whose value is the conjugate of this
	public Complex conjugate() {
		return new Complex(re, -im);
	}

	// return a new Complex object whose value is the reciprocal of this
	public Complex reciprocal() {
		double scale = re * re + im * im;
		return new Complex(re / scale, -im / scale);
	}

	// return the real or imaginary part
	public double re() {
		return re;
	}

	public double im() {
		return im;
	}

	// return a / b
	public Complex divides(Complex b) {
		Complex a = this;
		return a.times(b.reciprocal());
	}

	// return a new Complex object whose value is the complex exponential of this
	public Complex exp() {
		return new Complex(Math.exp(re) * Math.cos(im), Math.exp(re) * Math.sin(im));
	}

	// return a new Complex object whose value is the complex sine of this
	public Complex sin() {
		return new Complex(Math.sin(re) * Math.cosh(im), Math.cos(re) * Math.sinh(im));
	}

	// return a new Complex object whose value is the complex cosine of this
	public Complex cos() {
		return new Complex(Math.cos(re) * Math.cosh(im), -Math.sin(re) * Math.sinh(im));
	}

	public static Complex sqrt(double value) {
		if (value < 0)
			return new Complex(0, Math.sqrt(-value));
		else
			return new Complex(Math.sqrt(value), 0);
	}

	// return a new Complex object whose value is the complex tangent of this
	public Complex tan() {
		return sin().divides(cos());
	}

	// a static version of plus
	public static Complex plus(Complex a, Complex b) {
		double real = a.re + b.re;
		double imag = a.im + b.im;
		Complex sum = new Complex(real, imag);
		return sum;
	}

	// See Section 3.3.
	public boolean equals(Object x) {
		if (x == null)
			return false;
		if (this.getClass() != x.getClass())
			return false;
		Complex that = (Complex) x;
		return (this.re == that.re) && (this.im == that.im);
	}

	// See Section 3.3.
	public int hashCode() {
		return Objects.hash(re, im);
	}

	public boolean isNaN() {
		return Double.isNaN(re) || Double.isNaN(im);
	}
}

class Pair<X, Y> {
	public final X first;
	public final Y second;

	public Pair(X first, Y second) {
		this.first = first;
		this.second = second;
	}
}

public class StrategyExercise {
	@Test
	public void positiveTestOrdinaryStrategy() {
		DiscriminantStrategy strategy = new OrdinaryDiscriminantStrategy();
		QuadraticEquationSolver solver = new QuadraticEquationSolver(strategy);
		Pair<Complex, Complex> results = solver.solve(1, 10, 16);
		assertEquals(new Complex(-2, 0), results.first);
		assertEquals(new Complex(-8, 0), results.second);
	}

	@Test
	public void positiveTestRealStrategy() {
		DiscriminantStrategy strategy = new RealDiscriminantStrategy();
		QuadraticEquationSolver solver = new QuadraticEquationSolver(strategy);
		Pair<Complex, Complex> results = solver.solve(1, 10, 16);
		assertEquals(new Complex(-2, 0), results.first);
		assertEquals(new Complex(-8, 0), results.second);
	}

	@Test
	public void negativeTestOrdinaryStrategy() {
		OrdinaryDiscriminantStrategy strategy = new OrdinaryDiscriminantStrategy();
		QuadraticEquationSolver solver = new QuadraticEquationSolver(strategy);
		Pair<Complex, Complex> results = solver.solve(1, 4, 5);
		assertEquals(new Complex(-2, 1), results.first);
		assertEquals(new Complex(-2, -1), results.second);
	}

	@Test
	public void negativeTestRealStrategy() {
		DiscriminantStrategy strategy = new RealDiscriminantStrategy();
		QuadraticEquationSolver solver = new QuadraticEquationSolver(strategy);
		Pair<Complex, Complex> results = solver.solve(1, 4, 5);

		assertTrue(results.first.isNaN());
		assertTrue(results.second.isNaN());
	}
}
