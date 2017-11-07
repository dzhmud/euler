package me.dzhmud.euler.pack5;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.DigitsUtils;
import me.dzhmud.euler.util.Tuple;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Permuted multiples

 It can be seen that the number, 125874, and its double, 251748, contain exactly the same digits, but in a different order.
 Find the smallest positive integer, x, such that 2x, 3x, 4x, 5x, and 6x, contain the same digits.
 *
 * @author dzhmud
 */
public class Problem52 implements EulerSolution {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		System.out.println(new Problem52().getAnswer());
		System.out.println("Solution take " + (System.currentTimeMillis() - start) / 1000 + " sec");
	}

	@Override
	public String getAnswer() {
		//hint1: to speed up we can limit checked numbers to 10..0-16..6 (number of . unknown)
		//hint1: because exceeding 1(6), we get 6x containing one extra digit.
		//but for now it runs faster than a second.
		return "" + IntStream.iterate(1, i->i+1)
				.mapToObj(i -> Tuple.of(i, sort(DigitsUtils.getDigits(i))))
				.filter(tuple -> Arrays.equals(tuple.getValue(), sort(DigitsUtils.getDigits(2*tuple.getKey()))))
				.filter(tuple -> Arrays.equals(tuple.getValue(), sort(DigitsUtils.getDigits(3*tuple.getKey()))))
				.filter(tuple -> Arrays.equals(tuple.getValue(), sort(DigitsUtils.getDigits(4*tuple.getKey()))))
				.filter(tuple -> Arrays.equals(tuple.getValue(), sort(DigitsUtils.getDigits(5*tuple.getKey()))))
				.filter(tuple -> Arrays.equals(tuple.getValue(), sort(DigitsUtils.getDigits(6*tuple.getKey()))))
				.findFirst()
				.orElseThrow(SolutionNotFoundException::new)
				.getKey();
	}

	private static int[] sort(int[] digits) {
		Arrays.sort(digits);
		return digits;
	}

}
