package checkers;

import org.junit.Test;

import junit.framework.TestCase;

public class CheckersTest extends TestCase {

	Game game1 = new Game(false);
	//StartGame start = new StartGame();
	int[][] grid = Game.getTiles();
	int[][] grid1 = new int[8][8];
	int[][] grid2 = new int[8][8];
	int[][] grid3 = new int[8][8];

	// User's first click is a blank tile
	@Test
	public void testSetMove() {
		game1.setMove(0, 1);
		game1.setMove(4, 4);
	}

	// User's first click is a checker
	@Test
	public void testSetMove2() {
		game1.setMove(1, 1);
	}

	// User's first click is a checker and second click is occupied
	@Test
	public void testSetMove3() {
		game1.setMove(1, 1);
		game1.setMove(2, 2);
	}

	// valid move
	@Test
	public void testSetMove4() {
		game1.setMove(2, 2);
		game1.setMove(3, 3);
	}

	// black king
	@Test
	public void testBlackKing() {
	  checkers.Game.setTurn(checkers.Game.BLACK);
		grid[0][0] = checkers.Game.BLACK;
		checkers.Game.checkForKing();
	}

	//red king
	@Test
	public void testBlackKing2() {
	  checkers.Game.setTurn(checkers.Game.RED);
		grid[7][7] = checkers.Game.RED;
		checkers.Game.checkForKing();
	}

  @Test
  public void testWinner() {
	  grid1[0][0] = checkers.Game.RED;
	  grid1[1][1] = checkers.Game.BLACK;
	  assertEquals(checkers.Game.playerWon(grid1), false);
	  grid1[1][1] = checkers.Game.BROWN_SPACE;
	  assertEquals(checkers.Game.playerWon(grid1), true);
	  grid1[1][1] = checkers.Game.BLACK;
	  grid1[0][0] = checkers.Game.BROWN_SPACE;
	  assertEquals(checkers.Game.playerWon(grid1), true);
	}

  @Test
  public void isThereAKing() {
	  grid1[0][0] = checkers.Game.RED;
	  grid1[1][1] = checkers.Game.BLACK;
	  assertEquals(checkers.Game.playerWon(grid), false);
	}

	@Test
	public void testTurn() {
		checkers.Game.setTurn(checkers.Game.BLACK);
		assertEquals(checkers.Game.getTurn2(), "1");
		checkers.Game.turn = checkers.Game.RED;
		assertEquals(checkers.Game.getTurn2(), "2");
	}

	@Test
	public void testIsMoveValid() {
		checkers.Game.setTurn(checkers.Game.BLACK);
		grid2[5][5] = checkers.Game.BLACK;
		game1.setMove(5, 5);
		game1.setMove(4, 4);
		assertEquals(checkers.Game.isMoveValid(), true);
	}

	@Test
	public void testIsMoveValid2() {
	  checkers.Game.setTurn(checkers.Game.BLACK);
		grid2[5][5] = checkers.Game.BLACK;
		game1.setMove(5, 5);
		game1.setMove(4, 1);
		assertEquals(checkers.Game.isMoveValid(), false);
	}

	@Test
	public void testIsMoveValid3() {
	  checkers.Game.setTurn(checkers.Game.BLACK);
		grid2[5][5] = checkers.Game.BLACK;
		checkers.Game.getMove()[0] = 2;
		checkers.Game.getMove()[1] = 2;
		checkers.Game.getMove()[2] = 4;
		checkers.Game.getMove()[3] = 4;
		assertEquals(checkers.Game.isMoveValid(), false);
	}

	/*@Test
	public void testIsMoveValid4() {
	  checkers.Game.setTurn(checkers.Game.RED);
		grid2[2][2] = checkers.Game.RED;
		checkers.Game.getMove()[2] = 3;
		checkers.Game.getMove()[3] = 3;
		assertEquals(checkers.Game.isMoveValid(), true);
	}*/

	// Black up-right
	@Test
	public void testIsJumpValid() {
	  checkers.Game.setTurn(checkers.Game.BLACK);
		grid[5][1] = checkers.Game.BROWN_SPACE;
		grid[4][2] = checkers.Game.BLACK;
		grid[3][3] = checkers.Game.RED;
		grid[2][4] = checkers.Game.BROWN_SPACE;
		checkers.Game.getMove()[0] = 4;
		checkers.Game.getMove()[1] = 2;
		checkers.Game.getMove()[2] = 2;
		checkers.Game.getMove()[3] = 4;
		assertEquals(game1.isJumpValid(), true);
	}

