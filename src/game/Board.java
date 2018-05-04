package game;

public class Board {

	public static final int SIZE = 10 * 10;

	private Square[] squares;

	public Board() {
		squares = new Square[Board.SIZE];

		// Create Square in right Direction
		int countRight = 1;
		for (int y = 586; y >= 100; y = y - (2 * 60)) {
			for (int x = 283; x <= 825; x = x + 60) {
				squares[countRight - 1] = new Square(countRight - 1, x, y);
				countRight++;
			}
			countRight = countRight + 10;
		}

		// Create Square in left Direction
		int countLeft = 11;
		for (int y = 526; y >= 40; y = y - (2 * 60)) {
			for (int x = 823; x >= 280; x = x - 60) {
				squares[countLeft - 1] = new Square(countLeft - 1, x, y);
				countLeft++;
			}
			countLeft = countLeft + 10;
		}

		// Set and add Snake position
		Snake[] snakes = { new Snake(squares[49], squares[4]), new Snake(squares[42], squares[16]),
				new Snake(squares[55], squares[7]), new Snake(squares[72], squares[15]),
				new Snake(squares[86], squares[49]), new Snake(squares[83], squares[57]),
				new Snake(squares[97], squares[40]) };
		for (Snake s : snakes)
			addElement(s, s.getHead().getNumber());

		// Set and add Ladder position
		Ladder[] ladders = { new Ladder(squares[22], squares[1]), new Ladder(squares[58], squares[19]),
				new Ladder(squares[44], squares[6]), new Ladder(squares[95], squares[56]),
				new Ladder(squares[71], squares[52]), new Ladder(squares[91], squares[70]) };
		for (Ladder l : ladders)
			addElement(l, l.getBottom().getNumber());

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
		if (next_pos >= SIZE - 1) {
			squares[SIZE - 1].setGoal(true);
			addPlayer(piece, SIZE - 1);
		} else
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
