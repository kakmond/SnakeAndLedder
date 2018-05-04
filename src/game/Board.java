package game;

public class Board {

	public static final int SIZE = 10 * 10;

	private Square[] squares;

	public Board() {
		squares = new Square[Board.SIZE];

		// Create Square in right Direction
		int countRight = 1;
		for (int y = 605; y >= 100; y = y - (2 * 63)) {
			for (int x = 268; x <= 830; x = x + 62) {
				squares[countRight - 1] = new Square(countRight - 1, x, y);
				countRight++;
			}
			countRight = countRight + 10;
		}

		// Create Square in left Direction
		int countLeft = 11;
		for (int y = 542; y >= 35; y = y - (2 * 62)) {
			for (int x = 826; x >= 268; x = x - 62) {
				squares[countLeft - 1] = new Square(countLeft - 1, x, y);
				countLeft++;
			}
			countLeft = countLeft + 10;
		}

		// Set and add Snake position
		Snake[] snakes = { new Snake(squares[49], squares[4]), 
				new Snake(squares[42], squares[16]),
				new Snake(squares[55], squares[7]), 
				new Snake(squares[72], squares[14]),
				new Snake(squares[86], squares[48]), 
				new Snake(squares[83], squares[57]),
				new Snake(squares[97], squares[39]) };
		for (Snake s : snakes)
			addElement(s, s.getHead().getNumber());

		// Set and add Ladder position
		Ladder[] ladders = { new Ladder(squares[22], squares[1]), 
				new Ladder(squares[58], squares[19]),
				new Ladder(squares[44], squares[5]), 
				new Ladder(squares[95], squares[56]),
				new Ladder(squares[71], squares[51]), 
				new Ladder(squares[91], squares[70]) };
		for (Ladder l : ladders)
			addElement(l, l.getBottom().getNumber());

		// Set Freeze position
		Freeze[] freezes = {
				new Freeze( squares[6] ),
				new Freeze( squares[37] ),
				new Freeze( squares[67] ),
				new Freeze( squares[92] )
		};
		
		// Set Backward position
		Backward[] backwards = {
				new Backward( squares[30] ),
				new Backward( squares[57] ),
				new Backward( squares[64] )
		};
		
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
