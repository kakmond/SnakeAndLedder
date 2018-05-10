package game;

public class Backward implements Element {

	private Square sq;

	public Backward(Square sq) {
		this.sq = sq;
	}

	public Square getBackwardSquare() {
		return sq;
	}

	@Override
	public int actionCommand() {
		return Game.BACKWARD_COMMAND;
	}

}
