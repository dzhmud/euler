package me.dzhmud.euler.util;

import java.math.BigInteger;

/**
 * Utility methods for working with factorials.
 *
 * @author dzhmud
 */
public final class FactorialUtils {
	private FactorialUtils(){}

	public static BigInteger factorial(int i) {
		if (i == 0) return BigInteger.valueOf(1);
		assert i > 0;
		BigInteger result = BigInteger.valueOf(i);
		while (--i > 1)
			result = result.multiply(BigInteger.valueOf(i));
		return result;
	}
}
