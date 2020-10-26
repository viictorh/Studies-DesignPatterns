package br.com.vhb.designpattern.builder;

class PersonFacade {
	// address
	public String streetAddress, postcode, city;

	// employment
	public String companyName, position;
	public int annualIncome;

	@Override
	public String toString() {
		return "Person{" + "streetAddress='" + streetAddress + '\'' + ", postcode='" + postcode + '\'' + ", city='"
				+ city + '\'' + ", companyName='" + companyName + '\'' + ", position='" + position + '\''
				+ ", annualIncome=" + annualIncome + '}';
	}
}

// builder facade
class PersonBuilderFacade {
	// the object we're going to build
	protected PersonFacade person = new PersonFacade(); // reference!

	public PersonJobBuilder works() {
		return new PersonJobBuilder(person);
	}

	public PersonAddressBuilder lives() {
		return new PersonAddressBuilder(person);
	}

	public PersonFacade build() {
		return person;
	}
}

class PersonAddressBuilder extends PersonBuilderFacade {
	public PersonAddressBuilder(PersonFacade person) {
		this.person = person;
	}

	public PersonAddressBuilder at(String streetAddress) {
		person.streetAddress = streetAddress;
		return this;
	}

	public PersonAddressBuilder withPostcode(String postcode) {
		person.postcode = postcode;
		return this;
	}

	public PersonAddressBuilder in(String city) {
		person.city = city;
		return this;
	}
}

class PersonJobBuilder extends PersonBuilderFacade {
	public PersonJobBuilder(PersonFacade person) {
		this.person = person;
	}

	public PersonJobBuilder at(String companyName) {
		person.companyName = companyName;
		return this;
	}

	public PersonJobBuilder asA(String position) {
		person.position = position;
		return this;
	}

	public PersonJobBuilder earning(int annualIncome) {
		person.annualIncome = annualIncome;
		return this;
	}
}

public class BuilderFacade {
	public static void main(String[] args) {
		PersonBuilderFacade pb = new PersonBuilderFacade();
		PersonFacade person = pb
						.lives()
							.at("123 London Road")
							.in("London")
							.withPostcode("SW12BC")
						.works()
							.at("Fabrikam")
							.asA("Engineer")
							.earning(123000)
						.build();
		System.out.println(person);
	}
}