package me.dzhmud.euler.pack4;

import me.dzhmud.euler.EulerSolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Sub-string divisibility
 *

 The number, 1406357289, is a 0 to 9 pandigital number because it is made up of each of the digits 0 to 9 in some order, but it also has a rather interesting sub-string divisibility property.

 Let d1 be the 1st digit, d2 be the 2nd digit, and so on. In this way, we note the following:

 d2d3d4=406 is divisible by 2
 d3d4d5=063 is divisible by 3
 d4d5d6=635 is divisible by 5
 d5d6d7=357 is divisible by 7
 d6d7d8=572 is divisible by 11
 d7d8d9=728 is divisible by 13
 d8d9d10=289 is divisible by 17

 Find the sum of all 0 to 9 pandigital numbers with this property.

 *
 * @author dzhmud
 */
public class Problem43 implements EulerSolution {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		System.out.println(new Problem43().getAnswer());
		System.out.println("Solution take " + (System.currentTimeMillis() - start)/1000 + " sec");
	}

	@Override
	public String getAnswer() {
		final List<Long> pandigits = new ArrayList<>();

		//the quickest way to find q2q3q4q5q6q7q8q9 I can imagine.
		TRIPLETS.get(17).forEach(tr17 ->
			TRIPLETS.get(13).stream().filter(tr13 -> tr13.canMergeBefore.test(tr17)).forEach(tr13 ->
				TRIPLETS.get(11).stream().filter(tr11 -> tr11.canMergeBefore.test(tr13)).forEach(tr11 ->
					TRIPLETS.get(7).stream().filter(tr7 -> tr7.canMergeBefore.test(tr11))
											.filter(tr7 -> tr7.differentDigits.test(tr17)).forEach(tr7 ->
						TRIPLETS.get(5).stream().filter(tr5 -> tr5.canMergeBefore.test(tr7))
												.filter(tr5 -> tr5.differentDigits.test(tr13)).forEach(tr5 ->
							TRIPLETS.get(3).stream().filter(tr3-> tr3.canMergeBefore.test(tr5))
													.filter(tr3-> tr3.differentDigits.test(tr11)).forEach(tr3 ->
								TRIPLETS.get(2).stream().filter(tr2 -> tr2.canMergeBefore.test(tr3))
														.filter(tr2 -> tr2.differentDigits.test(tr7))
														.filter(tr2 -> tr2.differentDigits.test(tr17)).forEach(tr2 ->
									pandigits.add(getResult(tr2, tr3, tr5, tr7, tr11, tr13, tr17))
								)
							)
						)
					)
				)
			)
		);
		return "" + pandigits.stream().mapToLong(x->x).sum();
	}

	//merge triplets and add one more digit.
	private static long getResult(Triplet... triplets) {
		long result = triplets[0].value;
		for (int i = 1; i < triplets.length; i++) {
			result = result*10 + triplets[i].value % 10;
		}
		//now discover first digit.
		String value = String.valueOf(result);
		for (char c = '0'; c <= '9'; c++) {
			if (value.indexOf(c) < 0) {
				result = result + (c - '0') * 1000000000L;
			}
		}
		return result;
	}

	private static final int[] divisors = new int[]{2,3,5,7,11,13,17};

	private static Map<Integer, List<Triplet>> TRIPLETS = new HashMap<>();

	/**	 Predicate that tests if all digits in the value are different.	 */
	private static IntPredicate HAS_DIFFERENT_DIGITS = value -> {
		assert value < 1000;
		int[] digits = new int[3];
		digits[2] = value %10;
		digits[1] = value %100 /10;
		digits[0] = value /100;
		return digits[0] != digits[1] && digits[0] != digits[2] && digits[1] != digits[2];
	};

	static {
		for (int divisor : divisors) {
			final List<Triplet> triplets = IntStream.range(divisor, 1000)
					.filter(value -> value % divisor == 0)
					.filter(HAS_DIFFERENT_DIGITS)
					.mapToObj(Triplet::new)
					.collect(Collectors.toList());
			TRIPLETS.put(divisor, triplets);
		}
	}

	private static class Triplet {
		final int value;
		final int mask;
		//tests that this and other triplet do not have any common digit
		final Predicate<Triplet> differentDigits;
		//tests if first 2digits of another value matches with last 2digits of this value: 123(this) -> 234(other)
		final Predicate<Triplet> canMergeBefore;

		Triplet(int value) {
			assert value < 1000;
			this.value = value;
			{
				int mask = 0;
				for (int i = 0; i < 3; i++) {
					int digit = value % 10;
					mask = mask | 1 << digit;
					value /= 10;
				}
				this.mask = mask;
			}
			this.differentDigits = triplet -> (triplet.mask & this.mask) == 0;
			this.canMergeBefore = triplet ->
				(this.value %100 == triplet.value /10) && (this.value /100 != triplet.value %10);
		}

	}

}
