package me.dzhmud.euler.pack0;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.CommonUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *

 A palindromic number reads the same both ways. The largest palindrome made from the product of two 2-digit numbers is 9009 = 91 × 99.

 Find the largest palindrome made from the product of two 3-digit numbers.

 *
 * @author dzhmud
 */
public class Problem04 implements EulerSolution {

	public static void main(String[] args) {
		new Problem04().measureTime();
	}

	@Override
	public String getAnswer() {
		List<Integer> palindroms = new ArrayList<>();
		int temp;
		for (int f1 = 900; f1 < 1000; f1++) {
			for (int f2 = 900; f2 < 1000; f2++) {
				temp = f1*f2;
				if (CommonUtils.isPalindromic(temp))
					palindroms.add(temp);
			}
		}
		palindroms.sort(Comparator.reverseOrder());
		return palindroms.get(0) + "";
	}



}
