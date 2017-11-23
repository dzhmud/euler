package me.dzhmud.euler.pack4;

import me.dzhmud.euler.EulerSolution;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

/**
 * Self powers
 *
 *

 The series, 1^1 + 2^2 + 3^3 + ... + 10^10 = 10405071317.

 Find the last ten digits of the series, 1^1 + 2^2 + 3^3 + ... + 1000^1000.

 *
 * @author dzhmud
 */
public class Problem48 implements EulerSolution {

	public static void main(String[] args) {
		new Problem48().measureTime();
	}

	@Override
	public String getAnswer() {
		final AtomicReference<BigInteger> sum = new AtomicReference(BigInteger.valueOf(0));
		IntStream.rangeClosed(1,1000).forEach(i ->
				sum.getAndAccumulate(BigInteger.valueOf(i).pow(i), BigInteger::add)
		);
		String result = sum.get().toString();
		return result.substring(result.length() -10);
	}

}
