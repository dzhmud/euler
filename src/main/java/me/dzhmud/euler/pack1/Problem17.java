package me.dzhmud.euler.pack1;

import me.dzhmud.euler.EulerSolution;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 *

 If the numbers 1 to 5 are written out in words: one, two, three, four, five, then there are 3 + 3 + 5 + 4 + 4 = 19 letters used in total.

 If all the numbers from 1 to 1000 (one thousand) inclusive were written out in words, how many letters would be used?

 NOTE: Do not count spaces or hyphens. For example, 342 (three hundred and forty-two) contains 23 letters and 115 (one hundred and fifteen) contains 20 letters. The use of "and" when writing out numbers is in compliance with British usage.

 *
 * @author dzhmud
 */
public class Problem17 implements EulerSolution {

	public static void main(String[] args) {
		EulerSolution.measureTime(new Problem17()::getAnswer);
	}

	private int THOUSAND = "one thousand".replace(" ","").length();
	private int[] oneToTen = getLengths("one", "two", "three", "four", "five", "six", "seven", "eight", "nine");
	private int[] tenToTweny = getLengths("eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen");
	private int[] tens = getLengths("ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety");
	private int and = "and".length();
	private int hundred = "hundred".length();

	private static int[] cache = new int[100];

	@Override
	public String getAnswer() {
		return "" + IntStream.rangeClosed(1, 1000).map(this::getLength).sum();
	}

	private int getLength(int value) {
		int result = 0;
		if (value < 100) {
			if (cache[value] > 0)
				return cache[value];
			if (value > 10 && value < 20)
				return tenToTweny[value-11];
			if (value >= 10)
				result += tens[value/10 - 1];
			if (value % 10 > 0)
				result += oneToTen[value % 10 - 1];
			cache[value] = result;
		} else if (value >= 100 && value < 1000) {
			result = oneToTen[value/100 - 1] + hundred;
			int remainder = value % 100;
			if (remainder > 0)
				result += and + getLength(remainder);
		} else if (value == 1000)
			result = THOUSAND;
		return result;
	}

	private static int[] getLengths(String... array) {
		return Arrays.stream(array).mapToInt(String::length).toArray();
	}

}
