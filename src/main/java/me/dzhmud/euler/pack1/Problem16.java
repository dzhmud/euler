package me.dzhmud.euler.pack1;

import me.dzhmud.euler.EulerSolution;

import java.math.BigInteger;

/**
 *

 2^15 = 32768 and the sum of its digits is 3 + 2 + 7 + 6 + 8 = 26.

 What is the sum of the digits of the number 2^1000?

 *
 * @author dzhmud
 */
public class Problem16 implements EulerSolution {

	public static void main(String[] args) {
		EulerSolution.measureTime(new Problem16()::getAnswer);
	}

	@Override
	public String getAnswer() {
		final BigInteger value = BigInteger.valueOf(2).pow(1000);
		final int zero = '0';
		return "" + value.toString().chars().map(code -> code - zero).sum();
	}

}
