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
 * @author Brandon Griggs, Jacob Craffey, Nick Frein 
 *
 * Class for setting up and executing the save and load features
 * of the checkers game.
 */
public class SaveLoad {

	/**
	 * method to save the current game to a file that can be loaded later.
	 */
	public static void save() {
		Writer writer = null;

		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("./src/checkers/Save.txt"), "utf-8"));
			for (int xTile = 0; xTile < 8; xTile++) {
				for (int yTile = 0; yTile < 8; yTile++) {
					writer.write(Integer.toString(Game.getTile(xTile, yTile)));
				}
			}

		} catch (IOException ex) {
			// report
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
				/* ignore */ }
		}

	}
	/**
	 * method to load a game that was saved at an earlier time.
	 */
	public static void load() {
		List<Integer> value = new ArrayList<Integer>();
		int xTile = 0;
		int yTile = -1;
		try {
			for (String line : Files.readAllLines(Paths.get(
					"./src/checkers/Save.txt"))) {
			    for (String part : line.split("")) {
			        Integer i = Integer.valueOf(part);
			        value.add(i);
			    }
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int count = 0; count < value.size() - 8; count++) {
			if (yTile == 7) {
				yTile = -1;
				xTile++;
			} 
			yTile++;
			Game.setTile(xTile, yTile, value.get(count));
		}
	}
}