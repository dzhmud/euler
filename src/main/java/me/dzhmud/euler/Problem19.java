package me.dzhmud.euler;

import java.util.Calendar;

/**
 *

 You are given the following information, but you may prefer to do some research for yourself.

 1 Jan 1900 was a Monday.
 Thirty days has September,
 April, June and November.
 All the rest have thirty-one,
 Saving February alone,
 Which has twenty-eight, rain or shine.
 And on leap years, twenty-nine.
 A leap year occurs on any year evenly divisible by 4, but not on a century unless it is divisible by 400.

 How many Sundays fell on the first of the month during the twentieth century (1 Jan 1901 to 31 Dec 2000)?

 *
 * @author dzhmud
 */
public class Problem19 implements EulerSolution {

	public static void main(String[] args) {
		System.out.println(new Problem19().getAnswer());
	}

	@Override
	public String getAnswer() {
		int result = 0;
		Calendar c = Calendar.getInstance();
		c.set(1901, Calendar.JANUARY, 1);
		while(c.get(Calendar.YEAR) < 2001) {
			c.add(Calendar.MONTH, 1);
			if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				result += 1;
			}
		}
		return "" + result;
	}

}
