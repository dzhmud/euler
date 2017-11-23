package me.dzhmud.euler.pack3;

import me.dzhmud.euler.EulerSolution;

import java.util.stream.IntStream;

/**
 * Digit cancelling fractions
 * {@see https://projecteuler.net/problem=33}
 *
 * @author dzhmud
 */
public class Problem33 implements EulerSolution {

	public static void main(String[] args) {
		new Problem33().measureTime();
	}

	@Override
	public String getAnswer() {
		return "" +
				IntStream.rangeClosed(10, 98).boxed()
						.flatMap(a -> IntStream.rangeClosed(a + 1, 99)
								.mapToObj(b -> new Fraction(a, b))
						)
						.filter(Fraction::isCurious)
//						.peek(System.out::println)
						.reduce(Fraction::multiply)
						.orElseThrow(SolutionNotFoundException::new)
						.b;
	}

	private static class Fraction {
		private final int a;
		private final int b;
		private boolean isSimplified;

		Fraction(int a, int b) {
			this.a = a;
			this.b = b;
			if (a == 1) {
				isSimplified = true;
			}
		}

		boolean isCurious() {
			if (a%10 == 0 && b%10 == 0)
				return false;
			final int num0 = a/10, num1 = a%10;
			final int den0 = b/10, den1 = b%10;
			Fraction simplified = null;
			if (findSameDigit(num0, den0, den1)) {
				simplified = new Fraction(num1, getNotSameDigit(num0, den0, den1));
			} else if (findSameDigit(num1, den0, den1)) {
				simplified = new Fraction(num0, getNotSameDigit(num1, den0, den1));
			}
			return simplified != null && simplified.equals(this);
		}

		private static boolean findSameDigit(int x, int... values) {
			for (int value : values)
				if (value == x)
					return true;
			return false;
		}

		private static int getNotSameDigit(int x, int value0, int value1) {
			if (x == value0) {
				return value1;
			} else if (x == value1) {
				return value0;
			} else {
				throw new IllegalArgumentException("Expected that at least 2 of 3 arguments are equal!");
			}
		}

		Fraction simplify() {
			if (isSimplified)
				return this;
			if (b % a == 0) {
				return new Fraction(1, b/a);
			}
			for (int i = 2; i < a; i++) {
				if (a % i == 0 && b % i == 0)
					return new Fraction(a/i, b/i).simplify();
			}
			isSimplified = true;
			return this;
		}

		Fraction multiply(final Fraction fraction) {
			final Fraction fr0 = this.simplify(), fr1 = fraction.simplify();
			return new Fraction(fr0.a * fr1.a, fr0.b * fr1.b).simplify();
		}

		public boolean equals(Object o) {
			return this == o || o != null && getClass() == o.getClass() && this.equals((Fraction) o);
		}

		boolean equals(Fraction fraction) {
			return (a * fraction.b) == (b * fraction.a);
		}

		@Override
		public int hashCode() {
			return 10*b/a;
		}

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder();
			sb.append(a).append("/").append(b);
			if (!this.isSimplified) {
				final Fraction simplified = simplify();
				if (simplified != this) {
					sb.append(" = ").append(simplified.a).append("/").append(simplified.b);
				}
			}
			return sb.toString();
		}
	}

}
