package game;

public class Board {

	public static final int SIZE = 64;

	private Square[] squares;

	public Board() {
		squares = new Square[Board.SIZE];
		// TODO: initialize squares ตาม x,y

//		 for (int i = 0; i < squares.length; i++) {
//		 squares[i] = new Square(i);
//		 }
	}

	public void addPiece(Element piece, int position) {
		squares[position].addPiece(piece);
	}

	public void movePiece(Player piece, int steps) {
		// TODO: สร้างและเรียก method เช็คว่าชนอะไรซักอย่างหรือไม่
		int pos = getPlayerPosition(piece);
		squares[pos].removePiece(piece);
		int next_pos = pos + steps;
		if (next_pos >= SIZE - 1) {
			squares[SIZE - 1].setGoal(true); // set goal ที่ x,y ตำแหน่งสุดท้าย
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
}
