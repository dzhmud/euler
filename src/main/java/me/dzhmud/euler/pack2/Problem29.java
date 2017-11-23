package me.dzhmud.euler.pack2;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.FactorUtils;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;


/**
 * Distinct powers.
 *
 Consider all integer combinations of ab for 2 ≤ a ≤ 5 and 2 ≤ b ≤ 5:

 2^2=4, 2^3=8, 2^4=16, 2^5=32
 3^2=9, 3^3=27, 3^4=81, 3^5=243
 4^2=16, 4^3=64, 4^4=256, 4^5=1024
 5^2=25, 5^3=125, 5^4=625, 5^5=3125

 If they are then placed in numerical order, with any repeats removed, we get the following sequence of 15 distinct terms:

 4, 8, 9, 16, 25, 27, 32, 64, 81, 125, 243, 256, 625, 1024, 3125

 How many distinct terms are in the sequence generated by ab for 2 ≤ a ≤ 100 and 2 ≤ b ≤ 100?

 *
 * @author dzhmud
 */
public class Problem29 implements EulerSolution {

	public static void main(String[] args) {
		new Problem29().measureTime();
	}

	@Override
	public String getAnswer() {
//		return  "" + getAnswerVer1();
		return  "" + getAnswerVer2();

	}

	//1. straightforward approach with BigInteger.
	private long getAnswerVer1() {
		return IntStream.rangeClosed(2, 100).boxed()
				.flatMap(a -> IntStream.rangeClosed(2, 100)
						.mapToObj(b -> BigInteger.valueOf(a).pow(b))
				).distinct().count();
	}

	//2. with base & exponent analysis.
	//we do not need to actually calculate base^exponent,
	//we actually need to check if a0^b0 == a1^b1
	//which can be easily checked w/out actual value.
	private long getAnswerVer2() {
		return IntStream.rangeClosed(2, 100).boxed()
				.flatMap(a -> IntStream.rangeClosed(2, 100)
						.mapToObj(b -> new Pow(a, b))
				).distinct().count();
	}

	private static final class Pow {
		private final int base;
		private final int exponent;
		private final int hashCode;

		Pow(int base, int exponent) {
			this.base = base;
			this.exponent = exponent;
			this.hashCode = getHashCode(base);
		}

		@Override
		public boolean equals(Object o) {
			//overloaded equals will actually check value for equality but without its calculation
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			Pow pow = (Pow) o;
			if (base == pow.base)
				return (exponent == pow.exponent);

			//the only possibility to be equal is when biggerBase = lessBase.pow(lessBase.exp/biggerBase.exp)
			final Pow lessBase, biggerBase;
			lessBase = this.base < pow.base ? this : pow;
			biggerBase = this.base > pow.base ? this : pow;

			//bases incompatible
			if (biggerBase.base % lessBase.base != 0) {
				return false;
			}
			//exponent incompatible
			if (lessBase.exponent <= biggerBase.exponent)
				return false;

			//next check is incorrect: (3^5)^2 = 9^5 !!!!! 5%2 != 0
//			if (lessBase.exponent % biggerBase.exponent != 0)
//				return false;

			Pow biggerBase1 = new Pow(biggerBase.base/lessBase.base, biggerBase.exponent);
			Pow lowerBase1 = new Pow(lessBase.base, lessBase.exponent - biggerBase.exponent);
			return biggerBase1.equals(lowerBase1);
		}

		@Override
		public int hashCode() {
			//overloaded hashCode should return equal hash for equal values
			//to achieve this w/out value calculation, calculate product of all distinct factors of base.
			return hashCode;
		}

		private static int getHashCode(int base) {
			List<Long> factors = FactorUtils.getFactors_v1(base, true);
			AtomicLong result = new AtomicLong(1L);
			factors.forEach(aLong -> result.updateAndGet(operand -> operand*aLong));
			return result.intValue();
		}

		@Override
		public String toString() {
			return base + "^" + exponent;
		}

	}

}
