package game;

import java.util.ArrayList;
import java.util.List;

import gameUI.ConsoleUI;

public class Main {

	public static void main(String[] args) {

		List<Element> test2 = new ArrayList<>();
		Element test = new Player("R");
		test2.add(test);

		System.out.println(test2.contains(test));

	}
}