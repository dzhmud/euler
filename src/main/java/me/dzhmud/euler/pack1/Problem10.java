package me.dzhmud.euler.pack1;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.PrimeUtils;

import java.util.stream.IntStream;

/**
 *

 The sum of the primes below 10 is 2 + 3 + 5 + 7 = 17.

 Find the sum of all the primes below two million.

 *
 * @author dzhmud
 */
public class Problem10 implements EulerSolution {

	public static void main(String[] args) {
		new Problem10().measureTime();
	}

	@Override
	public String getAnswer() {
		return "" + (2 + IntStream.range(3,2*1000*1000).filter(x -> x%2 == 1).filter(PrimeUtils::isPrime_v1).asLongStream().sum());
	}

}
