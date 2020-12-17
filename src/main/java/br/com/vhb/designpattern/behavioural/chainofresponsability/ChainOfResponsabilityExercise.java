package br.com.vhb.designpattern.behavioural.chainofresponsability;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

//**********************************
//**********************************
/** PROVAVELMENTE ESSA SOLUÇÃO NÃO ESTÁ CONTEMPLANDO O DESIGN PATTERN CONFORME A CLASSE ChainOfResponsabilityProf.java */ 
// Considero que o exercicio desenvolvido pelo professor ficou muito mais complexo do que deveria ser.
//**********************************
//**********************************


abstract class CreatureEx {
	protected GameEx game;
	protected int baseAttack, baseDefense;

	public CreatureEx(GameEx game, int i, int j) {
		this.game = game;
		baseAttack = i;
		baseDefense = j;
	}

	public abstract int getAttack();

	public abstract int getDefense();
}

class GoblinEx extends CreatureEx {

	public GoblinEx(GameEx game) {
		this(game, 1, 1);
	}

	public GoblinEx(GameEx game, int i, int j) {
		super(game, i, j);
	}

	@Override
	public int getAttack() {
		Predicate<CreatureEx> p1 = (g) -> g instanceof GoblinKing;
		Predicate<CreatureEx> p2 = (g) -> this != g;
		if (!(this instanceof GoblinKing) && game.creatures.stream().anyMatch(g -> p1.and(p2).test(g))) {
			return baseAttack + 1;
		}
		return baseAttack;
	}

	@Override
	public int getDefense() {
		return (int) game.creatures.stream().filter(g -> g instanceof GoblinEx).count() - 1 + baseDefense;
	}
}

class GoblinKing extends GoblinEx {
	public GoblinKing(GameEx game) {
		super(game, 3, 3);
	}

}

class GameEx {
	public List<CreatureEx> creatures = new ArrayList<>();

}

public class ChainOfResponsabilityExercise {
	@Test
	public void manyGoblinsTest() {
		GameEx game = new GameEx();
		GoblinEx goblin = new GoblinEx(game);
		game.creatures.add(goblin);

		assertEquals(1, goblin.getAttack());
		assertEquals(1, goblin.getDefense());

		GoblinEx goblin2 = new GoblinEx(game);
		game.creatures.add(goblin2);

		assertEquals(1, goblin.getAttack());
		assertEquals(2, goblin.getDefense());

		GoblinKing goblin3 = new GoblinKing(game);
		game.creatures.add(goblin3);

		assertEquals(2, goblin.getAttack());
		assertEquals(3, goblin.getDefense());
		
		game.creatures.remove(goblin3);
		assertEquals(2, goblin.getDefense());
		
		
	}
}
