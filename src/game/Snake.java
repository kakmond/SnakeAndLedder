package game;

public class Snake implements Element {

	private int tail;
	private int head;

	public Snake(int head, int tail) {
		this.head = head;
		this.tail = tail;
	}

	public int getHead() {
		return head;
	}

	public int getTail() {
		return tail;
	}

	@Override
	public int actionCommand() {
		return Game.SNAKE_COMMAND;
	}

}
