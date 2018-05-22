package game;

import state.BackwardDice;

public class Backward implements Element {

	private int sq;

	public Backward(int sq) {
		this.sq = sq;
	}

	public int getBackwardSquare() {
		return sq;
	}

	@Override
	public int actionCommand(Board board, Player player) {
		player.setState(new BackwardDice());
		return Game.BACKWARD_COMMAND;
	}

}
