package me.dzhmud.euler.pack1;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.FileUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *

 By starting at the top of the triangle below and moving to adjacent numbers on the row below, the maximum total from top to bottom is 23.

    3
   7 4
  2 4 6
 8 5 9 3

 That is, 3 + 7 + 4 + 9 = 23.

 Find the maximum total from top to bottom of the triangle below:
 ${include Problem18.txt}

 NOTE: As there are only 16384 routes, it is possible to solve this problem by trying every route. However, Problem 67, is the same challenge with a triangle containing one-hundred rows; it cannot be solved by brute force, and requires a clever method! ;o)

 *
 * @author dzhmud
 */
public class Problem18 implements EulerSolution {

	public static void main(String[] args) {
		new Problem18().measureTime();
	}

	@Override
	public String getAnswer() {
		return getAnswer("Problem18.txt");
	}

	public String getAnswer(String fileName) {
		int[][] values = readFile(fileName);
		for (int i = 1; i < values.length; i++) {
			int[] previousRow = values[i-1];
			int[] currentRow = values[i];
			currentRow[0] += previousRow[0];
			currentRow[currentRow.length-1] += previousRow[previousRow.length-1];
			for (int j = 1; j < currentRow.length-1; j++) {
				currentRow[j] += Math.max(previousRow[j-1], previousRow[j]);
			}
		}
		return "" + Arrays.stream(values[values.length-1]).max().orElse(0);
	}

	private static int[][] readFile(String fileName) {
		List<String> rows = FileUtils.getContents(fileName, Collectors.toList());
		int[][] result = new int[rows.size()][];
		for (int i = 0; i < rows.size(); i++) {
			result[i] = Arrays.stream(rows.get(i).split("\\s")).mapToInt(Integer::valueOf).toArray();
		}
		return result;
	}

}
