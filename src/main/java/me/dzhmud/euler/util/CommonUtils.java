package me.dzhmud.euler.util;

/**
 * Common utils.
 *
 * @author dzhmud
 */
public class CommonUtils {

	public static boolean isPalindrom(final int number) {
		int reversed = 0;
		int temp = Math.abs(number);
		while (temp > 0) {
			reversed = reversed * 10 + temp % 10;
			temp /= 10;
		}
		return reversed ==  Math.abs(number);
	}

}
