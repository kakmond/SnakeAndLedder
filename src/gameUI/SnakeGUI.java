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
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import game.Game;
import game.Player;
import game.Replay;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;

public class SnakeGUI extends JFrame {

	private Renderer renderer;
	private Game game;
	private JTextField txtPlayer;
	private JTextField txtPlayer_3;
	private JTextField txtPlayer_2;
	private JTextField txtPlayer_1;

	private ImageIcon hero1 = new ImageIcon(SnakeGUI.class.getResource("/resources/hero1.png"));
	private ImageIcon hero2 = new ImageIcon(SnakeGUI.class.getResource("/resources/hero2.png"));
	private ImageIcon hero3 = new ImageIcon(SnakeGUI.class.getResource("/resources/hero3.png"));
	private ImageIcon hero4 = new ImageIcon(SnakeGUI.class.getResource("/resources/hero4.png"));

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

		Thread gameThread = new Thread(game);
		gameThread.start();
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

		renderer = new Renderer();

		super.setLayout(new BorderLayout());
		// getContentPane().setLayout(null);
		game.addObserver(renderer);

		getContentPane().add(renderer);

		// --------------------------------
		addMouseListener(new MouseEvent());

		super.setResizable(false);
		super.setSize(1200, 700);
		super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		renderer.requestFocus();

