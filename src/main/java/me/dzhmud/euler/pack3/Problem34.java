package me.dzhmud.euler.pack3;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.FactorialUtils;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;

/**
 * Digit factorials
 *
 145 is a curious number, as 1! + 4! + 5! = 1 + 24 + 120 = 145.

 Find the sum of all numbers which are equal to the sum of the factorial of their digits.

 Note: as 1! = 1 and 2! = 2 are not sums they are not included.

 *
 * @author dzhmud
 */
public class Problem34 implements EulerSolution {

	public static void main(String[] args) {
		System.out.println(new Problem34().getAnswer());
	}

	@Override
	public String getAnswer() {
		/*
		 * hint: primitive calculation says that 7digits are max in searched number,
		 * 8digit values are guaranteed to be bigger then the sum of factorials of their digits.
		 */

		//try bruteforce. anti-optimal, but give it a try
		return "" + IntStream.rangeClosed(10, 7*factorials[9]).filter(isCurious).peek(Problem34::printCuriousNumber).sum();
	}

	//cache digits factorials.
	private final static int[] factorials = new int[10];
	static {
		factorials[0] = 1;
		IntStream.rangeClosed(1, 9).forEach(value -> factorials[value] = FactorialUtils.factorial(value).intValue());
	}

	private static IntPredicate isCurious = value -> {
		int sum = 0; //TODO can be cached for value without last digit!
		int digits = value;
		while (digits > 0) {
			sum += factorials[digits%10];
			digits /= 10;
		}
		return sum == value;
	};

	private static void printCuriousNumber(int value) {
		StringBuilder sb = new StringBuilder().append(value).append(" = ");
		String sValue = "" + value;
		for (int i = 0; i < sValue.length(); i++) {
			sb.append(sValue.charAt(i)).append("! + ");
		}
		sb.delete(sb.length()-3, sb.length());
//		System.out.println(sb.toString());
	}

}
