package me.dzhmud.euler.pack2;

import me.dzhmud.euler.EulerSolution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import static me.dzhmud.euler.pack2.Problem26.Division.getRecurringLength;

/**
 *
 A unit fraction contains 1 in the numerator. The decimal representation of the unit fractions with denominators 2 to 10 are given:

 1/2	= 	0.5
 1/3	= 	0.(3)
 1/4	= 	0.25
 1/5	= 	0.2
 1/6	= 	0.1(6)
 1/7	= 	0.(142857)
 1/8	= 	0.125
 1/9	= 	0.(1)
 1/10	= 	0.1

 Where 0.1(6) means 0.166666..., and has a 1-digit recurring cycle. It can be seen that 1/7 has a 6-digit recurring cycle.

 Find the value of d < 1000 for which 1/d contains the longest recurring cycle in its decimal fraction part.

 *
 * @author dzhmud
 */
public class Problem26 implements EulerSolution {

	public static void main(String[] args) {
		new Problem26().measureTime();
	}

	@Override
	public String getAnswer() {
		final Function<Integer, Integer> getRecurringLength = i -> getRecurringLength(1, i);
		return "" + IntStream.range(1, 1000).boxed()
				.sorted(Comparator.comparing(getRecurringLength).reversed())
				.findFirst().orElse(-1);
	}

	static class Division {
		private final boolean negativeResult;
		private final int value;
		private final int divisor;
		private int currentValue;
		private StringBuilder result = new StringBuilder("");
		private List<Integer> reminders = new ArrayList<>();
		private int recurringLength = 0;

		Division(int value, int divisor) {
			if (divisor == 0) {
				throw new ArithmeticException("Division by zero is forbidden in simple math");
			}
			this.negativeResult = (value > 0) ^ (divisor > 0);
			this.value = Math.abs(value);
			this.divisor = Math.abs(divisor);

		}

		static int getRecurringLength(int a, int b) {
			Division d = new Division(a, b);
			d.divide();
			return d.recurringLength;
		}

		private void prepare() {
			if (negativeResult) {
				result.append("-");
			}
			final int roundPart = value / divisor;
			currentValue = value - roundPart * divisor;
			result.append(roundPart).append('.');
			reminders.add(currentValue);
			currentValue *= 10;
		}

		String divide() {
			prepare();
			boolean divisionEnded = false;
			while (!divisionEnded) {
				final int currentDigit = currentValue / divisor;
				result.append(currentDigit);
				final int reminder = currentValue - currentDigit * divisor;

				//division ended without recurring.
				if (reminder == 0) {
					divisionEnded = true;
					continue;
				}
				//division with recurring. First recurring cycle just ended.
				if (reminders.contains(reminder)) {
					divisionEnded = true;
					result.append(")");
					int digitsBeforeRecurringStart = reminders.indexOf(reminder);
					recurringLength = reminders.size() - digitsBeforeRecurringStart;
					result.insert(result.indexOf(".") + 1 + digitsBeforeRecurringStart, "(");
//					System.out.println(result);
					continue;
				}
				//next division step
				reminders.add(reminder);
				currentValue = reminder * 10;
			}
			return result.toString();
		}

	}

}
