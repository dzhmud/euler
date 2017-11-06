package me.dzhmud.euler;

import me.dzhmud.euler.util.PrimeUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Prime permutations
 *
 The arithmetic sequence, 1487, 4817, 8147, in which each of the terms increases by 3330, is unusual in two ways:
 (i) each of the three terms are prime, and,
 (ii) each of the 4-digit numbers are permutations of one another.

 There are no arithmetic sequences made up of three 1-, 2-, or 3-digit primes, exhibiting this property,
 but there is one other 4-digit increasing sequence.

 What 12-digit number do you form by concatenating the three terms in this sequence?

 *
 * @author dzhmud
 */
public class Problem49 implements EulerSolution {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		System.out.println(new Problem49().getAnswer());
		System.out.println("Solution take " + (System.currentTimeMillis() - start) / 1000 + " sec");
	}

	@Override
	public String getAnswer() {
		return IntStream.range(1000, 10000)
				.filter(PrimeUtils::isPrime_v1)
				.mapToObj(Problem49::getPrimePermutations)
				.filter(list -> list.size() >= 3)
				.peek(list -> list.sort(Comparator.naturalOrder()))
				.filter(list -> list.get(0) > 1000)
				.filter(list -> list.get(0) != 1487)
				.distinct()
				.map(Problem49::getArithmeticSequence)
				.filter(list -> list.size() == 3)
				.findFirst().orElseThrow(IllegalArgumentException::new)

				.stream()
				.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
				.toString();
	}

	private static List<Integer> getPrimePermutations(int value) {
		final List<Integer> values = new ArrayList<>();
		int[] digits = new int[]{value %10, value %100 /10, value %1000 /100, value /1000};
		for (int i0 = 0; i0 < 4; i0++) {
			for (int i1 = 0; i1 < 4; i1++) {
				if (i1 == i0) continue;
				for (int i2 = 0; i2 < 4; i2++) {
					if (i2 == i0 || i2 == i1) continue;
					int i3 = (3+2+1) - (i0+i1+i2);

					values.add(1000*digits[i0] + 100* digits[i1] + 10*digits[i2] + digits[i3]);
				}
			}
		}
		return values.stream().distinct().filter(PrimeUtils::isPrime_v1).collect(Collectors.toList());
	}

	/**
	 * Try to find arithmetic sequence in the list of prime permutations.
	 * NOTE: list given to this method should be sorted in ascending order.
	 * @param list list of prime permutations, of size 3+
	 * @return list of permutations that form arithmetic sequence, or empty list
	 */
	private static List<Integer> getArithmeticSequence(List<Integer> list) {
		for (int i = 0; i < list.size() - 2; i++) {
			for (int j = i+1; j < list.size() -1; j++) {
				int diff = list.get(j) - list.get(i);
				int thirdInSequence = list.get(j) + diff;
				if (list.contains(thirdInSequence))
					return Arrays.asList(list.get(i), list.get(j), thirdInSequence);
			}
		}
		return Collections.emptyList();
	}
}
