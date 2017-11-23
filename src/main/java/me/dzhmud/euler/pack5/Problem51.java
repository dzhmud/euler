package me.dzhmud.euler.pack5;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.DigitsUtils;
import me.dzhmud.euler.util.PrimeUtils;
import me.dzhmud.euler.util.Tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Prime digit replacements
 *
 By replacing the 1st digit of the 2-digit number *3, it turns out that six of the nine possible values:
 13, 23, 43, 53, 73, and 83, are all prime.

 By replacing the 3rd and 4th digits of 56**3 with the same digit, this 5-digit number is the first example
 having seven primes among the ten generated numbers, yielding the family:
 56003, 56113, 56333, 56443, 56663, 56773, and 56993.
 Consequently 56003, being the first member of this family, is the smallest prime with this property.

 Find the smallest prime which, by replacing part of the number (not necessarily adjacent digits)
 with the same digit, is part of an eight prime value family.

 *
 * @author dzhmud
 */
public class Problem51 implements EulerSolution {

	public static void main(String[] args) {
		new Problem51().measureTime();
	}

	/**
	 * can be speed up after some refactoring.
	 * currently, the solution to problem is a 3-digit permutation,
	 * and before we get to answer, bunch of 4- and 5-digit permutations are tested on lower values.
	 */
	@Override
	public String getAnswer() {
		List<Long> result = null;
		final int targetSize = 8;
		for (int value = 11; value < 200000 && result == null; value++) {
			List<PermutationStrategy> strategies = getAllStrategies(value);
			for (PermutationStrategy strategy: strategies) {
				List<Long> permutations = filterPrimes(strategy.permute(value));
				if (permutations.size() == targetSize) {
					result = permutations;
					System.out.println("result = " + result);
					break;
				}
			}
		}
		return "" + ((result != null) ? result.get(0) : "not found!");
	}

	private static List<Long> filterPrimes(List<Long> values) {
		return values.stream().filter(PrimeUtils::isPrime_v2).collect(Collectors.toList());
	}

	/**
	 * Set of possible permutation strategies differs only if digits count differs,
	 * therefore strategies can be cached using digits count as key.
	 * And also, because they are stateless :)
	 */
	private static Map<Integer, List<PermutationStrategy>> CACHE = new HashMap<>();

	private static List<PermutationStrategy> getAllStrategies(long value) {
		final int digitsCount = DigitsUtils.getDigitsCount(value);
		return CACHE.computeIfAbsent(digitsCount, x -> {
			List<PermutationStrategy> result = new ArrayList<>();
			for (int i = 1; i < digitsCount ; i++) {
				result.addAll(PermutationStrategy.XDigitPermutation.getStrategies(value, i));
			}
			return result;
		});

	}

	interface PermutationStrategy {
		List<Long> permute(long value);

		abstract class AbtractPermutationStrategy implements PermutationStrategy {
			private static long[]powersOfTen = new long[]{1, 10};
			static long getPowerOfTen(int index) {
				if (index >= powersOfTen.length) {
					powersOfTen = Arrays.copyOf(powersOfTen, index+1);
					for (int i = 2; i < powersOfTen.length; i++) {
						powersOfTen[i] = powersOfTen[i-1]*10;
					}
				}
				return powersOfTen[index];
			}
		}

		@SuppressWarnings("unused")
		class SingleDigitPermutation extends AbtractPermutationStrategy {
			/**
			 * Depending on value length, there will be different count of strategies.
			 * In this(singe digit permutation) case, method will return list of strategies,
			 * where single strategy is responsible for mutating single digit position, except last digit.
			 * @param value the value to which result will be applied.
			 * @return list of strategies
			 */
			static List<PermutationStrategy> getStrategies(long value) {
				// cache results of this method!
				if (value < 10) throw new IllegalArgumentException("Check algorithm!");
				int count = DigitsUtils.getDigitsCount(value);
				List<PermutationStrategy> result = new ArrayList<>(count-1);
				for (int i = 0; i < count -1; i++) {
					result.add(new SingleDigitPermutation(i));
				}
				return result;
			}

			/** mutation position, 0 for highest digit, 1 for next and so on. */
			private final int position;

			private SingleDigitPermutation(int position) {
				this.position = position;
			}

			@Override
			public List<Long> permute(long value) {
				final int[]digits = DigitsUtils.getDigits_v2(value);
				final long power = getPowerOfTen(digits.length - position-1);
				List<Long> result = new ArrayList<>(10);
				int mutatedDigitValue = digits[position];
				value -= (mutatedDigitValue) * power;//mutated position got 0
				if (position > 0) {
					result.add(value);
				}
				for (int i = 0; i < 9; i++)
					result.add(value+=power);
				return result;
			}

			@Override
			public String toString() {
				return String.format("SingleDigitPermutation(%s)", position);
			}
		}

