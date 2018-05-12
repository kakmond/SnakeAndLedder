package game;

public class Ladder implements Element {

	private int top;
	private int bottom;

	public Ladder(int top, int bottom) {
		this.top = top;
		this.bottom = bottom;
	}

	public int getBottom() {
		return bottom;
	}

	public int getTop() {
		return top;
	}

	@Override
	public int actionCommand() {
		return Game.LADDER_COMMAND;
	}

}
