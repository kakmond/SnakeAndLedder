package game;

public class Player implements Element {

	private String name;

	public Player(String name) {
		this.name = name;
	}

	public int roll(Die die) {
		die.roll();
		return die.getFace();
	}

	public String getName() {
		return this.name;
	}

	public void movePiece(Board board, int steps) {
		board.movePiece(this, steps);
	}

	@Override
	public void performAction() {
		// Do nothing.
	}
}
