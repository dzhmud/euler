package me.dzhmud.euler.pack3;

import me.dzhmud.euler.EulerSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 *

 We shall say that an n-digit number is pandigital if it makes use of all the digits 1 to n exactly once; for example, the 5-digit number, 15234, is 1 through 5 pandigital.

 The product 7254 is unusual, as the identity, 39 × 186 = 7254, containing multiplicand, multiplier, and product is 1 through 9 pandigital.

 Find the sum of all products whose multiplicand/multiplier/product identity can be written as a 1 through 9 pandigital.
 HINT: Some products can be obtained in more than one way so be sure to only include it once in your sum.

 *
 * @author dzhmud
 */
public class Problem32 implements EulerSolution {

	public static void main(String[] args) {
		System.out.println(new Problem32().getAnswer());
	}

	@Override
	public String getAnswer() {
		/* small research shows that all possible solutions are of forms ab*cde OR a*bcde  **/
		//a*bcde
		IntStream.rangeClosed(2, 9).boxed().parallel().forEach(
				a -> IntStream.rangeClosed(1234, 9876).parallel().forEach(
						b -> checkPandigital(a, b))
				);
		//ab*cde
		IntStream.rangeClosed(12, 98).boxed().parallel().forEach(
				a -> IntStream.rangeClosed(123, 987).parallel().forEach(
						b -> checkPandigital(a, b))
		);

//		System.out.println(solutions);
		return "" + solutions.stream()
				.map(Solution::getProduct)
				.distinct()
//				.peek(System.out::println)
				.reduce((a,b)-> a+b)
				.orElseThrow(SolutionNotFoundException::new);
	}

	private static List<Solution> solutions = new ArrayList<>();

	private static void checkPandigital(final int a, final int b) {
		final int product = a*b;
		final int[] digits = new int[9];
		for (int value : new int[]{a,b,product}) {
			int digit;
			while (value > 0) {
				digit = value%10;
				if (digit == 0 || digits[digit-1] > 0) {
					return;
				}
				digits[digit-1] +=1;
				value /= 10;
			}
		}
		for (int i : digits)
			if (i != 1)
				return;
		solutions.add(new Solution(a,b,product));
	}

	private static final class Solution {
		private final int a;
		private final int b;
		private final int product;

		public Solution(int a, int b, int product) {
			this.a = a;
			this.b = b;
			this.product = product;
		}

		public int getProduct() {
			return product;
		}

		@Override
		public String toString() {
			return a+"*"+b+"="+product;
		}
	}

}
