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

	public int getStartY() {
		return startY;
	}

	public void setStartXY(int x, int y) {
		this.startX = x;
		this.startY = y;
	}

	public int getDestX() {
		return destX;
	}

	public int getDestY() {
		return destY;
	}

	public void setDestXY(int x, int y) {
		this.destX = x;
		this.destY = y;
	}

	public void setName(String name) {
		this.name = name;
	}

}
