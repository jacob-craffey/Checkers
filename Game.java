package checkers;

public class Game {
	int[][] tiles = new int[8][8];
	int[][] move = new int[2][2];

	public Game() {

		// Sets up the gameboard
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if ((x + y) % 2 == 0) {
					if (x < 3) {
						tiles[x][y] = 3;
					} else if (x > 4) {
						tiles[x][y] = 2;
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

	public void setMove(int x, int y) {

		System.out.println(move);
	}
}
