package checkers;

import java.util.ArrayList;
import java.util.Random;

/** Interface for the AI. Controls the AI's behavior
 * under given circumstances**/
public interface AIinterface {
	
	  /** Integers. **/
	 int SIX = 6;
	  /** Integers. **/
	 int SEVEN = 7;
	  /** Integers. **/
	 int EIGHT = 8;

	/** Int to indicate where a checker is going to move.**/
  int DOWN_ONE = 1;
  /** Int to indicate where a checker is going to move.**/
  int UP_ONE = -1;
  /** Int to indicate where a checker is going to move.**/
  int RIGHT_ONE = 1;
  /** Int to indicate where a checker is going to move.**/
  int LEFT_ONE = -1;
  /** Int to indicate where black checkers are on the board.**/
  int BLACK = 2;
  /** Int to indicate where red checkers are on the board.**/
  int RED = 3;
  /** Int to indicate where black kings are on the board.**/
  int BLACK_KING = 4;
  /** Int to indicate where red kings are on the board.**/
  int RED_KING = 5;
  /** Int to indicate where grey spaces are on the board.**/
  int GREY_SPACE = 0;
  /** Int to indicate where brown spaces are on the board.**/
  int BROWN_SPACE = 1;
  /** Dimension of the board.**/
  int BOARD_DIMENSION = 8;
  

  /** 
   * Function for swapping the AI's tiles to complete moves or 
   * jumps. Similar to the swap tiles function for players.
   * 
   * @param tiles   The game board
   * @param firstX  The first x coordinate for moving the AI
   * @param firstY  The first Y coordinate for moving the AI
   * @param secondX  The second x coordinate for moving the AI
   * @param secondY  The second y coordinate for moving the AI
   * @return two dimensional array of the game board after the move.
   * @see int[][]
   * **/
  static int[][] swapTiles(int[][] tiles, final int firstX,
    final int firstY, final int secondX, final int secondY) {

    int temp = tiles[firstX][firstY];
    tiles[firstX][firstY] = tiles[secondX][secondY];
    tiles[secondX][secondY] = temp;
    return tiles;
  }

  
  /** 
   * Checks for a king before the AI's turn. 
   * @param tiles   The game board
   * @return two dimensional array of the game board after the move.
   * @see int[][]
   * **/
  static int[][] checkForKing(int[][] tiles) {
    for (int x = 0; x < EIGHT; x++) {
      if (tiles[0][x] == BLACK) {
        tiles[0][x] = BLACK_KING;
      }
      if (tiles[SEVEN][x] == RED) {
        tiles[SEVEN][x] = RED_KING;
      }
    }
    return tiles;
  }
  
