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
					if (!isReplay || ended) {
						synchronized (gameThread) {
							wait(); // wait for user roll dice or any action.
						}
						if (!isReplay) // get dice value from user
							diceValue = currentPlayerDiceValue;
						else
							currentPlayerIndex = 0;
					}
					if (isReplay) // load dice value from replay
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
			commandID = element.actionCommand();
			if (commandID == SNAKE_COMMAND) {
				Snake snake = (Snake) element;
				board.movePlayerToDest(currentPlayer(), snake.getTail().getNumber());
			} else if (commandID == LADDER_COMMAND) {
				Ladder ladder = (Ladder) element;
				board.movePlayerToDest(currentPlayer(), ladder.getTop().getNumber());
			} else if (commandID == BACKWARD_COMMAND)
				currentPlayer().setStrategy(new BackwardDice());
			else if (commandID == FREEZE_COMMAND)
				currentPlayer().setStrategy(new FreezeDice());
		}
		super.setChanged();
		/** send commandID to notify which walk pattern should perform */
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

	private void end() {
		ended = true;
		setChanged();
		notifyObservers();
	}

	public Player currentPlayer() {
		return players[currentPlayerIndex];
	}

	private void switchPlayer() {
		currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
	}

	private void currentPlayerMoveByStep(int steps) throws InterruptedException {

		// If walk through the goal.
		if (getCurrentPlayerPosition() + steps > (board.SIZE - 1)) {
			int walkForwardToGoal = (board.SIZE - 1) - getCurrentPlayerPosition();
			int walkBackFromGoal = steps - walkForwardToGoal;
			int finalPosition = (board.SIZE - 1) - walkBackFromGoal;
			if (finalPosition > getCurrentPlayerPosition())
				steps = finalPosition - getCurrentPlayerPosition();
			else
				steps = (-1) * (getCurrentPlayerPosition() - finalPosition);
		}

		for (int i = 0; i < Math.abs(steps); i++) {
			currentPlayer().setStartXY(getCurrentPlayerPostionX(), getCurrentPlayerPostionY());
			if (steps > 0) // move forward
				board.movePlayerByStep(currentPlayer(), 1);
			else // move backward
				board.movePlayerByStep(currentPlayer(), -1);
			currentPlayer().setDestXY(getCurrentPlayerPostionX(), getCurrentPlayerPostionY());
			setChanged();
			notifyObservers();
			Thread.sleep(600);
		}
		currentPlayer().setStartXY(getCurrentPlayerPostionX(), getCurrentPlayerPostionY());
	}

	public String currentPlayerName() {
		return currentPlayer().getName();
	}

	public int getPlayerPostion(Player p) {
		return board.getPlayerPosition(p);
	}

	public int getCurrentPlayerPosition() {
		return board.getPlayerPosition(currentPlayer());
	}

	public int currentPlayerRollDice() {
		return this.currentPlayerDiceValue = currentPlayer().roll(die);
	}

	private boolean currentPlayerWin() {
		return board.playerIsAtGoal(currentPlayer());
	}

	private Square getCurrentPlayerSquare() {
		return board.getPlayerSquare(currentPlayer());
	}

	public int getCurrentPlayerPostionX() {
		return board.getPlayerPostionX(currentPlayer());
	}

	public int getCurrentPlayerPostionY() {
		return board.getPlayerPostionY(currentPlayer());
	}

	public int getPlayerPostionX(Player p) {
		return board.getPlayerPostionX(p);
	}

	public int getPlayerPostionY(Player p) {
		return board.getPlayerPostionY(p);
	}

	public Player[] getPlayers() {
		return players;
	}

	public void currentPlayerMove(int face) {
		this.currentPlayerDiceValue = face;
		if (!isReplay)
			this.replay.addReplay(saveStateToMemento());
		synchronized (this.gameThread) {
			this.gameThread.notify();
		}
	}

	public boolean isReplay() {
		return this.isReplay;
	}

	public void replay() {
		currentPlayerIndex = 0;
		this.isReplay = true;
		this.ended = false;
		replay.resetIndex();
		/** Set players to start point */
		for (Player p : players) {
			board.movePlayerToDest(p, 0);
			p.setDestXY(board.getPlayerPostionX(p), board.getPlayerPostionY(p));
			p.setStartXY(board.getPlayerPostionX(p), board.getPlayerPostionY(p));
		}

		/** Load dice histories from ReplayManager */
		synchronized (this.gameThread) {
			this.gameThread.notify();
		}
	}

	private Memento saveStateToMemento() {
		return new Memento(currentPlayerDiceValue);
	}

	private int getDiceValueFromMemento(Memento memento) {
		return memento.getDiceValue();
	}
}