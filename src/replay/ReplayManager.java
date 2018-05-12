package replay;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReplayManager implements Iterable<Memento> {

	private List<Memento> histories = new ArrayList<>();

	public void addReplay(Memento history) {
		this.histories.add(history);
	}

	@Override
	public Iterator<Memento> iterator() {
		return new Iterator<Memento>() {

			private int index = 0;

			@Override
			public boolean hasNext() {
				return index < histories.size();
			}

			@Override
			public Memento next() {
				Memento m = histories.get(index);
				index++;
				return m;
			}
		};
	}

}