  /** 
   * Function for controlling the logic of the AI. 
   * Dictates all choices the AI makes.
   * 
   * @param tiles   The game board
   * @return two dimensional array of the game board after the move.
   * @see int[][]
   * **/
  static int[][] ai(int[][] tiles) {
 
    ArrayList<Integer> aiPositions = new ArrayList<Integer>();
    boolean moveWasMade = false;
    int red = 3;

    for (int y = 0; y < EIGHT; y++) {
      for (int x = 0; x < EIGHT; x++) {
        if ((tiles[y][x] == red) || (tiles[y][x] == RED_KING)) {
          //System.out.println("RED x:" + x + " y: " + y + " has: "
        	//	  + tiles[y][x]);  
          aiPositions.add(y);
          aiPositions.add(x);
          tiles = aijumpavailable(tiles, y, x);
          if ((tiles[y][x] != red) && (tiles[y][x] != RED_KING)) {
            //System.out.println("move was made");
            return tiles;
          }
        }
      }
    }
    
    while (!aiPositions.isEmpty() && !moveWasMade) {
      int randomChecker = (int) (Math.random() * ((aiPositions.size() - 1)));
      if (randomChecker % 2 == 1) {
        randomChecker--;
      }
      //System.out.println("Random: " + randomChecker);
      //System.out.println("AI Positions: " + aiPositions.size());
      //System.out.println("random checker y: " 
    	//	  + (int) aiPositions.get(randomChecker));

      
      int y1 = (int) aiPositions.get(randomChecker);
      aiPositions.remove(randomChecker);
      
      //System.out.println("random checker x: " 
    	//	  + (int) aiPositions.get(randomChecker));
      //System.out.println("");
      
      int x1 = (int) aiPositions.get(randomChecker);
      aiPositions.remove(randomChecker);
      
      Random directionX = new Random();
      // ((max - min) + 1)
      int randomDirectionX = directionX.nextInt((2 - 0) + 1); 
      randomDirectionX--;
      if (randomDirectionX == 0) {
        randomDirectionX--;
      }
      
      Random directionY = new Random();
      // ((max - min) + 1)
      int randomDirectionY = directionY.nextInt((2 - 0) + 1);
      if (tiles[y1][x1] == RED_KING) {
        randomDirectionY--;
        if (randomDirectionY == 0) {
          randomDirectionY--;
        }
      } else {
        randomDirectionY = 1;
      }
  
// Moving Down
      if (randomDirectionY > 0) {
        if (randomDirectionX <= 0) {
          if ((x1 > 0) && (y1 < SEVEN)) {
            //System.out.println("leftDown: " + tiles[y1+1][x1-1]);
            if (tiles[y1 + 1][x1 - 1] == 1) {
              tiles = swapTiles(tiles, y1, x1, y1 + 1, x1 - 1);
              tiles = checkForKing(tiles);
              moveWasMade = true;
              return tiles;
            }
          } else if ((x1 < SEVEN) && (y1 < SEVEN)) {
            if (tiles[y1 + 1][x1 + 1] == 1) {
              tiles = swapTiles(tiles, y1, x1, y1 + 1, x1 + 1);
              tiles = checkForKing(tiles);
              moveWasMade = true;
              return tiles;
            }
          }
  // Move to the right
        } else {
          if ((x1 < SEVEN) && (y1 < SEVEN)) {
            if (tiles[y1 + 1][x1 + 1] == 1) {
              tiles = swapTiles(tiles, y1, x1, y1 + 1, x1 + 1);
              tiles = checkForKing(tiles);
              moveWasMade = true;
              return tiles;
            }
        } else if ((x1 > 0) && (y1 < SEVEN)) {
            if (tiles[y1 + 1][x1 - 1] == 1) {
              tiles = swapTiles(tiles, y1, x1, y1 + 1, x1 - 1);
              tiles = checkForKing(tiles);
              moveWasMade = true;
              return tiles;
            }
        }
      
      }
      
   // Moving Up
      } else {
        if (randomDirectionX <= 0) {
          if ((x1 > 0) && (y1 > 0)) {
            //System.out.println("leftDown: " + tiles[y1+1][x1-1]);
            if (tiles[y1 - 1][x1 - 1] == 1) {
              tiles = swapTiles(tiles, y1, x1, y1 - 1, x1 - 1);
              tiles = checkForKing(tiles);
              moveWasMade = true;
              return tiles;
            }
          } else if ((x1 < SEVEN) && (y1 > 0)) {
            if (tiles[y1 - 1][x1 + 1] == 1) {
              tiles = swapTiles(tiles, y1, x1, y1 - 1, x1 + 1);
              tiles = checkForKing(tiles);
              moveWasMade = true;
              return tiles;
            }
          }
  // Move to the right
        } else {
          if ((x1 < SEVEN) && (y1 > 0)) {
            if (tiles[y1 - 1][x1 + 1] == 1) {
              tiles = swapTiles(tiles, y1, x1, y1 - 1, x1 + 1);
              tiles = checkForKing(tiles);
              moveWasMade = true;
              return tiles;
            }
        } else if ((x1 > 0) && (y1 > 0)) {
            if (tiles[y1 - 1][x1 - 1] == 1) {
              tiles = swapTiles(tiles, y1, x1, y1 - 1, x1 - 1);
              tiles = checkForKing(tiles);
              moveWasMade = true;
              return tiles;
            }
          }
        }
      
      }
    }
      tiles = checkForKing(tiles);
      return tiles;
  }
  /** 
   * method for checking if a jump is available to the AI.
   * 
   * @param tiles   The game board
   * @param y		The Y coordinate for the starting location of a checker
   * @param x		The x coordinate for the starting location of a checker
   * @return two dimensional array of the game board after the move.
   * @see int[][]
   * **/
  static int[][] aijumpavailable(int[][] tiles, int y, int x) {
    if ((y + 2 < EIGHT) && (x + 2 < EIGHT)) {
      if ((tiles[y + 1][x + 1] == BLACK  || tiles[y + 1][x + 1] 
    		  	== BLACK_KING) && (tiles[y + 2][x + 2] == 1)) {
        tiles = swapTiles(tiles, y, x, y + 2, x + 2);
        tiles[y + 1][x + 1] = 1;
        tiles = checkForKing(tiles);
        tiles = aijumpavailable(tiles, y + 2, x + 2);
        return tiles;
      }
    }
    
    if ((y + 2 < EIGHT) && (x - 2 >= 0)) {
      if ((tiles[y + 1][x - 1] == BLACK || tiles[y + 1][x - 1] 
    		  == BLACK_KING) && (tiles[y + 2][x - 2] == 1)) {
        tiles = swapTiles(tiles, y, x, y + 2, x - 2);
        tiles[y + 1][x - 1] = 1;
        tiles = checkForKing(tiles);
        tiles = aijumpavailable(tiles, y + 2, x - 2);
        return tiles;
      }
    }
    
    if ((y - 2 >= 0) && (x + 2 < EIGHT) && (tiles[y][x] == RED_KING)) {
      if ((tiles[y - 1][x + 1] == BLACK || tiles[y - 1][x + 1] 
    		  == BLACK_KING) && (tiles[y - 2][x + 2] == 1)) {
        tiles = swapTiles(tiles, y, x, y - 2, x + 2);
        tiles[y - 1][x + 1] = 1;
        tiles = aijumpavailable(tiles, y - 2, x + 2);
        return tiles;
      }
    }
    
    if ((y - 2 >= 0) && (x - 2 >= 0) && (tiles[y][x] == RED_KING)) {
      if ((tiles[y - 1][x - 1] == BLACK || tiles[y - 1][x - 1]
    		  == BLACK_KING) && (tiles[y - 2][x - 2] == 1)) {
        tiles = swapTiles(tiles, y, x, y - 2, x - 2);
        tiles[y - 1][x - 1] = 1;
        tiles = aijumpavailable(tiles, y - 2, x - 2);
        return tiles;
      }
    }
    
    return tiles;
  }
}