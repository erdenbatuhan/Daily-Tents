/*
 * Project   : DailyTents
 * Class     : Grass.java
 * Developer : Batuhan Erden
 */

public class Grass extends GameObject {

	protected Grass(int x, int y) {
		super(x, y, "Grass");
	}

	@Override
	protected boolean isInsertable() {
		return true;
	}
}

// Test test test 1234