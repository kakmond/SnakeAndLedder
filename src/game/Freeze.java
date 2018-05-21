package game;

import state.FreezeDice;

public class Freeze implements Element {

	private int sq;

	public Freeze(int sq) {
		this.sq = sq;
	}

	public int getFreezeSquare() {
		return sq;
	}

	@Override
	public int actionCommand(Board board, Player player) {
		player.setState(new FreezeDice());
		return Game.FREEZE_COMMAND;
	}

}
