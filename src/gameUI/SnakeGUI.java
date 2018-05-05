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
import game.PlayerHistory;
import game.Replay;

import javax.imageio.ImageIO;
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
	private JTextField txtPlayer1;
	private JTextField txtPlayer2;
	private JTextField txtPlayer3;
	private JTextField txtPlayer4;
	private static JButton replayButton ;
	
	private static String consoleHistory = "";

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
	
	public static void replayButtonClose( boolean b ) {
		if( b ) {
			replayButton.setEnabled( false );
		}
		else {
			replayButton.setEnabled( true );
		}
	}
	
	public static void updateConsoleHistory( String s ) {
		consoleHistory = consoleHistory.concat( s );
	}

	class Renderer extends JPanel implements Observer {

		private JLabel imageDice = new JLabel("");
		private JTextField textPlayerTurn = new JTextField("Player's turn");
		private JTextPane textConsole = new JTextPane();
		private JScrollPane textScrollPane = new JScrollPane( textConsole );

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

			JButton rollButton = new JButton("Roll");
			rollButton.setEnabled( false );
			rollButton.setPreferredSize(new Dimension(40, 0));
			rollButton.setBounds(900, 600, 280, 67);
			imageDice.setBounds(1050, 10, 135, 194);

			textPlayerTurn.setEditable(false);
			textPlayerTurn.setText(game.currentPlayerName() + "'s turn.");
			textPlayerTurn.setHorizontalAlignment(JTextField.CENTER);
			textPlayerTurn.setBounds(900, 110, 135, 50);

			replayButton = new JButton("Replay");
			replayButton.setBounds(900, 50, 135, 50);
			replayButton.setEnabled( false );
			replayButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// Replay function
				}
			});
			
			textConsole.setEditable( false );
			textScrollPane.setBounds( 900 , 200 , 280 , 390 );
			
			/** roll the dice */
			rollButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					int face = game.currentPlayerRollDice();
					game.currentPlayerMove(face);
					consoleHistory = consoleHistory.concat(
							"Player's Turn : " + game.currentPlayerName() + "\n" +
							"He/She rolls dice.\n" + 
							"--> get " + face + " value(s).\n" +
							"--> move to " + (game.currentPlayerPosition() + face + 1) + " positions.\n"
							);
					/**
					 * TODO:
					 * 
					 * Tell user if the face is 0 --> That means you are waiting for the train.
					 * 
					 * Tell user if the face is less than 0 --> That means you are drunk now.
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
					textConsole.setText( consoleHistory );
					
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

			JButton btnNewGame = new JButton("New Game");
			btnNewGame.setBounds(51, 120, 160, 40);
			getContentPane().add(btnNewGame);

			JLabel lblImageIcon = new JLabel();
			lblImageIcon.setHorizontalAlignment(SwingConstants.CENTER);
			lblImageIcon.setBackground(Color.GREEN);
			lblImageIcon.setForeground(Color.BLACK);
			lblImageIcon.setBounds(20, 20, 210, 110);
			Image image = gameLogo.getImage();
			Image newimg = image.getScaledInstance(210, 110,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			gameLogo = new ImageIcon(newimg);
			lblImageIcon.setIcon( gameLogo );

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

			txtPlayer1 = new JTextField();
			txtPlayer1.setText("P1 Name");
			txtPlayer1.setBounds(21, 345, 86, 20);
			txtPlayer1.setColumns(10);
			txtPlayer1.setEnabled( false );

			txtPlayer2 = new JTextField();
			txtPlayer2.setText("P2 Name ");
			txtPlayer2.setColumns(10);
			txtPlayer2.setBounds(21, 432, 86, 20);
			txtPlayer2.setEnabled( false );

			txtPlayer3 = new JTextField();
			txtPlayer3.setText("P3 Name");
			txtPlayer3.setColumns(10);
			txtPlayer3.setBounds(21, 509, 86, 20);
			txtPlayer3.setEnabled( false );

			txtPlayer4 = new JTextField();
			txtPlayer4.setText("P4 Name");
			txtPlayer4.setColumns(10);
			txtPlayer4.setBounds(21, 594, 86, 20);
			txtPlayer4.setEnabled( false );

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
					repaint();
				}

				public void finishCreateGame() {
					btnNewGame.setEnabled(false);
					rollButton.setEnabled( true );
					
					// Set players.
					if( rdbtn2Players.isSelected() ) game.setPlayer( 2 );
					else if( rdbtn3Players.isSelected() ) game.setPlayer( 3 );
					else if( rdbtn4Players.isSelected() ) game.setPlayer( 4 );
					
					// Disable player text field.
					txtPlayer1.setEnabled( false );
					txtPlayer2.setEnabled( false );
					txtPlayer3.setEnabled( false );
					txtPlayer4.setEnabled( false );
					
					// Set players name.
					Player[] p = game.getPlayers();
					for( int i = 0 ; i < game.getPlayers().length ; i++ ) {
						if( i == 0 ) p[i].setName( txtPlayer1.getText() );
						if( i == 1 ) p[i].setName( txtPlayer2.getText() );
						if( i == 2 ) p[i].setName( txtPlayer3.getText() );
						if( i == 3 ) p[i].setName( txtPlayer4.getText() );
					}
				}
			});
			
			rdbtn2Players.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					rdbtn3Players.setSelected( false );
					rdbtn4Players.setSelected( false );
					txtPlayer1.setEnabled( true );
					txtPlayer2.setEnabled( true );
					txtPlayer3.setEnabled( false );
					txtPlayer4.setEnabled( false );
				}
			});

			rdbtn3Players.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					rdbtn2Players.setSelected( false );
					rdbtn4Players.setSelected( false );
					txtPlayer1.setEnabled( true );
					txtPlayer2.setEnabled( true );
					txtPlayer3.setEnabled( true );
					txtPlayer4.setEnabled( false );
				}
			});

			rdbtn4Players.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					rdbtn2Players.setSelected( false );
					rdbtn3Players.setSelected( false );
					txtPlayer1.setEnabled( true );
					txtPlayer2.setEnabled( true );
					txtPlayer3.setEnabled( true );
					txtPlayer4.setEnabled( true );
				}
			});
			
			add(btnNewGame);
			add(lblImageIcon);
			add(btnSelectNumberOf);
			add(rdbtn2Players);
			add(rdbtn3Players);
			add(rdbtn4Players);
			add(btnEnterPlayerName);
			add(txtPlayer1);
			add(txtPlayer4);
			add(txtPlayer3);
			add(txtPlayer2);
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
			textConsole.setText( consoleHistory );
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
					System.out.println("Normal walking.");
					consoleHistory = consoleHistory.concat( game.currentPlayerName() + " normal walking.\n\n" );
				} else if (commandID == Game.SNAKE_COMMAND) {
					System.out.println("Facing snake.");
					isMoveDirectly = true;
					repaint();
					consoleHistory = consoleHistory.concat( game.currentPlayerName() + " faces Snake.\n\n" );
				} else if (commandID == Game.LADDER_COMMAND) {
					System.out.println("Facing ladder.");
					isMoveDirectly = true;
					consoleHistory = consoleHistory.concat( game.currentPlayerName() + " faces Ladder.\n\n" );
				} else if (commandID == Game.FREEZE_COMMAND) {
					System.out.println("The training is coming. Freeze 1 turn.");
					consoleHistory = consoleHistory.concat( game.currentPlayerName() + " Freeze!!!\n\n" );
				} else if (commandID == Game.BACKWARD_COMMAND) {
					System.out.println("Let's go party. Going backward.");
					consoleHistory = consoleHistory.concat( game.currentPlayerName() + " Backward!!!\n\n" );
				}
				updateConsoleHistory();
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
