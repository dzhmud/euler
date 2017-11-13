package me.dzhmud.euler;

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
}
