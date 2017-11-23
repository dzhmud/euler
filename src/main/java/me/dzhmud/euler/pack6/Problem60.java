package me.dzhmud.euler.pack6;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.PrimeUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.LongStream;

/**
 * Prime pair sets
 *
 The primes 3, 7, 109, and 673, are quite remarkable.
 By taking any two primes and concatenating them in any order the result will always be prime.
 For example, taking 7 and 109, both 7109 and 1097 are prime. The sum of these four primes, 792,
 represents the lowest sum for a set of four primes with this property.
 Find the lowest sum for a set of five primes for which any two primes concatenate to produce another prime.
 *
 * @author dzhmud
 */
public class Problem60 implements EulerSolution {

	public static void main(String[] args) {
		new Problem60().measureTime();
	}

	/**
	 * The algorithm would be:
	 * 1. take prime A
	 * 2. start checking primes that are < A, starting from A-2. Name it B. Check that AB and BA are both primes.
	 * 3. find prime C < B such that all of AC,CA,CB,BC are primes.
	 * 4. same for next primes in set.
	 * 5. if failed, take next prime A, bigger than current.
	 */
	@Override
	public String getAnswer() {
		PrimeUtils.isPrime_v2(10000);//warmup cache
		long[] result =
			LongStream.iterate(13, i-> i+2).filter(PrimeUtils::isPrime_v2).boxed().flatMap(a ->
				getPrimesBelow(a).stream().filter(b -> isFormingPrimes(b, a)).flatMap(b ->
					getPrimesBelow(b).stream().filter(c -> isFormingPrimes(c, b, a)).flatMap(c ->
						getPrimesBelow(c).stream().filter(d -> isFormingPrimes(d, c, b, a)).flatMap(d ->
							getPrimesBelow(d).stream().filter(e -> isFormingPrimes(e, d, c, b, a)).map(e ->
									new long[]{a,b,c,d,e}
							)
						)
					)
				)
			).findFirst()
			.orElseThrow(SolutionNotFoundException::new);
		return "" + Arrays.stream(result).sum();
	}

	private static final Map<Long, List<Long>> CACHE = new HashMap<>();

	/**
	 * Get primes in range [3, highBorder) in descending order;
	 */
	private static List<Long> getPrimesBelow(final long highBorder) {
		if (highBorder <= 3)
			return Collections.emptyList();
		return CACHE.computeIfAbsent(highBorder, x -> {
			List<Long> result = PrimeUtils.getPrimesInRange(3, highBorder);
			Collections.reverse(result);
			return result;
		});
	}

	/**
	 * Check if all possible concatenations of {@code a} and each value from {@code b} form a primes.
	 * @param a prime candidate
	 * @param b already collected primes
	 * @return true if all concatenations form a prime
	 */
	private static boolean isFormingPrimes(long a, long... b) {
		for(long prime: b) {
			if (!isFormingPrimes(a, prime)) {
				return false;
			}
		}
		return true;
	}

	/* This cache makes calculation two times slower!. */
//	private static Map<Tuple<Long,Long>, Boolean> CACHE_FORMING_PRIMES = new HashMap<>();

	private static boolean isFormingPrimes(long a, long prime) {
//		return CACHE_FORMING_PRIMES.computeIfAbsent(Tuple.of(a,prime), key ->
//			PrimeUtils.isPrime_v2(Long.valueOf(""+a+prime)) && PrimeUtils.isPrime_v2(Long.valueOf(""+prime+a))
//		);
		return PrimeUtils.isPrime_v2(Long.valueOf(""+a+prime)) && PrimeUtils.isPrime_v2(Long.valueOf(""+prime+a));
	}

}
