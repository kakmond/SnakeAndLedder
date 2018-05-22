package state;

import game.Die;

public class BackwardDice implements DiceState {

	@Override
	public int roll(Die die) {
		die.roll();
		int face = die.getFace() * -1;
		return face;
	}

}
