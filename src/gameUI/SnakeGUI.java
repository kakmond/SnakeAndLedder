package gameUI;

import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import game.Game;
import game.Player;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Timer;
import java.awt.event.ActionEvent;

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
		game.addObserver(renderer);

		add(renderer);

		addMouseListener(new MouseEvent());

		super.setResizable(false);
		super.setSize(1200, 700);
		super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		renderer.requestFocus();

		super.setVisible(true);
	}

	class Renderer extends JPanel implements ActionListener, Observer {

		private JLabel imageDice = new JLabel("");
		private ImageIcon dice1 = new ImageIcon(SnakeGUI.class.getResource("/resources/dice1.jpg"));
		private ImageIcon dice2 = new ImageIcon(SnakeGUI.class.getResource("/resources/dice2.jpeg"));
		private ImageIcon dice3 = new ImageIcon(SnakeGUI.class.getResource("/resources/dice3.jpeg"));
		private ImageIcon dice4 = new ImageIcon(SnakeGUI.class.getResource("/resources/dice4.jpeg"));
		private ImageIcon dice5 = new ImageIcon(SnakeGUI.class.getResource("/resources/dice5.jpeg"));
		private ImageIcon dice6 = new ImageIcon(SnakeGUI.class.getResource("/resources/dice6.jpeg"));

		private Timer timer;

		private int startX;
		private int startY;
		private int destX;
		private int destY;

		public Renderer() {

			game.setPlayer(4);

			startX = game.currentPlayer().getStartX();
			startY = game.currentPlayer().getStartY();

			System.out.println("START IS " + startX + " " + startY);

			timer = new Timer(5, this);

			super.setLayout(null);
			JButton btnNewButton = new JButton("Roll");

			btnNewButton.setPreferredSize(new Dimension(40, 0));
			btnNewButton.setBounds(940, 506, 135, 67);
			imageDice.setBounds(940, 299, 135, 194);

			/** roll the dice */
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int face = game.currentPlayerRollDice();
					// game.currentPlayerMove(face);
					synchronized (game) {
						game.notify();
					}
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

			add(imageDice);
			add(btnNewButton);

			// JLabel hero1 = new JLabel("");
			// hero1.setIcon(new
			// ImageIcon(SnakeGUI.class.getResource("/resources/hero3.png")));
			// hero1.setBounds(251, 0, 644, 600);
			// add(hero1);

			JLabel bg = new JLabel("");
			bg.setIcon(new ImageIcon(SnakeGUI.class.getResource("/resources/newBoard.jpeg")));
			// bg.setBounds(251, 0, 644, 647);
			bg.setBounds(250, 20, 644, 627);
			add(bg);

			setDoubleBuffered(true);
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			paintPlayer(g);
		}

		private void paintPlayer(Graphics g) {
			for (Player p : game.getPlayers()) {
				if (p != game.currentPlayer()) {
					Color playerColor = p.getColor();
					int x = game.getPlayerPostionX(p);
					int y = game.getPlayerPostionY(p);
					g.setColor(playerColor);
					g.fillOval(x, y, 25, 25);
					g.setColor(Color.black);
					g.drawString(p.getName(), x + 9, y + 15);
				} else {
					g.setColor(game.currentPlayer().getColor());
					g.fillOval(startX, startY, 25, 25);
					g.setColor(Color.black);
					g.drawString(game.currentPlayer().getName(), startX + 9, startY + 15);
				}
			}
			/* for test */
			// g.fillOval((268) + 62 * 9, 605 - 63 - (63 * 0), 25, 25);
			// g.setColor(Color.black);
			// g.drawString(game.currentPlayer().getName(), startX + 9, startY +
			// 15);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (startX < destX) {
				startX++;
				repaint();
			} else if (startX > destX) {
				startX--;
				repaint();
			} else if (startY > destY) {
				startY--;
				repaint();
			} else if (startY < destY) {
				startY++;
				repaint();
			} else {
				timer.stop();
			}
		}

		@Override
		public void update(Observable o, Object arg) {
			startX = game.currentPlayer().getStartX();
			startY = game.currentPlayer().getStartY();
			destX = game.currentPlayer().getDestX();
			destY = game.currentPlayer().getDestY();
			timer.start();
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
