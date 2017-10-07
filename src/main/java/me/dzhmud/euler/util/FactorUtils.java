package me.dzhmud.euler.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Different utility methods for finding factors of number.
 *
 * @author dzhmud
 */
public final class FactorUtils {

	private FactorUtils(){}

	/**
	 * Dumbest version of finding factors.
	 * @param number
	 * @param onlyDistinctFactors
	 * @return
	 */
	public static List<Long> getFactors_v1(final long number, boolean onlyDistinctFactors) {
		assert number > 0;
		List<Long> result = new ArrayList<>();
		long temp = number;
		for (long factor = 2; factor <= temp; factor ++) {
			if (temp % factor > 0) {
				continue;
			}
			result.add(factor);
			temp /= factor;
			while (temp % factor == 0) {
				temp /= factor;
				if (!onlyDistinctFactors) {
					result.add(factor);
				}
			}
		}
		if (result.size() == 1)
			result.add(1L);
		return result;
	}


	/**
	 * get divisors as needed in problem12.
	 * @param number
	 * @return
	 */
	public static Set<Long> getDivisors(final long number) {
		return getDivisors(number, false);
	}

	public static Set<Long> getDivisors(final long number, boolean ordered) {
		assert number > 0;
		Set<Long> result = new HashSet<>();
		result.add(1L);
		result.add(number);
		for (long factor = 2; factor <= number/factor; factor ++) {
			if (number % factor == 0) {
				result.add(factor);
				result.add(number/factor);
			}
		}
		if (ordered) {
			List<Long> list = new ArrayList<>(result);
			list.sort(null);
			result = new LinkedHashSet<>(list);
		}
		return result;
	}

	private static int[] cache = new int[10000];

	public static void setDivisorsSumCacheSize(int size) {
		if (size > cache.length) {
			cache = Arrays.copyOf(cache, size);
		}
	}

	public static int getDivisorsSum(int value) {
		if (value == 0)
			return 0;
		if (value < cache.length && cache[value] > 0)
			return cache[value];
		final Set<Long> divisors = getDivisors(value);
		divisors.remove(Long.valueOf(value));//this is needed
		final AtomicInteger sum = new AtomicInteger(0);
		divisors.forEach(aLong -> sum.addAndGet(aLong.intValue()));
		int result = sum.intValue();
		if (value < cache.length)
			cache[value] = result;
		return result;
	}

}
