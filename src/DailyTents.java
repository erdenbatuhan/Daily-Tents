/*
 * Project   : DailyTents
 * Class     : DailyTents.java
 * Developer : Batuhan Erden
 */

import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class DailyTents {

	protected static final Random RANDOM = new Random();
	private static final int FIXED_FRAME_SIZE = 300;

	public static void main(String[] args) {
		new DailyTents();
	}

	protected DailyTents() {
		JFrame mainMenuFrame = new JFrame("Daily Tents");
		DailyTentsController controller = new DailyTentsController(mainMenuFrame);

		mainMenuFrame.getContentPane().setBackground(Color.CYAN);
		mainMenuFrame.setSize(FIXED_FRAME_SIZE, FIXED_FRAME_SIZE);
		mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainMenuFrame.setLayout(new GridBagLayout());

		controller.updateView();

		mainMenuFrame.setResizable(false);
		mainMenuFrame.setVisible(true);
	}
}