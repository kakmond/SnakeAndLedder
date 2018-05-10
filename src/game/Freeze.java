package game;

public class Freeze implements Element {

	private Square sq;

	public Freeze(Square sq) {
		this.sq = sq;
	}

	public Square getFreezeSquare() {
		return sq;
	}

	@Override
	public int actionCommand() {
		return Game.FREEZE_COMMAND;
	}

}
