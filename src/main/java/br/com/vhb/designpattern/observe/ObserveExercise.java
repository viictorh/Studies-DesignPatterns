package br.com.vhb.designpattern.observe;

import static org.junit.Assert.assertEquals;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.junit.Test;

/**
 * <p>
 * Imagine a game where one or more rats can attack a player. Each individual
 * rat has an <code>attack</code> value of 1. However, rats attack as a swarm,
 * so each rat's <code>attack</code> value is equal to the total number of rats
 * in play.
 * </p>
 * 
 * <p>
 * Given that a rat enters play through the constructor and leaves play (dies)
 * via its <code>close()</code> method, please implement the Game and Rat
 * classes so that, at any point in the game, the <code>attack</code> value of a
 * rat is always consistent.
 * </p>
 * 
 * @author victor
 *
 */

class Event_Ex<T> {
	private Map<Integer, Consumer<T>> observers = new HashMap<>();

	public void subscribe(int id, Consumer<T> consumer) {
		observers.put(id, consumer);
	}

	public void unsubscribe(int id) {
		observers.remove(id);
	}

	public void notifySubs(T t) {
		for (Consumer<T> c : observers.values()) {
			c.accept(t);
		}
	}
}

class Game_Ex {
	public static int monsterCount = 0;
	public final Event_Ex<IObservable> notifier = new Event_Ex<>();

	public int nextMonster() {
		return monsterCount++;
	}
}

interface IObservable {
}

class Rat_Ex implements Closeable, IObservable {
	public int attack = 1;
	private int id;
	private Game_Ex game;
	private boolean live = true;

	public Rat_Ex(Game_Ex game) {
		this.game = game;
		id = game.nextMonster();
		game.notifier.subscribe(id, (r) -> evaluate(r));
		game.notifier.notifySubs(this);
	}

	private void evaluate(IObservable r) {
		if (r instanceof Rat_Ex && r != this) {
			Rat_Ex rat = (Rat_Ex) r;
			if (rat.live) {
				this.attack++;
				rat.attack++;
			} else {
				this.attack--;
			}
		}
	}

	@Override
	public void close() {
		live = false;
		game.notifier.unsubscribe(id);
		game.notifier.notifySubs(this);
	}
}

public class ObserveExercise {
	@Test
	public void singleRatTest() {
		Game_Ex game = new Game_Ex();
		Rat_Ex rat = new Rat_Ex(game);
		assertEquals(1, rat.attack);
	}

	@Test
	public void twoRatTest() {
		Game_Ex game = new Game_Ex();
		Rat_Ex rat = new Rat_Ex(game);
		Rat_Ex rat2 = new Rat_Ex(game);
		assertEquals(2, rat.attack);
		assertEquals(2, rat2.attack);
	}

	@Test
	public void threeRatsOneDies() throws IOException {
		Game_Ex game = new Game_Ex();

		Rat_Ex rat = new Rat_Ex(game);
		assertEquals(1, rat.attack);

		Rat_Ex rat2 = new Rat_Ex(game);
		assertEquals(2, rat.attack);
		assertEquals(2, rat2.attack);

		try (Rat_Ex rat3 = new Rat_Ex(game)) {
			assertEquals(3, rat.attack);
			assertEquals(3, rat2.attack);
			assertEquals(3, rat3.attack);
		}

		assertEquals(2, rat.attack);
		assertEquals(2, rat2.attack);
	}
}