		super.setVisible(true);
	}

	class Renderer extends JPanel implements Observer {

		private JLabel imageDice = new JLabel("");
		private JTextField textPlayerTurn = new JTextField("Player's turn");
		private JTextField textPlayerStatus = new JTextField("Player's Status");
		private JButton replayButton = new JButton("Replay");
		private JButton saveButton = new JButton("Save");
		private JButton loadButton = new JButton("Load");

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

		public Renderer() {
			timer = new Timer(5, new MoveByStep());

			setHero();

			game.setPlayer(1);

			startX = game.currentPlayer().getStartX();
			startY = game.currentPlayer().getStartY();

			setLayout(null);

			// --------------------------------
			// Right Controller
			// --------------------------------

			JButton btnNewButton = new JButton("Roll");

			btnNewButton.setPreferredSize(new Dimension(40, 0));
			btnNewButton.setBounds(940, 500, 135, 67);
			imageDice.setBounds(940, 320, 135, 194);

			/** roll the dice */
			btnNewButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					int face = game.currentPlayerRollDice();
					game.currentPlayerMove(face);

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
				}
			});

			textPlayerTurn.setEditable(false);
			textPlayerTurn.setText(game.currentPlayerName() + "'s turn.");
			textPlayerTurn.setHorizontalAlignment(JTextField.CENTER);
			textPlayerTurn.setBounds(940, 50, 135, 40);

			textPlayerStatus.setEditable(false);
			textPlayerStatus.setHorizontalAlignment(JTextField.CENTER);
			textPlayerStatus.setBounds(940, 100, 135, 80);

			replayButton.setBounds(940, 190, 135, 40);
			replayButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// Replay function
				}
			});

			saveButton.setBounds(940, 240, 135, 40);
			saveButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// Save function
				}
			});

			loadButton.setBounds(940, 290, 135, 40);
			loadButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// Load function
				}
			});

			add(textPlayerTurn);
			add(textPlayerStatus);
			add(replayButton);
			add(saveButton);
			add(loadButton);
			add(imageDice);
			add(btnNewButton);

			// --------------------------------
			// Left Controller
			// --------------------------------

			JButton btnNewGame = new JButton("New Game");
			btnNewGame.setBounds(51, 11, 160, 23);
			add(btnNewGame);

			JLabel lblPlayerTurn = new JLabel("Player Turn");
			lblPlayerTurn.setHorizontalAlignment(SwingConstants.CENTER);
			lblPlayerTurn.setBounds(93, 45, 67, 23);
			add(lblPlayerTurn);

			JLabel lblImageIcon = new JLabel("Image Icon");
			lblImageIcon.setHorizontalAlignment(SwingConstants.CENTER);
			lblImageIcon.setBackground(Color.GREEN);
			lblImageIcon.setForeground(Color.BLACK);
			lblImageIcon.setBounds(82, 64, 95, 94);
			add(lblImageIcon);

			JButton btnSelectNumberOf = new JButton("Select number of player");

			btnSelectNumberOf.setBounds(51, 169, 160, 23);
			add(btnSelectNumberOf);

			JRadioButton rdbtnPlayers = new JRadioButton("2 Players");
			rdbtnPlayers.setBounds(68, 199, 109, 23);
			add(rdbtnPlayers);

			JRadioButton rdbtnPlayers_1 = new JRadioButton("3 Players");
			rdbtnPlayers_1.setBounds(68, 225, 109, 23);
			add(rdbtnPlayers_1);

			JRadioButton rdbtnPlayers_2 = new JRadioButton("4 Players");
			rdbtnPlayers_2.setBounds(68, 251, 109, 23);
			add(rdbtnPlayers_2);

			JButton btnEnterPlayerName = new JButton("Enter Player's Name");
			btnEnterPlayerName.setBounds(51, 281, 160, 23);
			add(btnEnterPlayerName);

			txtPlayer = new JTextField();
			txtPlayer.setText("Player1");
			txtPlayer.setBounds(21, 345, 86, 20);
			add(txtPlayer);
			txtPlayer.setColumns(10);

			txtPlayer_3 = new JTextField();
			txtPlayer_3.setText("Player2");
			txtPlayer_3.setColumns(10);
			txtPlayer_3.setBounds(21, 432, 86, 20);
			add(txtPlayer_3);

			txtPlayer_2 = new JTextField();
			txtPlayer_2.setText("Player3");
			txtPlayer_2.setColumns(10);
			txtPlayer_2.setBounds(21, 509, 86, 20);
			add(txtPlayer_2);

			txtPlayer_1 = new JTextField();
			txtPlayer_1.setText("Player4");
			txtPlayer_1.setColumns(10);
			txtPlayer_1.setBounds(21, 594, 86, 20);
			add(txtPlayer_1);

			JLabel lblImage = new JLabel("image");
			lblImage.setBounds(117, 315, 86, 80);
			lblImage.setIcon(hero1);
			add(lblImage);

			JLabel label = new JLabel("image");
			label.setBounds(109, 395, 102, 94);
			label.setIcon(hero2);
			add(label);

			JLabel label_1 = new JLabel("image");
			label_1.setBounds(130, 479, 81, 80);
			label_1.setIcon(hero3);
			add(label_1);

			JLabel label_2 = new JLabel("image");
			label_2.setBounds(117, 557, 102, 94);
			label_2.setIcon(hero4);
			add(label_2);

			// --------------------------------
			// Middle Controller
			// --------------------------------

			JLabel bg = new JLabel("");
			bg.setIcon(new ImageIcon(SnakeGUI.class.getResource("/resources/newBoard.jpeg")));
			bg.setBounds(250, 20, 644, 627);
			add(bg);

			setDoubleBuffered(true);
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
			// default moving.
			isMoveDirectly = false;
			startX = game.currentPlayer().getStartX();
			startY = game.currentPlayer().getStartY();
			destX = game.currentPlayer().getDestX();
			destY = game.currentPlayer().getDestY();

			if (arg == null)
				timer.start();

			else { /** check element */
				int commandID = (int) arg;
				if (commandID == Game.NO_COMMAND) {
					textPlayerStatus.setText("Normal walking");
					System.out.println("Normal walking.");
				} else if (commandID == Game.SNAKE_COMMAND) {
					textPlayerStatus.setText("Facing Snake.");
					System.out.println("Facing snake.");
					isMoveDirectly = true;
					repaint();
				} else if (commandID == Game.LADDER_COMMAND) {
					textPlayerStatus.setText("Facing Ladder.");
					System.out.println("Facing ladder.");
					isMoveDirectly = true;
					repaint();
				} else if (commandID == Game.FREEZE_COMMAND) {
					textPlayerStatus.setText("Freeze 1 turn.");
					System.out.println("The training is coming. Freeze 1 turn.");
				} else if (commandID == Game.BACKWARD_COMMAND) {
					textPlayerStatus.setText("Going Backward.");
					System.out.println("Let's go party. Going backward.");
				}

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

	/** A Class for determine coordinate */
	class MouseEvent implements MouseListener {

		@Override
		public void mouseClicked(java.awt.event.MouseEvent e) {
			System.out.println(e.getX() + " " + e.getY());
		}

		@Override
		public void mousePressed(java.awt.event.MouseEvent e) {

		}

		@Override
		public void mouseReleased(java.awt.event.MouseEvent e) {

		}

		@Override
		public void mouseEntered(java.awt.event.MouseEvent e) {

		}

		@Override
		public void mouseExited(java.awt.event.MouseEvent e) {

		}

	}
}
