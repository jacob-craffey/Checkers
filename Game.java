package checkers;

import java.io.File;
import java.util.Arrays;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

/**
 * Authors: Brandon Griggs, Jacob Craffey, Nick Frein 
 * Checkers game. This class holds the executable commands for carrying 
 * out a game of checkers. 
 */
public class Game {

	/** Tile that make up the game board.**/
	private static int[][] tiles = new int[8][8];
	/** Array that holds the player's clicks.**/
	private static int[] move = new int[4];
	/** boolean used to determine if user is on his first click.**/
	private boolean firstClick = true;
	/** boolean used to determine if user is done with their turn.**/
	private static boolean turnEnd = false;
	/** boolean used to determine if user is on their second jump.**/
	private static boolean secondJump = false;
	/** boolean used to determine if a player has won.**/
	private boolean won = false;
	/** Int to determine whose turn it is.**/
	private static int turn;


	private static final int downOne = 1;
	/** Int to indicate where a checker is going to move.**/
	private static final int upOne = -1;
	/** Int to indicate where a checker is going to move.**/
	private static final int rightOne = 1;
	/** Int to indicate where a checker is going to move.**/
	private static final int leftOne = -1;
	/** Int to indicate where black checkers are on the board.**/
	private static final int black = 2;
	/** Int to indicate where red checkers are on the board.**/
	private static final int red = 3;
	/** Int to indicate where black kings are on the board.**/
	private static final int blackKing = 4;
	/** Int to indicate where red kings are on the board.**/
	private static final int redKing = 5;
	/** Int to indicate where grey spaces are on the board.**/
	private static final int greySpace = 0;
	/** Int to indicate where brown spaces are on the board.**/
	private static final int brownSpace = 1;
	/** Dimension of the board.**/
	private static final int boardDimension = 8;

	/** String for holding the sound file for jumping.**/
	private String jumpSoundFile = "./src/checkers/sounds/jump.wav";
	/** String for holding the sound file for getting kinged.**/
	private static String kingSoundFile = "./src/checkers/sounds/king.wav";

	 /** 
	   * Sets up the game board. 
	  **/
	public Game() {
		turn = new Random().nextInt(2) + 2;

		for (int x = 0; x < boardDimension; x++) {
			for (int y = 0; y < boardDimension; y++) {
				if ((x + y) % 2 == 0) {
					if (x < 3) {
						tiles[x][y] = red;
					} else if (x > 4) {
						tiles[x][y] = black;
					} else {
						tiles[x][y] = brownSpace;
					}
				} else {
					tiles[x][y] = greySpace;
				}
			}
		}
	}

	  /** 
	   * Gets the array that is the game board. 
	   * 
	   * @return  The array that is the game board.
	   * @see int[][]
	   **/
	public static int[][] getArray() {
		return Arrays.copyOf(tiles, tiles.length);
	}

	 /** 
	   * method used to determine player's turn. 
	   * 
	   * @return  String of the color whose turn it is.
	   * @see  String
	   **/
	public final String getTurn() {
		if (turn == black) {
			return ("1");
		} else {
			return ("2");
		}
	}

	 /** 
	   * Sets the moves in an array. First two addresses are for the first
	   * move's dimensions and the second two are for the second move's
	   * dimensions.
	   * 
	   *  @param xtile  x-coordinate of the move
	   *  @param ytile  y-coordinate of the move
	   **/
	public final void setMove(final int xtile, final int ytile) {
		if (!turnEnd) {
			if (firstClick) {
				// Restarts player's turn if they clicked an empty tile on first
				// click.
				if (tiles[xtile][ytile] == greySpace 
						|| tiles[xtile][ytile] == brownSpace) {
					return;
				}
				// If correct, store the first click values
				move[0] = xtile;
				move[1] = ytile;
				firstClick = false;
			} else {
				// If player doesn't click a blank space to move to, it scraps
				// the
				// move and player gets to restart turn.
				if (tiles[xtile][ytile] == red 
						|| tiles[xtile][ytile] == black) {
					move[0] = 0;
					move[1] = 0;
					firstClick = true;
					return;
				}

				// If correct, store second values
				move[2] = xtile;
				move[3] = ytile;
				firstClick = true;
				// Goes through to determine what the player did that move
				if (!secondJump) {
					isMoveValid();
				}
				isJumpValid();
				if (!secondJump) {
					kingMove();
				}
			}
		}
	}

