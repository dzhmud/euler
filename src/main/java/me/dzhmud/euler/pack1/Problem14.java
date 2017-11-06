package me.dzhmud.euler.pack1;

import me.dzhmud.euler.EulerSolution;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 *

 The following iterative sequence is defined for the set of positive integers:

 n → n/2 (n is even)
 n → 3n + 1 (n is odd)

 Using the rule above and starting with 13, we generate the following sequence:
 13 → 40 → 20 → 10 → 5 → 16 → 8 → 4 → 2 → 1

 It can be seen that this sequence (starting at 13 and finishing at 1) contains 10 terms. Although it has not been proved yet (Collatz Problem), it is thought that all starting numbers finish at 1.

 Which starting number, under one million, produces the longest chain?

 NOTE: Once the chain starts the terms are allowed to go above one million.

 *
 * @author dzhmud
 */
public class Problem14 implements EulerSolution {

	public static void main(String[] args) {
		System.out.println(new Problem14().getAnswer());
	}

	@Override
	public String getAnswer() {
		//cache instead of finding max on each iteration.
		final Map<Integer,Long> lengths= new HashMap<>(1000*1000, 1.0f);
		IntStream.rangeClosed(1, 1000*1000).parallel().forEach(i -> lengths.put(i, getSequenceLength(i)));
		return "" + lengths.entrySet().stream()
				.max(Comparator.comparing(Map.Entry::getValue))
				.orElseThrow(IllegalArgumentException::new)
				.getKey();
	}

	//here caching number<->steps can be added.
	private static long getSequenceLength(final int startValue) {
		long current = startValue;
		long iterations = 0;
		while (current > 1) {
			++iterations;
			current = current%2 == 0 ? current/2 : 3*current + 1;
		}
		return iterations;
	}

}
