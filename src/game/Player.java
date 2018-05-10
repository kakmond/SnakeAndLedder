package game;

import strategy.DiceStrategy;
import strategy.NormalDice;

public class Player {

	private String name;
	private int index;

	/** for smooth running */
	private int startX;
	private int startY;
	private int destX;
	private int destY;

	private DiceStrategy strategy;

	public Player(String name, int index) {
		this.index = index;
		this.strategy = new NormalDice();
		this.name = name;
	}

	public void setStrategy(DiceStrategy strategy) {
		this.strategy = strategy;
	}

	public int roll(Die die) {
		int val = this.strategy.roll(die);
		this.setStrategy(new NormalDice());
		return val;
	}

	public int getIndex() {
		return this.index;
	}

	public String getName() {
		return this.name;
	}

	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public int getDestX() {
		return destX;
	}

	public void setDestX(int destX) {
		this.destX = destX;
	}

	public int getDestY() {
		return destY;
	}

	public void setDestY(int destY) {
		this.destY = destY;
	}

	public void setName(String name) {
		this.name = name;
	}

}
