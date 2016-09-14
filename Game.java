package checkers;

public class Game {
	int[][] tiles = new int[8][8];
	int[] move = new int[4];
	boolean firstClick = true;
	int turn = 2;

	final int upOne = 1;
	final int downOne = -1;
	final int rightOne = 1;
	final int leftOne = -1;
	final int black = 2;
	final int red = 3;
	final int greySpace = 0;
	final int brownSpace = 1;

	public Game() {
		// Sets up the gameboard
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if ((x + y) % 2 == 0) {
					if (x < 3) {
						tiles[x][y] = red;
					} else if (x > 4) {
						tiles[x][y] = black;
					} else {
						tiles[x][y] = 1;
					}
				} else {
					tiles[x][y] = 0;
				}
			}
		}
	}

	public int getTile(int x, int y) {
		return tiles[x][y];
	}

	public String getTurn() {
		if (turn == black) {
			return ("Black");
		} else {
			return ("Red");
		}
	}

	// Sets the moves in an array. First two addresses are for the first
	// move's dimensions and the second two are for the second move's
	// dimensions
	public void setMove(int x, int y) {
		if (firstClick) {
			// Restarts player's turn if they clicked an empty tile on first
			// click.
			if (tiles[x][y] == greySpace || tiles[x][y] == brownSpace) {
				return;
			}
			move[0] = x;
			move[1] = y;
			firstClick = false;
		} else {
			// If player doesn't click a blank space to move to, it scraps the
			// move and player gets to restart turn.
			if (tiles[x][y] == red || tiles[x][y] == black) {
				move[0] = 0;
				move[1] = 1;
				firstClick = true;
				return;
			}

			// If correct, store second values
			move[2] = x;
			move[3] = y;
			firstClick = true;
			isMoveValid();
		}
	}

	// Checks if the button's pressed are a valid move
	public boolean isMoveValid() {
		if (turn == black && tiles[move[0]][move[1]] == black) {
			if (move[0] == (move[2] + upOne)) {
				if ((move[1] == (move[3] + leftOne)) || (move[1] == (move[3] + rightOne))) {
					turn();
					swapTiles(move[0], move[1], move[2], move[3]);
					return true;
				}
			}

		}
		if (turn == red && tiles[move[0]][move[1]] == red) {
			if (move[0] == (move[2] + downOne)) {
				if ((move[1] == (move[3] + leftOne)) || (move[1] == (move[3] + rightOne))) {
					turn();
					swapTiles(move[0], move[1], move[2], move[3]);
					return true;
				}
			}
		} else {
			return false;
		}
		return false;
	}

	// Every time this is called, it switches turns
	public void turn() {
		if (turn == black) {
			turn = red;
		} else {
			turn = black;
		}
	}

	// Swaps the two values of the tiles for a normal move.
	public void swapTiles(int firstX, int firstY, int secondX, int secondY) {
		int tempTileValue = tiles[firstX][firstY];

		tiles[firstX][firstY] = tiles[secondX][secondY];
		tiles[secondX][secondY] = tempTileValue;
	}

	// Use for debugging purposes only
	public void debugCheckRealBoard() {
		for (int x = 0; x < 8; x++) {
			System.out.println();
			for (int y = 0; y < 8; y++) {
				System.out.print(tiles[x][y]);
			}
		}
	}
}
