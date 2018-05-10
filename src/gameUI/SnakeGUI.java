package gameUI;

import java.awt.EventQueue;
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

import game.Game;
import game.Player;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

public class SnakeGUI extends JFrame {

	private Renderer renderer;
	private Game game;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		Game game = new Game();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SnakeGUI window = new SnakeGUI(game);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SnakeGUI(Game game) {
		this.game = game;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		game.setPlayer(1);

		renderer = new Renderer();

		super.setLayout(new BorderLayout());
		// getContentPane().setLayout(null);
		game.addObserver(renderer);

		getContentPane().add(renderer);

		// --------------------------------

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

		private ImageIcon gameLogo = new ImageIcon(SnakeGUI.class.getResource("/resources/snlLogo.png"));

		private ImageIcon dice1 = new ImageIcon(SnakeGUI.class.getResource("/resources/dice1.jpg"));
		private ImageIcon dice2 = new ImageIcon(SnakeGUI.class.getResource("/resources/dice2.jpeg"));
		private ImageIcon dice3 = new ImageIcon(SnakeGUI.class.getResource("/resources/dice3.jpeg"));
		private ImageIcon dice4 = new ImageIcon(SnakeGUI.class.getResource("/resources/dice4.jpeg"));
		private ImageIcon dice5 = new ImageIcon(SnakeGUI.class.getResource("/resources/dice5.jpeg"));
		private ImageIcon dice6 = new ImageIcon(SnakeGUI.class.getResource("/resources/dice6.jpeg"));

		private ImageIcon hero1 = new ImageIcon(SnakeGUI.class.getResource("/resources/hero1.png"));
		private ImageIcon hero2 = new ImageIcon(SnakeGUI.class.getResource("/resources/hero2.png"));
		private ImageIcon hero3 = new ImageIcon(SnakeGUI.class.getResource("/resources/hero3.png"));
		private ImageIcon hero4 = new ImageIcon(SnakeGUI.class.getResource("/resources/hero4.png"));

		private Timer timer;

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
			timer = new Timer(3, new MoveByStep());

			setHero();

			startX = game.currentPlayer().getStartX();
			startY = game.currentPlayer().getStartY();

			setLayout(null);

			// --------------------------------
			// Right Controller
			// --------------------------------

			rollButton = new JButton("Roll");
			rollButton.setEnabled(false);
			rollButton.setPreferredSize(new Dimension(40, 0));
			rollButton.setBounds(900, 600, 280, 67);
			imageDice.setBounds(1050, 10, 135, 194);

			textPlayerTurn.setEditable(false);
			textPlayerTurn.setText(game.currentPlayerName() + "'s turn.");
			textPlayerTurn.setHorizontalAlignment(JTextField.CENTER);
			textPlayerTurn.setBounds(900, 110, 135, 50);

			replayButton = new JButton("Replay");
			replayButton.setBounds(900, 50, 135, 50);
			replayButton.setEnabled(false);
			replayButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// Replay function
				}
			});

			textConsole.setEditable(false);
			textScrollPane.setBounds(900, 200, 280, 390);
			/** roll the dice */

			rollButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					int face = game.currentPlayerRollDice();

