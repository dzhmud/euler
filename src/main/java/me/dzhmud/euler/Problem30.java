package me.dzhmud.euler;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;

/**
 * Digit fifth powers.
 *

 Surprisingly there are only three numbers that can be written as the sum of fourth powers of their digits:

 1634 = 1^4 + 6^4 + 3^4 + 4^4
 8208 = 8^4 + 2^4 + 0^4 + 8^4
 9474 = 9^4 + 4^4 + 7^4 + 4^4

 As 1 = 1^4 is not a sum it is not included.

 The sum of these numbers is 1634 + 8208 + 9474 = 19316.

 Find the sum of all the numbers that can be written as the sum of fifth powers of their digits.

 *
 * @author dzhmud
 */
public class Problem30 implements EulerSolution {

	public static void main(String[] args) {
		System.out.println(new Problem30().getAnswer());
	}

	@Override
	public String getAnswer() {
		return "" + getAnswer(5);
	}

	private static int getAnswer(int power) {
		final int[] digitsPowers = new int[10];

		for (int i = 0; i < 10; i++) {
			int result = 1;
			for (int j = 0; j < power; j++)
				result *= i;
			digitsPowers[i] = result;
		}

		final IntPredicate isSumOfPowers = value ->  {
			int sum = 0;
			for (int remainder = value; remainder > 0; remainder /= 10) {
				sum += digitsPowers[remainder % 10];
			}
			return sum == value;
		};

		return IntStream.range(2, digitsPowers[9]*power).filter(isSumOfPowers).sum();
	}


}
