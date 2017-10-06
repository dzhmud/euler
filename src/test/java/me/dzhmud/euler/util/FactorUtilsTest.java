package me.dzhmud.euler.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.LongUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static me.dzhmud.euler.util.FactorUtils.getDivisors;
import static me.dzhmud.euler.util.FactorUtils.getFactors_v1;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Test for FactorUtils class
 *
 * @author dzhmud
 */
public class FactorUtilsTest {

	@Test
	public void getFactors_v1_test() {
		//1 is added only for prime numbers.
		assertListEquals(Arrays.asList(1L, 2L), getFactors_v1(1024, true));
		assertListEquals(
				LongStream.iterate(2L, LongUnaryOperator.identity()).limit(10).boxed().collect(Collectors.toList()),
				getFactors_v1(1024, false)
		);
		assertListEquals(Arrays.asList(2L, 3L, 13L, 13L, 23L), getFactors_v1(2*3*13*13*23, false));
		assertListEquals(Arrays.asList(2L, 3L, 13L, 23L), getFactors_v1(2*3*13*13*23, true));

		assertListEquals(Arrays.asList(1L, 19L), getFactors_v1(19, true));
		assertListEquals(Arrays.asList(1L, 19L), getFactors_v1(19, false));
	}

	@Test
	public void getDivisorsTest() {
		assertArrayEquals(new Long[]{1L}, getDivisors(1).toArray());
		assertArrayEquals(new Long[]{1L,3L,9L}, getDivisors(9, true).toArray());
		assertArrayEquals(new Long[]{1L,3L,5L,15L}, getDivisors(15, true).toArray());
		assertArrayEquals(new Long[]{1L,2L,4L,7L,14L,28L}, getDivisors(28, true).toArray());
	}

	private static void assertListEquals (List<Long> list1, List<Long> list2) {
		Collections.sort(list1);
		Collections.sort(list2);
		assertEquals(list1, list2);
	}


}
