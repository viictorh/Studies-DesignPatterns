package br.com.vhb.designpattern.prototype;

import java.util.Arrays;

// Cloneable is a marker interface
class Address2 implements Cloneable {
	public String streetName;
	public int houseNumber;

	public Address2(String streetName, int houseNumber) {
		this.streetName = streetName;
		this.houseNumber = houseNumber;
	}

	@Override
	public String toString() {
		return "Address{" + "streetName='" + streetName + '\'' + ", houseNumber=" + houseNumber + '}';
	}

	// base class clone() is protected
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new Address2(streetName, houseNumber);
	}
}

class Person implements Cloneable {
	public String[] names;
	public Address2 address;

	public Person(String[] names, Address2 address) {
		this.names = names;
		this.address = address;
	}

	@Override
	public String toString() {
		return "Person{" + "names=" + Arrays.toString(names) + ", address=" + address + '}';
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return new Person(
				// clone() creates a shallow copy!
				/* names */ names.clone(),

				// fixes address but not names
				/* address */ // (Address) address.clone()
				address instanceof Cloneable ? (Address2) address.clone() : address);
	}
}

public class Prototype {
	public static void main(String[] args) throws CloneNotSupportedException {
		Person john = new Person(new String[] { "John", "Smith" }, new Address2("London Road", 123));

		// shallow copy, not good:
		// Person jane = john;

		// jane is the girl next door
		Person jane = (Person) john.clone();
		jane.names[0] = "Jane"; // clone is (originally) shallow copy
		jane.address.houseNumber = 124; // oops, also changed john

		System.out.println(john);
		System.out.println(jane);
	}
}
