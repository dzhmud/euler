package me.dzhmud.euler.pack4;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.PrimeUtils;

import java.util.List;
import java.util.stream.IntStream;

import static me.dzhmud.euler.util.FactorUtils.getFactors_v1;

/**
 * Distinct primes factors
 *

 The first two consecutive numbers to have two distinct prime factors are:

 14 = 2 × 7
 15 = 3 × 5

 The first three consecutive numbers to have three distinct prime factors are:

 644 = 2² × 7 × 23
 645 = 3 × 5 × 43
 646 = 2 × 17 × 19.

 Find the first four consecutive integers to have four distinct prime factors each. What is the first of these numbers?

 *
 * @author dzhmud
 */
public class Problem47 implements EulerSolution {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		System.out.println(new Problem47().getAnswer());
		System.out.println("Solution take " + (System.currentTimeMillis() - start) / 1000 + " sec");
	}

	@Override
	public String getAnswer() {
		return "" + IntStream.iterate(2, i->i+1)
				.filter(x-> hasXPrimeFactors(x, 4))
				.filter(x-> hasXPrimeFactors(x+1, 4))
				.filter(x-> hasXPrimeFactors(x+2, 4))
				.filter(x-> hasXPrimeFactors(x+3, 4))
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}

	private static boolean hasXPrimeFactors(long value, int count) {
		List<Long> factors = getFactors(value);
		if (count == 2 && factors.contains(1L))
			factors.remove(1L);
		return count == factors.size() && factors.stream().allMatch(PrimeUtils::isPrime_v1);
	}

	private static List<Long> getFactors(long value) {
		return getFactors_v1(value, true);
	}

}
