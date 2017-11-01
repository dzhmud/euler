package me.dzhmud.euler;

import me.dzhmud.euler.util.PrimeUtils;

import java.util.Comparator;
import java.util.stream.IntStream;

/**
 * Quadratic primes
 *
 * {@see https://projecteuler.net/problem=27}
 *
 * @author dzhmud
 */
public class Problem27 implements EulerSolution {

	public static void main(String[] args) {
		System.out.println(new Problem27().getAnswer());
	}

	@Override
	public String getAnswer() {
		return "" + IntStream.rangeClosed(-999, 999).boxed()
				.flatMap(a -> IntStream.rangeClosed(-1000, 1000)
						.mapToObj(b -> new QuadraticFormula(a, b)
						)
				)
				//hint: as we start with n == 0, b should be prime
				.filter(quadraticFormula -> PrimeUtils.isPrime_v1(quadraticFormula.b))
				.max(Comparator.comparing(QuadraticFormula::getConsecutivePrimesLength))
				.orElseThrow(IllegalArgumentException::new)
				.getCoefficientsProduct();
	}

	static class QuadraticFormula {
		private final int a;
		private final int b;

		QuadraticFormula(int a, int b) {
			this.a = a;
			this.b = b;
		}

		private int getValue(int n) {
			return n*n + a*n + b;
		}

		int getConsecutivePrimesLength() {
			//HINT: if (n==b) we get guaranteed non-prime
			for (int n = 0; n < b; n++) {
				if (!PrimeUtils.isPrime_v1(getValue(n)))
					return n;
			}
			return b;
		}

		int getCoefficientsProduct() {
			return a*b;
		}


	}

}
