package checkers;


import java.awt.Color;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestCheckers {

	Game game1 = new Game();
	StartGame start = new StartGame();
	int[][] grid = Game.tiles;
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
		Game.turn = Game.BLACK;
		grid[0][0] = Game.BLACK;
		game1.checkForKing();
	}

	// red king
	@Test
	public void testBlackKing2() {
		Game.turn = Game.RED;
		grid[7][7] = Game.RED;
		game1.checkForKing();
	}

  @Test
  public void testWinner() {
	  grid1[0][0] = Game.RED;
	  grid1[1][1] = Game.BLACK;
	  assertEquals(game1.playerWon(grid1), false);
	  grid1[1][1] = Game.BROWN_SPACE;
	  assertEquals(game1.playerWon(grid1), true);
	  grid1[1][1] = Game.BLACK;
	  grid1[0][0] = Game.BROWN_SPACE;
	  assertEquals(game1.playerWon(grid1), true);
	}

  @Test
  public void isThereAKing() {
	  grid1[0][0] = Game.RED;
	  grid1[1][1] = Game.BLACK;
	  assertEquals(game1.playerWon(grid), false);
	}

	@Test
	public void testTurn() {
		Game.turn = Game.BLACK;
		assertEquals(game1.getTurn(), "1");
		Game.turn = Game.RED;
		assertEquals(game1.getTurn(), "2");
	}

	@Test
	public void testIsMoveValid() {
		Game.turn = Game.BLACK;
		grid2[5][5] = Game.BLACK;
		game1.setMove(5, 5);
		game1.setMove(4, 4);
		assertEquals(game1.isMoveValid(), true);
	}

	@Test
	public void testIsMoveValid2() {
		Game.turn = Game.BLACK;
		grid2[5][5] = Game.BLACK;
		game1.setMove(5, 5);
		game1.setMove(4, 1);
		assertEquals(game1.isMoveValid(), false);
	}

	@Test
	public void testIsMoveValid3() {
		Game.turn = Game.BLACK;
		grid2[5][5] = Game.BLACK;
		Game.move[0] = 2;
		Game.move[1] = 2;
		Game.move[2] = 4;
		Game.move[3] = 4;
		assertEquals(game1.isMoveValid(), false);
	}

	@Test
	public void testIsMoveValid4() {
		Game.turn = Game.RED;
		grid2[2][2] = Game.RED;
		Game.move[2] = 3;
		Game.move[3] = 3;
		assertEquals(game1.isMoveValid(), true);
	}

	// Black up-right
	@Test
	public void testIsJumpValid() {
		Game.turn = Game.BLACK;
		grid[5][1] = Game.BROWN_SPACE;
		grid[4][2] = Game.BLACK;
		grid[3][3] = Game.RED;
		grid[2][4] = Game.BROWN_SPACE;
		Game.move[0] = 4;
		Game.move[1] = 2;
		Game.move[2] = 2;
		Game.move[3] = 4;
		assertEquals(game1.isJumpValid(), true);
	}

	// Black up-left
	@Test
	public void testIsJumpValid2() {
		Game.turn = Game.BLACK;
		grid[5][3] = Game.BROWN_SPACE;
		grid[4][2] = Game.BLACK;
		grid[3][1] = Game.RED;
		grid[2][0] = Game.BROWN_SPACE;
		Game.move[0] = 4;
		Game.move[1] = 2;
		Game.move[2] = 2;
		Game.move[3] = 0;
		assertEquals(game1.isJumpValid(), true);
	}

	// Black up-right
	@Test
	public void testIsJumpAvailable() {
		grid[4][2] = Game.BLACK;
		grid[3][3] = Game.RED;
		grid[2][4] = Game.BROWN_SPACE;
		grid[1][5] = Game.RED_KING;
		grid[0][6] = Game.BROWN_SPACE;
		Game.move[0] = 4;
		Game.move[1] = 2;
		Game.move[2] = 2;
		Game.move[3] = 4;
		assertEquals(game1.isjumpavailable(Game.BLACK), true);
	}

	// invalid nextJump tile
	@Test
	public void testIsJumpAvailable8() {
		grid[4][2] = Game.BLACK;
		grid[3][3] = Game.RED;
		grid[2][4] = Game.BROWN_SPACE;
		grid[1][5] = 0;
		grid[0][6] = Game.BROWN_SPACE;
		Game.move[0] = 4;
		Game.move[1] = 2;
		Game.move[2] = 2;
		Game.move[3] = 4;
		assertEquals(game1.isjumpavailable(Game.BLACK), false);
	}

	// ArrayindexOutOfBounds catch
	@Test
	public void testIsJumpAvailable5() {
		grid[4][2] = Game.BLACK_KING;
		grid[3][3] = Game.RED;
		grid[2][4] = Game.BROWN_SPACE;
		grid[1][5] = Game.RED;
		grid[0][6] = Game.BROWN_SPACE;
		Game.move[0] = 4;
		Game.move[1] = 2;
		Game.move[2] = 2;
		Game.move[3] = 15;
		assertEquals(game1.isjumpavailable(Game.BLACK), false);
	}

	public void runGui(){
		
	}
	// Black up-left
	@Test
	public void testIsJumpAvailable2() {
		grid[4][4] = Game.BLACK;
		grid[3][3] = Game.RED;
		grid[2][2] = Game.BROWN_SPACE;
		grid[1][1] = Game.RED;
		grid[0][0] = Game.BROWN_SPACE;
		Game.move[0] = 4;
		Game.move[1] = 4;
		Game.move[2] = 2;
		Game.move[3] = 2;
		assertEquals(game1.isjumpavailable(Game.BLACK), true);
	}

	// Black down-right
	@Test
	public void testIsJumpAvailable3() {
		grid[0][0] = Game.BLACK_KING;
		grid[1][1] = Game.RED;
		grid[2][2] = Game.BROWN_SPACE;
		grid[3][3] = Game.RED;
		grid[4][4] = Game.BROWN_SPACE;
		Game.move[0] = 0;
		Game.move[1] = 0;
		Game.move[2] = 2;
		Game.move[3] = 2;
		assertEquals(game1.isjumpavailable(Game.BLACK_KING), true);
	}

	// Black down-left
	@Test
	public void testIsJumpAvailable4() {
		grid[0][4] = Game.BLACK_KING;
		grid[1][3] = Game.RED;
		grid[2][2] = Game.BROWN_SPACE;
		grid[3][1] = Game.RED;
		grid[4][0] = Game.BROWN_SPACE;
		Game.move[0] = 0;
		Game.move[1] = 0;
		Game.move[2] = 2;
		Game.move[3] = 2;
		assertEquals(game1.isjumpavailable(Game.BLACK_KING), true);
	}

	//
	@Test
	public void testIsJumpAvailable6() {
		grid[4][2] = Game.BLACK;
		grid[3][3] = Game.RED_KING;
		grid[2][4] = Game.BROWN_SPACE;
		grid[1][5] = Game.RED_KING;
		grid[0][6] = Game.BROWN_SPACE;
		Game.move[0] = 4;
		Game.move[1] = 2;
		Game.move[2] = 2;
		Game.move[3] = 4;
		assertEquals(game1.isjumpavailable(8), false);
	}

	@Test
	public void testIsJumpAvailable7() {
		grid[4][2] = Game.BLACK;
		grid[3][3] = Game.RED;
		grid[2][4] = Game.BROWN_SPACE;
		grid[1][5] = Game.RED_KING;
		grid[0][6] = Game.BROWN_SPACE;
		Game.move[0] = 4;
		Game.move[1] = 2;
		Game.move[2] = 2;
		Game.move[3] = 4;
		assertEquals(game1.isjumpavailable(8), false);
	}

	/** Tests valid second jump options. **/
	@Test
  public final void isJumpUpLeftAvailable() {
		grid[4][4] = Game.BLACK;
		grid[3][3] = Game.RED;
		grid[2][2] = Game.BROWN_SPACE;
		grid[1][1] = Game.RED_KING;
		grid[0][0] = Game.BROWN_SPACE;
		Game.move[0] = 4;
		Game.move[1] = 4;
		Game.move[2] = 2;
		Game.move[3] = 2;
		assertEquals(game1.isjumpavailable(Game.BLACK), true);
	}

	// Tests invalid second jump options
	@Test
	public void isJumpUpLeftAvailable2() {
		grid[4][4] = Game.BLACK;
		grid[3][3] = Game.RED;
		grid[2][2] = Game.BROWN_SPACE;
		grid[1][1] = 0;
		grid[0][0] = Game.BROWN_SPACE;
		Game.move[0] = 4;
		Game.move[1] = 4;
		Game.move[2] = 2;
		Game.move[3] = 2;
		assertEquals(game1.isjumpavailable(Game.BLACK), false);
	}

	// Tests invalid second jump options
	@Test
	public void kingMultipleJumpDownRight() {
		grid[0][0] = Game.BLACK_KING;
		grid[1][1] = Game.RED;
		grid[2][2] = Game.BROWN_SPACE;
		grid[3][3] = Game.RED_KING;
		grid[4][4] = Game.BROWN_SPACE;
		Game.move[0] = 0;
		Game.move[1] = 0;
		Game.move[2] = 2;
		Game.move[3] = 2;
		assertEquals(game1.isjumpavailable(Game.BLACK_KING), true);
	}

	// Tests invalid second jump options
	@Test
	public void kingMultipleJumpDownRight2() {
		grid[0][0] = Game.BLACK_KING;
		grid[1][1] = Game.RED;
		grid[2][2] = Game.BROWN_SPACE;
		grid[3][3] = Game.RED_KING;
		grid[4][4] = 8;
		Game.move[0] = 0;
		Game.move[1] = 0;
		Game.move[2] = 2;
		Game.move[3] = 2;
		assertEquals(game1.isjumpavailable(Game.BLACK_KING), false);
	}

	@Test
	public void kingMultipleJumpDownRightException() {
		grid[0][0] = Game.BLACK_KING;
		grid[1][1] = Game.RED;
		grid[2][2] = Game.BROWN_SPACE;
		grid[3][3] = Game.RED_KING;
		grid[4][4] = Game.BROWN_SPACE;
		Game.move[0] = 0;
		Game.move[1] = 0;
		Game.move[2] = 2;
		Game.move[3] = 23;
		assertEquals(game1.isjumpavailable(Game.BLACK_KING), false);
	}

	@Test
	public void kingMultipleJumpDownLeft() {
		grid[0][4] = Game.BLACK_KING;
		grid[1][3] = Game.RED;
		grid[2][2] = Game.BROWN_SPACE;
		grid[3][1] = Game.RED_KING;
		grid[4][0] = Game.BROWN_SPACE;
		Game.move[0] = 0;
		Game.move[1] = 4;
		Game.move[2] = 2;
		Game.move[3] = 2;
		assertEquals(game1.isjumpavailable(Game.BLACK_KING), true);
	}

	@Test
	public void kingMultipleJumpDownLeftFalse() {
		grid[0][4] = Game.BLACK_KING;
		grid[1][3] = Game.RED;
		grid[2][2] = Game.BROWN_SPACE;
		grid[3][1] = Game.RED_KING;
		grid[4][0] = 6;
		Game.move[0] = 0;
		Game.move[1] = 4;
		Game.move[2] = 2;
		Game.move[3] = 2;
		assertEquals(game1.isjumpavailable(Game.BLACK_KING), false);
	}

	@Test
	public void RedMultipleJumpDownRight() {
		grid[0][0] = Game.RED_KING;
		grid[1][1] = Game.BLACK_KING;
		grid[2][2] = Game.BROWN_SPACE;
		grid[3][3] = Game.BLACK_KING;
		grid[4][4] = Game.BROWN_SPACE;
		Game.move[0] = 0;
		Game.move[1] = 0;
		Game.move[2] = 2;
		Game.move[3] = 2;
		assertEquals(game1.isjumpavailable(Game.RED), true);
	}

	@Test
	public void nonColorFalseRed() {
		grid[0][0] = Game.RED_KING;
		grid[1][1] = Game.BLACK_KING;
		grid[2][2] = Game.BROWN_SPACE;
		grid[3][3] = Game.BLACK;
		grid[4][4] = 9;
		Game.move[0] = 0;
		Game.move[1] = 0;
		Game.move[2] = 2;
		Game.move[3] = 2;
		assertEquals(game1.isjumpavailable(Game.RED_KING), false);
	}

	@Test
	public void tilesOutOfBoundsRedMultiple() {
		grid[0][0] = Game.RED_KING;
		grid[1][1] = Game.BLACK_KING;
		grid[2][2] = Game.BROWN_SPACE;
		grid[3][3] = Game.BLACK;
		grid[4][4] = Game.BROWN_SPACE;
		Game.move[0] = 0;
		Game.move[1] = 0;
		Game.move[2] = 10;
		Game.move[3] = 2;
		assertEquals(game1.isjumpavailable(Game.RED_KING), false);
	}
	
	@Test
	public void RedMultipleJumpDownRight2() {
		grid[0][0] = Game.RED_KING;
		grid[1][1] = 15;
		grid[2][2] = Game.BROWN_SPACE;
		grid[3][3] = Game.BLACK_KING;
		grid[4][4] = Game.BROWN_SPACE;
		Game.move[0] = 0;
		Game.move[1] = 0;
		Game.move[2] = 2;
		Game.move[3] = 2;
		assertEquals(game1.isjumpavailable(Game.RED), true);
	}

	// Invalid play sound input
	@Test
	public void testPlaySound() {
		game1.playSound("invalid fileName");
	}

	// black king move
	@Test
	public void testKingMove() {
		Game.turn = Game.BLACK;
		grid[0][0] = Game.BLACK_KING;
		grid[1][1] = Game.BROWN_SPACE;
		game1.setMove(0, 0);
		game1.setMove(1, 1);
	}

	// red king move
	@Test
	public void testKingMove2() {
		grid[7][7] = Game.RED_KING;
		grid[6][6] = Game.BROWN_SPACE;
		game1.setMove(7, 7);
		game1.setMove(6, 6);
	}
	
	@Test
	public void start(){
	
	}
}