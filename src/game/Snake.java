package game;

public class Snake implements Element {

	private Square tail;
	private Square head;

	public Snake(Square head, Square tail) {
		this.head = head;
		this.tail = tail;
	}

	public Square getHead() {
		return head;
	}

	public Square getTail() {
		return tail;
	}

	@Override
	public int getCommandID() {
		return Game.SNAKE_COMMAND;
	}

}
