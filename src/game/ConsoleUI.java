package game;

import java.util.Scanner;

public class ConsoleUI {

	private Scanner scan = new Scanner(System.in);

	public void start() {
		System.out.print("How many player? ");
		int numPlayer = scan.nextInt();
		Game game = new Game(numPlayer);
		while (!game.isEnd()) {
			System.out.println("-----------------");
			System.out.println(game.currentPlayerName());
			System.out.println("Position : " + game.currentPlayerPosition());
			System.out.println("Please hit enter to roll a die :D");

			scan.nextLine();

			int face = game.currentPlayerRollDice();
			System.out.println("Die face = " + face);
			game.currentPlayerMove(face);
			System.out.println("Position : " + game.currentPlayerPosition());
			if (game.currentPlayerWin()) {
				System.out.println(game.currentPlayerName() + " win");
				game.end();
			} else {
				game.switchPlayer();
			}
		}
	}

}
