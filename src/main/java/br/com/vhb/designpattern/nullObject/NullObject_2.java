package br.com.vhb.designpattern.nullObject;

import java.lang.reflect.Proxy;

interface ILog {
	void info(String msg);

	void warn(String msg);
}

class BankAccount {
	private ILog log;
	private int balance;

	public BankAccount(ILog log) {
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
				log.info("Withdrew " + amount + ", we have " + balance + " left");
			}
		} else {
			if (log != null) {
				log.warn("Could not withdraw " + amount + " because balance is only " + balance);
			}
		}
	}
}

/**
 * Construção dinamica do pattern nullobject usando proxy.
 * 
 * @author victor
 *
 */
public class NullObject_2 {
	@SuppressWarnings("unchecked")
	public static <T> T noOp(Class<T> itf) {
		return (T) Proxy.newProxyInstance(itf.getClassLoader(), new Class<?>[] { itf }, (proxy, method, args) -> {
			if (method.getReturnType().equals(Void.TYPE))
				return null;
			else
				return method.getReturnType().getConstructor().newInstance();
		});
	}

	public static void main(String[] args) {
		ILog log = noOp(ILog.class);
		BankAccount ba = new BankAccount(log);
		ba.deposit(100);
		ba.withdraw(200);
	}
}
