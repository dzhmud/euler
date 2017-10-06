package me.dzhmud.euler;

import me.dzhmud.euler.util.FactorUtils;

import java.util.List;

/**
 *

 The prime factors of 13195 are 5, 7, 13 and 29.

 What is the largest prime factor of the number 600851475143 ?

 *
 * @author dzhmud
 */
public class Problem03 implements EulerSolution {

	public static void main(String[] args) {
		System.out.println(new Problem03().getAnswer());
	}

	@Override
	public String getAnswer() {
		List<Long> factors = FactorUtils.getFactors_v1(600851475143L, true);
		return "" + factors.get(factors.size() - 1);
	}

}
