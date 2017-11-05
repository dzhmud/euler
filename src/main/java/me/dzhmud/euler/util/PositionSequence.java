package me.dzhmud.euler.util;

import java.util.Arrays;
import java.util.function.Function;

/**
 * Generic sequence class for sequences where each member is calculated based on position in sequence.
 *
 * @author dzhmud
 */
public class PositionSequence {

	public static PositionSequence get(Function<Integer, Long> function) {
		return new PositionSequence(function);
	}

	private PositionSequence(Function<Integer, Long> function) {
		this.function = function;
	}

	private final Function<Integer, Long> function;

	private long[] values = new long[]{1};

	public long getValue(final int index) {
		if (values.length < index) {
			fillToIndex(index);
		}
		return values[index - 1];
	}

	public boolean contains(final long value) {
		if (values[values.length-1] < value) {
			fillToValue(value);
			return values[values.length-1] == value;
		}
		return index(value) >= 0;
	}

	public int index(final long value) {
		return Arrays.binarySearch(values, value);
	}

	private void fillToValue(final long value) {
		if (values[values.length-1] < value) {
			while(getValue(values.length) < value)
				fillToIndex(values.length+1);
		} else {
			System.out.println("No need to call #fillToValue(). Check your code!");
		}
	}

	private void fillToIndex(final int index) {
		if (index > values.length) {
			final int prevLength = values.length;
			values = Arrays.copyOf(values, index);
			for (int i = prevLength+1; i <= values.length; i++) {
				long value = function.apply(i);
				values[i-1] = value;
			}
		}
	}

}
