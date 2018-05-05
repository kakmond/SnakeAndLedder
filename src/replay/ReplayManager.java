package replay;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReplayManager implements Iterable<PlayerHistory> {

	private List<PlayerHistory> histories = new ArrayList<>();

	public void addReplay(PlayerHistory history) {
		this.histories.add(history);
	}

	@Override
	public Iterator<PlayerHistory> iterator() {
		return new Iterator<PlayerHistory>() {

			private int index = 0;

			@Override
			public boolean hasNext() {
				return index < histories.size();
			}

			@Override
			public PlayerHistory next() {
				return histories.get(index);
			}

		};
	}

}
