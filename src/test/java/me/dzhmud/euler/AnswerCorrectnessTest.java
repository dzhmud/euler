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
//		test(Problem26.class, "983");
//		test(Problem27.class, "-59231");
//		test(Problem28.class, "669171001");
//		test(Problem29.class, "9183");
//		test(Problem30.class, "443839");
//		test(Problem31.class, "73682");
//		test(Problem32.class, "45228");
//		test(Problem33.class, "100");
//		test(Problem34.class, "40730");
//		test(Problem35.class, "55");
//		test(Problem36.class, "872187");
//		test(Problem37.class, "748317");
//		test(Problem38.class, "932718654");
//		test(Problem39.class, "840");
//		test(Problem40.class, "210");
//		test(Problem41.class, "7652413");
//		test(Problem42.class, "162");
//		test(Problem43.class, "16695334890");
//		test(Problem44.class, "5482660");
//		test(Problem45.class, "1533776805");
//		test(Problem46.class, "5777");
//		test(Problem47.class, "134043");
//		test(Problem48.class, "9110846700");
//		test(Problem49.class, "296962999629");
		test(Problem50.class, "997651");


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

