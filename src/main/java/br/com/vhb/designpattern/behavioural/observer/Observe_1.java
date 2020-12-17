package br.com.vhb.designpattern.behavioural.observer;

import java.util.ArrayList;
import java.util.List;

class PropertyChangedEventArgsObs<T> {
	public T source;
	public String propertyName;
	public Object newValue;

	public PropertyChangedEventArgsObs(T source, String propertyName, Object newValue) {
		this.source = source;
		this.propertyName = propertyName;
		this.newValue = newValue;
	}
}

// observes objects of type T
interface Observer<T> {
	void handle(PropertyChangedEventArgsObs<T> args);
}

// can be observed
class Observable<T> {
	private List<Observer<T>> observers = new ArrayList<>();

	public void subscribe(Observer<T> observer) {
		observers.add(observer);
	}

	protected void propertyChanged(T source, String propertyName, Object newValue) {
		for (Observer<T> o : observers)
			o.handle(new PropertyChangedEventArgsObs<T>(source, propertyName, newValue));
	}
}

class PersonObs extends Observable<PersonObs> {
	private int age;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		if (this.age == age)
			return;
		this.age = age;
		propertyChanged(this, "age", age);
	}
}

public class Observe_1 implements Observer<PersonObs> {
	public Observe_1() {
		PersonObs person = new PersonObs();
		person.subscribe(this);
		for (int i = 20; i < 24; ++i)
			person.setAge(i);
	}

	public static void main(String[] args) {
		new Observe_1();
	}

	@Override
	public void handle(PropertyChangedEventArgsObs<PersonObs> args) {
		System.out.println("Person's " + args.propertyName + " has been changed to " + args.newValue);
	}
}
