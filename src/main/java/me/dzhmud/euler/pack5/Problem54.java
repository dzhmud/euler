package me.dzhmud.euler.pack5;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.FileUtils;
import me.dzhmud.euler.util.Tuple;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Poker hands
 * {@see https://projecteuler.net/problem=54}
 *
 * @author dzhmud
 */
public class Problem54 implements EulerSolution {

	public static void main(String[] args) {
		new Problem54().measureTime();
	}

	@Override
	public String getAnswer() {
		return "" + readFile().stream()
				.filter(round -> round.getKey().compareTo(round.getValue()) > 0)
				.count();
	}

	private static List<Tuple<PlayerHand, PlayerHand>> readFile() {
		final List<String> rows = FileUtils.getContents("Problem54.txt", Collectors.toList());
		return rows.stream().map(row -> {
			final String[] cards = row.split("\\s");
			PlayerHand first = new PlayerHand(Arrays.copyOf(cards, 5));
			PlayerHand second = new PlayerHand(Arrays.copyOfRange(cards, 5, 10));
			return Tuple.of(first, second);
		}).collect(Collectors.toList());
	}

	private static class PlayerHand implements Comparable<PlayerHand> {

		private final List<Card> cards;

		PlayerHand(String[] cards) {
			this(Arrays.stream(cards).map(Card::of).toArray(Card[]::new));
		}

		PlayerHand(Card... cards) {
			assert cards.length == 5;
			this.cards = Arrays.asList(cards);
			this.cards.sort(Comparator.reverseOrder());
		}

		@Override
		public int compareTo(PlayerHand o) {
			int result = getRank().compare(o.getRank());
			if (result == 0) {
				for (int i = 0; i < 5 && result == 0; i++) {
					result = cards.get(i).compareTo(o.cards.get(i));
				}
			}
			return result;
		}

		@Override
		public String toString() {
			return cards.toString();
		}

		private HandRank<?> getRank() {
			return getPlayerHandRank(this);
		}

		static List<Function<PlayerHand, HandRank>> rankFunctions = Arrays.asList(
				HandRank.RoyalFlush::tryRank,
				HandRank.StraightFlush::tryRank,
				HandRank.FourOfAKind::tryRank,
				HandRank.FullHouse::tryRank,
				HandRank.Flush::tryRank,
				HandRank.Straight::tryRank,
				HandRank.ThreeOfAKind::tryRank,
				HandRank.TwoPairs::tryRank,
				HandRank.OnePair::tryRank,
				HandRank.HighCard::tryRank
		);

		static HandRank getPlayerHandRank(final PlayerHand playerHand) {
			HandRank result = null;
			for (Function<PlayerHand, HandRank> rankFunction : rankFunctions) {
				result = rankFunction.apply(playerHand);
				if (result != null)
					break;
			}
			return result;
		}
	}



	static class Card extends Tuple<Value,Suit> implements Comparable<Card> {

		static Card of(String card) {
			assert card.length() == 2;
			return new Card(Value.of(card.charAt(0)), Suit.of(card.charAt(1)));
		}

		Card(Value value, Suit suit) {
			super(value, suit);
		}

		Value getNominal() {
			return getKey();
		}

		Suit getSuit() {
			return getValue();
		}

		@Override
		public int compareTo(Card o) {
			return getNominal().compareTo(o.getNominal());
		}

		boolean isOneLevelHigher(Card o) {
			return getNominal().isOneLevelHigher(o.getNominal());
		}

		boolean eq(Card o) {
			return compareTo(o) == 0;
		}

		@Override
		public String toString() {
			return getNominal().shortcut+getSuit().shortcut;
		}

	}

	private enum Suit {
		DIAMONDS("D"),
		HEARTS("H"),
		SPADES("S"),
		CLUBS("C");
		
		private final String shortcut;
		Suit(String shortcut) {
			this.shortcut = shortcut;
		}

		static Suit of(char shortcut) {
			for (Suit s: values() ) {
				if (s.shortcut.charAt(0) == shortcut)
					return s;
			}
			throw new IllegalArgumentException();
		}
	}
	
	private enum Value implements Comparable<Value> {
		v2("2"),
		v3("3"), 
		v4("4"), 
		v5("5"), 
		v6("6"), 
		v7("7"), 
		v8("8"), 
		v9("9"), 
		v10("T"),
		Jack("J"),
		Queen("Q"),
		King("K"),
		Ace("A");

		private final String shortcut;
		Value(String shortcut) {
			this.shortcut = shortcut;
		}

		boolean isOneLevelHigher(Value other) {
			return ordinal() - other.ordinal() == 1;
		}

		static Value of(char shortcut) {
			for (Value value: values() ) {
				if (value.shortcut.charAt(0) == shortcut)
					return value;
			}
			throw new IllegalArgumentException();
		}

	}

	private abstract static class HandRank<T extends HandRank> implements Comparable<T> {

		final int rank;

		private HandRank(int rank) {
			this.rank = rank;
		}

		@SuppressWarnings("unchecked")
		int compare(HandRank<?> o) {
			int result = Integer.compare(rank, o.rank);
			if (result == 0) {
				result = compareTo((T)o);
			}
			return result;
		}

		/**
		 * Default implementation. Sub-classes should override, if needed.
		 * Some (e.g. Royal Flush) can take this impl.
		 * @param other other instance of current class
		 * @return zero by default. See subclasses.
		 */
		public int compareTo(T other) {
			return 0;
		}

		@Override
		public String toString() {
			return getClass().getSimpleName();
		}


		static class HighCard extends HandRank<HighCard> {
			private final Card highest;
			private HighCard(Card highest) {
				super(0);
				this.highest = highest;
			}

			public int compareTo(HighCard o) {
				return highest.compareTo(o.highest);
			}

			static HighCard tryRank(PlayerHand playerHand) {
				return new HighCard(playerHand.cards.get(0));
			}
		}

		static class OnePair extends HandRank<OnePair> {
			private final Card oneOfPair;
			private OnePair(Card oneOfPair) {
				super(1);
				this.oneOfPair = oneOfPair;
			}

			public int compareTo(OnePair o) {
				return oneOfPair.compareTo(o.oneOfPair);
			}

			static OnePair tryRank(PlayerHand playerHand) {
				List<Card> cards = playerHand.cards;
				for (int i = 0; i < cards.size()-1; i++) {
					if (cards.get(i).eq(cards.get(i+1)))
						return new OnePair(cards.get(i));
				}
				return null;
			}
		}

		static class TwoPairs extends HandRank<TwoPairs> {
			private final Card highPair, lowPair;
			private TwoPairs(Card high, Card low) {
				super(2);
				this.highPair = high;
				this.lowPair =low;
			}

			public int compareTo(TwoPairs o) {
				int result = highPair.compareTo(o.highPair);
				if (result == 0)
					result = lowPair.compareTo(o.lowPair);
				return result;
			}

			static TwoPairs tryRank(PlayerHand playerHand) {
				List<Card> cards = playerHand.cards;
				Card high = null, low = null;
				for (int i = 0; i < cards.size()-1; i++) {
					if (cards.get(i).eq(cards.get(i+1))) {
						if (high == null) {
							high = cards.get(i);
							i++;
						} else {
							low = cards.get(i);
						}
					}
				}
				return high != null && low != null ? new TwoPairs(high, low) : null;
			}
		}

		static class ThreeOfAKind extends HandRank<ThreeOfAKind> {
			private final Card oneOfThree;
			private ThreeOfAKind(Card card) {
				super(3);
				this.oneOfThree = card;
			}

			public int compareTo(ThreeOfAKind o) {
				return oneOfThree.compareTo(o.oneOfThree);
			}

			static ThreeOfAKind tryRank(PlayerHand playerHand) {
				List<Card> cards = playerHand.cards;
				for (int i = 0; i < cards.size()-2; i++) {
					if (cards.get(i).eq(cards.get(i+1)) && cards.get(i).eq(cards.get(i+2)))
						return new ThreeOfAKind(cards.get(i));
				}
				return null;
			}
		}

		static class Straight extends HandRank<Straight> {
			private Straight() {
				super(4);
			}

			static Straight tryRank(PlayerHand playerHand) {
				List<Card> cards = playerHand.cards;
				boolean isStraight = true;
				for (int i = 0; i < cards.size()-1 && isStraight; i++) {
					isStraight = cards.get(i).isOneLevelHigher(cards.get(i+1));
				}
				return isStraight ? new Straight() : null;
			}
		}

		static class Flush extends HandRank<Flush> {
			private Flush() {
				super(5);
			}

			static Flush tryRank(PlayerHand playerHand) {
				final boolean isFlush = playerHand.cards.stream().map(Card::getSuit).distinct().count() == 1;
				return isFlush ? new Flush() : null;
			}
		}

		static class FullHouse extends HandRank<FullHouse> {
			private final Card triple, pair;
			private FullHouse(Card triple, Card pair) {
				super(6);
				this.triple = triple;
				this.pair =pair;
			}

			public int compareTo(FullHouse o) {
				int result = triple.compareTo(o.triple);
				if (result == 0)
					result = pair.compareTo(o.pair);
				return result;
			}

			static FullHouse tryRank(PlayerHand playerHand) {
				final List<Card> cards = playerHand.cards;
				final BiFunction<Integer,Integer,Boolean> eq = (i, j) -> cards.get(i).eq(cards.get(j));
				Card triple = null, pair = null;
				if (eq.apply(0,1) && eq.apply(3,4) && (eq.apply(2,0) || eq.apply(2,4))) {
					triple = cards.get(2);
					pair = triple.eq(cards.get(0)) ? cards.get(4) : cards.get(0);
				}
				return triple != null && pair != null ? new FullHouse(triple, pair) : null;
			}
		}

		static class FourOfAKind extends HandRank<FourOfAKind> {
			private final Card nominal;
			private FourOfAKind(Card card) {
				super(7);
				this.nominal = card;
			}

			public int compareTo(FourOfAKind o) {
				return nominal.compareTo(o.nominal);
			}

			static FourOfAKind tryRank(PlayerHand playerHand) {
				List<Card> cards = playerHand.cards;
				int i = cards.get(0).eq(cards.get(1)) ? 0 : 1;
				boolean fourOfAKing = true;
				for (int j = i; fourOfAKing && j < cards.size()-1+i; j++) {
					fourOfAKing = cards.get(j).eq(cards.get(j+1));
				}
				return fourOfAKing ? new FourOfAKind(cards.get(1)) : null;
			}
		}

		static class StraightFlush extends HandRank<StraightFlush> {
			private StraightFlush() {
				super(8);
			}

			static StraightFlush tryRank(PlayerHand playerHand) {
				return isStraightFlush(playerHand) ? new StraightFlush() : null;
			}

			static boolean isStraightFlush(PlayerHand playerHand) {
				List<Card> cards = playerHand.cards;
				boolean isStraightFlush = true;
				for (int i = 0; i < cards.size()-1 && isStraightFlush; i++) {
					isStraightFlush = cards.get(i).isOneLevelHigher(cards.get(i + 1));
					isStraightFlush &= cards.get(i).getSuit() == cards.get(i+1).getSuit();
				}
				return isStraightFlush;
			}
		}

		static class RoyalFlush extends HandRank<RoyalFlush> {
			private RoyalFlush() {
				super(9);
			}

			static RoyalFlush tryRank(PlayerHand playerHand) {
				boolean isRoyalFlush = playerHand.cards.get(0).getNominal().equals(Value.Ace);
				isRoyalFlush &= StraightFlush.isStraightFlush(playerHand);
				return isRoyalFlush ? new RoyalFlush() : null;
			}
		}

	}

}
