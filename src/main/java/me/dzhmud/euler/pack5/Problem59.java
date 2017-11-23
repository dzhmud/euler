package me.dzhmud.euler.pack5;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.FileUtils;

import java.util.Arrays;

/**
 * XOR decryption
 * {@see https://projecteuler.net/problem=59}
 *
 * @author dzhmud
 */
public class Problem59 implements EulerSolution {

	public static void main(String[] args) {
		EulerSolution.measureTime(new Problem59()::getAnswer);
	}

	@Override
	public String getAnswer() {
		final int[] encrypted = readFile();
		for (int a = 'a'; a <= 'z';a++) {
			for (int b = 'a'; b <= 'z';b++) {
				for (int c = 'a'; c <= 'z';c++) {
					String decrypted = decrypt(encrypted, new int[]{a,b,c});
					if (isDecrypted(decrypted)) {
//						System.out.println(decrypted);
						return "" + getASCIISum(decrypted);
					}
				}
			}
		}
		throw new SolutionNotFoundException();
	}

	private static int getASCIISum(String string) {
		int sum = 0;
		for (int i = 0; i < string.length(); i++) {
			sum += string.charAt(i);
		}
		return sum;
	}

	private static boolean isDecrypted(String string) {
		return string.contains(" the ") || string.contains("The ");
	}

	private static String decrypt(int[] encrypted, int[] code) {
		StringBuilder sb = new StringBuilder(encrypted.length);
		for (int i = 0; i < encrypted.length; i++) {
			sb.append((char)(encrypted[i] ^ code[i%code.length]));
		}
		return sb.toString();
	}

	private static int[] readFile() {
		final String rows = FileUtils.getContents("Problem59.txt");
		final String[] values = rows.split(",");
		return Arrays.stream(values).mapToInt(Integer::valueOf).toArray();
	}

}
