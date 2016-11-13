package checkers;

import java.util.ArrayList;
import java.util.Random;

public interface AIinterface{

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
  
  
  public static int[][] swapTiles(int[][] tiles, final int firstX,
    final int firstY, final int secondX,
    final int secondY) {
    
    int temp = tiles[firstX][firstY];
    tiles[firstX][firstY] = tiles[secondX][secondY];
    tiles[secondX][secondY] = temp;
    return tiles;
  }

  
  public static int[][] checkForKing(int[][] tiles) {
    int y = 0;
    for(int x=0;x<8;x++) {
      if(tiles[0][x] == BLACK) {
        tiles[0][x] = BLACK_KING;
      }
      if(tiles[7][x] == RED) {
        tiles[7][x] = RED_KING;
      }
    }
    return tiles;
  }
  

  public static int[][] ai(int[][] tiles) {
    ArrayList<Integer> aiPositions = new ArrayList();
    boolean moveWasMade=false;
    int RED = 3;

    
    for(int y=0; y<8; y++) {
      for(int x=0; x<8; x++) {
        if( (tiles[y][x] == RED) || (tiles[y][x] == RED_KING) ) {
          System.out.println("RED x:" + x + " y: " + y +" has: " + tiles[y][x]);  
          aiPositions.add(y);
          aiPositions.add(x);
          tiles=aijumpavailable(tiles,y,x);
          if( (tiles[y][x]!=RED) && (tiles[y][x]!=RED_KING) ) {
            System.out.println("move was made");
            return tiles;
          }
        }
      }
    }
    
    while(!aiPositions.isEmpty() && !moveWasMade) {
      int randomChecker = (int)(Math.random() * ((aiPositions.size()-1)));
      if (randomChecker % 2 == 1) {
        randomChecker--;
      }
      System.out.println("Random: " + randomChecker);
      System.out.println("AI Positions: " + aiPositions.size());
      System.out.println("random checker y: " + (int)aiPositions.get(randomChecker));

      
      int y1 = (int)aiPositions.get(randomChecker);
      aiPositions.remove(randomChecker);
      
      System.out.println("random checker x: " + (int)aiPositions.get(randomChecker));
      System.out.println("");
      
      int x1 = (int)aiPositions.get(randomChecker);
      aiPositions.remove(randomChecker);
      
      Random directionX = new Random();
      int randomDirectionX = directionX.nextInt((2-0)+1); // ((max - min) + 1)
      randomDirectionX--;
      if(randomDirectionX==0) {
        randomDirectionX--;
      }
      
      Random directionY = new Random();
      int randomDirectionY = directionY.nextInt((2-0)+1); // ((max - min) + 1)
      if(tiles[y1][x1]==RED_KING) {
        randomDirectionY--;
        if(randomDirectionY==0) {
          randomDirectionY--;
        }
      } else {
        randomDirectionY=1;
      }
  
// Moving Down
      if(randomDirectionY>0) {
        if(randomDirectionX<=0) {
          if((x1>0) && (y1<7)) {
            //System.out.println("leftDown: " + tiles[y1+1][x1-1]);
            if(tiles[y1+1][x1-1] == 1) {
              tiles = swapTiles(tiles, y1,x1,y1+1,x1-1);
              tiles = checkForKing(tiles);
              moveWasMade=true;
              return tiles;
            }
          } else if((x1<7) && (y1<7)) {
            if(tiles[y1+1][x1+1] == 1) {
              tiles = swapTiles(tiles,y1,x1,y1+1,x1+1);
              tiles = checkForKing(tiles);
              moveWasMade=true;
              return tiles;
            }
          }
  // Move to the right
        } else {
          if((x1<7) && (y1<7)) {
            if(tiles[y1+1][x1+1] == 1) {
              tiles = swapTiles(tiles, y1,x1,y1+1,x1+1);
              tiles = checkForKing(tiles);
              moveWasMade=true;
              return tiles;
            }
        } else if((x1>0) && (y1<7)) {
            if(tiles[y1+1][x1-1] == 1) {
              tiles = swapTiles(tiles, y1,x1,y1+1,x1-1);
              tiles = checkForKing(tiles);
              moveWasMade=true;
              return tiles;
            }
        }
      
      }
    }
      
   // Moving Up
      else {
        if(randomDirectionX<=0) {
          if((x1>0) && (y1>0)) {
            //System.out.println("leftDown: " + tiles[y1+1][x1-1]);
            if(tiles[y1-1][x1-1] == 1) {
              tiles = swapTiles(tiles, y1,x1,y1-1,x1-1);
              tiles = checkForKing(tiles);
              moveWasMade=true;
              return tiles;
            }
          } else if((x1<7) && (y1>0)) {
            if(tiles[y1-1][x1+1] == 1) {
              tiles = swapTiles(tiles, y1,x1,y1-1,x1+1);
              tiles = checkForKing(tiles);
              moveWasMade=true;
              return tiles;
            }
          }
  // Move to the right
        } else {
          if((x1<7) && (y1>0)) {
            if(tiles[y1-1][x1+1] == 1) {
              tiles = swapTiles(tiles,y1,x1,y1-1,x1+1);
              tiles = checkForKing(tiles);
              moveWasMade=true;
              return tiles;
            }
        } else if((x1>0) && (y1>0)) {
            if(tiles[y1-1][x1-1] == 1) {
              tiles = swapTiles(tiles,y1,x1,y1-1,x1-1);
              tiles = checkForKing(tiles);
              moveWasMade=true;
              return tiles;
            }
          }
        }
      
      }
    }
      tiles = checkForKing(tiles);
      return tiles;
  }
  
  public static int[][] aijumpavailable(int[][] tiles, int y, int x) {
    if( (y+2<8) && (x+2<8) ) {
      if((tiles[y+1][x+1]==BLACK  || tiles[y+1][x+1]==BLACK_KING) && (tiles[y+2][x+2]==1) ) {
        swapTiles(tiles,y,x,y+2,x+2);
        tiles[y+1][x+1] = 1;
        tiles=checkForKing(tiles);
        tiles=aijumpavailable(tiles,y+2,x+2);
        return tiles;
      }
    }
    
    if( (y+2<8) && (x-2>=0) ) {
      if ( (tiles[y+1][x-1]==BLACK || tiles[y+1][x-1]==BLACK_KING) && (tiles[y+2][x-2]==1) ) {
        tiles=swapTiles(tiles,y,x,y+2,x-2);
        tiles[y+1][x-1] = 1;
        tiles=checkForKing(tiles);
        tiles=aijumpavailable(tiles,y+2,x-2);
        return tiles;
      }
    }
    
    if( (y-2>=0) && (x+2<8) && (tiles[y][x]==RED_KING) ) {
      if( (tiles[y-1][x+1]==BLACK || tiles[y-1][x+1]==BLACK_KING) && (tiles[y-2][x+2]==1) ) {
        tiles=swapTiles(tiles,y,x,y-2,x+2);
        tiles[y-1][x+1] = 1;
        tiles=aijumpavailable(tiles,y-2,x+2);
        return tiles;
      }
    }
    
    if( (y-2>=0) && (x-2>=0) && (tiles[y][x]==RED_KING) ) {
      if ( (tiles[y-1][x-1]==BLACK || tiles[y-1][x-1]==BLACK_KING) && (tiles[y-2][x-2]==1) ) {
        tiles=swapTiles(tiles,y,x,y-2,x-2);
        tiles[y-1][x-1] = 1;
        tiles=aijumpavailable(tiles,y-2,x-2);
        return tiles;
      }
    }
    
    return tiles;
  }
}