	/** 
 	 *   Checks the board for kings, looking for checkers of opposite colors
 	 *    on the top or bottom of the board.
	 **/
	public final void checkForKing() {
		if (turn == black) {
			for (int scanTopRow = 0; scanTopRow 
					< boardDimension; scanTopRow++) {
				if (tiles[0][scanTopRow] == black) {
					tiles[0][scanTopRow] = blackKing;
					playSound(kingSoundFile);
				}
			}
		} else if (turn == red) {
			for (int scanBottomRow = 0; scanBottomRow 
					< boardDimension; scanBottomRow++) {
				if (tiles[7][scanBottomRow] == red) {
					tiles[7][scanBottomRow] = redKing;
					playSound(kingSoundFile);
				}
			}
		}
	}

	  /** 
	   * Method for moving the king. King pieces can move diagonally
	   * in any direction. Tiles are swapped when a move is made. 
	   **/
	public final void kingMove() {
		if (turn == black && tiles[move[0]][move[1]] == blackKing) {
			if (move[0] == (move[2] + downOne) 
					|| move[0] == (move[2] + upOne)) {
				if ((move[1] == (move[3] + leftOne)) 
						|| (move[1] == (move[3] + rightOne))) {
					turnEnd = true;
					swapTiles(move[0], move[1], move[2], move[3]);
					turn();
				}
			}
		}
		if (turn == red && tiles[move[0]][move[1]] == redKing) {
			if (move[0] == (move[2] + downOne) 
					|| move[0] == (move[2] + upOne)) {
				if ((move[1] == (move[3] + leftOne)) 
						|| (move[1] == (move[3] + rightOne))) {
					turnEnd = true;
					swapTiles(move[0], move[1], move[2], move[3]);
					turn();
				}
			}
		}
	}

	  /** 
	   * Checks if the player clicked on a tile that is diagonal from 
	   * their piece. 
	   * 
	   * @return  true or false for if it is a valid move.
	   * @see boolean
	   **/
	public final boolean isMoveValid() {
		// Checks to see if BLACK player clicks a BLACK checker
		if (turn == black && tiles[move[0]][move[1]] == black) {
			// Checks if BLACK player's second click 1 row above
			if (move[0] == (move[2] + downOne)) {
				// Checks to see if the BLACK player's second click is diagonal
				// of the first click
				if ((move[1] == (move[3] + leftOne)) 
						|| (move[1] == (move[3] + rightOne))) {
					turnEnd = true;
					swapTiles(move[0], move[1], move[2], move[3]);
					checkForKing();
					turn();
					return true;
				}
			}

		}
		// Checks to see if RED player clicks a RED checker
		if (turn == red && tiles[move[0]][move[1]] == red) {
			// Checks if RED player's second click 1 row down
			if (move[0] == (move[2] + upOne)) {
				// Checks to see if the RED player's second click is diagonal of
				// the first click
				if ((move[1] == (move[3] + leftOne)) 
						|| (move[1] == (move[3] + rightOne))) {
					turnEnd = true;
					swapTiles(move[0], move[1], move[2], move[3]);
					checkForKing();
					turn();
					return true;
				}
			}
		} else {
			return false;
		}
		return false;
	}

