/*
 * Project   : DailyTents
 * Class     : Board.java
 * Developer : Batuhan Erden
 */

import java.util.ArrayList;

public class Board {

	private static final int[] ACCEPTABLE_DIMENSIONS = { 8, 12, 16, 20 };
	private static final int[] TREE_COUNT_FOR_DIMENSIONS = { 12, 28, 51, 80 };
	protected static int[] tentCountForRows, tentCountForColumns;
	protected static int dimension, treeCount, insertedTentCount;
	protected static boolean wasObjectInserted = false;
	protected ArrayList<Tent> tents = new ArrayList<Tent>();
	protected GameObject[][] board;

	protected Board(int dimension) {
		Board.dimension = dimension;
		createNewMap();
		setTreeCount();
	}

	protected void createNewMap() {
		tents.clear();
		board = new GameObject[dimension][dimension];
		fillMapWithGrasses();
	}

	private void fillMapWithGrasses() {
		for (int row = 0; row < dimension; row++) {
			for (int column = 0; column < dimension; column++) {
				board[row][column] = new Grass(row, column);
			}
		}
	}

	private void setTreeCount() {
		for (int i = 0; i < ACCEPTABLE_DIMENSIONS.length; i++) {
			if (dimension == ACCEPTABLE_DIMENSIONS[i]) {
				treeCount = TREE_COUNT_FOR_DIMENSIONS[i];
				break;
			}
		}
	}

	protected static boolean isDimensionAcceptable(int dimension) {
		for (int i = 0; i < ACCEPTABLE_DIMENSIONS.length; i++) {
			if (dimension == ACCEPTABLE_DIMENSIONS[i])
				return true;
		}

		return false;
	}

	protected void insert(GameObject gameObject) {
		if (gameObject.isInsertable()) {
			int x = gameObject.x;
			int y = gameObject.y;

			board[x][y] = gameObject;
			wasObjectInserted = true;
		}
	}

	protected void insertTo(GameObject gameObject, int x, int y) {
		board[x][y] = gameObject;
		wasObjectInserted = true;
	}

	protected void freeBoardFromTents() {
		insertedTentCount = 0;
		
		for (int row = 0; row < dimension; row++) {
			for (int column = 0; column < dimension; column++) {
				if (board[row][column] instanceof Tent)
					board[row][column] = new Grass(row, column);
			}
		}
		
		makeEmptyRowsAndColumnsRed();
	}
	
	protected void makeEmptyRowsAndColumnsRed() {
		for (int row = 0; row < Board.dimension; row++) {
			for (int column = 0; column < Board.dimension; column++) {
				if (Board.tentCountForRows[row] == 0 || Board.tentCountForColumns[column] == 0)
					if (board[row][column] instanceof Grass)
						board[row][column].setName("Red");
			}
		}
	}

	protected void calculateTentCount() {
		tentCountForRows = new int[dimension];
		tentCountForColumns = new int[dimension];

		for (int row = 0; row < dimension; row++) {
			tentCountForRows[row] = getTentCountForRow(row);
		}
		for (int column = 0; column < dimension; column++) {
			tentCountForColumns[column] = getTentCountForColumn(column);
		}
	}

	private int getTentCountForRow(int row) {
		int tentCount = 0;
		for (int column = 0; column < dimension; column++) {
			if (board[row][column] instanceof Tent)
				tentCount++;
		}

		return tentCount;
	}

	private int getTentCountForColumn(int column) {
		int tentCount = 0;
		for (int row = 0; row < dimension; row++) {
			if (board[row][column] instanceof Tent)
				tentCount++;
		}

		return tentCount;
	}
}