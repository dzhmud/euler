package me.dzhmud.euler.util;

import java.math.BigInteger;
import java.util.LinkedList;

/**
 * {@see https://projecteuler.net/problem=65}
 *
 * @author dzhmud
 */
public class BigIntegerFraction {

	public static BigIntegerFraction getValue(LinkedList<Integer> values) {
		if (values.size() == 1)
			return new BigIntegerFraction(values.get(0), 1);
		values = new LinkedList<>(values);
		BigIntegerFraction result = new BigIntegerFraction(1, values.pollLast());
		for (Integer i = values.pollLast(); i != null ; i = values.pollLast()) {
			result = result.add(i).revert();
		}
		return result.revert();
	}

	public final BigInteger nominator;
	public final BigInteger denominator;

	public BigIntegerFraction(int nominator, int denominator) {
		this(BigInteger.valueOf(nominator), BigInteger.valueOf(denominator));
	}

	public BigIntegerFraction(BigInteger nominator, BigInteger denominator) {
		this.nominator = nominator;
		this.denominator = denominator;
	}

	public BigIntegerFraction add(int value) {
		BigInteger newNominator = denominator.multiply(BigInteger.valueOf(value)).add(nominator);
		return new BigIntegerFraction(newNominator, denominator);
	}

	public BigIntegerFraction revert() {
		return new BigIntegerFraction(denominator, nominator);
	}

	@Override
	public String toString() {
		return nominator + "/" + denominator;
	}

}
