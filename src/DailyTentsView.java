/*
 * Project   : DailyTents
 * Class     : DailyTentsView.java
 * Developer : Batuhan Erden
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DailyTentsView {

	protected static final JButton START_BUTTON = new JButton("START THE GAME");
	protected static final JButton EXIT_BUTTON = new JButton("EXIT THE GAME");
	protected static final JButton MENU_BUTTON = new JButton(new ImageIcon("img/MenuIcon.png"));
	protected static final int CELL_SIZE = 35;
	private DailyTentsController controller;
	private JFrame mainMenuFrame, gameFrame, solutionFrame;
	private JPanel gamePanel, solutionPanel;
	private JButton[][] buttons;

	protected DailyTentsView(JFrame mainMenuFrame, final DailyTentsController controller) {
		this.mainMenuFrame = mainMenuFrame;
		this.controller = controller;

		gameFrame = new JFrame("Daily Tents - Game Scene");
		gamePanel = new JPanel();
		solutionFrame = new JFrame();
		solutionPanel = new JPanel();

		setButtonProperties();
		mainMenuFrame.add(START_BUTTON);
		mainMenuFrame.add(EXIT_BUTTON);
	}

	private void setButtonProperties() {
		MENU_BUTTON.setBorderPainted(false);

		START_BUTTON.addActionListener(new ButtonProcessor());
		EXIT_BUTTON.addActionListener(new ButtonProcessor());
		MENU_BUTTON.addActionListener(new ButtonProcessor());
	}

	protected void updateGameScene(Board currentBoard) {
		gameFrame.remove(gamePanel); // Removes the old panel from the frame.
		gamePanel = new JPanel();
		buttons = new JButton[Board.dimension][Board.dimension];

		setFrameProperties(gameFrame);
		setPanelProperties(gamePanel);

		drawBoard(currentBoard, gamePanel, false);
		gameFrame.add(gamePanel);

		gameFrame.setResizable(false);
		gameFrame.setVisible(true);
	}

	protected void showSolution(Board solutionBoard) {
		solutionFrame.setVisible(false); // Makes the old solution invisible.
		solutionFrame = new JFrame("Daily Tents - Solution");
		solutionPanel = new JPanel();

		solutionFrame.setLocation(gameFrame.getWidth() + CELL_SIZE, 0);
		setFrameProperties(solutionFrame);
		setPanelProperties(solutionPanel);

		drawBoard(solutionBoard, solutionPanel, true);
		solutionFrame.add(solutionPanel);

		solutionFrame.setResizable(false);
		solutionFrame.setVisible(true);
	}

	private void setFrameProperties(JFrame frame) {
		int frameSize = (Board.dimension + 1) * CELL_SIZE;

		frame.setSize(frameSize, frameSize);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void setPanelProperties(JPanel panel) {
		panel.setBackground(Color.GREEN);
		panel.setLayout(new GridLayout((Board.dimension + 1), (Board.dimension + 1)));
	}

	private void drawBoard(Board currentBoard, JPanel panel, boolean isSolution) {
		// '-1' represents the frame part of the board
		for (int row = -1; row < Board.dimension; row++) {
			for (int column = -1; column < Board.dimension; column++) {
				if (row == -1 && column == -1) {
					if (!isSolution) {
						panel.add(MENU_BUTTON);
					} else {
						panel.add(new JLabel());
					}
				} else if (row == -1 && column != -1) {
					panel.add(new JLabel("   " + Board.tentCountForColumns[column]));
				} else if (row != -1 && column == -1) {
					panel.add(new JLabel("   " + Board.tentCountForRows[row]));
				} else if (row != -1 && column != -1) {
					drawGameObject(currentBoard, panel, row, column, isSolution);
				}
			}
		}
	}

	private void drawGameObject(Board currentBoard, JPanel panel, int row, int column, boolean isSolution) {
		GameObject currentGameObject = currentBoard.board[row][column];

		if (!(currentGameObject instanceof Tree) && currentGameObject.name != "Red" && !isSolution) {
			JButton button = new JButton(currentGameObject.icon);
			button.setName(currentGameObject.name);
			button.addActionListener(new ButtonProcessor());
			button.setBorderPainted(false);

			buttons[row][column] = button;
			panel.add(button);
		} else {
			panel.add(new JLabel(currentGameObject.icon));
		}
	}

	protected void showInGameMenu(String text) {
		Object[] actions = { "Open help page", "Start a new game", "Restart the game", "See the solution", "Exit the game" };
		String chosenAction = (String) JOptionPane.showInputDialog(null, text + System.lineSeparator(), "In-Game Menu",
				JOptionPane.PLAIN_MESSAGE, null, actions, actions[0]);

		if (chosenAction != null)
			if (chosenAction.equals("Start a new game") || chosenAction.equals("Restart the game"))
				solutionFrame.setVisible(false);

		controller.handleInGameMenuAction(chosenAction);
	}

	protected String getDimensionFromUser() {
		Object[] dimensions = { "8", "12", "16", "20" };
		String chosenDimension = (String) JOptionPane.showInputDialog(null,
				"Choose the dimension of the board:" + System.lineSeparator(), "Dimension Chooser",
				JOptionPane.PLAIN_MESSAGE, null, dimensions, dimensions[0]);

		return chosenDimension;
	}

	protected JFrame getMainMenuFrame() {
		return mainMenuFrame;
	}

	private class ButtonProcessor implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() instanceof JButton) {
				JButton buttonClicked = (JButton) e.getSource();
				controller.processButton(buttonClicked, buttons);
			}
		}
	}
}