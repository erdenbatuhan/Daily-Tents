/*
 * Project   : DailyTents
 * Class     : Tent.java
 * Developer : Batuhan Erden
 */

public class Tent extends GameObject {

	protected Tent(int x, int y) {
		super(x, y, "Tent");
	}

	@Override
	protected boolean isInsertable() {
		return !isLocationFull(x, y) && !isAdjacentToTent();
	}
}