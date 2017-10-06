package me.dzhmud.euler;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static me.dzhmud.euler.util.FactorUtils.getDivisors;
/**
 *

 Let d(n) be defined as the sum of proper divisors of n (numbers less than n which divide evenly into n).
 If d(a) = b and d(b) = a, where a ≠ b, then a and b are an amicable pair and each of a and b are called amicable numbers.

 For example, the proper divisors of 220 are 1, 2, 4, 5, 10, 11, 20, 22, 44, 55 and 110; therefore d(220) = 284. The proper divisors of 284 are 1, 2, 4, 71 and 142; so d(284) = 220.

 Evaluate the sum of all the amicable numbers under 10000.

 *
 * @author dzhmud
 */
public class Problem21 implements EulerSolution {

	public static void main(String[] args) {
		System.out.println(new Problem21().getAnswer());
	}

	@Override
	public String getAnswer() {
		return "" + IntStream.range(1, 10000)
				.filter(value -> {
					int divSum = getDivisorsSum(value);
					return divSum != value && value == getDivisorsSum(divSum);
				})
				.sum();
	}

	private int[] cache = new int[10000];

	private int getDivisorsSum(int value) {
		if (value == 0)
			return 0;
		if (value < 10000 && cache[value] > 0)
			return cache[value];
		final Set<Long> divisors = getDivisors(value);
		divisors.remove(Long.valueOf(value));
		final AtomicInteger sum = new AtomicInteger(0);
		divisors.forEach(aLong -> sum.addAndGet(aLong.intValue()));
		int result = sum.intValue();
		if (value < 10000)
			cache[value] = result;
		return result;
	}

}
