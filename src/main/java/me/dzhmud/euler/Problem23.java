package me.dzhmud.euler;

import me.dzhmud.euler.util.FactorUtils;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;

/**
 * Non-abundant sums
 *

 A perfect number is a number for which the sum of its proper divisors is exactly equal to the number.
 For example, the sum of the proper divisors of 28 would be 1 + 2 + 4 + 7 + 14 = 28, which means that 28 is a perfect number.

 A number n is called deficient if the sum of its proper divisors is less than n and it is called abundant if this sum exceeds n.

 As 12 is the smallest abundant number, 1 + 2 + 3 + 4 + 6 = 16, the smallest number that can be written as the sum of two abundant numbers is 24.
 By mathematical analysis, it can be shown that all integers greater than 28123 can be written as the sum of two abundant numbers.
 However, this upper limit cannot be reduced any further by analysis even though it is known that
 the greatest number that cannot be expressed as the sum of two abundant numbers is less than this limit.

 Find the sum of all the positive integers which cannot be written as the sum of two abundant numbers.

 *
 * @author dzhmud
 */
public class Problem23 implements EulerSolution {

	public static void main(String[] args) {
		System.out.println(new Problem23().getAnswer());
	}

	private final int limit = 28123;

	@Override
	public String getAnswer() {
		FactorUtils.setDivisorsSumCacheSize(limit);
		final boolean[] canBeSum = new boolean[limit];
		final boolean[] abundantNumbers = new boolean[limit];
		//find all abundant numbers in range
		for (int i = 12; i < limit; i++) {
			abundantNumbers[i] = isAbundant(i);
		}
		final IntPredicate isAbundant = value -> abundantNumbers[value];
		//find all possible sums of 2 abundant numbers
		IntStream.range(12, limit).filter(isAbundant).forEach(value ->
				IntStream.range(value, limit).filter(isAbundant).forEach(value1 -> {
					int sum = value+value1;
					if (sum < limit)
						canBeSum[sum] = true;
				})
		);
		//find needed sum
		return "" + IntStream.range(1, limit).filter(value -> !canBeSum[value]).sum();
	}

	private boolean isAbundant(int value) {
		return value < FactorUtils.getDivisorsSum(value);
	}

}
