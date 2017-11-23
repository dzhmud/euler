package me.dzhmud.euler.pack6;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.Tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Cubic permutations
 *

 The cube, 41063625 (345^3), can be permuted to produce two other cubes: 56623104 (384^3) and 66430125 (405^3).
 In fact, 41063625 is the smallest cube which has exactly three permutations of its digits which are also cube.

 Find the smallest cube for which exactly five permutations of its digits are cube.

 *
 * @author dzhmud
 */
public class Problem62 implements EulerSolution {

	public static void main(String[] args) {
		new Problem62().measureTime();
	}

	@Override
	public String getAnswer() {
		long low = 10, high = 100;
		for (int i = 1; i < 20; i++) {
			low *= 10;
			high *= 10;
			//find all cubes between high and low(they have same amount of digits)
			final List<Long> values = new ArrayList<>();
			for (long value = (long) Math.ceil(Math.cbrt(low)); value < (long) Math.floor(Math.cbrt(high)); value++) {
				values.add(value*value*value);
			}
			//map cubes to String, then sort result alphabetically, and find the result, that repeats exactly 5 times
		    final List<Tuple<Long,String>> result = values.stream()
					.map(longValue -> Tuple.of(longValue, sort(String.valueOf(longValue))))
					.collect(Collectors.groupingBy(Tuple::getValue))
					.entrySet().stream().filter(entry -> entry.getValue().size() == 5)
					.map(Map.Entry::getValue)
					.findFirst()
					.orElse(Collections.emptyList());
			//when result found, iterate al 5 cubes to find minimum value.
			if (result.size() != 0) {
					return ""+result.stream()
					.map(Tuple::getKey)
					.min(Comparator.naturalOrder())
					.orElseThrow(SolutionNotFoundException::new);
			}
		}
		throw new SolutionNotFoundException();
	}

	private static String sort(String s) {
		char[] chars = s.toCharArray();
		Arrays.sort(chars);
		return new String(chars);
	}

}
