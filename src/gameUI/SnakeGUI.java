package gameUI;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import game.Board;
import game.Game;
import game.Player;
import replay.Memento;
import replay.ReplayManager;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

public class SnakeGUI extends JFrame {

	private Renderer renderer;
	private Game game;
	private ReplayManager replayManager;

	/**
	 * Create the application.
	 */
	public SnakeGUI(Game game) {
		replayManager = new ReplayManager();
		this.game = game;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		game.setPlayer(2);

		renderer = new Renderer();

		super.setLayout(new BorderLayout());
		game.addObserver(renderer);

		getContentPane().add(renderer);

		super.setResizable(false);
		super.setSize(1200, 700);
		super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		renderer.requestFocus();

		super.setVisible(true);
	}

	class Renderer extends JPanel implements Observer {

		private JButton replayButton;

		private JTextField[] txtPlayer = new JTextField[4];

		private JButton btnNewGame;

		private JLabel imageDice = new JLabel("");
		private JTextField textPlayerTurn = new JTextField("Player's turn");
		private JTextPane textConsole = new JTextPane();
		private JScrollPane textScrollPane = new JScrollPane(textConsole);

		private JRadioButton rdbtn2Players;
		private JRadioButton rdbtn3Players;
		private JRadioButton rdbtn4Players;

		private ImageIcon gameLogo = new ImageIcon(SnakeGUI.class.getResource("/resources/snlLogo.png"));

		private ImageIcon dice[] = new ImageIcon[6];

		private Timer timer; // animation

		private int startX;
		private int startY;
		private int destX;
		private int destY;

		private Image[] hero = new Image[4];
		private int[] paddingImage = { 0, -20, 7, -20 };

		private boolean isMoveDirectly = false;

		private JButton rollButton;
		private String consoleHistory = "";

		public Renderer() {
			timer = new Timer(3, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (startX < destX)
						startX++;
					else if (startX > destX)
						startX--;
					else if (startY > destY)
						startY--;
					else if (startY < destY)
						startY++;
					else if (startX == destX && startY == destY)
						timer.stop();
					repaint();
				}
			});

