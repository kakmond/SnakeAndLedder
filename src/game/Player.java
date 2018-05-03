package game;

public class Player {

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

}
