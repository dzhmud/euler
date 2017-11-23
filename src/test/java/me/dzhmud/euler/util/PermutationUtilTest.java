package me.dzhmud.euler.util;

import org.junit.Assert;
import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.function.Supplier;

import static me.dzhmud.euler.util.PermutationUtilTest.Simple.A;
import static me.dzhmud.euler.util.PermutationUtilTest.Simple.B;
import static me.dzhmud.euler.util.PermutationUtilTest.Simple.C;
import static me.dzhmud.euler.util.PermutationUtilTest.Simple.D;

/**
 * Simple test for {@link PermutationsUtil} class.
 *
 * @author dzhmud
 */
public class PermutationUtilTest {

	enum Simple {
		A,B,C,D
	}

	@Test public void testUnorderedPermutations() {
		shouldFail( ()->PermutationsUtil.getUnOrderedCombinations(new Simple[]{A,B,C,D}, 0), NotImplementedException.class);
		shouldFail( ()->PermutationsUtil.getUnOrderedCombinations(new Simple[]{A,B,C,D}, 5), NotImplementedException.class);

		//TODO test of UNORDERED combinations rely on order, refactor!
		assertEquals(new Simple[][]{{A}, {B}, {C}, {D}},
				PermutationsUtil.getUnOrderedCombinations(new Simple[]{A,B,C,D}, 1));

		assertEquals(new Simple[][]{{A,B}, {A,C}, {A,D}, {B,C}, {B,D}, {C,D}},
				PermutationsUtil.getUnOrderedCombinations(new Simple[]{A,B,C,D}, 2));

		assertEquals(new Simple[][]{{A,B,C}, {A,B,D}, {A,C,D}, {B,C,D}},
				PermutationsUtil.getUnOrderedCombinations(new Simple[]{A,B,C,D}, 3));

		assertEquals(new Simple[][]{{A,B,C,D}},
				PermutationsUtil.getUnOrderedCombinations(new Simple[]{A,B,C,D}, 4));
	}

	@Test public void testOrderedPermutations() {
		shouldFail(() -> PermutationsUtil.getOrderedCombinations(new Simple[]{A, B, C, D}, 0), NotImplementedException.class);
		shouldFail(() -> PermutationsUtil.getOrderedCombinations(new Simple[]{A, B, C, D}, 5), NotImplementedException.class);

		assertEquals(PermutationsUtil.getOrderedCombinations(new Simple[]{A, B, C, D}, 1),
				new Simple[][]{{A}, {B}, {C}, {D}});

		assertEquals(PermutationsUtil.getOrderedCombinations(new Simple[]{A, B, C, D}, 2),
				new Simple[][]{
					{A, B}, {A, C}, {A, D},
					{B, A}, {B, C}, {B, D},
					{C, A}, {C, B}, {C, D},
					{D, A}, {D, B}, {D, C}
				}
		);
		
		assertEquals(PermutationsUtil.getOrderedCombinations(new Simple[]{A, B, C, D}, 3),
				new Simple[][]{
					{A, B, C}, {A, B, D}, {A, C, B}, {A, C, D}, {A, D, B}, {A, D, C},
					{B, A, C}, {B, A, D}, {B, C, A}, {B, C, D}, {B, D, A}, {B, D, C},
					{C, A, B}, {C, A, D}, {C, B, A}, {C, B, D}, {C, D, A}, {C, D, B},
					{D, A, B}, {D, A, C}, {D, B, A}, {D, B, C}, {D, C, A}, {D, C, B}
				}
		);
		
		assertEquals(PermutationsUtil.getOrderedCombinations(new Simple[]{A, B, C, D}, 4),
				new Simple[][]{
					{A, B, C, D}, {A, B, D, C}, {A, C, B, D}, {A, C, D, B}, {A, D, B, C}, {A, D, C, B},
					{B, A, C, D}, {B, A, D, C}, {B, C, A, D}, {B, C, D, A}, {B, D, A, C}, {B, D, C, A},
					{C, A, B, D}, {C, A, D, B}, {C, B, A, D}, {C, B, D, A}, {C, D, A, B}, {C, D, B, A},
					{D, A, B, C}, {D, A, C, B}, {D, B, A, C}, {D, B, C, A}, {D, C, A, B}, {D, C, B, A}
				}
		);
	}

	private static void shouldFail(Supplier s, Class<? extends Throwable> exceptionClass) {
		try {
			s.get();
			Assert.fail("Expected exception!");
		} catch (Throwable e) {
			Assert.assertTrue(exceptionClass.isInstance(e));
		}
	}

	private static <T> void assertEquals(List<List<T>> actual, T[][] expected) {
		assertEquals(expected, actual);
	}
	
	private static <T> void assertEquals(T[][] expected, List<List<T>> actual) {
		Assert.assertEquals(expected.length, actual.size());
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], actual.get(i).toArray());
		}
	}

}
