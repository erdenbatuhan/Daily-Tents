/*
 * Project   : DailyTents
 * Class     : Tree.java
 * Developer : Batuhan Erden
 */

public class Tree extends GameObject {

	protected Tree(int x, int y) {
		super(x, y, "Tree");
	}

	@Override
	protected boolean isInsertable() {
		int randomInt = DailyTents.RANDOM.nextInt(4);
		int previousX = x;
		int previousY = y;

		chooseRandomCoordinates(randomInt);

		int loopCount = 0;
		while (isLocationFull(x, y) && ++loopCount < 4) {
			x = previousX;
			y = previousY;

			chooseRandomCoordinates(randomInt++ % 4);
		}
		
		return true;
	}

	private void chooseRandomCoordinates(int randomInt) {
		if (randomInt == 0) {
			setCoordinates(x - 1, y);
		} else if (randomInt == 1) {
			setCoordinates(x + 1, y);
		} else if (randomInt == 2) {
			setCoordinates(x, y - 1);
		} else if (randomInt == 3) {
			setCoordinates(x, y + 1);
		}
	}

	private void setCoordinates(int newX, int newY) {
		if (isInsideBoard(newX, newY)) {
			x = newX;
			y = newY;
		}
	}
}