//game

package checkers;

public class Game {
	int[][] tiles = new int[8][8];
	int[] move = new int[4];
	boolean firstClick = true;
	boolean turnEnd = false;
	int turn = 2;

	final int JUMP_LEFT = -2;
	final int JUMP_RIGHT = 2;
	final int JUMP_UP = -2;
	final int JUMP_DOWN = 2;
	final int UP_ONE = 1;
	final int DOWN_ONE = -1;
	final int RIGHT_ONE = 1;
	final int LEFT_ONE = -1;
	final int BLACK = 2;
	final int RED = 3;
	final int BLACK_KING = 4;
	final int RED_KING = 5;
	final int GREY_SPACE = 0;
	final int BROWN_SPACE = 1;
	final int BOARD_DIMENSION = 8;

	public Game() {
		// Sets up the gameboard
		for (int x = 0; x < BOARD_DIMENSION; x++) {
			for (int y = 0; y < BOARD_DIMENSION; y++) {
				if ((x + y) % 2 == 0) {
					if (x < 3) {
						tiles[x][y] = RED;
					} else if (x > 4) {
						tiles[x][y] = BLACK;
					} else {
						tiles[x][y] = BROWN_SPACE;
					}
				} else {
					tiles[x][y] = GREY_SPACE;
				}
			}
		}
	}

	public int getTile(int x, int y) {
		return tiles[x][y];
	}

	public String getTurn() {
		if (turn == BLACK) {
			return ("BLACK");
		} else {
			return ("RED");
		}
	}

	// Sets the moves in an array. First two addresses are for the first
	// move's dimensions and the second two are for the second move's
	// dimensions
	public void setMove(int x, int y) {
		if (turnEnd == false) {
			if (firstClick) {
				// Restarts player's turn if they clicked an empty tile on first
				// click.
				if (tiles[x][y] == GREY_SPACE || tiles[x][y] == BROWN_SPACE) {
					return;
				}
				// If correct, store the first click values
				move[0] = x;
				move[1] = y;
				firstClick = false;
			} else {
				// If player doesn't click a blank space to move to, it scraps
				// the
				// move and player gets to restart turn.
				if (tiles[x][y] == RED || tiles[x][y] == BLACK) {
					move[0] = 0;
					move[1] = 0;
					firstClick = true;
					return;
				}

				// If correct, store second values
				move[2] = x;
				move[3] = y;
				firstClick = true;
				// Goes through to determine what the player did that move
				isMoveValid();
				isJumpValid();
				checkForKing();
				kingMove();
			}
		}
	}

	public void checkForKing() {
		if (turn == BLACK) {
			for (int scanTopRow = 0; scanTopRow < BOARD_DIMENSION; scanTopRow++) {
				if (tiles[0][scanTopRow] == BLACK) {
					tiles[0][scanTopRow] = BLACK_KING;
				}
			}
		} else if (turn == RED) {
			for (int scanBottomRow = 0; scanBottomRow < BOARD_DIMENSION; scanBottomRow++) {
				if (tiles[7][scanBottomRow] == RED) {
					tiles[7][scanBottomRow] = RED_KING;
				}
			}
		}
	}

	public void kingMove() {
		if (turn == BLACK && tiles[move[0]][move[1]] == BLACK_KING) {
			if (move[0] == (move[2] + UP_ONE) || move[0] == (move[2] + DOWN_ONE)) {
				if ((move[1] == (move[3] + LEFT_ONE)) || (move[1] == (move[3] + RIGHT_ONE))) {
					turnEnd = true;
					swapTiles(move[0], move[1], move[2], move[3]);
				}
			}
		}
		if (turn == RED && tiles[move[0]][move[1]] == RED_KING) {
			if (move[0] == (move[2] + UP_ONE) || move[0] == (move[2] + DOWN_ONE)) {
				if ((move[1] == (move[3] + LEFT_ONE)) || (move[1] == (move[3] + RIGHT_ONE))) {
					turnEnd = true;
					swapTiles(move[0], move[1], move[2], move[3]);
				}
			}
		}
	}

