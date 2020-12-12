package br.com.vhb.designpattern.nullObject;

import org.junit.Test;

/**
 * <p>
 * In this example, we have a class <code>Account</code> that is very tightly
 * coupled to a <code>Log</code> ... it also breaks SRP by performing all sorts
 * of weird checks in <code>someOperation()</code> .
 * </p>
 * 
 * <p>
 * Your mission, should you choose to accept it, is to implement a
 * <code>NullLog</code> class that can be fed into an <code>Account</code> and
 * that doesn't throw any exceptions.
 * </p>
 * 
 * @author victor
 *
 */
interface Log {
	// max # of elements in the log
	int getRecordLimit();

	// number of elements already in the log
	int getRecordCount();

	// expected to increment record count
	void logInfo(String message);
}

class Account {
	private Log log;

	public Account(Log log) {
		this.log = log;
	}

	public void someOperation() throws Exception {
		int c = log.getRecordCount();
		log.logInfo("Performing an operation");
		if (c + 1 != log.getRecordCount())
			throw new Exception();
		if (log.getRecordCount() >= log.getRecordLimit())
			throw new Exception();
	}
}

class NullLog implements Log {
	private int count = 0;

	@Override
	public int getRecordLimit() {
		return count + 1;
	}

	@Override
	public int getRecordCount() {
		return count;
	}

	@Override
	public void logInfo(String message) {
		count++;
	}
}

public class NullObjectExercise {

	@Test
	public void singleCallTest() throws Exception {
		Account a = new Account(new NullLog());
		a.someOperation();
	}

	@Test
	public void manyCallsTest() throws Exception {
		Account a = new Account(new NullLog());
		for (int i = 0; i < 100; ++i)
			a.someOperation();
	}
}
