package me.dzhmud.euler;

/**
 * Maximum path sum II
 * @see <a href="https://projecteuler.net/problem=67">description</a>
 *
 * @author dzhmud
 */
public class Problem67 implements EulerSolution {

	public static void main(String[] args) {
		System.out.println(new Problem67().getAnswer());
	}

	@Override
	public String getAnswer() {
		return new Problem18().getAnswer("Problem67.txt");
	}

}
