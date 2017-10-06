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
		assertTrue(CommonUtils.isPalindrom(12344321));
		assertTrue(CommonUtils.isPalindrom(1234321));
		assertTrue(CommonUtils.isPalindrom(11));
		assertTrue(CommonUtils.isPalindrom(1));
		assertTrue(CommonUtils.isPalindrom(0));
		assertTrue(CommonUtils.isPalindrom(-11));
		assertTrue(CommonUtils.isPalindrom(-1234321));

		assertFalse(CommonUtils.isPalindrom(11234321));
		assertFalse(CommonUtils.isPalindrom(112314321));


	}
}
