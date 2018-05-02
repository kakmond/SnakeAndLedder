package game;
import java.util.Random;

public class Die {

	public static final int MAX_FACE = 6;

	private int face;

	public Die() {
		face = 1;
	}

	public void roll() {
		face = 1 + new Random().nextInt(MAX_FACE);
	}

	public int getFace() {
		return face;
	}
}
