package game;

import java.awt.EventQueue;

import gameUI.SnakeGUI;

public class Main {

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
}