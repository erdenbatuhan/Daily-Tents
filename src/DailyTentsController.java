/*
 * Project   : DailyTents
 * Class     : DailyTentsController.java
 * Developer : Batuhan Erden
 */

import javax.swing.*;
import java.net.*;
import java.awt.Desktop;
import java.io.IOException;

public class DailyTentsController {
	
	private static final String HELP_PAGE = "https://www.brainbashers.com/tentshelp.asp";
	private DailyTentsModel model;
	private DailyTentsView view;

	protected DailyTentsController(JFrame mainMenuFrame) {
		model = new DailyTentsModel(0);
		view = new DailyTentsView(mainMenuFrame, this);
	}

	protected void processButton(JButton buttonClicked, JButton[][] buttons) {
		if (buttonClicked.equals(DailyTentsView.START_BUTTON))
			chooseDimension();
		else if (buttonClicked.equals(DailyTentsView.MENU_BUTTON))
			view.showInGameMenu(model.inGameMenuText);
		else if (buttonClicked.equals(DailyTentsView.EXIT_BUTTON))
			System.exit(0);
		else if (buttonClicked.getName().equals("Grass") || buttonClicked.getName().equals("Red"))
			model.insertTentToPanel(buttonClicked, buttons);
		else if (buttonClicked.getName().equals("Tent"))
			model.deleteTentFromPanel(buttonClicked, buttons);

		updateView();
	}

	protected void handleInGameMenuAction(String chosenAction) {
		if (chosenAction == null)
			return;
		else if (chosenAction.equals("Open help page"))
			openHelpPage();
		else if (chosenAction.equals("Start a new game"))
			chooseDimension();
		else if (chosenAction.equals("See the solution"))
			view.showSolution(model.solutionBoard);
		else if (chosenAction.equals("Restart the game"))
			model.gameBoard.freeBoardFromTents();
		else
			System.exit(0);

		updateView();
	}
	
	private void openHelpPage() {
		try {
			Desktop.getDesktop().browse(new URI(HELP_PAGE));
		} catch (IOException | URISyntaxException e) {
			System.err.println("Cause = " + e.getCause());
		}
	}

	private void chooseDimension() {
		String chosenDimension = view.getDimensionFromUser();

		if (chosenDimension != null) {
			int dimension = Integer.parseInt(chosenDimension);
			model = new DailyTentsModel(dimension);

			model.initializeBoard();
			view.getMainMenuFrame().setVisible(false);
		}
	}

	protected void updateView() {
		view.updateGameScene(model.gameBoard);
		
		if (model.isGameFinished()) {
			view.showInGameMenu(model.gameEndedText);
		}
	}
}