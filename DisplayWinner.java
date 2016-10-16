
package checkers;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Brandon Griggs, Jacob Craffey, Nick Frein 
 * 
 * Class for displaying the winner of the checkers game.
 * When called, it will create a window displaying who won.
 */
public class DisplayWinner {
	  /** 
	   * Creates a window that displays who won the game. 
	   * 
	   * @param winner  a string indicating who won.
	   **/
  public static void display(final String winner) {

    JFrame frame3 = new JFrame("Winner");
    frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame3.setSize(150, 150);
    frame3.setLocation(400, 200);
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