package me.dzhmud.euler;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Double-base palindromes
 *

 The decimal number, 585 = 1001001001 (binary), is palindromic in both bases.

 Find the sum of all numbers, less than one million, which are palindromic in base 10 and base 2.

 (Please note that the palindromic number, in either base, may not include leading zeros.)

 *
 * @author dzhmud
 */
public class Problem36 implements EulerSolution {

	public static void main(String[] args) {
		System.out.println(new Problem36().getAnswer());
	}

	@Override
	public String getAnswer() {
		//we have to check only odd values, as even will end with zero in binary form.
		return "" + IntStream.range(1,1000*1000)
				.filter(x -> isPalindrom(String.valueOf(x)))
				.filter(x -> isPalindrom(encodeInBinary(x)))
				.sum();
	}

	private static boolean isPalindrom(String value) {
//		return new StringBuilder(value).reverse().equals(value);
		for (int i = 0; i < value.length() / 2; i++) {
			if (value.charAt(i) != value.charAt(value.length() - 1 - i))
				return false;
		}
		return true;
	}

	private static int[] powersOfTwo = new int[0];
	static {
		int value = 1;
		while (value < 1000*1000) {
			powersOfTwo = Arrays.copyOf(powersOfTwo, 1 + powersOfTwo.length);
			powersOfTwo[powersOfTwo.length - 1] = value;
			value <<= 1;
		}
		//reverse array for more usability
		List<Integer> values = Arrays.stream(powersOfTwo).boxed().collect(Collectors.toList());
		values.sort(Comparator.reverseOrder());
		powersOfTwo = values.stream().mapToInt(x->x).toArray();
	}

	private static String encodeInBinary(int value) {
		StringBuilder sb = new StringBuilder(20);
		for (int powerOf2 : powersOfTwo) {
			if (value >= powerOf2) {
				sb.append(1);
				value -= powerOf2;
			} else {
				if (sb.length() > 0)
					sb.append(0);
			}
		}
		return sb.toString();
	}

}
