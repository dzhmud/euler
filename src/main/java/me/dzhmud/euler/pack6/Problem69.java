package me.dzhmud.euler.pack6;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.FactorUtils;
import me.dzhmud.euler.util.PrimeUtils;
import me.dzhmud.euler.util.Tuple;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Totient maximum
 *

 Euler's Totient function, φ(n) [sometimes called the phi function],
 is used to determine the number of numbers less than n which are relatively prime to n.
 For example, as 1, 2, 4, 5, 7, and 8, are all less than nine and relatively prime to nine, φ(9)=6.

 n 	    Relatively Prime 	φ(n) 	n/φ(n)
 2 	    1 	                1 	    2
 3 	    1,2 	            2 	    1.5
 4 	    1,3 	            2 	    2
 5 	    1,2,3,4 	        4 	    1.25
 6 	    1,5 	            2 	    3
 7 	    1,2,3,4,5,6 	    6 	    1.1666...
 8 	    1,3,5,7 	        4 	    2
 9 	    1,2,4,5,7,8 	    6 	    1.5
 10 	1,3,7,9 	        4 	    2.5

 It can be seen that n=6 produces a maximum n/φ(n) for n ≤ 10.

 Find the value of n ≤ 1,000,000 for which n/φ(n) is a maximum.

 *
 * @author dzhmud
 */
public class Problem69 implements EulerSolution {

	public static void main(String[] args) {
		EulerSolution.measureTime(new Problem69()::getAnswer);
	}

	final int limit = 200*1000;

	/**
	 * Have not found acceptable by time solution, but logic points out to 2*3*5*7*11*13*17, which is in fact the answer.
	 * So, how to get it:
	 * 1. to get maximum of n/φ(n), we have to have minimum in denominator, which means to have minimum value of relative primes.
	 * 2. the target N is complex, as prime N have φ(n) = N-1, and n/φ(n) -> 1
	 * 3. imagine N and sequence 1..N
	 * 4. now to find number of relative primes of N, take each prime factor of N, name it F.
	 * 5. for each F, go through sequence 1..N and mark out F, 2*F, 3*F, .. X*F
	 * 6. the target is to minimize number of values left.
	 * 7. it's quite logical that the most efficient N will be product of consecutive prime factors.
	 * 8. no, find the maximum product of consecutive prime factors that is less then target 1000*1000
	 * It turns out, that it is 510510 = 2*3*5*7*11*13*17
	 * @return
	 */
	@Override
	public String getAnswer() {
		if (System.currentTimeMillis() > 0)
			return ""+2*3*5*7*11*13*17;
		System.out.println(FactorUtils.getFactors_v1(30030, false));
		PrimeUtils.isPrime_v2(limit);//warm-up primes cache
		return "" + IntStream.range(2, limit)
				.filter(i -> !PrimeUtils.isPrime_v2(i))//filter out primes, as their n/φ(n) -> 1
				.mapToObj(i -> Tuple.of(i, calculateValue(i)))
				.max(Comparator.comparing(Tuple::getValue)).orElseThrow(SNFE)
				.getKey();
	}








	//TODO TODO REFACTORR !!!!!!!











	private Double calculateValue(int n) {
		return (double)n / getRelativePrimesCount(n);
	}

	@SuppressWarnings("unchecked")
	private final Set<Integer>[] FACTORS = new HashSet[limit+1];
	private final Integer[] MASKS = new Integer[limit+1];
	{
//		for (int factor = 2; factor <= limit/2; factor++) {
//			for (int index = factor*2; index <= limit; index+=factor) {
//				if (FACTORS[index] == null) FACTORS[index] = new HashSet<>();
//				FACTORS[index].add(factor);
//			}
//		}
		for (int i = 2; i < limit; i++) {
			List<Long> factors = FactorUtils.getFactors_v1(i, true);
			factors.remove(Long.valueOf(1));
			Set<Integer> factorsInt = factors.stream().mapToInt(Long::intValue).boxed().collect(Collectors.toSet());
			FACTORS[i] = factorsInt;
			int mask = 0;
			for (long factor : factors) {
				if (factor > 30) break;
				mask += 1 << factor;
			}
			MASKS[i] = mask;
		}
	}
	private int getRelativePrimesCount(int n) {
		if (PrimeUtils.isPrime_v2(n))
			return n-1;
		Set<Integer> factors = FACTORS[n];
		boolean[] candidates = new boolean[n];
		for (int factor : factors) {
			for (int i = factor; i < n; i+=factor) {
				candidates[i] = true;
			}
		}
//		if (n < 10) {
//			System.out.print(n + ":"+Arrays.toString(candidates));
//		}
		int count = 0;
		for (boolean hasCommonFactor : candidates) {
			if (!hasCommonFactor)
				count++;
		}
//		if (n < 10) {
//			System.out.println("\t" + count);
//		}
		return count-1;
//		IntStream stream = IntStream.range(2,n);
//		stream = stream.filter(i -> (MASKS[i] & MASKS[n]) == 0);
//		for (Integer factor : factors) {
//			if (factor > 30)
//				stream = stream.filter(i -> i % factor > 0);
//		}
//		return 1 + (int)stream.count();
//		return 1 + (int)IntStream.range(2,n).filter(i -> areRelativelyPrime(i, n)).count();
	}

	private boolean areRelativelyPrime(int a, int b) {
		assert a < b;
//		if (FACTORS[b] == null) {
//			return true;
//		}
		if (FACTORS[a] == null) {
			return !FACTORS[b].contains(a);
		}
		return (MASKS[a] & MASKS[b]) == 0 && !haveInterSections(FACTORS[a], FACTORS[b]);
	}

	private boolean haveInterSections(Set set0, Set set1) {
		Objects.requireNonNull(set0);
		Objects.requireNonNull(set1);
		final Set smaller = set0.size() < set1.size() ? set0 : set1;
		final Set bigger = smaller == set0 ? set1 : set0;

		Iterator it = smaller.iterator();
		while (it.hasNext()) {
			if (bigger.contains(it.next())) {
				return true;
			}
		}
		return false;
	}

}
