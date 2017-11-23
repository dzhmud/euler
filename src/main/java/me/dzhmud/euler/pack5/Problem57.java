package me.dzhmud.euler.pack5;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.Tuple;

import java.math.BigInteger;

/**
 * Square root convergents
 *
 * @author dzhmud
 */
public class Problem57 implements EulerSolution {

	public static void main(String[] args) {
		EulerSolution.measureTime(new Problem57()::getAnswer);
	}

	@Override
	public String getAnswer() {
		//hint: it is quite obvious, that every iteration can be get from previous with formula:
		// Xi = 1+1/(1+Xi-1)
		//if Xi-1 is represented as a/b, then Xi = (a+2b)/(a+b)
		int count = 0;
		BigInteger one = BigInteger.valueOf(1);
		Fraction current = new Fraction(one, one);
		for (int i = 0; i < 1000; i++) {
			current = current.getNextIteration();
			if (current.numHasMoreDigits())
				count++;
		}
		return "" + count;
	}

	private static class Fraction extends Tuple<BigInteger, BigInteger> {

		Fraction (BigInteger a, BigInteger b) {
			super(a,b);
		}

		//nominator
		private BigInteger getA() {
			return getKey();
		}
		//denominator
		private BigInteger getB() {
			return getValue();
		}

		boolean numHasMoreDigits() {
			return getA().toString().length() > getB().toString().length();
		}

		Fraction getNextIteration() {
			BigInteger denominator = getA().add(getB());
			return new Fraction(denominator.add(getB()), denominator);
		}

		@Override
		public String toString() {
			return getA() + "/" + getB();
		}
	}

}
