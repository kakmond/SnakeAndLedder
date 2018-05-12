package game;

public class Board {

	public static final int SIZE = 10 * 10;

	private Square[] squares;

	public Board() {

		squares = new Square[Board.SIZE];

		// Create Square in right Direction
		int countRight = 1;
		for (int y = 563; y >= 0; y = y - (2 * 62)) {
			for (int x = 240; x <= 800; x = x + 62) {
				squares[countRight - 1] = new Square(countRight - 1, x, y);
				countRight++;
			}
			countRight = countRight + 10;
		}

		// Create Square in left Direction
		int countLeft = 11;
		for (int y = 501; y >= 0; y = y - (2 * 62)) {
			for (int x = 798; x >= 220; x = x - 62) {
				squares[countLeft - 1] = new Square(countLeft - 1, x, y);
				countLeft++;
			}
			countLeft = countLeft + 10;
		}

	}

	public void setGoal(int position) {
		this.squares[position].setGoal(true);
	}

	public void addPlayer(Player player, int position) {
		squares[position].addPlayer(player);
	}

	public void addElement(Element piece, int position) {
		squares[position].addElement(piece);
	}

	public void movePlayerToDest(Player piece, int dest) {
		int pos = getPlayerPosition(piece);
		squares[pos].removePiece(piece);
		addPlayer(piece, dest);
	}

	public void movePlayerByStep(Player piece, int steps) {
		int pos = getPlayerPosition(piece);
		squares[pos].removePiece(piece);
		int next_pos = pos + steps;
		if (next_pos >= SIZE - 1)
			addPlayer(piece, SIZE - 1);
		else
			addPlayer(piece, next_pos);
	}

	public int getPlayerPosition(Player piece) {
		for (Square s : squares)
			if (s.hasPlayer(piece))
				return s.getNumber();
		return -1;
	}

	public boolean playerIsAtGoal(Player piece) {
		return squares[getPlayerPosition(piece)].isGoal();
	}

	public Square getPlayerSquare(Player player) {
		return squares[getPlayerPosition(player)];
	}

	public int getPlayerPostionX(Player player) {
		return squares[getPlayerPosition(player)].getX();
	}

	public int getPlayerPostionY(Player player) {
		return squares[getPlayerPosition(player)].getY();
	}

}
