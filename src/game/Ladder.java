package game;

public class Ladder implements Element {

	private Square top;
	private Square bottom;

	public Ladder(Square top, Square bottom) {
		this.top = top;
		this.bottom = bottom;
	}

	public Square getBottom() {
		return bottom;
	}

	public Square getTop() {
		return top;
	}

	@Override
	public int getCommandID() {
		return Game.LADDER_COMMAND;
	}

}
