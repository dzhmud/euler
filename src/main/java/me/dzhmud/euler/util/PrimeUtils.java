package me.dzhmud.euler.util;

import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Different utility methods for dealing with prime numbers.
 *
 * @author dzhmud
 */
public final class PrimeUtils {

	private PrimeUtils(){}

	public static boolean DEBUG = false;

	//this cache only for prime numbers, as they take most time.
	private static final TreeMap<Long, Object> CACHE = new TreeMap<>();
	private static final Object OBJECT = new Object();
	public static boolean USE_CACHE = true;

	/**
	 * Dumbest variant, caches every prime checked.
	 */
	public static boolean isPrime_v1(long number) {
		if (DEBUG) System.out.println("PrimeUtils.isPrime_v1(" + number + ")");
		if (number == 2) return true;
		if (number < 2 || number % 2 == 0) return false;
		if (USE_CACHE && CACHE.containsKey(number)) return true;
		for (long factor = 3; factor < number; factor +=2 ) {
			if (number % factor == 0)
				return false;
			if (number / factor < factor)
				break;
		}
		if (USE_CACHE) CACHE.put(number, OBJECT);
		return true;
	}

	//this cache should contain all subsequent primes that are <= square root of currently checking value.
	private static final NavigableMap<Long, Object> HARD_CACHE = new ConcurrentSkipListMap<>();
	static {
		HARD_CACHE.put(2L, OBJECT);//to simplify rest of code
		HARD_CACHE.put(3L, OBJECT);//to simplify rest of code
	}

	/**
	 * Pretended to be good, by caching all subsequent primes
	 * and then checking divisibility only by them, not just every odd number.
	 * But it turns out, it's not as effective as I expected, or I just don't see
	 * performance bottleneck.
	 * In average, performance is same as of {@link #isPrime_v1(long)}.
	 *
	 * @param number value to check
	 * @return true if value given is prime and false if it is complex OR less than 2.
	 */
	public static boolean isPrime_v2(long number) {
		if (DEBUG) System.out.println("PrimeUtils.isPrime_v2(" + number + ")");
		if (number < 2 || number % 2 == 0 && number != 2) return false;

		long lastKey = HARD_CACHE.lastKey();
		if (number <= lastKey) {
			return HARD_CACHE.containsKey(number);
		} else if (number > lastKey*lastKey) {
			fillCacheTill(number);
			return HARD_CACHE.containsKey(number);
		} else {
			return isPrimeInner(number);
		}
	}

	private static boolean isPrimeInner(long value) {
		if (!CACHE.containsKey(value)) {
			final Set<Long> probableFactors = HARD_CACHE.headMap((long) (1+Math.sqrt(value)), true).keySet();
			for (long factor : probableFactors) {
				if (value % factor == 0)
					return false;
			}
			CACHE.put(value, OBJECT);
		}
		return true;
	}

	private static void fillCacheTill(long to) {
		for (long value = HARD_CACHE.lastKey()+2; value <= to; value+=2) {
			if (isPrimeInner(value)) {
				HARD_CACHE.put(value, OBJECT);
			}
		}
	}

}
