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

public class MainMenu extends JPanel {
  
  /** Integers. **/
  private static final int ONE_FIFTY = 150;
  /** Integers. **/
  private static final int SIX_HUNDRED = 600;
  
  boolean playerVsComputer = false;
  
  public MainMenu() {
    final String[] players = {"Player vs Player", "Player vs Computer"};
    // boolean playerVsComputer = false;
    
    JFrame frame2 = new JFrame("Checkers");
    frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame2.setSize(SIX_HUNDRED, ONE_FIFTY);
    frame2.setLayout(new FlowLayout());
    frame2.setVisible(true);
  
    JPanel panel2 = new JPanel();
    panel2.setLayout(new FlowLayout());
    frame2.add(panel2);
  
    JComboBox<String> comboBox = new JComboBox<String>(players);
  
    comboBox.setMaximumSize(comboBox.getPreferredSize());
    comboBox.setVisible(true);
  
    JLabel player1Label = new JLabel("Select Game Mode");
    JButton button = new JButton("OK");
  
    panel2.add(player1Label);
    panel2.add(comboBox);
    panel2.add(button);
    frame2.setVisible(true);
    
    button.addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent pressOk) {
        if (comboBox.getSelectedIndex() == 0) {
          playerVsComputer = false;
        }
        if (comboBox.getSelectedIndex() == 1) {
          playerVsComputer = true;
        }
        ChooseColor.choose(playerVsComputer);
      }
    });
  }
}
