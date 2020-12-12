package br.com.vhb.designpattern.nullObject;

interface LogI {
	void info(String msg);

	void warn(String msg);
}

class ConsoleLogImpl implements LogI {

	@Override
	public void info(String msg) {
		System.out.println(msg);
	}

	@Override
	public void warn(String msg) {
		System.out.println("WARNING: " + msg);
	}
}

class BankAccountObj {
	private LogI log;
	private int balance;

	public BankAccountObj(LogI log) {
		this.log = log;
	}

	public void deposit(int amount) {
		balance += amount;

		// check for null everywhere?
		if (log != null) {
			log.info("Deposited " + amount + ", balance is now " + balance);
		}
	}

	public void withdraw(int amount) {
		if (balance >= amount) {
			balance -= amount;
			if (log != null) {
				System.out.println("Withdrew " + amount + ", we have " + balance + " left");
			}
		} else {
			if (log != null) {
				System.out.println("Could not withdraw " + amount + " because balance is only " + balance);
			}
		}
	}
}

final class NullLogImpl implements LogI {

	@Override
	public void info(String msg) {
	}

	@Override
	public void warn(String msg) {
	}
}

public class NullObject_1 {
	public static void main(String[] args) {
		NullLogImpl log = new NullLogImpl();

		BankAccountObj ba = new BankAccountObj(log);
		ba.deposit(100);
		ba.withdraw(200);
	}
}
