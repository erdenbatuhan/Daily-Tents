/*
 * Project   : DailyTents
 * Class     : GameObject.java
 * Developer : Batuhan Erden
 */

import javax.swing.ImageIcon;

public abstract class GameObject {
	
	protected int x, y;
	protected String name;
	protected ImageIcon icon;

	protected GameObject(int x, int y, String name) {
		this.x = x;
		this.y = y;
		this.name = name;

		this.icon = new ImageIcon("img/" + name + ".png");
	}

	protected abstract boolean isInsertable();

	protected boolean isLocationFull(int x, int y) {
		return !(DailyTentsModel.currentBoard.board[x][y] instanceof Grass);
	}

	protected boolean isAdjacentToTent() {
		for (int row = x - 1; row <= x + 1; row++) {
			for (int column = y - 1; column <= y + 1; column++) {
				if (isInsideBoard(row, column) && doesLocationContainTent(row, column))
					return true;
			}
		}
		
		return false;
	}

	protected boolean isInsideBoard(int x, int y) {
		boolean isInsideBoardHorizontally = (0 <= x) && (x < Board.dimension);
		boolean isInsideBoardVertically   = (0 <= y) && (y < Board.dimension);

		return isInsideBoardHorizontally && isInsideBoardVertically;
	}

	protected boolean doesLocationContainTent(int x, int y) {
		return DailyTentsModel.currentBoard.board[x][y] instanceof Tent;
	}
	
	protected void setName(String name) {
		this.name = name;
		this.icon = new ImageIcon("img/" + name + ".png");
	}
}