	  /** 
	   * Checks if the player clicks a tile that is two diagonal tiles away
	   * from their checker, it's empty, and has an enemy checker in 
	   * between. If this is true, the player can jump.
	   * 
	   * @return  true of false for if the player made a valid jump
	   * @see boolean 
	   **/
	public final boolean isJumpValid() {
		if (turn == black) {
			// up right
			if (move[0] > move[2] && move[1] < move[3]) {
				if (tiles[move[0] - 1][move[1] + 1] == red 
						|| tiles[move[0] - 1][move[1] + 1] == redKing) {
					if (move[0] == move[2] + 2 && move[1] == move[3] - 2 
							&& tiles[move[2]][move[3]] == brownSpace) {
						jump(rightOne, upOne);
					}
				}
			}
			// up left
			if (move[0] > move[2] && move[1] > move[3]) {
				if (tiles[move[0] - 1][move[1] - 1] == red 
						|| tiles[move[0] - 1][move[1] - 1] == redKing) {
					if (move[0] == move[2] + 2 && move[1] == move[3] + 2 
							&& tiles[move[2]][move[3]] == brownSpace) {
						jump(leftOne, upOne);
					}
				}
			}
			// down right
			if (tiles[move[0]][move[1]] == blackKing) {
				if (move[0] < move[2] && move[1] < move[3]) {
					if (tiles[move[0] + 1][move[1] + 1] == red 
							|| tiles[move[0] + 1][move[1] + 1] == redKing) {
						if (move[0] + 2 == move[2] && move[1] + 2 == move[3]) {
							jump(rightOne, downOne);
						}
					}
				}
			}
			// down left
			if (tiles[move[0]][move[1]] == blackKing) {
				if (move[0] < move[2] && move[1] > move[3]) {
					if (tiles[move[0] + 1][move[1] - 1] == red 
							|| tiles[move[0] + 1][move[1] - 1] == redKing) {
						if (move[0] + 2 == move[2] && move[1] - 2 == move[3] 
								&& tiles[move[2]][move[3]] == brownSpace) {
							jump(leftOne, downOne);
						}
					}
				}
			}
		}

		if (turn == red) {
			// up right
			if (tiles[move[0]][move[1]] == redKing) {
				if (move[0] > move[2] && move[1] < move[3]) {
					if (tiles[move[0] - 1][move[1] + 1] == black 
							|| tiles[move[0] - 1][move[1] + 1] == blackKing) {
						if (move[0] - 2 == move[2] && move[1] + 2 == move[3] 
								&& tiles[move[2]][move[3]] == brownSpace) {
							jump(rightOne, upOne);
						}
					}
				}
			}
			// up left
			if (tiles[move[0]][move[1]] == redKing) {
				if (move[0] > move[2] && move[1] > move[3]) {
					if (tiles[move[0] - 1][move[1] - 1] == black 
							|| tiles[move[0] - 1][move[1] - 1] == blackKing) {
						if (move[0] - 2 == move[2] && move[1] - 2 == move[3] 
								&& tiles[move[2]][move[3]] == brownSpace) {
							jump(leftOne, upOne);
						}
					}
				}
			}
			// down right
			if (move[0] < move[2] && move[1] < move[3]) {
				if (tiles[move[0] + 1][move[1] + 1] == black 
						|| tiles[move[0] + 1][move[1] + 1] == blackKing) {
					if (move[0] + 2 == move[2] && move[1] + 2 == move[3] 
							&& tiles[move[2]][move[3]] == brownSpace) {
						jump(rightOne, downOne);
					}
				}
			}
			// down left
			if (move[0] < move[2] && move[1] > move[3]) {
				if (tiles[move[0] + 1][move[1] - 1] == black 
						|| tiles[move[0] + 1][move[1] - 1] == blackKing) {
					if (move[0] + 2 == move[2] && move[1] - 2 == move[3] 
							&& tiles[move[2]][move[3]] == brownSpace) {
						jump(leftOne, downOne);
					}
				}
			}
		}
		return false;
	}

