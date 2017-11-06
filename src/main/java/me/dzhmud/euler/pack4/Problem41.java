package me.dzhmud.euler.pack4;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.PrimeUtils;

import java.util.stream.IntStream;

/**
 * Pandigital prime

 We shall say that an n-digit number is pandigital if it makes use of all the digits 1 to n exactly once.
 For example, 2143 is a 4-digit pandigital and is also prime.

 What is the largest n-digit pandigital prime that exists?
 *
 * @author dzhmud
 */
public class Problem41 implements EulerSolution {

	public static void main(String[] args) {
		//hint1: 1-9 and 1-8 pandigitals can't be prime, as sum of their digits is divisible by 3, therefore they are
		//divisible by 3 too.
		long start = System.currentTimeMillis();
		System.out.println(new Problem41().getAnswer());
		System.out.println("Solution take " + (System.currentTimeMillis() - start)/1000 + " sec");
	}

	@Override
	public String getAnswer() {
		return "" + IntStream.iterate(9876543, i->i-2)
				.filter(Problem41::isPandigital)
				.filter(PrimeUtils::isPrime_v1)
				.findFirst().orElseThrow(IllegalArgumentException::new);
	}

	private static boolean isPandigital(final int a) {
		final int[] digits = new int[9];
		int value = a;
		int digit;
		while (value > 0) {
			digit = value%10;
			if (digit == 0 || digits[digit-1] > 0) {
				return false;
			}
			digits[digit-1] +=1;
			value /= 10;
		}
		//now check that digits array is of form [1,1,1,...0?,0?]
		boolean checkZeros = false;
		for (int i : digits) {
			if (i > 1)
				return false;
			if (i == 0 && !checkZeros)
				checkZeros = true;
			if (i == 1 && checkZeros)
				return false;
		}
		return true;
	}

}