	// Black up-left
	@Test
	public void testIsJumpValid2() {
	  checkers.Game.setTurn(checkers.Game.BLACK);
		grid[5][3] = checkers.Game.BROWN_SPACE;
		grid[4][2] = checkers.Game.BLACK;
		grid[3][1] = checkers.Game.RED;
		grid[2][0] = checkers.Game.BROWN_SPACE;
		checkers.Game.getMove()[0] = 4;
		checkers.Game.getMove()[1] = 2;
		checkers.Game.getMove()[2] = 2;
		checkers.Game.getMove()[3] = 0;
		assertEquals(game1.isJumpValid(), true);
	}

	// Black up-right
	@Test
	public void testIsJumpAvailable() {
		grid[4][2] = checkers.Game.BLACK;
		grid[3][3] = checkers.Game.RED;
		grid[2][4] = checkers.Game.BROWN_SPACE;
		grid[1][5] = checkers.Game.RED_KING;
		grid[0][6] = checkers.Game.BROWN_SPACE;
		checkers.Game.getMove()[0] = 4;
		checkers.Game.getMove()[1] = 2;
		checkers.Game.getMove()[2] = 2;
		checkers.Game.getMove()[3] = 4;
		assertEquals(game1.isjumpavailable(checkers.Game.BLACK), true);
	}

	// invalid nextJump tile
	@Test
	public void testIsJumpAvailable8() {
		grid[4][2] = checkers.Game.BLACK;
		grid[3][3] = checkers.Game.RED;
		grid[2][4] = checkers.Game.BROWN_SPACE;
		grid[1][5] = 0;
		grid[0][6] = checkers.Game.BROWN_SPACE;
		checkers.Game.getMove()[0] = 4;
		checkers.Game.getMove()[1] = 2;
		checkers.Game.getMove()[2] = 2;
		checkers.Game.getMove()[3] = 4;
		assertEquals(game1.isjumpavailable(checkers.Game.BLACK), false);
	}

	// ArrayindexOutOfBounds catch
	@Test
	public void testIsJumpAvailable5() {
		grid[4][2] = checkers.Game.BLACK_KING;
		grid[3][3] = checkers.Game.RED;
		grid[2][4] = checkers.Game.BROWN_SPACE;
		grid[1][5] = checkers.Game.RED;
		grid[0][6] = checkers.Game.BROWN_SPACE;
		checkers.Game.getMove()[0] = 4;
		checkers.Game.getMove()[1] = 2;
		checkers.Game.getMove()[2] = 2;
		checkers.Game.getMove()[3] = 15;
		assertEquals(game1.isjumpavailable(checkers.Game.BLACK), false);
	}

	// Black up-left
	@Test
	public void testIsJumpAvailable2() {
		grid[4][4] = checkers.Game.BLACK;
		grid[3][3] = checkers.Game.RED;
		grid[2][2] = checkers.Game.BROWN_SPACE;
		grid[1][1] = checkers.Game.RED;
		grid[0][0] = checkers.Game.BROWN_SPACE;
		checkers.Game.getMove()[0] = 4;
		checkers.Game.getMove()[1] = 4;
		checkers.Game.getMove()[2] = 2;
		checkers.Game.getMove()[3] = 2;
		assertEquals(game1.isjumpavailable(checkers.Game.BLACK), true);
	}

	// Black down-right
	@Test
	public void testIsJumpAvailable3() {
		grid[0][0] = checkers.Game.BLACK_KING;
		grid[1][1] = checkers.Game.RED;
		grid[2][2] = checkers.Game.BROWN_SPACE;
		grid[3][3] = checkers.Game.RED;
		grid[4][4] = checkers.Game.BROWN_SPACE;
		checkers.Game.getMove()[0] = 0;
		checkers.Game.getMove()[1] = 0;
		checkers.Game.getMove()[2] = 2;
		checkers.Game.getMove()[3] = 2;
		assertEquals(game1.isjumpavailable(checkers.Game.BLACK_KING), true);
	}

	// Black down-left
	@Test
	public void testIsJumpAvailable4() {
		grid[0][4] = checkers.Game.BLACK_KING;
		grid[1][3] = checkers.Game.RED;
		grid[2][2] = checkers.Game.BROWN_SPACE;
		grid[3][1] = checkers.Game.RED;
		grid[4][0] = checkers.Game.BROWN_SPACE;
		checkers.Game.getMove()[0] = 0;
		checkers.Game.getMove()[1] = 0;
		checkers.Game.getMove()[2] = 2;
		checkers.Game.getMove()[3] = 2;
		assertEquals(game1.isjumpavailable(checkers.Game.BLACK_KING), true);
	}

