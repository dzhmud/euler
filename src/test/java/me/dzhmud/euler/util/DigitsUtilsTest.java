package me.dzhmud.euler.util;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test of DigitsUtils class
 *
 * @author dzhmud
 */
public class DigitsUtilsTest {

	@Test
	public void getDigitsCountTest() {
		assertEquals(1, DigitsUtils.getDigitsCount(0));
		assertEquals(1, DigitsUtils.getDigitsCount(2));
		assertEquals(2, DigitsUtils.getDigitsCount(22));
		assertEquals(2, DigitsUtils.getDigitsCount(99));
		assertEquals(3, DigitsUtils.getDigitsCount(100));
		assertEquals(3, DigitsUtils.getDigitsCount(101));
		assertEquals(10, DigitsUtils.getDigitsCount(1234567890));
	}

	@Test
	public void getDigitsTest() {
		assertArrayEquals(new int[]{0}, DigitsUtils.getDigits(0));
		assertArrayEquals(new int[]{1}, DigitsUtils.getDigits(1));
		assertArrayEquals(new int[]{1,2}, DigitsUtils.getDigits(12));
		assertArrayEquals(new int[]{1,0,0,0}, DigitsUtils.getDigits(1000));
		assertArrayEquals(new int[]{1,2,3,4,5,6,7,8,9,0}, DigitsUtils.getDigits(1234567890));
	}

	@Test
	public void getDigits_v2_Test() {
		assertArrayEquals(new int[]{0}, DigitsUtils.getDigits_v2(0));
		assertArrayEquals(new int[]{1}, DigitsUtils.getDigits_v2(1));
		assertArrayEquals(new int[]{1,2}, DigitsUtils.getDigits_v2(12));
		assertArrayEquals(new int[]{1,0,0,0}, DigitsUtils.getDigits_v2(1000));
		assertArrayEquals(new int[]{1,2,3,4,5,6,7,8,9,0}, DigitsUtils.getDigits_v2(1234567890));
	}

	@Test
	@Ignore
	public void performanceComparisonTest() {
		final List<Long> values = new ArrayList<>();
		LongStream.range(1000*1000, 10*1000*1000).forEach(values::add);

		long start = System.currentTimeMillis();
		values.forEach(aLong -> {
			if (DigitsUtils.getDigits(aLong).length != 7)
				fail("FAILED PARSING!");
		});
		System.out.println("#getDigits take " + (System.currentTimeMillis() - start)  + " millis");

		start = System.currentTimeMillis();
		values.forEach(aLong -> {
			if (DigitsUtils.getDigits_v2(aLong).length != 7)
				fail("FAILED PARSING!");
		});
		System.out.println("#getDigits_v2 take " + (System.currentTimeMillis() - start)  + " millis");

	}
}
