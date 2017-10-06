package me.dzhmud.euler;

import static me.dzhmud.euler.util.FactorialUtils.factorial;

/**
 * Factorial digit sum
 *

 n! means n × (n − 1) × ... × 3 × 2 × 1

 For example, 10! = 10 × 9 × ... × 3 × 2 × 1 = 3628800,
 and the sum of the digits in the number 10! is 3 + 6 + 2 + 8 + 8 + 0 + 0 = 27.

 Find the sum of the digits in the number 100!

 *
 * @author dzhmud
 */
public class Problem20 implements EulerSolution {

	public static void main(String[] args) {
		System.out.println(new Problem20().getAnswer());
	}

	@Override
	public String getAnswer() {
		final int zero = '0';
		return "" + factorial(100).toString().chars().map(code -> code - zero).sum();
	}


}
