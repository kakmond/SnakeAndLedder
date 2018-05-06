package game;

import java.util.Observable;

import replay.Memento;
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

	private boolean isReplay;
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
				int diceValue = 0;
				try {
					if (!isReplay || isEnd()) {
						synchronized (gameThread) {
							wait(); // wait for user roll dice.
						}
						diceValue = currentPlayerDiceValue;
					} else if (replay.hasNext()) // load dice from replay
						diceValue = getDiceValueFromMemento(replay.next());

					if (diceValue != 0) {
						currentPlayerMoveByStep(diceValue);
						gameLogic();
					}
					if (currentPlayerWin())
						end();
					else
						switchPlayer();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}

			}
		}
	};

	public Game() {
		replay = new ReplayManager();
		isReplay = false;
		ended = false;
		die = new Die();
		board = new Board();
		currentPlayerIndex = 0;
		gameThread.start();
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
				// board.movePlayerToDest(currentPlayer(),
				// ladder.getTop().getNumber());
				board.movePlayerToDest(currentPlayer(), 98);
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
		isReplay = false;
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
			Thread.sleep(500);
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
		if (!isReplay) {
			System.out.println("ADD");
			this.replay.addReplay(saveStateToMemento());
		}

	}

	public boolean isReplay() {
		return this.isReplay;
	}

	public void replay() {
		this.isReplay = true;
		this.ended = false;
		replay.resetIndex();
		/** Set players to start point */
		for (Player p : players) {
			board.movePlayerToDest(p, 0);
			p.setDestX(getCurrentPlayerPostionX());
			p.setDestY(getCurrentPlayerPostionY());
			p.setStartX(getCurrentPlayerPostionX());
			p.setStartY(getCurrentPlayerPostionY());
		}
		currentPlayerIndex = 0;
		setChanged();
		notifyObservers();/** Load dice histories from ReplayManager */
		synchronized (this.gameThread) {
			this.gameThread.notify();
		}
	}

	public Memento saveStateToMemento() {
		return new Memento(currentPlayerDiceValue);
	}

	public int getDiceValueFromMemento(Memento memento) {
		return memento.getDiceValue();
	}
}