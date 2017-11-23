package me.dzhmud.euler.pack6;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.PositionSequence;
import me.dzhmud.euler.util.Tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Cyclical figurate numbers
 * {@see https://projecteuler.net/problem=61 }
 *
 * @author dzhmud
 */
public class Problem61 implements EulerSolution {

	public static void main(String[] args) {
		EulerSolution.measureTime(new Problem61()::getAnswer);
	}

	@Override
	public String getAnswer() {
		//fill sequences to speed up the solution.
		Arrays.stream(new PositionSequence[]{p3,p4,p5,p6,p7,p8}).forEach(ps -> ps.contains(10000));

		long[] answer = IntStream.range(1010,10000)
				.filter(p8::contains)
				.filter(x -> getThirdDigit(x) != 0)
				.mapToObj(n8 -> new CycleSequence(n8, p8))
				.flatMap(CycleSequence::getNextVariants)
				.flatMap(CycleSequence::getNextVariants)
				.flatMap(CycleSequence::getNextVariants)
				.flatMap(CycleSequence::getNextVariants)
				.flatMap(CycleSequence::getNextVariants)
				.findFirst()
				.orElseThrow(SolutionNotFoundException::new)
				.numbers;
		return "" + Arrays.stream(answer).sum();
	}

	final static PositionSequence
			p3 = PositionSequence.get(n -> (long)n*(n+1)/2),
			p4 = PositionSequence.get(n -> (long)n*n),
			p5 = PositionSequence.get(n -> (long)n*(3*n-1)/2),
			p6 = PositionSequence.get(n -> (long)n*(2*n-1)),
			p7 = PositionSequence.get(n -> (long)n*(5*n-3)/2),
			p8 = PositionSequence.get(n -> (long)n*(3*n-2));

	private static int getThirdDigit(long value) {
		return (int)(value %100 /10);
	}

	private static class CycleSequence implements Cloneable {
		private long[] numbers = new long[0];
		private LinkedList<PositionSequence> sequencesUsed = new LinkedList<>();
		private LinkedList<PositionSequence> sequencesUnUsed = new LinkedList<>(Arrays.asList(p3,p4,p5,p6,p7,p8));

		CycleSequence(long value, PositionSequence ps) {
			assert getThirdDigit(value) != 0;
			numbers = Arrays.copyOf(numbers, numbers.length+1);
			numbers[numbers.length-1] = value;
			sequencesUsed.add(ps);
			sequencesUnUsed.remove(ps);
		}

		private CycleSequence add(long value, PositionSequence ps) {
			CycleSequence clone = clone();
			clone.numbers = Arrays.copyOf(clone.numbers, clone.numbers.length+1);
			clone.numbers[clone.numbers.length-1] = value;
			clone.sequencesUsed.add(ps);
			clone.sequencesUnUsed.remove(ps);
			return clone;
		}

		Stream<CycleSequence> getNextVariants() {
			final long firstTwoDigits = numbers[numbers.length-1] %100;
			if (numbers.length  == 5) {
				long candidate = firstTwoDigits*100 + numbers[0] /100;
				if (sequencesUnUsed.get(0).contains(candidate)) {
					CycleSequence result = add(candidate, sequencesUnUsed.get(0));
					return Stream.of(result);
				} else {
					return Stream.empty();
				}
			} else {
				List<Tuple<Long, PositionSequence>> variants = new ArrayList<>();
				for (long value = firstTwoDigits*100+10; value < (firstTwoDigits+1)*100; value++) {
					if (getThirdDigit(value) == 0)
						continue;
					for (PositionSequence ps : sequencesUnUsed) {
						if (ps.contains(value))
							variants.add(Tuple.of(value, ps));
					}
				}
				return variants.stream().map(tuple -> add(tuple.getKey(), tuple.getValue()));
			}
		}

		@Override
		protected CycleSequence clone() {
			try {
				CycleSequence cs = (CycleSequence) super.clone();
				cs.numbers = Arrays.copyOf(numbers, numbers.length);
				cs.sequencesUsed = new LinkedList<>(sequencesUsed);
				cs.sequencesUnUsed = new LinkedList<>(sequencesUnUsed);
				return cs;
			} catch (CloneNotSupportedException cnse) {
				throw new IllegalArgumentException();
			}
		}

		@Override
		public String toString() {
			return Arrays.toString(numbers);
		}
	}

}
