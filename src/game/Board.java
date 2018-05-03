package game;

import java.util.Arrays;

public class Board {

	public static final int SIZE = 10 * 10;

	private Square[] squares;

	public Board() {
		squares = new Square[Board.SIZE];

		// Create Square in right Direction
		int countRight = 1;
		for (int y = 630; y >= 77; y = y - (2 * 59)) {
			for (int x = 328; x <= 893; x = x + 58) {
				squares[countRight - 1] = new Square(countRight, x, y);
				countRight++;
			}
			countRight = countRight + 10;
		}

		// Create Square in left Direction
		int countLeft = 11;
		for (int y = 558; y >= 77; y = y - (2 * 59)) {
			for (int x = 850; x >= 303; x = x - 58) {
				squares[countLeft - 1] = new Square(countLeft, x, y);
				countLeft++;
			}
			countLeft = countLeft + 10;
		}

	}

	public void addPiece(Player piece, int position) {
		squares[position].addPiece(piece);
	}

	public void movePiece(Player piece, int steps) {
		int pos = getPlayerPosition(piece);
		squares[pos].removePiece(piece);
		int next_pos = pos + steps;
		if (next_pos >= SIZE - 1) {
			squares[SIZE - 1].setGoal(true);
			addPiece(piece, SIZE - 1);
		} else
			addPiece(piece, next_pos);
	}

	public int getPlayerPosition(Player piece) {
		for (Square s : squares) {
			if (s.hasPlayer(piece))
				return s.getNumber();
		}
		return -1;
	}

	public boolean playerIsAtGoal(Player piece) {
		return squares[getPlayerPosition(piece)].isGoal();
	}

	public int getPlayerPostionX(Player player) {
		return squares[getPlayerPosition(player)].getX();
	}

	public int getPlayerPostionY(Player player) {
		return squares[getPlayerPosition(player)].getY();
	}

}
