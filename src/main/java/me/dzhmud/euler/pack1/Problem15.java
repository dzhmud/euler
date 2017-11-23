package me.dzhmud.euler.pack1;

import me.dzhmud.euler.EulerSolution;

import java.math.BigInteger;

import static me.dzhmud.euler.util.FactorialUtils.factorial;

/**
 *

 Starting in the top left corner of a 2×2 grid, and only being able to move to the right and down, there are exactly 6 routes to the bottom right corner.

 How many such routes are there through a 20×20 grid?

 *
 * @author dzhmud
 */
public class Problem15 implements EulerSolution {

	public static void main(String[] args) {
		EulerSolution.measureTime(new Problem15()::getAnswer);
	}

	private static final int SIZE = 20;

	/**
	 * Putting in each point a number of routes to it, we get next:
	 * 0 1 1 1 1 1 1...
	 * 1 2 3 4 5 6 7...
	 * 1 3 6 10 15 21 28...
	 * 1 4 10 20 35 ..
	 * 1 5 15 35 70 ...
	 * 1 6 21 56 126...
	 * 1 7 28 84 ..
	 * . .
	 * . .
	 * ======
	 * Simple calculation of binomial coefficient can be done with formula (n,k) = n!/(k!(n-k)!)
	 * We got 40!/(20!*20!) = 21*22*23*..40/(2*3*4*..20)= 21*23*25*..39/(2*3*4*..*10)
	 * ======
	 * Other way is to simply calculate 20 rows row-by-row by just summing value to the left with value to the left.
	 *
	 * @return answer
	 */
	@Override
	public String getAnswer() {
		BigInteger factorial20 = factorial(SIZE);
		return factorial(2*SIZE).divide(factorial20).divide(factorial20).toString();
	}


}
