package br.com.vhb.designpattern.state;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 *
 * <p>
 * A combination lock is a lock that opens after the right digits have been
 * entered. A lock is preprogrammed with a combination (e.g., <code>12345</code>
 * ) and the user is expected to enter this combination to unlock the lock.
 * </p>
 * 
 * <p>
 * The lock has a <code>status</code> field that indicates the state of the
 * lock. The rules are:
 * </p>
 * *
 * <ul>
 * <li>If the lock has just been locked (or at startup), the status is
 * LOCKED.</li>
 * <li>If a digit has been entered, that digit is shown on the screen. As the
 * user enters more digits, they are added to <code>status</code> .</li>
 * <li>If the user has entered the correct sequence of digits, the lock status
 * changes to OPEN.</li>
 * <li>If the user enters an incorrect sequence of digits, the lock status
 * changes to ERROR.</li>
 * </ul>
 * 
 * <p>
 * Please implement the <code>CombinationLock</code> class to enable this
 * behavior. Be sure to test both correct and incorrect inputs.
 * </p>
 * 
 */
public class StateExercise {

	@Test
	public void testExample() {
		CombinationLock c1 = new CombinationLock(new int[] { 1, 2, 3, 4 });
		assertEquals("LOCKED", c1.status);
		c1.enterDigit(1);
		assertEquals("1", c1.status);
		c1.enterDigit(2);
		assertEquals("12", c1.status);
		c1.enterDigit(3);
		assertEquals("123", c1.status);
		c1.enterDigit(4);
		assertEquals("OPEN", c1.status);
	}

	@Test
	public void testSuccess() {
		CombinationLock cl = new CombinationLock(new int[] { 1, 2, 3, 4, 7, 9, 12 });
		assertEquals("LOCKED", cl.status);
		cl.enterDigit(1);
		assertEquals("1", cl.status);
		cl.enterDigit(2);
		assertEquals("12", cl.status);
		cl.enterDigit(3);
		assertEquals("123", cl.status);
		cl.enterDigit(4);
		assertEquals("1234", cl.status);
		cl.enterDigit(7);
		assertEquals("12347", cl.status);
		cl.enterDigit(9);
		assertEquals("123479", cl.status);
		cl.enterDigit(12);
		assertEquals("OPEN", cl.status);
	}

	@Test
	public void testFailure() {
		CombinationLock cl = new CombinationLock(new int[] { 1, 2, 3 });

		assertEquals("LOCKED", cl.status);

		cl.enterDigit(1);
		assertEquals("1", cl.status);

		cl.enterDigit(2);
		assertEquals("12", cl.status);

		cl.enterDigit(5);
		assertEquals("ERROR", cl.status);
	}

}

class CombinationLock {
	private int[] combination;
	private int pos = 0;
	public String status;

	public CombinationLock(int[] combination) {
		this.combination = combination;
		status = "LOCKED";
	}

	public void enterDigit(int digit) {
		if (status == "OPEN")
			return;
		else if (status == "LOCKED")
			status = "";

		if (combination[pos] == digit) {
			pos++;
			if (combination.length == pos)
				status = "OPEN";
			else
				status += digit;
		} else {
			status = "ERROR";
		}
	}
}