		@SuppressWarnings("unused")
		class TwoDigitPermutation extends AbtractPermutationStrategy {
			/**
			 * Depending on value length, there will be different count of strategies.
			 * In this(two digit permutation) case, method will return list of strategies,
			 * where single strategy is responsible for mutating two digit positions, except last digit.
			 * @param value the value to which result will be applied.
			 * @return list of strategies
			 */
			static List<PermutationStrategy> getStrategies(long value) {
				if (value < 100) throw new IllegalArgumentException("Check algorithm! Asked for 2-digit permutations on value < 100!");
				int count = DigitsUtils.getDigitsCount(value);
				final List<PermutationStrategy> result = new ArrayList<>(count-2);
				for (int i = 0; i < count -2; i++) {
					for (int j = i+1; j < count -1; j++) {
						result.add(new TwoDigitPermutation(i, j));
					}
				}
				return result;
			}

			/** mutation positions, 0 for highest digit, 1 for next and so on. */
			private final int position0, position1;

			private TwoDigitPermutation(int position0, int position1) {
				assert position0 < position1;//some code relies on this
				this.position0 = position0;
				this.position1 = position1;
			}

			@Override
			public List<Long> permute(long value) {
				final List<Long> result = new ArrayList<>(9);
				final int[]digits = DigitsUtils.getDigits_v2(value);
				final long power0 = getPowerOfTen(digits.length - position0-1),
						power1 = getPowerOfTen(digits.length - position1-1);
				final int mutatedDigitValue0 = digits[position0],
						mutatedDigitValue1 = digits[position1];
				value -= (mutatedDigitValue0) * power0;//mutated position0 got 0
				value -= (mutatedDigitValue1) * power1;//mutated position1 got 0
				if (position0 > 0) {
					result.add(value);
				}
				for (int i = 0; i < 9; i++) {
					result.add(value += power0 + power1);
				}
				return result;
			}

			@Override
			public String toString() {
				return String.format("TwoDigitPermutation(%s, %s)", position0, position1);
			}

		}

		class XDigitPermutation extends AbtractPermutationStrategy {

			static Map<Tuple<Integer,Integer>, List<PermutationStrategy>> cache = new HashMap<>();
			/**
			 * Depending on value length, there will be different count of strategies.
			 * In this(x digit permutation) case, method will return list of strategies,
			 * where single strategy is responsible for mutating x digit positions, except last digit.
			 * @param value the value to which result will be applied.
			 * @return list of strategies
			 */
			static List<PermutationStrategy> getStrategies(long value, int mutationsCount) {
				final Tuple<Integer,Integer> cacheKey = Tuple.of(DigitsUtils.getDigitsCount(value),mutationsCount);
				if (cache.containsKey(cacheKey))
					return cache.get(cacheKey);

				final List<PermutationStrategy> result = new ArrayList<>();
				for(int[] positions : getPositions(value, mutationsCount))
					result.add(new XDigitPermutation(positions));

				cache.put(cacheKey, result);
				return result;
			}

			private static int[][] getPositions(long value, int mutationsCount) {
				int digitsCount = DigitsUtils.getDigitsCount(value);
				assert digitsCount >= mutationsCount + 1;
				int[] positions = IntStream.range(0, digitsCount).toArray();
				return takeXofY(positions, mutationsCount);
			}

			/**
			 * Simple combinatorial task to take X items from Y.
			 * Here we take #count of digits from array containing subsequent integers([0,1,2,..N])
			 * All possible variants are stored in array-of-arrays.
			 * @param digits digit positions to select from
			 * @param count number of digit positions to select
			 * @return all possible selections.
			 */
			private static int[][] takeXofY(int[] digits, int count) {
				assert count < digits.length;
				int[][] result = new int[0][count];
				if (count == 1) {
					result = new int[digits.length][count];
					for (int i = 0; i < digits.length; i++) {
						result[i] = new int[]{digits[i]};
					}
					return result;
				}
				int[] current = new int[count];
				for (int j = 0; j < digits.length - count+1; j++) {
					current[0] = digits[j];
					int[][] next = takeXofY(Arrays.copyOfRange(digits, j+1, digits.length), count-1);
					if (next.length > 0) {
						for (int[] permutation : next) {
							result = Arrays.copyOf(result, result.length+1);
							System.arraycopy(permutation, 0, current, 1, permutation.length);
							result[result.length-1] = Arrays.copyOf(current, current.length);
						}
					}
				}
				return result;
			}

			/** mutation positions, 0 for highest digit, 1 for next and so on. */
			private final int[] positions;

			private XDigitPermutation(int... positions) {
				Arrays.sort(positions);
				this.positions = positions;
			}

			@Override
			public List<Long> permute(long value) {
				final List<Long> result = new ArrayList<>(9);
				final int[]digits = DigitsUtils.getDigits_v2(value);
				long powersSum = 0;
				for (int position : positions) {
					final int mutatedDigitValue = digits[position];
					final long powerOfTen = getPowerOfTen(digits.length - position -1);
					value -= mutatedDigitValue * powerOfTen;//change current digit to 0
					powersSum += powerOfTen;
				}
				if (positions[0] > 0) {
					result.add(value);//only if current strategy does not mutate first digit
				}
				for (int i = 0; i < 9; i++) {
					result.add(value += powersSum);
				}
				return result;
			}

			@Override
			public String toString() {
				StringBuilder sb = new StringBuilder("XDigitPermutation(");
				Arrays.stream(positions).forEachOrdered(x -> sb.append(x).append(","));
				sb.setCharAt(sb.length()-1, ')');
				return sb.toString();
			}

		}

	}

}
