package br.com.vhb.designpattern.behavioural.memento;

class MementoAccount {
	public int balance;

	public MementoAccount(int balance) {
		this.balance = balance;
	}
}

class BankAccount {
	private int balance;

	public BankAccount(int balance) {
		this.balance = balance;
	}

	public MementoAccount deposit(int amount) {
		balance += amount;
		return new MementoAccount(balance);
	}

	public void restore(MementoAccount m) {
		balance = m.balance;
	}

	@Override
	public String toString() {
		return "BankAccount{" + "balance=" + balance + '}';
	}
}

public class Memento_1 {
	public static void main(String[] args) {
		BankAccount ba = new BankAccount(100);
		MementoAccount m1 = ba.deposit(50); // 150
		MementoAccount m2 = ba.deposit(25); // 175
		System.out.println(ba);

		// restore to m1
		ba.restore(m1);
		System.out.println(ba);

		// restore to m2
		ba.restore(m2);
		System.out.println(ba);
	}
}