			try {
				for (int i = 0; i < 4; i++)
					hero[i] = ImageIO.read(SnakeGUI.class.getResource("/resources/hero" + (i + 1) + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}

			for (int i = 0; i < dice.length; i++)
				dice[i] = new ImageIcon(SnakeGUI.class.getResource("/resources/dice" + (i + 1) + ".jpeg"));

			setLayout(null);

			rollButton = new JButton("Roll");
			rollButton.setEnabled(false);
			rollButton.setPreferredSize(new Dimension(40, 0));
			rollButton.setBounds(900, 600, 280, 67);
			imageDice.setBounds(1050, 10, 135, 194);

			textPlayerTurn.setEditable(false);
			textPlayerTurn.setHorizontalAlignment(JTextField.CENTER);
			textPlayerTurn.setBounds(900, 110, 135, 50);

			replayButton = new JButton("Replay");
			replayButton.setBounds(900, 50, 135, 50);
			replayButton.setEnabled(false);
			replayButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// Replay function
					replayButton.setEnabled(false);
					btnNewGame.setEnabled(false);
					rdbtn2Players.setEnabled(false);
					rdbtn3Players.setEnabled(false);
					rdbtn4Players.setEnabled(false);
					game.replay(replayManager);
					if (game.isReplay())
						consoleHistory = consoleHistory.concat("Replaying...\n\n");
					updateConsoleHistory();
				}
			});

			textConsole.setEditable(false);
			textScrollPane.setBounds(900, 200, 280, 390);
			/** roll the dice */

			rollButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

					int face = game.currentPlayerRollDice();
					if (face == 0) {
						consoleHistory = consoleHistory
								.concat("Player's Turn : " + game.currentPlayerName() + "\n" + "Freeze.\n\n");
						rollButton.setEnabled(true);
					} else if (face < 0)
						consoleHistory = consoleHistory.concat("Player's Turn : " + game.currentPlayerName() + "\n"
								+ "He/She gets drunk and rolls dice.\n" + "--> get " + Math.abs(face) + " value(s).\n"
								+ "--> Move backward to " + (game.getPlayerPostion(game.currentPlayer()) + face + 1)
								+ " positions.\n");
					else if (game.getPlayerPostion(game.currentPlayer()) + face >= Board.SIZE)
						consoleHistory = consoleHistory.concat("Player's Turn : " + game.currentPlayerName() + "\n"
								+ "He/She rolls dice.\n" + "--> get " + face + " value(s).\n"
								+ "Get through ending point.\n" + "--> So move backward to "
								+ (Board.SIZE - ((game.getPlayerPostion(game.currentPlayer()) + face + 1) - Board.SIZE))
								+ " positions.\n");
					else
						consoleHistory = consoleHistory.concat("Player's Turn : " + game.currentPlayerName() + "\n"
								+ "He/She rolls dice.\n" + "--> get " + face + " value(s).\n" + "--> Move forward to "
								+ (game.getPlayerPostion(game.currentPlayer()) + face + 1) + " positions.\n");

					game.currentPlayerMove(face);
					replayManager.addReplay(new Memento(face));

					if (Math.abs(face) == 1)
						imageDice.setIcon(dice[0]);
					else if (Math.abs(face) == 2)
						imageDice.setIcon(dice[1]);
					else if (Math.abs(face) == 3)
						imageDice.setIcon(dice[2]);
					else if (Math.abs(face) == 4)
						imageDice.setIcon(dice[3]);
					else if (Math.abs(face) == 5)
						imageDice.setIcon(dice[4]);
					else if (Math.abs(face) == 6)
						imageDice.setIcon(dice[5]);

					textConsole.setText(consoleHistory);
				}
			});

			add(textPlayerTurn);
			add(textScrollPane);
			add(replayButton);
			add(imageDice);
			add(rollButton);

			btnNewGame = new JButton("New Game");
			btnNewGame.setBounds(51, 120, 160, 40);

			JLabel lblImageIcon = new JLabel();
			lblImageIcon.setHorizontalAlignment(SwingConstants.CENTER);
			lblImageIcon.setBackground(Color.GREEN);
			lblImageIcon.setForeground(Color.BLACK);
			lblImageIcon.setBounds(20, 20, 210, 110);
			Image image = gameLogo.getImage();
			Image newimg = image.getScaledInstance(210, 110, java.awt.Image.SCALE_SMOOTH);

			gameLogo = new ImageIcon(newimg);
			lblImageIcon.setIcon(gameLogo);

			JButton btnSelectNumberOf = new JButton("Select number of player");
			btnSelectNumberOf.setBounds(30, 169, 200, 30);
			btnSelectNumberOf.setEnabled(false);

			rdbtn2Players = new JRadioButton("2 Players");
			rdbtn2Players.setBounds(68, 199, 109, 23);

			rdbtn3Players = new JRadioButton("3 Players");
			rdbtn3Players.setBounds(68, 225, 109, 23);

			rdbtn4Players = new JRadioButton("4 Players");
			rdbtn4Players.setBounds(68, 251, 109, 23);

			JButton btnEnterPlayerName = new JButton("Enter Player's Name");
			btnEnterPlayerName.setBounds(43, 281, 160, 23);
			btnEnterPlayerName.setEnabled(false);

			/** initialize player text */
			int padding = 0;
			for (int i = 0; i < 4; i++) {
				txtPlayer[i] = new JTextField();
				txtPlayer[i].setText("P" + (i + 1));
				txtPlayer[i].setBounds(21, 345 + padding, 86, 20);
				txtPlayer[i].setColumns(10);
				txtPlayer[i].setEnabled(true);
				padding += 87;
			}

			// default text player
			txtPlayer[3].setEnabled(false);
			txtPlayer[2].setEnabled(false);

			ImageIcon heroImage[] = new ImageIcon[4];
			for (int i = 0; i < 4; i++)
				heroImage[i] = new ImageIcon(SnakeGUI.class.getResource("/resources/hero" + (i + 1) + ".png"));

			JLabel lblImage1 = new JLabel("image");
			lblImage1.setBounds(117, 315, 86, 80);
			lblImage1.setIcon(heroImage[0]);

			JLabel lblImage2 = new JLabel("image");
			lblImage2.setBounds(109, 395, 102, 94);
			lblImage2.setIcon(heroImage[1]);

			JLabel lblImage3 = new JLabel("image");
			lblImage3.setBounds(130, 479, 81, 80);
			lblImage3.setIcon(heroImage[2]);

			JLabel lblImage4 = new JLabel("image");
			lblImage4.setBounds(117, 557, 102, 94);
			lblImage4.setIcon(heroImage[3]);

			btnNewGame.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// Create new game.
					createGame();
				}

				public void createGame() {
					btnNewGame.setEnabled(false);
					replayButton.setEnabled(false);
					rollButton.setEnabled(true);

					// Set players.
					if (rdbtn2Players.isSelected())
						game.setPlayer(2);
					else if (rdbtn3Players.isSelected())
						game.setPlayer(3);
					else if (rdbtn4Players.isSelected())
						game.setPlayer(4);

					// Disable player text field.
					for (int i = 0; i < 4; i++)
						txtPlayer[i].setEnabled(false);

					rdbtn2Players.setEnabled(false);
					rdbtn3Players.setEnabled(false);
					rdbtn4Players.setEnabled(false);

					// Set players name.
					for (int i = 0; i < game.getPlayers().length; i++)
						game.setNamePlayer(i, txtPlayer[i].getText().toString());

					isMoveDirectly = true;
					repaint();
					replayManager = new ReplayManager();
					game.start();
					textPlayerTurn.setText(game.currentPlayerName() + "'s turn.");
				}
			});

			rdbtn2Players.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					rdbtn3Players.setSelected(false);
					rdbtn4Players.setSelected(false);
					txtPlayer[0].setEnabled(true);
					txtPlayer[1].setEnabled(true);
					txtPlayer[2].setEnabled(false);
					txtPlayer[3].setEnabled(false);
				}
			});

			rdbtn3Players.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					rdbtn2Players.setSelected(false);
					rdbtn4Players.setSelected(false);
					txtPlayer[0].setEnabled(true);
					txtPlayer[1].setEnabled(true);
					txtPlayer[2].setEnabled(true);
					txtPlayer[3].setEnabled(false);
				}
			});

			rdbtn4Players.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					rdbtn2Players.setSelected(false);
					rdbtn3Players.setSelected(false);
					for (int i = 0; i < 4; i++)
						txtPlayer[i].setEnabled(true);
				}
			});

			/** initialize radio button into group */
			ButtonGroup group = new ButtonGroup();
			group.add(rdbtn2Players);
			group.add(rdbtn3Players);
			group.add(rdbtn4Players);
			rdbtn2Players.setSelected(true);

			add(btnNewGame);
			add(lblImageIcon);
			add(btnSelectNumberOf);

			add(rdbtn2Players);
			add(rdbtn3Players);
			add(rdbtn4Players);

			add(btnEnterPlayerName);

			for (int i = 0; i < 4; i++)
				add(txtPlayer[i]);

			add(lblImage1);
			add(lblImage2);
			add(lblImage3);
			add(lblImage4);

			JLabel bg = new JLabel("");
			bg.setIcon(new ImageIcon(SnakeGUI.class.getResource("/resources/newBoard.jpeg")));
			bg.setBounds(250, 20, 644, 627);
			add(bg);

			startX = game.currentPlayer().getStartX();
			startY = game.currentPlayer().getStartY();
			destX = game.currentPlayer().getDestX();
			destY = game.currentPlayer().getDestY();

			setDoubleBuffered(true);
		}

		public void updateConsoleHistory() {
			textConsole.setText(consoleHistory);
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			if (!isMoveDirectly)
				paintHeroByStep(g);
			else
				paintHeroByDirectly(g);
		}

		private void paintHeroByStep(Graphics g) {
			for (Player p : game.getPlayers())
				if (p != game.currentPlayer())
					g.drawImage(hero[p.getIndex()], game.getPlayerPostionX(p) + paddingImage[p.getIndex()],
							game.getPlayerPostionY(p), this);
				else
					g.drawImage(hero[p.getIndex()], startX + paddingImage[p.getIndex()], startY, this);

		}

		private void paintHeroByDirectly(Graphics g) {
			for (Player p : game.getPlayers())
				if (p != game.currentPlayer())
					g.drawImage(hero[p.getIndex()], game.getPlayerPostionX(p) + paddingImage[p.getIndex()],
							game.getPlayerPostionY(p), this);
				else
					g.drawImage(hero[p.getIndex()],
							game.getPlayerPostionX(game.currentPlayer()) + paddingImage[p.getIndex()],
							game.getPlayerPostionY(game.currentPlayer()), this);
		}

		@Override
		public void update(Observable o, Object arg) {
			rollButton.setEnabled(false);
			// default moving.
			isMoveDirectly = false;
			startX = game.currentPlayer().getStartX();
			startY = game.currentPlayer().getStartY();
			destX = game.currentPlayer().getDestX();
			destY = game.currentPlayer().getDestY();

			if (game.isEnd()) {
				rollButton.setEnabled(false);
				if (!game.isReplay())
					consoleHistory = consoleHistory.concat(game.currentPlayerName() + " WIN!!! \n\n");
				updateConsoleHistory();
				replayButton.setEnabled(true);
				btnNewGame.setEnabled(true);
				rdbtn2Players.setEnabled(true);
				rdbtn3Players.setEnabled(true);
				rdbtn4Players.setEnabled(true);
			} else if (arg == null)
				timer.start();
			else { /** check element */
				int commandID = (int) arg;
				if (commandID == Game.NO_COMMAND) {
					if (!game.isReplay())
						consoleHistory = consoleHistory.concat(game.currentPlayerName() + " see nothing.\n\n");
				} else if (commandID == Game.SNAKE_COMMAND) {
					isMoveDirectly = true;
					repaint();
					if (!game.isReplay())
						consoleHistory = consoleHistory
								.concat(game.currentPlayerName() + " faces Snake (going down).\n\n");
				} else if (commandID == Game.LADDER_COMMAND) {
					isMoveDirectly = true;
					repaint();
					if (!game.isReplay())
						consoleHistory = consoleHistory
								.concat(game.currentPlayerName() + " faces Ladder (climbing up).\n\n");
				} else if (commandID == Game.FREEZE_COMMAND) {
					if (!game.isReplay())
						consoleHistory = consoleHistory.concat(
								game.currentPlayerName() + " must wait for the train passing. (freeze next turn)\n\n");
				} else if (commandID == Game.BACKWARD_COMMAND)
					if (!game.isReplay())
						consoleHistory = consoleHistory
								.concat(game.currentPlayerName() + " get drunk now!! (going backward next turn)\n\n");
				if (!game.isReplay()) {
					updateConsoleHistory();
					rollButton.setEnabled(true);
				}
				textPlayerTurn.setText(game.currentPlayerName() + "'s turn.");
			}

		}

	}

}