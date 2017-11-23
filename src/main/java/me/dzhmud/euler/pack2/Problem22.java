package me.dzhmud.euler.pack2;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.FileUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *

 Using Problem22.txt, a 46K text file containing over five-thousand first names, begin by sorting it into alphabetical order.
 Then working out the alphabetical value for each name, multiply this value by its alphabetical position in the list to obtain a name score.

 For example, when the list is sorted into alphabetical order, COLIN, which is worth 3 + 15 + 12 + 9 + 14 = 53, is the 938th name in the list. So, COLIN would obtain a score of 938 × 53 = 49714.

 What is the total of all the name scores in the file?

 *
 * @author dzhmud
 */
public class Problem22 implements EulerSolution {

	public static void main(String[] args) {
		new Problem22().measureTime();
	}

	@Override
	public String getAnswer() {
		String names = FileUtils.getContents("Problem22.txt", Collectors.joining());
		List<String> namesList = new ArrayList<>(Arrays.asList(names.split("\"(,\")?")));
		namesList.remove(0);//nuance of splitting
		namesList.sort(Comparator.naturalOrder());
		long result = 0;
		for (int i = 0; i < namesList.size(); i++) {
			result += (i+1) * getNameScore(namesList.get(i));
		}
		return ""+result;
	}

	private static final int OFFSET = 'A' - 1;
	private static int[] CACHE = new int['Z'+1];
	static {
		for (int i = 'A'; i <= 'Z'; i++) {
			CACHE[i] = i - OFFSET;
		}
	}
	private static int getNameScore(String name) {
		int result = 0;
		char[] chars = name.toCharArray();
		for (char c : chars) {
			result += CACHE[c];
		}
		return result;
	}

}
