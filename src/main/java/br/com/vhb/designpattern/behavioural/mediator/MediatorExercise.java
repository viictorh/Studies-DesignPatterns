package br.com.vhb.designpattern.behavioural.mediator;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * <p>
 * Our system has any number of instances of&nbsp;<code>Participant</code>&nbsp;
 * classes. Each Participant has a <code>value</code>&nbsp; integer, initially
 * zero.
 * </p>
 * 
 * <p>
 * A participant can&nbsp;<code>say()</code>&nbsp; a particular value, which is
 * broadcast to all other participants. At this point in time, every other
 * participant is&nbsp;<em>obliged</em>&nbsp;to increase
 * their&nbsp;<code>value</code>&nbsp; by the value being broadcast.
 * </p>
 * 
 * 
 * 
 * <p>
 * Example:
 * </p>
 * 
 * 
 * 
 * <ul>
 * <li>Two participants start with values 0 and 0 respectively</li>
 * <li>Participant 1 broadcasts the value 3. We now have Participant 1 value =
 * 0, Participant 2 value = 3</li>
 * <li>Participant 2 broadcasts the value 2. We now have Participant 1 value =
 * 2, Participant 2 value = 3</li>
 * </ul>
 * 
 *
 */
class Participant {
	private static int counter = 1;
	private int id;
	private int value;
	private Mediator mediator;

	public Participant(Mediator mediator) {
		id = counter++;
		this.mediator = mediator;
		mediator.addParticipant(this);
	}

	public void say(int n) {
		mediator.increase(id, n);
	}

	public void notify(int senderId, int n) {
		if (this.id != senderId) {
			this.value += n;
		}
	}

	public int getValue() {
		return value;
	}
}

class Mediator {
	private List<Participant> participants = new ArrayList<>();

	public void addParticipant(Participant p) {
		participants.add(p);
	}

	public void increase(int id, int n) {
		participants.forEach(p -> p.notify(id, n));
	}

}

public class MediatorExercise {
	@Test
	public void test() {
		Mediator mediator = new Mediator();
		Participant p1 = new Participant(mediator);
		Participant p2 = new Participant(mediator);

		assertEquals(0, p1.getValue());
		assertEquals(0, p2.getValue());

		p1.say(2);

		assertEquals(0, p1.getValue());
		assertEquals(2, p2.getValue());

		p2.say(4);

		assertEquals(4, p1.getValue());
		assertEquals(2, p2.getValue());

		Participant p3 = new Participant(mediator);

		p2.say(3);
		p3.say(1);

		assertEquals(8, p1.getValue());
		assertEquals(3, p2.getValue());
		assertEquals(3, p3.getValue());

	}
}
