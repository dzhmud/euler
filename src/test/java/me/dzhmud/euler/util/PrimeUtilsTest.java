package me.dzhmud.euler.util;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Collection;
import java.util.stream.LongStream;

/**
 * TODO comment this class
 *
 * @author dzhmud
 */
public class PrimeUtilsTest {

	@Test
	public void testPrimeUtilsCacheEffectiveness() {
		long start = System.currentTimeMillis();
		PrimeUtils.USE_CACHE = false;
		System.out.println(runTest());
		System.out.println("W/OUT CACHE test took " + (System.currentTimeMillis() - start)  + " millis");

		PrimeUtils.USE_CACHE = true;
		System.out.println(runTest());
		start = System.currentTimeMillis();
		System.out.println(runTest());
		System.out.println("WITH  CACHE test took " + (System.currentTimeMillis() - start)  + " millis");

		start = System.currentTimeMillis();
		System.out.println(runTest2());
		System.out.println("WITH HARD_CACHE test took " + (System.currentTimeMillis() - start)  + " millis");

		start = System.currentTimeMillis();
		System.out.println(runTest2());
		System.out.println("WITH HARD_CACHE and WARMUP test took " + (System.currentTimeMillis() - start)  + " millis");
	}

	private static long runTest() {
		return LongStream.range(2, 15*1000*1000).filter(PrimeUtils::isPrime_v1).count();
	}

	private static long runTest2() {
		return LongStream.range(2, 15*1000*1000).filter(PrimeUtils::isPrime_v2).count();
	}

	@Test
	public void testPrimesInRange() {
		test(new int[]{2,3,5,7}, PrimeUtils.getPrimesInRange(2,11));
		test(new int[]{2,3,5,7,11}, PrimeUtils.getPrimesInRange(2,12));
		test(new int[]{2}, PrimeUtils.getPrimesInRange(2,3));
		test(new int[]{}, PrimeUtils.getPrimesInRange(4,5));
		test(new int[]{5}, PrimeUtils.getPrimesInRange(4,6));
	}

	private static void test(int[] expected, Collection<Long> actual) {
		Assert.assertArrayEquals(expected, actual.stream().mapToInt(Long::intValue).toArray());
	}

	@Test
	@Ignore
	public void testPrimeUtils_v2() {
		System.out.println(PrimeUtils.isPrime_v2(129));
		System.out.println(PrimeUtils.isPrime_v2(71));
	}
}
