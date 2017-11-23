package me.dzhmud.euler.pack6;

import me.dzhmud.euler.EulerSolution;

import java.math.BigInteger;

/**
 * Powerful digit counts
 *
 The 5-digit number, 16807=7^5, is also a fifth power. Similarly, the 9-digit number, 134217728=8^9, is a ninth power.
 How many n-digit positive integers exist which are also an nth power?

 * @author dzhmud
 */
public class Problem63 implements EulerSolution {

	public static void main(String[] args) {
		new Problem63().measureTime();
	}

	@Override
	public String getAnswer() {
		//hint1: it is clear that only powers of base 1-9 match the condition
		//hint2: calculate powers until value length become less than power -> after this all next powers of same base
		// will have less digits than corresponding power
		int count = 0;
		for (long base = 1; base < 10; base++) {
			BigInteger baseBI = BigInteger.valueOf(base);
			BigInteger value = BigInteger.valueOf(base);

			for (int power = 1; value.toString().length() == power; power++) {
				count++;
				value = value.multiply(baseBI);
			}
		}

		return "" + count;
	}

}
