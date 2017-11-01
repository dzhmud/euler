package me.dzhmud.euler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Coin sums.
 *
 In England the currency is made up of pound, £, and pence, p, and there are eight coins in general circulation:

 1p, 2p, 5p, 10p, 20p, 50p, £1 (100p) and £2 (200p).

 It is possible to make £2 in the following way:

 1×£1 + 1×50p + 2×20p + 1×5p + 1×2p + 3×1p

 How many different ways can £2 be made using any number of coins?

 *
 * @author dzhmud
 */
public class Problem31 implements EulerSolution {

	public static void main(String[] args) {
		System.out.println(new Problem31().getAnswer());
	}

	@Override
	public String getAnswer() {
		HashSet<Coins> coins = new LinkedHashSet<>(Arrays.asList(Coins.values()));
		String result =  ""+ countDifferentWays(200, coins, new ArrayList<>());
		System.out.println(differentWays);
		return result;
	}

	private static List<List<Coins>> differentWays = new ArrayList<>();

	//TODO clean this out
	private static int countDifferentWays(int targetSum, HashSet<Coins> availableCoins, ArrayList<Coins> fixedCoins) {
		if (targetSum == 0) {
			differentWays.add((List<Coins>)fixedCoins.clone());
			return 1;
		}
		if (availableCoins.size() == 1) {//assume it is 1p.
			List<Coins> finishedVariant = (List<Coins>)fixedCoins.clone();
			Coins lastCoin = availableCoins.iterator().next();
			while (targetSum-- > 0) {
				finishedVariant.add(lastCoin);
			}
			differentWays.add(finishedVariant);
			return 1;
		}
		int result = 0;
		Coins biggest = getMax(availableCoins);
		availableCoins = (HashSet<Coins>)availableCoins.clone();
		availableCoins.remove(biggest);
		System.out.println(biggest + " removing from set.");
		if (biggest.value > targetSum) {
			System.out.println(biggest + " is too much for " + targetSum);
			return countDifferentWays(targetSum, availableCoins, fixedCoins);
		} else if (biggest.value == targetSum) {
//			availableCoins.remove(biggest);
			System.out.println(biggest+ " == targetSum, count 1 + number of ways w/out this value.");
			{
				List<Coins> finishedVariant = (List<Coins>)fixedCoins.clone();
				finishedVariant.add(biggest);
				differentWays.add(finishedVariant);
			}
			return 1 + countDifferentWays(targetSum, availableCoins, fixedCoins);
		} else {
//			availableCoins.remove(biggest);
			System.out.println(biggest+ " < " + targetSum);
			boolean withoutCurrentBiggestCoin = true;
			while (targetSum >= 0) {
				System.out.println("counting ways(" + targetSum+", "+availableCoins+")");

				fixedCoins = (ArrayList<Coins>)fixedCoins.clone();
				if (withoutCurrentBiggestCoin) {
					withoutCurrentBiggestCoin = false;
				} else {
					fixedCoins.add(biggest);
				}

				result += countDifferentWays(targetSum, availableCoins, fixedCoins);
				targetSum -= biggest.value;
			}
		}
		return result;
	}

	private static Coins getMax(Collection<Coins> collection) {
		if (collection == null || collection.isEmpty())
			throw new IllegalArgumentException("empty collection unexpected here!");
		Coins result =  Coins.p1;
		for (Coins c: collection) {
			if (c.value > result.value)
				result = c;
		}
		return result;
	}

	private enum Coins {
		p1(1), p2(2), p5(5), p10(10), p20(20), p50(50), £1(100), £2(200);
		private final int value;

		Coins(int pence) {
			this.value = pence;
		}

		@Override
		public String toString() {
			return value < 100 ? value + "p" : name();
		}

	}

}
