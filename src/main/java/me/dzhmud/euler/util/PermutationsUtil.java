package me.dzhmud.euler.util;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Different utils for combinatorial tasks.
 *
 * @author dzhmud
 */
public class PermutationsUtil {

	private PermutationsUtil() {}

	/**
	 * Get combinations of input values, where position matters, e.g. we will get 2 combinations of size 2 from
	 * 2 values [A,B]: AB and BA.
	 * @param values values to form combinations from
	 * @param size size of combinations
	 * @param <T> type of values
	 * @return all possible combinations
	 */
	public static <T> List<List<T>> getOrderedCombinations(T[] values, int size) {
		if (size > values.length || size < 1) {
			throw new NotImplementedException();
		} else {
			return getOrderedCombinations(Arrays.asList(values), size);
		}
	}

	/**
	 * Get combinations of input values, where position doesn't matter, e.g. we will get three combinations of size 2
	 * from 3 values [A,B,C]: AB, AC, BC.
	 * @param values values to form combinations from
	 * @param size size of combinations
	 * @param <T> type of values
	 * @return all possible combinations
	 */
	public static <T> List<List<T>> getUnOrderedCombinations(T[] values, int size) {
		if (size > values.length || size < 1) {
			throw new NotImplementedException();
		} else if (size == values.length) {
			return Collections.singletonList(Arrays.asList(values));
		} else {
			return getUnOrderedCombinations(Arrays.asList(values), size);
		}
	}

	private static <T> List<T> getList(T t) {
		List<T> result = new ArrayList<>();
		result.add(t);
		return result;
	}

	private static <T> List<List<T>> getUnOrderedCombinations(final List<T> values, int size) {
		final List<List<T>> result = new ArrayList<>();
		if (size == 1) {
			values.forEach(t -> result.add(getList(t)));
			return result;
		} else {
			for (int i = 0; i < values.size() -size +1; i++) {
				T current = values.get(i);
				List<List<T>> temp = getUnOrderedCombinations(values.subList(i+1, values.size()), size -1);
				temp.forEach(list -> list.add(0, current));//TODO bad idea, refactor later;
				result.addAll(temp);
			}
			return result;
		}
	}

	private static <T> List<List<T>> getOrderedCombinations(final List<T> values, int size) {
		final List<List<T>> result = new ArrayList<>();
		if (size == 1) {
			values.forEach(t -> result.add(getList(t)));
			return result;
		} else {
			for (int i = 0; i < values.size(); i++) {
				List<T> restOfElements = new ArrayList<>(values);
				T current = restOfElements.remove(i);
				List<List<T>> temp = getOrderedCombinations(restOfElements, size -1);
				temp.forEach(list -> list.add(0, current));//TODO bad idea, refactor later;
				result.addAll(temp);
			}
			return result;
		}
	}

			/*private static <T> boolean isCyclicShift(List<T> list0, List<T> list1) {
			assert list0.size() == list1.size();
			final int size = list0.size();
//			if (!list0.containsAll(list1))
//				return false;
			final int index = list1.indexOf(list0.get(0));
			if (index < 0)
				return false;
			for (int i = 0; i < size - index; i++) {
				if (!list0.get(i).equals(list1.get(i+index)))
					return false;
			}
			for (int i = size - index; i < list0.size(); i++) {
				if (!list0.get(i).equals(list1.get(i +index -size)))
					return false;
			}
			return true;
//			final List<T> list2 = new ArrayList<>(list1.subList(index,list1.size()));
//			list2.addAll(list1.subList(0, index));
//			return list2.equals(list0);
//			final List<T> list2 = list1.subList(index, list1.size());
//			final List<T> list20 = list0.subList(0, list2.size());
//			final List<T> list3 = list1.subList(0,index);
//			final List<T> list30 = list0.subList(list2.size(), list0.size());
//			return list2.equals(list20) && list3.equals(list30);
		}*/

}
