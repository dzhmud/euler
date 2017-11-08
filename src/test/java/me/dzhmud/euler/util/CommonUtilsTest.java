package me.dzhmud.euler.util;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for CommonUtils class.
 *
 * @author dzhmud
 */
public class CommonUtilsTest {

	@Test
	public void isPalindromTest() {
		assertTrue(CommonUtils.isPalindromic(12344321));
		assertTrue(CommonUtils.isPalindromic(1234321));
		assertTrue(CommonUtils.isPalindromic(11));
		assertTrue(CommonUtils.isPalindromic(1));
		assertTrue(CommonUtils.isPalindromic(0));
		assertTrue(CommonUtils.isPalindromic(-11));
		assertTrue(CommonUtils.isPalindromic(-1234321));

		assertFalse(CommonUtils.isPalindromic(11234321));
		assertFalse(CommonUtils.isPalindromic(112314321));


	}
}
