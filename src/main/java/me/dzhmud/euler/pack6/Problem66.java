package me.dzhmud.euler.pack6;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.BigIntegerFraction;
import me.dzhmud.euler.util.PositionSequence;
import me.dzhmud.euler.util.SQRTContinuedFractions;
import me.dzhmud.euler.util.Tuple;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.IntStream;

/**
 * Diophantine equation
 * Consider quadratic Diophantine equations of the form:
    x^2 – Dy^2 = 1

 For example, when D=13, the minimal solution in x is 649^2 – 13×180^2 = 1.
 It can be assumed that there are no solutions in positive integers when D is square.

 By finding minimal solutions in x for D = {2, 3, 5, 6, 7}, we obtain the following:
	 3^2 – 2×2^2 = 1
	 2^2 – 3×1^2 = 1
	 9^2 – 5×4^2 = 1
	 5^2 – 6×2^2 = 1
	 8^2 – 7×3^2 = 1
 Hence, by considering minimal solutions in x for D ≤ 7, the largest x is obtained when D=5.

 Find the value of D ≤ 1000 in minimal solutions of x for which the largest value of x is obtained.
 *
 * @author dzhmud
 */
public class Problem66 implements EulerSolution {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		System.out.println(new Problem66().getAnswer());
		System.out.println("Solution take " + (System.currentTimeMillis() - start) / 1000 + " sec");
	}

	/**
	 * {@see https://en.wikipedia.org/wiki/Pell%27s_equation#Fundamental_solution_via_continued_fractions}
	 * Code from {@link Problem64} and {@link Problem65} can be used to first generate continued fractions for each D
	 * and then iteratively try fractions until we get a solution.
	 */
	@Override
	public String getAnswer() {
		return "" + IntStream.rangeClosed(2, 1000)
				.filter(D -> !squares.contains(D))
				.mapToObj(Problem66::findSolution)
				.max(Comparator.comparing(Tuple::getValue))
				.orElseThrow(SolutionNotFoundException::new)
				.getKey();
	}

	private static PositionSequence squares = PositionSequence.get(n -> (long)n*n);

	private static Tuple<BigInteger, BigInteger> findSolution(int D) {
		final SQRTContinuedFractions sqrt = new SQRTContinuedFractions(D);
		final int[] fractions = sqrt.getSequence();
		final LinkedList<Integer> fractionsList = new LinkedList<>();
		fractionsList.add(sqrt.roundPart);
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			fractionsList.add(fractions[i %fractions.length]);
			BigIntegerFraction fraction = BigIntegerFraction.getValue(fractionsList);
			if (fraction.denominator.pow(2).multiply(BigInteger.valueOf(D)).add(BigInteger.ONE)
					.equals(fraction.nominator.pow(2))) {
				return Tuple.of(BigInteger.valueOf(D), fraction.nominator);
			}
		}
		throw new SolutionNotFoundException();
	}

}
