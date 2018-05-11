package game;

public class Backward implements Element {

	private int sq;

	public Backward(int sq) {
		this.sq = sq;
	}

	public int getBackwardSquare() {
		return sq;
	}

	@Override
	public int actionCommand() {
		return Game.BACKWARD_COMMAND;
	}

}
