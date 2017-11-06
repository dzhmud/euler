package me.dzhmud.euler.pack0;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.FileUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 *

 The four adjacent digits in the 1000-digit number that have the greatest product are 9 × 9 × 8 × 9 = 5832.

#{include Problem08.txt}

 Find the thirteen adjacent digits in the 1000-digit number that have the greatest product. What is the value of this product?

 *
 * @author dzhmud
 */
public class Problem08 implements EulerSolution {

	public static void main(String[] args) {
		System.out.println(new Problem08().getAnswer());
	}

	private static final int LENGTH = 13;
	@Override
	public String getAnswer()  {
		final String number = FileUtils.getContents("Problem08.txt", Collectors.joining());
		return "" + Arrays.stream(number.split("0"))
				.filter(x -> x.length() >= LENGTH)
				.map(x -> getMaxProduct(x, LENGTH))
				.max(Comparator.naturalOrder()).orElse(-1L);
	}

	private static Long getMaxProduct(String digits, int amountOfDigits) {
		assert digits.length() >= amountOfDigits;
		long product = 0;
		for (int i = 0; i <= digits.length() - amountOfDigits; i++) {
			product = Math.max(product, getProduct(digits.substring(i, i+amountOfDigits)));
		}
		return product;
	}

	private static long getProduct(String digits) {
		assert digits != null && digits.length() > 0;
		return digits.chars().map(x -> Character.digit(x, 10)).asLongStream().reduce((a,b)->a*b).orElseThrow(IllegalArgumentException::new);
	}

}
