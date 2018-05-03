package gameUI;

import javax.swing.*;

import game.Game;

import java.awt.*;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

public class SnakeUI extends JFrame implements Observer {

	private Game game;
	private Renderer renderer;

	public SnakeUI() {
		// this.game = game;
		// this.game.setPlayer(4); // set เป็น 4 player ไปก่อน

		setLayout(new BorderLayout());

		JPanel newPanel = new JPanel(new BorderLayout());
		JLabel label = new JLabel("Enter username:");
		JTextField userName = new JTextField(20);

		JButton b = new JButton("Click Here");
		b.setPreferredSize(new Dimension(40, 0));
		b.setBounds(50, 100, 95, 30);

		ImageIcon imageIcon = new ImageIcon("src//resources//dice1.gif");
		JLabel picLabel = new JLabel();
		picLabel.setIcon(imageIcon);

		newPanel.add(b, BorderLayout.NORTH);
		newPanel.add(picLabel, BorderLayout.SOUTH);

		add(newPanel, BorderLayout.CENTER);

		setTitle("Snakes and Ladders");
		renderer = new Renderer();
		add(renderer, BorderLayout.CENTER);
		addMouseListener(new MouseEvent());

		// setLayout(new FlowLayout());

		setResizable(false);
		setSize(1200, 700);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);

	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
	}

	class Renderer extends JPanel {

		private int blockWidth = 20;
		// private int mapSize;

		public Renderer() {
			JLabel background = new JLabel(new ImageIcon("src//resources//board.jpg"));
			add(background);
			setDoubleBuffered(true);
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			paintPlayer(g);
		}

		private void paintPlayer(Graphics g) {
			System.out.println("TEST");
			g.setColor(Color.BLACK);
			g.fillOval(295, 565, 25, 25);
			g.setColor(Color.BLUE);
			g.fillOval(295, 565, 25, 25);
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