	// Checks if the button's pressed are a valid move
	public boolean isMoveValid() {
		// Checks to see if BLACK player clicks a BLACK checker
		if (turn == BLACK && tiles[move[0]][move[1]] == BLACK) {
			// Checks if BLACK player's second click 1 row above
			if (move[0] == (move[2] + UP_ONE)) {
				// Checks to see if the BLACK player's second click is diagonal
				// of the first click
				if ((move[1] == (move[3] + LEFT_ONE)) || (move[1] == (move[3] + RIGHT_ONE))) {
					turnEnd = true;
					swapTiles(move[0], move[1], move[2], move[3]);
					turn();
					return true;
				}
			}

		}
		// Checks to see if RED player clicks a RED checker
		if (turn == RED && tiles[move[0]][move[1]] == RED) {
			// Checks if RED player's second click 1 row down
			if (move[0] == (move[2] + DOWN_ONE)) {
				// Checks to see if the RED player's second click is diagonal of
				// the first click
				if ((move[1] == (move[3] + LEFT_ONE)) || (move[1] == (move[3] + RIGHT_ONE))) {
					turnEnd = true;
					swapTiles(move[0], move[1], move[2], move[3]);
					turn();
					return true;
				}
			}
		} else {
			return false;
		}
		return false;
	}

	public boolean isJumpValid() {
		if (turn == BLACK) {
			// up right
			if (move[0] > move[2] && move[1] < move[3]) {
				if (tiles[move[0] - 1][move[1] + 1] == RED || tiles[move[0] - 1][move[1] + 1] == RED_KING) {
					if (move[0] == move[2] + 2 && move[1] == move[3] - 2 && tiles[move[2]][move[3]] == BROWN_SPACE) {
						System.out.println("upright");
						jump("up", "right");
					}
				}
			}
			// up left
			if (move[0] > move[2] && move[1] > move[3]) {
				if (tiles[move[0] - 1][move[1] - 1] == RED || tiles[move[0] - 1][move[1] - 1] == RED_KING) {
					if (move[0] == move[2] + 2 && move[1] == move[3] + 2 && tiles[move[2]][move[3]] == BROWN_SPACE) {
						System.out.println("upleft");
						jump("up", "left");
					}
				}
			}
			// down right
			if (tiles[move[0]][move[1]] == BLACK_KING) {
				if (move[0] < move[2] && move[1] < move[3]) {
					if (tiles[move[0] + 1][move[1] + 1] == RED || tiles[move[0] + 1][move[1] + 1] == RED_KING) {
						if (move[0] + 2 == move[2] && move[1] + 2 == move[3]) {

							jump("down", "right");
						}
					}
				}
			}
			// down left
			if (tiles[move[0]][move[1]] == BLACK_KING) {
				if (move[0] < move[2] && move[1] > move[3]) {
					if (tiles[move[0] + 1][move[1] - 1] == RED || tiles[move[0] + 1][move[1] - 1] == RED_KING) {
						if (move[0] + 2 == move[2] && move[1] - 2 == move[3]
								&& tiles[move[2]][move[3]] == BROWN_SPACE) {
							jump("down", "left");
						}
					}
				}
			}
		}

		if (turn == RED) {
			// up right
			if (tiles[move[0]][move[1]] == RED_KING) {
				if (move[0] > move[2] && move[1] < move[3]) {
					if (tiles[move[0] - 1][move[1] + 1] == BLACK || tiles[move[0] - 1][move[1] + 1] == BLACK_KING) {
						if (move[0] - 2 == move[2] && move[1] + 2 == move[3]
								&& tiles[move[2]][move[3]] == BROWN_SPACE) {
							jump("up", "right");
						}
					}
				}
			}
			// up left
			if (tiles[move[0]][move[1]] == RED_KING) {
				if (move[0] > move[2] && move[1] > move[3]) {
					if (tiles[move[0] - 1][move[1] - 1] == BLACK || tiles[move[0] - 1][move[1] - 1] == BLACK_KING) {
						if (move[0] - 2 == move[2] && move[1] - 2 == move[3]
								&& tiles[move[2]][move[3]] == BROWN_SPACE) {
							jump("up", "left");
						}
					}
				}
			}
			// down right
			if (move[0] < move[2] && move[1] < move[3]) {
				if (tiles[move[0] + 1][move[1] + 1] == BLACK || tiles[move[0] + 1][move[1] + 1] == BLACK_KING) {
					if (move[0] + 2 == move[2] && move[1] + 2 == move[3] && tiles[move[2]][move[3]] == BROWN_SPACE) {

						jump("down", "right");
					}
				}
			}

			// down left
			if (move[0] < move[2] && move[1] > move[3]) {
				if (tiles[move[0] + 1][move[1] - 1] == BLACK || tiles[move[0] + 1][move[1] - 1] == BLACK_KING) {
					if (move[0] + 2 == move[2] && move[1] - 2 == move[3] && tiles[move[2]][move[3]] == BROWN_SPACE) {
						jump("down", "left");
					}
				}
			}
		}
		return false;
	}

