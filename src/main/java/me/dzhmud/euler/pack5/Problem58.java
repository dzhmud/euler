package me.dzhmud.euler.pack5;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.PrimeUtils;

/**
 * Spiral primes
 * {@see https://projecteuler.net/problem=58}
 *
 * @author dzhmud
 */
public class Problem58 implements EulerSolution {

	public static void main(String[] args) {
		EulerSolution.measureTime(new Problem58()::getAnswer);
	}

	/**
	 * It can be seen, that every layer has (sideLength-1)*4 numbers
	 * Those numbers on diagonals, are formed by adding (sideLength-1) 4 times to maximum number of
	 * previous layer
	 * @return answer
	 */
	@Override
	public String getAnswer() {
		Spiral spiral = new Spiral();
		while (spiral.getDiagonalPrimesRatio() >= 10) {
			spiral.addNewLayer();
		}
		return "" + spiral.sideLength;
	}

	private static class Spiral {
		//starting from layer 3*3, to get rid of checks for zero.
		private int diagonalPrimes = 3, diagonalNumbers = 5;
		private int sideLength = 3;
		private int currentMax = 9;
		Spiral(){}

		void addNewLayer() {
			sideLength ++;
			for (int i = 0; i < 4; i++) {
				currentMax += sideLength;
				if (PrimeUtils.isPrime_v2(currentMax))
					diagonalPrimes++;
			}
			diagonalNumbers += 4;
			sideLength ++;
		}

		int getDiagonalPrimesRatio() {
			return 100*diagonalPrimes/diagonalNumbers;
		}
	}

}
