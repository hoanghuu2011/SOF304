package poly.Lab2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.annotation.processing.Generated;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

@Generated(value = "org.junit-tools-1.1.0")
public class MathFuncTest {

	private MathFunc math;

	@Before
	public void init() {
		math = new MathFunc();
	}

	@After
	public void tearDown() {
		math = null;
	}

	@Test
	public void calls() {
		assertEquals(0, math.getCalls());
		math.factorial(1);
		assertEquals(1, math.getCalls());
		math.factorial(1);
		assertEquals(2, math.getCalls());
	}

	@Test
	public void factorial() {
		assertTrue(math.factorial(0) == 1);
		assertTrue(math.factorial(1) == 1);
		assertTrue(math.factorial(5) == 120);
	}

	@Test(expected = IllegalArgumentException.class)
	public void factorialNegative() {
		math.factorial(-1);
	}

	@Ignore
	@Test
	public void todo() {
		assertTrue(math.plus(1, 1) == 3);
	}

	public static void main(String[] args) throws Exception {
		JUnitCore runner = new JUnitCore();
		Result result = runner.run(MathFuncTest.class);
		System.out.println("run tests: " + result.getRunCount());
		System.out.println("failed tests: " + result.getFailureCount());
		System.out.println("ignored tests: " + result.getIgnoreCount());
		System.out.println("success: " + result.wasSuccessful());
	}

}