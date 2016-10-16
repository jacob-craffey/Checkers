package checkers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Gui extends JPanel {
	private JButton[][] tiles;
	private ImageIcon[] imageIcon;
	private Game game;
	private JLabel currentTurnLabel;
	private JLabel currentTurn = new JLabel();
	private BufferedImage image;
	Graphics2D g2;
	JMenuBar menuBar;
	JMenu file, undo;
	JMenuItem menuSave, menuLoad, menuUndo;

	/** Default if players don't choose their colors. **/
	public Gui() {
		change(Color.RED, Color.BLACK);
	}

	/** Creates window that displays checkers board. **/
	public Gui(Color color1, Color color2) {
		game = new Game();

		// Sets up necessary elements for interface
		tiles = new JButton[8][8];
		final JFrame frame = new JFrame("Checkers");
		JPanel panel = new JPanel();
		final JPanel currentTurnPanel = new JPanel();
		ButtonListener listener = new ButtonListener();
		currentTurnLabel = new JLabel("Current Turn: Player");
		currentTurn = new JLabel(game.getTurn());
		GridLayout gridLayout = new GridLayout(8, 8);
		panel.setLayout(gridLayout);
		
		//Sets up the Jmenu
		menuBar = new JMenuBar();
		file = new JMenu("File");
		undo = new JMenu("Undo");
		
		menuSave = new JMenuItem("Save");
		menuLoad = new JMenuItem("Load");
		menuUndo = new JMenuItem("Undo");
		
		menuUndo.addActionListener(listener);
		menuLoad.addActionListener(listener);
		menuSave.addActionListener(listener);
		
		file.add(menuSave);
		file.add(menuLoad);
		undo.add(menuUndo);
		
		menuBar.add(file);
		menuBar.add(undo);
		
		frame.setJMenuBar(menuBar);

		
		// assigns colored tiles to button's value
		imageIcon = new ImageIcon[6];
		for (int tileValue = 0; tileValue < 6; tileValue++) {
			imageIcon[tileValue] = new ImageIcon(
					"C:/Users/jcraf_000/workspace/Checkers/src/checkers/tiles/" + tileValue + ".jpg");
		}

		changeColors(2, color1); // changes checkers to match players' selected
									// colors
		changeColors(3, color2);

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

	// takes players' selected colors and sets up board
	public static void change(Color col1, Color col2) {
		new Gui(col1, col2);
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
			if (menuSave == event.getSource()) {
				SaveLoad.Save();
			}
			
			if (menuLoad == event.getSource()){
				SaveLoad.Load();
				reloadBoard();
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

	private BufferedImage getImage(String filename) {
		try {
			InputStream input = getClass().getResourceAsStream(filename);
			return ImageIO.read(input);
		} catch (IOException noImage) {
			System.out.println("The image was not loaded.");
		}
		return null;
	}

	/** Colored circles are placed over the tiles containing checkers. **/
	public void changeColors(int playerNum, Color col) {
		// change colors for tile png
		image = getImage("tiles/" + playerNum + ".jpg");
		g2 = image.createGraphics();
		g2.setColor(col);
		g2.fillOval(0, 0, 100, 100);
		imageIcon[playerNum] = new ImageIcon(image);
	}
}