package me.dzhmud.euler;

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
		long start = System.currentTimeMillis();
		System.out.println(new Problem48().getAnswer());
		System.out.println("Solution take " + (System.currentTimeMillis() - start) / 1000 + " sec");
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
