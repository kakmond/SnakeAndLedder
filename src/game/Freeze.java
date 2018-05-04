package game;

public class Freeze implements Element {

	@Override
	public int getCommandID() {
		return Game.FREEZE_COMMAND;
	}

}
