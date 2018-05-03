package game;

public class Snake implements Element {

	private Square headSq;
	private Square tailSq;
	
	public Snake() {
		
	}
	
	public Snake( Square headSq , Square tailSq ) {
		this.headSq = headSq;
		this.tailSq = tailSq;
	}
	
	public Square getHeadSq() {
		return headSq;
	}
	
	public Square getTailSq() {
		return tailSq;
	}
	
	@Override
	public void performAction() {
		// If player stops in headSq , He will move to tailSq.
	}

}
