package game;

import java.util.*;
import java.util.HashMap;
import java.util.Map;

public class Replay {

	private static Replay replayObj;
	private List<PlayerHistory> history;

	private Replay() {
		history = new ArrayList<>();
	}

	public static Replay getInstance() {
		if (replayObj == null) {
			replayObj = new Replay();
		}
		return replayObj;
	}

	public void addAction(Player p, int steps) {
		history.add(new PlayerHistory(p, steps));
	}

	public List<PlayerHistory> getHistory() {
		return history;
	}

}
