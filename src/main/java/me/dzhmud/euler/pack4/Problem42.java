package me.dzhmud.euler.pack4;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.FileUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Coded triangle numbers
 * {@see https://projecteuler.net/problem=42}
 *
 * @author dzhmud
 */
public class Problem42 implements EulerSolution {

	public static void main(String[] args) {
		new Problem42().measureTime();
	}

	@Override
	public String getAnswer() {
		final String names = FileUtils.getContents("Problem42.txt", Collectors.joining());
		final List<String> namesList = new ArrayList<>(Arrays.asList(names.split("\"(,\")?")));
		namesList.remove(0);//nuance of splitting
		{
			final int longest = namesList.stream().max(Comparator.comparing(String::length)).orElse("").length();
			setupTriangles(longest);
		}
		return "" + namesList.stream().filter(Problem42::isTriangleWord).count();
	}

	private static boolean isTriangleWord(String word) {
		int sum = 0;
		for (char c : word.toCharArray())
			sum += (c - 'A' +1);
		return triangleValues[sum];
	}

	private static boolean[] triangleValues = null;

	private static void setupTriangles(final int longestWordLength) {
		final int limit = longestWordLength * ('Z' - 'A' +1);
		triangleValues = new boolean[limit];
		for (int i = 1; ; i++) {
			int value = i*(i+1)/2;
			if (value >= limit)
				break;
			triangleValues[value] = true;
		}
	}

}
