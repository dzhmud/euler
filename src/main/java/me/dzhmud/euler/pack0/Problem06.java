package me.dzhmud.euler.pack0;

import me.dzhmud.euler.EulerSolution;

import java.util.stream.IntStream;

/**
 *

 The sum of the squares of the first ten natural numbers is,
 1^2 + 2^2 + ... + 10^2 = 385

 The square of the sum of the first ten natural numbers is,
 (1 + 2 + ... + 10)^2 = 55^2 = 3025

 Hence the difference between the sum of the squares of the first ten natural numbers and the square of the sum is 3025 − 385 = 2640.

 Find the difference between the sum of the squares of the first one hundred natural numbers and the square of the sum.

 *
 * @author dzhmud
 */
public class Problem06 implements EulerSolution {

	public static void main(String[] args) {
		System.out.println(new Problem06().getAnswer());
	}

	@Override
	public String getAnswer() {
		final long sumOfSquares = IntStream.rangeClosed(1, 100).map(x -> x*x).reduce(Integer::sum).orElse(-1);
		final long sum = IntStream.rangeClosed(1, 100).reduce(Integer::sum).orElse(-1);
		return "" + (sum*sum - sumOfSquares);
	}

}
