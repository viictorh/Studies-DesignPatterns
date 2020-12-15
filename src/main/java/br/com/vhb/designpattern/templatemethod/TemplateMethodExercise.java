package br.com.vhb.designpattern.templatemethod;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * <p>
 * Imagine a typical collectible card game which has cards representing
 * creatures. Each creature has two values: Attack and Health. Creatures can
 * fight each other, dealing their Attack damage, thereby reducing their
 * opponent's health.
 * </p>
 * 
 * <p>
 * The class CardGame implements the logic for two creatures fighting one
 * another. However, the exact mechanics of how damage is dealt is different:
 * </p>
 * 
 * <ul>
 * <li><code>TemporaryCardDamageGame</code> : In some games (e.g., Magic: the
 * Gathering), unless the creature has been killed, its health returns to the
 * original value at the end of combat.</li>
 * <li><code>PermanentCardDamageGame</code> : In other games (e.g.,
 * Hearthstone), health damage persists.</li>
 * </ul>
 * 
 * <p>
 * You are asked to implement classes <code>TemporaryCardDamageGame</code> and
 * <code>PermanentCardDamageGame</code> that would allow us to simulate combat
 * between creatures.
 * </p>
 * 
 * <p>
 * Some examples:
 * </p>
 * 
 * <ul>
 * <li>With temporary damage, creatures 1/2 and 1/3 can never kill one another.
 * With permanent damage, second creature will win after 2 rounds of
 * combat.</li>
 * <li>With either temporary or permanent damage, two 2/2 creatures kill one
 * another.</li>
 * </ul>
 * 
 * @author victor
 *
 */
class Creature {
	public int attack, health;

	public Creature(int attack, int health) {
		this.attack = attack;
		this.health = health;
	}
}

abstract class CardGame {
	public Creature[] creatures;

	public CardGame(Creature[] creatures) {
		this.creatures = creatures;
	}

	// returns -1 if no clear winner (both alive or both dead)
	public int combat(int creature1, int creature2) {
		Creature first = creatures[creature1];
		Creature second = creatures[creature2];
		hit(first, second);
		hit(second, first);

		boolean firstDead = first.health <= 0;
		boolean secondDead = second.health <= 0;
		if (first.health > 0 && second.health > 0 || firstDead && secondDead) {
			return -1;
		} else if (firstDead) {
			return creature2;
		} else {
			return creature1;
		}

	}

	// attacker hits other creature
	protected abstract void hit(Creature attacker, Creature other);
}

class TemporaryCardDamageGame extends CardGame {

	public TemporaryCardDamageGame(Creature[] creatures) {
		super(creatures);
	}

	@Override
	protected void hit(Creature attacker, Creature other) {
		int health = other.health - attacker.attack;
		if (health <= 0) {
			other.health = 0;
		}
	}
}

class PermanentCardDamageGame extends CardGame {
	public PermanentCardDamageGame(Creature[] creatures) {
		super(creatures);

	}

	@Override
	protected void hit(Creature attacker, Creature other) {
		other.health -= attacker.attack;
	}
}

public class TemplateMethodExercise {
	@Test
	public void impasseTest() {
		Creature c1 = new Creature(1, 2);
		Creature c2 = new Creature(1, 2);
		TemporaryCardDamageGame game = new TemporaryCardDamageGame(new Creature[] { c1, c2 });
		assertEquals(-1, game.combat(0, 1));
		assertEquals(-1, game.combat(0, 1));
	}

	@Test
	public void temporaryMurderTest() {
		Creature c1 = new Creature(1, 1);
		Creature c2 = new Creature(2, 2);
		TemporaryCardDamageGame game = new TemporaryCardDamageGame(new Creature[] { c1, c2 });
		assertEquals(1, game.combat(0, 1));
	}

	@Test
	public void doubleMurderTest() {
		Creature c1 = new Creature(2, 2);
		Creature c2 = new Creature(2, 2);
		TemporaryCardDamageGame game = new TemporaryCardDamageGame(new Creature[] { c1, c2 });
		assertEquals(-1, game.combat(0, 1));
	}

	@Test
	public void permanentDamageDeathTest() {
		Creature c1 = new Creature(1, 2);
		Creature c2 = new Creature(1, 3);
		CardGame game = new PermanentCardDamageGame(new Creature[] { c1, c2 });
		assertEquals(-1, game.combat(0, 1));
		assertEquals(1, game.combat(0, 1));
	}
}
