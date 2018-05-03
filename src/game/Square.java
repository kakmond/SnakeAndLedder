package game;

import java.util.ArrayList;
import java.util.List;

public class Square {

	private List<Player> pieces;
	private int x;
	private int y;
	private int number;
	private Element element;
	private boolean isElement;
	private boolean goal;

	public Square(int position, int x, int y) {
		this.number = position;
		this.x = x;
		this.y = y;
		pieces = new ArrayList<Player>();
		goal = false;
		isElement = false;
	}

	public void setGoal(boolean goal) {
		this.goal = goal;
	}

	public void addElement(Element e) {
		this.element = e;
		this.isElement = true;
	}

	public boolean isElement() {
		return isElement;
	}

	public Element getElement() {
		return this.element;
	}

	public void addPlayer(Player piece) {
		pieces.add(piece);
	}

	public void removePiece(Player piece) {
		pieces.remove(piece);
	}

	public boolean hasPlayer(Player piece) {
		return pieces.contains(piece);
	}

	public boolean isGoal() {
		return goal;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getNumber() {
		return this.number;
	}
}
