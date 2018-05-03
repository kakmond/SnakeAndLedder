package gameUI;

import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import game.Game;
import gameUI.SnakeUI.MouseEvent;
import gameUI.SnakeUI.Renderer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;
import java.awt.event.ActionEvent;

public class SnakeGUI extends JFrame implements Observer {

	private Renderer renderer;
	private Game game;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SnakeGUI window = new SnakeGUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the application.
	 */
	public SnakeGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		game = new Game();
		game.addObserver(this);

		renderer = new Renderer();

		add(renderer);

		game.setPlayer(1);

		addMouseListener(new MouseEvent());

		super.setResizable(false);
		super.setSize(1200, 700);
		super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		renderer.requestFocus();

		super.setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("CALL FROM OBSERVABLE");
		renderer.repaint();
	}

	class Renderer extends JPanel {

		private int blockWidth = 20;
		// private int mapSize;

		public Renderer() {

			JButton btnNewButton = new JButton("Roll");
			btnNewButton.setPreferredSize(new Dimension(40, 0));
			btnNewButton.setBounds(940, 506, 135, 67);

			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int face = game.currentPlayerRollDice();
					game.currentPlayerMove(face);
				}
			});
			add(btnNewButton);

			super.setLayout(null);
			JLabel lblNewLabel = new JLabel("");
			lblNewLabel.setIcon(new ImageIcon(SnakeGUI.class.getResource("/resources/board.jpg")));
			lblNewLabel.setBounds(251, 0, 644, 647);
			super.add(lblNewLabel);

			JLabel label = new JLabel("");
			label.setIcon(new ImageIcon(SnakeGUI.class.getResource("/resources/dice1.jpg")));
			label.setBounds(940, 299, 135, 194);
			add(label);

			setDoubleBuffered(true);
		}

		@Override
		public void paint(Graphics g) {
			System.out.println("CALl!");
			super.paint(g);
			paintPlayer(g);
		}

		private void paintPlayer(Graphics g) {

			g.setColor(Color.BLACK);
			int x = game.getCurrentPlayerPostionX();
			int y = game.getCurrentPlayerPostionY();
			System.out.println("Get data: x= " + x + " Y= " + y);
			g.fillOval(x, y, 25, 25);
			g.setColor(Color.BLUE);
			g.fillOval(x, y, 25, 25);
		}
	}

	/** A Class for determine coordinate */
	class MouseEvent implements MouseListener {

		@Override
		public void mouseClicked(java.awt.event.MouseEvent e) {
			System.out.println("X: " + e.getX() + " Y:" + e.getY());

		}

		@Override
		public void mousePressed(java.awt.event.MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(java.awt.event.MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseEntered(java.awt.event.MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(java.awt.event.MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}
}
