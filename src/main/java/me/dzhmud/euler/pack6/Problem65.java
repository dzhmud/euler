package me.dzhmud.euler.pack6;

import me.dzhmud.euler.EulerSolution;
import me.dzhmud.euler.util.BigIntegerFraction;
import me.dzhmud.euler.util.DigitsUtils;

import java.util.LinkedList;

/**
 * Convergents of e
 *
 * {@see https://projecteuler.net/problem=65}
 *
 * @author dzhmud
 */
public class Problem65 implements EulerSolution {

	public static void main(String[] args) {
		new Problem65().measureTime();
	}

	@Override
	public String getAnswer() {
		LinkedList<Integer> e = new LinkedList<>();
		e.add(2);
		for (int i = 2; i < 100+1; i++) {
			e.add(i%3==0 ? 2*i/3 : 1);
		}
		return "" + DigitsUtils.getDigitsSum(BigIntegerFraction.getValue(e).nominator);
	}






}
