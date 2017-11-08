package me.dzhmud.euler.pack5;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.CommonUtils;

import java.math.BigInteger;
import java.util.stream.IntStream;

/**
 * Lychrel numbers
 *
 * {@see https://projecteuler.net/problem=55}
 *
 * @author dzhmud
 */
public class Problem55 implements EulerSolution {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		System.out.println(new Problem55().getAnswer());
		System.out.println("Solution take " + (System.currentTimeMillis() - start) / 1000 + " sec");
	}

	@Override
	public String getAnswer() {
		return "" + IntStream.range(1, 10000).filter(Problem55::isLychrel).count();
	}

	private static boolean isLychrel(final int i) {
		BigInteger start = BigInteger.valueOf(i);
		for (int iteration = 1; iteration < 50; iteration++) {
			start = addReversed(start);
			if (isPalindromic(start))
				return false;
		}
		return true;
	}

	private static BigInteger addReversed(BigInteger original) {
		return original.add(new BigInteger(new StringBuilder(original.toString()).reverse().toString()));
	}

	private static boolean isPalindromic(BigInteger bigInteger) {
		return CommonUtils.isPalindromic(bigInteger.toString());
	}

}
