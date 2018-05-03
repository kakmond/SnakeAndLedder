package game;

public class Ladder implements Element {

	private Square topSq;
	private Square bottomSq;
	
	public Ladder() {
		
	}
	
	public Ladder( Square topSq , Square bottomSq ) {
		this.topSq = topSq;
		this.bottomSq = bottomSq;
	}
	
	public Square getTopSq() {
		return topSq;
	}
	
	public Square getBottomSq() {
		return bottomSq;
	}
	
	@Override
	public void performAction() {
		// If player stops in bottomSq , He will move to topSq.
	}

}
