package game;

public class Backward implements Element {

	private Square sq;
	
	public Backward() {
		
	}
	
	public Backward( Square sq ) {
		this.sq = sq;
	}
	
	public Square getBackwardSquare() {
		return sq;
	}
	
	@Override
	public int getCommandID() {
		return Game.BACKWARD_COMMAND;
	}

}
