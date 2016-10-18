package checkers;

/**
  * @author Brandon Griggs, Jacob Craffey, Nick Frein. 
  */

/**
  * Class for getting the game rolling. Starts out by letting the
  * players select their color. The game progresses in other classes
  * through this root start.
  */
final class StartGame {
  
  /** Constructor used to prevent check style error. **/
  StartGame() {
    // does nothing
  }
  
  /**
   * main function of the class. Carries out tasks as described in the
   * class javadoc.
   * 
   * @param args standard notation
   */
  public static void main(final String [] args) {
    //DisplayWinner.display("Player 1 Wins!");  // test for DisplayWinner window
    new ChooseColor(); // GUI is activated once colors are chosen
  }
}