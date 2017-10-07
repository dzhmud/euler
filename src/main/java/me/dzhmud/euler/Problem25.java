package me.dzhmud.euler;

import java.math.BigInteger;

/**
 1000-digit Fibonacci number

 The Fibonacci sequence is defined by the recurrence relation:
 Fn = Fn−1 + Fn−2, where F1 = 1 and F2 = 1.

 Hence the first 12 terms will be:
 F1 = 1
 F2 = 1
 F3 = 2
 F4 = 3
 F5 = 5
 F6 = 8
 F7 = 13
 F8 = 21
 F9 = 34
 F10 = 55
 F11 = 89
 F12 = 144

 The 12th term, F12, is the first term to contain three digits.

 What is the index of the first term in the Fibonacci sequence to contain 1000 digits?

 * @author dzhmud
 */
public class Problem25 implements EulerSolution {

	public static void main(String[] args) {
		System.out.println(new Problem25().getAnswer());
	}

	@Override
	public String getAnswer() {
		BigInteger target = new BigInteger("10").pow(999);//lowest value with 1000 digits.
		FibonacciSequence fs = new FibonacciSequence(1, 1);
		while(fs.getNext().compareTo(target) < 0){}
		return ""+fs.getOrdinal();
	}

	public static class FibonacciSequence {
		private BigInteger previous;
		private BigInteger current;
		private int ordinal;

		public FibonacciSequence(int f1, int f2) {
			previous = new BigInteger(""+f1);
			current = new BigInteger(""+f2);
			ordinal = 2;
		}

		public BigInteger getNext() {
			final BigInteger next = previous.add(current);
			previous = current;
			current = next;
			ordinal++;
			return current;
		}

		public int getOrdinal() {
			return ordinal;
		}
	}

}
