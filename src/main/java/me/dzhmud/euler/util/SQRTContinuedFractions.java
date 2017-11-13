package me.dzhmud.euler.util;

import java.util.Arrays;

/**
 * {@see https://projecteuler.net/problem=64} about all this stuff.
 *
 * @author dzhmud
 */
public final class SQRTContinuedFractions {
	private final int base;
	public final int roundPart;
	private int[] sequence = new int[0];

	public SQRTContinuedFractions(int value) {
		this.base = value;
		roundPart = (int)Math.sqrt(base);
		if (roundPart*roundPart != base) {
			fillSequence(-roundPart);
		}
	}

	public int[] getSequence() {
		return Arrays.copyOf(sequence, sequence.length);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("√").append(base).append('=').append("[").append(roundPart).append(";");
		if (sequence.length > 0) {
			sb.append("(");
			for (int i : sequence) {
				sb.append(i).append(",");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append(")");
		}
		sb.append("]");
		return sb.toString();
	}

	private void fillSequence(int currentB) {
		final Fraction.State1 start = Fraction.of(
				new Fraction.RationalPart(1),
				new Fraction.IrrationalPart(base,currentB,1)
		);
		Fraction mutated = start;
		for (;;) {
			mutated = mutated.mutate().mutate().mutate();//mutate state1 -> state4
			sequence = Arrays.copyOf(sequence, sequence.length+1);
			sequence[sequence.length -1] = Fraction.State4.class.cast(mutated).roundPart;
			mutated = mutated.mutate();//mutate state4 -> state1
			if (mutated.equals(start))
				break;
		}

	}

	/**
	 * {@see https://projecteuler.net/problem=64} about all this stuff.
	 * @param <N> type of nominator, either RationalPart or IrrationalPart
	 * @param <D> type of denominator, either RationalPart or IrrationalPart
	 */
	private abstract static class Fraction<N extends Fraction.FractionPart,D extends Fraction.FractionPart> {

		static State1 of(RationalPart nominator, IrrationalPart denominator) {
			return new State1(nominator, denominator);
		}

		final N nominator;
		final D denominator;

		Fraction(N nominator, D denominator){
			this.nominator = nominator;
			this.denominator = denominator;
		}

		protected abstract Fraction mutate();

		@Override
		public boolean equals(Object obj) {
			return obj instanceof Fraction && equals(Fraction.class.cast(obj));
		}

		public boolean equals(Fraction fraction) {
			if (getClass() != fraction.getClass()) {
				System.out.println("Comparing fractions in different states!");
				return false;
			}
			return nominator.equals(fraction.nominator) && denominator.equals(fraction.denominator);
		}

		@Override
		public String toString() {
			return String.valueOf(nominator) + '/' + denominator;
		}



		private static class State1 extends Fraction<RationalPart,IrrationalPart> {
			State1(RationalPart nominator, IrrationalPart denominator) {
				super(nominator, denominator);
			}

			protected State2 mutate() {
				IrrationalPart newNominator = new IrrationalPart(denominator.sqrt, -denominator.value, nominator.value);
				RationalPart newDenominator = new RationalPart(denominator.sqrt - denominator.value * denominator.value);
				return new State2(newNominator, newDenominator);
			}
		}

		private static class State2 extends Fraction<IrrationalPart,RationalPart> {
			State2(IrrationalPart nominator, RationalPart denominator) {
				super(nominator, denominator);
			}

			protected State3 mutate() {
				if (nominator.multiplicator != 1 && denominator.value % nominator.multiplicator != 0) {
					System.out.println("Failed to mutate State2 -> State3!");
					System.out.println("Current fraction value is: " + toString());
					System.exit(0);
				}
				assert nominator.multiplicator == 1 || denominator.value % nominator.multiplicator == 0;
				IrrationalPart newNominator = new IrrationalPart(nominator.sqrt, nominator.value, 1);
				RationalPart newDenominator = new RationalPart(denominator.value/ nominator.multiplicator);
				return new State3(newNominator, newDenominator);
			}
		}

		private static class State3 extends Fraction<IrrationalPart,RationalPart> {
			State3(IrrationalPart nominator, RationalPart denominator) {
				super(nominator, denominator);
			}

			protected State4 mutate() {
				final int sqrtValue = (int)Math.sqrt(nominator.sqrt);
				final int nominatorRoundValue = sqrtValue + nominator.value;
				final int roundPart = nominatorRoundValue / denominator.value;
				final int nominatorNewValue = nominator.value - roundPart * denominator.value;
				IrrationalPart newNominator = new IrrationalPart(nominator.sqrt, nominatorNewValue, 1);
				return new State4(roundPart, newNominator, denominator);
			}
		}

		private static class State4 extends Fraction<IrrationalPart,RationalPart> {
			private final int roundPart;

			State4(int roundPart, IrrationalPart nominator, RationalPart denominator) {
				super(nominator, denominator);
				this.roundPart = roundPart;
			}

			protected State1 mutate() {
				return new State1(denominator, nominator);
			}

			@Override
			public String toString() {
				StringBuilder sb = new StringBuilder();
				if (roundPart > 0) {
					sb.append(roundPart).append(" + ");
				}
				sb.append(super.toString());
				return sb.toString();
			}
		}


		//just marker interface to make implementing classes interchangeable.
		interface FractionPart {}

		private final static class RationalPart implements FractionPart {
			private final int value;

			RationalPart(int value) {
				this.value = value;
			}
			@Override
			public String toString() {
				return String.valueOf(value);
			}

			@Override
			public boolean equals(Object o) {
				return o instanceof RationalPart && value == ((RationalPart)o).value;
			}

			@Override
			public int hashCode() {
				return value;
			}
		}

		//represents part of (multiplicator*(sqrt(sqrt) + value))
		private final static class IrrationalPart implements FractionPart {
			private final int sqrt;
			private final int value;
			private final int multiplicator;

			IrrationalPart(int sqrt, int value, int multiplicator) {
				this.sqrt = sqrt;
				this.value = value;
				this.multiplicator = multiplicator;
			}

			@Override
			public String toString() {
				StringBuilder sb = new StringBuilder();
				if (multiplicator > 1) {
					sb.append(multiplicator);
				}
				sb.append('(').append('√').append(sqrt);
				if (value > 0)
					sb.append('+');
				sb.append(value).append(')');
				return sb.toString();
			}

			@Override
			public boolean equals(Object o) {
				return this == o || o instanceof IrrationalPart && this.equals(IrrationalPart.class.cast(o));
			}

			private boolean equals(IrrationalPart that) {
				return that != null && sqrt == that.sqrt && value == that.value && multiplicator == that.multiplicator;
			}

			@Override
			public int hashCode() {
				int result = sqrt;
				result = 31 * result + value;
				result = 31 * result + multiplicator;
				return result;
			}
		}

	}

}
