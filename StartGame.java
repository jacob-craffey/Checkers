package checkers;

/**
  * @author Brandon Griggs, Jacob Craffey, Nick Frein. 
  */

//CHECKSTYLE:OFF
/**
  * Class for getting the game rolling. Starts out by letting the
  * players select their color. The game progresses in other classes
  * through this root start.
  */
public class StartGame {
  //CHECKSTYLE:ON
  
  /**
   * main function of the class. Carries out tasks as described in the
   * class javadoc.
   * 
   * @param args standard notation
   */
  public static void main(final String [] args) {
    //DisplayWinner.display("Player 1 Wins!");  // test for DisplayWinner window
    new MainMenu(); // GUI is activated once colors are chosen
  }
}