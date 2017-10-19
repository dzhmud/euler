package me.dzhmud.euler;

import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


/**
 * Test that checks all answers found so far.
 * Some routines are reused, so check that nothing is broken.
 *
 * @author dzhmud
 */
public class AnswerCorrectnessTest {

	@Test
	public void testAnswers() {
//		test(Problem01.class, "233168");
//		test(Problem02.class, "4613732");
//		test(Problem03.class, "6857");
//		test(Problem04.class, "906609");
//		test(Problem05.class, "232792560");
//		test(Problem06.class, "25164150");
//		test(Problem07.class, "104743");
//		test(Problem08.class, "23514624000");
//		test(Problem09.class, "31875000");
//		test(Problem10.class, "142913828922");
//		test(Problem11.class, "70600674");
//		test(Problem12.class, "76576500");
//		test(Problem13.class, "5537376230");
//		test(Problem14.class, "837799");
//		test(Problem15.class, "137846528820");
//		test(Problem16.class, "1366");
//		test(Problem17.class, "21124");
//		test(Problem18.class, "1074");
//		test(Problem19.class, "171");
//		test(Problem20.class, "648");
//		test(Problem21.class, "31626");
//		test(Problem22.class, "871198282");
//		test(Problem23.class, "4179871");
//		test(Problem24.class, "2783915460");
//		test(Problem25.class, "4782");
		test(Problem26.class, "983");


		test(Problem67.class, "7273");
	}

	private static void test(Class<? extends EulerSolution> clazz, String answer) {
		try {
			final long start = System.currentTimeMillis();
			assertEquals(answer, clazz.newInstance().getAnswer());
			final long duration = System.currentTimeMillis() - start;
			if (duration > 10)
				System.out.println(clazz.getSimpleName() + "\t:\t"+duration+"ms");
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@SuppressWarnings("unused")
	private static void printMemoryUsage() {
		MemoryMXBean memory = ManagementFactory.getMemoryMXBean();
		System.out.println(memory.getHeapMemoryUsage());
		System.out.println(memory.getNonHeapMemoryUsage());
//		for (MemoryPoolMXBean memoryMXBean : ManagementFactory.getMemoryPoolMXBeans()) {
//			System.out.println(memoryMXBean.getName());
//			if ("Metaspace".equals(memoryMXBean.getName()))
//			{
//				System.out.println(memoryMXBean.getUsage().getUsed()/1024/1024 + " mb");
//			}
//
//		}
	}
}

