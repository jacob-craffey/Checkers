package checkers;

import java.io.File;
import java.util.ArrayList;
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
public class Game implements AIinterface {
  /** Integers. **/
  private static final int ONE = 1;
  /** Integers. **/
  private static final int TWO = 2;
  /** Integers. **/
  private static final int THREE = 3;
  /** Integers. **/
  private static final int FOUR = 4;
  /** Integers. **/
  private static final int SIX = 6;
  /** Integers. **/
  private static final int SEVEN = 7;
  /** Integers. **/
  private static final int EIGHT = 8;

  /** Tile that make up the game board.**/
  private static int[][] tiles = new int[EIGHT][EIGHT];
  /** Array that holds the player's clicks.**/
  private static int[] move = new int[FOUR];
  /** boolean used to determine if user is on his first click.**/
  private boolean firstClick = true;
  /** boolean used to determine if user is done with their turn.**/
  private static boolean turnEnd = false;
  /** boolean used to determine if user is on their second jump.**/
  private static boolean secondJump = false;
  /** boolean used to determine if a player has won.**/
  @SuppressWarnings("unused")
  private boolean won = false;
  /** Int to determine whose turn it is.**/

  
  static final int DOWN_ONE = 1;
  /** Int to indicate where a checker is going to move.**/
  static final int UP_ONE = -1;
  /** Int to indicate where a checker is going to move.**/
  static final int RIGHT_ONE = 1;
  /** Int to indicate where a checker is going to move.**/
  static final int LEFT_ONE = -1;
  /** Int to indicate where black checkers are on the board.**/
  static final int BLACK = 2;
  /** Int to indicate where red checkers are on the board.**/
  static final int RED = 3;
  /** Int to indicate where black kings are on the board.**/
  static final int BLACK_KING = 4;
  /** Int to indicate where red kings are on the board.**/
  static final int RED_KING = 5;
  /** Int to indicate where grey spaces are on the board.**/
  static final int GREY_SPACE = 0;
  /** Int to indicate where brown spaces are on the board.**/
  static final int BROWN_SPACE = 1;
  /** Dimension of the board.**/
  static final int BOARD_DIMENSION = 8;
  /** File path for WAV. **/
  private static String jumpSoundFile = "./src/checkers/sounds/jump.wav";
  /** File path for WAV. **/
  private static String kingSoundFile = "./src/checkers/sounds/king.wav";
  
  /** Player turn starts at 1 or 2. **/
  // CHECKSTYLE:OFF
  static int turn = BLACK;
  //static int turn = new Random().nextInt(TWO) + TWO;
  //CHECKSTYLE:ON