	public void jump(String vertical, String horizontal) {
		if (turn == BLACK) {
			if (vertical == "up" && horizontal == "left") {
				tiles[move[0] - 1][move[1] - 1] = BROWN_SPACE;
				swapTiles(move[0], move[1], move[2], move[3]);
				isAnotherJumpPossible();
				return;
			}
			if (vertical == "up" && horizontal == "right") {
				tiles[move[0] - 1][move[1] + 1] = BROWN_SPACE;
				swapTiles(move[0], move[1], move[2], move[3]);
				isAnotherJumpPossible();
				return;
			}
			if (vertical == "down" && horizontal == "right") {
				tiles[move[0] + 1][move[1] + 1] = BROWN_SPACE;
				swapTiles(move[0], move[1], move[2], move[3]);
				isAnotherJumpPossible();
				return;
			}
			if (vertical == "down" && horizontal == "left") {
				tiles[move[0] + 1][move[1] - 1] = BROWN_SPACE;
				swapTiles(move[0], move[1], move[2], move[3]);
				isAnotherJumpPossible();
				return;
			}
		}
		if (turn == RED) {
			if (vertical == "up" && horizontal == "left") {
				tiles[move[0] - 1][move[1] - 1] = BROWN_SPACE;
				swapTiles(move[0], move[1], move[2], move[3]);
				isAnotherJumpPossible();
				return;
			}
			if (vertical == "up" && horizontal == "right") {
				tiles[move[0] - 1][move[1] + 1] = BROWN_SPACE;
				swapTiles(move[0], move[1], move[2], move[3]);
				isAnotherJumpPossible();
				return;
			}
			if (vertical == "down" && horizontal == "right") {
				tiles[move[0] + 1][move[1] + 1] = BROWN_SPACE;
				swapTiles(move[0], move[1], move[2], move[3]);
				isAnotherJumpPossible();
				return;
			}
			if (vertical == "down" && horizontal == "left") {
				tiles[move[0] + 1][move[1] - 1] = BROWN_SPACE;
				swapTiles(move[0], move[1], move[2], move[3]);
				isAnotherJumpPossible();
				return;

			}
		}
	}

	public boolean isAnotherJumpPossible() {

		if (turn == BLACK) {

		}
		if (turn == RED) {
		}
		turn();
		return false;
	}

	// Every time this is called, it switches turns
	public void turn() {
		if (turn == BLACK) {
			turnEnd = false;
			turn = RED;
		} else {
			turnEnd = false;
			turn = BLACK;
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