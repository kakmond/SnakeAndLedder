package game;

public class Board {

	public static final int SIZE = 10 * 10;

	private Square[] squares;

	public Board() {
		squares = new Square[Board.SIZE];
		// TODO: initialize squares x,y and position
		//
		// 329,630 388,630 ,447,630
		//
		// int startX = 329;
		// int startY = 630;
		// for (int i = 0; i < squares.length; i++) {
		// if(i%10==0){
		// squares[i] = new Square(i, startX, startY);
		// startX = startX + 59;
		// }
		// }
		
		// Create Square in right Direction
		int countRight = 1;
		for( int y = 630 ; y >= 77 ; y = y - ( 2 * 59 ) ) {
			for( int x = 328 ; x <= 893 ; x = x + 58 ) {
					squares[countRight-1] = new Square(countRight, x, y);
					countRight++;
			}
			countRight = countRight + 10;
		}
		
		// Create Square in left Direction
		int countLeft = 11;
		for( int y = 558 ; y >= 77 ; y = y - ( 2 * 59 ) ) {
			for( int x = 850 ; x >= 303 ; x = x - 58 ) {
					squares[countLeft-1] = new Square(countLeft, x, y);
					countLeft++;
			}
			countLeft = countLeft + 10;
		}
		
		// Set Snake position
		Snake s50 = new Snake( squares[49] , squares[4] );
		Snake s43 = new Snake( squares[42] , squares[16] );
		Snake s56 = new Snake( squares[55] , squares[7] );
		Snake s73 = new Snake( squares[72] , squares[15] );
		Snake s87 = new Snake( squares[86] , squares[49] );
		Snake s84 = new Snake( squares[83] , squares[57] );
		Snake s98 = new Snake( squares[97] , squares[40] );
		
		// Set Ladder position
		Ladder l23 = new Ladder( squares[22] , squares[1] );
		Ladder l59 = new Ladder( squares[58] , squares[19] );
		Ladder l45 = new Ladder( squares[44] , squares[6] );
		Ladder l96 = new Ladder( squares[95] , squares[56] );
		Ladder l72 = new Ladder( squares[71] , squares[52] );
		Ladder l92 = new Ladder( squares[91] , squares[70] );
	}

	public void addPiece(Element piece, int position) {
		squares[position].addPiece(piece);
	}

	public void movePiece(Player piece, int steps) {
		// TODO: ���ҧ������¡ method ����Ҫ����ëѡ���ҧ�������
		int pos = getPlayerPosition(piece);
		squares[pos].removePiece(piece);
		int next_pos = pos + steps;
		if (next_pos >= SIZE - 1) {
			squares[SIZE - 1].setGoal(true); // set goal ��� x,y ���˹��ش����
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
