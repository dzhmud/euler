package me.dzhmud.euler.pack4;

import me.dzhmud.euler.EulerSolution;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;

/**
 * Champernowne's constant
 *

 An irrational decimal fraction is created by concatenating the positive integers:

 0.123456789101112131415161718192021...

 It can be seen that the 12th digit of the fractional part is 1.

 If dn represents the nth digit of the fractional part, find the value of the following expression.

 d1 × d10 × d100 × d1000 × d10000 × d100000 × d1000000

 *
 * @author dzhmud
 */
public class Problem40 implements EulerSolution {

	public static void main(String[] args) {
		new Problem40().measureTime();
	}

	@Override
	public String getAnswer() {
		final AtomicInteger index = new AtomicInteger(0);
		final int result = IntStream.generate(SUPPLIER)
				.limit(1000000)
				.peek(i-> index.incrementAndGet())
				.filter(i -> searchedIndex.test(index.intValue()))
//				.peek(i -> System.out.println("d("+index.intValue()+") = " + i))
				.reduce((left, right) -> left * right)
				.orElseThrow(SolutionNotFoundException::new);

		fillerThread.interrupt();
		return "" + result;
	}

	private static IntPredicate searchedIndex = value ->
			value == 1 ||
			value == 10 ||
			value == 100 ||
			value == 1000 ||
			value == 10000 ||
			value == 100000 ||
			value == 1000000;

	private static Thread fillerThread = null;

	private static final IntSupplier SUPPLIER = new IntSupplier() {

		private final ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1000);

		private final Thread filler = new Thread("filler") {
			private int currentInt = 1;
			@Override
			public void run() {
				try {
					while (!isInterrupted()) {
						int value = currentInt++;
						int[] digits = new int[0];
						while (value > 0) {
							digits = Arrays.copyOf(digits, 1 + digits.length);
							digits[digits.length - 1] = value % 10;
							value /= 10;
						}
						for (int i = digits.length - 1; i >= 0 ; i--) {
							queue.put(digits[i]);
						}
					}
				} catch (InterruptedException e) {
					//OK, it's meant to be interrupted when not needed anymore
				}
			}
		};

		{
			filler.start();
			Problem40.fillerThread = filler;
		}

		@Override
		public int getAsInt() {
			try {
				return queue.take();
			} catch (InterruptedException e) {
				//not OK
				throw new RuntimeException(e);
			}
		}
	};

}
