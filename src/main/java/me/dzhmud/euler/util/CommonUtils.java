package me.dzhmud.euler.util;

/**
 * Common utils.
 *
 * @author dzhmud
 */
public class CommonUtils {

	public static boolean isPalindromic(final int number) {
		int reversed = 0;
		int temp = Math.abs(number);
		while (temp > 0) {
			reversed = reversed * 10 + temp % 10;
			temp /= 10;
		}
		return reversed ==  Math.abs(number);
	}

	public static boolean isPalindromic(String number) {
		assert number != null;
		return number.equals(new StringBuilder(number).reverse().toString());
	}

}
