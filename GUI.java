//GUI

package checkers;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI extends JPanel {
	private JButton[][] tiles;
	private ImageIcon[] imageIcon;
	private Game game;
	private JLabel currentTurnLabel;
	private JLabel currentTurn = new JLabel();

	public GUI() {

		game = new Game();

		// Sets up necessary elements for interface
		tiles = new JButton[8][8];
		JFrame frame = new JFrame("Checkers");
		JPanel panel = new JPanel();
		JPanel currentTurnPanel = new JPanel();
		ButtonListener listener = new ButtonListener();
		currentTurnLabel = new JLabel("Current Turn: ");
		currentTurn = new JLabel(game.getTurn());
		GridLayout gridLayout = new GridLayout(8, 8);
		panel.setLayout(gridLayout);

		// assigns colored tiles to button's value
		imageIcon = new ImageIcon[6];
		for (int tileValue = 0; tileValue < 6; tileValue++) {
			imageIcon[tileValue] = new ImageIcon(
					"C:/Users/Jacob/workspace/Checkers/src/checkers/tiles/" + tileValue + ".jpg");
		}

		// nested loop to add the 2d array of buttons
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				tiles[x][y] = new JButton();
				tiles[x][y].setIcon(imageIcon[game.getTile(x, y)]);
				tiles[x][y].addActionListener(listener);
				panel.add(tiles[x][y]);

			}
		}

		// Adds the components to the current turn panel
		currentTurnPanel.add(currentTurnLabel);
		currentTurnPanel.add(currentTurn);

		// necessary frame properties
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 800);
		frame.add(panel);
		frame.add(currentTurnPanel, BorderLayout.NORTH);
		frame.setResizable(false);
	}

	public static void main(String[] args) {
		new GUI();
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {

			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					if (tiles[x][y] == event.getSource()) {
						game.setMove(x, y);
						reloadBoard();
						currentTurn.setText(game.getTurn());
					}
				}
			}
		}

		// Reloads the board UI after every click
		public void reloadBoard() {
			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					tiles[x][y].setIcon(imageIcon[game.getTile(x, y)]);
				}
			}
		}

	}
}