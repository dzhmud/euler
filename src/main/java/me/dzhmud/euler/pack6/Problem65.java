package me.dzhmud.euler.pack6;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.DigitsUtils;

import java.math.BigInteger;
import java.util.LinkedList;

/**
 * Convergents of e
 *
 * {@see https://projecteuler.net/problem=65}
 *
 * @author dzhmud
 */
public class Problem65 implements EulerSolution {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		System.out.println(new Problem65().getAnswer());
		System.out.println("Solution take " + (System.currentTimeMillis() - start) / 1000 + " sec");
	}

	@Override
	public String getAnswer() {
		LinkedList<Integer> e = new LinkedList<>();
		e.add(2);
		for (int i = 2; i < 100+1; i++) {
			e.add(i%3==0 ? 2*i/3 : 1);
		}
		return "" + DigitsUtils.getDigitsSum(getValue(e).nominator);
	}

	private static Fraction getValue(LinkedList<Integer> values) {
		if (values.size() == 1)
			return new Fraction(values.get(0), 1);
		Fraction result = new Fraction(1, values.pollLast());
		for (Integer i = values.pollLast(); i != null ; i = values.pollLast()) {
			result = result.add(i).revert();
		}
		return result.revert();
	}


	private static class Fraction {
		private final BigInteger nominator;
		private final BigInteger denominator;

		Fraction(int nominator, int denominator) {
			this(BigInteger.valueOf(nominator), BigInteger.valueOf(denominator));
		}

		private Fraction(BigInteger nominator, BigInteger denominator) {
			this.nominator = nominator;
			this.denominator = denominator;
		}

		Fraction add(int value) {
			BigInteger newNominator = denominator.multiply(BigInteger.valueOf(value)).add(nominator);
			return new Fraction(newNominator, denominator);
		}

		Fraction revert() {
			return new Fraction(denominator, nominator);
		}

		@Override
		public String toString() {
			return nominator + "/" + denominator;
		}
	}

}
