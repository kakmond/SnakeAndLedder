package game;

import state.DiceState;
import state.NormalDice;

public class Player {

	private String name;
	private int index;

	/** for smooth running */
	private int startX;
	private int startY;
	private int destX;
	private int destY;

	private DiceState state;

	public Player(String name, int index) {
		this.state = new NormalDice();
		this.index = index;
		this.name = name;
	}

	public void setState(DiceState state) {
		this.state = state;
	}

	public int roll(Die die) {
		int val = this.state.roll(die);
		this.state = new NormalDice();
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
