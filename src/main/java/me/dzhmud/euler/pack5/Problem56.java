package me.dzhmud.euler.pack5;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.DigitsUtils;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Powerful digit sum.
 * {@see https://projecteuler.net/problem=56}
 * Find maximum digit sum among numbers of form a^b, where a,b < 100
 *
 * @author dzhmud
 */
public class Problem56 implements EulerSolution {

	public static void main(String[] args) {
		EulerSolution.measureTime(new Problem56()::getAnswer);
	}

	@Override
	public String getAnswer() {
		return "" + IntStream.range(1,100).flatMap(a ->
				IntStream.range(1,100).map(b ->
						getDigitsSum(BigInteger.valueOf(a).pow(b))
				)
		).max().orElseThrow(SolutionNotFoundException::new);
	}

	private static int getDigitsSum(BigInteger bigInteger) {
		int[] digits = DigitsUtils.getDigits(bigInteger.toString());
		return Arrays.stream(digits).sum();
	}

}
