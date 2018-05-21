package state;

import game.Die;

public class NormalDice implements DiceState {

	@Override
	public int roll(Die die) {
		die.roll();
		int face = die.getFace();
		return face;
	}

}
