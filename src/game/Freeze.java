package game;

public class Freeze implements Element {

	private Square sq;
	
	public Freeze() {
		
	}
	
	public Freeze( Square sq ) {
		this.sq = sq;
	}
	
	public Square getFreezeSquare() {
		return sq;
	}
	
	@Override
	public int getCommandID() {
		return Game.FREEZE_COMMAND;
	}

}
