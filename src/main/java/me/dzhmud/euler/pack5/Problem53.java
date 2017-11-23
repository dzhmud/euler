package me.dzhmud.euler.pack5;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.FactorialUtils;

import java.math.BigInteger;
import java.util.stream.IntStream;

/**
 * Combinatoric selections
 * {@see https://projecteuler.net/problem=53}
 *
 * @author dzhmud
 */
public class Problem53 implements EulerSolution {

	public static void main(String[] args) {
		new Problem53().measureTime();
	}

	@Override
	public String getAnswer() {
		return "" + IntStream.rangeClosed(1, 100).boxed()
				.flatMap(n -> IntStream.rangeClosed(1,n).mapToObj(r -> new NCR(n,r)))
				.filter(NCR::isGreaterThanMillion)
				.count();
	}

	private static class NCR {
		private static final BigInteger MILLION = BigInteger.valueOf(1000*1000);
		private final int n, r;

		NCR(int n, int r) {
			assert n >=r;
			this.n = n;
			this.r = r;
		}

		private BigInteger getValue() {
			return FactorialUtils.factorial(n).divide(FactorialUtils.factorial(r)).divide(FactorialUtils.factorial(n-r));
		}

		boolean isGreaterThanMillion() {
			return getValue().compareTo(MILLION) > 0;
		}

	}

}
