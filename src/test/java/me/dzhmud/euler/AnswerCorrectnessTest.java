package me.dzhmud.euler;

import me.dzhmud.euler.pack0.Problem01;
import me.dzhmud.euler.pack0.Problem02;
import me.dzhmud.euler.pack0.Problem03;
import me.dzhmud.euler.pack0.Problem04;
import me.dzhmud.euler.pack0.Problem05;
import me.dzhmud.euler.pack0.Problem06;
import me.dzhmud.euler.pack0.Problem07;
import me.dzhmud.euler.pack0.Problem08;
import me.dzhmud.euler.pack0.Problem09;
import me.dzhmud.euler.pack1.Problem10;
import me.dzhmud.euler.pack1.Problem11;
import me.dzhmud.euler.pack1.Problem12;
import me.dzhmud.euler.pack1.Problem13;
import me.dzhmud.euler.pack1.Problem14;
import me.dzhmud.euler.pack1.Problem15;
import me.dzhmud.euler.pack1.Problem16;
import me.dzhmud.euler.pack1.Problem17;
import me.dzhmud.euler.pack1.Problem18;
import me.dzhmud.euler.pack1.Problem19;
import me.dzhmud.euler.pack2.Problem20;
import me.dzhmud.euler.pack2.Problem21;
import me.dzhmud.euler.pack2.Problem22;
import me.dzhmud.euler.pack2.Problem23;
import me.dzhmud.euler.pack2.Problem24;
import me.dzhmud.euler.pack2.Problem25;
import me.dzhmud.euler.pack2.Problem26;
import me.dzhmud.euler.pack2.Problem27;
import me.dzhmud.euler.pack2.Problem28;
import me.dzhmud.euler.pack2.Problem29;
import me.dzhmud.euler.pack3.Problem30;
import me.dzhmud.euler.pack3.Problem31;
import me.dzhmud.euler.pack3.Problem32;
import me.dzhmud.euler.pack3.Problem33;
import me.dzhmud.euler.pack3.Problem34;
import me.dzhmud.euler.pack3.Problem35;
import me.dzhmud.euler.pack3.Problem36;
import me.dzhmud.euler.pack3.Problem37;
import me.dzhmud.euler.pack3.Problem38;
import me.dzhmud.euler.pack3.Problem39;
import me.dzhmud.euler.pack4.Problem40;
import me.dzhmud.euler.pack4.Problem41;
import me.dzhmud.euler.pack4.Problem42;
import me.dzhmud.euler.pack4.Problem43;
import me.dzhmud.euler.pack4.Problem44;
import me.dzhmud.euler.pack4.Problem45;
import me.dzhmud.euler.pack4.Problem46;
import me.dzhmud.euler.pack4.Problem47;
import me.dzhmud.euler.pack4.Problem48;
import me.dzhmud.euler.pack4.Problem49;
import me.dzhmud.euler.pack5.Problem52;
import me.dzhmud.euler.pack5.Problem53;
import me.dzhmud.euler.pack5.Problem54;
import me.dzhmud.euler.pack5.Problem55;
import me.dzhmud.euler.pack5.Problem56;
import me.dzhmud.euler.pack5.Problem57;
import me.dzhmud.euler.pack5.Problem58;
import me.dzhmud.euler.pack5.Problem59;
import me.dzhmud.euler.pack6.Problem60;
import me.dzhmud.euler.pack6.Problem61;
import me.dzhmud.euler.pack6.Problem62;
import me.dzhmud.euler.pack6.Problem63;
import me.dzhmud.euler.pack6.Problem64;
import me.dzhmud.euler.pack6.Problem65;
import me.dzhmud.euler.pack6.Problem67;
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
@SuppressWarnings("unusedImports")
public class AnswerCorrectnessTest {

	@Test
	public void testAnswers() {
		test(Problem01.class, "233168");
		test(Problem02.class, "4613732");
		test(Problem03.class, "6857");
		test(Problem04.class, "906609");
		test(Problem05.class, "232792560");
		test(Problem06.class, "25164150");
		test(Problem07.class, "104743");
		test(Problem08.class, "23514624000");
		test(Problem09.class, "31875000");
		test(Problem10.class, "142913828922");
		test(Problem11.class, "70600674");
		test(Problem12.class, "76576500");
		test(Problem13.class, "5537376230");
		test(Problem14.class, "837799");
		test(Problem15.class, "137846528820");
		test(Problem16.class, "1366");
		test(Problem17.class, "21124");
		test(Problem18.class, "1074");
		test(Problem19.class, "171");
		test(Problem20.class, "648");
		test(Problem21.class, "31626");
		test(Problem22.class, "871198282");
		test(Problem23.class, "4179871");
		test(Problem24.class, "2783915460");
		test(Problem25.class, "4782");
		test(Problem26.class, "983");
		test(Problem27.class, "-59231");
		test(Problem28.class, "669171001");
		test(Problem29.class, "9183");
		test(Problem30.class, "443839");
		test(Problem31.class, "73682");
		test(Problem32.class, "45228");
		test(Problem33.class, "100");
		test(Problem34.class, "40730");
		test(Problem35.class, "55");
		test(Problem36.class, "872187");
		test(Problem37.class, "748317");
		test(Problem38.class, "932718654");
		test(Problem39.class, "840");
		test(Problem40.class, "210");
		test(Problem41.class, "7652413");
		test(Problem42.class, "162");
		test(Problem43.class, "16695334890");
		test(Problem44.class, "5482660");
		test(Problem45.class, "1533776805");
		test(Problem46.class, "5777");
		test(Problem47.class, "134043");
		test(Problem48.class, "9110846700");
		test(Problem49.class, "296962999629");
//		test(Problem50.class, "997651");//TODO this one is SLOOOOW
//		test(Problem51.class, "121313");//TODO this one is slow
		test(Problem52.class, "142857");
		test(Problem53.class, "4075");
		test(Problem54.class, "376");
		test(Problem55.class, "249");
		test(Problem56.class, "972");
		test(Problem57.class, "153");
		test(Problem58.class, "26241");
		test(Problem59.class, "107359");
		test(Problem60.class, "26033");
		test(Problem61.class, "28684");
		test(Problem62.class, "127035954683");
		test(Problem63.class, "49");
		test(Problem64.class, "1322");
		test(Problem65.class, "272");


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

