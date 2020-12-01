package br.com.vhb.designpattern.command;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

interface CommandBetter {
	boolean execute();
}

class DepositeCommand implements CommandBetter {

	private Account_2 account;
	private int amount;

	public DepositeCommand(Account_2 account, int amount) {
		this.account = account;
		this.amount = amount;
	}

	@Override
	public boolean execute() {
		account.balance += amount;
		return true;
	}
}

class WithdrawCommand implements CommandBetter {

	private Account_2 account;
	private int amount;

	public WithdrawCommand(Account_2 account, int amount) {
		this.account = account;
		this.amount = amount;
	}

	@Override
	public boolean execute() {
		if (account.balance > 0 && account.balance > amount) {
			account.balance -= amount;
			return true;
		}
		return false;
	}
}

class Account_2 {
	public int balance;
}

public class CommandExerciseBetter {

	@Test
	public void test() {
		Account_2 a = new Account_2();

		CommandBetter command = new DepositeCommand(a, 100);
		assertTrue(command.execute());
		assertEquals(100, a.balance);

		command = new WithdrawCommand(a, 50);
		assertTrue(command.execute());
		assertEquals(50, a.balance);

		command = new WithdrawCommand(a, 150);
		assertFalse(command.execute());
		assertEquals(50, a.balance);
	}
}
