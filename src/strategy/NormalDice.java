package strategy;

import game.Die;

public class NormalDice implements DiceStrategy {

	@Override
	public int roll(Die die) {
		die.roll();
		return die.getFace();
	}

}
