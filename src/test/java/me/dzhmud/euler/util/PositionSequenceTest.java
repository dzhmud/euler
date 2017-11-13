package me.dzhmud.euler.util;

import org.junit.Test;

/**
 * small manual test of effectiveness of PositionSequence class.
 *
 * @author dzhmud
 */
public class PositionSequenceTest {

	@Test
	public void testProductivity() {
		System.out.println("Long.MAX_VALUE = " + Long.MAX_VALUE);
		System.out.println("Max_Int^2      = " + ((long)Integer.MAX_VALUE*Integer.MAX_VALUE));

		PositionSequence test = PositionSequence.get(n -> (long)n*n);
		long start = System.currentTimeMillis();
		System.out.println(test.contains(20000000000L));
		System.out.println("Solution take " + (System.currentTimeMillis() - start) / 1000 + " sec");
	}
}