	//
	@Test
	public void testIsJumpAvailable6() {
		grid[4][2] = checkers.Game.BLACK;
		grid[3][3] = checkers.Game.RED_KING;
		grid[2][4] = checkers.Game.BROWN_SPACE;
		grid[1][5] = checkers.Game.RED_KING;
		grid[0][6] = checkers.Game.BROWN_SPACE;
		checkers.Game.getMove()[0] = 4;
		checkers.Game.getMove()[1] = 2;
		checkers.Game.getMove()[2] = 2;
		checkers.Game.getMove()[3] = 4;
		assertEquals(game1.isjumpavailable(8), false);
	}

	@Test
	public void testIsJumpAvailable7() {
		grid[4][2] = checkers.Game.BLACK;
		grid[3][3] = checkers.Game.RED;
		grid[2][4] = checkers.Game.BROWN_SPACE;
		grid[1][5] = checkers.Game.RED_KING;
		grid[0][6] = checkers.Game.BROWN_SPACE;
		checkers.Game.getMove()[0] = 4;
		checkers.Game.getMove()[1] = 2;
		checkers.Game.getMove()[2] = 2;
		checkers.Game.getMove()[3] = 4;
		assertEquals(game1.isjumpavailable(8), false);
	}

	/** Tests valid second jump options. **/
	@Test
  public final void isJumpUpLeftAvailable() {
		grid[4][4] = checkers.Game.BLACK;
		grid[3][3] = checkers.Game.RED;
		grid[2][2] = checkers.Game.BROWN_SPACE;
		grid[1][1] = checkers.Game.RED_KING;
		grid[0][0] = checkers.Game.BROWN_SPACE;
		checkers.Game.getMove()[0] = 4;
		checkers.Game.getMove()[1] = 4;
		checkers.Game.getMove()[2] = 2;
		checkers.Game.getMove()[3] = 2;
		assertEquals(game1.isjumpavailable(checkers.Game.BLACK), true);
	}

	// Tests invalid second jump options
	@Test
	public void isJumpUpLeftAvailable2() {
		grid[4][4] = checkers.Game.BLACK;
		grid[3][3] = checkers.Game.RED;
		grid[2][2] = checkers.Game.BROWN_SPACE;
		grid[1][1] = 0;
		grid[0][0] = checkers.Game.BROWN_SPACE;
		checkers.Game.getMove()[0] = 4;
		checkers.Game.getMove()[1] = 4;
		checkers.Game.getMove()[2] = 2;
		checkers.Game.getMove()[3] = 2;
		assertEquals(game1.isjumpavailable(checkers.Game.BLACK), false);
	}

	// Tests invalid second jump options
	@Test
	public void kingMultipleJumpDownRight() {
		grid[0][0] = checkers.Game.BLACK_KING;
		grid[1][1] = checkers.Game.RED;
		grid[2][2] = checkers.Game.BROWN_SPACE;
		grid[3][3] = checkers.Game.RED_KING;
		grid[4][4] = checkers.Game.BROWN_SPACE;
		checkers.Game.getMove()[0] = 0;
		checkers.Game.getMove()[1] = 0;
		checkers.Game.getMove()[2] = 2;
		checkers.Game.getMove()[3] = 2;
		assertEquals(game1.isjumpavailable(checkers.Game.BLACK_KING), true);
	}

	// Tests invalid second jump options
	@Test
	public void kingMultipleJumpDownRight2() {
		grid[0][0] = checkers.Game.BLACK_KING;
		grid[1][1] = checkers.Game.RED;
		grid[2][2] = checkers.Game.BROWN_SPACE;
		grid[3][3] = checkers.Game.RED_KING;
		grid[4][4] = 8;
		checkers.Game.getMove()[0] = 0;
		checkers.Game.getMove()[1] = 0;
		checkers.Game.getMove()[2] = 2;
		checkers.Game.getMove()[3] = 2;
		assertEquals(game1.isjumpavailable(checkers.Game.BLACK_KING), false);
	}

	@Test
	public void kingMultipleJumpDownRightException() {
		grid[0][0] = checkers.Game.BLACK_KING;
		grid[1][1] = checkers.Game.RED;
		grid[2][2] = checkers.Game.BROWN_SPACE;
		grid[3][3] = checkers.Game.RED_KING;
		grid[4][4] = checkers.Game.BROWN_SPACE;
		checkers.Game.getMove()[0] = 0;
		checkers.Game.getMove()[1] = 0;
		checkers.Game.getMove()[2] = 2;
		checkers.Game.getMove()[3] = 23;
		assertEquals(game1.isjumpavailable(checkers.Game.BLACK_KING), false);
	}

