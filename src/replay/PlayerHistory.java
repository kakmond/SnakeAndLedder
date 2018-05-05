package replay;

import game.Player;

public class PlayerHistory {

	private Player player;
	private int diceValue;

	public PlayerHistory(Player player, int diceValue) {
		this.player = player;
		this.diceValue = diceValue;
	}

	public Player getPlayer() {
		return this.player;
	}

	public int getDiceValue() {
		return this.diceValue;
	}

}
