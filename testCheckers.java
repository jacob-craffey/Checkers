package checkers;

import java.awt.Color;

import org.junit.Test;

import static org.junit.Assert.*;

public class testCheckers {

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
		Game.turn = Game.black;
		grid[0][0] = Game.black;
		game1.checkForKing();
	}

	// red king
	@Test
	public void testBlackKing2() {
		Game.turn = Game.red;
		grid[7][7] = Game.red;
		game1.checkForKing();
	}

	@Test
	public void testWinner() {
		grid1[0][0] = Game.red;
		grid1[1][1] = Game.black;
		assertEquals(game1.playerWon(grid1), false);
		grid1[1][1] = Game.brownSpace;
		assertEquals(game1.playerWon(grid1), true);
		grid1[1][1] = Game.black;
		grid1[0][0] = Game.brownSpace;
		assertEquals(game1.playerWon(grid1), true);
	}

	@Test
	public void isThereAKing() {
		grid1[0][0] = Game.red;
		grid1[1][1] = Game.black;
		assertEquals(game1.playerWon(grid), false);
	}

	@Test
	public void testTurn() {
		Game.turn = Game.black;
		assertEquals(game1.getTurn(), "1");
		Game.turn = Game.red;
		assertEquals(game1.getTurn(), "2");
	}

	@Test
	public void testIsMoveValid() {
		Game.turn = Game.black;
		grid2[5][5] = Game.black;
		game1.setMove(5, 5);
		game1.setMove(4, 4);
		assertEquals(game1.isMoveValid(), true);
	}

	@Test
	public void testIsMoveValid2() {
		Game.turn = Game.black;
		grid2[5][5] = Game.black;
		game1.setMove(5, 5);
		game1.setMove(4, 1);
		assertEquals(game1.isMoveValid(), false);
	}

	@Test
	public void testIsMoveValid3() {
		Game.turn = Game.black;
		grid2[5][5] = Game.black;
		Game.move[0] = 2;
		Game.move[1] = 2;
		Game.move[2] = 4;
		Game.move[3] = 4;
		assertEquals(game1.isMoveValid(), false);
	}

	@Test
	public void testIsMoveValid4() {
		Game.turn = Game.red;
		grid2[2][2] = Game.red;
		Game.move[2] = 3;
		Game.move[3] = 3;
		assertEquals(game1.isMoveValid(), true);
	}

	// Black up-right
	@Test
	public void testIsJumpValid() {
		Game.turn = Game.black;
		grid[5][1] = Game.brownSpace;
		grid[4][2] = Game.black;
		grid[3][3] = Game.red;
		grid[2][4] = Game.brownSpace;
		Game.move[0] = 4;
		Game.move[1] = 2;
		Game.move[2] = 2;
		Game.move[3] = 4;
		assertEquals(game1.isJumpValid(), true);
	}

	// Black up-left
	@Test
	public void testIsJumpValid2() {
		Game.turn = Game.black;
		grid[5][3] = Game.brownSpace;
		grid[4][2] = Game.black;
		grid[3][1] = Game.red;
		grid[2][0] = Game.brownSpace;
		Game.move[0] = 4;
		Game.move[1] = 2;
		Game.move[2] = 2;
		Game.move[3] = 0;
		assertEquals(game1.isJumpValid(), true);
	}

	// Black up-right
	@Test
	public void testIsJumpAvailable() {
		grid[4][2] = Game.black;
		grid[3][3] = Game.red;
		grid[2][4] = Game.brownSpace;
		grid[1][5] = Game.redKing;
		grid[0][6] = Game.brownSpace;
		Game.move[0] = 4;
		Game.move[1] = 2;
		Game.move[2] = 2;
		Game.move[3] = 4;
		assertEquals(game1.isjumpavailable(Game.black), true);
	}

	// invalid nextJump tile
	@Test
	public void testIsJumpAvailable8() {
		grid[4][2] = Game.black;
		grid[3][3] = Game.red;
		grid[2][4] = Game.brownSpace;
		grid[1][5] = 0;
		grid[0][6] = Game.brownSpace;
		Game.move[0] = 4;
		Game.move[1] = 2;
		Game.move[2] = 2;
		Game.move[3] = 4;
		assertEquals(game1.isjumpavailable(Game.black), false);
	}

	// ArrayindexOutOfBounds catch
	@Test
	public void testIsJumpAvailable5() {
		grid[4][2] = Game.blackKing;
		grid[3][3] = Game.red;
		grid[2][4] = Game.brownSpace;
		grid[1][5] = Game.red;
		grid[0][6] = Game.brownSpace;
		Game.move[0] = 4;
		Game.move[1] = 2;
		Game.move[2] = 2;
		Game.move[3] = 15;
		assertEquals(game1.isjumpavailable(Game.black), false);
	}

	public void runGui(){
		
	}
	// Black up-left
	@Test
	public void testIsJumpAvailable2() {
		grid[4][4] = Game.black;
		grid[3][3] = Game.red;
		grid[2][2] = Game.brownSpace;
		grid[1][1] = Game.red;
		grid[0][0] = Game.brownSpace;
		Game.move[0] = 4;
		Game.move[1] = 4;
		Game.move[2] = 2;
		Game.move[3] = 2;
		assertEquals(game1.isjumpavailable(Game.black), true);
	}

	// Black down-right
	@Test
	public void testIsJumpAvailable3() {
		grid[0][0] = Game.blackKing;
		grid[1][1] = Game.red;
		grid[2][2] = Game.brownSpace;
		grid[3][3] = Game.red;
		grid[4][4] = Game.brownSpace;
		Game.move[0] = 0;
		Game.move[1] = 0;
		Game.move[2] = 2;
		Game.move[3] = 2;
		assertEquals(game1.isjumpavailable(Game.blackKing), true);
	}

	// Black down-left
	@Test
	public void testIsJumpAvailable4() {
		grid[0][4] = Game.blackKing;
		grid[1][3] = Game.red;
		grid[2][2] = Game.brownSpace;
		grid[3][1] = Game.red;
		grid[4][0] = Game.brownSpace;
		Game.move[0] = 0;
		Game.move[1] = 0;
		Game.move[2] = 2;
		Game.move[3] = 2;
		assertEquals(game1.isjumpavailable(Game.blackKing), true);
	}

	//
	@Test
	public void testIsJumpAvailable6() {
		grid[4][2] = Game.black;
		grid[3][3] = Game.redKing;
		grid[2][4] = Game.brownSpace;
		grid[1][5] = Game.redKing;
		grid[0][6] = Game.brownSpace;
		Game.move[0] = 4;
		Game.move[1] = 2;
		Game.move[2] = 2;
		Game.move[3] = 4;
		assertEquals(game1.isjumpavailable(8), false);
	}

	@Test
	public void testIsJumpAvailable7() {
		grid[4][2] = Game.black;
		grid[3][3] = Game.red;
		grid[2][4] = Game.brownSpace;
		grid[1][5] = Game.redKing;
		grid[0][6] = Game.brownSpace;
		Game.move[0] = 4;
		Game.move[1] = 2;
		Game.move[2] = 2;
		Game.move[3] = 4;
		assertEquals(game1.isjumpavailable(8), false);
	}

	// Tests valid second jump options
	@Test
	public void isJumpUpLeftAvailable() {
		grid[4][4] = Game.black;
		grid[3][3] = Game.red;
		grid[2][2] = Game.brownSpace;
		grid[1][1] = Game.redKing;
		grid[0][0] = Game.brownSpace;
		Game.move[0] = 4;
		Game.move[1] = 4;
		Game.move[2] = 2;
		Game.move[3] = 2;
		assertEquals(game1.isjumpavailable(Game.black), true);
	}

	// Tests invalid second jump options
	@Test
	public void isJumpUpLeftAvailable2() {
		grid[4][4] = Game.black;
		grid[3][3] = Game.red;
		grid[2][2] = Game.brownSpace;
		grid[1][1] = 0;
		grid[0][0] = Game.brownSpace;
		Game.move[0] = 4;
		Game.move[1] = 4;
		Game.move[2] = 2;
		Game.move[3] = 2;
		assertEquals(game1.isjumpavailable(Game.black), false);
	}

	// Tests invalid second jump options
	@Test
	public void kingMultipleJumpDownRight() {
		grid[0][0] = Game.blackKing;
		grid[1][1] = Game.red;
		grid[2][2] = Game.brownSpace;
		grid[3][3] = Game.redKing;
		grid[4][4] = Game.brownSpace;
		Game.move[0] = 0;
		Game.move[1] = 0;
		Game.move[2] = 2;
		Game.move[3] = 2;
		assertEquals(game1.isjumpavailable(Game.blackKing), true);
	}

	// Tests invalid second jump options
	@Test
	public void kingMultipleJumpDownRight2() {
		grid[0][0] = Game.blackKing;
		grid[1][1] = Game.red;
		grid[2][2] = Game.brownSpace;
		grid[3][3] = Game.redKing;
		grid[4][4] = 8;
		Game.move[0] = 0;
		Game.move[1] = 0;
		Game.move[2] = 2;
		Game.move[3] = 2;
		assertEquals(game1.isjumpavailable(Game.blackKing), false);
	}

	@Test
	public void kingMultipleJumpDownRightException() {
		grid[0][0] = Game.blackKing;
		grid[1][1] = Game.red;
		grid[2][2] = Game.brownSpace;
		grid[3][3] = Game.redKing;
		grid[4][4] = Game.brownSpace;
		Game.move[0] = 0;
		Game.move[1] = 0;
		Game.move[2] = 2;
		Game.move[3] = 23;
		assertEquals(game1.isjumpavailable(Game.blackKing), false);
	}

	@Test
	public void kingMultipleJumpDownLeft() {
		grid[0][4] = Game.blackKing;
		grid[1][3] = Game.red;
		grid[2][2] = Game.brownSpace;
		grid[3][1] = Game.redKing;
		grid[4][0] = Game.brownSpace;
		Game.move[0] = 0;
		Game.move[1] = 4;
		Game.move[2] = 2;
		Game.move[3] = 2;
		assertEquals(game1.isjumpavailable(Game.blackKing), true);
	}

	@Test
	public void kingMultipleJumpDownLeftFalse() {
		grid[0][4] = Game.blackKing;
		grid[1][3] = Game.red;
		grid[2][2] = Game.brownSpace;
		grid[3][1] = Game.redKing;
		grid[4][0] = 6;
		Game.move[0] = 0;
		Game.move[1] = 4;
		Game.move[2] = 2;
		Game.move[3] = 2;
		assertEquals(game1.isjumpavailable(Game.blackKing), false);
	}

	@Test
	public void RedMultipleJumpDownRight() {
		grid[0][0] = Game.redKing;
		grid[1][1] = Game.blackKing;
		grid[2][2] = Game.brownSpace;
		grid[3][3] = Game.blackKing;
		grid[4][4] = Game.brownSpace;
		Game.move[0] = 0;
		Game.move[1] = 0;
		Game.move[2] = 2;
		Game.move[3] = 2;
		assertEquals(game1.isjumpavailable(Game.red), true);
	}

	@Test
	public void nonColorFalseRed() {
		grid[0][0] = Game.redKing;
		grid[1][1] = Game.blackKing;
		grid[2][2] = Game.brownSpace;
		grid[3][3] = Game.black;
		grid[4][4] = 9;
		Game.move[0] = 0;
		Game.move[1] = 0;
		Game.move[2] = 2;
		Game.move[3] = 2;
		assertEquals(game1.isjumpavailable(Game.redKing), false);
	}

	@Test
	public void tilesOutOfBoundsRedMultiple() {
		grid[0][0] = Game.redKing;
		grid[1][1] = Game.blackKing;
		grid[2][2] = Game.brownSpace;
		grid[3][3] = Game.black;
		grid[4][4] = Game.brownSpace;
		Game.move[0] = 0;
		Game.move[1] = 0;
		Game.move[2] = 10;
		Game.move[3] = 2;
		assertEquals(game1.isjumpavailable(Game.redKing), false);
	}
	
	@Test
	public void RedMultipleJumpDownRight2() {
		grid[0][0] = Game.redKing;
		grid[1][1] = 15;
		grid[2][2] = Game.brownSpace;
		grid[3][3] = Game.blackKing;
		grid[4][4] = Game.brownSpace;
		Game.move[0] = 0;
		Game.move[1] = 0;
		Game.move[2] = 2;
		Game.move[3] = 2;
		assertEquals(game1.isjumpavailable(Game.red), true);
	}

	// Invalid play sound input
	@Test
	public void testPlaySound() {
		game1.playSound("invalid fileName");
	}

	// black king move
	@Test
	public void testKingMove() {
		Game.turn = Game.black;
		grid[0][0] = Game.blackKing;
		grid[1][1] = Game.brownSpace;
		game1.setMove(0, 0);
		game1.setMove(1, 1);
	}

	// red king move
	@Test
	public void testKingMove2() {
		grid[7][7] = Game.redKing;
		grid[6][6] = Game.brownSpace;
		game1.setMove(7, 7);
		game1.setMove(6, 6);
	}
	
	@Test
	public void start(){
	
	}
}
