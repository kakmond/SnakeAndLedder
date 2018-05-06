package replay;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReplayManager implements Iterator<Memento> {

	private List<Memento> histories = new ArrayList<>();
	private int index = 0;

	public void addReplay(Memento history) {
		this.histories.add(history);
	}

	public int getDiceHistory(int index) {
		return histories.get(index).getDiceValue();
	}

	public void resetIndex() {
		this.index = 0;
	}

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

}
