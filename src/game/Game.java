package game;

import java.util.Observable;

public class Game extends Observable {

	private Player[] players;
	private Die die;
	private Board board;
	private boolean ended;
	private int currentPlayerIndex;

	// private boolean waitForInput = true;

	// private Thread gameThread = new Thread() {
	// @Override
	// public void run() {
	// super.run();
	// while (waitForInput) {
	// // singleGameLoop();
	// }
	// }
	// };
	//
	// public void start() {
	// ended = false;
	// gameThread.start();
	// }

	public Game() {
		ended = false;
		die = new Die();
		board = new Board();
		currentPlayerIndex = 0;
	}

	private void gameLogic() {
		if (getCurrentPlayerSquare().isElement())
			board.movePlayerByElement(currentPlayer(), getCurrentPlayerSquare().getElement().desinationPosition());
		switchPlayer();
		super.setChanged();
		super.notifyObservers();
	}

	public void setPlayer(int num) {
		players = new Player[num];
		for (int i = 0; i < num; i++) {
			players[i] = new Player((i + 1) + "");
			board.addPlayer(players[i], 0);
			setChanged();
			notifyObservers();
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
		// Thread moveThread = new Thread() {
		//
		// int i = 0;
		//
		// @Override
		// public void run() {
		// super.run();
		// while (i < steps) {
		// board.movePlayerByStep(currentPlayer(), 1);
		// i++;
		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e) {
		//
		// }
		//
		// }
		// }
		// };
		// moveThread.run();
		board.movePlayerByStep(currentPlayer(), steps);
		this.gameLogic();
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

	public Square getCurrentPlayerSquare() {
		return board.getPlayerSquare(currentPlayer());
	}

	public int getCurrentPlayerPostionX() {
		return board.getPlayerPostionX(currentPlayer());
	}

	public int getCurrentPlayerPostionY() {
		return board.getPlayerPostionY(currentPlayer());
	}
}
