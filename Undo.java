package checkers;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
/**
 * @author Brandon Griggs, Jacob Craffey, Nick Frein 
 *
 * Class for setting up and executing the undo feature
 * of the checkers game.
 */
public class Undo {
	/** Queue used to keep track of previous game boards for undo.**/
	private static BlockingQueue<int[][]> queue 
	= new ArrayBlockingQueue<int[][]>(2);

	/**
	 * Method for adding the current game board to a stack so it can be
	 * retrieved when undo is called. 
	 * 
	 * @param board  the game board
	 **/
	public final void add(final int[][] board) {
		queue.add(board);
	}

	/**
	 * Method executing the undo action by taking the previous board
	 * from the queue.
	 */
	public final void undoAction() {
		try {
			queue.take();
		} catch (InterruptedException e) {
			System.out.println("Cannot undo!");
			return;
		}
	}
}