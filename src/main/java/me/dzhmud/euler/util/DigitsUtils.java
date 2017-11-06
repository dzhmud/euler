package me.dzhmud.euler.util;

/**
 * Set of methods designed for working with separate digits of the numbers.
 *
 * @author dzhmud
 */
public final class DigitsUtils {
	private DigitsUtils(){}

	public static int[] getDigits(long value) {
		final int[] digits = new int[getDigitsCount(value)];
		long divisor0 = 10, divisor1 = 1;
		for (int i = digits.length-1; i >=0 ; i--) {
			digits[i] = (int)(value %divisor0 /divisor1);
			divisor0 *= 10;
			divisor1 *= 10;
		}
		return digits;
	}

	//this one appears to be ~3x faster than #getDigits()
	public static int[] getDigits_v2(long value) {
		final String s = String.valueOf(value);
		final int[] digits = new int[s.length()];
		for (int i = 0; i< s.length();i++){
			digits[i] = s.charAt(i) - '0';
		}
		return digits;
	}

	public static int getDigitsCount(long value) {
		assert value >= 0;//TODO refactor if needed.
		if (value == 0) return 1;
		return 1+(int)Math.floor(Math.log10(value));
	}

}
