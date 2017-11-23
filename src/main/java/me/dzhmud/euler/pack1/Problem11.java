package me.dzhmud.euler.pack1;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.FileUtils;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import static java.util.function.IntUnaryOperator.identity;

/**
 * In the 20×20 grid below, four numbers along a diagonal line have been marked in red.
 * #{include Problem11.txt}
 * The product of these numbers is 26 × 63 × 78 × 14 = 1788696.
 * What is the greatest product of four adjacent numbers in the same direction (up, down, left, right, or diagonally) in the 20×20 grid?
 *
 * @author dzhmud
 */
public class Problem11 implements EulerSolution {

	public static void main(String[] args) {
		EulerSolution.measureTime(new Problem11()::getAnswer);
	}

	private static final int SIZE = 20;

	private static final int LINE_SIZE = 4;

	@Override
	public String getAnswer() {
		int result = 0;
		final int[][] grid = readGrid();
		final Point[] points = new Point[LINE_SIZE];
		final ToIntFunction<Point> getValueByPoint = point -> grid[point.x][point.y];

		for (int x = 0; x < SIZE; x++) {//row number
			for (int y = 0; y < SIZE; y++) {//column number
				points[0] = new Point(x, y);
				for (Direction direction : Direction.values()) {
					for (int i = 1; i < LINE_SIZE; i++) {
						points[i] = points[i-1].getNextPoint(direction);
					}
					if (points[LINE_SIZE-1].isInRange(SIZE)) {
						int product = Arrays.stream(points)
								.mapToInt(getValueByPoint)
								.reduce((left, right) -> left * right)
								.orElseThrow(IllegalArgumentException::new);
						result = Math.max(result, product);
					}
				}
			}
		}
		return "" + result;
	}



	private int[][] readGrid() {
		int[][] result = new int[SIZE][SIZE];
		List<String> rows = FileUtils.getContents("Problem11.txt", Collectors.toList());
		assert rows.size() == SIZE;
		for (int i = 0; i < rows.size(); i++) {
			result[i] = Arrays.stream(rows.get(i).split("\\s")).mapToInt(Integer::valueOf).toArray();
		}
		return result;
	}

	private static final IntUnaryOperator increment = (i -> ++i), decrement = (i -> --i);

	private enum Direction {
		UPLEFT(decrement, decrement),
		UP(decrement, identity()),
		UPRIGHT(decrement, increment),
		LEFT(identity(), decrement),
		RIGHT(identity(), increment),
		DOWNLEFT(increment, decrement),
		DOWN(increment, identity()),
		DOWNRIGHT(increment, increment);

		private final IntUnaryOperator xOp, yOp;

		Direction(IntUnaryOperator xOp, IntUnaryOperator yOp) {
			this.xOp = xOp;
			this.yOp = yOp;
		}

	}

	private static class Point {

		final int x, y;

		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		Point getNextPoint(Direction direction) {
			return new Point(direction.xOp.applyAsInt(x), direction.yOp.applyAsInt(y));
		}

		boolean isInRange(int size) {
			return x >= 0 && y >= 0 && x < size && y < size;
		}

		@Override
		public String toString() {
			return "Point["+x+","+y+"]";
		}

	}

}
