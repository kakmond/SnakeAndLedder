package game;

public class Game {

	private Player[] players;
	private Die die;
	private Board board;
	private boolean ended;
	private int currentPlayerIndex;

	public Game( int num ) {
		ended = false;
		die = new Die();
		board = new Board();
		currentPlayerIndex = 0;
		setPlayer( num );
	}

	public void setPlayer(int num) {
		players = new Player[num];
		for (int i = 0; i < num; i++) {
			players[i] = new Player((i+1) + "");
			board.addPiece( players[i].getPiece() , 0 );
		}
	}

	public boolean isEnd() {
		return ended;
	}

	public void end() {
		ended = true;
	}

	public Player currentPlayer() {
		return players[currentPlayerIndex];
	}

	public void switchPlayer() {
		currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
	}

	public void currentPlayerMove(int steps) {
		this.board.movePiece(currentPlayer().getPiece(), steps);
	}

	public String currentPlayerName() {
		return currentPlayer().getName();
	}

	public int currentPlayerPosition() {
		return board.getPiecePosition(currentPlayer().getPiece());
	}

	public int currentPlayerRollDice() {
		return currentPlayer().roll(die);
	}

	public boolean currentPlayerWin() {
		return board.pieceIsAtGoal(currentPlayer().getPiece());
	}
}
