package game;

public class Freeze implements Element {

	private int sq;

	public Freeze(int sq) {
		this.sq = sq;
	}

	public int getFreezeSquare() {
		return sq;
	}

	@Override
	public int actionCommand() {
		return Game.FREEZE_COMMAND;
	}

}
