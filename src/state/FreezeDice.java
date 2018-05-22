package state;

import game.Die;

public class FreezeDice implements DiceState {

	@Override
	public int roll(Die die) {
		return 0;
	}
}
