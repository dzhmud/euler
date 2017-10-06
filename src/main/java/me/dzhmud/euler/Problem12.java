package me.dzhmud.euler;

import me.dzhmud.euler.util.FactorUtils;

/**
 *
 The sequence of triangle numbers is generated by adding the natural numbers. So the 7th triangle number would be 1 + 2 + 3 + 4 + 5 + 6 + 7 = 28. The first ten terms would be:

 1, 3, 6, 10, 15, 21, 28, 36, 45, 55, ...

 Let us list the factors of the first seven triangle numbers:

 1: 1
 3: 1,3
 6: 1,2,3,6
 10: 1,2,5,10
 15: 1,3,5,15
 21: 1,3,7,21
 28: 1,2,4,7,14,28

 We can see that 28 is the first triangle number to have over five divisors.

 What is the value of the first triangle number to have over five hundred divisors?

 *
 * @author dzhmud
 */
public class Problem12 implements EulerSolution {

	public static void main(String[] args) {
		System.out.println(new Problem12().getAnswer());
	}

	@Override
	public String getAnswer() {
		TriangleNumber current = new TriangleNumber();
		while (FactorUtils.getDivisors(current.getNext().value).size() <= 500){}
		return "" + current.value;
	}

	private class TriangleNumber {
		private int ordinal;
		private int value;
		TriangleNumber(){
			ordinal = 1;
			value = 1;
		}

		TriangleNumber getNext() {
			ordinal++;
			value +=ordinal;
			return this;
		}

	}

}
