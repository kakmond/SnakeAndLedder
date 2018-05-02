package gameUI;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class SnakeUI extends JFrame implements Observer {

	public SnakeUI() {

		setLayout(new BorderLayout());
		setTitle("Snakes and Ladders");
		/* Set board */
		JLabel background = new JLabel(new ImageIcon("src\\resources\\board.jpg"));
		add(background, BorderLayout.CENTER);

		setSize(1200, 700);
		setVisible(true);
	}

	public static void main(String args[]) {
		new GameUI();
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
	}

	class Renderer extends JPanel {

		private int mapSize;

		public Renderer() {
			setPreferredSize(new Dimension(mapSize, mapSize));
			setDoubleBuffered(true);
		}
	}

}