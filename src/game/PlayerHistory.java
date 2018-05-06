package game;

public class PlayerHistory {
	
	private Player p;
	private int steps;
	
	public PlayerHistory( Player p , int steps ) {
		this.p = p;
		this.steps = steps;
	}
	
	public Player getPlayer() {
		return p;
	}
	
	public int getSteps() {
		return steps;
	}
	
}