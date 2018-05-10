package game;

import java.util.Observable;

import gameUI.SnakeGUI;
import replay.ReplayManager;
import strategy.BackwardDice;
import strategy.FreezeDice;

public class Game extends Observable {

	private Player[] players;
	private Die die;
	private Board board;
	private boolean ended;
	private int currentPlayerIndex;
	private int currentPlayerDiceValue;
	private ReplayManager replay;

	public static final int NO_COMMAND = 0;
	public static final int SNAKE_COMMAND = 1;
	public static final int LADDER_COMMAND = 2;
	public static final int BACKWARD_COMMAND = 3;
	public static final int FREEZE_COMMAND = 4;

	private Thread gameThread = new Thread() {
		@Override
		public void run() {
			while (true) {
				try {
					synchronized (gameThread) {
						wait();
					}
					if (currentPlayerDiceValue != 0) {
						System.out.println( "Value = " + currentPlayerDiceValue );
						currentPlayerMoveByStep(currentPlayerDiceValue);
						gameLogic();
					}
					if (currentPlayerWin()) {
						end();
					} else
						switchPlayer();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}
	};

	public Game() {
		replay = new ReplayManager();
		ended = false;
		die = new Die();
		board = new Board();
		currentPlayerIndex = 0;
		this.gameThread.start();
	}

	private void gameLogic() {
		int commandID = NO_COMMAND;
		Square currentPlayerSquare = getCurrentPlayerSquare();
		if (currentPlayerSquare.isElement()) {
			Element element = currentPlayerSquare.getElement();
			commandID = element.getCommandID();
			if (commandID == SNAKE_COMMAND) {
				Snake snake = (Snake) element;
				board.movePlayerToDest(currentPlayer(), snake.getTail().getNumber());
			} else if (commandID == LADDER_COMMAND) {
				Ladder ladder = (Ladder) element;
				 board.movePlayerToDest(currentPlayer(),
				 ladder.getTop().getNumber());
				// board.movePlayerToDest(currentPlayer(), 98);
			} else if (commandID == BACKWARD_COMMAND) {
				currentPlayer().setStrategy(new BackwardDice());
			} else if (commandID == FREEZE_COMMAND) {
				currentPlayer().setStrategy(new FreezeDice());
			}
		}
		super.setChanged();
		super.notifyObservers(commandID);

	}

	public void setPlayer(int num) {
		replay = new ReplayManager();
		if (isEnd())
			ended = false;
		players = new Player[num];
		for (int i = 0; i < num; i++) {
			String nameTemp = (i + 1) + "";
			players[i] = new Player(nameTemp, i);
			board.addPlayer(players[i], 0);
		}
	}

	public void setNamePlayer(int num, String name) {
		players[num].setName(name);
	}

	public boolean isEnd() {
		return ended;
	}

	public void end() {
		ended = true;
		setChanged();
		notifyObservers();
	}

	public Player currentPlayer() {
		return players[currentPlayerIndex];
	}

	public void switchPlayer() {
		currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
	}

	public void currentPlayerMoveByStep(int steps) throws InterruptedException {
		
		// Walk through the goal
		if( currentPlayerPosition() + steps > ( board.SIZE - 1 ) ) {
			int walkForwardToGoal = ( board.SIZE - 1 ) - currentPlayerPosition();
			int walkBackFromGoal = steps - walkForwardToGoal;
			int finalPosition =  ( board.SIZE - 1 ) - walkBackFromGoal;
			if( finalPosition > currentPlayerPosition() ) {
				steps = finalPosition - currentPlayerPosition();
			}
			else {
				steps = (-1) * ( currentPlayerPosition() - finalPosition );
			}
		}
		
		for (int i = 0; i < Math.abs(steps); i++) {
			currentPlayer().setStartX(getCurrentPlayerPostionX());
			currentPlayer().setStartY(getCurrentPlayerPostionY());
			if (steps > 0) // move forward
				board.movePlayerByStep(currentPlayer(), 1);
			else // move backward
				board.movePlayerByStep(currentPlayer(), -1);
			currentPlayer().setDestX(getCurrentPlayerPostionX());
			currentPlayer().setDestY(getCurrentPlayerPostionY());
			setChanged();
			notifyObservers();
			Thread.sleep(200);
		}
		currentPlayer().setStartX(getCurrentPlayerPostionX());
		currentPlayer().setStartY(getCurrentPlayerPostionY());
	}

	public String currentPlayerName() {
		return currentPlayer().getName();
	}

	public int getPlayerPostion(Player p) {
		return board.getPlayerPosition(p);
	}

	public int currentPlayerPosition() {
		return board.getPlayerPosition(currentPlayer());
	}

	public int currentPlayerRollDice() {
		return this.currentPlayerDiceValue = currentPlayer().roll(die);
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

	public int getPlayerPostionX(Player p) {
		return board.getPlayerPostionX(p);
	}

	public int getCurrentPlayerPostionY() {
		return board.getPlayerPostionY(currentPlayer());
	}

	public int getPlayerPostionY(Player p) {
		return board.getPlayerPostionY(p);
	}

	public Player[] getPlayers() {
		return players;
	}

	public void currentPlayerMove(int face) {
		this.currentPlayerDiceValue = face;
		synchronized (this.gameThread) {
			this.gameThread.notify();
		}
	}

	public void setPlayersAtStart() {
		for (int i = 0; i < players.length; i++) {
			board.movePlayerToDest(players[i], 0);
			players[i].setStartX(board.getPlayerPostionX(players[i]));
			players[i].setStartY(board.getPlayerPostionY(players[i]));
		}
		setChanged();
		notifyObservers();
	}

	public void replay() {
		/** Set players to start point */
		for (Player p : players) {
			board.movePlayerToDest(p, 0);
			p.setDestX(getCurrentPlayerPostionY());
			p.setDestY(getCurrentPlayerPostionY());
			p.setStartX(getCurrentPlayerPostionX());
			p.setStartY(getCurrentPlayerPostionY());
		}

	}

	// public void start() {
	// replay = new ReplayManager();
	// }
}