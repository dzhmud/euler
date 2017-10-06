package me.dzhmud.euler;

/**
 *

 A Pythagorean triplet is a set of three natural numbers, a < b < c, for which,
 a^2 + b^2 = c^2

 For example, 3^2 + 4^2 = 9 + 16 = 25 = 5^2.

 There exists exactly one Pythagorean triplet for which a + b + c = 1000.
 Find the product abc.

 *
 * @author dzhmud
 */
public class Problem09 implements EulerSolution {

	public static void main(String[] args) {
		System.out.println(new Problem09().getAnswer());
	}

	@Override
	public String getAnswer() {
		for (int a = 1; a < 500; a++) {
			int asq = a * a;
			for (int b = 1; b < 500; b++) {
				int c = 1000 - a - b;
				if (c * c == asq + b*b) {
					return "" + a*b*c;
				}
			}
		}
		return "not found";
	}

}
