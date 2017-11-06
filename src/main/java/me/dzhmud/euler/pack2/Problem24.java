package me.dzhmud.euler.pack2;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.FactorialUtils;

import java.util.Arrays;

/**
 * Lexicographic permutations
 *

 A permutation is an ordered arrangement of objects.
 For example, 3124 is one possible permutation of the digits 1, 2, 3 and 4.
 If all of the permutations are listed numerically or alphabetically, we call it lexicographic order.
 The lexicographic permutations of 0, 1 and 2 are:

 012   021   102   120   201   210

 What is the millionth lexicographic permutation of the digits 0, 1, 2, 3, 4, 5, 6, 7, 8 and 9?

 *
 * @author dzhmud
 */
public class Problem24 implements EulerSolution {

	public static void main(String[] args) {
		System.out.println(new Problem24().getAnswer());
	}

	@Override
	public String getAnswer() {
		return getNthPermutation(new int[]{0,1,2,3,4,5,6,7,8,9}, 1000*1000);
	}

	/**
	 * As we start with 0123456789 we will have 9! permutations with leading 0,
	 * next is 1023456789, again 9! permutations with leading 1, etc..
	 * When total permutations count does not allow to change current digit, take next digit position.
	 * Repeat until permutations count is reached.
	 * @param value int array of digits that can be used in permutation.
	 * @param totalPermutations permutations count
	 * @return value after {@param totalPermutations} of lexicographic permutations.
	 */
	private String getNthPermutation(int[] value, int totalPermutations) {
		if (totalPermutations > FactorialUtils.factorial(value.length).intValue())
			throwIllegalPermutationCountException(value.length);
		totalPermutations--;
		for (int i = 0; i < value.length-1; i++) {
			int ordinal = value.length - i;
			int currentDigitPermutations = FactorialUtils.factorial(ordinal-1).intValue();
			while (totalPermutations >= currentDigitPermutations) {
				int currentDigit = value[i];
				//find minimal value in the reminder that exceeds current digit.
				Arrays.sort(value, i+1, value.length);
				for (int j = i+1; j < value.length; j++) {
					if (value[j] > value[i]) {
						value[i] = value[j];
						value[j] = currentDigit;
						Arrays.sort(value, i+1, value.length);
						totalPermutations -= currentDigitPermutations;
						break;
					}
				}
			}
		}
		StringBuilder sb = new StringBuilder("");
		for (int digit : value) {
			sb.append(digit);
		}
		return sb.toString();
	}

	private void throwIllegalPermutationCountException(int digitsCount) {
		throw new IllegalArgumentException(
				String.format(
						"There are only %s permutations available with %s digits",
						FactorialUtils.factorial(digitsCount).intValue(),
						digitsCount
				)
		);
	}

}
