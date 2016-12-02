package checkers;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Brandon Griggs, Jacob Craffey, Nick Frein.
 * 
 **/

/**
 * Class for displaying the winner of the checkers game.
 * When called, it will create a window displaying who won.
 */
final class DisplayWinner {
  
  /** Intrgers. **/
  private static final int HUNDRED_FIFTY = 150;
  /** Intrgers. **/
  private static final int TWO_HUNDRED = 200;
  /** Intrgers. **/
  private static final int FOUR_HUNDRED = 400;
  
  
  /** Constructor used to prevent check style error. **/  
  private DisplayWinner() {
    
  }
  
  /** 
  * Creates a window that displays who won the game. 
  * 
  * @param winner  a string indicating who won.
  **/
  public static void display(final String winner) {

    JFrame frame3 = new JFrame("Winner");
    frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame3.setSize(HUNDRED_FIFTY, HUNDRED_FIFTY);
    frame3.setLocation(FOUR_HUNDRED, TWO_HUNDRED);
    frame3.setLayout(new FlowLayout());
    frame3.setVisible(true);

    JPanel panel3 = new JPanel();
    panel3.setLayout(new FlowLayout());
    frame3.add(panel3);

    JLabel label3 = new JLabel(winner);
    JButton button3 = new JButton("OK");

    panel3.add(label3);
    panel3.add(button3);
  }
}