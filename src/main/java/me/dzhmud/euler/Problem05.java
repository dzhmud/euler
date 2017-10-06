package me.dzhmud.euler;

import me.dzhmud.euler.util.FactorUtils;

import java.util.List;

/**
 *

 2520 is the smallest number that can be divided by each of the numbers from 1 to 10 without any remainder.

 What is the smallest positive number that is evenly divisible by all of the numbers from 1 to 20?

 *
 * @author dzhmud
 */
public class Problem05 implements EulerSolution {

	public static void main(String[] args) {
		System.out.println(new Problem05().getAnswer());
	}

	@Override
	public String getAnswer() {
		int[] factors = new int[20];
		for (int i = 20; i > 1; i--) {
			int[] temp = new int[20];
			List<Long> currentFactors = FactorUtils.getFactors_v1(i, false);
			currentFactors.forEach(f -> temp[f.intValue()-1]++);
			factors = mergeFactors(factors, temp);
		}
		long result = 1;
		for (int i = 20; i > 1; i--) {
			while (0 < factors[i-1]--)
				result *= i;
		}

		return "" + result;
	}

	private static int[] mergeFactors(int[] f1, int[] f2) {
		assert f1.length == f2.length;
		int[] result = new int[f1.length];
		for (int index = 0; index < f1.length; index++) {
			result[index] = Math.max(f1[index], f2[index]);
		}
		return result;
	}

}
