package me.dzhmud.euler.pack3;

import me.dzhmud.euler.EulerSolution;

import java.util.Comparator;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Integer right triangles
 *
 If p is the perimeter of a right angle triangle with integral length sides, {a,b,c}, there are exactly three solutions for p = 120.

 {20,48,52}, {24,45,51}, {30,40,50}

 For which value of p ≤ 1000, is the number of solutions maximised?

 *
 * @author dzhmud
 */
public class Problem39 implements EulerSolution {

	public static void main(String[] args) {
		EulerSolution.measureTime(new Problem39()::getAnswer);
	}

	@Override
	public String getAnswer() {
		//basic research shows that in right angle triangles hypotenuse can be from (sqrt(2)/(2+sqrt(2)) to ~1/2 of perimeter)
		return "" + IntStream.range(3, 500).boxed()
				.flatMap(Problem39::generateTriangles)
				.distinct()
				.collect(Collectors.groupingBy(RightTriangle::getPerimeter))
				.entrySet().stream()
				.max(Comparator.comparing(entry -> entry.getValue().size()))
				.orElseThrow(SolutionNotFoundException::new)
				.getKey();
	}

	private static Stream<RightTriangle> generateTriangles(int hypotenuse) {
		return IntStream.range(1, hypotenuse)
				.filter(x -> x + hypotenuse < 1000)
				.filter(x -> squaresMap.containsKey(squares[hypotenuse] - squares[x]))
				.mapToObj(x -> new RightTriangle(x, squaresMap.get(squares[hypotenuse] - squares[x]), hypotenuse))
				.filter(triangle -> triangle.getPerimeter() <= 1000);
	}

	private static int[] squares = new int[501];
	private static TreeMap<Integer, Integer> squaresMap = new TreeMap<>();
	static {
		IntStream.rangeClosed(1,500).forEach(x -> {squares[x] = x*x; squaresMap.put(x*x, x);});
	}

	private static final class RightTriangle {
		private final int a, b, hyp;

		RightTriangle(int a, int b, int hyp) {
			assert squares[hyp] == squares[a] + squares[b];
			this.a = a;
			this.b = b;
			this.hyp = hyp;
		}

		int getPerimeter() {
			return a+b+hyp;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			return o != null && o instanceof RightTriangle && equals((RightTriangle) o);
		}

		boolean equals(RightTriangle that) {
			return hyp == that.hyp && (a == that.a || a == that.b);
		}

		@Override
		public int hashCode() {
			return 31*hyp + a*b;
		}

		@Override
		public String toString() {
			return String.format("{%s,%s,%s}", a,b,hyp);
		}
	}

}
