package br.com.vhb.designpattern.command;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

class Command_Ex {
	enum Action {
		DEPOSIT, WITHDRAW
	}

	public Action action;
	public int amount;
	public boolean success;

	public Command_Ex(Action action, int amount) {
		this.action = action;
		this.amount = amount;
	}
}

class Account {
	public int balance;

	public void process(Command_Ex c) {
		switch (c.action) {
		case DEPOSIT:
			balance += c.amount;
			c.success = true;
			break;
		case WITHDRAW:
			if (balance > 0 && balance >= c.amount) {
				balance -= c.amount;
				c.success = true;
			}
			break;
		}
	}
}

public class CommandExercise {
	@Test
	public void test() {
		Account a = new Account();

		Command_Ex command = new Command_Ex(Command_Ex.Action.DEPOSIT, 100);
		a.process(command);

		assertEquals(100, a.balance);
		assertTrue(command.success);

		command = new Command_Ex(Command_Ex.Action.WITHDRAW, 50);
		a.process(command);

		assertEquals(50, a.balance);
		assertTrue(command.success);

		command = new Command_Ex(Command_Ex.Action.WITHDRAW, 150);
		a.process(command);

		assertEquals(50, a.balance);
		assertFalse(command.success);
	}
}
