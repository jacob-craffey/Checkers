package checkers;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Brandon Griggs, Jacob Craffey, Nick Frein.
 *
 **/

/**
 * Class for setting up and executing the save and load features
 * of the checkers game.
 */
final class SaveLoad {
  
  /** Intrgers. **/
  private static final int SEVEN = 7;
  /** Intrgers. **/
  private static final int EIGHT = 8;
  
  /** Constructor used to prevent check style error. **/
  private SaveLoad() {
    
  }

  /**
  * method to save the current game to a file that can be loaded later.
  */
  public static void save() {
    Writer writer = null;

    try {
      writer = new BufferedWriter(new OutputStreamWriter(
          new FileOutputStream("./src/checkers/Save.txt"), "utf-8"));
      for (int xtile = 0; xtile < EIGHT; xtile++) {
        for (int ytile = 0; ytile < EIGHT; ytile++) {
          writer.write(Integer.toString(Game.getTile(xtile, ytile)));
        }
      }

    } catch (IOException ex) {
      // report
    } finally {
      try {
        writer.close();
      } catch (Exception ex) {
        /* ignore */ 
      }
    }

  }

  /**
  * method to load a game that was saved at an earlier time.
  */
  public static void load() {
    List<Integer> value = new ArrayList<Integer>();
    int xtile = 0;
    int ytile = -1;
    try {
      for (String line : Files.readAllLines(Paths.get(
          "./src/checkers/Save.txt"))) {
        for (String part : line.split("")) {
          Integer num = Integer.valueOf(part);
          value.add(num);
        }
      }
    } catch (NumberFormatException numExcept) {
      numExcept.printStackTrace();
    } catch (IOException ioExcept) {
      ioExcept.printStackTrace();
    }

    for (int count = 0; count < value.size() - EIGHT; count++) {
      if (ytile == SEVEN) {
        ytile = -1;
        xtile++;
      } 
      ytile++;
      Game.setTile(xtile, ytile, value.get(count));
    }
  }
}