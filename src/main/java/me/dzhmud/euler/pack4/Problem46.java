package me.dzhmud.euler.pack4;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.PrimeUtils;

import java.util.Arrays;
import java.util.function.LongPredicate;
import java.util.stream.LongStream;

/**
 * Goldbach's other conjecture
 *

 It was proposed by Christian Goldbach that every odd composite number can be written as the sum of a prime and twice a square.

 9 = 7 + 2×1^2
 15 = 7 + 2×2^2
 21 = 3 + 2×3^2
 25 = 7 + 2×3^2
 27 = 19 + 2×2^2
 33 = 31 + 2×1^2

 It turns out that the conjecture was false.

 What is the smallest odd composite that cannot be written as the sum of a prime and twice a square?

 *
 * @author dzhmud
 */
public class Problem46 implements EulerSolution {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		System.out.println(new Problem46().getAnswer());
		System.out.println("Solution take " + (System.currentTimeMillis() - start) / 1000 + " sec");
	}

	@Override
	public String getAnswer() {
		return "" + LongStream.iterate(9, i-> i+2)
				.filter(i -> !PrimeUtils.isPrime_v1(i))
				.filter(cantBeWritten)
				.findFirst()
				.orElse(0);
	}

	private long[] squares = new long[]{2};

	private LongPredicate cantBeWritten = value -> {
		ensureSquaresSize(value);
		for (long sq : squares) {
			if (PrimeUtils.isPrime_v1(value -sq))
				return false;
		}
		return true;
	};

	private void ensureSquaresSize(long value) {
		if (squares[squares.length -1] < value) {
			final int previousLength = squares.length;
			squares = Arrays.copyOf(squares, (int)Math.sqrt(value/2) + 1);
			for (int i = previousLength+1; i < squares.length+1; i++) {
				squares[i-1] = 2*i*i;
			}
		}
	}
}
