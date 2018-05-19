package game;

import java.util.Iterator;
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
	private Iterator<Memento> replayList;

	public static final int NO_COMMAND = 0;
	public static final int SNAKE_COMMAND = 1;
	public static final int LADDER_COMMAND = 2;
	public static final int BACKWARD_COMMAND = 3;
	public static final int FREEZE_COMMAND = 4;

	private Thread gameThread;

	public Game() {
		isReplay = false;
		ended = false;
		die = new Die();
		board = new Board();
		currentPlayerIndex = 0;
		// gameThread.start();

		// Set and add Snake position
		Snake[] snakes = { new Snake(49, 4), new Snake(42, 16), new Snake(55, 7), new Snake(72, 14), new Snake(86, 48),
				new Snake(83, 62), new Snake(97, 39) };
		for (Snake s : snakes)
			board.addElement(s, s.getHead());

		// Set and add Ladder position
		Ladder[] ladders = { new Ladder(22, 1), new Ladder(58, 19), new Ladder(44, 5), new Ladder(95, 56),
				new Ladder(71, 51), new Ladder(91, 70) };
		for (Ladder l : ladders)
			board.addElement(l, l.getBottom());

		// Set and add Freeze position
		Freeze[] freezes = { new Freeze(6), new Freeze(37), new Freeze(67), new Freeze(92) };
		for (Freeze f : freezes)
			board.addElement(f, f.getFreezeSquare());

		// Set and add Backward position
		Backward[] backwards = { new Backward(30), new Backward(57), new Backward(64) };
		for (Backward b : backwards)
			board.addElement(b, b.getBackwardSquare());

		// Set ending position
		board.setGoal(Board.SIZE - 1);
	}

	private void gameLogic() {
		int commandID = NO_COMMAND;
		Square currentPlayerSquare = board.getPlayerSquare(currentPlayer());
		if (currentPlayerSquare.isElement()) {
			Element element = currentPlayerSquare.getElement();
			commandID = element.actionCommand();
			if (commandID == SNAKE_COMMAND) {
				Snake snake = (Snake) element;
				board.movePlayerToDest(currentPlayer(), snake.getTail());
			} else if (commandID == LADDER_COMMAND) {
				Ladder ladder = (Ladder) element;
				board.movePlayerToDest(currentPlayer(), ladder.getTop());
			} else if (commandID == BACKWARD_COMMAND)
				currentPlayer().setStrategy(new BackwardDice());
			else if (commandID == FREEZE_COMMAND)
				currentPlayer().setStrategy(new FreezeDice());
		}
		if (board.playerIsAtGoal(currentPlayer()))
			end();
		else
			switchPlayer();
		super.setChanged();
		/** send commandID to notify which walk pattern should perform */
		super.notifyObservers(commandID);
	}

	public void setPlayer(int num) {
		isReplay = false;
		ended = false;
		players = new Player[num];
		for (int i = 0; i < num; i++) {
			String nameTemp = (i + 1) + "";
			players[i] = new Player(nameTemp, i);
			board.addPlayer(players[i], 0);
			players[i].setDestXY(board.getPlayerPostionX(players[i]), board.getPlayerPostionY(players[i]));
			players[i].setStartXY(board.getPlayerPostionX(players[i]), board.getPlayerPostionY(players[i]));
		}
	}

	public void setNamePlayer(int num, String name) {
		players[num].setName(name);
	}

	public void start() {

		gameThread = new Thread() {
			@Override
			public void run() {
				while (!ended) {
					int diceValue = 0;
					try {
						if (!isReplay)
							synchronized (gameThread) {
								/** wait for user roll dice or any action */
								wait();
							}
						else
							getDiceValueFromMemento(replayList.next());
						diceValue = currentPlayerDiceValue;
						if (diceValue != 0) {
							currentPlayerMoveByStep(diceValue);
							gameLogic();
						} else
							switchPlayer();

					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		};
		gameThread.start();
	}

	public boolean isEnd() {
		return ended;
	}

	private void end() {
		ended = true;
	}

	public Player currentPlayer() {
		return players[currentPlayerIndex];
	}

	private void switchPlayer() {
		currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
	}

	private void currentPlayerMoveByStep(int steps) throws InterruptedException {

		// If walk through the goal.
		if (getPlayerPostion(currentPlayer()) + steps > (board.SIZE - 1)) {
			int walkForwardToGoal = (board.SIZE - 1) - getPlayerPostion(currentPlayer());
			int walkBackFromGoal = steps - walkForwardToGoal;
			int finalPosition = (board.SIZE - 1) - walkBackFromGoal;
			if (finalPosition > getPlayerPostion(currentPlayer()))
				steps = finalPosition - getPlayerPostion(currentPlayer());
			else
				steps = (-1) * (getPlayerPostion(currentPlayer()) - finalPosition);
		}

		for (int i = 0; i < Math.abs(steps); i++) {
			currentPlayer().setStartXY(getPlayerPostionX(currentPlayer()), getPlayerPostionY(currentPlayer()));
			if (steps > 0) // move forward
				board.movePlayerByStep(currentPlayer(), 1);
			else // move backward
				board.movePlayerByStep(currentPlayer(), -1);
			currentPlayer().setDestXY(getPlayerPostionX(currentPlayer()), getPlayerPostionY(currentPlayer()));
			setChanged();
			notifyObservers();
			Thread.sleep(500);
		}
		currentPlayer().setStartXY(getPlayerPostionX(currentPlayer()), getPlayerPostionY(currentPlayer()));
	}

	public String currentPlayerName() {
		return currentPlayer().getName();
	}

	public int getPlayerPostion(Player p) {
		return board.getPlayerPosition(p);
	}

	public int currentPlayerRollDice() {
		return this.currentPlayerDiceValue = currentPlayer().roll(die);
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
		synchronized (gameThread) {
			gameThread.notify();
		}
	}

	public boolean isReplay() {
		return this.isReplay;
	}

	public void replay(ReplayManager replay) {
		currentPlayerIndex = 0;
		this.isReplay = true;
		this.ended = false;
		this.replayList = replay.iterator();
		/** Set players to start point */
		for (Player p : players) {
			board.movePlayerToDest(p, 0);
			p.setDestXY(board.getPlayerPostionX(p), board.getPlayerPostionY(p));
			p.setStartXY(board.getPlayerPostionX(p), board.getPlayerPostionY(p));
		}
		/** Load dice histories from ReplayManager */
		this.start();
	}

	private void getDiceValueFromMemento(Memento memento) {
		this.currentPlayerDiceValue = memento.getDiceValue();
	}
}