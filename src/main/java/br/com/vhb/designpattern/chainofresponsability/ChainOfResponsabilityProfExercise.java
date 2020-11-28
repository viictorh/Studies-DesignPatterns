package br.com.vhb.designpattern.chainofresponsability;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

abstract class CreatureProf {
	protected GameProf game;
	protected int baseAttack, baseDefense;

	public CreatureProf(GameProf game, int baseAttack, int baseDefense) {
		this.game = game;
		this.baseAttack = baseAttack;
		this.baseDefense = baseDefense;
	}

	public abstract int getAttack();

	public abstract int getDefense();

	public abstract void query(Object source, StatQuery sq);
}

class GoblinProf extends CreatureProf {
	protected GoblinProf(GameProf game, int baseAttack, int baseDefense) {
		super(game, baseAttack, baseDefense);
	}

	public GoblinProf(GameProf game) {
		super(game, 1, 1);
	}

	@Override
	public int getAttack() {
		StatQuery q = new StatQuery(Statistic.ATTACK);
		for (CreatureProf c : game.creatures)
			c.query(this, q);
		return q.result;
	}

	@Override
	public int getDefense() {
		StatQuery q = new StatQuery(Statistic.DEFENSE);
		for (CreatureProf c : game.creatures)
			c.query(this, q);
		return q.result;
	}

	@Override
	public void query(Object source, StatQuery sq) {
		if (source == this) {
			switch (sq.statistic) {
			case ATTACK:
				sq.result += baseAttack;
				break;
			case DEFENSE:
				sq.result += baseDefense;
				break;
			}
		} else if (sq.statistic == Statistic.DEFENSE) {
			sq.result++;
		}
	}
}

class GoblinKingProf extends GoblinProf {
	public GoblinKingProf(GameProf game) {
		super(game, 3, 3);
	}

	@Override
	public void query(Object source, StatQuery sq) {
		if (source != this && sq.statistic == Statistic.ATTACK) {
			sq.result++; // every goblin gets +1 attack
		} else
			super.query(source, sq);
	}
}

enum Statistic {
	ATTACK, DEFENSE
}

class StatQuery {
	public Statistic statistic;
	public int result;

	public StatQuery(Statistic statistic) {
		this.statistic = statistic;
	}
}

class GameProf {
	public List<CreatureProf> creatures = new ArrayList<>();
}

public class ChainOfResponsabilityProfExercise {
	@Test
	public void manyGoblinsTest() {
		GameProf game = new GameProf();
		GoblinProf goblin = new GoblinProf(game);
		game.creatures.add(goblin);

		assertEquals(1, goblin.getAttack());
		assertEquals(1, goblin.getDefense());

		GoblinProf goblin2 = new GoblinProf(game);
		game.creatures.add(goblin2);

		assertEquals(1, goblin.getAttack());
		assertEquals(2, goblin.getDefense());

		GoblinKingProf goblin3 = new GoblinKingProf(game);
		game.creatures.add(goblin3);

		assertEquals(2, goblin.getAttack());
		assertEquals(3, goblin.getDefense());

		game.creatures.remove(goblin3);
		assertEquals(2, goblin.getDefense());

	}
}
