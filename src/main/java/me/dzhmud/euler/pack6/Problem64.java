package me.dzhmud.euler.pack6;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.SQRTContinuedFractions;

import java.util.stream.IntStream;

/**
 * Odd period square roots
 *
 * {@see https://projecteuler.net/problem=64}
 *
 * @author dzhmud
 */
public class Problem64 implements EulerSolution {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		System.out.println(new Problem64().getAnswer());
		System.out.println("Solution take " + (System.currentTimeMillis() - start) / 1000 + " sec");
	}

	@Override
	public String getAnswer() {
		return "" + IntStream.rangeClosed(2,10000)
				.map(Problem64::getPeriod)
				.filter(length -> length % 2 == 1)
				.count();
	}

	/**
	 * Return period length of irrational part of square root of given value.
	 * @param value value to calculate sqrt from.
	 * @return period length or 0 if there is none.
	 */
	private static int getPeriod(int value) {
		return new SQRTContinuedFractions(value).getSequence().length;
	}



}
