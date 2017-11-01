package me.dzhmud.euler;

import me.dzhmud.euler.util.PrimeUtils;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;

/**
 * Truncatable primes
 *

 The number 3797 has an interesting property. Being prime itself, it is possible to continuously remove digits
 from left to right, and remain prime at each stage: 3797, 797, 97, and 7.
 Similarly we can work from right to left: 3797, 379, 37, and 3.

 Find the sum of the only eleven primes that are both truncatable from left to right and right to left.

 NOTE: 2, 3, 5, and 7 are not considered to be truncatable primes.

 *
 * @author dzhmud
 */
public class Problem37 implements EulerSolution {

	public static void main(String[] args) {
		System.out.println(new Problem37().getAnswer());
	}

	@Override
	public String getAnswer() {
		return "" + IntStream.iterate(11, x -> x+1)
				.filter(isTruncatablePrime)
				.limit(11)
				.sum();
	}


	private static IntPredicate isTruncatablePrime = value -> {
		if (!PrimeUtils.isPrime_v1(value))
			return false;
		int divisor = 10;

		while(divisor < value) {
			if (!PrimeUtils.isPrime_v1(value / divisor))//3797 -> 379, 37, 3.
				return false;
			if (!PrimeUtils.isPrime_v1(value % divisor))//3797 -> 797, 97, 7.
				return false;
			divisor *= 10;
		}
		return true;
	};
}
