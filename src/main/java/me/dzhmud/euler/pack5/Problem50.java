package me.dzhmud.euler.pack5;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.PrimeUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Consecutive prime sum
 *

 The prime 41, can be written as the sum of six consecutive primes:
 41 = 2 + 3 + 5 + 7 + 11 + 13

 This is the longest sum of consecutive primes that adds to a prime below one-hundred.

 The longest sum of consecutive primes below one-thousand that adds to a prime, contains 21 terms, and is equal to 953.

 Which prime, below one-million, can be written as the sum of the most consecutive primes?

 *
 * @author dzhmud
 */
public class Problem50 implements EulerSolution {

	public static void main(String[] args) {
		EulerSolution.measureTime(new Problem50()::getAnswer);
	}

	@Override
	public String getAnswer() {
		//rather slow solution
		return "" + IntStream.range(2, 1000*1000).boxed()
				.filter(PrimeUtils::isPrime_v1)
				.collect(Collectors.toMap(Function.identity(), Problem50::getPrimesSumLength))
				.entrySet().stream()
				.max(Comparator.comparing(Map.Entry::getValue))
				.orElseThrow(SolutionNotFoundException::new)
				.getKey();
	}

	private static List<Integer> primesList = IntStream.range(2, 1000*1000).filter(PrimeUtils::isPrime_v1).boxed().collect(Collectors.toList());

	private static int getPrimesSumLength(final int prime) {
		int position = primesList.indexOf(prime);
		int result = 0;
		for (int i = position -1; i > 0; i--) {
			int sum = 0;
			for (int j = i; j >= 0 && sum < prime; j--) {
				sum += primesList.get(j);
				if (sum == prime) {
					result = Math.max(result, i - j + 1);
				}
			}
		}
		return result;
	}

}