  /** 
  * Sets up the game board. 
  **/
  public Game() {
    
    
    
    for (int ytile = 0; ytile < BOARD_DIMENSION; ytile++) {
      for (int xtile = 0; xtile < BOARD_DIMENSION; xtile++) {
        if ((ytile + xtile) % TWO == 0) {
          if (ytile < THREE) {
            getTiles()[ytile][xtile] = RED;
          } else if (ytile > FOUR) {
            getTiles()[ytile][xtile] = BLACK;
          } else {
            getTiles()[ytile][xtile] = BROWN_SPACE;
          }
        } else {
          getTiles()[ytile][xtile] = GREY_SPACE;
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
    return Arrays.copyOf(getTiles(), getTiles().length);
  }

  /** 
  * method used to determine player's turn. 
  * 
  * @return  String of the color whose turn it is.
  * @see  String
  **/
  public final String getTurn() {
    if (turn == BLACK) {
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
        if (getTiles()[xtile][ytile] == GREY_SPACE 
            || getTiles()[xtile][ytile] == BROWN_SPACE) {
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
        if (getTiles()[xtile][ytile] == RED || getTiles()[xtile][ytile] 
            == BLACK) {
          move[0] = 0;
          move[1] = 0;
          firstClick = true;
          return;
        }

        // If correct, store second values
        move[TWO] = xtile;
        move[THREE] = ytile;
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
  public static void checkForKing() {
    tiles = AIinterface.checkForKing(tiles);
  }

  /** 
  * Method for moving the king. King pieces can move diagonally
  * in any direction. Tiles are swapped when a move is made. 
  **/
  public final void kingMove() {
    if (turn == BLACK && getTiles()[getMove()[0]][getMove()[1]] == BLACK_KING) {
      if (getMove()[0] == (getMove()[TWO] + DOWN_ONE) 
          || getMove()[0] == (getMove()[TWO] + UP_ONE)) {
        if ((getMove()[1] == (getMove()[THREE] + LEFT_ONE)) 
            || (getMove()[1] == (getMove()[THREE] + RIGHT_ONE))) {
          turnEnd = true;
          swapTiles(getMove()[0], getMove()[1], getMove()[TWO], 
              getMove()[THREE]);
          turn();
        }
      }
    }
    if (turn == RED && getTiles()[getMove()[0]][getMove()[1]] == RED_KING) {
      if (getMove()[0] == (getMove()[TWO] + DOWN_ONE) 
          || getMove()[0] == (getMove()[TWO] + UP_ONE)) {
        if ((getMove()[1] == (getMove()[THREE] + LEFT_ONE)) 
            || (getMove()[1] == (getMove()[THREE] + RIGHT_ONE))) {
          turnEnd = true;
          swapTiles(getMove()[0], getMove()[1], getMove()[TWO], 
              getMove()[THREE]);
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
  public static boolean isMoveValid() {
    // Checks to see if BLACK player clicks a BLACK checker
    if (turn == BLACK && getTiles()[getMove()[0]][getMove()[1]] == BLACK) {
      // Checks if BLACK player's second click 1 row above
      if (getMove()[0] == (getMove()[TWO] + DOWN_ONE)) {
        // Checks to see if the BLACK player's second click is diagonal
        // of the first click
        if ((getMove()[1] == (getMove()[THREE] + LEFT_ONE)) 
            || (getMove()[1] == (getMove()[THREE] + RIGHT_ONE))) {
          turnEnd = true;
          swapTiles(getMove()[0], getMove()[1], getMove()[TWO],
              getMove()[THREE]);
          checkForKing();
          turn();
          return true;
        }
      }

    }
    // Checks to see if RED player clicks a RED checker
    if (turn == RED && getTiles()[getMove()[0]][getMove()[1]] == RED) {
      // Checks if RED player's second click 1 row down
      if (getMove()[0] == (getMove()[TWO] + UP_ONE)) {
        // Checks to see if the RED player's second click is diagonal of
        // the first click
        if ((getMove()[1] == (getMove()[THREE] + LEFT_ONE)) 
            || (getMove()[1] == (getMove()[THREE] + RIGHT_ONE))) {
          turnEnd = true;
          swapTiles(getMove()[0], getMove()[1], getMove()[TWO], 
              getMove()[THREE]);
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
    if (turn == BLACK) {
      // up right
      if (getMove()[0] > getMove()[TWO] && getMove()[1] < getMove()[THREE]) {
        if (getTiles()[getMove()[0] - 1][getMove()[1] + 1] == RED 
            || getTiles()[getMove()[0] - 1][getMove()[1] + 1] == RED_KING) {
          if (getMove()[0] == getMove()[TWO] + TWO && getMove()[1]
              == getMove()[THREE] - TWO 
              && getTiles()[getMove()[TWO]][getMove()[THREE]] == BROWN_SPACE) {
            jump(RIGHT_ONE, UP_ONE);
            return true;
          }
        }
      }
      // up left
      if (getMove()[0] > getMove()[TWO] && getMove()[1] > getMove()[THREE]) {
        if (getTiles()[getMove()[0] - 1][getMove()[1] - 1] == RED 
            || getTiles()[getMove()[0] - 1][getMove()[1] - 1] == RED_KING) {
          if (getMove()[0] == getMove()[TWO] + TWO && getMove()[1]
              == getMove()[THREE] + TWO 
              && getTiles()[getMove()[TWO]][getMove()[THREE]] == BROWN_SPACE) {
            jump(LEFT_ONE, UP_ONE);
            return true;
          }
        }
      }
      // down right
      if (getTiles()[getMove()[0]][getMove()[1]] == BLACK_KING) {
        if (getMove()[0] < getMove()[TWO] && getMove()[1] < getMove()[THREE]) {
          if (getTiles()[getMove()[0] + 1][getMove()[1] + 1] == RED 
              || getTiles()[getMove()[0] + 1][getMove()[1] + 1] == RED_KING) {
            if (getMove()[0] + TWO == getMove()[TWO] && getMove()[1] + TWO
                == getMove()[THREE]) {
              jump(RIGHT_ONE, DOWN_ONE);
              return true;
            }
          }
        }
      }
      // down left
      if (getTiles()[getMove()[0]][getMove()[1]] == BLACK_KING) {
        if (getMove()[0] < getMove()[TWO] && getMove()[1] > getMove()[THREE]) {
          if (getTiles()[getMove()[0] + 1][getMove()[1] - 1] == RED 
              || getTiles()[getMove()[0] + 1][getMove()[1] - 1] == RED_KING) {
            if (getMove()[0] + TWO == getMove()[TWO] && getMove()[1] - TWO
                == getMove()[THREE] 
                && getTiles()[getMove()[TWO]][getMove()[THREE]] 
                    == BROWN_SPACE) {
              jump(LEFT_ONE, DOWN_ONE);
              return true;
            }
          }
        }
      }
    }

    if (turn == RED) {
      // up right
      if (getTiles()[getMove()[0]][getMove()[1]] == RED_KING) {
        if (getMove()[0] > getMove()[TWO] && getMove()[1] < getMove()[THREE]) {
          if (getTiles()[getMove()[0] - 1][getMove()[1] + 1] == BLACK 
              || getTiles()[getMove()[0] - 1][getMove()[1] + 1] == BLACK_KING) {
            if (getMove()[0] - TWO == getMove()[TWO] && getMove()[1] + TWO
                == getMove()[THREE] 
                && getTiles()[getMove()[TWO]][getMove()[THREE]]
                    == BROWN_SPACE) {
              jump(RIGHT_ONE, UP_ONE);
              return true;
            }
          }
        }
      }
      // up left
      if (getTiles()[getMove()[0]][getMove()[1]] == RED_KING) {
        if (getMove()[0] > getMove()[TWO] && getMove()[1] > getMove()[THREE]) {
          if (getTiles()[getMove()[0] - 1][getMove()[1] - 1] == BLACK 
              || getTiles()[getMove()[0] - 1][getMove()[1] - 1] == BLACK_KING) {
            if (getMove()[0] - TWO == getMove()[TWO] && getMove()[1] - TWO
                == getMove()[THREE] 
                && getTiles()[getMove()[TWO]][getMove()[THREE]]
                    == BROWN_SPACE) {
              jump(LEFT_ONE, UP_ONE);
              return true;
            }
          }
        }
      }
      // down right
      if (getMove()[0] < getMove()[TWO] && getMove()[1] < getMove()[THREE]) {
        if (getTiles()[getMove()[0] + 1][getMove()[1] + 1] == BLACK 
            || getTiles()[getMove()[0] + 1][getMove()[1] + 1] == BLACK_KING) {
          if (getMove()[0] + TWO == getMove()[TWO] && getMove()[1] + TWO
              == getMove()[THREE] 
              && getTiles()[getMove()[TWO]][getMove()[THREE]] == BROWN_SPACE) {
            jump(RIGHT_ONE, DOWN_ONE);
            return true;
          }
        }
      }
      // down left
      if (getMove()[0] < getMove()[TWO] && getMove()[1] > getMove()[THREE]) {
        if (getTiles()[getMove()[0] + 1][getMove()[1] - 1] == BLACK 
            || getTiles()[getMove()[0] + 1][getMove()[1] - 1] == BLACK_KING) {
          if (getMove()[0] + TWO == getMove()[TWO] && getMove()[1] - TWO
              == getMove()[THREE] 
              && getTiles()[getMove()[TWO]][getMove()[THREE]] == BROWN_SPACE) {
            jump(LEFT_ONE, DOWN_ONE);
            return true;
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
  *          to jump, positive of negative
  * @param vertical  The vertical direction the player wants 
  *          to jump, positive of negative
  **/
  public final void jump(final int horizontal, final int vertical) {
    getTiles()[(getMove()[0] + getMove()[TWO]) / TWO][(getMove()[1]
        + getMove()[THREE]) / TWO] 
        = BROWN_SPACE;
    swapTiles(getMove()[0], getMove()[1], getMove()[TWO], getMove()[THREE]);
    checkForKing();
    won = playerWon(getTiles());
    isjumpavailable(getTiles()[getMove()[TWO]][getMove()[THREE]]);
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
    if (color == BLACK || color == BLACK_KING) {
      try {
        if (getTiles()[getMove()[TWO] - 1][getMove()[THREE] + 1] == RED 
            || getTiles()[getMove()[TWO] - 1][getMove()[THREE] + 1]
                == RED_KING) {
          if (getTiles()[getMove()[TWO] - TWO][getMove()[THREE] + TWO]
              == BROWN_SPACE) {
            secondJump = true;
            return true;
          }
        }
      } catch (ArrayIndexOutOfBoundsException outOfBounds) {
        // do nothing
      }
      try {
        if (getTiles()[getMove()[TWO] - 1][getMove()[THREE] - 1] == RED 
            || getTiles()[getMove()[TWO] - 1][getMove()[THREE] - 1]
                == RED_KING) {
          if (getTiles()[getMove()[TWO] - TWO][getMove()[THREE] - TWO]
              == BROWN_SPACE) {
            secondJump = true;
            return true;
          }
        }
      } catch (ArrayIndexOutOfBoundsException outOfBounds) {
        // do nothing
      }
    }
    if (color == BLACK_KING) {
      try {
        if (getTiles()[getMove()[TWO] + 1][getMove()[THREE] + 1] == RED 
            || getTiles()[getMove()[TWO] + 1][getMove()[THREE] + 1]
                == RED_KING) {
          if (getTiles()[getMove()[TWO] + TWO][getMove()[THREE] + TWO]
              == BROWN_SPACE) {
            secondJump = true;
            return true;
          }
        }
      } catch (ArrayIndexOutOfBoundsException outOfBounds) {
        // do nothing
      }
      try {
        if (getTiles()[getMove()[TWO] + 1][getMove()[THREE] - 1] == RED 
            || getTiles()[getMove()[TWO] + 1][getMove()[THREE] - 1]
                == RED_KING) {
          if (getTiles()[getMove()[TWO] + TWO][getMove()[THREE] - TWO]
              == BROWN_SPACE) {
            secondJump = true;
            return true;
          }
        }
      } catch (ArrayIndexOutOfBoundsException outOfBounds) {
        // do nothing
      }
    }

    if (color == RED || color == RED_KING) {
      try {
        if (getTiles()[getMove()[TWO] + 1][getMove()[THREE] + 1] == BLACK 
            || getTiles()[getMove()[TWO] + 1][getMove()[THREE] + 1]
                == BLACK_KING) {
          if (getTiles()[getMove()[TWO] + TWO][getMove()[THREE] + TWO]
              == BROWN_SPACE) {
            secondJump = true;
            return true;
          }
        }
      } catch (ArrayIndexOutOfBoundsException outOfBounds) {
        // do nothing
      }
      try {
        if (getTiles()[getMove()[TWO] + 1][getMove()[THREE] - 1] == BLACK 
            || getTiles()[getMove()[TWO] + 1][getMove()[THREE] - 1]
                == BLACK_KING) {
          if (getTiles()[getMove()[TWO] + TWO][getMove()[THREE] - TWO]
              == BROWN_SPACE) {
            secondJump = true;
            return true;
          }
        }
      } catch (ArrayIndexOutOfBoundsException outOfBounds) {
        // do nothing
      }
    }
    if (color == RED_KING) {
      try {
        if (getTiles()[getMove()[TWO] - 1][getMove()[THREE] + 1] == BLACK 
            || getTiles()[getMove()[TWO] - 1][getMove()[THREE] + 1]
                == BLACK_KING) {
          if (getTiles()[getMove()[TWO] - TWO][getMove()[THREE] + TWO]
              == BROWN_SPACE) {
            secondJump = true;
            return true;
          }
        }
      } catch (ArrayIndexOutOfBoundsException outOfBounds) {
        // do nothing
      }

      try {
        if (getTiles()[getMove()[TWO] - ONE][getMove()[THREE] - ONE] == BLACK 
            || getTiles()[getMove()[TWO] - 1][getMove()[THREE] - 1] 
                == BLACK_KING) {
          if (getTiles()[getMove()[TWO] - TWO][getMove()[THREE] - TWO] 
              == BROWN_SPACE) {
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
  public static void turn() {
    if (turn == BLACK) {
      turnEnd = false;
      secondJump = false;     
      turn = RED;
    } /*else {
      turnEnd = false;
      secondJump = false;
      turn = BLACK;
      ai();
    } */
    ai();
    turn=BLACK;
  }
  
  public static void ai() {
    tiles=AIinterface.ai(tiles);
  }
  

  /** 
  * Swaps the two values of the tiles for a normal move.
  * 
  *  @param firstX  The x value of the starting location
  *  @param firstY  The y value of the starting location
  *  @param secondX  The x value for the ending location
  *  @param secondY  The y value for the 
  **/
  public static void swapTiles(final int firstX,
      final int firstY, final int secondX,
      final int secondY) {
    int tempTileValue = getTiles()[firstX][firstY];

    getTiles()[firstX][firstY] = getTiles()[secondX][secondY];
    getTiles()[secondX][secondY] = tempTileValue;
  }

  /** 
  * Checks if either player has no checkers left. 
  * Sends winner message to DisplayWindow().
  * 
  *  @param ptiles  the game board that will be checked for a winner
  *  @return true or false depending on if a player has won
  *  @see boolean
  **/
  public static boolean playerWon(final int[][] ptiles) {
    // int noWinner = 0;
    int blackCheckers = 0;
    int redCheckers = 0;

    for (int xtile = 0; xtile < EIGHT; xtile++) {
      for (int ytile = 0; ytile < EIGHT; ytile++) {
        if ((ptiles[xtile][ytile] == BLACK) 
            || ((ptiles[xtile][ytile] == BLACK_KING))) {
          blackCheckers = 1; // At least 1 Black checker still exists
        }
        if ((ptiles[xtile][ytile] == RED) 
            || (ptiles[xtile][ytile] == RED_KING)) {
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
  public static void playSound(final String fileName) {
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
   * @param xtile  x-coordinate of tile wanting to be set
   * @param ytile  y-coordinate of tile wanting to be set
   * @param value  The value that is going to be set at tile location.
   */
  public static void setTile(final int xtile,
      final int ytile, final int value) {
    getTiles()[xtile][ytile] = value;
  }
  

  /** 
  * Gets tiles x and y's contents.
  * 
  * @param xtile  x-coordinate of the tile
  * @param ytile  y-coordinate of the tile
  * @return  The tile indicated by the coordinates
  * @see int
  **/
  public static int getTile(final int xtile, final int ytile) {
    return getTiles()[xtile][ytile];
  }

  /** 
   * Getter for move array.
   * 
   * @return move array. **/
  public static int[] getMove() {
    return move;
  }

  //CHECKSTYLE:OFF
  /** 
   * Setter for move array.
   * 
   * @param move array. **/
  public static void setMove(final int[] move) {
    Game.move = move;
  }
  //CHECKSTYLE:ON

  /** 
   * Setter for tiles array.
   * 
   * @return tiles array. **/
  public static int[][] getTiles() {
    return tiles;
  }

  //CHECKSTYLE:OFF
  /** 
   * Setter foe tiles array.
   * 
   * @param tiles array. **/
  public static void setTiles(final int[][] tiles) {
    Game.tiles = tiles;
  }
  //CHECKSTYLE:ON
  
  
}