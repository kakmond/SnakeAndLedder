package strategy;

import game.Die;

public class BackwardDice implements DiceStrategy {

	@Override
	public int roll(Die die) {
		die.roll();
		return die.getFace() * -1;
	}

}
