package me.dzhmud.euler.pack0;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.PrimeUtils;

/**
 *

 By listing the first six prime numbers: 2, 3, 5, 7, 11, and 13, we can see that the 6th prime is 13.

 What is the 10 001st prime number?

 *
 * @author dzhmud
 */
public class Problem07 implements EulerSolution {

	public static void main(String[] args) {
		new Problem07().measureTime();
	}

	@Override
	public String getAnswer() {
		final int ordinal = 10001;
		int count = 1;//count prime(2) separately
		long currentPrime = 2;
		for(long i = 3; count < ordinal; i +=2) {
			if (PrimeUtils.isPrime_v1(i)) {
				count++;
				currentPrime = i;
			}
		}
		return ""+currentPrime;
	}

}
