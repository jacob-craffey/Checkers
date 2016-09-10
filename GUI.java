package checkers;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JPanel {
	private JButton[][] tiles;
	private JFrame frame;
	private JPanel panel;

	public GUI() {

		Game game = new Game();
		// Sets up necessary elements for interface
		tiles = new JButton[10][10];
		JFrame frame = new JFrame("Checkers");
		JPanel panel = new JPanel();
		GridLayout gridLayout = new GridLayout(8, 8);
		panel.setLayout(gridLayout);

		// nested loop to add the 2d array of buttons
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				tiles[x][y] = new JButton();
				tiles[x][y].setText(Integer.toString(game.getTile(x, y)));
				panel.add(tiles[x][y]);

			}
		}

		// necessary frame properties
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 800);
		frame.add(panel);
	}

	public static void main(String[] args) {
		new GUI();
	}
}
