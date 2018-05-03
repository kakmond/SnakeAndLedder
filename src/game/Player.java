package game;

import java.awt.Color;
import java.util.Random;

public class Player {

	private String name;
	private Color color;

	public Player(String name) {
		this.name = name;
		int r = (int)( Math.random() * 256 );
		int g = (int)( Math.random() * 256 );
		int b = (int)( Math.random() * 256 );
		color = new Color(r, g, b);
	}

	public int roll(Die die) {
		die.roll();
		return die.getFace();
	}

	public String getName() {
		return this.name;
	}
	
	public Color getColor() {
		return color;
	}

	// public void movePlayerByStep(Board board, int steps) {
	// board.movePlayerByStep(this, steps);
	// }
}