	  /** 
	   * Method for jumping checkers. If a jump is made, the tiles are 
	   * swapped and the enemy checker is removed. Also includes sound
	   * and checks if the jump cause a player to win the game. 
	   * 
	   * @param horizontal  The horizontal direction the player wants
	   * 					to jump, positive of negative
	   * @param vertical  The vertical direction the player wants 
	   * 					to jump, positive of negative
	   **/
	public final void jump(final int horizontal, final int vertical) {
		tiles[(move[0] + move[2]) / 2][(move[1] + move[3]) / 2] = brownSpace;
		swapTiles(move[0], move[1], move[2], move[3]);
		checkForKing();
		won = playerWon(tiles);
		isjumpavailable(tiles[move[2]][move[3]]);
		playSound(jumpSoundFile);
	}

	  /** 
	   *  Checks if player's checker can jump over another enemy checker.
	   *  The player has to keep jumping as long as another move is possible.
	   *  
	   *   @param color  the color of the player who is trying to jump
	   *   @return true or false for if the jump is valid
	   *   @see boolean
	   **/
	public final boolean isjumpavailable(final int color) {
		if (color == black || color == blackKing) {
			try {
				if (tiles[move[2] - 1][move[3] + 1] == red 
						|| tiles[move[2] - 1][move[3] + 1] == redKing) {
					if (tiles[move[2] - 2][move[3] + 2] == brownSpace) {
						secondJump = true;
						return true;
					}
				}
			} catch (ArrayIndexOutOfBoundsException outOfBounds) {
				// do nothing
			}
			try {
				if (tiles[move[2] - 1][move[3] - 1] == red 
						|| tiles[move[2] - 1][move[3] - 1] == redKing) {
					if (tiles[move[2] - 2][move[3] - 2] == brownSpace) {
						secondJump = true;
						return true;
					}
				}
			} catch (ArrayIndexOutOfBoundsException outOfBounds) {
				// do nothing
			}
		}
		if (color == blackKing) {
			try {
				if (tiles[move[2] + 1][move[3] + 1] == red 
						|| tiles[move[2] + 1][move[3] + 1] == redKing) {
					if (tiles[move[2] + 2][move[3] + 2] == brownSpace) {
						secondJump = true;
						return true;
					}
				}
			} catch (ArrayIndexOutOfBoundsException outOfBounds) {
				// do nothing
			}
			try {
				if (tiles[move[2] + 1][move[3] - 1] == red 
						|| tiles[move[2] + 1][move[3] - 1] == redKing) {
					if (tiles[move[2] + 2][move[3] - 2] == brownSpace) {
						secondJump = true;
						return true;
					}
				}
			} catch (ArrayIndexOutOfBoundsException outOfBounds) {
				// do nothing
			}
		}

		if (color == red || color == redKing) {
			try {
				if (tiles[move[2] + 1][move[3] + 1] == black 
						|| tiles[move[2] + 1][move[3] + 1] == blackKing) {
					if (tiles[move[2] + 2][move[3] + 2] == brownSpace) {
						secondJump = true;
						return true;
					}
				}
			} catch (ArrayIndexOutOfBoundsException outOfBounds) {
				// do nothing
			}
			try {
				if (tiles[move[2] + 1][move[3] - 1] == black 
						|| tiles[move[2] + 1][move[3] - 1] == blackKing) {
					if (tiles[move[2] + 2][move[3] - 2] == brownSpace) {
						secondJump = true;
						return true;
					}
				}
			} catch (ArrayIndexOutOfBoundsException outOfBounds) {
				// do nothing
			}
		}
		if (color == redKing) {
			try {
				if (tiles[move[2] - 1][move[3] + 1] == black 
						|| tiles[move[2] - 1][move[3] + 1] == blackKing) {
					if (tiles[move[2] - 2][move[3] + 2] == brownSpace) {
						secondJump = true;
						return true;
					}
				}
			} catch (ArrayIndexOutOfBoundsException outOfBounds) {
				// do nothing
			}

			try {
				if (tiles[move[2] - 1][move[3] - 1] == black 
						|| tiles[move[2] - 1][move[3] - 1] == blackKing) {
					if (tiles[move[2] - 2][move[3] - 2] == brownSpace) {
						secondJump = true;
						return true;
					}
				}
			} catch (ArrayIndexOutOfBoundsException outOfBounds) {
				// do nothing
			}
		}
		turn();
		return false;
	}

