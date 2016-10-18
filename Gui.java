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
/**Suppress a useless warming.**/

@SuppressWarnings("serial")

/**
 * Authors: Brandon Griggs, Jacob Craffey, Nick Frein 
 * GUI for checkers game. Sets up the game board and visual
 * presentation of the game.
 **/
public class Gui extends JPanel {
  
  /** Integers. **/
  private final int threeNum = 3;
  
  /** Integers. **/
  private final int sixNum = 6;
  
  /** Integers. **/
  private final int eightNum = 8;
  /** Integers. **/
  private final int hundred = 100;
  
  /** Integers. **/
  private final int eightHundred = 800;

  /**2d array of buttons that represent the tiles of the game board.**/
  private JButton[][] tiles;
  
  /**Array of the images used in the game.**/
  private ImageIcon[] imageIcon;

  /**The game of checkers.**/
  private Game game;

  /**Label for displaying player of the current turn.**/
  private JLabel currentTurnLabel;

  /** Label for displaying the player currently in their turn.**/
  private JLabel currentTurn = new JLabel();

  /**Used for images in the game.**/
  private BufferedImage image;

  /**Used to draw over checkers so player can pick their color.**/
  private Graphics2D g2;

  /**Menu bar that holds the menu options.**/
  private JMenuBar menuBar;
  
  /**Menu options that contain either load and save or undo.**/
  private JMenu file;
  
  /**Menu options that contain either load and save or undo.**/  
  private JMenu undo;
  
  /**Menu items that go in the dropdown boxes.**/
  private JMenuItem menuSave;
  
  /**Menu items that go in the dropdown boxes.**/
  private JMenuItem menuLoad; 
  
  /**Menu items that go in the dropdown boxes.**/
  private JMenuItem menuUndo;

  /** Default if players don't choose their colors. **/
  public Gui() {
    change(Color.RED, Color.BLACK);
  }

  /**
  * Creates Window that displays checkers board.
  * 
  * @param color1  the color of player 1
  * @param color2  the color of player 2
  **/
  public Gui(final Color color1, final Color color2) {
    game = new Game();

    // Sets up necessary elements for interface
    tiles = new JButton[eightNum][eightNum];
    final JFrame frame = new JFrame("Checkers");
    JPanel panel = new JPanel();
    final JPanel currentTurnPanel = new JPanel();
    final ButtonListener listener = new ButtonListener();
    currentTurnLabel = new JLabel("Current Turn: Player");
    currentTurn = new JLabel(game.getTurn());
    GridLayout gridLayout = new GridLayout(eightNum, eightNum);
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
    imageIcon = new ImageIcon[sixNum];
    for (int tileValue = 0; tileValue < sixNum; tileValue++) {
      imageIcon[tileValue] = new ImageIcon(
          "./src/checkers/tiles/" + tileValue + ".jpg");
    }

    changeColors(2, color1); // changes checkers to match players' selected
    // colors
    changeColors(threeNum, color2);

    // nested loop to add the 2d array of buttons
    for (int xtile = 0; xtile < eightNum; xtile++) {
      for (int ytile = 0; ytile < eightNum; ytile++) {
        tiles[xtile][ytile] = new JButton();
        tiles[xtile][ytile].setIcon(imageIcon[Game.getTile(xtile, ytile)]);
        tiles[xtile][ytile].addActionListener(listener);
        panel.add(tiles[xtile][ytile]);
      }
    }

    // Adds the components to the current turn panel
    currentTurnPanel.add(currentTurnLabel);
    currentTurnPanel.add(currentTurn);

    // necessary frame properties
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(eightHundred, eightHundred);
    frame.add(panel);
    frame.add(currentTurnPanel, BorderLayout.NORTH);
    frame.setResizable(false);
  }

  /**
  * Helper method that changes the colors of the checkers on the
  * board by passing them as a parameter in the constructor.
  * 
  * @param col1 Color of player1
  * @param col2  Color of player2
  **/
  public static void change(final Color col1, final Color col2) {
    new Gui(col1, col2);
  }

  /**
  * Button listener that listens to the clicks on the game board and
  * sets moves accordingly. 
  **/
  private class ButtonListener implements ActionListener {

    /**
     * Listens for actions performed. 
    * @param event  The event that triggers the listener. 
    **/
    public void actionPerformed(final ActionEvent event) {
      for (int xtile = 0; xtile < eightNum; xtile++) {
        for (int ytile = 0; ytile < eightNum; ytile++) {
          if (tiles[xtile][ytile] == event.getSource()) {
            game.setMove(xtile, ytile);
            reloadBoard();
            currentTurn.setText(game.getTurn());
          }
        }
      }
      if (menuSave == event.getSource()) {
        SaveLoad.save();
      }

      if (menuLoad == event.getSource()) {
        SaveLoad.load();
        reloadBoard();
      }
    }

    /**
    *  Reloads the board UI after every click.
    **/
    public void reloadBoard() {
      for (int xtile = 0; xtile < eightNum; xtile++) {
        for (int ytile = 0; ytile < eightNum; ytile++) {
          tiles[xtile][ytile].setIcon(imageIcon[Game.getTile(xtile, ytile)]);
        }
      }
    }
  }

  /**
  * Grabs an image from a given filename.
  * 
  * @param filename  The name of the file you would like to open
  * @return  The image that was grabbed 
  * @see BufferedImage
  **/
  private BufferedImage getImage(final String filename) {
    try {
      InputStream input = getClass().getResourceAsStream(filename);
      return ImageIO.read(input);
    } catch (IOException noImage) {
      System.out.println("The image was not loaded.");
    }
    return null;
  }

  /**
  * Colored circles are placed over the existing tiles containing checkers
  * to match the player's selected colors.
  * 
  * @param playerNum  The number of the player whose 
  *     color you want to change.
  * @param col  The color the checkers will be switching too.
  **/
  public final void changeColors(final int playerNum, final Color col) {
    // change colors for tile png
    image = getImage("tiles/" + playerNum + ".jpg");
    g2 = image.createGraphics();
    g2.setColor(col);
    g2.fillOval(0, 0, hundred, hundred);
    imageIcon[playerNum] = new ImageIcon(image);
  }
}