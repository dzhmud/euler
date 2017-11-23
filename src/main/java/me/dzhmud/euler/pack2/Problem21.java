package me.dzhmud.euler.pack2;

import me.dzhmud.euler.EulerSolution;

import java.util.stream.IntStream;

import static me.dzhmud.euler.util.FactorUtils.getDivisorsSum;

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
		new Problem21().measureTime();
	}

	@Override
	public String getAnswer() {
		return "" + IntStream.range(1, 10000)
				.filter(value -> {
					int divSum = getDivisorsSum(value);
					return value != divSum && value == getDivisorsSum(divSum);
				})
				.sum();
	}

}
