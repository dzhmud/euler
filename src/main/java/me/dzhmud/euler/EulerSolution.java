package me.dzhmud.euler;

import java.util.function.Supplier;

/**
 * Common interface for solutions.
 *
 * @author dzhmud
 */
public interface EulerSolution {

	java.lang.String getAnswer();

	class SolutionNotFoundException extends RuntimeException {
		public SolutionNotFoundException() {
		}

		public SolutionNotFoundException(String message) {
			super(message);
		}
	}

	Supplier<SolutionNotFoundException> SNFE = SolutionNotFoundException::new;

	default void measureTime() {
		long start = System.currentTimeMillis();
		System.out.println(getAnswer());
		System.out.println("Solution take " + (System.currentTimeMillis() - start) / 1000 + " sec");
	}
}
