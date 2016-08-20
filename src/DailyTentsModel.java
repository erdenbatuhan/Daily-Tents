/*
 * Project   : DailyTents
 * Class     : DailyTentsModel.java
 * Developer : Batuhan Erden
 */

import javax.swing.JButton;

public class DailyTentsModel {

	protected static Board currentBoard;
	protected Board gameBoard, solutionBoard;
	protected String inGameMenuText = "Choose an action..";
	protected String gameEndedText = null;

	protected DailyTentsModel(int dimension) {
		if (dimension != 0) {
			gameBoard = new Board(dimension);
			solutionBoard = new Board(dimension);
		}
	}

	protected void initializeBoard() {
		Board.insertedTentCount = 0;

		currentBoard = solutionBoard;
		initializeTents();
		initializeTrees();
		currentBoard = gameBoard;
		
		gameBoard.makeEmptyRowsAndColumnsRed();
		solutionBoard.makeEmptyRowsAndColumnsRed();
	}

	private void initializeTents() {
		Tent tent = null;

		for (int i = 0; i < Board.treeCount; i++) {
			Board.wasObjectInserted = false;
			int loopCount = 0;

			while (!Board.wasObjectInserted) {
				if (loopCount++ > 50) {
					// If something goes wrong, code enters here!
					i = 0;
					solutionBoard.createNewMap();
				}
				int randomX = DailyTents.RANDOM.nextInt(Board.dimension);
				int randomY = DailyTents.RANDOM.nextInt(Board.dimension);

				tent = new Tent(randomX, randomY);
				solutionBoard.insert(tent);
			}
			solutionBoard.tents.add(tent);
		}
		solutionBoard.calculateTentCount();
	}

	private void initializeTrees() {
		Tree tree = null;

		for (int i = 0; i < Board.treeCount; i++) {
			int tmpX = solutionBoard.tents.get(i).x;
			int tmpY = solutionBoard.tents.get(i).y;

			tree = new Tree(tmpX, tmpY);
			solutionBoard.insert(tree);
			
			tmpX = tree.x;
			tmpY = tree.y;
			gameBoard.insertTo(tree, tree.x, tree.y);
		}
	}

	protected void insertTentToPanel(JButton button, JButton[][] buttons) {
		int x = getCoordinatesOfButton(button, buttons, "x");
		int y = getCoordinatesOfButton(button, buttons, "y");
		Tent tent = new Tent(x, y);

		gameBoard.insertTo(tent, x, y);
		Board.insertedTentCount++;
	}
	
	protected void deleteTentFromPanel(JButton button, JButton[][] buttons) {
		int x = getCoordinatesOfButton(button, buttons, "x");
		int y = getCoordinatesOfButton(button, buttons, "y");
		Grass grass = new Grass(x, y);

		gameBoard.insertTo(grass, x, y);
		Board.insertedTentCount--;
	}

	private int getCoordinatesOfButton(JButton button, JButton[][] buttons, String chosenCoordinate) {
		for (int row = 0; row < Board.dimension; row++) {
			for (int column = 0; column < Board.dimension; column++) {
				if (button.equals(buttons[row][column])) {
					if (chosenCoordinate.equals("x"))
						return row;
					else
						return column;
				}
			}
		}

		return -1;
	}

	protected boolean isGameFinished() {
		if (Board.dimension == 0) {
			return false;
		} else if (isSolutionFound()) {
			gameEndedText = "CONGRATULATIONS, YOU'VE WON!!";
			return true;
		} else if (isMapFull()) {
			gameEndedText = "UNFORTUNATELY, YOU'VE LOST!! The number of Tents = The number of Trees & Your board is wrong!!";

			return true;
		}

		return false;
	}

	protected boolean isSolutionFound() {
		for (int row = 0; row < Board.dimension; row++) {
			for (int column = 0; column < Board.dimension; column++) {
				GameObject gameObject1 = gameBoard.board[row][column];
				GameObject gameObject2 = solutionBoard.board[row][column];

				if (gameObject1.name != gameObject2.name) {
					return false;
				}
			}
		}

		return true;
	}

	private boolean isMapFull() {
		return Board.insertedTentCount == Board.treeCount;
	}
}