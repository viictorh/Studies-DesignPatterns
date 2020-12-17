package br.com.vhb.designpattern.structural.proxy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

class Person {
	private int age;

	public Person(int age) {
		this.age = age;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String drink() {
		return "drinking";
	}

	public String drive() {
		return "driving";
	}

	public String drinkAndDrive() {
		return "driving while drunk";
	}
}

class ResponsiblePerson {
	private Person person;

	public ResponsiblePerson(Person person) {
		this.person = person;
	}

	public int getAge() {
		return person.getAge();
	}

	public void setAge(int age) {
		person.setAge(age);
	}

	public String drink() {
		if (person.getAge() < 18) {
			return "too young";
		}

		return "drinking";
	}

	public String drive() {
		if (person.getAge() < 16) {
			return "too young";
		}
		return "driving";
	}

	public String drinkAndDrive() {
		return "dead";
	}
}

public class ProxyExercise {
	@Test
	public void test() {
		Person p = new Person(10);
		ResponsiblePerson rp = new ResponsiblePerson(p);

		assertEquals("too young", rp.drive());
		assertEquals("too young", rp.drink());
		assertEquals("dead", rp.drinkAndDrive());

		rp.setAge(20);

		assertEquals("driving", rp.drive());
		assertEquals("drinking", rp.drink());
		assertEquals("dead", rp.drinkAndDrive());
	}
}
