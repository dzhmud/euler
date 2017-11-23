package me.dzhmud.euler.pack3;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.PrimeUtils;

import java.util.stream.IntStream;

/**
 * Circular primes
 *
 The number, 197, is called a circular prime because all rotations of the digits: 197, 971, and 719, are themselves prime.

 There are thirteen such primes below 100: 2, 3, 5, 7, 11, 13, 17, 31, 37, 71, 73, 79, and 97.

 How many circular primes are there below one million?
 *
 * @author dzhmud
 */
public class Problem35 implements EulerSolution {

	public static void main(String[] args) {
		EulerSolution.measureTime(new Problem35()::getAnswer);
	}

	@Override
	public String getAnswer() {
		//count 2 separately, as it is the only even digit in all circular primes.
		return "" + (1 + IntStream.range(3,1000*1000)
				.filter(Problem35::isCircularPrime)
				.count());
	}

	private static int[] powersOfTen = new int[]{1,10,100,1000,10000,100000,1000000};

	private static boolean isCircularPrime(final int value) {
		//first, check value itself
		if (!PrimeUtils.isPrime_v1(value))
			return false;
		//find number of digits(and circular iterations, also, it differs by 1)
		//also, check that every digit is odd
		int iterations = 0;
		for (int i = 0; i < powersOfTen.length - 1; i++) {//reversing this cycle will give a speed-up.
			final int digit = (value % powersOfTen[i+1]) / powersOfTen[i];
			if (digit % 2 == 0) {
				return false;
			}
			if (value > powersOfTen[i] && value < powersOfTen[i+1]) {
				iterations = i;
				break;
			}
		}
		//generate circular values and check primes
		final int power =  powersOfTen[iterations];
		int newValue = value;
		for (; iterations > 0; iterations--) {
			newValue = newValue/10 + newValue%10 * power; //12345 ->  1234 + 5 * 10000
			if (!PrimeUtils.isPrime_v1(newValue)) {
				return false;
			}
		}
		//value is prime, and all circular permutations give primes also
		return true;
	}

}
