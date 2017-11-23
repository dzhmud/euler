package me.dzhmud.euler.pack1;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.FileUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Work out the first ten digits of the sum of the following one-hundred 50-digit numbers.
 * #{include Problem13.txt}
 *
 * @author dzhmud
 */
public class Problem13 implements EulerSolution {

	public static void main(String[] args) {
		EulerSolution.measureTime(new Problem13()::getAnswer);
	}

	@Override
	public String getAnswer() {
		List<String> rows = FileUtils.getContents("Problem13.txt", Collectors.toList());
		LongNumber sum = rows.stream()
				.reduce(
						new LongNumber("0"),
						(longNumber, s) -> longNumber.add(new LongNumber(s)),
						LongNumber::add
				);
		return sum.toString().substring(0,10);
	}

	public static final class LongNumber {
		private static final int ZERO_OFFSET = '0';
		private static final byte[] DIGITS_CACHE = new byte[]{0,1,2,3,4,5,6,7,8,9};

		//digits in reverse order, so digits[0] is the lowest.
		private final byte[] digits;

		LongNumber(String value) {
			final char[] chars = reverse(value).toCharArray();
			digits = new byte[chars.length];
			for (int i = 0; i < chars.length; i++) {
				digits[i] = DIGITS_CACHE[chars[i] - ZERO_OFFSET];
			}
		}

		private LongNumber(byte[] value) {
			digits = value.clone();
		}

		LongNumber add(LongNumber another) {
			Objects.requireNonNull(another);
			byte[] result = new byte[1+Math.max(digits.length, another.digits.length)];
			byte sum;
			for (int i = 0; i < result.length -1; i++) {
				sum = result[i];
				if (i < digits.length)
					sum += digits[i];
				if (i < another.digits.length)
					sum+=another.digits[i];
				result[i] = (byte)(sum % 10);
				if (sum > 9) {
					result[i + 1] = 1;//adding two numbers digit-by-digit we have max sum of 19.
				}
			}
			if (result[result.length - 1] == 0) {
				result = Arrays.copyOf(result, result.length - 1);
			}
			return new LongNumber(result);
		}

		@Override
		public String toString() {
			char[] chars = new char[digits.length];
			for (int i = 0; i < digits.length; i++) {
				chars[i] = (char)(digits[i] + ZERO_OFFSET);
			}
			return reverse(new String(chars));
		}

		private static String reverse(String string) {
			return new StringBuilder(string).reverse().toString();
		}

	}


}
