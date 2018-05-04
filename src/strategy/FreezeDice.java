package strategy;

import game.Die;

public class FreezeDice implements DiceStrategy {

	@Override
	public int roll(Die die) {
		return 0;
	}

}
