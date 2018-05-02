package game;

public class Board {

	public static final int SIZE = 64;

	private Square[] squares;

	public Board() {
		squares = new Square[Board.SIZE];
		for (int i = 0; i < squares.length; i++) {
			squares[i] = new Square(i);
		}
	}

	public void addPiece(Piece piece, int position) {
		squares[position].addPiece(piece);
	}

	public void movePiece(Piece piece, int steps) {
		int pos = getPiecePosition(piece);
		squares[pos].removePiece(piece);
		int next_pos = pos + steps;
		addPiece(piece, next_pos);
		if (next_pos == SIZE - 1) {
			squares[next_pos].setGoal(true);
		}
	}

	public int getPiecePosition(Piece piece) {
		for (Square s : squares) {
			if (s.hasPiece(piece))
				return s.getNumber();
		}
		return -1;
	}

	public boolean pieceIsAtGoal(Piece piece) {
		return squares[getPiecePosition(piece)].isGoal();
	}
}
