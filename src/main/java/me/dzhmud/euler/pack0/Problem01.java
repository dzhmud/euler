package me.dzhmud.euler.pack0;

import me.dzhmud.euler.EulerSolution;

import java.util.stream.IntStream;

/**
 * If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9. The sum of these multiples is 23.
 * Find the sum of all the multiples of 3 or 5 below 1000.
 *
 * @author dzhmud
 */
public class Problem01 implements EulerSolution {

	public static void main(String[] args) {
		System.out.println(new Problem01().getAnswer());
	}

	@Override
	public String getAnswer() {
		return IntStream.range(3, 1000).filter(x -> x % 3 == 0 || x % 5 == 0).sum() + "";
	}

}
