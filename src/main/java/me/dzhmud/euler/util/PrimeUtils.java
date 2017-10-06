package me.dzhmud.euler.util;

/**
 * Different utility methods for dealing with prime numbers.
 *
 * @author dzhmud
 */
public final class PrimeUtils {

	private PrimeUtils(){}

	/**
	 * Dumbest variant, no optimisation at all.
	 */
	public static boolean isPrime_v1(long number) {
		if (number < 2) return false;
		if (number == 2) return true;
		if (number % 2 == 0) return false;
		for (long factor = 3; factor < number; factor +=2 ) {
			if (number % factor == 0)
				return false;
			if (number / factor < factor)
				break;
		}
		return true;
	}

}
