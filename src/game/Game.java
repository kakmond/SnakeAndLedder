package game;

import java.util.Observable;

public class Game extends Observable {

	private Player[] players;
	private Die die;
	private Board board;
	private boolean ended;
	private int currentPlayerIndex;

	public Game() {
		ended = false;
		die = new Die();
		board = new Board();
		// TODO: สร้าง snake และ ladder และในคลาสนั้นจะมีจุดสิ้นสุด และปลายสุด
		
		currentPlayerIndex = 0;
	}

	public void setPlayer(int num) {
		players = new Player[num];
		for (int i = 0; i < num; i++) {
			players[i] = new Player((i + 1) + "");
			board.addPiece(players[i], 0);
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
		this.board.movePiece(currentPlayer(), steps);
	}

	public String currentPlayerName() {
		return currentPlayer().getName();
	}

	public int currentPlayerPosition() {
		return board.getPlayerPosition(currentPlayer());
	}

	public int currentPlayerRollDice() {
		return currentPlayer().roll(die);
	}

	public boolean currentPlayerWin() {
		return board.playerIsAtGoal(currentPlayer());
	}
}
