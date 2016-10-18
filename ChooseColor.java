package checkers;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**suppress a needless warning.**/
@SuppressWarnings("serial")
/**
 * @author Brandon Griggs, Jacob Craffey, Nick Frein 
 *
 * Class for letting players choose the color of their liking
 * for their checkers.
 */
public class ChooseColor extends JPanel {
  /** color of player 1.**/
  private Color color1 = Color.RED; 
  /** color of player 2.**/
  private Color color2 = Color.BLUE;
  
  /** Integers. **/
  private static final int THREE = 3;
  /** Integers. **/
  private static final int FOUR = 4;
  /** Integers. **/
  private static final int FIVE = 5;
  /** Integers. **/
  private static final int SIX = 6;
  /** Integers. **/
  private static final int SEVEN = 7;
  /** Integers. **/
  private static final int EIGHT = 8;
  /** Integers. **/
  private static final int ONE_FIFTY = 150;
  /** Integers. **/
  private static final int SIX_HUNDRED = 600;

  /**
   *  Creates two drop down windows that let players choose 
   *  their checkers' colors. 
   **/
  public /*Color*/ ChooseColor() {
    final String[] colors = {"Red", "Orange", "Yellow", 
        "Green", "Blue", "Purple", "Pink", "Black"};

    JFrame frame2 = new JFrame("Colors");
    frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame2.setSize(SIX_HUNDRED, ONE_FIFTY);
    frame2.setLayout(new FlowLayout());
    frame2.setVisible(true);

    JPanel panel2 = new JPanel();
    panel2.setLayout(new FlowLayout());
    frame2.add(panel2);

    JComboBox<String> comboBox = new JComboBox<String>(colors);
    JComboBox<String> comboBox2 = new JComboBox<String>(colors);

    comboBox.setMaximumSize(comboBox.getPreferredSize());
    comboBox.setVisible(true);
    comboBox2.setMaximumSize(comboBox.getPreferredSize());
    comboBox2.setVisible(true);

    JLabel player1Label = new JLabel("Select Color for Player 1");
    JLabel player2Label = new JLabel("Select Color for Player 2");
    JButton button = new JButton("OK");

    panel2.add(player1Label);
    panel2.add(comboBox);
    panel2.add(player2Label);
    panel2.add(comboBox2);
    panel2.add(button);
    frame2.setVisible(true);


    button.addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent pressOk) {
        if (comboBox.getSelectedIndex() == 0) {
          color1 = Color.RED;
        }
        if (comboBox.getSelectedIndex() == 1) {
          color1 = Color.ORANGE;
        }
        if (comboBox.getSelectedIndex() == 2) {
          color1 = Color.YELLOW;
        }
        if (comboBox.getSelectedIndex() == THREE) {
          color1 = Color.GREEN;
        }
        if (comboBox.getSelectedIndex() == FOUR) {
          color1 = Color.BLUE;
        }
        if (comboBox.getSelectedIndex() == FIVE) {
          color1 = Color.MAGENTA;
        }
        if (comboBox.getSelectedIndex() == SIX) {
          color1 = Color.PINK;
        }
        if (comboBox.getSelectedIndex() == SEVEN) {
          color1 = Color.BLACK;
        }

        if (comboBox2.getSelectedIndex() == 0) {
          color2 = Color.RED;
        }
        if (comboBox2.getSelectedIndex() == 1) {
          color2 = Color.ORANGE;
        }
        if (comboBox2.getSelectedIndex() == 2) {
          color2 = Color.YELLOW;
        }
        if (comboBox2.getSelectedIndex() == THREE) {
          color2 = Color.GREEN;
        }
        if (comboBox2.getSelectedIndex() == FOUR) {
          color2 = Color.BLUE;
        }
        if (comboBox2.getSelectedIndex() == FIVE) {
          color2 = Color.MAGENTA;
        }
        if (comboBox2.getSelectedIndex() == SIX) {
          color2 = Color.PINK;
        }
        if (comboBox.getSelectedIndex() == SEVEN) {
          color1 = Color.BLACK;
        }

        //new GUI();
        Gui.change(color1, color2);
      }
    });
    //return color1;
  }
}