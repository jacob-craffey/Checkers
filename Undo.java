package checkers;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Undo {
	static BlockingQueue<int[][]> queue = new ArrayBlockingQueue<int[][]>(2);

	public void add(int[][] board) {
		queue.add(board);
	}

	public void undoAction() {
		try {
			queue.take();
		} catch (InterruptedException e) {
			System.out.println("Cannot undo!");
			return;
		}
	}
}