//					int i = 0;
//					if (i == 0) {
//						game.currentPlayerMove(1);
//						i++;
//					} else
						game.currentPlayerMove(face);
					consoleHistory = consoleHistory.concat("\nPlayer's Turn : " + game.currentPlayerName() + "\n"
							+ "He/She rolls dice.\n" + "--> get " + face + " value(s).\n" );
					
					//"--> move to " + (game.currentPlayerPosition() + face + 1) + " positions.\n"
					
					/**
					 * TODO:
					 * 
					 * Tell user if the face is 0 --> That means you are waiting
					 * for the train.
					 * 
					 * Tell user if the face is less than 0 --> That means you
					 * are drunk now.
					 * 
					 */
					System.out.println(face);
					if (face == 1) {
						imageDice.setIcon(dice1);
					}
					if (face == 2) {
						imageDice.setIcon(dice2);
					}
					if (face == 3) {
						imageDice.setIcon(dice3);
					}
					if (face == 4) {
						imageDice.setIcon(dice4);
					}
					if (face == 5) {
						imageDice.setIcon(dice5);
					}
					if (face == 6) {
						imageDice.setIcon(dice6);
					}
					textPlayerTurn.setText(game.currentPlayerName() + "'s turn.");
					textConsole.setText(consoleHistory);

				}
			});

			add(textPlayerTurn);
			add(textScrollPane);
			add(replayButton);
			add(imageDice);
			add(rollButton);

			// --------------------------------
			// Left Controller
			// --------------------------------

			btnNewGame = new JButton("New Game");
			btnNewGame.setBounds(51, 120, 160, 40);
			add(btnNewGame);

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

			JRadioButton rdbtn2Players = new JRadioButton("2 Players");
			rdbtn2Players.setBounds(68, 199, 109, 23);

			JRadioButton rdbtn3Players = new JRadioButton("3 Players");
			rdbtn3Players.setBounds(68, 225, 109, 23);

			JRadioButton rdbtn4Players = new JRadioButton("4 Players");
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

			JLabel lblImage1 = new JLabel("image");
			lblImage1.setBounds(117, 315, 86, 80);
			lblImage1.setIcon(hero1);

			JLabel lblImage2 = new JLabel("image");
			lblImage2.setBounds(109, 395, 102, 94);
			lblImage2.setIcon(hero2);
			getContentPane().add(lblImage2);

			JLabel lblImage3 = new JLabel("image");
			lblImage3.setBounds(130, 479, 81, 80);
			lblImage3.setIcon(hero3);
			getContentPane().add(lblImage3);

			JLabel lblImage4 = new JLabel("image");
			lblImage4.setBounds(117, 557, 102, 94);
			lblImage4.setIcon(hero4);
			getContentPane().add(lblImage4);

			btnNewGame.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// Create new game.
					finishCreateGame();
					// repaint();
				}

				public void finishCreateGame() {
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
					
					// Disable set players choice.
					rdbtn2Players.setEnabled( false );
					rdbtn3Players.setEnabled( false );
					rdbtn4Players.setEnabled( false );

					// Set players name.
					for (int i = 0; i < game.getPlayers().length; i++)
						game.setNamePlayer(i, txtPlayer[i].getText().toString());

					isMoveDirectly = true;
					repaint();
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

			for (int i = 0; i < 4; i++) {
				add(txtPlayer[i]);
			}

			add(lblImage1);
			add(lblImage2);
			add(lblImage3);
			add(lblImage4);

			// --------------------------------
			// Middle Controller
			// --------------------------------

			JLabel bg = new JLabel("");
			bg.setIcon(new ImageIcon(SnakeGUI.class.getResource("/resources/newBoard.jpeg")));
			bg.setBounds(250, 20, 644, 627);
			add(bg);

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
			// g.drawImage(hero[2], (240 + 7) + 62 * 8, 563, this);
			// g.drawImage(hero[0], 240 + 62 * 9, (563), this);
			// g.drawImage(hero[1], (240 - 20) + 62 * 9, 563 - 62 * 5, this);
			// g.drawImage(hero[3], (240 - 20) + 62 * 0, 563, this);
		}

		private void paintHeroByStep(Graphics g) {
			for (Player p : game.getPlayers()) {
				if (p != game.currentPlayer())
					g.drawImage(hero[p.getIndex()], game.getPlayerPostionX(p) + paddingImage[p.getIndex()],
							game.getPlayerPostionY(p), this);
				else
					g.drawImage(hero[p.getIndex()], startX + paddingImage[p.getIndex()], startY, this);
			}
		}

		private void paintHeroByDirectly(Graphics g) {
			for (Player p : game.getPlayers()) {
				if (p != game.currentPlayer())
					g.drawImage(hero[p.getIndex()], game.getPlayerPostionX(p) + paddingImage[p.getIndex()],
							game.getPlayerPostionY(p), this);
				else
					g.drawImage(hero[p.getIndex()], game.getCurrentPlayerPostionX() + paddingImage[p.getIndex()],
							game.getCurrentPlayerPostionY(), this);
			}
		}

		private void setHero() {
			try {
				for (int i = 0; i < 4; i++)
					hero[i] = ImageIO.read(SnakeGUI.class.getResource("/resources/hero" + (i + 1) + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
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
				consoleHistory = consoleHistory.concat(game.currentPlayerName() + " WIN!!!");
				updateConsoleHistory();
				replayButton.setEnabled(true);
				btnNewGame.setEnabled(true);
			} else if (arg == null)
				timer.start();
			else { /** check element */
				int commandID = (int) arg;
				if (commandID == Game.NO_COMMAND)
					consoleHistory = consoleHistory.concat(game.currentPlayerName() + " normal walking.\n");
				else if (commandID == Game.SNAKE_COMMAND) {
					System.out.println("Facing snake.");
					isMoveDirectly = true;
					repaint();
					consoleHistory = consoleHistory.concat(game.currentPlayerName() + " faces Snake.\n");
				} else if (commandID == Game.LADDER_COMMAND) {
					System.out.println("Facing ladder.");
					isMoveDirectly = true;
					repaint();
					consoleHistory = consoleHistory.concat(game.currentPlayerName() + " faces Ladder.\n");
				} else if (commandID == Game.FREEZE_COMMAND) {
					System.out.println("The training is coming. Freeze 1 turn.");
					consoleHistory = consoleHistory.concat(game.currentPlayerName() + " Freeze!!!\n");
				} else if (commandID == Game.BACKWARD_COMMAND) {
					System.out.println("Let's go party. Going backward.");
					consoleHistory = consoleHistory.concat(game.currentPlayerName() + " Backward!!!\n");
				}
				updateConsoleHistory();
				rollButton.setEnabled(true);
			}

		}

		class MoveByStep implements ActionListener {
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
		}

	}

}