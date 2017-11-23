package me.dzhmud.euler.pack6;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.PermutationsUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Magic 5-gon ring
 * {@see https://projecteuler.net/problem=68}
 *
 * @author dzhmud
 */
public class Problem68 implements EulerSolution {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		System.out.println(new Problem68().getAnswer());
		System.out.println("Solution take " + (System.currentTimeMillis() - start) / 1000 + " sec");
	}

	@Override
	public String getAnswer() {
		return "" + new MagicRing(5).getSolutions().stream()
				.map(s -> s.replaceAll("[,;]",""))
				.filter(s -> s.length() == 16)
				.max(Comparator.naturalOrder())
				.orElseThrow(SNFE);
	}

	private static class MagicRing {

		private final int size;
		private Integer[] values;
		private final int oneToXSum;
		private List<Group> groups = new ArrayList<>();

		MagicRing(final int size) {
			this.size = size;
			this.values = new Integer[2*size];
			for (int i = 0; i < 2*size; )
				values[i] = ++i;
			oneToXSum = Arrays.stream(values).mapToInt(x->x).sum();
			Group first = new Group(new Node(), new Node(), new Node());
			groups.add(first);
			for(int i = 1; i < size; i++) {
				Node thirdNode = (i == size - 1) ? groups.get(0).middle : new Node();
				Group group = new Group(new Node(), groups.get(i-1).third, thirdNode);
				groups.add(group);
			}
			groups = Collections.unmodifiableList(groups);
		}

		@SuppressWarnings("unused")
		boolean isMagicRing() {
			return groups.stream().mapToInt(Group::getSum).distinct().count() == 1;
		}

		List<Integer> getOuterValues(List<Integer> innerRing) {
			final List<Integer> outer = new ArrayList<>(Arrays.asList(values));
			outer.removeAll(innerRing);
			return outer;
		}

		Collection<String> getSolutions() {
			final Set<String> result = new HashSet<>();
			PermutationsUtil.getOrderedCombinations(values, size).forEach(
					innerRing -> {
						final int innerSum = innerRing.stream().mapToInt(x->x).sum();
						if (innerSum % size > 0) return;
						clearNodes();
						for (int i = 0; i < groups.size(); i++) {
							groups.get(i).middle.value = innerRing.get(i);
						}
						final int groupSum = (innerSum + oneToXSum) / size;
						final List<Integer> outer = getOuterValues(innerRing);
						for (Group group : groups) {
							Integer externalValue = groupSum - group.middle.value - group.third.value;
							if (outer.contains(externalValue)) {
								group.external.value = externalValue;
								outer.remove(externalValue);
							} else {
								break;
							}
						}
						if (outer.size() == 0) {//this is definitely the magic ring
							result.add(this.toString());
						}
					}
			);
			return result;
		}

		private void clearNodes() {
			groups.forEach(Group::clearValues);
		}

		@Override
		public String toString() {
			final Group minExternalNode = groups.stream().min(Group.BY_EXTERNAL_NODE).orElseThrow(SNFE);
			final int index = groups.indexOf(minExternalNode);
			final List<Group> sorted = new ArrayList<>();
			sorted.addAll(groups.subList(index, groups.size()));
			sorted.addAll(groups.subList(0, index));
			return sorted.stream().map(Group::toString).collect(Collectors.joining(";"));
		}

		static class Node {
			int value;

			@Override
			public String toString() {
				return String.valueOf(value);
			}
		}

		static class Group {

			static final Comparator<Group> BY_EXTERNAL_NODE = Comparator.comparing(gr -> gr.external.value);

			final Node external;
			final Node middle;
			final Node third;

			Group(Node external, Node middle, Node third) {
				this.external = external;
				this.middle = middle;
				this.third = third;
			}

			int getSum() {
				return external.value + middle.value + third.value;
			}

			private void clearValues() {
				external.value = 0;
				middle.value = 0;
				third.value = 0;
			}

			@Override
			public String toString() {
				return String.format("%s,%s,%s", external, middle, third);
			}
		}
	}

}
