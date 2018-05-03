package game;

import java.util.ArrayList;
import java.util.List;

public class Square {

	private List<Element> pieces;
	private int x;
	private int y;
	private int number;
	private boolean goal;

	public Square(int position, int x, int y) {
		this.number = position;
		this.x = x;
		this.y = y;
		pieces = new ArrayList<Element>();
		goal = false;
	}

	public void setGoal(boolean goal) {
		this.goal = goal;
	}

	public void addPiece(Player piece) {
		pieces.add(piece);
	}

	public void removePiece(Player piece) {
		if (pieces.contains(piece))
			System.out.println("CONTAIN!");
		else
			System.out.println("NOOO");
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
