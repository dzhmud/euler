package me.dzhmud.euler;

import java.util.stream.IntStream;

/**
 *

 Starting with the number 1 and moving to the right in a clockwise direction a 5 by 5 spiral is formed as follows:

 21 22 23 24 25
 20  7  8  9 10
 19  6  1  2 11
 18  5  4  3 12
 17 16 15 14 13

 It can be verified that the sum of the numbers on the diagonals is 101.

 What is the sum of the numbers on the diagonals in a 1001 by 1001 spiral formed in the same way?

 *
 * @author dzhmud
 */
public class Problem28 implements EulerSolution {

	public static void main(String[] args) {
		System.out.println(new Problem28().getAnswer());
	}

	@Override
	public String getAnswer() {
		//3*3 square contains: 1+2*1, 1+2*2, 1+2*3, 1+2*4 -> 1*4 + 2*10
		//5*5 square contains: 9+4*1, 9+4*2, 9+4*3, 9+4*4 -> 9*4 + 4*10
		//7*7 would have sum: 25*4 + 6*10
		long result = 1 + IntStream.iterate(2, i -> i+2).limit(500)
				.map(i -> (i-1)*(i-1)*4 + i * 10)
				.sum();
		return ""+result;
	}

}