	@Test
	public void kingMultipleJumpDownLeft() {
		grid[0][4] = checkers.Game.BLACK_KING;
		grid[1][3] = checkers.Game.RED;
		grid[2][2] = checkers.Game.BROWN_SPACE;
		grid[3][1] = checkers.Game.RED_KING;
		grid[4][0] = checkers.Game.BROWN_SPACE;
		checkers.Game.getMove()[0] = 0;
		checkers.Game.getMove()[1] = 4;
		checkers.Game.getMove()[2] = 2;
		checkers.Game.getMove()[3] = 2;
		assertEquals(game1.isjumpavailable(checkers.Game.BLACK_KING), true);
	}

	@Test
	public void kingMultipleJumpDownLeftFalse() {
		grid[0][4] = checkers.Game.BLACK_KING;
		grid[1][3] = checkers.Game.RED;
		grid[2][2] = checkers.Game.BROWN_SPACE;
		grid[3][1] = checkers.Game.RED_KING;
		grid[4][0] = 6;
		checkers.Game.getMove()[0] = 0;
		checkers.Game.getMove()[1] = 4;
		checkers.Game.getMove()[2] = 2;
		checkers.Game.getMove()[3] = 2;
		assertEquals(game1.isjumpavailable(checkers.Game.BLACK_KING), false);
	}

	@Test
	public void RedMultipleJumpDownRight() {
		grid[0][0] = checkers.Game.RED_KING;
		grid[1][1] = checkers.Game.BLACK_KING;
		grid[2][2] = checkers.Game.BROWN_SPACE;
		grid[3][3] = checkers.Game.BLACK_KING;
		grid[4][4] = checkers.Game.BROWN_SPACE;
		checkers.Game.getMove()[0] = 0;
		checkers.Game.getMove()[1] = 0;
		checkers.Game.getMove()[2] = 2;
		checkers.Game.getMove()[3] = 2;
		assertEquals(game1.isjumpavailable(checkers.Game.RED), true);
	}

	@Test
	public void nonColorFalseRed() {
		grid[0][0] = checkers.Game.RED_KING;
		grid[1][1] = checkers.Game.BLACK_KING;
		grid[2][2] = checkers.Game.BROWN_SPACE;
		grid[3][3] = checkers.Game.BLACK;
		grid[4][4] = 9;
		checkers.Game.getMove()[0] = 0;
		checkers.Game.getMove()[1] = 0;
		checkers.Game.getMove()[2] = 2;
		checkers.Game.getMove()[3] = 2;
		assertEquals(game1.isjumpavailable(checkers.Game.RED_KING), false);
	}

	@Test
	public void tilesOutOfBoundsRedMultiple() {
		grid[0][0] = checkers.Game.RED_KING;
		grid[1][1] = checkers.Game.BLACK_KING;
		grid[2][2] = checkers.Game.BROWN_SPACE;
		grid[3][3] = checkers.Game.BLACK;
		grid[4][4] = checkers.Game.BROWN_SPACE;
		checkers.Game.getMove()[0] = 0;
		checkers.Game.getMove()[1] = 0;
		checkers.Game.getMove()[2] = 10;
		checkers.Game.getMove()[3] = 2;
		assertEquals(game1.isjumpavailable(checkers.Game.RED_KING), false);
	}
	
	@Test
	public void RedMultipleJumpDownRight2() {
		grid[0][0] = checkers.Game.RED_KING;
		grid[1][1] = 15;
		grid[2][2] = checkers.Game.BROWN_SPACE;
		grid[3][3] = checkers.Game.BLACK_KING;
		grid[4][4] = checkers.Game.BROWN_SPACE;
		checkers.Game.getMove()[0] = 0;
		checkers.Game.getMove()[1] = 0;
		checkers.Game.getMove()[2] = 2;
		checkers.Game.getMove()[3] = 2;
		assertEquals(game1.isjumpavailable(checkers.Game.RED), true);
	}

	// Invalid play sound input
	@Test
	public void testPlaySound() {
		checkers.Game.playSound("invalid fileName");
	}

	// black king move
	@Test
	public void testKingMove() {
	  checkers.Game.setTurn(checkers.Game.BLACK);
		grid[0][0] = checkers.Game.BLACK_KING;
		grid[1][1] = checkers.Game.BROWN_SPACE;
		game1.setMove(0, 0);
		game1.setMove(1, 1);
	}

	// red king move
	@Test
	public void testKingMove2() {
		grid[7][7] = checkers.Game.RED_KING;
		grid[6][6] = checkers.Game.BROWN_SPACE;
		game1.setMove(7, 7);
		game1.setMove(6, 6);
	}
	
}