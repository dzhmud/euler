package me.dzhmud.euler;

import java.util.Comparator;
import java.util.stream.IntStream;

/**
 * Pandigital multiples
 *

 Take the number 192 and multiply it by each of 1, 2, and 3:

 192 × 1 = 192
 192 × 2 = 384
 192 × 3 = 576

 By concatenating each product we get the 1 to 9 pandigital, 192384576.
 We will call 192384576 the concatenated product of 192 and (1,2,3)

 The same can be achieved by starting with 9 and multiplying by 1, 2, 3, 4, and 5,
 giving the pandigital, 918273645, which is the concatenated product of 9 and (1,2,3,4,5).

 What is the largest 1 to 9 pandigital 9-digit number that can be formed as the
 concatenated product of an integer with (1,2, ... , n) where n > 1?

 *
 * @author dzhmud
 */
public class Problem38 implements EulerSolution {

	public static void main(String[] args) {
		System.out.println(new Problem38().getAnswer());
	}

	@Override
	public String getAnswer() {
		//it can be faster to generate pandigits, starting from 987654321 and checking if they can be obtained
		//in the way described
		return IntStream.range(1,10000)
				.mapToObj(Problem38::generateValue)
				.filter(Problem38::checkPandigital)
				.max(Comparator.naturalOrder())
				.orElseThrow(IllegalArgumentException::new);
	}

	private static String generateValue(int base) {
		StringBuilder sb = new StringBuilder(9);
		sb.append(base);
		for (int i = 2; sb.length() < 9; i++) {
			sb.append(base*i);
		}
		return sb.toString();
	}

	private static char[] digits = new char[]{'1','2','3','4','5','6','7','8','9'};

	private static boolean checkPandigital(String value) {
		if (value.length() == 9) {
			for (char digit : digits) {
				if (value.indexOf(digit) < 0)
					return false;
			}
			return true;
		}
		return false;
	}

}