	  /**
	   * Method for switching turns. Every time this is called,
	   * it switches turns. 
	   **/
	public final void turn() {
		if (turn == black) {
			turnEnd = false;
			secondJump = false;
			turn = red;
		} else {
			turnEnd = false;
			secondJump = false;
			turn = black;
		}
	}

	  /** 
	   * Swaps the two values of the tiles for a normal move.
	   * 
	   *  @param firstX  The x value of the starting location
	   *  @param firstY  The y value of the starting location
	   *  @param secondX  The x value for the ending location
	   *  @param secondY  The y value for the 
	   **/
	public final void swapTiles(final int firstX,
			final int firstY, final int secondX,
			final int secondY) {
		int tempTileValue = tiles[firstX][firstY];

		tiles[firstX][firstY] = tiles[secondX][secondY];
		tiles[secondX][secondY] = tempTileValue;
	}

	/** Use for debugging purposes only. **/
	public final void debugCheckRealBoard() {
		for (int x = 0; x < 8; x++) {
			System.out.println();
			for (int y = 0; y < 8; y++) {
				System.out.print(tiles[x][y]);
			}
		}
	}

	  /** 
	   * Checks if either player has no checkers left. 
	   * Sends winner message to DisplayWindow().
	   * 
	   *  @param ptiles  the game board that will be checked for a winner
	   *  @return true or false depending on if a player has won
	   *  @see boolean
	   **/
	public final boolean playerWon(final int[][] ptiles) {
		// int noWinner = 0;
		int blackCheckers = 0;
		int redCheckers = 0;

		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if ((ptiles[x][y] == black) || ((ptiles[x][y] == blackKing))) {
					blackCheckers = 1; // At least 1 Black checker still exists
				}
				if ((ptiles[x][y] == red) || (ptiles[x][y] == redKing)) {
					redCheckers = 1; // At least 1 Red checker still exists
				}
			}
		}

		if ((blackCheckers == 1) && (redCheckers == 0)) {
			DisplayWinner.display("Player 1 Wins!");
			return true;
		} else if ((blackCheckers == 0) && (redCheckers == 1)) {
			DisplayWinner.display("Player 2 Wins!");
			return true;
		}
		return false;
	}

	  /** 
	   * Looks for sound file. If found, plays WAV file. 
	   * 
	   * @param fileName  The name of the WAV file you want to open
	   **/
	public final void playSound(final String fileName) {
		try {
			File soundFile = new File(fileName);
			AudioInputStream audioStream = 
					AudioSystem.getAudioInputStream(soundFile);
			AudioFormat audioFormat = audioStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
			Clip clip = (Clip) AudioSystem.getLine(info);
			clip.open(audioStream);
			clip.start();
		} catch (Exception cantFindFile) {
			System.out.println("can't find WAV file");
		}
	}
	
	/**
	 * Sets the tile at a given location to a given value.
	 * 
	 * @param xTile  x-coordinate of tile wanting to be set
	 * @param yTile  y-coordinate of tile wanting to be set
	 * @param value  The value that is going to be set at tile location.
	 */
	public static void setTile(final int xTile,
			final int yTile, final int value) {
		tiles[xTile][yTile] = value;
	}
	

	  /** 
	   * Gets tiles x and y's contents.
	   * 
	   * @param x  x-coordinate of the tile
	   * @param y  y-coordinate of the tile
	   * @return  The tile indicated by the coordinates
	   * @see int
	   **/
	public static int getTile(final int x, final int y) {
		// TODO Auto-generated method stub
		return tiles[x][y];
	}
	
	
}